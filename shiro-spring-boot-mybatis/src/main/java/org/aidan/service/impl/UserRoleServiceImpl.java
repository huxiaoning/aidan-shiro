package org.aidan.service.impl;

import org.aidan.entity.UserRole;
import org.aidan.mapper.UserRoleDao;
import org.aidan.service.UserRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色 服务实现类
 * </p>
 *
 * @author Aidan
 * @since 2019-03-09
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

}
