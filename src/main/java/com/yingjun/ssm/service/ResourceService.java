package com.yingjun.ssm.service;

import com.yingjun.ssm.entity.Resource;

import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface ResourceService {


    public void createResource(Resource resource);
    public void updateResource(Resource resource);
    public void deleteResource(Long resourceId);

    Resource findOne(Long resourceId);
    List<Resource> findAll();

    /**
     * 得到资源对应的权限字符串
     * @param resourceIds
     * @return
     */
    List<String> findPermissions(List<Long> resourceIds);

    /**
     * 根据用户权限得到菜单
     * @param permissions
     * @return
     */
    List<Resource> findMenus(List<String> permissions);
}
