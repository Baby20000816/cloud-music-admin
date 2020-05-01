package com.soft1851.cloud.music.admin.service;

import com.soft1851.cloud.music.admin.dto.SignDto;
import com.soft1851.cloud.music.admin.entity.SysAdmin;
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
public interface SysAdminService extends IService<SysAdmin> {

    /**
     * 用户登录
     * @param signDto
     * @return
     */
    Map<String, Object> sign(SignDto signDto);

    /**
     * 通过账号获取用户信息
     * @param name
     * @return
     */
    SysAdmin getAdminByName(String name);

    /**
     * 根据用户name获取用户角色
     * @param name
     * @return
     */
    List<Map<String, Object>> getAdminRoleByAdminName(String name);
    /**
     * 修改个人资料
     * @param admin
     */
    void updateInfo(SysAdmin admin);

    /**
     * 单个新增用户
     * @param admin
     */
    void insertSingle(SysAdmin admin);

    /**
     * 批量删除
     */
    void batchDelete(String id);

    /**
     * 查询所有用户
     * @return
     */
    Map<String, Object> selectAll();
}
