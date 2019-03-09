package org.aidan.service.impl;

import org.aidan.entity.Role;
import org.aidan.mapper.RoleDao;
import org.aidan.service.RoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author Aidan
 * @since 2019-03-09
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

}
