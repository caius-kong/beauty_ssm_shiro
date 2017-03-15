package com.yingjun.ssm.dao;

import com.yingjun.ssm.entity.UrlFilter;
import java.util.List;

public interface UrlFilterDao {

    int createUrlFilter(UrlFilter urlFilter);
    UrlFilter updateUrlFilter(UrlFilter urlFilter);
    void deleteUrlFilter(Long urlFilterId);

    UrlFilter findOne(Long urlFilterId);
    List<UrlFilter> findAll();
}
