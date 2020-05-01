package com.soft1851.cloud.music.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft1851.cloud.music.admin.entity.RoleAdmin;
import com.soft1851.cloud.music.admin.entity.RoleMenu;
import com.soft1851.cloud.music.admin.entity.SysAdmin;
import com.soft1851.cloud.music.admin.mapper.RoleAdminMapper;
import com.soft1851.cloud.music.admin.mapper.RoleMenuMapper;
import com.soft1851.cloud.music.admin.mapper.SysAdminMapper;
import com.soft1851.cloud.music.admin.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private SysAdminMapper sysAdminMapper;

    @Override
    public Map<String, Object> getRoleMenuByRoleId(int roleId, String name) {
        Map<String, Object> map = new HashMap<>();
        //定义条件构造器
        QueryWrapper<SysAdmin> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        //根据name查询用户信息
        SysAdmin sysAdmin = sysAdminMapper.selectOne(wrapper);
        //根据角色id查询角色权限
        List<Map<String, Object>> roleMenu = roleMenuMapper.getParentMenuByRoleId(roleId);
        for (Map<String, Object> map1 : roleMenu) {
            map1.remove("menu_id");
            map1.remove("role_id");
        }
        if (sysAdmin != null) {
            map.put("user", sysAdmin);
            map.put("permissions", roleMenu);
            return map;
        }
        return null;
    }
}
