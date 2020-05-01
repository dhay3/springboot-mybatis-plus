package com.chz.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * xss过滤工具
 */
public class JsoupUtils {
    //设置白名单
    private static final Whitelist WHITELIST = Whitelist.basicWithImages();
    //配置过滤参数不对代码格式化
    private static final Document
            .OutputSettings OUTPUT_SETTINGS = new Document
            //默认开启,关闭输入的代码格式化
            .OutputSettings().prettyPrint(false);

    static {
        //为标签添加属性,使用伪标签(:all表示所有标签),这里指白名单中的标签
        //允许富文本编辑器设置行内样式
        WHITELIST.addAttributes(":all", "style");
    }

    /**
     * content是用户输入的内容,没有baseUri,所以设置空
     * 过滤,如果不需要baseUri 就使用空字符串
     * 从不信任的html片段中截取信任的片段
     */
    public static String clean(String content) {
        return Jsoup.clean(content, "", WHITELIST, OUTPUT_SETTINGS);
    }
    /*
     这里能发现事件被过滤了
    public static void main(String[] args) {
       String text = "<a href=\"http://www.baidu.com/a\" onclick=\"alert(1);\">sss</a><script>alert(0);</script>sss";
       System.out.println(clean(text));
    }
     */
}
