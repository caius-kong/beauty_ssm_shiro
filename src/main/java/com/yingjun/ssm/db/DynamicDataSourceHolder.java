package com.yingjun.ssm.db;

/**
 * Created by kongyunhui on 2017/3/3.
 */
public class DynamicDataSourceHolder {
    // 使用多线程维护变量，ThreadLocal为每个"使用该变量的线程"提供独立的变量副本，每个线程.变量互不干扰！
    // 因此putXX、getXX的都是每个线程的局部变量。
    private static final ThreadLocal<String> holder = new ThreadLocal<String>();

    public static void putDataSource(String value){
        holder.set(value);
    }

    public static String getDataSource(){
        return holder.get();
    }
}
