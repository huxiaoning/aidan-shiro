package org.aidan.service.impl;

import org.aidan.entity.RolePermission;
import org.aidan.mapper.RolePermissionDao;
import org.aidan.service.RolePermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色-权限 服务实现类
 * </p>
 *
 * @author Aidan
 * @since 2019-03-09
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermission> implements RolePermissionService {

}
