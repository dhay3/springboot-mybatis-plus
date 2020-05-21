package com.chz.springsecuritysms.controller;

import com.chz.springsecuritysms.code.ImageCode;
import com.chz.springsecuritysms.code.SMSCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Slf4j
@RestController
public class ValidateController {
    public final static String SESSION_KEY_IMAGE_CODE = "SESSION_KEY_IMAGE_CODE";
    public final static String SESSION_KEY_SMS_CODE = "SESSION_KEY_SMS_CODE";
    //无法显示导包
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    @GetMapping("/code/sms")
    public void createSMSCode(HttpServletRequest request, HttpServletResponse response,String mobile) {
        SMSCode smsCode = getRandomCode();
        //将sms code 设置到session域中
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_SMS_CODE + mobile, smsCode);
        //验证码到控制台替代短信
        System.out.println("您的登录验证码为：" + smsCode.getCode() + "，有效时间为60秒");
    }

    /**
     * 生成图形验证码请求
     */
    @GetMapping("/code/image")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = createImageCode(100, 36, 60);
        //存储一个session key 为SESSION_KEY_IMAGE_CODE, 值为imageCode的到session域中
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_IMAGE_CODE, imageCode);
        log.warn("验证码" + imageCode.getCode());
        //将图片写入到前端
        ImageIO.write(imageCode.getBufferedImage(), "png", response.getOutputStream());
    }

    /**
     * @param width  验证码图片宽度
     * @param height 验证码图片长度
     * @param ttl    验证码有效时间 60s
     * @return
     */
    private ImageCode createImageCode(int width, int height, int ttl) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        //设置颜色
        g.setColor(getRandomColor(200, 259));
        //设置形状
        g.fillRect(0, 0, width, height);
        //设置字体颜色
        g.setColor(getRandomColor(160, 200));
        //设置字体
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        //干扰线
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        StringBuilder codeStr = new StringBuilder();
        ImageCode imageCode = new ImageCode(ttl, image);
        char[] codeSequence = imageCode.getCodeSequence();
        //验证码内容
        for (int i = 0; i < imageCode.getCodeCount(); i++) {
            //随机获取校验内容
            String rand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            //为每个字符设置不同的颜色
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 17 * i + 6, 25);
            codeStr.append(rand);
        }
        //将验证码的内容封装到对象中以便做对比
        imageCode.setCode(codeStr.toString());
        g.dispose();
        return imageCode;
    }

    /**
     * 获取随机颜色
     */
    private Color getRandomColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 获取随机短信验证码
     */
    private SMSCode getRandomCode() {
        //randomNumeric只会从数字中找, random()从所有字符中找
        String code = RandomStringUtils.randomNumeric(6);
        return new SMSCode(code, 60);
    }
}
