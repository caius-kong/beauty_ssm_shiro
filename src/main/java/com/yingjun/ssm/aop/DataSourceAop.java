package com.yingjun.ssm.aop;

import com.yingjun.ssm.db.DynamicDataSource;
import com.yingjun.ssm.db.DynamicDataSourceHolder;
import com.yingjun.ssm.utils.Helper;
import org.apache.commons.lang3.RandomUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kongyunhui on 2017/3/3.
 *
 * 拦截dao层的方法，通过获取@DataSource来动态切换数据源
 *
 * 问题：数据源需要同时使用一个事务？
 */
@Aspect
@Component
public class DataSourceAop {
    private static final Logger LOG = LoggerFactory.getLogger(DataSourceAop.class);
    private static final int SLAVE1_WEIGHT = 1; // 自定义权重
    private static final int SLAVE2_WEIGHT = 1;
    private static List<String> readDataSourceKeyList;

    // @anontation不支持拦截接口方法，因此此处必须使用execution
    @Before("execution(* com.yingjun.ssm.dao..*.*(..))")
    public void before(JoinPoint point){
        System.out.println("--->DataSourceAop start...");
        // 如果本次请求已经设置过写库，则直接返回 (解决"读已知所写"问题。就是说一个请求写入数据A后，想读取A确认一下，此时你切换数据源，可能导致读取不到！软事务中会出现的问题。)
        if("master".equals(DynamicDataSourceHolder.getDataSource())){
            LOG.info("上一个dao使用的源是master，直接返回，解决'读已知所写'问题");
            return;
        }

        // 遍历所有dao层，因此存在没有@DataSource的方法，需要空指针处理
        DataSource annotation = getAnnotation(point, DataSource.class);
        if(annotation != null) {
            String ds_key = annotation.value();
            // 根据权重随机slave
            if(readDataSourceKeyList == null){
                initReadDataSourceKeyList();
            }
            LOG.info("当前应用配置的readDataSourceKeyList: [{}]", readDataSourceKeyList);
            if("slave".equals(ds_key)){
                ds_key = readDataSourceKeyList.get(RandomUtils.nextInt(0, readDataSourceKeyList.size()));
            }
            DynamicDataSourceHolder.putDataSource(ds_key);
            LOG.info(Helper.getFullMethod(point) + " set db as [{}]", ds_key);
        }
    }

    /**
     * 按照权重不同，加入相应的ds_key
     */
    private void initReadDataSourceKeyList(){
        readDataSourceKeyList = new ArrayList<>();
        for(int i=0; i < SLAVE1_WEIGHT; i++) {
            readDataSourceKeyList.add("slave1");
        }
        for(int i=0; i < SLAVE2_WEIGHT; i++) {
            readDataSourceKeyList.add("slave2");
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
