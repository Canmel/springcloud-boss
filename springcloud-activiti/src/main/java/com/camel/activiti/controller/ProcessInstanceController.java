package com.camel.activiti.controller;

import com.camel.activiti.utils.ActivitiObj2HashMapUtils;
import com.camel.common.RestServiceController;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
 * <流程实例>
 * @author baily
 * @since 1.0
 * @date 2019/9/18
 **/
@RestController
@RequestMapping("/instance")
public class ProcessInstanceController implements RestServiceController<ProcessInstance, String> {

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public Object getOne(String s) {
        return null;
    }

    @Override
    public Result getList(@RequestParam(value = "rowSize", defaultValue = "10", required = false) Integer rowSize, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
        List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().active().orderByProcessInstanceId().desc().listPage(rowSize * (page - 1), rowSize);
        List<Map> list = new ArrayList<>();
        instanceList.forEach(processInstance -> {
            list.add(ActivitiObj2HashMapUtils.getInstance().processInstance2Map(processInstance));
        });
        Map<String, Object> result = new HashMap<>(16);
        result.put("count", runtimeService.createProcessInstanceQuery().active().count());
        result.put("list", list);

        return ResultUtil.success(result);
    }

    @Override
    public Object postOne(ProcessInstance entity) {
        return null;
    }

    @Override
    public Object putOne(String s, ProcessInstance entity) {
        return null;
    }

    @Override
    public Object patchOne(String s, ProcessInstance entity) {
        return null;
    }

    @Override
    public Result deleteOne(String s) {
        return null;
    }
}
