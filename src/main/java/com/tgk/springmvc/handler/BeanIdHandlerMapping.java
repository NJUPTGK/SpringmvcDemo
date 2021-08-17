package com.tgk.springmvc.handler;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Commit;

import java.util.HashMap;
import java.util.Map;

@Component
public class BeanIdHandlerMapping implements HandlerMapping {
    //处理定义在Spring-servlet.xml里的bean
    static Map<String,Object> map = new HashMap<>();

    @Override
    public Object getHandler(String url) {
        return map.get(url);
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        //postProcessorBeforeInstantiation在实例化之前调用
        //postProcessorAfterInstantiation在实例化之后，初始化之前调用
        if (beanName.startsWith("/")){//在Spring-servlet-xml里配置
            map.put(beanName,bean);
            System.out.println(map.values());//会打印com.tgk.springmvc.Controller.HelloServlet
        }
        // 此项目中有5个标有@component和@Controller的bean，还有一个HelloServlet被实例化和初始化了，
        // 因为AnnHandlerMapping和BeanIdHandlerMapping里面有postProcessAfterInstantiation，所以它们自己不执行自己的这个方法
//        if (beanName.equals("BeanIdHandlerMapping")){
//            System.out.println(beanName);
//        }
//        System.out.println("1");
        return false;
    }

}
//InstantiationAwareBeanPostProcessor代表了Spring的另外一段生命周期：实例化。先区别一下Spring Bean的实例化和初始化两个阶段的主要作用：
//(1)、实例化----实例化的过程是一个创建Bean的过程，即调用Bean的构造函数，单例的Bean放入单例池中
//(2)、初始化----初始化的过程是一个赋值的过程，即调用Bean的setter，设置Bean的属性
//BeanPostProcessor作用于过程（2）前后，现在的InstantiationAwareBeanPostProcessor则作用于过程（1）前后；
