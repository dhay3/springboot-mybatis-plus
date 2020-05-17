package com.chz.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 对应数据库中的数据
 */
@Data
public class UserDO implements Serializable {
    private static final Long serialVersionUID = 1L;
    private String userName;
    private String password;
    //模拟
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialNonExpired = true;
    private Boolean enable = true;
    private List<String> roles = new ArrayList<>();
}
