package com.camel.core;

import com.camel.core.entity.Result;
import com.camel.core.entity.process.ActivitiForm;

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
 * <流程服务基类>
 * @author baily
 * @since 1.0
 * @date 2019/7/11
 **/
public interface BaseProcessService {
    /**
     发起申请
     @param id      业务KEY
     @param flowId  流程ID
     @param businessKey 业务KEY
     @return 是否完成
     */
    Boolean apply(Integer id, String flowId, String businessKey);

    /**
     查询当前流程
     @param id
     @param businessKey
     @return
     */
    Result current(Integer id, String businessKey);

    /**
     查询当前流程
     @param id
     @param businessKey
     @return
     */
    Result current(String id, String businessKey);

    /**
     * 通过
     * @param id
     * @param activitiForm
     * @param businessKey
     * @return
     */
    Result pass(String id, ActivitiForm activitiForm, String businessKey);

    /**
     * 驳回
     * @param id
     * @param activitiForm
     * @param businessKey
     * @return
     */
    Result back(String id, ActivitiForm activitiForm, String businessKey);

    /**
     * 审核意见
     * @param id
     * @return
     */
    Result comment(String id);

    /**
     * 获取代办事项
     * @return
     */
    Result toDO();
}
