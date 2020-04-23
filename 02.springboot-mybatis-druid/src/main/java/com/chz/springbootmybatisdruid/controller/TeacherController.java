package com.chz.springbootmybatisdruid.controller;

import com.chz.springbootmybatisdruid.entity.Teacher;
import com.chz.springbootmybatisdruid.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeacherController {
    @Autowired
    ITeacherService teacherService;
    @RequestMapping("/add")
    public Integer add(Teacher teacher){
       return teacherService.add(teacher);
    }
    @RequestMapping("/list")
    public List<Teacher> list(){
        return teacherService.list();
    }
}
