package com.coderlucifar.middleware.whitelist.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface DoWhiteList {

    // 当前接口入参需要提取的属性
    String key() default "";
    // 拦截到用户请求后的返回信息
    String returnJson() default "";

}
