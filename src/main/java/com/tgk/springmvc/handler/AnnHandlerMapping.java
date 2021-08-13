package com.tgk.springmvc.handler;

import com.tgk.springmvc.RequestMapping;
import com.tgk.springmvc.RequestMappingInfo;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component//RequestMapping
public class AnnHandlerMapping implements HandlerMapping{
    static Map<String,Object> map = new HashMap<>();

    @Override
    public Object getHandler(String url) {
        return map.get(url);
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        Method[] methods =  bean.getClass().getDeclaredMethods();
        for (Method method:methods){
            RequestMappingInfo info = createRequestMappingInfo(method, bean);
            map.put(info.getUrl(),info);

        }
        return true;
    }
    //定义一个对象
    private RequestMappingInfo createRequestMappingInfo(Method method, Object bean){
        RequestMappingInfo info = new RequestMappingInfo();
        if(method.isAnnotationPresent(RequestMapping.class)){
            info.setMethod(method);
            info.setObj(bean);
            info.setUrl(method.getDeclaredAnnotation(RequestMapping.class).value());
        }
        return info;
    }
}
