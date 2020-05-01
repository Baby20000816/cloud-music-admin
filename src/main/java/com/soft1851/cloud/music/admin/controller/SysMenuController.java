package com.soft1851.cloud.music.admin.controller;


import com.soft1851.cloud.music.admin.common.ResponseResult;
import com.soft1851.cloud.music.admin.entity.SysMenu;
import com.soft1851.cloud.music.admin.service.SysMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wf
 * @since 2020-04-21
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {
    @Resource
    private SysMenuService sysMenuService;

    @GetMapping(value = "/list")
    public Map<String, Object> selectAll() {
        return sysMenuService.selectAll();
    }

    @PostMapping(value = "/single")
    public ResponseResult insertSingle(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return ResponseResult.success();
    }

    @DeleteMapping(value = "/single/{id}")
    public ResponseResult deleteSingle(@PathVariable int id) {
        sysMenuService.removeById(id);
        return ResponseResult.success();
    }
}
