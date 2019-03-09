package org.aidan.service;

import com.baomidou.mybatisplus.service.IService;
import org.aidan.entity.Role;

import java.util.Set;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author Aidan
 * @since 2019-03-09
 */
public interface RoleService extends IService<Role> {

    /**
     * 获取用户的角色集合
     *
     * @param userId
     * @return
     */
    Set<String> getRoleSetByUserId(Long userId);
}
