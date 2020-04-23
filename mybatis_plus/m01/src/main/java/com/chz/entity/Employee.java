package com.chz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 *
 * @author 82341
 * mybatis并不需要getter setter
 * 定义JavaBean中成员变量尽量使用包装类
 * 存在如果mysql中声明字段为not null 但是采用了基本类型的变量,存在将默认值导入到mysql中的可能
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
/**
 * 默认类名首字母小写就是表名
 * 如果表名于类名首字母小写不符合时,要指定表名
 */
//@TableName("tbl_employee")
public class Employee {
    /**
     * 要指定主键,如果字段名与成员变量名相同可以不指定value
     * 如果表中开启主键自增,成员变量要开启主键自增策略
     * 全局配置主键策略可以不用添加
     */
//    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 默认开启骆驼峰命名,下划线转成骆驼峰  mybatis不默认开启
     * 如需指定,使用 @TableField("last_name")
     */
    private String lastName;
    private String email;
    private Integer gender;
    private Integer age;
    /**
     * exist=false表中不包含该字段
     */
    @TableField(exist = false)
    private Double salary;

    public Employee(String lastName, String email, Integer gender, Integer age) {
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.age = age;
    }
}
