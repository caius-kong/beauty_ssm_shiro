package com.yingjun.ssm.dao;


import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import org.apache.shiro.codec.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.yingjun.ssm.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

	@Test
	public void testFindAll() {
		List<User> list=userDao.findAll();
		for (User user : list) {
			System.out.println(user);
		}
	}

	@Test
	public void testFindByUsername() {
		User user = userDao.findByUsername("admin");
		System.out.println(user);
    }

	@Test
	public void testFindUsers() {
		User user = new User();
		user.setUsername("a");
		List<User> users = userDao.findUsers(user);
		System.out.println(users);
	}
}
