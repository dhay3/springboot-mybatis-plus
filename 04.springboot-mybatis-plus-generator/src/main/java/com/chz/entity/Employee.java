package com.chz.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author chz
 * @since 2020-04-23
 */

@Data
@Accessors(chain = true)
@TableName("tbl_employee")
public class Employee extends Model<Employee> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String lastName;

    private String email;

    private String gender;

    private Integer age;
    //标明该字段是乐观锁的字段
    @Version
    private Integer version;
    //标明该字段是逻辑删除字段
    @TableLogic
    private Integer deleted;
    //自动插入字段
    @TableField(fill = FieldFill.INSERT)
    //将接收到的参数格式化输出
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    //只接受指定类型的格式的参数
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private Date date;


}
