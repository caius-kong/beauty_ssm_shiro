package com.yingjun.ssm.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by kongyunhui on 2017/3/3.
 *
 * 该类充当了DataSource的路由中介, 能有在运行时, 根据某种key值来动态切换到真正的DataSource上。
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 决定当前查找的数据源的key
     * @return key
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSource();
    }
}
