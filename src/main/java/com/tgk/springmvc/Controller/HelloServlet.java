package com.tgk.springmvc.Controller;

import com.tgk.springmvc.RequestMapping;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(".......servlet处理器");//service就是不管是get还是post先进来再说
        PrintWriter p = resp.getWriter();
        p.println("service");//如果不加这两句话，网页上就什么都没有，不会在网页上打印东西
    }
}
