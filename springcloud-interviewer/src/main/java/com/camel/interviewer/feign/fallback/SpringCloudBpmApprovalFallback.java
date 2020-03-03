package com.camel.interviewer.feign.fallback;

import com.camel.core.entity.Result;
import com.camel.core.model.SysUser;
import com.camel.interviewer.feign.SpringCloudSystemFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 @author baily */
@Slf4j
@Component
public class SpringCloudBpmApprovalFallback implements FallbackFactory<SpringCloudSystemFeignClient> {
    @Override
    public SpringCloudSystemFeignClient create(Throwable throwable) {
        String msg = throwable == null ? "" : throwable.getMessage();
        if (!StringUtils.isEmpty(msg)) {
            System.out.println(msg);
        }
        return new SpringCloudSystemFeignClient() {
            @Override
            public Result interviewerSave(SysUser sysUser) {
                return null;
            }
        };
    }
}
