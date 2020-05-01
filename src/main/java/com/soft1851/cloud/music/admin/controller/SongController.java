package com.soft1851.cloud.music.admin.controller;


import com.soft1851.cloud.music.admin.entity.Song;
import com.soft1851.cloud.music.admin.service.SongService;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jh_wu
 * @since 2020-04-21
 */
@RestController
@RequestMapping("/song")
@Validated
public class SongController {
    @Resource
    private SongService songService;

    @GetMapping("/list")
    public List<Map<String, Object>> selectAll() {
        return songService.selectAll();
    }

    @GetMapping("/page")
    public List<Song> getByPage(@Valid @Param("currentPage") @NotNull int currentPage,@Valid @Param("size") @NotNull int size) {
        return songService.getByPage(currentPage, size);
    }

    @GetMapping("/blur")
    public List<Song> blurSelectSongList(@Valid @Param("field") @NotNull String field) {
        return songService.blurSelect(field);
    }

    @GetMapping("/type")
    public List<Map<String, Object>> getByType(){
        return songService.getByType();
    }
    @GetMapping(value = "/export")
    public void exportData() {
        songService.exportData();
    }
}
