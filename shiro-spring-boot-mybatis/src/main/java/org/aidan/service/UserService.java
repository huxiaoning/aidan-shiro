package org.aidan.service;

import org.aidan.entity.User;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author Aidan
 * @since 2019-03-09
 */
public interface UserService extends IService<User> {

    User getUserByUserName(String userName);

}
