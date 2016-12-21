package com.yingjun.ssm.dao;


import com.yingjun.ssm.entity.Role;
import com.yingjun.ssm.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

	@Test
	public void testFindAll() {
		List<Role> list=roleDao.findAll();
		System.out.println(list);
	}

	@Test
	public void testFindOne(){
		Role one = roleDao.findOne(new Long(1l));
		System.out.println(one);
	}
}
