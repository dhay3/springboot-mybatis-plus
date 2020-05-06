package com.chz.conf.component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
filter和listener 都能通过@Component注入唯独Servlet不行
通过@ServletComponentScan(basePackages = {"com.chz.conf.component"})
或是配置ServletRegistrationBean
 */
@WebServlet("/myServlet")
public class MyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet doPost ...");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet doGet ...");
    }
}
