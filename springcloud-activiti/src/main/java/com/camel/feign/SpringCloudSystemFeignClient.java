package com.camel.feign;

import com.camel.activiti.config.KeepErrMsgConfiguration;
import com.camel.core.entity.Result;
import com.camel.feign.fallback.SpringCloudBpmApprovalFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
@FeignClient(value = "springcloud-system", fallback = SpringCloudBpmApprovalFallback.class, configuration = {KeepErrMsgConfiguration.class})
public interface SpringCloudSystemFeignClient {

    /**
     * 获取所有角色
     * @return
     */
    @RequestMapping(value = "/sysRole/all/list", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Result allRole();

    /**
     * 通过角色获取用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/sysUser/role/{id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Result usersByRole(@PathVariable("id") String id);
}
