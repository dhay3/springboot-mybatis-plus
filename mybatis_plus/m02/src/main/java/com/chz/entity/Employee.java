package com.chz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Mybatis-plus AR 继承Model
 * 表所对应的类就是mapper
 * 即使不使用接口还是要配置且要在spring配置文件中
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Employee extends Model<Employee> {

    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;
    private Integer age;
    @TableField(exist = false)
    private Double salary;

    public Employee(String lastName, String email, Integer gender, Integer age) {
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.age = age;
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
