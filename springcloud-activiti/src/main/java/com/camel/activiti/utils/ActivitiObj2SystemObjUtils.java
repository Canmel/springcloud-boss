package com.camel.activiti.utils;

import com.camel.core.entity.process.Comment;
import com.camel.core.entity.process.UserTask;
import org.activiti.engine.task.Task;

import java.util.ArrayList;
import java.util.List;

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
 * <ACTIVITI对象转系统对象>
 * @author baily
 * @since 1.0
 * @date 2019/7/7
 **/
public class ActivitiObj2SystemObjUtils {
    private final static ActivitiObj2SystemObjUtils INSTANCE = new ActivitiObj2SystemObjUtils();

    public ActivitiObj2SystemObjUtils() {
    }

    public static ActivitiObj2SystemObjUtils getInstance(){return INSTANCE;}

    public List<Comment> commentsToObj(List<org.activiti.engine.task.Comment> comments) {
        List<Comment> result = new ArrayList<>();
        comments.forEach(comment -> {
            Comment c1 = new Comment(comment.getUserId(), comment.getTime(), comment.getTaskId(), comment.getFullMessage());
            result.add(c1);
        });
        return result;
    }

    public List<UserTask> tasks2UserTasks(List<Task> tasks) {
        List<UserTask> userTaskList = new ArrayList<>();
        tasks.forEach(task -> {
            UserTask userTask = new UserTask();
            userTask.setId(task.getId());
            userTask.setProcessInstanceId(task.getProcessInstanceId());
            userTask.setUsername(task.getAssignee());
            userTask.setEnd(false);
            userTask.setName(task.getName());
            userTask.setProcessDefinitionId(task.getProcessDefinitionId());
            userTask.setCreatedTime(task.getCreateTime());
            userTaskList.add(userTask);
        });
        return userTaskList;
    }
}
