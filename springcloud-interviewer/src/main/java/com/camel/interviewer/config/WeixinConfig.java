package com.camel.interviewer.config;

import com.camel.interviewer.controller.WeixinApiController;
import com.camel.interviewer.controller.WeixinMsgController;
import com.camel.interviewer.controller.WeixinPayController;
import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.jfinal.template.Engine;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.wxaapp.WxaConfig;
import com.jfinal.wxaapp.WxaConfigKit;
import org.springframework.stereotype.Component;

//@Component
public class WeixinConfig extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
        PropKit.use("a_little_config.txt");
        me.setDevMode(PropKit.getBoolean("devMode", false));

        // ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
        ApiConfigKit.setDevMode(me.getDevMode());
        // 默认使用的jackson，下面示例是切换到fastJson
//		me.setJsonFactory(new FastJsonFactory());
    }

    public void configRoute(Routes me) {
        // jfinal 3.6 开始，如果有继承 MsgController 的类，则需要开启下面的配置，将超类中的 index() 映射为 action
        me.setMappingSuperClass(true);

        me.add("/msg", WeixinMsgController.class);
        me.add("/api", WeixinApiController.class, "/api");
        me.add("/pay", WeixinPayController.class);
    }

    @Override
    public void configPlugin(Plugins me) {
        // 1.5 之后支持redis存储access_token、js_ticket，需要先启动RedisPlugin
        // RedisPlugin redisPlugin = new RedisPlugin("weixin", "127.0.0.1");
        // me.add(redisPlugin);
    }

    public void afterJFinalStart() {
        // 1.5 之后支持redis存储access_token、js_ticket，需要先启动RedisPlugin
//        ApiConfigKit.setAccessTokenCache(new RedisAccessTokenCache());
        // 1.6新增的2种初始化
//        ApiConfigKit.setAccessTokenCache(new RedisAccessTokenCache(Redis.use("weixin")));
//        ApiConfigKit.setAccessTokenCache(new RedisAccessTokenCache("weixin"));

        ApiConfig ac = new ApiConfig();
        // 配置微信 API 相关参数
        ac.setToken(PropKit.get("token"));
        ac.setAppId(PropKit.get("appId"));
        ac.setAppSecret(PropKit.get("appSecret"));

        /**
         *  是否对消息进行加密，对应于微信平台的消息加解密方式：
         *  1：true进行加密且必须配置 encodingAesKey
         *  2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
        ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));

        /**
         * 多个公众号时，重复调用ApiConfigKit.putApiConfig(ac)依次添加即可，第一个添加的是默认。
         */
        ApiConfigKit.putApiConfig(ac);

        /**
         * 1.9 新增LocalTestTokenCache用于本地和线上同时使用一套appId时避免本地将线上AccessToken冲掉
         *
         * 设计初衷：https://www.oschina.net/question/2702126_2237352
         *
         * 注意：
         * 1. 上线时应保证此处isLocalDev为false，或者注释掉该不分代码！
         *
         * 2. 为了安全起见，此处可以自己添加密钥之类的参数，例如：
         * http://localhost/weixin/api/getToken?secret=xxxx
         * 然后在WeixinApiController#getToken()方法中判断secret
         *
         * @see WeixinApiController#getToken()
         */
//        if (isLocalDev) {
//            String onLineTokenUrl = "http://localhost/weixin/api/getToken";
//            ApiConfigKit.setAccessTokenCache(new LocalTestTokenCache(onLineTokenUrl));
//        }
        WxaConfig wc = new WxaConfig();
        wc.setAppId("wx40d4fe2c2c03a2f3");
        wc.setAppSecret("c5f6da5b695d372321c9a46b792ec8f0");
        WxaConfigKit.setWxaConfig(wc);
    }

    @Override
    public void configInterceptor(Interceptors me) {}
    @Override
    public void configHandler(Handlers me) {}
    @Override
    public void configEngine(Engine engine) {}
}
