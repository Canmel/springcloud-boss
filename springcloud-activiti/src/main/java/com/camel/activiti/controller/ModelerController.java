package com.camel.activiti.controller;

import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.camel.common.RestServiceController;
import com.camel.util.ToWeb;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by liuruijie on 2017/4/20.
 * 模型管理
 */
@RestController
@RequestMapping("models")
public class ModelerController implements RestServiceController<Model, String> {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ObjectMapper objectMapper;

    /**
     * 新建一个空模型
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    @PostMapping("newModel")
    public Result newModel() throws UnsupportedEncodingException {
        //初始化一个空模型
        Model model = repositoryService.newModel();

        //设置一些默认信息
        String name = "new-process";
        String description = "";
        int revision = 1;
        String key = "process";

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
        return ResultUtil.success("成功", "../editor?modelId=" + id);
    }


    /**
     * 发布模型为流程定义
     *
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("{id}/deployment")
    public Result deploy(@PathVariable("id") String id) throws Exception {

        //获取模型
        Model modelData = repositoryService.getModel(id);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(), "模型数据为空，请先设计流程并成功保存，再进行发布。");
        }

        JsonNode modelNode = new ObjectMapper().readTree(bytes);

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0) {
            return ResultUtil.error(ResultEnum.BAD_REQUEST.getCode(), "数据模型不符要求，请至少设计一条主线流程。");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8")).category(modelData.getCategory())
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);

        return ResultUtil.success("发布流程成功！");
    }

    @Override
    public Object getOne(@PathVariable("id") String id) {
        Model model = repositoryService.createModelQuery().modelId(id).singleResult();
        return ToWeb.buildResult().setObjData(model);
    }

    @Override
    public Result getList(@RequestParam(value = "rowSize", defaultValue = "1000", required = false) Integer rowSize, @RequestParam(value = "page", defaultValue = "1", required = false) Integer page) {
        List<Model> list = repositoryService.createModelQuery().listPage(rowSize * (page - 1)
                , rowSize);
        long count = repositoryService.createModelQuery().count();
        return ResultUtil.success(list);
    }

    @Override
    public Result deleteOne(@PathVariable("id") String id) {
        repositoryService.deleteModel(id);
        return ResultUtil.success("删除模型成功");
    }

    @Override
    public Object postOne(@RequestBody Model entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object putOne(@PathVariable("id") String s, @RequestBody Model entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object patchOne(@PathVariable("id") String s, @RequestBody Model entity) {
        throw new UnsupportedOperationException();
    }


}
