package com.camel.oa.service.impl;

import com.camel.core.BaseProcessService;
import com.camel.core.entity.Result;
import com.camel.core.entity.process.ActivitiForm;
import com.camel.oa.feign.SpringCloudActivitiFeignClient;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
 * <流程基类>
 * @author baily
 * @since 1.0
 * @date 2019/7/10
 **/
@Service
public class BaseProcessServiceImpl implements BaseProcessService {

    @Resource
    private SpringCloudActivitiFeignClient springCloudActivitiFeignClient;

    @Override
    public Boolean apply(Integer id, String flowId, String busniessKey) {
        Result result = springCloudActivitiFeignClient.applyById(busniessKey + id, flowId);
        return ObjectUtils.isEmpty(result) ? false : result.isSuccess();
    }

    @Override
    public Result current(Integer id, String businessKey) {
        Result result = current(id.toString(), businessKey);
        return result;
    }

    @Override
    public Result current(String id, String businessKey) {
        Result result = springCloudActivitiFeignClient.current(businessKey + id, businessKey);
        return result;
    }

    @Override
    public Result pass(String id, ActivitiForm activitiForm, String businessKey) {
        Result result = springCloudActivitiFeignClient.pass(id, activitiForm.getComment(), businessKey + activitiForm.getBusinessId());
        return result;
    }

    @Override
    public Result back(String id, ActivitiForm activitiForm, String businessKey) {
        Result result = springCloudActivitiFeignClient.back(id, activitiForm.getComment(), businessKey + activitiForm.getBusinessId());
        return result;
    }

    @Override
    public Result comment(String id) {
        Result result = springCloudActivitiFeignClient.comment(id);
        return result;
    }

    @Override
    public Result toDO() {
        Result result = springCloudActivitiFeignClient.toDo();
        return result;
    }
}
