package com.yingjun.ssm.service;

import com.google.common.collect.Lists;
import com.yingjun.ssm.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kongyunhui on 2016/12/5.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class ServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private UrlFilterService urlFilterService;
    @Autowired
    private ShiroFilerChainManager shiroFilerChainManager;

    /**
     * 如果事务生效，那么此次操作应该是一条语句都没有插入
     */
    @Test
    public void test1(){
        User user1 = new User("kong", "123", "123");
        User user2 = new User("kong", "123", "123");
        List<User> users = Lists.newArrayList(user1, user2);
        userService.createUsers(users);
    }

    @Test
    public void test2(){
        User user1 = new User("kong111111111", "123", "123");
        userService.createUser(user1);
    }

    /**
     * before cache: 701ms (和findRoleIds慢在它不仅获得了user.roleIds，而且根据RoleIds去role中对比id确认了)
     * after  cache: 290ms
     */
    @Test
    public void findRoles(){
        List<String> admin = userService.findRoles("admin");
        System.out.println(admin);
    }

    /**
     * before cache: 580ms
     * after  cache: 290ms
     */
    @Test
    public void findRoleIds(){
//        List<String> admin = userService.findRoleIds("admin");
//        System.out.println(admin);
    }
}
