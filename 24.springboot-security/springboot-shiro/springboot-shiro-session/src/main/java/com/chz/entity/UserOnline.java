package com.chz.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class UserOnline implements Serializable {

    private static final long serialVersionUID = -1560050835397048089L;
    //session id
    private String id;
    private String userId;
    private String username;
    //用户主机地址
    private String host;
    //用户登入时系统ip
    private String systemHost;
    //状态
    private String status;
    //登入时间
    private Date startTimeStamp;
    //登出时间
    private Date endTimeStamp;
    //超时时间
    private Long timeout;
}

