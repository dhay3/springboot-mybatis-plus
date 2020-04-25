package com.chz.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)//fluent会导致json数据无法正确输出
//@Validated自会对controller层生效,不会对mybatis入库数据校验
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    //    @NotBlank
    private String lastName;
    //    @Email
    private String email;
    private String gender;
    private Integer age;
    private Integer version;
    private Integer deleted;
    //输出到redis中同样会生效
//    @JsonFormat(pattern = "yyyy-mm-dd hh:MM:ss")
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:MM:ss")
    //LocalDateTime这一系列的比较特殊必须要指定序列化的方式,否则机会抛出
    // Cannot deserialize instance of `java.time.LocalDateTime` out of START_ARRAY
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime date;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", date=" + date +
                '}';
    }
}
