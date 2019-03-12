package org.aidan.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.aidan.entity.User;
import org.aidan.service.UserService;
import org.aidan.shiro.enums.LoginType;
import org.aidan.shiro.token.CustomizedToken;
import org.aidan.vo.LoginVo;
import org.aidan.vo.OptResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录控制器
 *
 * @author aidan
 */
@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public OptResult login(@RequestBody LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        OptResult optResult = new OptResult();

        Subject subject = SecurityUtils.getSubject();

        CustomizedToken token = new CustomizedToken(loginVo.getAccount(), loginVo.getPassword(), LoginType.ADMIN.toString());
        subject.login(token);

        boolean authenticated = subject.isAuthenticated();
        if (!authenticated) {
            return optResult.fail("登录失败");
        }
        User u = new User();
        u.setName(loginVo.getAccount());
        EntityWrapper<User> ew = new EntityWrapper<>(u);
        User user = userService.selectOne(ew);
        optResult.setData(user);


        Session session = subject.getSession();
        System.out.println(session.getId());

        return optResult.success();
    }


}
