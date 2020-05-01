package com.soft1851.cloud.music.admin.service.impl;

import com.soft1851.cloud.music.admin.entity.SysMenu;
import com.soft1851.cloud.music.admin.mapper.SysMenuMapper;
import com.soft1851.cloud.music.admin.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public Map<String, Object> selectAll() {
        return null;
    }

    @Override
    public void insertSingle(SysMenu menu) {

    }

    @Override
    public void deleteSingle(int menuId) {

    }
}
