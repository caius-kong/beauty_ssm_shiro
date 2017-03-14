package com.yingjun.ssm.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login")
    public String showLoginForm(HttpServletRequest req, Model model) {
        System.out.println("--->跳转登录界面...");
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        String error = null;
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        return "login";
    }

//    private void ensureUserIsLoggedOut() {
//        try {
//            // Get the user if one is logged in.
//            Subject currentUser = SecurityUtils.getSubject();
//            if (currentUser == null)
//                return;
//
//            // Log the user out and kill their session if possible.
//            currentUser.logout();
//            Session session = currentUser.getSession(false);
//            if (session == null)
//                return;
//
//            session.stop();
//        } catch (Exception e) {
//            // Ignore all errors, as we're trying to silently
//            // log the user out.
//        }
//    }
}
