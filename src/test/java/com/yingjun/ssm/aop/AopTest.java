package com.yingjun.ssm.aop;


import com.yingjun.ssm.dao.UserDao;
import com.yingjun.ssm.entity.User;
import com.yingjun.ssm.service.UserService;
import com.yingjun.ssm.web.IndexController;
import com.yingjun.ssm.web.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class AopTest {

    @Autowired
    private UserService userService;

    @Autowired
    private IndexController indexController;

	@Test
	public void testServiceAop() {
		List<User> all = userService.findAll();
		System.out.println(all);
	}
}
