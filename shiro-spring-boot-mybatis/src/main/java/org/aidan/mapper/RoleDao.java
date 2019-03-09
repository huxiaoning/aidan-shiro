package org.aidan.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.aidan.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author Aidan
 * @since 2019-03-09
 */
public interface RoleDao extends BaseMapper<Role> {
    /**
     * 获取用户的角色集合
     *
     * @param userId
     * @return
     */
    Set<String> getRoleSetByUserId(@Param("userId") Long userId);
}
