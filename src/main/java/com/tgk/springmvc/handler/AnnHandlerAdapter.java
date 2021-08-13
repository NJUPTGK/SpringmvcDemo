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
    }

    @Override
    public Object handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        Map<String, String[]> parameterMap = req.getParameterMap();//请求携带的参数
        RequestMappingInfo requestMappingInfo = (RequestMappingInfo) handler;
        Method method = requestMappingInfo.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] paramValue = new Object[parameters.length];
        for (int i=0;i<parameters.length;i++){
            for (Map.Entry<String,String[]> ebtry : parameterMap.entrySet()){
                    if (ebtry.getKey().equals(parameters[i].getAnnotation(RequestParam.class).value())){
                        paramValue[i] = ebtry.getValue()[0];
                    }
            }
        }
        return method.invoke(requestMappingInfo.getObj(),paramValue);
    }
}
