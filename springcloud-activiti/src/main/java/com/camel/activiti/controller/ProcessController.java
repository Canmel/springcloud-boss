package com.camel.activiti.controller;

import com.camel.activiti.service.ProcessService;
import com.camel.common.utils.IoUtils;
import com.camel.core.entity.Result;
import com.camel.core.entity.process.ActivitiForm;
import com.camel.core.entity.process.UserTask;
import com.camel.core.utils.ResultUtil;
import com.camel.exceptions.WorkFlowImageGenerateFaildException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
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
 * <测试流程>
 * @author baily
 * @since 1.0
 * @date 2019/9/16
 **/
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     发起申请 (流程ID)
     @return
     */
    @GetMapping("/applyById")
    public Result applyById(String busniessKey, String flowId) {
        processService.applyById(busniessKey, flowId);
        return ResultUtil.success("发起流程成功");
    }

    /**
     通过
     @param id
     @return
     */
    @GetMapping("/pass")
    public Result pass(String id, String comment, String businessId) {
        ActivitiForm activitiForm = new ActivitiForm(comment, businessId, true);
        Map paramMap = objectMapper.convertValue(activitiForm, HashMap.class);
        boolean isPass = processService.passProcess(id, paramMap, () -> {
            System.out.println("通过回调");
        });
        ProcessInstance processInstance = processService.processInstance(businessId);
        Map<String, Object> result = new HashMap<>(16);
        if (!ObjectUtils.isEmpty(processInstance) && !processInstance.isEnded()) {
            result.put("isEnd", false);
            return ResultUtil.success("审批成功", result);
        }
        result.put("isEnd", true);
        return ResultUtil.success("审批成功", result);
    }

    /**
     驳回
     @param id
     @return
     */
    @GetMapping("/back")
    public Result back(String id, String comment, String businessId) {
        ActivitiForm activitiForm = new ActivitiForm(comment, businessId, false);
        Map paramMap = objectMapper.convertValue(activitiForm, HashMap.class);
        boolean isBack = processService.backProcess(id, null, paramMap, () -> {
            System.out.println("工作流控制器中调用");
        });
        return ResultUtil.success("驳回成功");
    }

    @GetMapping("reject")
    public Result reject() {
        return ResultUtil.success("驳回成功");
    }

    @GetMapping("start")
    public Result start() {
        return ResultUtil.success("发起流程成功");
    }

    @GetMapping("current")
    public Result current(String busniessKey, String flowKey) {
        List<Task> tasks = processService.current(busniessKey, flowKey);
        List<UserTask> userTasks = new ArrayList<>();
        tasks.forEach(task -> {
            UserTask userTask = new UserTask();
            userTask.setName(task.getName());
            userTask.setDescription(task.getDescription());
            userTask.setEnd(false);
            userTask.setId(task.getId());
            userTasks.add(userTask);
        });
        return ResultUtil.success(userTasks);
    }

    /**
     获取评论信息列表
     @param id
     @return
     */
    @GetMapping("/comments")
    public Result comments(String id) {
        List<UserTask> list = processService.comments(id);
        return ResultUtil.success(list);
    }

    /**
     流程跟踪图
     @param response
     响应
     @param id
     任务ID
     */
    @GetMapping("/trace/{id}")
    public void taskImage(HttpServletResponse response, @PathVariable String id) {
        InputStream inputStream = processService.processTraceImage(id);

        try {
            OutputStream outputStream = response.getOutputStream();
            IoUtils.writeToOtputStream(outputStream, inputStream);
        } catch (IOException e) {
            throw new WorkFlowImageGenerateFaildException();
        }
    }

    @GetMapping("/todo")
    public Result toDo(Principal principal) {
        OAuth2Authentication authentication = (OAuth2Authentication) principal;
        List<UserTask> tasks = processService.toDo(authentication);
        return ResultUtil.success(tasks);
    }
}
