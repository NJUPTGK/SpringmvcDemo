package com.tgk.springmvc.handler;

import org.springframework.stereotype.Component;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class ServletHandleAdapter implements HandlerAdapter{

    @Override
    public boolean support(Object handler) {
        return handler instanceof Servlet;
    }

    @Override
    public Object handle(HttpServletRequest req, HttpServletResponse resp,Object handler) throws Exception {
        ((Servlet)handler).service(req,resp);
        return null;
    }
}
