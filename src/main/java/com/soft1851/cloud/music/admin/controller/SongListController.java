package com.soft1851.cloud.music.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft1851.cloud.music.admin.entity.SongList;
import com.soft1851.cloud.music.admin.service.SongListService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
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
@RequestMapping(value = "/songList")
public class SongListController {
    @Resource
    private SongListService songListService;

    @GetMapping("/list")
    public List<Map<String, Object>> selectAll() {
        return songListService.selectAll();
    }

    @GetMapping("/page")
    public List<SongList> getByPage(@Param("currentPage") int currentPage, @Param("size") int size) {
        return songListService.getByPage(currentPage, size);
    }

    @GetMapping("/blur")
    public List<SongList> blurSelectSongList(@Param("field") String field) {
        return songListService.blurSelect(field);
    }

    @GetMapping("/type")
    public List<Map<String, Object>> getByType(){
        return songListService.getByType();
    }
}
