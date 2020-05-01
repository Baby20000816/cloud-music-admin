package com.soft1851.cloud.music.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.soft1851.cloud.music.admin.common.ResultCode;
import com.soft1851.cloud.music.admin.entity.Song;
import com.soft1851.cloud.music.admin.entity.SongList;
import com.soft1851.cloud.music.admin.exception.CustomException;
import com.soft1851.cloud.music.admin.mapper.SongMapper;
import com.soft1851.cloud.music.admin.service.SongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft1851.cloud.music.admin.util.ExcelConsumer;
import com.soft1851.cloud.music.admin.util.ExportDataAdapter;
import com.soft1851.cloud.music.admin.util.ThreadPool;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jh_wu
 * @since 2020-04-23
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@Slf4j
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {
    @Resource
    private SongMapper songMapper;
    @Override
    public List<Map<String, Object>> selectAll() {
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.select("song_id", "song_name","sort_id","singer","duration","thumbnail","url","lyric","comment_count",
                "like_count","play_count", "delete_flag", "update_time", "create_time")
                .orderByDesc("like_count");
        List<Map<String, Object>> song = songMapper.selectMaps(wrapper);
        if(song != null){
            return song;
        }
        throw new CustomException("歌曲查询异常", ResultCode.DATABASE_ERROR);
    }

    @Override
    public List<Song> getByPage(int current, int size) {
        Page<Song> page = new Page<>(current, size);
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        IPage<Song> iPage = songMapper.selectPage(page, wrapper);
        return iPage.getRecords();
    }

    @Override
    public List<Map<String, Object>> getByType() {
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.select("singer").groupBy("singer").orderByDesc("play_count");
        List<Map<String, Object>> maps = songMapper.selectMaps(wrapper);
        for (Map<String, Object> map : maps) {
            if ("0".equals(map.get("singer"))) {
                map.remove("singer");
            }else {
                QueryWrapper<Song> wrapper1 = new QueryWrapper<>();
                wrapper1.orderByDesc("play_count").eq("singer", map.get("singer"));
                List<Map<String, Object>> songs = songMapper.selectMaps(wrapper1);
                map.put("child", songs);
            }
        }
        return maps;
    }

    @Override
    public List<Song> blurSelect(String field) {
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.like("song_name", field)
                .or().like("singer", field);
        return songMapper.selectList(wrapper);
    }

    @Override
    public void batchInsert(List<Song> songs) {
    }

    @Override
    public void batchDelete(String id) {
        String[] ids = id.split(",");
        List<String> idList = Arrays.asList(ids);
        try {
            songMapper.deleteBatchIds(idList);
        }catch (Exception e){
            throw new CustomException("歌曲批量删除异常", ResultCode.DATABASE_ERROR);
        }
    }

    @Override
    public void delete(String id) {
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.eq("song_id", id);
        try {
            songMapper.delete(wrapper);
        } catch (Exception e) {
            throw new CustomException("歌曲删除异常", ResultCode.DATABASE_ERROR);
        }
    }

    @Override
    @SneakyThrows
    public void exportData(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletResponse response = attributes.getResponse();
        assert response != null;
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition","attachment");
        //导出excel对象
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(1000);
        //数据缓冲
        ExportDataAdapter<Song> exportDataAdapter = new ExportDataAdapter<>();
        //线程同步对象
        CountDownLatch latch = new CountDownLatch(2);
        //启动线程获取数据(生产者)
        ThreadPool.getExecutor().submit(() -> produceExportData(exportDataAdapter, latch));
        //启动线程导出数据（消费者）
        ThreadPool.getExecutor().submit(() -> new ExcelConsumer<>(Song.class, exportDataAdapter, sxssfWorkbook, latch, "歌曲数据").run());
        latch.await();
        //使用字节流写数据
        OutputStream outputStream = null;
        outputStream = response.getOutputStream();
        sxssfWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 生产者生产数据
     *
     * @param exportDataAdapter
     * @param latch
     */
    private void produceExportData(ExportDataAdapter<Song> exportDataAdapter, CountDownLatch latch) {
        List<Song> songs = songMapper.selectList(null);
        songs.forEach(exportDataAdapter::addData);
        log.info("数据生产完成");
        //数据生产结束
        latch.countDown();
    }
}
