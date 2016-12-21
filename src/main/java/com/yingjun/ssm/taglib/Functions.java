package com.yingjun.ssm.taglib;

import com.yingjun.ssm.entity.Resource;
import com.yingjun.ssm.entity.Role;
import com.yingjun.ssm.service.ResourceService;
import com.yingjun.ssm.service.RoleService;
import com.yingjun.ssm.spring.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.Collection;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 *
 * 由于方法必须是static(1)，而通过Spring托管的对象不是static(2)，因此通过SpringUtils来获取bean
 * (1). tld文件引用static方法
 * (2). 静态方法中不能使用非静态属性
 */
public class Functions {
    public static boolean in(Iterable iterable, Object element) {
        if(iterable == null) {
            return false;
        }
        return CollectionUtils.contains(iterable.iterator(), element);
    }

    public static String roleName(Long roleId) {
        Role role = getRoleService().findOne(roleId);
        if(role == null) {
            return "";
        }
        return role.getDescription();
    }

    public static String roleNames(Collection<Long> roleIds) {
        if(CollectionUtils.isEmpty(roleIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for(Long roleId : roleIds) {
            Role role = getRoleService().findOne(roleId);
            if(role == null) {
                return "";
            }
            s.append(role.getDescription());
            s.append(",");
        }

        if(s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }
    public static String resourceName(Long resourceId) {
        Resource resource = getResourceService().findOne(resourceId);
        if(resource == null) {
            return "";
        }
        return resource.getName();
    }
    public static String resourceNames(Collection<Long> resourceIds) {
        if(CollectionUtils.isEmpty(resourceIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for(Long resourceId : resourceIds) {
            Resource resource = getResourceService().findOne(resourceId);
            if(resource == null) {
                return "";
            }
            s.append(resource.getName());
            s.append(",");
        }

        if(s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }

    /**
     * 通过ConfigurableListableBeanFactory获得bean实例
     */
    private static RoleService roleService;
    private static ResourceService resourceService;

    public static RoleService getRoleService() {
        if(roleService == null) {
            roleService = SpringUtils.getBean(RoleService.class);
        }
        return roleService;
    }

    public static ResourceService getResourceService() {
        if(resourceService == null) {
            resourceService = SpringUtils.getBean(ResourceService.class);
        }
        return resourceService;
    }
}

