package com.chz.springsecuritysession.code;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * 验证码
 */
@Data
public class ImageCode {
    private static final long serialVersionUID = -643779290513077666L;

    //允许出现的序列值
    private char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    //有效时间
    private LocalDateTime ttl;
    //验证码内容
    private String code;
    //验证码长度
    private Integer codeCount = 4;
    private BufferedImage bufferedImage;

    public ImageCode(LocalDateTime ttl, String code, BufferedImage bufferedImage) {
        this.ttl = ttl;
        this.code = code;
        this.bufferedImage = bufferedImage;
    }

    public ImageCode(Integer ttl, BufferedImage bufferedImage) {
        this.ttl = LocalDateTime.now().plusSeconds(ttl);
        this.bufferedImage = bufferedImage;
    }

    public ImageCode(Integer ttl, String code, BufferedImage bufferedImage) {
        this.ttl = LocalDateTime.now().plusSeconds(ttl);
        this.code = code;
        this.bufferedImage = bufferedImage;
    }

    /**
     * 判断验证码是否过期
     */
    public boolean isExpire() {
        return LocalDateTime.now().isAfter(ttl);
    }
}
