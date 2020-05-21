package com.chz.springsecuritysms.code;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class SMSCode {
    //验证码内容
    private String code;
    //有效时间
    private LocalDateTime ttl;

    public SMSCode(String code, Integer ttl) {
        this.code = code;
        this.ttl=LocalDateTime.now().plusSeconds(ttl);
    }

    public SMSCode(String code, LocalDateTime ttl) {
        this.code = code;
        this.ttl = ttl;
    }

    /**
     * 验证码是否过期
     */
    public boolean isExpire(){
        return LocalDateTime.now().isAfter(ttl);
    }
}
