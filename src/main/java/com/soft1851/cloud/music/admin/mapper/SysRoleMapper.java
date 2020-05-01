package com.soft1851.cloud.music.admin.mapper;

import com.soft1851.cloud.music.admin.entity.SysRole;
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
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 获取所有
     * @return
     */
    List<Map<String, Object>> selectAll();
}
