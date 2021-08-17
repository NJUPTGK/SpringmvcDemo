package com.tgk.springmvc.handler;

import com.tgk.springmvc.RequestMapping;
import com.tgk.springmvc.RequestMappingInfo;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component//RequestMapping
public class AnnHandlerMapping implements HandlerMapping{//用于处理requestMapping这个注解的
    static Map<String,Object> map = new HashMap<>();

    @Override
    public Object getHandler(String url) {
        return map.get(url);
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        //System.out.println("3");
        Method[] methods =  bean.getClass().getDeclaredMethods();//把bean中定义的所有方法取出来
        for (Method method:methods){
            RequestMappingInfo info = createRequestMappingInfo(method, bean);
            map.put(info.getUrl(),info);

        }
        return true;
    }
    //定义一个对象RequestMappingInfo
    private RequestMappingInfo createRequestMappingInfo(Method method, Object bean){
        RequestMappingInfo info = new RequestMappingInfo();
        if(method.isAnnotationPresent(RequestMapping.class)){//判断方法是不是有requestMapping这个注解
            info.setMethod(method);
            info.setObj(bean);
            info.setUrl(method.getDeclaredAnnotation(RequestMapping.class).value());//获取注解上的参数
        }
        return info;
    }
}
