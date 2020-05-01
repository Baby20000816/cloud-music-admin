package com.soft1851.cloud.music.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft1851.cloud.music.admin.entity.Song;
import com.soft1851.cloud.music.admin.entity.SongList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SongServiceTest {
    @Resource
    private SongService songService;

    @Test
    void selectAll() {
        List<Map<String, Object>> maps = songService.selectAll();
        System.out.println(maps.size());
    }

    @Test
    void getByPage() {
        Page<Song> page = new Page<>(1, 2);
        QueryWrapper<Song> wrapper = new QueryWrapper<>(null);
        IPage<Song> page1 = songService.page(page, wrapper);
        System.out.println(page1.getRecords());
        System.out.println("总页数" + page1.getTotal());
    }

    @Test
    void blurSelect() {
        List<Song> maps = songService.blurSelect("薛之谦");
        System.out.println(maps);
    }

    @Test
    void getByType() {
        List<Map<String, Object>> maps = new ArrayList<>();
        maps = songService.getByType();
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }
}