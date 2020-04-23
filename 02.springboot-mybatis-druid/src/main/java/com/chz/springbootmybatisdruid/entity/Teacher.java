package com.chz.springbootmybatisdruid.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Teacher {
    private Integer id;
    private String tName;
    private Integer age;
    private Integer gender;

    public Teacher(String tName, Integer age, Integer gender) {
        this.tName = tName;
        this.age = age;
        this.gender = gender;
    }
}
