package com.yingjun.ssm.aop;

import com.yingjun.ssm.cache.RedisCache;
import com.yingjun.ssm.db.DynamicDataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kongyunhui on 2017/3/21.
 *
 * 设置读写模式拦截器
 */
@Aspect
@Component
public class DataSourceAop {
    public static final Logger LOG = LoggerFactory.getLogger(DataSourceAop.class);
    
    @Before("execution(* com.yingjun.ssm.dao..*.*(..))")
    public void beforeMethod(JoinPoint point){
        System.out.println("--->DataSourceAop start...");
        String methodName = point.getSignature().getName();
        System.out.println("--->methodName:" + methodName);
        Pattern pattern = Pattern.compile("^[save|add|create|insert|update|merge|del|remove|put].*");
        Matcher matcher = pattern.matcher(methodName);
        if (matcher.matches()) {
            LOG.info(methodName + " hit write");
            DynamicDataSourceHolder.setIsWrite(true);
        } else {
            LOG.info(methodName + " miss write");
            DynamicDataSourceHolder.setIsWrite(false);
        }
    }

//    /**
//     * http://www.cnblogs.com/zhwbqd/p/3757060.html
//     * 参考DynamicDataSource.java的注释。
//     * 一句话，jetty的工作队列会重用处理线程, 导致threadLocal中的值被重用，因此需要清除。
//     */
//    @AfterReturning("execution(* com.yingjun.ssm.dao..*.*(..))")
//    public void afterReturningMethod(){
//        DynamicDataSourceHolder.clear();
//    }
}
