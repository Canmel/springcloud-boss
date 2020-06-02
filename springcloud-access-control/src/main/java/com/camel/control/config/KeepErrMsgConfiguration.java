package com.camel.control.config;

import com.alibaba.fastjson.JSONObject;
import com.camel.core.entity.Result;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

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
 * <feign错误信息转换>
 * @author baily
 * @since 1.0
 * @date 2019/7/7
 **/
public class KeepErrMsgConfiguration {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }
    /**
     * 自定义错误
     */
    public class UserErrorDecoder implements ErrorDecoder {
        private Logger logger = LoggerFactory.getLogger(getClass());
        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;
            try {
                // 获取原始的返回内容
                String json = Util.toString(response.body().asReader());
                exception = new RuntimeException(json);

                // 将返回内容反序列化为Result，这里应根据自身项目作修改
                JSONObject object = JSONObject.parseObject(json);
                Result result = (Result) object.toJavaObject(Result.class);
//                // 业务异常抛出简单的 RuntimeException，保留原来错误信息
                if (!result.isSuccess()) {
                    exception = new RuntimeException(result.getMsg());
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            }
            return exception;
        }
    }
}
