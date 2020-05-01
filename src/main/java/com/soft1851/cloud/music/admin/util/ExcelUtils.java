package com.soft1851.cloud.music.admin.util;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.soft1851.cloud.music.admin.common.ResultCode;
import com.soft1851.cloud.music.admin.entity.Song;
import com.soft1851.cloud.music.admin.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author jh_wu
 * @ClassName ExceUtils
 * @Description TODO
 * @Date 2020/4/30:20:29
 * @Version 1.0
 **/
@Slf4j
public class ExcelUtils {
    //list 变量是导出的数据，map存放表头的标题信息，title表格名
    public static void exportExcel(HttpServletResponse response, List list, Map<String, String> map, String title){
        // 通过工具类创建writer
        ExcelWriter writer = cn.hutool.poi.excel.ExcelUtil.getWriter(true);
        //自定义标题别名
        Set<Map.Entry<String, String>> entries = map.entrySet();
        //迭代器遍历数据
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            //自定义表头
            writer.addHeaderAlias(next.getKey(), next.getValue());
        }

        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(map.size() - 1, title);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        try {
            writer.flush(response.getOutputStream(), true);
        } catch (IOException e) {
            log.info("歌单导出异常");
            throw new CustomException("歌单数据导出异常", ResultCode.DATABASE_EXPORT_ERROR);
        }
        // 关闭writer，释放内存
        writer.close();
    }

    /**
     * 写出map数据
     *
     * @param response
     * @param list  输出到excel的列表
     * @param title excel 标题
     */
    public static void exportExcel(HttpServletResponse response, List<Map<String, Object>> list, String title) throws IOException {
        //设置输出头
        response.setHeader("Content-Disposition", "attachment;fileName=" + new String((new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx").getBytes("UTF-8")));
        // 通过工具类创建writer
        ExcelWriter writer = cn.hutool.poi.excel.ExcelUtil.getWriter(true);
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(list.size() - 1, title);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        //out为OutputStream，需要写出到的目标流
        writer.flush(response.getOutputStream());
        // 关闭writer，释放内存
        writer.close();
    }
    //下载模板
    public static void downloadModel(HttpServletResponse response, Map<String, String> map, String title){
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new LinkedHashMap<>();
        map1.put("song_name","句号");
        map1.put("singer", "G.E.M.邓紫棋");
        map1.put("duration", "03:55");
        map1.put("thumbnail", "http://ww1.sinaimg.cn/large/0084EtCNgy1ge52ciinlfj30c50c9k36.jpg");
        map1.put("url", "http://music.163.com/song/media/outer/url?id=1405283464.mp3");
        map1.put("lyric", "暂无");
        list.add(map1);
        // 通过工具类创建writer
        ExcelWriter writer = cn.hutool.poi.excel.ExcelUtil.getWriter(true);
        //自定义标题别名
        Set<Map.Entry<String, String>> entries = map.entrySet();
        //迭代器遍历数据
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            //自定义表头
            writer.addHeaderAlias(next.getKey(), next.getValue());
        }

        // 合并单元格后的标题行，使用默认标题样式
        writer.write(list);
        //out为OutputStream，需要写出到的目标流
        try {
            writer.flush(response.getOutputStream(), true);
        } catch (IOException e) {
            log.info("歌单导出异常");
            throw new CustomException("歌单数据导出异常", ResultCode.DATABASE_EXPORT_ERROR);
        }
        // 关闭writer，释放内存
        writer.close();
    }
    //批量导入歌曲
    public static List<Song> importExcel(File file){
        List<Song> songs = new ArrayList<>();
        InputStream inputStream = null;
        try {
            //获取文件信息
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //通过getReader方法确定读取哪个sheet中的数据
        ExcelReader reader = ExcelUtil.getReader(inputStream, "sheet1");
        //获取总行数
        List<List<Object>> read = reader.read(1, reader.getRowCount());
        for (List<Object> objects : read) {
            //对每行数据取出构建出一个song对象
            Song song = Song.builder()
                    .songName(objects.get(0).toString())
                    .songId(UUID.randomUUID().toString().replace("-", ""))
                    .sortId("0")
                    .singer(objects.get(1).toString())
                    .duration(objects.get(2).toString())
                    .thumbnail(objects.get(3).toString())
                    .url(objects.get(4).toString())
                    .lyric(objects.get(5).toString())
                    .commentCount(0)
                    .playCount(0)
                    .deleteFlag("0")
                    .updateTime(LocalDateTime.now())
                    .createTime(LocalDateTime.now())
                    .build();
            songs.add(song);
        }
        return songs;
    }

    public static void main(String[] args) {
//        importExcel();
        File file = new File("D:\\Downloads\\Chrome\\model.xlsx");
        System.out.println(importExcel(file));
    }
}

