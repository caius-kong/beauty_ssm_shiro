package com.yingjun.ssm.dao;


import com.yingjun.ssm.dto.UserDto;
import com.yingjun.ssm.entity.User;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface UserDao {

    public void createUser(UserDto userDto);
    public void updateUser(UserDto userDto);
    public void deleteUser(Long userId);

    User findOne(Long userId);

    List<User> findAll();

    User findByUsername(String username);

    List<User> findUsers(User user);
}
