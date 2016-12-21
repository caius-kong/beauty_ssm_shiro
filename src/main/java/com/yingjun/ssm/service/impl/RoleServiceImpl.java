package com.yingjun.ssm.service.impl;

import com.yingjun.ssm.aop.MethodCache;
import com.yingjun.ssm.cache.RedisCache;
import com.yingjun.ssm.dao.RoleDao;
import com.yingjun.ssm.entity.Role;
import com.yingjun.ssm.service.ResourceService;
import com.yingjun.ssm.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RedisCache cache;

    public void createRole(Role role) {
        roleDao.createRole(role);
    }

    public void updateRole(Role role) {
        roleDao.updateRole(role);
    }

    public void deleteRole(Long roleId) {
        roleDao.deleteRole(roleId);
    }

    @Override
    @MethodCache(clz = Role.class)
    public Role findOne(Long roleId) {
        return roleDao.findOne(roleId);
    }

    @Override
    @MethodCache(isCollection = 1, clz = Role.class)
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    @MethodCache(isCollection = 1, clz = String.class)
    public List<String> findRoles(List<Long> roleIds) {
        List<String> list = new ArrayList<String>();
        for (Long roleId : roleIds) {
            Role role = findOne(roleId);
            if (role != null) {
                list.add(role.getRole());
            }
        }
        return list;
    }

    @Override
    @MethodCache(isCollection = 1, clz = String.class)
    public List<String> findPermissions(List<Long> roleIds) {
        List<Long> resourceIds = new ArrayList<Long>();
        for (Long roleId : roleIds) {
            Role role = findOne(roleId);
            if (role != null) {
                resourceIds.addAll(role.getResourceIds());
            }
        }
        return resourceService.findPermissions(resourceIds);
    }
}
