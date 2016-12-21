package com.yingjun.ssm.web;

import com.yingjun.ssm.aop.MethodCache;
import com.yingjun.ssm.entity.Resource;
import com.yingjun.ssm.entity.User;
import com.yingjun.ssm.service.ResourceService;
import com.yingjun.ssm.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Controller
public class IndexController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(Model model) {
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        List<String> permissions = userService.findPermissions(username); // username -> user.roleIds -> role.resourceIds -> resource.permission
        List<Resource> menus = resourceService.findMenus(permissions); // resource.permission -> resource.name (type=menu and parentId!=0)
        model.addAttribute("menus", menus);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

}
