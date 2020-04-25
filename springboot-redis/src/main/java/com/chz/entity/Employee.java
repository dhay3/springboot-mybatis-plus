package com.chz.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author chz
 * @since 2020-04-24
 */
@Data
@TableName("tbl_employee")
public class Employee extends Model<Employee> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String lastName;

    private String email;

    private String gender;

    private Integer age;
    @Version
    private Integer version;
    @TableLogic
    private Integer deleted;
    @JsonFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:MM:ss")
    private LocalDateTime date;

}
