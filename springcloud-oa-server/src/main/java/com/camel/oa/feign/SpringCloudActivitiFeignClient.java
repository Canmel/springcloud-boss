package com.camel.oa.feign;

import com.camel.core.entity.Result;
import com.camel.oa.config.KeepErrMsgConfiguration;
import com.camel.oa.feign.fallback.SpringCloudBpmApprovalFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author baily
 */
@FeignClient(value = "springcloud-activiti", fallback = SpringCloudBpmApprovalFallback.class, configuration = {KeepErrMsgConfiguration.class})
public interface SpringCloudActivitiFeignClient {

    /**
     * 发起流程
     * @param busniessKey   业务      KEY
     * @param flowKey       流程关键字 KEY
     * @return
     */
    @RequestMapping(value = "/flow/apply", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Result apply(@RequestParam("busniessKey") String busniessKey, @RequestParam("flowKey") String flowKey);

    /**
     * 发起流程
     * @param busniessKey   业务      KEY
     * @param flowId       流程关键字 KEY
     * @return
     */
    @RequestMapping(value = "/process/applyById", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Result applyById(@RequestParam("busniessKey") String busniessKey, @RequestParam("flowId") String flowId);

    /**
     查询当前流程
     @param busniessKey     业务      KEY
     @param flowKey         流程关键字 KEY
     @return
     */
    @RequestMapping(value = "/flow/current", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Result current(@RequestParam("busniessKey") String busniessKey, @RequestParam("flowKey") String flowKey);

    /**
     审批通过当前流程
     @param comment
     @param businessId
     @param id
     @return
     */
    @RequestMapping(value = "/flow/pass", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Result pass(@RequestParam("id") String id, @RequestParam("comment") String comment, @RequestParam("businessId") String businessId);

    /**
     审批驳回当前流程
     @param comment
     @param businessId
     @param id
     @return
     */
    @RequestMapping(value = "/flow/back", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Result back(@RequestParam("id") String id, @RequestParam("comment") String comment, @RequestParam("businessId") String businessId);

    /**
     获取审批意见
     @param id
     @return
     */
    @RequestMapping(value = "/flow/comments", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Result comment(@RequestParam("id") String id);

    /**
     获取当前用户的任务列表
     @return
     */
    @RequestMapping(value = "/flow/todo", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Result toDo();
}
