package com.chz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author chz
 * @since 2020-05-03
 */
@Data
@TableName("t_role")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "r_id", type = IdType.AUTO)
    private Integer rId;
    private String role;
    @TableField(exist = false)
    private List<Perm> perms;
}
