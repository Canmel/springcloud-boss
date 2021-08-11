package com.camel.realname.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.camel.core.entity.Result;
import com.camel.core.enums.ResultEnum;
import com.camel.core.utils.ResultUtil;
import com.camel.realname.config.AlipayConfig;
import com.camel.realname.model.ApproveOrder;
import com.camel.realname.service.AliPayService;
import com.camel.realname.utils.QRCodeUtils;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.ibatis.annotations.Insert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@RestController
@RequestMapping("/alipay")
public class AliPayController {
    @Resource
    private AliPayService aliPayService;

    /**
     * 创建订单返回二维码base64
     * @param order 订单信息
     * @return Result
     * @throws IOException
     * @throws AlipayApiException
     * @throws WriterException
     */
    @PostMapping("/order")
    public Result open(@RequestBody @Validated(Insert.class) ApproveOrder order) throws IOException, AlipayApiException, WriterException {
        System.out.println(order);
//        AlipayTradePrecreateResponse response = aliPayService.PrecreateRequest(order);
//        System.out.println(response.getBody());
//        if(response.isSuccess()){
//            String qrCode = response.getQrCode();// 二维码code
//            String outTradeNo = response.getOutTradeNo();// 商户的订单号
//            String qrCodeBase64 = QRCodeUtils.getBase64Qrcode(qrCode,"png");
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("qrCodeBase64",qrCodeBase64);
//            map.put("outTradeNo",outTradeNo);
//            return ResultUtil.success("订单创建成功",map);
//        } else {
//            return ResultUtil.error(ResultEnum.SERVICE_ERROR.getCode(),"订单创建失败");
//        }
        String qrCodeBase64 = QRCodeUtils.getBase64Qrcode("https://www.baidu.com","png");
        HashMap<String, Object> map = new HashMap<>();
        map.put("qrCodeBase64",qrCodeBase64);
        return ResultUtil.success(map);
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
}
