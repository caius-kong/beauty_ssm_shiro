package com.yingjun.ssm.dao;

import com.yingjun.ssm.dto.UserDto;
import com.yingjun.ssm.entity.User;
import java.util.List;

public interface UserDao {
    void createUser(UserDto userDto);
    void updateUser(UserDto userDto);
    void deleteUser(Long userId);

    User findOne(Long userId);
    List<User> findAll();
    User findByUsername(String username);
    List<User> findUsers(User user);
}
