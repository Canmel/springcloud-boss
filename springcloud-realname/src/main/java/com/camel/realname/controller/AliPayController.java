package com.camel.realname.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.realname.annotation.AuthIgnore;
import com.camel.realname.config.AlipayConfig;
import com.camel.realname.model.ApproveOrder;
import com.camel.realname.service.AliPayService;
import com.camel.realname.service.ApproveOrderService;
import com.camel.realname.utils.QRCodeUtils;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/alipay")
public class AliPayController {
    @Resource
    private AliPayService aliPayService;

    @Resource
    private AlipayConfig alipayConfig;

    @Resource
    private ApproveOrderService approveOrderService;

    /**
     * 创建订单返回二维码base64
     * @param order 订单信息
     * @return Result
     * @throws IOException
     * @throws AlipayApiException
     * @throws WriterException
     */
    @PostMapping("/order")
    @ResponseBody
    public Result open(@RequestBody @Validated(Insert.class) ApproveOrder order) throws IOException, AlipayApiException, WriterException {
        System.out.println(order);
        AlipayTradePrecreateResponse response = aliPayService.PrecreateRequest(order);
        if(response.isSuccess()){
            String qrCode = response.getQrCode();// 二维码code
            String outTradeNo = response.getOutTradeNo();// 商户的订单号
            String qrCodeBase64 = QRCodeUtils.getBase64Qrcode(qrCode,"png");
            HashMap<String, Object> map = new HashMap<>();
            map.put("qrCodeBase64",qrCodeBase64);
            map.put("outTradeNo",outTradeNo);
            return ResultUtil.success("订单创建成功",map);
        } else {
            return ResultUtil.error(ResultEnum.SERVICE_ERROR.getCode(),"订单创建失败");
        }

//        String qrCodeBase64 = QRCodeUtils.getBase64Qrcode("https://www.baidu.com","png");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("qrCodeBase64",qrCodeBase64);
//        return ResultUtil.success(map);
    }

    /**
     * 根据qrcode在浏览器中返回二维码
     * @param qrCode qrcode
     * @param response HttpServletResponse
     * @throws IOException
     */
    @GetMapping("/qrCode")
    public void getQRCode(@RequestParam("qrCode") String qrCode,
                          HttpServletResponse response) throws IOException {
        // 设置响应流信息
        response.setContentType("image/jpg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        OutputStream stream = response.getOutputStream();
        String content = "https://www.baidu.com";
        BitMatrix bitMatrix = QRCodeUtils.createCode(content);
        //以流的形式输出到前端
        MatrixToImageWriter.writeToStream(bitMatrix,"jpg",stream);
    }

    /**
     * 支付宝付款异步通知
     * @param request
     * @param response
     * @throws Exception
     */
    @AuthIgnore
    @PostMapping("/pay")
    public void alipay_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("commin...");
        String  message = "success";
        Map<String, String> params = new HashMap<String, String>();
        // 取出所有参数是为了验证签名
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            params.put(parameterName, request.getParameter(parameterName));
        }
        System.out.println(params);
//        //验证签名
//        boolean signVerified = false;
//        try {
//            signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), "UTF-8");
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//            message =  "failed";
//        }
//        if(signVerified){
//            //  验证签名成功！
//            // 若参数中的appid和填入的appid不相同，则为异常通知
//            if (!alipayConfig.getAppId().equals(params.get("app_id"))) {
//                // 与付款时的appid不同，此为异常通知，应忽略!
//                message =  "failed";
//            }else{
//                String outtradeno = params.get("out_trade_no");
//                //  号订单回调通知
//                //在数据库中查找订单号对应的订单，并将其金额与数据库中的金额对比，若对不上，也为异常通知
//                String status = params.get("trade_status");
//                if (status.equals("WAIT_BUYER_PAY")) { // 如果状态是正在等待用户付款
//                } else if (status.equals("TRADE_CLOSED")) { // 如果状态是未付款交易超时关闭，或支付完成后全额退款
//                } else if (status.equals("TRADE_SUCCESS") || status.equals("TRADE_FINISHED")) { // 如果状态是已经支付成功
//                    //成功 更新状态
//                    Integer res = approveOrderService.pay(Long.getLong(outtradeno));
//                } else {
//                    message = "failed";
////                    weixinpayBack.updateAccOrder(outtradeno);
//                }
//            }
//        }else{// 如果验证签名没有通过
//            message =  "failed";
//        }
//        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
//        out.write(message.getBytes());
//        out.flush();
//        out.close();
    }
}
