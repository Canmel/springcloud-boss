package com.camel.control.feign;

import com.camel.control.config.KeepErrMsgConfiguration;
import com.camel.control.feign.fallback.SpringCloudBpmApprovalFallback;
import com.camel.core.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "springcloud-survey", fallback = SpringCloudBpmApprovalFallback.class, configuration = {KeepErrMsgConfiguration.class})
public interface SpringCloudSurveyFeignClient {

    /**
     * 获取该身份证号对应的所有班次信息
     * @return
     */
    @RequestMapping(value = "/zsWorkRecord/access/list/{idNum}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    Result selectWorkR(@PathVariable("idNum") String idNum);

}
