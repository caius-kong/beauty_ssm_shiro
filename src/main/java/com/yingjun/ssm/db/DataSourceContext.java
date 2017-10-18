package com.yingjun.ssm.db;

/**
 * 数据源key + 读写模式
 */
public class DataSourceContext {

    /** dataSource key */
    private String  dsKey;

    /** 是否可写 */
    private Boolean isWrite;

    public Boolean getIsWrite() {
        return isWrite;
    }

    public void setIsWrite(Boolean isWrite) {
        this.isWrite = isWrite;
    }

    public String getDsKey() {
        return dsKey;
    }

    public void setDsKey(String dsKey) {
        this.dsKey = dsKey;
    }

    @Override
    public String toString() {
        return "DataSourceContext{" +
                "dsKey='" + dsKey + '\'' +
                ", isWrite=" + isWrite +
                '}';
    }
}