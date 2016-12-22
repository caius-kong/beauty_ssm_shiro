package com.yingjun.ssm.service;

import com.yingjun.ssm.entity.UrlFilter;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface UrlFilterService {

    /**
     * 添加urlFilter，注意必须立马更新FilterChains (因此数据库直接添加urlFilter记录是不会立马更新FilterChains，那就需要重启服务了)
     * @param urlFilter
     * @return
     */
    public UrlFilter createUrlFilter(UrlFilter urlFilter);
    public UrlFilter updateUrlFilter(UrlFilter urlFilter);
    public void deleteUrlFilter(Long urlFilterId);

    public UrlFilter findOne(Long urlFilterId);
    public List<UrlFilter> findAll();
}
