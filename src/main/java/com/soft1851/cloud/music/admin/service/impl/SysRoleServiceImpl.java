package com.soft1851.cloud.music.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft1851.cloud.music.admin.common.ResultCode;
import com.soft1851.cloud.music.admin.entity.RoleAdmin;
import com.soft1851.cloud.music.admin.entity.RoleMenu;
import com.soft1851.cloud.music.admin.entity.SysRole;
import com.soft1851.cloud.music.admin.exception.CustomException;
import com.soft1851.cloud.music.admin.mapper.RoleAdminMapper;
import com.soft1851.cloud.music.admin.mapper.RoleMenuMapper;
import com.soft1851.cloud.music.admin.mapper.SysRoleMapper;
import com.soft1851.cloud.music.admin.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wf
 * @since 2020-04-21
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private RoleAdminMapper roleAdminMapper;

    @Override
    public Map<String, Object> getRoleMenuByRoleId(int roleId) {
        Map<String, Object> data = new HashMap<>();
        // 根据角色id查询角色信息
        List<Map<String, Object>> maps = roleMenuMapper.getParentMenuByRoleId(roleId);
        for (Map<String, Object> map1 : maps) {
            map1.remove("role_id");
            map1.remove("menu_id");
        }
        if (maps != null) {
            data.put("permissions", maps);
            return data;
        }
        throw new CustomException("数据查找异常", ResultCode.DATA_IS_WRONG);
    }

    @Override
    public boolean checkRole(List<SysRole> roles, int roleId) {
        for (SysRole sysRole : roles) {
            if (roleId == sysRole.getRoleId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> selectAll() {
        List<Map<String, Object>> maps = new ArrayList<>();
        maps = sysRoleMapper.selectAll();
        if (maps != null) {
            return maps;
        }
        throw new CustomException("查询所有角色信息异常", ResultCode.DATA_IS_WRONG);
    }

    @Override
    public void insertSingle(SysRole role) {
        try {
            sysRoleMapper.insert(role);
        } catch (Exception e) {
            throw new CustomException("角色新增异常", ResultCode.DATA_IS_WRONG);
        }
    }

    @Override
    public void deleteSingle(int roleId) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        QueryWrapper<RoleAdmin> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("role_id", roleId);
        QueryWrapper<RoleMenu> wrapper2 =new QueryWrapper<>();
        wrapper2.eq("role_id", roleId);
        try {
            sysRoleMapper.delete(wrapper);
            roleAdminMapper.delete(wrapper1);
            roleMenuMapper.delete(wrapper2);
        } catch (Exception e) {
            throw new CustomException("角色删除异常", ResultCode.DATABASE_ERROR);
        }
    }
}
