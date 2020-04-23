package com.chz.springbootmybatisdruid.mapper;

import com.chz.springbootmybatisdruid.entity.Teacher;
import com.chz.springbootmybatisdruid.mapper.sql.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {
    //将增加的主键回写到Teacher中指定的属性id
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO tbl_teacher (t_name,age,gender)VALUES(#{tName},#{age},#{gender})")
    int add(Teacher teacher);

    //开启了delete-allow=false 禁止delete
    @Delete("DELETE FROM tbl_teacher WHERE id = #{id}")
    boolean deleteById(Integer id);

    @Update("UPDATE tbl_teacher SET t_name = #{tName},gender=#{gender},age=#{age} where id=#{id}")
    int update(Teacher teacher);

    @Select("SELECT * FROM tbl_teacher")
    @Results(id = "teacherMap", value = {
            @Result(column = "id", property = "id", id = true),
            @Result(column = "t_name", property = "tName")
    })
    List<Teacher> list();

    @Select("SELECT * FROM tbl_teacher WHERE id = #{id}")
    //引用@Results
    @ResultMap("teacherMap")
    Teacher get(Integer id);

    //动态查询sql
    @SelectProvider(type = UserSqlProvider.class)
    @ResultMap("teacherMap")
    Teacher dynGet(String name, Integer age);

    //动态更新sql
    @UpdateProvider(type = UserSqlProvider.class)
    boolean dynUpdate(String name, Integer age, Integer id);

    @UpdateProvider(type = UserSqlProvider.class)
    //动态删除sql(被druid屏蔽了)
    boolean dynDelete(Integer id);

    //动态增加sql
    @InsertProvider(type = UserSqlProvider.class)
    boolean dynInsert(Teacher teacher);


}
