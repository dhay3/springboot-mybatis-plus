package com.chz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringbootXssApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void jsoup1() throws IOException {
        //发送请求到url获取dom元素,可以是get,post
        Document document = Jsoup.connect("http://www.baidu.com")
                .cookie("auth", "token")
                .timeout(3000)
                .get();
        //获取dom元素中的指定块
        String title = document.title();
        System.out.println(title);
    }

    @Test
    public void jsoup2() throws IOException {
        String html = "<title>百度一下，你就知道</title>";
        //         将文本转为dom元素
        Document doc = Jsoup.parse(html);

//        如果设置了base_url为http://www.qq.com/
//        则图片的实际路径为http://www.qq.com/images/logo.jpg
//        Document doc = Jsoup.parse(html,"http://www.baidu.com");

        //从本地文件解析html
//        Document doc = Jsoup.parse(new File("/"), "utf-8", "http://www.baidu.com");
        //返回的dom对象包含head和body
        System.out.println(doc.toString());
    }
}
