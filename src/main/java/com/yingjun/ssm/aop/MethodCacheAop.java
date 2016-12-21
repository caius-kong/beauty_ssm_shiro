package com.yingjun.ssm.aop;

import com.google.common.base.Joiner;
import com.yingjun.ssm.cache.RedisCache;
import com.yingjun.ssm.dto.BaseResult;
import com.yingjun.ssm.entity.User;
import com.yingjun.ssm.utils.Helper;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.net.util.IPAddressUtil;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 方法级缓存拦截器
 * <p>
 */
@Aspect
@Component
public class MethodCacheAop {

    private static final Logger LOG = LoggerFactory.getLogger(MethodCacheAop.class);

    @Autowired
    private RedisCache cache;

    /**
     * 搭配 AspectJ 指示器“@annotation()”可以使本切面成为某个注解的代理实现
     */
    @Around("@annotation(com.yingjun.ssm.aop.MethodCache)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("--->MethodCacheAop start...");

        MethodCache annotation = getAnnotation(joinPoint, MethodCache.class);
        int isCollection = annotation.isCollection();
        Class clz = annotation.clz();
        int expire = annotation.expire();

        String fullMethod = Helper.getFullMethod(joinPoint);
        LOG.info("before " + fullMethod + " invoking!");

        // 缓存拦截器。命中：直接返回缓存结果，未命中：调用目标函数，通过目标函数计算实际值，并缓存
        String cache_key = Joiner.on("|").join(RedisCache.CAHCENAME, fullMethod);
        if (isCollection == 0) {
            Object cache_value = cache.getCache(cache_key, clz);
            if (cache_value != null) {
                LOG.info("cache hit，key [{}]", cache_key);
                return cache_value;
            } else {
                LOG.info("cache miss，key [{}]", cache_key);
                Object result = joinPoint.proceed(joinPoint.getArgs());
                if (result == null) {
                    LOG.error("fail to get data from source，key [{}]", cache_key);
                } else {
                    cache.putCacheWithExpireTime(cache_key, result, expire);
                    LOG.info("put cache with key[{}]", cache_key);
                }
                return result;
            }
        } else {
            List cache_value = cache.getListCache(cache_key, clz);
            if (cache_value != null) {
                LOG.info("cache hit，key [{}]", cache_key);
                return cache_value;
            } else {
                LOG.info("cache miss，key [{}]", cache_key);
                Object result = joinPoint.proceed(joinPoint.getArgs());
                if (result == null || ((List)result).size()==0) {
                    LOG.error("fail to get data from source，key [{}], the result:" + result, cache_key);
                } else {
                    cache.putListCacheWithExpireTime(cache_key, (List)result, expire);
                    LOG.info("put cache with key[{}]", cache_key);
                }
                return result;
            }
        }
    }

    /**
     * 获得@MethodCache注解的annotation，目的是获取属性expire
     *
     * @return
     */
    private <T extends Annotation> T getAnnotation(ProceedingJoinPoint jp, Class<T> clz) {
        MethodSignature sign = (MethodSignature) jp.getSignature();
        Method method = sign.getMethod();
        return method.getAnnotation(clz);
    }
}