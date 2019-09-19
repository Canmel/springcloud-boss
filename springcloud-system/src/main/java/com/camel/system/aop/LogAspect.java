package com.camel.system.aop;


import com.camel.redis.entity.RedisUser;
import com.camel.redis.utils.SerizlizeUtil;
import com.camel.system.annotation.Log;
import com.camel.system.model.SysLog;
import com.camel.system.service.MqService;
import com.camel.system.service.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**@author baily */
@Aspect
@Component
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MqService mqService;

    @Pointcut("@annotation(com.camel.system.annotation.Log)")
    public void operationLog() {

    }

    @Around(("execution(* com.camel..*.*(..)) && @annotation(log)"))
    public Object doAfterAdvice(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        LOGGER.info("==============================================用户操作日志-通知开始执行......==========================================");
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = joinPoint.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        String username = "";
        try {
            username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        } catch (Exception e) {
            throw new RedisConnectionFailureException("未发现可用的Redis服务器！请检查");
        }
        SysLog sysLog = new SysLog(null, username, log.option(), time, joinPoint.getArgs().toString(), joinPoint.getSignature().toShortString(), joinPoint.getArgs().toString(), log.moduleName());
        sysLogService.insert(sysLog);
        mqService.sendMsg(sysLog.toString());
        LOGGER.info("==============================================用户操作日志-通知结束执行......==========================================");
        return result;
    }
}
