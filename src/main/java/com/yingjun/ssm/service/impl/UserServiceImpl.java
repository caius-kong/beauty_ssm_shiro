package com.yingjun.ssm.service.impl;

import com.yingjun.ssm.aop.MethodCache;
import com.yingjun.ssm.cache.RedisCache;
import com.yingjun.ssm.dao.UserDao;
import com.yingjun.ssm.dto.UserDto;
import com.yingjun.ssm.entity.Role;
import com.yingjun.ssm.entity.User;
import com.yingjun.ssm.enums.ResultEnum;
import com.yingjun.ssm.exception.BizException;
import com.yingjun.ssm.service.PasswordHelper;
import com.yingjun.ssm.service.RoleService;
import com.yingjun.ssm.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.SourceExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisCache cache;

    /**
     * 创建用户
     *
     * @param user
     */
    public void createUser(User user) {
        //加密密码
        passwordHelper.encryptPassword(user);
        UserDto userDto = new UserDto();
        try {
            BeanUtils.copyProperties(userDto, user);
            userDto.setRole_ids(user.getRoleIdsStr());
            userDao.createUser(userDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUsers(List<User> list) {
        for (User user : list) {
            createUser(user);
        }
    }

    @Override
    public void updateUser(User user) {
        UserDto userDto = new UserDto();
        try {
            BeanUtils.copyProperties(userDto, user);
            userDto.setRole_ids(user.getRoleIdsStr());
            userDao.updateUser(userDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(Long userId) {
        userDao.deleteUser(userId);
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword) {
        User user = userDao.findOne(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        updateUser(user);
    }

    @Override
    @MethodCache(clz = User.class)
    public User findOne(Long userId) {
        return userDao.findOne(userId);
    }

    @Override
    @MethodCache(isCollection = 1, clz = User.class, expire = 300) // 触发缓存aop，返回结果是集合，集合内对象类型是User，缓存过期时间5分钟
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @MethodCache(clz = User.class)
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @MethodCache(isCollection = 1, clz = String.class)
    public List<String> findRoles(String username) {
        User user = findByUsername(username);
        if (user == null) {
            return Collections.EMPTY_LIST;
        }
        return roleService.findRoles(user.getRoleIds()); // 目的在于：程序健壮性（user.roleIds可不算数，需要去role中验证/获取）
    }

    @Override
    @MethodCache(isCollection = 1, clz = String.class)
    public List<String> findPermissions(String username) {
        User user = findByUsername(username);
        if (user == null) {
            return Collections.EMPTY_LIST;
        }
        return roleService.findPermissions(user.getRoleIds());
    }

    @Override
    @MethodCache(isCollection = 1, clz = User.class)
    public List<User> findUsers(String idOrUsername){
        // 参数处理 （idOrUsername）
        User user = new User();
        if (StringUtils.isNumeric(idOrUsername)) {
            user.setId(Long.parseLong(idOrUsername));
        } else {
            user.setUsername(idOrUsername);
        }

        // 业务处理
        List<User> users = userDao.findUsers(user);
        if (users == null || users.size() == 0) {
            throw new BizException(ResultEnum.DB_SELECTONE_IS_NULL.getMsg());
        }
        return users;
    }
}
