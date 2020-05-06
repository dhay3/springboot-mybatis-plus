package com.chz;


import com.chz.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.*;
import org.springframework.web.util.HtmlUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringbootUtilsApplicationTests {

    @Test
    void contextLoads() {

    }

    /**
     * 资源获取类
     * new的时候不会报错和java的File一样不会检查文件在不在
     */
    @Test
    public void resource() throws IOException {
        //能获取系统路径的文件
        FileSystemResource fileResource = new FileSystemResource("D:\\workspace_for_idea\\springboot\\springboot-utils\\src\\main\\resources\\test.txt");
        InputStream inputStream = fileResource.getInputStream();
        System.out.println(fileResource.getFilename());
        //获取classPath下文件
        ClassPathResource pathResource = new ClassPathResource("test.txt");
        InputStream inputStream1 = pathResource.getInputStream();
        System.out.println(pathResource.getFilename());
        //默认获取classPath下file对象,不会检查文件在不在,如果在classpath下要加classpath,不推荐使用该工具类
        File file = ResourceUtils.getFile("classpath:test3.txt");
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.canRead());
    }

    /**
     * 文件操作类
     *
     * @throws IOException
     */
    @Test
    public void file() throws IOException {
        ClassPathResource resource = new ClassPathResource("test.txt");
        //将文件内容复制到另一文件
        FileCopyUtils.copy(resource.getFile()
                , new File("src/main/resources/test3.txt"));
        //将文件内容读入到byte[]中
        byte[] bytes = FileCopyUtils.copyToByteArray(resource.getFile());
        //将文件内容转为指定编码格式资源
        EncodedResource encodedResource = new EncodedResource(resource, "utf-8");
        //将文件内容以流的形式复制到另一个文件,这样就不会有乱码了
        FileCopyUtils.copy(encodedResource.getInputStream()
                , new FileOutputStream("src/main/resources/test2.txt"));
        //拷贝到字符串中
        String s = FileCopyUtils.copyToString(encodedResource.getReader());
        System.out.println(s);
    }

    /**
     * 格式化html类
     */
    @Test
    public void testHtml() {
        String specialStr = "<div id=\"testDiv\">test1;test2</div>";
        //将html转译字符表示
        String s = HtmlUtils.htmlEscape(specialStr);
        //&lt;div id=&quot;testDiv&quot;&gt;test1;test2&lt;/div&gt;
        System.out.println(s);

        //将html转译数字表示
        String s1 = HtmlUtils.htmlEscapeDecimal(specialStr);
        //&#60;div id=&#34;testDiv&#34;&#62;test1;test2&#60;/div&#62;
        System.out.println(s1);
        //将字符串转译回html
        String s2 = HtmlUtils.htmlUnescape(s);
        System.out.println(s2);
    }

    /**
     * 数字类型转换类
     */
    @Test
    public void testNumberUtils() {
        //将字符串转为对应数字类型
        String num = "123 234";
        Integer integer1 = NumberUtils.parseNumber(num, Integer.class);
        System.out.println(integer1);
        BigDecimal bigDecimal = NumberUtils.parseNumber(num, BigDecimal.class);
        System.out.println(bigDecimal);
    }

    /**
     * 对象工具类
     */
    @Test
    public void testObjectUtils() {
        //能比较两个引用变量指向的对象地址是否相同,还能比较null
        String a = null;
        String b = null;
        System.out.println(ObjectUtils.nullSafeEquals(a, b));
    }

    /**
     * 反射工具类
     *
     * @throws IllegalAccessException
     */
    @Test
    public void testReflectionUtils() throws IllegalAccessException {
        ArrayList<String> list = new ArrayList<>();
        //获取类的declared filed
        Field default_capacity = ReflectionUtils.findField(ArrayList.class, "DEFAULT_CAPACITY");
        System.out.println(default_capacity);
        default_capacity.setAccessible(true);
        //从实例对象中获取属性值
        Object o = default_capacity.get(list);
        System.out.println(o);
    }

    /**
     * 序列化与反序列化工具
     * 可以不用按照写入顺序读取
     *
     * @throws IOException
     */
    @Test
    public void testSerialization() throws IOException {
        User u1 = new User("zs", "20");
        User u2 = new User("ls", "12");
        byte[] bu1 = SerializationUtils.serialize(u1);
        byte[] bu2 = SerializationUtils.serialize(u2);
        Object user2 = SerializationUtils.deserialize(bu2);
        System.out.println(user2);
        Object user1 = SerializationUtils.deserialize(bu1);
        System.out.println(user1);
    }

    @Test
    public void testStopWatch() throws InterruptedException {
        //指定stopWatch的名字
        StopWatch sw = new StopWatch("test");
        //指定任务
        sw.start("task1");
        TimeUnit.SECONDS.sleep(1);
        sw.stop();
        sw.start("task2");
        TimeUnit.SECONDS.sleep(2);
        sw.stop();
        //漂亮显示
        System.out.println(sw.prettyPrint() + "+++++++++");
        //输出所有任务的总时长,以纳秒显示
        String s = sw.shortSummary();
        double totalTimeSeconds = sw.getTotalTimeSeconds();
        System.out.println(s);
        System.out.println(totalTimeSeconds);
    }

    @Test
    public void testTypeUtils() {
        User zs = new User("zs", "23");
        Field[] declaredFields = User.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            //getGenericType能获取泛型类型
            Type genericType = declaredField.getGenericType();
            Class<?> type = declaredField.getType();
            System.out.println(genericType.getTypeName());
            System.out.println(type);
            System.out.println("-----------------");
        }
    }
}
