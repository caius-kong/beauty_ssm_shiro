package com.yingjun.ssm.dao;

import com.yingjun.ssm.entity.UrlFilter;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface UrlFilterDao {

    int createUrlFilter(UrlFilter urlFilter);
    UrlFilter updateUrlFilter(UrlFilter urlFilter);
    void deleteUrlFilter(Long urlFilterId);

    UrlFilter findOne(Long urlFilterId);
    List<UrlFilter> findAll();
}
