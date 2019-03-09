package org.aidan.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.aidan.entity.User;
import org.aidan.service.UserService;
import org.aidan.vo.OptResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/login")
    public OptResult login() {
        OptResult optResult = new OptResult();

        User u = new User();
        u.setName("aidan");
        EntityWrapper<User> ew = new EntityWrapper<>(u);
        User user = userService.selectOne(ew);
        optResult.setData(user);
        return optResult.success();
    }
}
