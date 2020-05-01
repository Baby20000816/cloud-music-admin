package com.soft1851.cloud.music.admin.service;

import com.soft1851.cloud.music.admin.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wf
 * @since 2020-04-21
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 查询所有菜单资源
     * @return
     */
    Map<String, Object> selectAll();

    /**
     * 新增权限
     * @param menu
     */
    void insertSingle(SysMenu menu);

    /**
     * 单个删除菜单资源
     * @param menuId
     */
    void deleteSingle(int menuId);
}
