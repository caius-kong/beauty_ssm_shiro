package com.yingjun.ssm.aop;

import java.lang.annotation.*;

/**
 * 方法级缓存
 * 标注了这个注解的方法返回值将会被缓存
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodCache {
    /**
     * 缓存结果是集合还是对象
     * 0: 对象
     * 1: 集合
     */
    int isCollection() default 0;

    /**
     * 对象类型
     */
    Class clz();

    /**
     * 缓存过期时间，单位是秒
     */
    int expire() default 300;
}