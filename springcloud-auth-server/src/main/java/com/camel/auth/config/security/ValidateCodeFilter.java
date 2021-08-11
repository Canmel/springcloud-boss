package com.camel.auth.config.security;

import com.camel.auth.config.oauth.PicKaptchaUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 验证码过滤
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Session 对象
     */
   @Autowired
   private RedisTemplate<String, String> redisTemplate;

    /**
     * 创建一个Set 集合 存放 需要验证码的 urls
     */
    private Set<String> urls = new HashSet<>();

    /**
     * security applicaiton  配置属性
     */
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private PicKaptchaUtil picKaptchaUtil;
    /**
     * spring的一个工具类：用来判断 两字符串 是否匹配
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 这个方法是 InitializingBean 接口下的一个方法， 在初始化配置完成后 运行此方法
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        //因为登录请求一定要有验证码 ，所以直接 add 到set 集合中
        urls.add("/oauth/token");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean action = false;
        for (String url:urls){
            //如果请求的url 和 配置中的url 相匹配
            if (pathMatcher.match(url,request.getRequestURI())){
                action = true;
            }
        }

        //拦截请求
        if (action){
            logger.info("拦截成功"+request.getRequestURI());
            //如果是登录请求
            validate(new ServletWebRequest(request));
            filterChain.doFilter(request,response);
        }else {
            //不做任何处理，调用后面的 过滤器
            filterChain.doFilter(request,response);
        }
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        //从request 请求中 取出 验证码
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),"imageCode");
        String key = ServletRequestUtils.getStringParameter(request.getRequest(),"key");
        String keyValue = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(codeInRequest)){
            logger.info("验证码不能为空");
            throw new MyAuthenticationException("验证码不能为空");
        }
        if (keyValue == null){
            logger.info("验证码不存在");
            throw new MyAuthenticationException("验证码不存在");
        }
        if (!StringUtils.equals(keyValue,codeInRequest)){
            logger.info("验证码不匹配"+"codeInSession:"+keyValue +", codeInRequest:"+codeInRequest);
            throw new MyAuthenticationException("验证码不匹配");
        }
        redisTemplate.delete(key);
    }
}
