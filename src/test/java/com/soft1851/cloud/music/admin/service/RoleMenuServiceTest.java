package com.soft1851.cloud.music.admin.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/4/21
 * @Version 1.0
 */
@SpringBootTest
class RoleMenuServiceTest {
    @Resource
    private RoleMenuService roleMenuService;

    @Test
    void getRoleMenuByRoleId() {
        System.out.println(roleMenuService.getRoleMenuByRoleId(1, "mqxu"));
    }
}