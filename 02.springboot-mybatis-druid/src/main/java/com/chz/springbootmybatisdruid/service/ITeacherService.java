package com.chz.springbootmybatisdruid.service;


import com.chz.springbootmybatisdruid.entity.Teacher;

import java.util.List;

public interface ITeacherService {


    int add(Teacher teacher);


    boolean deleteById(Integer id);


    int update(Teacher teacher);


    List<Teacher> list();


    Teacher get(Integer id);


    Teacher dynGet(String name, Integer age);


    boolean dynUpdate(String name, Integer age, Integer id);


    boolean dynDelete(Integer id);


    boolean dynInsert(Teacher teacher);
}
