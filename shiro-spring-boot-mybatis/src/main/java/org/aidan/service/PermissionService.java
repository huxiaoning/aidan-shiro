package org.aidan.service;

import com.baomidou.mybatisplus.service.IService;
import org.aidan.entity.Permission;

import java.util.Set;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author Aidan
 * @since 2019-03-09
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 获取用户的权限集合
     *
     * @param userId
     * @return
     */
    Set<String> getPermissionsByUserId(Long userId);
}
