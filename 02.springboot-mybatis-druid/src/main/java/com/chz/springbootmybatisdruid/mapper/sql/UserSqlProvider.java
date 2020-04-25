package com.chz.springbootmybatisdruid.mapper.sql;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import javax.validation.constraints.NotNull;

/**
 * 动态sql,支持链式编程
 * 自定provider继承ProviderMethodResolver
 * 且mapper中的方法名和自定义的Provider中的方法名一致,就可以省略@SelectProvider中method
 */
public class UserSqlProvider implements ProviderMethodResolver {
    //采用这种方式可以减少代码冗余
    //name 和 age是从mapper中传过来的参数
    public static String dynGet(final String name, final Integer age) {
        //采用局部匿名内部内参数要用final
        return new SQL() {
            {
                SELECT("*");
                FROM("tbl_teacher");
                if (null != name) {
                    WHERE("t_name = #{name}");
                }
                //where会自动拼接and
                if (null != age) {
                    WHERE("age=#{age}");
                }
            }
        }.toString();
    }

    //同样的必须要有一个条件,如果不需要做条件判断就不用给值,通过调用的函数给#{...}赋值
    public static String dynUpdate(@NotNull final String name, final Integer age) {
        return new SQL() {
            {
                UPDATE("tbl_teacher");
                if (null != name) {
                    SET("t_name=#{name}");
                } else if (null != age) {
                    SET("age=#{age}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

    public static String dynDelete() {
        return new SQL() {{
            DELETE_FROM("tbl_teacher");
            WHERE("id=#{id}");
        }}.toString();
    }

    public static String dynInsert() {
        return new SQL() {{
            INSERT_INTO("tbl_teacher").
                    VALUES("t_name,age,gender",
                            "#{tName},#{age},#{gender}");
        }}.toString();
    }

}
