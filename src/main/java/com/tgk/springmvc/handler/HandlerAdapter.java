package com.tgk.springmvc.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    public boolean support(Object handler);
    public Object handle(HttpServletRequest req, HttpServletResponse resp,Object handler) throws Exception;
}
