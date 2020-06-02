package com.camel.control.feign.fallback;

import com.camel.control.feign.SpringCloudSurveyFeignClient;
import com.camel.core.entity.Result;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 @author baily */
@Slf4j
@Component
public class SpringCloudBpmApprovalFallback implements FallbackFactory<SpringCloudSurveyFeignClient> {
    @Override
    public SpringCloudSurveyFeignClient create(Throwable throwable) {
        String msg = throwable == null ? "" : throwable.getMessage();
        if (!StringUtils.isEmpty(msg)) {
            System.out.println(msg);
        }
        return new SpringCloudSurveyFeignClient() {
            @Override
            public Result selectWorkR(String idNum) {
                return null;
            }

        };
    }
}
