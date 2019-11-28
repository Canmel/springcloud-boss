package com.camel.attendance.service.impl;

import com.camel.attendance.enums.SignRecordStatus;
import com.camel.attendance.enums.SignRecordType;
import com.camel.attendance.model.Args;
import com.camel.attendance.model.SignRecords;
import com.camel.attendance.mapper.SignRecordsMapper;
import com.camel.attendance.service.ArgsService;
import com.camel.attendance.service.SignRecordsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.common.entity.Member;
import com.camel.core.entity.Result;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.redis.utils.SessionContextUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 　　　　　　　 ┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━━━┛┻┓
 * 　　　　　　　┃         ┃ 　
 * 　　　　　　　┃    ━    ┃
 * 　　　　　　　┃  >   <  ┃
 * 　　　　　　　┃         ┃
 * 　　　　　　　┃... ⌒ ...┃
 * 　　　　　　　┃         ┃
 *             ┗━┓     ┏━┛
 *               ┃     ┃　Code is far away from bug with the animal protecting　　　　　　　　　　
 *               ┃     ┃   神兽保佑,代码无bug
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┃  　　　　　　
 *               ┃     ┃        <打卡记录 服务实现类>
 *               ┃     ┃　　　　　　　　　　　
 *               ┃     ┗━━━━┓   @author baily
 *               ┃          ┣┓
 *               ┃          ┏┛  @since 1.0
 *               ┗┓┓┏━━━━┳┓┏┛
 *                ┃┫┫    ┃┫┫    @date 2019-11-27
 *                ┗┻┛    ┗┻┛
 */
@Service
public class SignRecordsServiceImpl extends ServiceImpl<SignRecordsMapper, SignRecords> implements SignRecordsService {

    @Autowired
    private SignRecordsMapper mapper;

    @Autowired
    private ArgsService argsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<SignRecords> selectPage(SignRecords entity) {
        PageInfo pageInfo = PaginationUtil.startPage(entity, () -> {
            mapper.list(entity);
        });
        return pageInfo;
    }

    @Override
    public Boolean signIn(SignRecords signRecords, OAuth2Authentication auth2Authentication) {
        // 获取打卡时间的配置，完成记录， 创建快照
        List<Args> argsList = argsService.selectForMain();
        argsList.forEach(args -> {
            if(Args.SIGN_IN_TIME_CODE.equals(args.getCode())) {
                signRecords.setSignInTime(args.getValue());
            }
            if(Args.SIGN_OUT_TIME_CODE.equals(args.getCode())) {
                signRecords.setSignOutTime(args.getValue());
            }
            if(Args.ADVANCE_TIME_CODE.equals(args.getCode())) {
                signRecords.setAdvanceTime(Integer.parseInt(args.getValue()));
            }
            if(Args.DELAY_TIME_CODE.equals(args.getCode())) {
                signRecords.setDelayTime(Integer.parseInt(args.getValue()));
            }
        });

        Member member = (Member) SessionContextUtils.getInstance().currentUser(redisTemplate, auth2Authentication.getName());
        signRecords.setUserId(member.getId());
        signRecords.setStatus(SignRecordStatus.NORMAL.getCode());
        signRecords.setType(SignRecordType.SIGN_IN.getCode());

        return insert(signRecords);
    }

    @Override
    public Boolean signOut(SignRecords signRecords, OAuth2Authentication auth2Authentication) {
        return insert(signRecords);
    }
}
