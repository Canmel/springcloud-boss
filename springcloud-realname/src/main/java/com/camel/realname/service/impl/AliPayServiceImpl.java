package com.camel.realname.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.camel.realname.config.AlipayConfig;
import com.camel.realname.model.ApproveOrder;
import com.camel.realname.service.AliPayService;
import com.camel.realname.utils.SnowflakeIdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AliPayServiceImpl implements AliPayService {
    @Resource
    private AlipayConfig alipayConfig;

    @Override
    public AlipayTradePrecreateResponse PrecreateRequest(ApproveOrder order) throws AlipayApiException {
        //  我们向支付宝发起支付请求，返回得到qrcode
        AlipayClient alipayClient =
                new DefaultAlipayClient(
                        alipayConfig.getGetwayUrl(),
                        alipayConfig.getAppId(),
                        alipayConfig.getMerchatPrivateKey(),
                        "json",
                        alipayConfig.getCharset(),
                        alipayConfig.getAlipayPublicKey(),
                        alipayConfig.getSignType()
                );
        //  发起交易预创建请求
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(alipayConfig.getNotityUrl());
//        request.setReturnUrl(alipayConfig.getReturnUrl());
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setBody(order.getBody());
        model.setSubject(order.getSubject());
        model.setTotalAmount(order.getPrice().toString());
        model.setOutTradeNo(order.getId());
        request.setBizModel(model);
//        request.setBizContent("{" +
//                "\"out_trade_no\":\""+order.getId()+"\"," +
//                "\"seller_id\":\""+alipayConfig.getAppId()+"\"," +
//                "\"total_amount\":"+order.getPrice() +
//                "\"subject\":\""+order.getSubject()+"\"," +
//                "\"body\":\""+order.getBody()+"\"," +
//                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
//                "\"timeout_express\":\"90m\"," +
//                "}");
        return alipayClient.execute(request);
    }
}
