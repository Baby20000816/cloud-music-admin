package com.soft1851.cloud.music.admin.service;

import com.soft1851.cloud.music.admin.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jh_wu
 * @since 2020-04-23
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 用户角色信息，及该角色的权限信息
     * @param roleId
     * @return
     */
    Map<String, Object> getRoleMenuByRoleId(int roleId);

    /**
     * 校验角色是否正确
     * @param roles
     * @param roleId
     * @return
     */
    boolean checkRole(List<SysRole> roles, int roleId);
    /**
     * 获取所有角色信息
     * @return
     */
    List<Map<String, Object>> selectAll();

    /**
     * 新增单个角色
     * @param role
     */
    void insertSingle(SysRole role);

    /**
     * 角色id
     * @param roleId
     */
    void deleteSingle(int roleId);
}
