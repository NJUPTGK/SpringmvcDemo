package com.tgk.springmvc.handler;

import com.tgk.springmvc.RequestMapping;
import com.tgk.springmvc.RequestMappingInfo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

@Component
public class AnnHandlerAdapter implements HandlerAdapter{
    @Override
    public boolean support(Object handler) {
        return handler instanceof RequestMappingInfo;
    }//判断这个handler是不是RequestMappingInfo

    @Override
    public Object handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        Map<String, String[]> parameterMap = req.getParameterMap();//获取request请求携带的参数
        System.out.println(parameterMap.values());//http://localhost:8080/test?name=name 携带的参数就是name
        RequestMappingInfo requestMappingInfo = (RequestMappingInfo) handler;
        System.out.println(requestMappingInfo);
        Method method = requestMappingInfo.getMethod();//获取方法
        System.out.println(method);//打印的方法就是test方法
        Parameter[] parameters = method.getParameters();//获取方法的参数列表
        for (int i = 0; i < parameters.length; i++) {
            System.out.println(parameters[i]);//打印的结果是java.lang.String arg0
        }
        Object[] paramValue = new Object[parameters.length];
        for (int i=0;i<parameters.length;i++){//parameterMap和parameters进行匹配
            for (Map.Entry<String,String[]> ebtry : parameterMap.entrySet()){
                    if (ebtry.getKey().equals(parameters[i].getAnnotation(RequestParam.class).value())){//把获取request请求携带的参数的key和方法的参数列表上标有RequestParam注解的参数名进行比较
                        paramValue[i] = ebtry.getValue()[0];//拿到request请求传过来的参数的值，赋值给paramValue,如果没有传值的话，paramValue的值就是空的
                    }
            }
        }
        return method.invoke(requestMappingInfo.getObj(),paramValue);//用反射去执行方法，然后把paramValue也就是参数的值传进去
    }
}
