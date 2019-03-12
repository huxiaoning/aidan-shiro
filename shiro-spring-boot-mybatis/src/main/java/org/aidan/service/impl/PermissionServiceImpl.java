package org.aidan.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.aidan.entity.Permission;
import org.aidan.mapper.PermissionDao;
import org.aidan.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author Aidan
 * @since 2019-03-09
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {

    @Override
    public Set<String> getPermissionsByUserId(Long userId) {
        System.out.println("从数据库获取权限信息");
        return baseMapper.getPermissionsByUserId(userId);
    }
}
