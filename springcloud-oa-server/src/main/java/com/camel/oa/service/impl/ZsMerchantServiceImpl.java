package com.camel.oa.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.camel.core.entity.Result;
import com.camel.core.utils.PaginationUtil;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.mapper.ZsMerchantMapper;
import com.camel.oa.model.ZsMerchant;
import com.camel.oa.service.ZsMerchantService;
import com.camel.oa.utils.ApplicationToolsUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

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
 * <客商服务>
 * @author baily
 * @since 1.0
 * @date 2019/10/31
 **/
@Service
public class ZsMerchantServiceImpl extends ServiceImpl<ZsMerchantMapper, ZsMerchant> implements ZsMerchantService {

    @Autowired
    private ZsMerchantMapper mapper;

    @Autowired
    private ApplicationToolsUtils applicationToolsUtils;

    @Override
    public PageInfo selectPage(ZsMerchant entity) {
        return applicationToolsUtils.selectPage(entity, () -> {
            mapper.list(entity);
        });
    }

    @Override
    public Result save(ZsMerchant entity, OAuth2Authentication authentication) {
        applicationToolsUtils.setCreator(authentication, entity);
        if (mapper.insert(entity) > -1) {
            return ResultUtil.success("新增成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "新增失败");
    }
}
