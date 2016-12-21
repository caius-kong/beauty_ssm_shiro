package com.yingjun.ssm.utils;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by kongyunhui on 16/10/28.
 */
public class ShiroTest {
    @Test
    public void testHelloWorld(){
        login("classpath:shiro/shiro.ini");
        Subject subject = SecurityUtils.getSubject();
        Assert.assertEquals(true, subject.isAuthenticated()); // 断言用户是否已经登录
    }

    @Test
    public void testCustomRealm(){
        login("classpath:shiro/shiro_multi_realm.ini");
        Subject subject = SecurityUtils.getSubject();
        Assert.assertEquals(true, subject.isAuthenticated()); // 断言用户是否已经登录
    }

    @Test
    public void testJDBCRealm(){
        // 虽然没有指定users表,但是貌似默认会去配置的数据库下找users表 (名字写死了??)
        login("classpath:shiro/shiro_jdbc_realm.ini");
        Subject subject = SecurityUtils.getSubject();
        System.out.println("-委托人-->" + subject.getPrincipal());
        Assert.assertEquals(true, subject.isAuthenticated()); // 断言用户是否已经登录
    }

    /**
     * IniSecurityManagerFactory(加载ini) --> SecurityManager --> SecurityUtils
     *  --> Subject --> subject.login(token)
     */
    private void login(String configFile) {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);

        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("wang", "123");
        subject.login(token);

        //4、退出
//        subject.logout();

    }

    @After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }
}
