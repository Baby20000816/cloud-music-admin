package com.soft1851.cloud.music.admin.mapper;

import com.soft1851.cloud.music.admin.entity.RoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wf
 * @since 2020-04-21
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    /**
     * 根据用户角色查询该用户的顶级权限
     * @return
     */
    List<Map<String, Object>> getParentMenuByRoleId(int roleId);

    /**
     * 根据角色id与父类id查询子类权限
     * @param roleId
     * @param parentId
     * @return
     */
    List<Map<String, Object>> getChildMenuByRoleId(int roleId, int parentId);
}
