package org.aidan.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.aidan.entity.User;
import org.aidan.mapper.UserDao;
import org.aidan.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author Aidan
 * @since 2019-03-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public User getUserByUserName(String userName) {
        User u = new User();
        u.setName(userName);
        EntityWrapper<User> ew = new EntityWrapper<>(u);
        return selectOne(ew);
    }
}
