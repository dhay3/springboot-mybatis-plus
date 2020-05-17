package com.chz.springbootjson.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
//@JsonIgnoreProperties({"name","age"})//忽略一组json key注意是json key不是属性
public class User implements Serializable {
    private static final long serialVersionUID = 262962110318827040L;

    public interface UserNameFieldView {

    }

    public interface AllUserFieldView extends UserNameFieldView {

    }

    //指定输出的json key为username
//    @JsonProperty("username")
    //对应controller中的@JsonView
    @JsonView(UserNameFieldView.class)
    private String name;
    @JsonView(AllUserFieldView.class)
    private Integer age;
    //json忽略输出该字段
//    @JsonIgnore
    @JsonView(AllUserFieldView.class)
    private Integer password;
    //将对象转为json串
    //如果使用原生的json返回日期,格式有问题,需要自定格式ObjectMapper或使用注解
    @JsonFormat(pattern = "yyyy-MM-dd hh", timezone = "GTM+8")
    @JsonView(AllUserFieldView.class)
    private Date date;
    private String gender;
}
