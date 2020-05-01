package com.soft1851.cloud.music.admin.service;

import com.soft1851.cloud.music.admin.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wf
 * @since 2020-04-21
 */
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 获取角色权限及用户的个人信息
     * @param roleId
     * @param name
     * @return
     */
    Map<String, Object> getRoleMenuByRoleId(int roleId, String name);
}
