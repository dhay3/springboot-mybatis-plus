package com.chz.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * XssFilter过滤Xss请求的入口
 * 拦截防止xss
 */
@Slf4j
public class XssFilter implements Filter {
    //LoggerFactory log = LoggerFactory.getLogger(XssFilter.class)
    //是否包含富文本内容
    public static boolean IS_INCLUDE_RICH_TEXT = false;

    public List<String> excludes = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("----------------- xss filter init ----------------");
        //获取filter中的初始参数
        String isRichText = filterConfig.getInitParameter("isIncludeRichText");
        if (!StringUtils.isEmpty(isRichText)) {
            //将字符串转为布尔
            IS_INCLUDE_RICH_TEXT = BooleanUtils.toBoolean(isRichText);
        }
        String temp = filterConfig.getInitParameter("excludes");
        if (!StringUtils.isEmpty(temp)) {
            String[] url = temp.split(",");
            //spring工具类
            Assert.notNull(url, "exclude不能为null");
            excludes.addAll(Arrays.asList(url));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("----------------------xss filter is open----------------------");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (handleExcludeURL(req, resp)) {
            //包含exclude的url片段放行
            chain.doFilter(request, response);
            return;
        }
        //不包含exclude的url片段
        //将request包装到XssHttpServletRequestWrapper
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
                (HttpServletRequest) request, IS_INCLUDE_RICH_TEXT);
        //放行,交给spring处理
        chain.doFilter(xssRequest, response);
    }

    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
        //spring工具类,判断集合是否为空集合或集合为null
        if (CollectionUtils.isEmpty(excludes)) {
            return false;
        }
        //获取请求的servletPath,不带协议+ip+端口+项目名
        String servletPath = request.getServletPath();
        for (String pattern : excludes) {
            //这里的^表示匹配开头
            Pattern p = Pattern.compile("^" + pattern);
            //String实现CharSequence, 用pattern去匹配servlet
            Matcher matcher = p.matcher(servletPath);
            //判断请求的servletPath中是否有匹配pattern的,只要有一个就返回true
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

}
