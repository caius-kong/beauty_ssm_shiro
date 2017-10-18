package com.yingjun.ssm.db;

/**
 * Created by kongyunhui on 2017/3/3.
 * <p>
 * 在一般情况下，从接收请求到返回响应所经过的"所有程序调用"都同属于一个线程。
 * 因此我们希望本线程内共享一个数据源，各个线程之间互不干扰 ==> ThreadLocal保存变量
 */
public class DynamicDataSourceHolder {
    /**
     * 使用多线程维护变量，ThreadLocal为每个"使用该变量的线程"提供独立的变量副本，每个线程.变量互不干扰！
     */
    private static final ThreadLocal<DataSourceContext> DATASOURCE_LOCAL = new ThreadLocal<DataSourceContext>();

    /**
     * 设置数据源读写模式
     *
     * @param isWrite
     */
    public static void setIsWrite(boolean isWrite) {
        DataSourceContext dsContext = DATASOURCE_LOCAL.get();
        System.out.println("--->执行2.0setIsWrite:" + dsContext);
        // 如果已经持有且可写，直接返回
        // 这个'且可写'大有文章！读->写，需要切换；写->读，不切换！
        // 也就是说，本次线程切换到写库，那么接下来的本次线程内，一直使用写库！()
        // 解决'读已知所写问题'。本次请求写入数据A后，立马读取数据A，此时必须走写库，不然可能存在数据不同步问题。
        if (dsContext != null && dsContext.getIsWrite()) {
            System.out.println("--->2.写库直接返回");
            return;
        }
        // 第一次，需要创建；写，需要切换(即重建)。注意：重建新对象，key为空 ==> 进入DynamicDataSource动态数据源
        if (dsContext == null || isWrite) {
            dsContext = new DataSourceContext();
            dsContext.setIsWrite(isWrite);
            DATASOURCE_LOCAL.set(dsContext);
            System.out.println("--->2.创建" + dsContext);
        }
    }

    /**
     * 获取DataSourceContext变量
     *
     * @return
     */
    public static DataSourceContext getDataSourceContent() {
        return DATASOURCE_LOCAL.get();
    }

    /**
     * 清空
     */
    public static void clear(){
        DATASOURCE_LOCAL.remove();
    }
}
