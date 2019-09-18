package com.camel.activiti.service.impl;

import com.camel.activiti.service.FeignService;
import com.camel.core.entity.Result;
import com.camel.feign.SpringCloudSystemFeignClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
 * <>
 * @author baily
 * @since 1.0
 * @date 2019/9/18
 **/
@Service
public class FeignServiceImpl implements FeignService {
    @Resource
    private SpringCloudSystemFeignClient springCloudSystemFeignClient;

    @Override
    public Result allRole() {
        return springCloudSystemFeignClient.allRole();
    }

    @Override
    public Result usersByRole(Integer id) {
        return springCloudSystemFeignClient.usersByRole(id.toString());
    }
}
