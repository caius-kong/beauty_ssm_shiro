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
    private static final Logger LOG = LoggerFactory.getLogger(DataSourceAop.class);

    // @anontation不支持拦截接口方法，因此此处必须使用execution
    @Before("execution(* com.yingjun.ssm.dao..*.*(..))")
    public void before(JoinPoint point){
        System.out.println("--->DataSourceAop start...");
        // 遍历所有dao层，因此存在没有@DataSource的方法，需要空指针处理
        DataSource annotation = getAnnotation(point, DataSource.class);
        if(annotation != null) {
            String db_key = annotation.value();
            DynamicDataSourceHolder.putDataSource(db_key);
            LOG.info(Helper.getFullMethod(point) + " set db as [{}]", db_key);
        }
    }

    /**
     * 获得@DataSource注解的annotation，目的是获取属性value
     *
     * @return
     */
    private <T extends Annotation> T getAnnotation(JoinPoint jp, Class<T> clz) {
        MethodSignature sign = (MethodSignature) jp.getSignature();
        Method method = sign.getMethod();
        if(method!=null && method.isAnnotationPresent(DataSource.class)){
            return method.getAnnotation(clz);
        }
        return null;
    }
}
