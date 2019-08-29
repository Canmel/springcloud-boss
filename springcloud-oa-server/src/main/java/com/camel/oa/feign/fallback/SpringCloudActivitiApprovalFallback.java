package com.camel.oa.feign.fallback;

import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.oa.feign.SpringCloudActivitiFeignClient;
import com.camel.oa.feign.SpringCloudBpmFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 @author baily */
@Slf4j
@Component
public class SpringCloudActivitiApprovalFallback implements FallbackFactory<SpringCloudActivitiFeignClient> {
    @Override
    public SpringCloudActivitiFeignClient create(Throwable throwable) {
        String msg = throwable == null ? "" : throwable.getMessage();
        if (!StringUtils.isEmpty(msg)) {
            log.error(msg);
        }
        return new SpringCloudActivitiFeignClient() {
            @Override
            public Result apply(String busniessKey, String flowKey) {
                return null;
            }

            @Override
            public Result applyById(String busniessKey, String flowId) {
                return null;
            }

            @Override
            public Result current(String busniessKey, String flowKey) {
                return null;
            }

            @Override
            public Result pass(String id, String comment, String businessId) {
                return null;
            }

            @Override
            public Result back(String id, String comment, String businessId) {
                return null;
            }

            @Override
            public Result comment(String id) {
                return null;
            }

            @Override
            public Result toDo() {
                return null;
            }
        };
    }
}
