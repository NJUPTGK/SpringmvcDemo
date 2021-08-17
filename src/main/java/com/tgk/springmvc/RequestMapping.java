package com.tgk.springmvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//使用范围是在方法上
@Retention(RetentionPolicy.RUNTIME)//指定这个注解是在运行时生效
public @interface RequestMapping {
    String value() default "";
}
