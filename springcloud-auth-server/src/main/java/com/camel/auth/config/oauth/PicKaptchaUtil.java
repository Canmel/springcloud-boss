package com.camel.auth.config.oauth;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PicKaptchaUtil {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 生成验证码.
     * @param request request
     * @param response response
     * @return CaptchaDTO
     */
    public CaptchaDTO kaptcha(HttpServletRequest request, HttpServletResponse response) {
        // 获取sessionId
        CaptchaDTO captchaDTO = new CaptchaDTO();
        String key = "JQ" + request.getSession().getId();
        // 生产验证码字符串
        String createText = this.defaultKaptcha.createText();
        // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
        BufferedImage bufferedImage = this.defaultKaptcha.createImage(createText);
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 使用生产的验证码字符串返回一个BufferedImage
            ImageIO.write(bufferedImage, "jpeg", jpegOutputStream);
            Base64.Encoder encoder = Base64.getEncoder();
            String base64 = encoder.encodeToString(jpegOutputStream.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64,"
                    + base64.replaceAll("\r\n", "");

            captchaDTO.setCaptchaBase64(captchaBase64);
            captchaDTO.setKey(key);

            // 存储到redis，过期时间30分钟
            this.redisTemplate.opsForValue().set(key, createText);
            this.redisTemplate.expire(key, 15 * 60, TimeUnit.SECONDS);
        }
        catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        finally {
            try {
                if (jpegOutputStream != null) {
                    jpegOutputStream.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return captchaDTO;
    }

    /**
     * 校验验证码.
     * @param key kaptcha-key
     * @param kaptcha kaptcha
     * @return 校验结果
     */
    public boolean check(String key, String kaptcha) {
        String redisKaptcha = this.redisTemplate.opsForValue().get(key);
        return (!StringUtils.isEmpty(kaptcha)) && kaptcha.equals(redisKaptcha);
    }
}
