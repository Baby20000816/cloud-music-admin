package com.soft1851.cloud.music.admin.service;

import com.soft1851.cloud.music.admin.entity.Song;
import com.baomidou.mybatisplus.extension.service.IService;
import com.soft1851.cloud.music.admin.entity.SongList;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jh_wu
 * @since 2020-04-23
 */
public interface SongService extends IService<Song> {
    /**
     * 查询所有歌单
     * @return
     */
    List<Map<String, Object>> selectAll();
    /**
     * 分页查询
     * @param current
     * @param size
     * @return
     */
    List<Song> getByPage(int current, int size);

    /**
     * 根据类型分组
     * @return
     */
    List<Map<String, Object>> getByType();

    /**
     * 模糊查询
     * @return
     */
    List<Song> blurSelect(String filed);

    /**
     * 批量插入
     * @param songs
     */
    void batchInsert(List<Song> songs);

    /**
     * 批量删除
     * @param idList
     */
    void batchDelete(String idList);

    /**
     * 单个删除
     * @param id
     */
    void delete(String id);

    /**
     * 导出歌曲数据
     */
    void exportData();
}
