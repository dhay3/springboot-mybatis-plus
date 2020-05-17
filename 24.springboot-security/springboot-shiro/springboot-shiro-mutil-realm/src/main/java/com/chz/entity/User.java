package com.chz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 * 
 * </p>
 *
 * @author chz
 * @since 2020-05-03
 */
@Data
@TableName("t_user")
public class User implements Serializable {
    private static final long serialVersionUID=1L;
    @TableId(value = "u_id", type = IdType.AUTO)
    private Integer uId;
    private String name;
    private String password;
    @TableField(exist = false)
    private Set<Role> roles;


}
