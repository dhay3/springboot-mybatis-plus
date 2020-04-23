package com.chz.springbootmybatisdruid.service.impl;

import com.chz.springbootmybatisdruid.entity.Teacher;
import com.chz.springbootmybatisdruid.mapper.TeacherMapper;
import com.chz.springbootmybatisdruid.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeacherServiceImpl implements ITeacherService {
    @Autowired
    TeacherMapper mapper;
    @Override
    public int add(Teacher teacher) {
       return mapper.add(teacher);
    }

    @Override
    public boolean deleteById(Integer id) {
        return mapper.deleteById(id);
    }

    @Override
    public int update(Teacher teacher) {
        return mapper.update(teacher);
    }

    @Override
    public List<Teacher> list() {
        return mapper.list();
    }

    @Override
    public Teacher get(Integer id) {
        return mapper.get(id);
    }

    @Override
    public Teacher dynGet(String name, Integer age) {
        return mapper.dynGet(name,age);
    }

    @Override
    public boolean dynUpdate(String name, Integer age, Integer id) {
        return mapper.dynUpdate(name,age,id);
    }

    @Override
    public boolean dynDelete(Integer id) {
        return mapper.dynDelete(id);
    }

    @Override
    public boolean dynInsert(Teacher teacher) {
        return mapper.dynInsert(teacher);
    }
}
