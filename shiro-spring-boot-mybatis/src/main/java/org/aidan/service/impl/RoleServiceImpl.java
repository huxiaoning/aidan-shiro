package org.aidan.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.aidan.entity.Role;
import org.aidan.mapper.RoleDao;
import org.aidan.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    @Override
    public Set<String> getRoleSetByUserId(Long userId) {
        return baseMapper.getRoleSetByUserId(userId);
    }
}
