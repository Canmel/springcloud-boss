package com.camel.activiti.controller;

import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.activiti.vo.DeploymentResponse;
import com.camel.common.RestServiceController;
import com.camel.util.ActivitiObj2HashMapUtils;
import com.camel.util.ToWeb;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liuruijie on 2017/4/20.
 */
@RestController
@RequestMapping("deployments")
public class DeploymentController implements RestServiceController<Deployment, String> {
    @Autowired
    RepositoryService repositoryService;

    @Override
    public Object getOne(@PathVariable("id") String id) {
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(id).singleResult();
        return ToWeb.buildResult().setObjData(new DeploymentResponse(deployment));
    }

    @GetMapping("def/{key}")
    public Result deployed(@PathVariable String key) {
        List<ProcessDefinition> l = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).latestVersion().list();
        List<Map<String, Object>> result = new ArrayList<>();
        l.forEach(processDefinition -> {
            result.add(ActivitiObj2HashMapUtils.getInstance().processDefinition2Map(processDefinition));
        });
        return ResultUtil.success(result);
    }

    @Override
    public Result getList(@RequestParam(value = "rowSize", defaultValue = "1000", required = false) Integer rowSize, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().list();
        List<Map<String, Object>> result = new ArrayList<>();
        list.forEach(processDefinition -> {
            result.add(ActivitiObj2HashMapUtils.getInstance().processDefinition2Map(processDefinition));
        });
        return ResultUtil.success(result);
    }

    @Override
    public Result deleteOne(@PathVariable("id") String id) {
        repositoryService.deleteDeployment(id);
        return ResultUtil.success("删除部署成功");
    }

    @Override
    public Object postOne(@RequestBody Deployment entity) {
        return null;
    }

    @Override
    public Object putOne(@PathVariable("id") String s, @RequestBody Deployment entity) {
        return null;
    }

    @Override
    public Object patchOne(@PathVariable("id") String s, @RequestBody Deployment entity) {
        return null;
    }
}
