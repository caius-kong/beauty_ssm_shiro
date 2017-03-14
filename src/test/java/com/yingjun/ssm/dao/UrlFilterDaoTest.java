package com.yingjun.ssm.dao;


import com.yingjun.ssm.entity.UrlFilter;
import com.yingjun.ssm.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class UrlFilterDaoTest {

    @Autowired
    private UrlFilterDao urlFilterDao;

	@Test
	public void testInsert(){
		UrlFilter urlFilter = new UrlFilter();
		urlFilter.setName("用户修改");
		urlFilter.setUrl("/user/update");
        urlFilter.setRoles("1");
        System.out.println("-insert before-->" + urlFilter);
        urlFilterDao.createUrlFilter(urlFilter); // 首先声明，其实是返回主键，但是有了主键的类对象不就是我们需要的么！
        System.out.println("-insert after-->" + urlFilter);
    }

	@Test
	public void testFindAll() {
		List<UrlFilter> list=urlFilterDao.findAll();
		for (UrlFilter urlFilter : list) {
			System.out.println(urlFilter);
		}
	}
}
