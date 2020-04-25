package com.chz.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
@Component
@Accessors(chain = true, fluent = true)
public class SysLog implements Serializable {
    private static final long serialVersionUID = -1281873605525985787L;
    private Integer id;
    private String username;
    private String operation;
    private Integer time;
    private String method;
    private String params;
    private String ip;
    private Date createTime;
}

