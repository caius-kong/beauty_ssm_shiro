package com.yingjun.ssm.service.impl;

import com.yingjun.ssm.aop.MethodCache;
import com.yingjun.ssm.cache.RedisCache;
import com.yingjun.ssm.dao.ResourceDao;
import com.yingjun.ssm.entity.Resource;
import com.yingjun.ssm.service.ResourceService;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private RedisCache cache;

    @Override
    public void createResource(Resource resource) {
        resourceDao.createResource(resource);
    }

    @Override
    public void updateResource(Resource resource) {
        resourceDao.updateResource(resource);
    }

    @Override
    public void deleteResource(Long resourceId) {
        resourceDao.deleteResource(resourceId);
    }

    @Override
    @MethodCache(clz = Resource.class)
    public Resource findOne(Long resourceId) {
        return resourceDao.findOne(resourceId);
    }

    @Override
    @MethodCache(isCollection = 1, clz = Resource.class)
    public List<Resource> findAll() {
        return resourceDao.findAll();
    }

    @Override
    @MethodCache(isCollection = 1, clz = String.class)
    public List<String> findPermissions(List<Long> resourceIds) {
        List<String> permissions = new ArrayList<String>();
        for (Long resourceId : resourceIds) {
            Resource resource = findOne(resourceId);
            if (resource != null && !StringUtils.isEmpty(resource.getPermission())) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }

    @Override
    @MethodCache(isCollection = 1, clz = Resource.class)
    public List<Resource> findMenus(List<String> permissions) {
        List<Resource> allResources = findAll();
        List<Resource> menus = new ArrayList<Resource>();
        for (Resource resource : allResources) {
            if (resource.isRootNode()) {
                continue;
            }
            if (resource.getType() != Resource.ResourceType.menu) {
                continue;
            }
            if (!hasPermission(permissions, resource)) {
                continue;
            }
            menus.add(resource);
        }
        return menus;
    }

    private boolean hasPermission(List<String> permissions, Resource resource) {
        if (StringUtils.isEmpty(resource.getPermission())) {
            return true;
        }
        for (String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(resource.getPermission());
            if (p1.implies(p2) || p2.implies(p1)) {
                return true;
            }
        }
        return false;
    }
}
