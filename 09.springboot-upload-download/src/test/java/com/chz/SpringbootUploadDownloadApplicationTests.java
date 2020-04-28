package com.chz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

@SpringBootTest
class SpringbootUploadDownloadApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public void testStringUtils(){
        String inString="aabbccdd";
        //将字符串中指定的子字符串替换
        String str1 = StringUtils.replace(inString, "aa", "zz");
        System.out.println("str1===>"+str1);
        //删除字符串中指定的字符串
        String str2 = StringUtils.delete(inString, "dd");
        System.out.println("str2===>"+str2);
        //获取字符串路径中的文件,自动剔除目录
        String str3 = StringUtils.getFilename("path/myFile.txt");
        System.out.println("str3===>"+str3);
        boolean empty = StringUtils.isEmpty(inString);
        System.out.println(empty);
        //清理路径中的.. ,点点前的路径将被移除
//        String cleanPath = StringUtils.cleanPath("/path/../work/myFile.txt");
        String cleanPath = StringUtils.cleanPath("ht..html");
        System.out.println(cleanPath);
    }
}
