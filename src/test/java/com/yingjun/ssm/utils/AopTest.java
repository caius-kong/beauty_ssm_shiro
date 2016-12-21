package com.yingjun.ssm.utils;

import com.yingjun.ssm.dao.UserDao;
import com.yingjun.ssm.entity.User;
import com.yingjun.ssm.service.UserService;
import com.yingjun.ssm.service.impl.UserServiceImpl;
import com.yingjun.ssm.web.IndexController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by kongyunhui on 2016/11/29.
 */
public class AopTest {
    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-web.xml", "classpath:spring/spring-service.xml", "classpath:spring/spring-redis.xml", "classpath:spring/spring-dao.xml","classpath:spring/spring-quartz.xml");
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-web.xml", "classpath:spring/spring-service.xml", "classpath:spring/spring-redis.xml", "classpath:spring/spring-dao.xml","classpath:spring/spring-shiro.xml","classpath:spring/spring-cache.xml");
        UserService userService = context.getBean("userService", UserServiceImpl.class);
        System.out.println(userService.findAll());

//        IndexController indexController = context.getBean("IndexController", IndexController.class);
//        indexController.welcome();
        context.close();
    }
}
