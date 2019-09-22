package com.camel.activiti.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
 * <主页>
 * @author baily
 * @since 1.0
 * @date 2019/9/18
 **/
@RestController
@RequestMapping("home")
public class DashBoardController {
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("status")
    public Result processStatus() {
        Map<String, Object> result = new HashMap<>();
        result.put("modelNum", repositoryService.createModelQuery().count());
        result.put("deploymentNum",  repositoryService.createProcessDefinitionQuery().latestVersion().count());
        result.put("instanceNum",  runtimeService.createProcessInstanceQuery().active().count());
        result.put("taskNum",  taskService.createTaskQuery().count());
        return ResultUtil.success(result);
    }

    @GetMapping("board")
    public Result processDashBoard() {
        Map<String, Object> map = new HashMap<>();
        map.put("instanceNum", runtimeService.createProcessInstanceQuery().count());
        map.put("taskActiveNum", taskService.createTaskQuery().active().count());
        map.put("taskNum", taskService.createTaskQuery().count());
        map.put("hisTaskNum", historyService.createHistoricTaskInstanceQuery().count());
        map.put("hisInstanceNum", historyService.createHistoricActivityInstanceQuery().count());
        return ResultUtil.success(map);
    }
}
