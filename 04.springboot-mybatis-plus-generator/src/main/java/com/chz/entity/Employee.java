package com.chz.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class Employee implements Serializable {

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
    //将对象序列化为json串
    //@JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    //如果不支持可能是druid或是mybatis不支持,更换一下jar
    //接收指定格式的值封装到属性中
    //@DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    //将对象序列化为json串
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    //将json串反序列化为对象, @RequestBody接收一个json串转为对象
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    /*
     localDateTime对应数据库timestamp
     localDate 对应数据库date
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime date;
}
