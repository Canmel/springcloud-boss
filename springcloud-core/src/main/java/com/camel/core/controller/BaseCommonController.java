package com.camel.core.controller;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.toolkit.MapUtils;
import com.camel.core.BaseProcessService;
import com.camel.core.config.ProcessProperties;
import com.camel.core.entity.BaseEntity;
import com.camel.core.entity.BaseProcessPaginationEntity;
import com.camel.core.entity.Result;
import com.camel.core.entity.process.ActivitiForm;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
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
 * <控制器基类>
 * @author baily
 * @since 1.0
 * @date 2019/7/11
 **/
public abstract class BaseCommonController {

    public static final String USER_ID_PROP_NAME = "id";

    /**
     * 获取服务
     * @return
     */
    abstract public IService getiService();

    /**
     * 获取模块名称
     * @return
     */
    abstract public String getMouduleName();

    /**
     * 获取详情
     * @param serializable
     * @return
     */
    public Result details(Serializable serializable) {
        Object obj = getiService().selectById(serializable);
        if (ObjectUtils.isEmpty(obj)) {
            return ResultUtil.notFound();
        }
        return ResultUtil.success(obj);
    }

    /**
     * 保存
     * @param entity
     * @return
     */
    public Result save(BaseEntity entity) {
        boolean insert = getiService().insert(entity);
        if (insert) {
            return ResultUtil.success("新增" + getMouduleName() + "成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),"新增" + getMouduleName() + "失败");
    }

    /**
     * 更新
     * @param entity
     * @return
     */
    public Result update(BaseEntity entity) {
        if (getiService().updateById(entity)) {
            return ResultUtil.success("修改" + getMouduleName() + "成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),"修改" + getMouduleName() + "失败");
    }

    /**
     * 删除
     * @param serializable
     * @return
     */
    public Result delete(Serializable serializable) {
        if (getiService().deleteById(serializable)) {
            return ResultUtil.success("删除" + getMouduleName() + "成功");
        }
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),"删除" + getMouduleName() + "失败");

    }

    /**
     * 流程相关
     *  当前流程位置
     * @param id 业务主键
     * @return
     */
    public Result currented(Integer id){
        return getProcessService().current(id, getBusinessKey());
    }

    /**
     * 流程相关
     * 发起流程
     * @param id        业务主键
     * @param flowId    关联的流程ID
     * @return
     */
    public Result applyed(Integer id, String flowId) {
        Result result = getProcessService().apply(id, flowId, getBusinessKey()) ? ResultUtil.success("发起流程成功") : ResultUtil.error(HttpStatus.BAD_REQUEST.value(), "发起流程失败");
        return result;
    }

    /**
     * 流程相关
     * 审核-通过
     * @param id            业务ID
     * @param activitiForm  审核表单
     * @return
     */
    public Result passed(Integer id, ActivitiForm activitiForm){
        Result result = getProcessService().current(id, getBusinessKey());
        
        if(!ObjectUtils.isEmpty(result.getData())){
            List<Map<String, Object>> list = (List) result.getData();
            Map<String, Object> userTask = list.get(0);
            if(StringUtils.isEmpty(userTask.get(USER_ID_PROP_NAME))){
                return ResultUtil.success("审批失败");
            }
            Result r = getProcessService().pass(userTask.get(USER_ID_PROP_NAME).toString(), activitiForm, getBusinessKey());
            HashMap<String, Object> rMapData = (HashMap<String, Object>) r.getData();
            Object o = rMapData.get(ProcessProperties.PROCESS_ISEND_KEY);
            if(!ObjectUtils.isEmpty(o)){
                Boolean isEnd = (Boolean) o;
                if(isEnd) {
                    return new Result(HttpStatus.PROCESSING.value(), "审批成功", ((List) result.getData()).get(0), true);
                }
            }
            return ResultUtil.success("审批成功", ((List) result.getData()).get(0));
        }
        return ResultUtil.success("审批失败");
    }

    /**
     * 流程相关
     *  获取审核的评论信息
     * @param id    业务主键
     * @return
     */
    public Result commen(String id) {
        return getProcessService().comment(id);
    }

    /**
     * 流程相关
     * 驳回
     * @param taskId        任务ID
     * @param activitiForm  驳回表单
     * @return
     */
    public Result backed(String taskId, ActivitiForm activitiForm) {
        Result result = getProcessService().back(taskId, activitiForm, getBusinessKey());
        return result;
    }

    /**
     * 流程相关
     * 查询 相关未处理事物
      * @return
     */
    public Result todoies(){
        return getProcessService().toDO();
    }

    /**
     * 获取业务KEY
     *  各个子类需要重写
     * @return
     */
    public String getBusinessKey() {
        return "当使用到业务KEY的时候你必须重写这个方法！！";
    }

    /**
     * 流程相关
     *  各个流程子类必须重写这个方法
     * @return
     */
    public BaseProcessService getProcessService(){
        return null;
    }
}
