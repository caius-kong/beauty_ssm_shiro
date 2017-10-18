package com.yingjun.ssm.db;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kongyunhui on 2017/3/3.
 *
 * 该类充当了DataSource的路由中介, 能有在运行时, 根据某种key值来动态切换到真正的DataSource上。
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicDataSource.class);

    private static final int SLAVE1_WEIGHT = 1; // 自定义权重
    private static final int SLAVE2_WEIGHT = 1;
    private List<String> writeDataSourceKeyList; // 写数据源列表，这里保存key 根据权重会有相应的数量
    private List<String> readDataSourceKeyList; // 读数据源列表，这里保存key 根据权重会有相应的数量

    /**
     * 事务作用在service层：开启事务-设置dsKey-保存本次连接-dao方法-获取连接 -> 空，则调用DynamicDataSource去获取数据源；非空，则直接返回
     *
     * 也就是说：一旦使用事务(DataSourceTransactionManager)，Spring会默认马上去取得数据源，并且把它缓存到DataSourceTransactionObject对象中，
     * 用于后续的commit, rollback等事务操作，所以我们后续尽管切换AbstractRoutingDataSource, 对事务已然无效。
     *
     * 调用链：...
     *        -> AbstractPlatformTransactionManager.getTransaction() -> DataSourceTransactionManager.doBegin()
     *        -> AbstractRoutingDataSource.determineTargetDataSource()
     *        -> DataSourceTransactionManager.setTransactional()[将连接设置到TransactionUtils的threadLocal中]-
     *        -> 开始dao层调用(DefaultSqlSession -> getConnection() -> 空，则AbstractRoutingDataSource.getConnection())；非空，则直接使用
     *        ...
     *
     * 小结：一个xxServiceImpl调用，执行一次determineCurrentLookupKey。如果一个事务中出现了读改写，就需要切换源。
     *
     * 决定当前查找的数据源的key
     * @return key
     */
    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("--->determineCurrentLookupKey");
        DataSourceContext dsContent = DynamicDataSourceHolder.getDataSourceContent();
        // 已经设置过数据源key，直接返回（确保除了读改写时切换一次，其他情况都保持相同的源）
        // 这里为什么使用ds_key来判断是否需要切换？首次创建 || 读->写时重建，ds_key都是空。
        if(StringUtils.isNotBlank(dsContent.getDsKey())){
            System.out.println("--->3.已经设置过dsKey="+dsContent.getDsKey()+",直接返回（除了读改写时切换，其他情况都保持相同的源）");
            return dsContent.getDsKey();
        }
        // 根据读写模式，随机的从key列表中取一个使用
        LOG.info("--->3.根据读写模式，随机的从key列表中取一个使用");
        String dsKey="";
        if(dsContent.getIsWrite()){
            dsKey = writeDataSourceKeyList.get(RandomUtils.nextInt(0, writeDataSourceKeyList.size()));
        } else {
            dsKey = readDataSourceKeyList.get(RandomUtils.nextInt(0, readDataSourceKeyList.size()));
        }
        dsContent.setDsKey(dsKey);
        LOG.info("--->3.当前操作使用数据源:{}", dsContent.getDsKey());
        return dsContent.getDsKey();
    }

    /**
     * bean初始化时调用，解析数据源。主要用于动态修改targetDataSource、defaultTargetDataSource。
     * 注：最后调用super.afterPropertiesSet()来通知spring容器
     */
    @Override
    public void afterPropertiesSet() {
        initReadDataSourceKeyList();
        initWriteDataSourceKeyList();
        super.afterPropertiesSet();
        LOG.info("--->初始化动态数据源完成");
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

    private void initWriteDataSourceKeyList(){
        writeDataSourceKeyList = new ArrayList<>();
        writeDataSourceKeyList.add("master");
    }
}
