package org.aidan.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.aidan.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author Aidan
 * @since 2019-03-09
 */
public interface PermissionDao extends BaseMapper<Permission> {

    /**
     * 获取用户的权限集合
     *
     * @param userId
     * @return
     */
    Set<String> getPermissionsByUserId(@Param("userId") Long userId);

}
