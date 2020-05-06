package com.chz.entity;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
//@Validated
public class User implements Serializable {

    private static final long serialVersionUID = -8677072538094918597L;

    public interface IUserName {
    }
    //与@JsonView效果差不多
    public interface IAllFiled extends IUserName{
    }

    @NotBlank(groups = IUserName.class, message = "username can't be null")
    private String username;
    @NotNull(groups = IAllFiled.class, message = "age cant'be null")
    private Integer age;
    @NotNull(message = "性别只能为0或1")
    @Min(value = 0, message = "性别只能为0或1")
    @Max(value = 1, message = "性别只能为0或1")
    private Integer gender;
}
