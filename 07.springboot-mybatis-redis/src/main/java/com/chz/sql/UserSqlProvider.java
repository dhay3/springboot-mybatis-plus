package com.chz.sql;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import javax.validation.constraints.NotNull;

/**
 * 配置动态sql
 */
public class UserSqlProvider implements ProviderMethodResolver {
    public static String list(final String name, final Integer id) {
        return new SQL() {
            {
                SELECT("*").
                        FROM("tbl_employee");
                if (null != name) {
                    WHERE("last_name = #{name}");
                }
                if (null != id) {
                    WHERE("id=#{id}");
                }
            }
        }.toString();
    }

    public static String update(@NotNull final String name, final Integer age) {
        return new SQL() {
            {
                UPDATE("tbl_employee");
                if (null != name) {
                    SET("last_name=#{name}");
                }
                if (null != age) {
                    SET("age=#{age}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }
}
