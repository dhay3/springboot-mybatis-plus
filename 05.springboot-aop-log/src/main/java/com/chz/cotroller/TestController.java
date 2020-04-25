package com.chz.cotroller;

import com.chz.annotaion.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
public class TestController {
    @Log("方法一")
    @GetMapping("/one")
    @ResponseBody
    public String methodOne() {
        return "测试1";
    }

    @Log("方法二")
    @GetMapping("/two/{name}/{id}")
    @ResponseBody
    public String methodTwo(@PathVariable("name") String name, @PathVariable("id") Integer id) {
        return "测试2";
    }

    @Log("方法三")
    @GetMapping("/three")
    @ResponseBody
    public String methodThree() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return "测试3";
    }
}
