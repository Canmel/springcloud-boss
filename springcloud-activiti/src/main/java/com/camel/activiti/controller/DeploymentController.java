package com.camel.activiti.controller;

import com.camel.activiti.utils.ActivitiObj2HashMapUtils;
import com.camel.core.entity.Result;
import com.camel.core.utils.ResultUtil;
import com.camel.activiti.vo.DeploymentResponse;
import com.camel.common.RestServiceController;
import com.camel.util.ToWeb;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 * <部署>
 * @author baily
 * @since 1.0
 * @date 2019/9/18
 **/
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
    public Result getList(@RequestParam(value = "rowSize", defaultValue = "10", required = false) Integer rowSize, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().latestVersion().listPage(rowSize * (page - 1), rowSize);
        List<Map<String, Object>> result = new ArrayList<>();
        list.forEach(processDefinition -> {
            result.add(ActivitiObj2HashMapUtils.getInstance().processDefinition2Map(processDefinition));
        });
        Map<String, Object> r = new HashMap<>(16);
        r.put("count", repositoryService.createProcessDefinitionQuery().latestVersion().count());
        r.put("list", result);
        return ResultUtil.success(r);
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
