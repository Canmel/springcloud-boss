package com.camel.activiti.controller;

import com.camel.activiti.service.ProcessService;
import com.camel.common.utils.IoUtils;
import com.camel.core.entity.Result;
import com.camel.core.entity.process.UserTask;
import com.camel.core.utils.ResultUtil;
import com.camel.exceptions.WorkFlowImageGenerateFaildException;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    @GetMapping("/pass")
    public Result pass() {
        System.out.println("pass");
        return ResultUtil.success("审核成功");
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
}
