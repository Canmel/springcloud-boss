package com.camel.realname.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.camel.realname.model.ApproveOrder;

public interface AliPayService {
    /**
     * 发送预交易请求
     * @param order 订单信息
     * @return 响应信息
     */
    AlipayTradePrecreateResponse PrecreateRequest(ApproveOrder order) throws AlipayApiException;

}
