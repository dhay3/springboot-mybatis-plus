package com.chz.mapper;

import com.chz.entity.Employee;
import com.chz.sql.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @SelectProvider(type = UserSqlProvider.class)
    @Results(id = "employeeMap",
            value = {
                    @Result(property = "id", column = "id", id = true),
                    @Result(property = "lastName", column = "last_name")
            })
    List<Employee> list(String name, Integer id);

    //使用原生的NOW()函数自动插入时间
    @Insert("INSERT INTO tbl_employee (last_name,email,gender,age,date)VALUES" +
            "(#{lastName},#{email},#{gender},#{age},NOW())")
    boolean add(Employee employee);

//    @UpdateProvider(type = UserSqlProvider.class)
//    boolean update(String name, Integer age, Integer id);

    @Select("SELECT * FROM tbl_employee WHERE id = #{id}")
    @ResultMap("employeeMap")
    Employee get(Integer id);

    @Update("UPDATE tbl_employee SET " +
            "last_name=#{lastName}, email=#{email},gender=#{gender},age=#{age},date=NOW()" +
            "WHERE id=#{id}")
    boolean update(Employee employee);

    @Delete("DELETE FROM tbl_employee WHERE id = #{id}")
    boolean delete(Integer id);

}
