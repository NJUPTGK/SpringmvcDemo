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
        System.out.println("进入doget方法");
        Object handler = getHandlerMapping(req);
        System.out.println(handler+"+打印handler");//有可能会打印出HelloServlet或者RequestMappingInfo，具体的逻辑在AnnHandlerMapping和BeanIdHandlerMapping里
        //使用适配器模式
        HandlerAdapter adapter = getHandlerAdapter(handler);//这里有bug,已经改好了
        System.out.println(adapter+"+打印adapter");
        Object result = null;
        try {
            result = adapter.handle(req, resp, handler);
            // 抛java.lang.NullPointerException异常的话是因为
            // 当浏览器访问http://localhost:8080/test时，浏览器会抛出两次请求，第二次请求时，服务器会抛异常，这应该是正常的
            System.out.println(result+"+打印result");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result!=null){
            PrintWriter p = resp.getWriter();
            p.println(result);//如果不加这两句话，网页上就什么都没有，不会在网页上打印test
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("开始获取一个容器");
        //获取一个容器，这一步就是完成了bean的实例化和初始化，扫描所有带@Component注解的类
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config.getInitParameter("contextConfigLocation"));
        System.out.println("获取一个容器完成");
        Map<String, HandlerMapping> handlerMappingMaps = context.getBeansOfType(HandlerMapping.class);// 获取handlerMappingMaps
        Map<String, HandlerAdapter> handlerAdapters = context.getBeansOfType(HandlerAdapter.class);// 获取handlerAdapters
        handlerAdapter = handlerAdapters.values();
        handlerMappings = handlerMappingMaps.values();
        System.out.println(handlerAdapter+"+打印handlerAdapter");
        System.out.println(handlerMappings+"+打印handlerMappings");
    }

    private Object getHandlerMapping(HttpServletRequest req){
        if (handlerMappings!=null){
            for(HandlerMapping mapping:handlerMappings){
                //System.out.println(req.getRequestURI());
                Object handler = mapping.getHandler(req.getRequestURI());
                if (handler==null){
                    continue;
                }
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
