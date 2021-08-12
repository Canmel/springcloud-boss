package com.camel.realname.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtils {
    /**
     * 获得二维码的Base64
     * @param content qrcode
     * @param type 生成的二维码格式 比如 png,jpg
     * @return 二维码的Base64
     * @throws IOException
     */
    public static String getBase64Qrcode(String content,String type) throws IOException {
        BitMatrix bitMatrix = createCode(content);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, type, outputStream);
        Base64.Encoder encoder = Base64.getEncoder();
        return "data:image/"+type+";base64,"+encoder.encodeToString(outputStream.toByteArray());
    }

    /**
     *  生成二维码
     * @param content 二维码的内容
     * @return BitMatrix对象
     * */
    public static BitMatrix createCode(String content) throws IOException {
        //二维码的宽高
        int width = 260;
        int height = 260;
        //其他参数，如字符集编码
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //容错级别为H
        hints.put(EncodeHintType.ERROR_CORRECTION , ErrorCorrectionLevel.H);
        //白边的宽度，可取0~4
        hints.put(EncodeHintType.MARGIN , 0);

        BitMatrix bitMatrix = null;
        try {
            //生成矩阵，因为我的业务场景传来的是编码之后的URL，所以先解码
            bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);
            //bitMatrix = deleteWhite(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitMatrix;
    }
}
