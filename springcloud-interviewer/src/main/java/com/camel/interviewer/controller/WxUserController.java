package com.camel.interviewer.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.interviewer.annotation.AuthIgnore;
import com.camel.interviewer.model.WxSubscibe;
import com.camel.interviewer.model.WxUser;
import com.camel.interviewer.service.WxSubscibeService;
import com.camel.interviewer.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import com.camel.core.controller.BaseCommonController;
import org.thymeleaf.postprocessor.PostProcessor;

import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 微信用户信息 前端控制器
 * </p>
 *
 * @author baily
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/wxUser")
public class WxUserController extends BaseCommonController {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WxSubscibeService subscibeService;

    public static final String QUEUE_NAME = "ActiveMQ.System.New.User";

    @GetMapping
    private Result index(WxUser wxUser) {
        return ResultUtil.success(service.pageQuery(wxUser));
    }

    @PutMapping("/{id}")
    private Result changeShareUser(@PathVariable Integer id, @RequestBody Map<String, String> params) {
        String openid = params.get("openid");
        WxUser wxUser = service.selectById(id);
        Wrapper<WxSubscibe> wrapper = new EntityWrapper<>();
        wrapper.eq("user", wxUser.getOpenid());
        wrapper.eq("status", 1);
        List<WxSubscibe> wxSubscibes = subscibeService.selectList(wrapper);
        WxSubscibe wxSubscibe = null;
        if(!CollectionUtil.isEmpty(wxSubscibes)) {
            wxSubscibe = wxSubscibes.get(0);
        }
        if(!ObjectUtils.isEmpty(wxSubscibe)) {
            wxSubscibe.setShareUser(openid);
            subscibeService.updateById(wxSubscibe);
            return ResultUtil.success("修改推荐人成功");
        }
        wxSubscibe = new WxSubscibe();
        wxSubscibe.setUser(wxUser.getOpenid());
        wxSubscibe.setShareUser(openid);
        subscibeService.insert(wxSubscibe);
        return ResultUtil.success("修改推荐人成功");
    }

    @AuthIgnore
    @PostMapping
    private Result save(@RequestBody WxUser wxUser) {
        Object code = redisTemplate.opsForValue().get(wxUser.getPhone());
        if(!wxUser.getValidCode().equals(code)) {
            return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "验证码无效或已过期");
        }
        if(!ObjectUtils.isEmpty(wxUser.getIdNum())) {
            Wrapper wrapper = new EntityWrapper();
            wrapper.eq("id_num", wxUser.getIdNum());
            int count = service.selectCount(wrapper);
            if(count > 0) {
                return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "身份证号已经被注册，如果被占用请联系管理员");
            }
            Wrapper wrapperEmail = new EntityWrapper();
            wrapperEmail.eq("email", wxUser.getEmail());
            int c = service.selectCount(wrapperEmail);
            if(c > 0) {
                return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "邮箱已经被使用了");
            }
        }
        boolean insert = service.insert(wxUser);
        if (insert) {
            jmsMessagingTemplate.convertAndSend(QUEUE_NAME, new SysUser(null, wxUser.getUsername(), "123456", wxUser.getUsername(), wxUser.getEmail(), wxUser.getPhone(), wxUser.getIdNum()));
            redisTemplate.delete(wxUser.getPhone());
            return ResultUtil.success("新增" + getMouduleName() + "成功", wxUser);
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),"新增" + getMouduleName() + "失败");
    }

    @AuthIgnore
    @PutMapping
    private Result update(@RequestBody WxUser wxUser) {
        Object code = redisTemplate.opsForValue().get(wxUser.getPhone());
        if(!wxUser.getValidCode().equals(code)) {
            return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "验证码无效或已过期");
        }
        if (service.updateById(wxUser)) {
            jmsMessagingTemplate.convertAndSend(QUEUE_NAME, new SysUser(wxUser.getId(), wxUser.getUsername(), null, wxUser.getUsername(), wxUser.getEmail(), wxUser.getPhone(), wxUser.getIdNum()));
            redisTemplate.delete(wxUser.getPhone());
            return ResultUtil.success("修改" + getMouduleName() + "成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),"修改" + getMouduleName() + "失败");
    }

    @Autowired
    private WxUserService service;

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "用户信息";
    }
}

