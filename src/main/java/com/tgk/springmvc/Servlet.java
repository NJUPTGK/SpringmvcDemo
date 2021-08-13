package com.tgk.springmvc;

import com.tgk.springmvc.handler.HandlerAdapter;
import com.tgk.springmvc.handler.HandlerMapping;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

public class Servlet extends HttpServlet {
    //method  get post
    static Collection<HandlerMapping> handlerMappings;
    static Collection<HandlerAdapter> handlerAdapter;
    private String contextConfig;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理逻辑  class(指定了处理方法) method  很多种
        //servlet
        //url的指定   注解指定   id指定
        //映射器   维护url和处理器的路由关系
        //
        Object handler = getHandlerMapping(req);
        //使用适配器模式
        HandlerAdapter adapter = getHandlerAdapter(handler);//这里有bug,已经改好了
        Object result = null;
        try {
            result = adapter.handle(req, resp, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter p = resp.getWriter();
        p.println(result);

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config.getInitParameter("contextConfigLocation"));
        Map<String, HandlerMapping> handlerMappingMaps = context.getBeansOfType(HandlerMapping.class);
        Map<String, HandlerAdapter> handlerAdapters = context.getBeansOfType(HandlerAdapter.class);
        handlerAdapter = handlerAdapters.values();
        handlerMappings = handlerMappingMaps.values();
    }

    private Object getHandlerMapping(HttpServletRequest req){
        if (handlerMappings!=null){
            for(HandlerMapping mapping:handlerMappings){
                Object handler = mapping.getHandler(req.getRequestURI());
                return handler;
            }

        }
        return null;
    }

    private HandlerAdapter getHandlerAdapter(Object handle){
        if (handlerAdapter!=null){
            for(HandlerAdapter adapter:handlerAdapter){
                boolean flag = adapter.support(handle);
                if (flag){
                    return adapter;
                }
            }

        }
        return null;
    }
}
