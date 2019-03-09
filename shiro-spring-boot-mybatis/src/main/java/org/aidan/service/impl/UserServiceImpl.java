package org.aidan.service.impl;

import org.aidan.entity.User;
import org.aidan.mapper.UserDao;
import org.aidan.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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

}
