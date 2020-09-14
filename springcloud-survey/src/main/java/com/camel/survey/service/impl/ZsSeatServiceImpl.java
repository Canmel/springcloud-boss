package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.camel.survey.enums.ZsYesOrNo;
import com.camel.survey.mapper.ZsSeatMapper;
import com.camel.survey.mapper.ZsSurveyMapper;
import com.camel.survey.model.Args;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.service.ZsSeatService;
import com.camel.survey.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baily
 * @since 2020-02-19
 */
@Service
public class ZsSeatServiceImpl extends ServiceImpl<ZsSeatMapper, ZsSeat> implements ZsSeatService {

    @Autowired
    public ZsSeatMapper mapper;

    @Autowired
    public ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo<ZsSeat> pageQuery(ZsSeat zsSeat) {
        PageInfo pageInfo = PageHelper.startPage(zsSeat.getPageNum(), zsSeat.getPageSize()).doSelectPageInfo(()-> mapper.list(zsSeat));
        List<ZsSeat> seats = (List<ZsSeat>) pageInfo.getList();
        seats.forEach(record -> {
            record.setUser(applicationToolsUtils.getUser(record.getUid()));
        });
        return pageInfo;
    }

    @Override
    public ZsSeat selectByUid(Integer id) {
        Wrapper<ZsSeat> zsSeatWrapper = new EntityWrapper<>();
        zsSeatWrapper.eq("uid", id);
        return selectOne(zsSeatWrapper);
    }

    @Override
    public ZsSeat selectLastByUser(Integer id) {
        return mapper.selectLastByUser(id);
    }

    @Override
    public Result save(ZsSeat entity, OAuth2Authentication oAuth2Authentication) {
        if(entity.getSeatNum()==null||entity.getSeatNum().equals("")){
            return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "坐席号不可为空");
        }
        if(entity.getPassword()==null||entity.getPassword().equals("")){
            return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "坐席号密码不可为空");
        }
        Wrapper<ZsSeat> zsSeatWrapper = new EntityWrapper<>();
        zsSeatWrapper.eq("seat_num", entity.getSeatNum());
        if(selectList(zsSeatWrapper).size()>0){
            return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "该坐席号已存在");
        }
        if (insert(entity)) {
            return ResultUtil.success("新增坐席成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增坐席失败");
    }

    @Override
    public void callbackByUser(int userId) {
        mapper.callbackByUid(userId);
    }

    @Override
    public ZsSeat selectBySeat(String seat) {
        return mapper.searchBySeatNum(seat);
    }

    @Override
    public void callback(Integer id) {
        mapper.callback(id);
    }

    @Override
    public boolean assignSeat(Integer uid, Integer surveyId){
        Wrapper<ZsSeat> wrapper = new EntityWrapper<>();
        wrapper.eq("uid",uid);
        List<ZsSeat> seats = selectList(wrapper);
        if(seats.size()==0){
            Wrapper<ZsSeat> wrapper1 = new EntityWrapper<>();
            wrapper1.eq("state",0);
            if(selectCount(wrapper1)>0){
                ZsSeat seat = selectList(wrapper1).get(0);
                seat.setState(ZsYesOrNo.YES);
                seat.setUid(uid);
                seat.setWorkNum((seat.getUid()+1000)+"");
                if(!ObjectUtils.isEmpty(surveyId)) {
                    seat.setSurveyId(surveyId);
                }
                updateById(seat);
                return mapper.assignSeat(seat.getSeatNum(),seat.getUid());
            }
            else{
                return false;
            }
        } else {
            ZsSeat seat = seats.get(0);
            seat.setSurveyId(surveyId);
            updateById(seat);
        }
        return true;
    }

    @Override
    public Result manualAssign(ZsSeat entity, OAuth2Authentication oAuth2Authentication) {
        Wrapper<ZsSeat> wrapper = new EntityWrapper<>();
        wrapper.eq("seat_num",entity.getSeatNum());
        ZsSeat seat = selectOne(wrapper);
        if(seat!=null){
            if(seat.getState().getCode()==0){
                callbackByUser(entity.getUid());
            }
            else{
                return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "该坐席已分配，不可重复分配");
            }
        }
        else{
            return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "该坐席号不存在，请先新建坐席");
        }
        seat.setState(ZsYesOrNo.YES);
        seat.setUid(entity.getUid());
        seat.setWorkNum((seat.getUid()+1000)+"");
        seat.setPassword(entity.getPassword());
        if (updateById(seat)) {
            return ResultUtil.success("分配成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "分配失败");
    }
}
