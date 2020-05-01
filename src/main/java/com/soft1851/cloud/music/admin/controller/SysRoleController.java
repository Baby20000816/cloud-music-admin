package com.soft1851.cloud.music.admin.controller;


import com.soft1851.cloud.music.admin.common.ResponseResult;
import com.soft1851.cloud.music.admin.entity.SysRole;
import com.soft1851.cloud.music.admin.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jh_wu
 * @since 2020-04-23
 */
@RestController
@RequestMapping("/sysRole")
@Slf4j
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;

    @GetMapping(value = "/list")
    public Map<String, Object> getRoleMenuByRoleId(@Param("roleId") int roleId) {
        return sysRoleService.getRoleMenuByRoleId(roleId);
    }

    @GetMapping(value = "/all")
    public List<Map<String, Object>> selectAll() {
        return sysRoleService.selectAll();
    }

    @PostMapping(value = "/single")
    public ResponseResult insertAdmin(@RequestBody SysRole role) {
        sysRoleService.insertSingle(role);
        return ResponseResult.success();
    }

    @DeleteMapping(value = "/single/{roleId}")
    public ResponseResult deleteSingle(@PathVariable int roleId) {
        sysRoleService.deleteSingle(roleId);
        return ResponseResult.success();
    }
}
