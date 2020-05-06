package com.chz.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CorsController8081 {
    //方法二,单独接收
    @RequestMapping("/getCors")
    @CrossOrigin(origins = "http://localhost:8080")
    public Map<String, Object> getInfoCors(String msg) {
        System.out.println(msg);
        HashMap<String, Object> map = new HashMap<>();
        //可以将数据通过ajax返回到8080端口
        map.put("8081v", "8081k");
        return map;
    }
}
