package com.soft1851.cloud.music.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft1851.cloud.music.admin.entity.SongList;
import com.soft1851.cloud.music.admin.mapper.SongListMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.io.IOException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/4/22
 * @Version 1.0
 */
@SpringBootTest
class SongListServiceTest {
    @Resource
    private SongListService songListService;
    @Resource
    private SongListMapper songListMapper;

    @Test
    void selectAll() throws IOException {
        List<Map<String, Object>> maps = songListService.selectAll();
        System.out.println(maps.size());
//        ExcelsUtil.exportExcel(maps);
    }

    @Test
    void testSelectAll() {
    }

    @Test
    void getByPage() {
        Page<SongList> page = new Page<>(1, 2, 10);
        QueryWrapper<SongList> wrapper = new QueryWrapper<>(null);
        IPage<SongList> page1 = songListService.page(page, wrapper);
        System.out.println(page1.getRecords());
        System.out.println("总页数" + page1.getTotal());
    }

    @Test
    void getByType() {
        List<Map<String, Object>> maps = new ArrayList<>();
        maps = songListService.getByType();
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }

    @Test
    void getBlurSelect() {
        List<SongList> maps = songListService.blurSelect("看歌词");
        System.out.println(maps);
    }
}