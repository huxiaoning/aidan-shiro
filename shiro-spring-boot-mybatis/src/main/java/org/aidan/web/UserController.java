package org.aidan.web;

import org.aidan.entity.User;
import org.aidan.service.UserService;
import org.aidan.vo.OptResult;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @author aidan
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public OptResult add(@RequestBody User user) {
        OptResult optResult = new OptResult();
        String salt = user.getSalt();
        String password = user.getPassword();

        Md5Hash md5Hash = new Md5Hash(password, salt);
        user.setPassword(md5Hash.toString());
        userService.insert(user);
        return optResult.success();
    }
}
