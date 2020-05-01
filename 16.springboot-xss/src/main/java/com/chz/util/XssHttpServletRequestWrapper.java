package com.chz.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Objects;

/**
 * 核心
 * 过滤http请求中参数包含的恶意字符
 * 需要重写getParameter,getParameterValues,getHeader
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    //原始的请求
    public HttpServletRequest orgRequest;
    //是否包含富文本
    private boolean isIncludeRichText;


    public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {
        super(request);
        orgRequest = request;
        this.isIncludeRichText = isIncludeRichText;
    }

    public boolean isIncludeRichText() {
        return isIncludeRichText;
    }

    public void setIncludeRichText(boolean includeRichText) {
        isIncludeRichText = includeRichText;
    }

    /**
     * 过滤请求头
     */
    @Override
    public String getHeader(String name) {
        JsoupUtils.clean(name);
        String header = super.getHeader(name);
        if (!StringUtils.isEmpty(header)) {
            return JsoupUtils.clean(name);
        }
        return header;
    }

    /**
     * 过滤请求的参数和值
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
//        ObjectUtils.nullSafeEquals("content", name)
        boolean condition = Objects.equals("content", name) || name.endsWith("WithHtml");
        //如果请求的参数为content或是以WithHtml结尾的,且不包含富文本
        if (condition && !isIncludeRichText) {
            //不过滤参数
            return super.getParameter(name);
        }
        //过滤参数
        JsoupUtils.clean(name);
        String value = super.getParameter(name);
        //如果值不为null和空字符串""( " "不算空字符串因为就是判断长度)过滤值
        if (!StringUtils.isEmpty(value)) {
            JsoupUtils.clean(value);
        }
        return value;
    }

    /**
     * 过滤单个参数多个值
     * 如复选框
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        for (int i = 0; i < values.length; i++) {
            //过滤值后重新赋值
            values[i] = JsoupUtils.clean(values[i]);
        }
        return values;
    }

    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    public void setOrgRequest(HttpServletRequest orgRequest) {
        this.orgRequest = orgRequest;
    }

    /**
     * 获取原始的request请求
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
        if (request instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) request).getOrgRequest();
        }
        return request;
    }
}
