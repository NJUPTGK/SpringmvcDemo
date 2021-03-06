package com.tgk.springmvc.handler;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)//作用在参数上面
@Retention(RetentionPolicy.RUNTIME)//运行时
public @interface RequestParam {
    String value() default "";
}
