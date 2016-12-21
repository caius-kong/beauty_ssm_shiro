package com.yingjun.ssm.utils;

import com.google.common.base.Joiner;
import com.yingjun.ssm.cache.RedisCache;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.SourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kongyunhui on 2016/12/12.
 */
public class Helper {
    /**
     * 根据类名、方法名和参数值获取唯一的缓存键
     *
     * @return 格式为 "包名.类名.方法名.参数类型.参数值"，类似 "your.package.SomeService.getById(int).123"
     */
    public static String getFullMethod(JoinPoint joinPoint){
        return String.format(
                "%s.%s",
                joinPoint.getSignature().toString().split("\\s")[1],
                StringUtils.join(joinPoint.getArgs(), ",")
        );
    }
}
