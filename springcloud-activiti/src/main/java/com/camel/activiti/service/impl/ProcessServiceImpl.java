package com.camel.activiti.service.impl;

import com.camel.activiti.service.ProcessService;
import com.camel.activiti.utils.ActivitiObj2SystemObjUtils;
import com.camel.core.entity.process.ActivitiEndCallBack;
import com.camel.core.entity.process.UserTask;
import com.camel.exceptions.ProcessNotFoundException;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
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
 * <>
 * @author baily
 * @since 1.0
 * @date 2019/8/28
 **/
@Service
public class ProcessServiceImpl implements ProcessService {
    public static final String END_TAG = "END";

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

//    @Override
//    public List queryTask(String key) {
//        TaskQuery query = taskService.createTaskQuery();
//        query.active().taskAssignee(key);
//        List list = new ArrayList();
//        query.list().forEach(task -> {
//            Map<String, Object> map = new HashMap<>(16);
//            map.put("id", task.getId());
//            map.put("assignee", task.getAssignee());
//            map.put("name", task.getName());
//            map.put("description", task.getDescription());
//            list.add(map);
//        });
//        return list;
//    }


//    @Override
//    public List queryTaskByGroupId(List<String> groupId) {
//        TaskQuery query = taskService.createTaskQuery();
//        List<Task> tasks = query.taskCandidateGroupIn(groupId).list();
//        return tasks;
//    }

    @Override
    public boolean applyById(String busniessKey, String flowId) {
        ProcessInstance instance = runtimeService.startProcessInstanceById(flowId, busniessKey);
        return ObjectUtils.allNotNull(instance);
    }

    @Override
    public boolean apply(String busniessKey, String flowKey) {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(flowKey, busniessKey);
        return ObjectUtils.allNotNull(instance);
    }

    @Override
    public List<UserTask> commentsByInstanceId(String id) {
        return null;
    }

    @Override
    public List<Task> current(String busniessKey, String processDifinitionKey) {
        List<ProcessInstance> instance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(busniessKey).active().list();
        if (org.springframework.util.ObjectUtils.isEmpty(instance)) {
            // The current process is empty, and there may be value in the history.
            List<HistoricProcessInstance> hpi = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(busniessKey).list();
            if (!org.springframework.util.ObjectUtils.isEmpty(hpi)) {
                HistoricProcessInstance historicProcessInstance = hpi.get(hpi.size() - 1);
                List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(busniessKey).processInstanceId(historicProcessInstance.getId())
                        .finished().list();
                List<Task> result = new ArrayList<>();
                historicTaskInstanceList.forEach(htask -> {
                    TaskEntity task = new TaskEntity();
                    task.setName(htask.getName());
                    task.setId(htask.getId());
                    result.add(task);
                });
                return result;
            }
            throw new ProcessNotFoundException();
        } else {
            return taskService.createTaskQuery().processInstanceId(instance.get(0).getId()).active().list();
        }
    }

    @Override
    public InputStream processTraceImage(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = "";
        if (!org.springframework.util.ObjectUtils.isEmpty(task)) {
            processInstanceId = task.getProcessInstanceId();
        } else {
            HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
            processInstanceId = taskInstance.getProcessInstanceId();
        }

        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        // 获取历史流程实例
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();

        // 获取流程中已经执行的节点，按照执行先后顺序排序
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceId().asc().list();

        // 高亮已经执行流程节点ID集合
        List<String> highLightedActivitiIds = new ArrayList<>();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            highLightedActivitiIds.add(historicActivityInstance.getActivityId());
        }

        if (CollectionUtils.isEmpty(highLightedActivitiIds)) {
            List<HistoricTaskInstance> entities = historyService.createHistoricTaskInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).list();
            for (HistoricTaskInstance entity : entities) {
                highLightedActivitiIds.add(entity.getId());
            }
            historyService.createHistoricTaskInstanceQuery();
        }

        List<HistoricProcessInstance> historicFinishedProcessInstances = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).finished()
                .list();

        setProcessEngineConfiguration(processEngineConfiguration);

        // 生成图片的工具
        ProcessDiagramGenerator processDiagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();

        BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
        // 高亮流程已发生流转的线id集合
        List<String> highLightedFlowIds = getHighLightedFlows(bpmnModel, historicActivityInstances);

        // 使用默认配置获得流程图表生成器，并生成追踪图片字符流
        InputStream imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitiIds, highLightedFlowIds);

        return imageStream;
    }

    /**
     获取高亮流程
     @param bpmnModel
     @param historicActivityInstances
     @return
     */
    private static List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // 高亮流程已发生流转的线id集合
        List<String> highLightedFlowIds = new ArrayList<>();
        // 全部活动节点
        List<FlowNode> historicActivityNodes = new ArrayList<>();
        // 已完成的历史活动节点
        List<HistoricActivityInstance> finishedActivityInstances = new ArrayList<>();

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId());
            historicActivityNodes.add(flowNode);
            if (historicActivityInstance.getEndTime() != null) {
                finishedActivityInstances.add(historicActivityInstance);
            }
        }

        FlowNode currentFlowNode = null;
        FlowNode targetFlowNode = null;
        // 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
        for (HistoricActivityInstance currentActivityInstance : finishedActivityInstances) {
            // 获得当前活动对应的节点信息及outgoingFlows信息
            currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId());
            List<SequenceFlow> sequenceFlows = currentFlowNode.getOutgoingFlows();

            /**
             * 遍历outgoingFlows并找到已已流转的 满足如下条件认为已已流转：
             * 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转
             * 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
             */
            if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(currentActivityInstance.getActivityType())) {
                // 遍历历史活动节点，找到匹配流程目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef());
                    if (historicActivityNodes.contains(targetFlowNode)) {
                        highLightedFlowIds.add(targetFlowNode.getId());
                    }
                }
            } else {
                List<Map<String, Object>> tempMapList = new ArrayList<>();
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                        if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
                            Map<String, Object> map = new HashMap<>(16);
                            map.put("highLightedFlowId", sequenceFlow.getId());
                            map.put("highLightedFlowStartTime", historicActivityInstance.getStartTime().getTime());
                            tempMapList.add(map);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(tempMapList)) {
                    // 遍历匹配的集合，取得开始时间最早的一个
                    long earliestStamp = 0L;
                    String highLightedFlowId = null;
                    for (Map<String, Object> map : tempMapList) {
                        long highLightedFlowStartTime = Long.valueOf(map.get("highLightedFlowStartTime").toString());
                        if (earliestStamp == 0 || earliestStamp >= highLightedFlowStartTime) {
                            highLightedFlowId = map.get("highLightedFlowId").toString();
                            earliestStamp = highLightedFlowStartTime;
                        }
                    }
                    highLightedFlowIds.add(highLightedFlowId);
                }
            }
        }
        return highLightedFlowIds;
    }

    @Override
    public boolean passProcess(String taskId, Map<String, Object> variables, ActivitiEndCallBack activitiEndCallBack) {
        variables.put("isPass", true);
        Authentication.setAuthenticatedUserId((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        taskService.addComment(taskId, null, (String) variables.get("comment"));
        try {
            commitProcess(taskId, variables, null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (isEndByTaskId(taskId)) {
            // 执行回调函数
            activitiEndCallBack.callBack();
        }
        return true;
    }

    @Override
    public boolean backProcess(String taskId, String activityId, Map<String, Object> variables, ActivitiEndCallBack activitiEndCallBack) {
        variables.put("isPass", false);
        taskService.addComment(taskId, null, (String) variables.get("comment"));
        try {
            ActivityImpl endActivity = findActivitiImpl(taskId, "end");
            commitProcess(taskId, null, endActivity.getId());
        } catch (Exception e) {
            return false;
        }
        if (isEndByTaskId(taskId)) {
            // 执行回调函数
            activitiEndCallBack.callBack();
        }

        return true;
    }

    /**
     提交
     @param taskId
     任务ID
     @param variables
     流程参数
     @param activityId
     目标节点
     @exception Exception
     */
    private void commitProcess(String taskId, Map<String, Object> variables,
                               String activityId) throws Exception {
        if (variables == null) {
            variables = new HashMap<String, Object>(16);
        }
        // 跳转节点为空，默认提交操作
        if (StringUtils.isEmpty(activityId)) {
            taskService.complete(taskId, variables);
        } else {// 流程转向操作
            turnTransition(taskId, activityId, variables);
        }
//        taskService.complete(taskId, variables);
    }

    private void turnTransition(String taskId, String activityId,
                                Map<String, Object> variables) throws Exception {
        // 当前节点
        ActivityImpl currActivity = findActivitiImpl(taskId, null);
        // 清空当前流向
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

        // 创建新流向
        TransitionImpl newTransition = currActivity.createOutgoingTransition();
        // 目标节点
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
        // 设置新流向的目标节点
        newTransition.setDestination(pointActivity);

        // 执行转向任务
        taskService.complete(taskId, variables);
        // 删除目标节点新流入
        pointActivity.getIncomingTransitions().remove(newTransition);

        // 还原以前流向
        restoreTransition(currActivity, oriPvmTransitionList);
    }

    private ActivityImpl findActivitiImpl(String taskId, String activityId)
            throws Exception {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

        // 获取当前活动节点ID
        if (StringUtils.isEmpty(activityId)) {
            activityId = findTaskById(taskId).getTaskDefinitionKey();
        }

        // 根据流程定义，获取该流程实例的结束节点
        if (StringUtils.equals(activityId.toUpperCase(), END_TAG)) {
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {
                List<PvmTransition> pvmTransitionList = activityImpl
                        .getOutgoingTransitions();
                if (pvmTransitionList.isEmpty()) {
                    return activityImpl;
                }
            }
        }

        // 根据节点ID，获取对应的活动节点
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)
                .findActivity(activityId);

        return activityImpl;
    }

    private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
            String taskId) throws Exception {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(findTaskById(taskId)
                        .getProcessDefinitionId());

        if (processDefinition == null) {
            throw new Exception("流程定义未找到!");
        }

        return processDefinition;
    }

    private TaskEntity findTaskById(String taskId) throws Exception {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(
                taskId).singleResult();
        if (task == null) {
            throw new Exception("任务实例未找到!");
        }
        return task;
    }

    public boolean isEndByTaskId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (!org.springframework.util.ObjectUtils.isEmpty(task)) {
            return false;
        }
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        String processInstanceId = historicTaskInstance.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        return org.springframework.util.ObjectUtils.isEmpty(processInstance);

    }

    private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
        // 存储当前节点所有流向临时变量
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
        // 获取当前节点所有流向，存储到临时变量，然后清空
        List<PvmTransition> pvmTransitionList = activityImpl
                .getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
            oriPvmTransitionList.add(pvmTransition);
        }
        pvmTransitionList.clear();

        return oriPvmTransitionList;
    }

    private void restoreTransition(ActivityImpl activityImpl,
                                   List<PvmTransition> oriPvmTransitionList) {
        // 清空现有流向
        List<PvmTransition> pvmTransitionList = activityImpl
                .getOutgoingTransitions();
        pvmTransitionList.clear();
        // 还原以前流向
        for (PvmTransition pvmTransition : oriPvmTransitionList) {
            pvmTransitionList.add(pvmTransition);
        }
    }

    @Override
    public List<UserTask> comments(String id) {
        Task task = taskService.createTaskQuery().taskId(id).singleResult();
        List<UserTask> userTasks = new ArrayList<>();
        List<HistoricTaskInstance> tasks = new ArrayList<>();
        if (org.springframework.util.ObjectUtils.isEmpty(task)) {
            // 去历史记录中寻找
            HistoricTaskInstance task1 = historyService.createHistoricTaskInstanceQuery().taskId(id).singleResult();
            tasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(task1.getProcessInstanceId()).list();
        } else {
            String processInstanceId = task.getProcessInstanceId();
            tasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
        }

        for (HistoricTaskInstance t : tasks) {
            List<Comment> comments = taskService.getTaskComments(t.getId());
            UserTask userTask = new UserTask();
            userTask.setName(t.getName());

            userTask.setComment(ActivitiObj2SystemObjUtils.getInstance().commentsToObj(comments));
            HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(t.getId()).singleResult();
            Map<String, Object> map = historicTaskInstance.getProcessVariables();
            HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery().executionId(t.getExecutionId()).variableName("pass").singleResult();
            if (ObjectUtils.allNotNull(hvi)) {
                userTask.setPass((Boolean) hvi.getValue());
            }
            userTasks.add(userTask);
        }
        return userTasks;
    }

    public void setProcessEngineConfiguration(ProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setXmlEncoding("utf-8");
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
    }

    /**
     通过业务KEY 获取流程实例
     @param businessKey
     @return
     */
    @Override
    public ProcessInstance processInstance(String businessKey) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
        return processInstance;
    }

    @Override
    public List<UserTask> toDo(OAuth2Authentication authentication) {
        List<String> authrities = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        authentication.getAuthorities().forEach(grantedAuthority -> {
            tasks.addAll(taskService.createTaskQuery().active().taskAssignee(grantedAuthority.getAuthority()).list());
        });
        List<UserTask> userTasks = ActivitiObj2SystemObjUtils.getInstance().tasks2UserTasks(tasks);
        return userTasks;
    }
}
