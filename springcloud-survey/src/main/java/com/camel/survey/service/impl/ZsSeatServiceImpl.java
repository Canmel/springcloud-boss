package com.camel.survey.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.core.utils.ResultUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.camel.survey.mapper.ZsSeatMapper;
import com.camel.survey.mapper.ZsSurveyMapper;
import com.camel.survey.model.Args;
import com.camel.survey.model.ZsSeat;
import com.camel.survey.service.ZsSeatService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

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

    @Override
    public PageInfo<ZsSeat> pageQuery(ZsSeat zsSeat) {
        PageInfo pageInfo = PageHelper.startPage(zsSeat.getPageNum(), zsSeat.getPageSize()).doSelectPageInfo(()-> mapper.list(zsSeat));
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
        deleteByUserAndSeat(entity.getUid(),entity.getSeatNum());
        if (insert(entity)) {
            return ResultUtil.success("分配成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "分配失败");
    }

    @Override
    public boolean deleteByUserAndSeat(int userId,String seatNum) {
        Wrapper<ZsSeat> zsSeatWrapper = new EntityWrapper<>();
        zsSeatWrapper.eq("uid", userId);
        delete(zsSeatWrapper);
        zsSeatWrapper.eq("uid", null);
        zsSeatWrapper.eq("seat_num", seatNum);
        return delete(zsSeatWrapper);
    }
}
