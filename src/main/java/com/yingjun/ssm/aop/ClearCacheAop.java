package com.yingjun.ssm.aop;

import com.google.common.base.Joiner;
import com.yingjun.ssm.cache.RedisCache;
import com.yingjun.ssm.utils.Helper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 采用AOP的方式处理: XXService关于数据更新（增删改）时，缓存的清理。
 */
@Component
@Aspect
public class ClearCacheAop {
    private static final Logger LOG = LoggerFactory.getLogger(ClearCacheAop.class);

    @Autowired
    private RedisCache cache;

    // 声明切入点Pattern.COMMENTS(?x)
    @Pointcut("execution(* com.yingjun.ssm.dao..*.*(..))")
    public void clearCachePointcut() {
    }

    @Before("clearCachePointcut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("--->clearCachePointcut start...");
//        String className = jp.getTarget().getClass().getName(); // 这句话得到的是代理类对象
//        String fullMethod = joinPoint.getSignature().toString().split("\\s")[1]; // 这样才能拿到真实类对象
//        String className = fullMethod.substring(0, fullMethod.lastIndexOf("."));
        String methodName = joinPoint.getSignature().getName();

        String fullMethod = Helper.getFullMethod(joinPoint);
        LOG.info("before " + fullMethod + " invoking!");

        // 只要methodName涉及cud，就需要clearCache
        Pattern pattern = Pattern.compile("^[save|add|create|insert|update|merge|del|remove|put].*");
        Matcher matcher = pattern.matcher(methodName);
        if (matcher.matches()) {
            // 此时缓存中的数据不是最新的，需要对缓存进行清理（具体的缓存策略还是要根据具体需求制定）
            String cache_key = RedisCache.CAHCENAME + "|*";
            cache.deleteCacheWithPattern(cache_key);
            LOG.info("delete cache with key [{}]", cache_key);
        }
    }
}
