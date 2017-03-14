package com.yingjun.ssm.dao;


import com.yingjun.ssm.aop.DataSource;
import com.yingjun.ssm.dto.UserDto;
import com.yingjun.ssm.entity.User;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface UserDao {
    @DataSource("master")
    void createUser(UserDto userDto);
    @DataSource("master")
    void updateUser(UserDto userDto);
    @DataSource("master")
    void deleteUser(Long userId);

    @DataSource("slave1")
    User findOne(Long userId);

    @DataSource("slave1")
    List<User> findAll();

    @DataSource("slave2")
    User findByUsername(String username);

    @DataSource("slave2")
    List<User> findUsers(User user);
}
