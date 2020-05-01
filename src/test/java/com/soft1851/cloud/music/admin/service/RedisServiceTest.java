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
class RedisServiceTest {
    @Resource
    private RedisService service;

    @Test
    void testRedis() {
        service.set("code", "132132132");
        if(service.existsKey("code")) {
            System.out.println(service.getValue("code", String.class));
        }
    }
}