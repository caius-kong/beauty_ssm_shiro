package com.yingjun.ssm.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by kongyunhui on 2017/3/3.
 *
 * 观察AbstractRoutingDataSource中的determineTargetDataSource()方法，可以看出：
 * spring把所有的数据源都存放在了一个map中，这个方法返回一个key告诉spring用这个key从map中去取
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSource();
    }
}
