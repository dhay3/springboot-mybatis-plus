package com.chz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Accessors(chain = true,fluent = true)
public class Account {
    private String name;
    private Integer age;
}
