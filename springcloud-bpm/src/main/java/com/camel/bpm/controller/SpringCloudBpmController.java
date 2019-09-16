package com.camel.bpm.controller;

import com.camel.bpm.exceptions.WorkFlowImageGenerateFaildException;
import com.camel.bpm.model.WorkFlow;
import com.camel.bpm.service.BpmService;
import com.camel.common.utils.IoUtils;
import com.camel.core.entity.Result;
import com.camel.core.entity.process.ActivitiForm;
import com.camel.core.entity.process.UserTask;
import com.camel.core.utils.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.CollectionUtils;
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
 @author baily */
@RestController
@RequestMapping("/flow")
public class SpringCloudBpmController {

    @Autowired
    private ProcessEngine engine;

    @Autowired
    private BpmService service;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     部署
     @return
     */
    @GetMapping("/deploy/{id}")
    public Result deploy(@PathVariable Integer id) {
        Deployment deployment = service.deploy(id);
        return ResultUtil.success("部署完成" + deployment.getId(), deployment);
    }

    /**
     查询流程定义
     @param key
     @return
     */
    @GetMapping("/def/{key}")
    public Result definition(@PathVariable String key) {
        List result = service.definition(key);
        if (CollectionUtils.isEmpty(result)) {
            return ResultUtil.success("未找到相关流程", result);
        }
        return ResultUtil.success("查询成功", result);
    }

    /**
     根据流程定义ID 启动流程
     @param definitionId
     @return
     */
    @GetMapping("/start/{definitionId}")
    public Result start(@PathVariable String definitionId) {
        ProcessInstance instance = engine.getRuntimeService().startProcessInstanceById(definitionId);
        return ResultUtil.success("启动完成", instance.getId());
    }

    /**
     根据用户,查询当前任务
     @param assignee
     @return
     */
    @GetMapping("/task/{assignee}")
    public Result queryTask(@PathVariable String assignee) {
        List list = service.queryTask(assignee);
        return CollectionUtils.isEmpty(list) ? ResultUtil.success("未找到相关流程", list) : ResultUtil.success("查询成功", list);
    }

    @GetMapping("/defWorkflows")
    public Result defWorkflows(WorkFlow workFlow) {
        return ResultUtil.success(service.defWorkflows(workFlow));
    }


    /**
     发起申请 (流程ID)
     @return
     */
    @GetMapping("/applyById")
    public Result applyById(String busniessKey, String flowId) {
        service.applyById(busniessKey, flowId);
        return ResultUtil.success("发起流程成功");
    }

    /**
     发起申请 （流程KEY）
     @return
     */
    @GetMapping("/apply")
    public Result appl(String busniessKey, String flowKey) {
        if (service.apply(busniessKey, flowKey)) {
            return ResultUtil.success("发起流程成功");
        }
        return ResultUtil.success("发起流程失败", false);
    }

    @GetMapping("/current")
    public Result current(String busniessKey, String flowKey) {
        List<ProcessInstance> instances = engine.getRuntimeService().createProcessInstanceQuery().list();
        for (ProcessInstance i: instances) {
            System.out.println(i.getBusinessKey());
        }
        List<Task> tasks = service.current(busniessKey, flowKey);
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
     流程跟踪图
     @param response
     响应
     @param id
     任务ID
     */
    @GetMapping("/trace/{id}")
    public void taskImage(HttpServletResponse response, @PathVariable String id) {
        InputStream inputStream = service.processTraceImage(id);

        try {
            OutputStream outputStream = response.getOutputStream();
            IoUtils.writeToOtputStream(outputStream, inputStream);
        } catch (IOException e) {
            throw new WorkFlowImageGenerateFaildException();
        }
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
        boolean isPass = service.passProcess(id, paramMap, () -> {
            System.out.println("通过回调");
        });
        ProcessInstance processInstance = service.processInstance(businessId);
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
        boolean isBack = service.backProcess(id, null, paramMap, () -> {
            System.out.println("工作流控制器中调用");
        });
        return ResultUtil.success("驳回成功");
    }

    /**
     获取评论信息列表
     @param id
     @return
     */
    @GetMapping("/comments")
    public Result comments(String id) {
        List<UserTask> list = service.comments(id);
        return ResultUtil.success(list);
    }

    /**
     获取评论信息列表
     @param id
     @return
     */
    @GetMapping("/commentsByInstanceId")
    public Result commentsByInstanceId(String id) {
        List<UserTask> list = service.commentsByInstanceId(id);
        return ResultUtil.success(list);
    }

    @GetMapping("/todo")
    public Result toDo(Principal principal) {
        OAuth2Authentication authentication = (OAuth2Authentication) principal;
        List<UserTask> tasks = service.toDo(authentication);
        return ResultUtil.success(tasks);
    }

    @GetMapping("/index")
    public String index() {
        return "bpm服务启动成功";
    }
}
