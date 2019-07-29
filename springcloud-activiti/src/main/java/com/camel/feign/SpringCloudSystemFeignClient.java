package com.camel.feign;

import com.camel.core.entity.Result;
import com.camel.feign.fallback.SpringCloudBpmApprovalFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "springcloud-system", fallback = SpringCloudBpmApprovalFallback.class)
public interface SpringCloudSystemFeignClient {

    /**
     * 获取所有角色
     * @return
     */
    @RequestMapping(value = "/sysRole/all/list", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Result allRole();

    @RequestMapping(value = "/sysUser/role/{id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Result usersByRole(@PathVariable("id") String id);
}
