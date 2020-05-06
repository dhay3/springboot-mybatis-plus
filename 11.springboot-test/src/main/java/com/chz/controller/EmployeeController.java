package com.chz.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chz.entity.Employee;
import com.chz.service.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chz
 * @since 2020-04-26
 */
@Slf4j
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    IEmployeeService employeeService;

    /*
    单独文件上传
     */
    @ResponseBody
    @PostMapping("/upload")
    public String upload2(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return "上传失败, 请选择文件";
        }
        String filename = multipartFile.getOriginalFilename();
        String path = System.getProperty("user.dir");
        String suffix = filename.substring(filename.indexOf(".") + 1);
        //忽略大小写判断文件格式
        if (!(suffix.equalsIgnoreCase("png") || suffix.equalsIgnoreCase("jpg"))) {
            return "文件格式错误";
        }
        File file = new File(path + "/files/" + filename);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        String absolutePath = file.getAbsolutePath();
        System.out.println("文件路径===>" + absolutePath);
        try {
            multipartFile.transferTo(file);
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "失败upload";
    }

    /*
   stream多文件上传
    */
    @ResponseBody
    @PostMapping("/batchup")
    public String multiUpload(@RequestParam("file") MultipartFile[] files) {
        //可以通过stream流操作,流操作一定要有终止操作才会正真的执行
        Long collect = Arrays.stream(files).map(this::upload2).collect(Collectors.counting());
        log.info(collect + "个文件上传成功");
        return "上传成功Batch";
    }

    /*
   下载文件
    */
    @GetMapping("/down")
    public void download(HttpServletResponse response,
                         @RequestParam String filename) throws IOException {
        String path = Paths.get("files", filename).toString();
        File file = new File(path);
        System.out.println("filename==========>" + filename);
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
        try (FileInputStream fis = new FileInputStream(path);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ServletOutputStream responseOutputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = bis.read(buffer)) != -1) {
                responseOutputStream.write(buffer, 0, len);
            }
        }
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public Employee getRest(@PathVariable("id") Integer id, HttpServletRequest request) {
        //D:\workspace_for_idea\springboot\springboot-test\src\main\webapp
        System.out.println(request.getServletContext().getRealPath("/"));
        System.out.println(new File(".").getAbsolutePath());
        //D:\workspace_for_idea\springboot\springboot-test\.
        System.out.println(new File("/").getAbsolutePath());
        //D:\
        return employeeService.getById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Employee get(String name) {
        ///employee/get获取当前servlet uri
//        System.out.println(request.getServletPath());
        return employeeService.getOne(new QueryWrapper<Employee>().
                lambda().eq(Employee::getLastName, name));
    }

    @PostMapping("/save")
    public void save(@RequestBody Employee employee) {
        employeeService.save(employee);
    }

    @ResponseBody
    @GetMapping("/tran")
    public boolean transac(Integer formId, Integer toId, Integer age) {
        return employeeService.transaction(formId, toId, age);
    }

    @GetMapping("/test")
    public String skip() {
        return "view";
    }

    //这里不能加@ResponseBody,必须跳转到一个指定的页面
    @GetMapping("/model")
//    @RequestMapping(value = "/model",method = RequestMethod.GET)
    public String modelTest(Model model) {
        model.addAttribute("key", "value");
        return "view";
    }
}

