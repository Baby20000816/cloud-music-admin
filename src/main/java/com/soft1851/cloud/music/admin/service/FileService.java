package com.soft1851.cloud.music.admin.service;

import java.io.File;
import java.util.Map;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/4/22
 * @Version 1.0
 */
public interface FileService {

    /**
     * 设置导出歌单表单表头信息
     * @return
     */
    Map<String, String> exportSongList();

    /**
     * 批量导入歌曲
     * @param file
     */
    void importSong(File file);

    /**
     * 下载歌曲模板
     */
    Map<String, String> downloadSongModel();
}
