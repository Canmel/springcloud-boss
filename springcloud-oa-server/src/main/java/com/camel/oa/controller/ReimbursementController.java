package com.camel.oa.controller;


import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.toolkit.MapUtils;
import com.camel.common.entity.Member;
import com.camel.core.BaseProcessService;
import com.camel.core.config.ProcessProperties;
import com.camel.core.controller.BaseCommonController;
import com.camel.core.entity.Result;
import com.camel.core.entity.process.ActivitiForm;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.enums.ReimbursementStatus;
import com.camel.oa.feign.SpringCloudActivitiFeignClient;
import com.camel.oa.model.Reimbursement;
import com.camel.oa.service.ReimbursementService;
import com.camel.oa.service.impl.BaseProcessServiceImpl;
import com.camel.redis.entity.RedisUser;
import com.camel.redis.utils.SessionContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <报销控制器>
 * @author baily
 * @since 1.0
 * @date 2019/10/15
 **/
@RestController
@RequestMapping("/reimbursement")
public class ReimbursementController extends BaseCommonController {
    public static final String USER_ID_PROP_NAME = "id";

    @Autowired
    private ReimbursementService service;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BaseProcessService baseProcessService;

    @Autowired
    private SpringCloudActivitiFeignClient springCloudActivitiFeignClient;

    @GetMapping
    public Result index(Reimbursement reimbursement) {
        return ResultUtil.success(service.selectPage(reimbursement));
    }

    @GetMapping("/{id}")
    public Result ditail(@PathVariable Integer id) {
        return super.details(id);
    }

    @PostMapping
    public Result save(@RequestBody Reimbursement reimbursement) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, username);
        reimbursement.setCreator(member.getId());
        return super.save(reimbursement);
    }

    @PutMapping
    public Result update(@RequestBody Reimbursement reimbursement) {
        return super.update(reimbursement);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return super.delete(id);
    }

    @GetMapping("/apply/{id}")
    public Result apply(@PathVariable Integer id, String flowId) {
        Result result = super.applyed(id, flowId);
        Reimbursement reimbursement = new Reimbursement(id, ReimbursementStatus.APPLY.getValue());
        service.updateById(reimbursement);
        return result;
    }

    @GetMapping("/current/{id}")
    public Result cur(@PathVariable Integer id) {
        return super.currented(id);
    }

    @GetMapping("/comment/{id}")
    public Result com(@PathVariable String id) {
        Result result = super.commen(id);
        return result;
    }

    @GetMapping("/pass/{id}")
    public Result pass(@PathVariable Integer id, ActivitiForm activitiForm) {
        Result result = super.passed(id, activitiForm);
        HashMap<String, Object> rMapData = (HashMap<String, Object>) result.getData();
        if (MapUtils.isNotEmpty(rMapData) && (boolean) rMapData.get(ProcessProperties.PROCESS_ISEND_KEY)) {
            service.updateById(new Reimbursement(id, ReimbursementStatus.APPLY_SUCCESS.getValue()));
        }
        return result;
    }

    @GetMapping("/back/{id}")
    public Result back(@PathVariable String id, ActivitiForm activitiForm) {
        Result result = super.backed(id, activitiForm);
        if (!ObjectUtils.isEmpty(result) && result.isSuccess()) {
            service.updateById(new Reimbursement(Integer.parseInt(activitiForm.getBusinessId()), ReimbursementStatus.APPLY_FAILD.getValue()));
        }
        return result;
    }

    @GetMapping("/todo")
    public Result toDo() {
        Result result = super.todoies();
        return result;
    }

    @Override
    public IService getiService() {
        return service;
    }

    @Override
    public String getMouduleName() {
        return "报销";
    }

    @GetMapping("/status")
    public Result reimbursementStatus() {
        return ResultUtil.success(ReimbursementStatus.all());
    }

    @Override
    public String getBusinessKey() {
        return Reimbursement.class.getSimpleName().toUpperCase();
    }

    @Override
    public BaseProcessService getProcessService() {
        return baseProcessService;
    }
}

