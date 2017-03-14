package com.yingjun.ssm.aop;

import com.yingjun.ssm.db.DynamicDataSource;
import com.yingjun.ssm.db.DynamicDataSourceHolder;
import com.yingjun.ssm.utils.Helper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by kongyunhui on 2017/3/3.
 */
@Aspect
@Component
public class DataSourceAop {
    private static final Logger LOG = LoggerFactory.getLogger(MethodCacheAop.class);

    // 这里你可以使用execution遍历切入点，也可以使用@annotation定义所有拥有DataSource注解的方法为切入点
    @Before("@annotation(com.yingjun.ssm.aop.DataSource)")
    public void before(JoinPoint point){
        DataSource annotation = getAnnotation(point, DataSource.class);
        String db_key = annotation.value();
        DynamicDataSourceHolder.putDataSource(db_key);
        LOG.info(Helper.getFullMethod(point) + "set db as [{}]", db_key);
    }

    /**
     * 获得@DataSource注解的annotation，目的是获取属性value
     *
     * @return
     */
    private <T extends Annotation> T getAnnotation(JoinPoint jp, Class<T> clz) {
        MethodSignature sign = (MethodSignature) jp.getSignature();
        Method method = sign.getMethod();
        return method.getAnnotation(clz);
    }
}
