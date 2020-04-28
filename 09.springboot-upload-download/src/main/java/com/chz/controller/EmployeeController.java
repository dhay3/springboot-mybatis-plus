package com.chz.controller;


import com.chz.entity.Employee;
import com.chz.service.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
import java.util.Base64;
import java.util.List;
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

    @ResponseBody
    @RequestMapping("/get/{id}")
    public Employee getRest(@PathVariable("id") Integer id, HttpServletRequest request) {
        //D:\workspace_for_idea\springboot\springboot-test\src\main\webapp
        System.out.println(request.getServletContext().getRealPath("/"));
        //D:\workspace_for_idea\springboot\springboot-test\.
        System.out.println(new File(".").getAbsolutePath());
        //D:\
        System.out.println(new File("/").getAbsolutePath());
        //D:\workspace_for_idea\springboot\springboot-upload-download\
        String path = System.getProperty("user.dir");
        return employeeService.getById(id);
    }

    /*
    单独文件上传
     */
    @ResponseBody
    @PostMapping("/upload1")
    public String upload1(String desc,
                          Employee employee,
                          @RequestParam("file") MultipartFile multipartFile) throws IOException {
        //上传的文件不能为空
        if (multipartFile.isEmpty()) {
            return "上传失败, 请选择文件";
        }
        //springboot对封装的对象自动做了处理,能直接拿到值,但是springMvc不能
        System.out.println(employee);
        System.out.println(desc);
        //D:\workspace_for_idea\springboot\springboot-upload-download\
        //获取当前项目所在位置
        String filename = multipartFile.getOriginalFilename();
        System.out.println("文件大小===>" + multipartFile.getSize());
        System.out.println("上传文件类型===>" + multipartFile.getContentType());
        //获取根路径
        String path = System.getProperty("user.dir");
        File file = new File(path + "/files/" + filename);
        //判断父目录是否存在
        if (!file.getParentFile().exists()) {
            //新建文件
            file.getParentFile().mkdir();
        }
        String absolutePath = file.getAbsolutePath();
        System.out.println("文件路径===>" + absolutePath);
        multipartFile.transferTo(file);
        return "上传成功upload1";
    }

    @ResponseBody
    @PostMapping("/upload2")
    public String upload2(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return "上传失败, 请选择文件";
        }
        String filename = multipartFile.getOriginalFilename();
        System.out.println("filename===>" + filename);
        System.out.println("文件大小===>" + multipartFile.getSize());
        System.out.println("contentType===>" + multipartFile.getContentType());
        String path = System.getProperty("user.dir");
        //两种方式获取文件后缀名,正则中. 用\\.表示
        System.out.println(filename.split("\\.")[1]);
        String suffix = filename.substring(filename.indexOf(".") + 1);
        System.out.println("文件后缀名" + suffix);
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
        return "失败upload2";
    }

    /*
    多文件上传
    也可以采用  @RequestParam("file") Multipart[] 来作为参数
     */
    @ResponseBody
    @PostMapping("/multiup1")
    public String multiUpLoad(HttpServletRequest request) {
        //getFile中的name对应前端的name
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            //必须上传文件
            if (file.isEmpty()) {
                log.info("上传第" + (i + 1) + "个文件失败");
                return "上传第" + (i + 1) + "个文件失败";
            }
            String filename = file.getOriginalFilename();
            String path = request.getServletContext().getRealPath("/files");
            File filePath = new File(path + "/" + filename);
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdir();
            }
            try {
                log.info("上传第" + (i + 1) + "个文件成功");
                file.transferTo(filePath);
            } catch (IOException e) {
                log.info("上传第" + (i + 1) + "个文件失败");
                e.printStackTrace();
                return "上传第" + (i + 1) + "个文件失败";
            }
        }
        return "上传成功multiup1";
    }

    /*
    stream多文件上传
     */
    @ResponseBody
    @PostMapping("/multiup2")
    public String multiUpload(@RequestParam("file") MultipartFile[] files) {
        //可以通过stream流操作,流操作一定要有终止操作才会正真的执行
        Long collect = Arrays.stream(files).map(this::upload2).collect(Collectors.counting());
        log.info(collect + "个文件上传成功");
        return "上传成功multiup2";
    }

    /*
    下载文件
     */
    @GetMapping("/down")
    public void download(HttpServletResponse response,
                         @RequestParam String filename) throws IOException {
//        String header = request.getHeader("User-Agent");
//        String path="files"+File.separator+filename;
        String path = Paths.get("files", filename).toString();
        System.out.println("filename==========>" + filename);
        //设置消息头
        response.setContentType("application/force-download");//强制下载不打开
        //消息头,固定格式, filename这里是文件的名字
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
}

