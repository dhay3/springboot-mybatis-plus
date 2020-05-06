package com.chz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author chz
 * @since 2020-05-03
 */
@Data
@TableName("t_perm")
public class Perm implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "p_id", type = IdType.AUTO)
    private Integer pId;
    private String perm;

}
