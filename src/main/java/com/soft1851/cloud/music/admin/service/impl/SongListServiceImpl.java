package com.soft1851.cloud.music.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft1851.cloud.music.admin.common.ResultCode;
import com.soft1851.cloud.music.admin.entity.SongList;
import com.soft1851.cloud.music.admin.exception.CustomException;
import com.soft1851.cloud.music.admin.mapper.SongListMapper;
import com.soft1851.cloud.music.admin.service.SongListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft1851.cloud.music.admin.service.SongService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wf
 * @since 2020-04-21
 */
@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {
    @Resource
    private SongListMapper songListMapper;

    @Override
    public List<Map<String, Object>> selectAll() {
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.select("song_list_id", "song_list_name", "thumbnail", "plays_counts", "type", "song_count", "create_time")
                .orderByDesc("plays_counts");
        List<Map<String, Object>> songLists = songListMapper.selectMaps(wrapper);
        if(songLists != null){
            return songLists;
        }
        throw new CustomException("歌单查询异常", ResultCode.DATABASE_ERROR);
    }

    @Override
    public List<SongList> getByPage(int current, int size) {
        Page<SongList> page = new Page<>(current, size);
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        IPage<SongList> iPage = songListMapper.selectPage(page, wrapper);
        return iPage.getRecords();
    }

    @Override
    public List<Map<String, Object>> getByType() {
//        Page<Map<String, Object>> page = new Page<>(1, 5);
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.select("type").groupBy("type").orderByDesc("song_list_id");
        List<Map<String, Object>> maps = songListMapper.selectMaps(wrapper);
        for (Map<String, Object> map : maps) {
            if ("0".equals(map.get("type"))) {
                map.remove("type");
            }else {
                QueryWrapper<SongList> wrapper1 = new QueryWrapper<>();
                wrapper1.orderByDesc("song_list_id").eq("type", map.get("type"));
                List<Map<String, Object>> songLists = songListMapper.selectMaps(wrapper1);
                map.put("child", songLists);
            }
        }
//        IPage<Map<String, Object>> iPage = songListMapper.selectMapsPage(page, wrapper);
        return maps;
    }

    @Override
    public List<SongList> blurSelect(String field) {
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.like("song_list_name", field)
                .or().like("type", field);
        return songListMapper.selectList(wrapper);
    }
}
