package com.yingjun.ssm.dao;

import com.yingjun.ssm.aop.DataSource;
import com.yingjun.ssm.dto.UserDto;
import com.yingjun.ssm.entity.User;
import java.util.List;

public interface UserDao {
    @DataSource("master")
    void createUser(UserDto userDto);
    @DataSource("master")
    void updateUser(UserDto userDto);
    @DataSource("master")
    void deleteUser(Long userId);

    @DataSource("slave")
    User findOne(Long userId);

    @DataSource("slave")
    List<User> findAll();

    @DataSource("slave")
    User findByUsername(String username);

    @DataSource("slave")
    List<User> findUsers(User user);
}
