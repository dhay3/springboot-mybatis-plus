package com.chz.component;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component//注入bean覆盖默认的DefaultErrorAttributes
public class MyErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        //获取request域中map
//        HashMap<String, Object> map = (HashMap<String, Object>) webRequest.getAttribute("customer", 0);
        Object map = webRequest.getAttribute("customer", RequestAttributes.SCOPE_REQUEST);
        //保留原有的attr
        Map<String, Object> superAttr = super.getErrorAttributes(webRequest, includeStackTrace);
        superAttr.put("map", map);
        superAttr.put("info", "这是一条信息");
        return superAttr;
    }
}
