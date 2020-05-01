package com.soft1851.cloud.music.admin.controller;

import com.soft1851.cloud.music.admin.service.FileService;
import com.soft1851.cloud.music.admin.service.SongListService;
import com.soft1851.cloud.music.admin.util.ExcelUtils;
import com.soft1851.cloud.music.admin.util.FileUtil;
import com.soft1851.cloud.music.admin.util.PoiExcelsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.UUID;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/4/22
 * @Version 1.0
 */
@RestController
@RequestMapping("/resources")
@Slf4j
public class FileController {
    @Resource
    private SongListService songListService;
    @Resource
    private FileService fileService;

    @GetMapping("/songList")
    @ResponseBody
    public void export(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletResponse response = attributes.getResponse();
        assert response != null;
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition","attachment");
        ExcelUtils.exportExcel(response, songListService.selectAll(), fileService.exportSongList(), "歌单表");
    }

    @PostMapping(value = "/upload")
    public void exportResource(@RequestParam("file") MultipartFile[] files){
        for (MultipartFile file : files) {
            log.info("文件的响应类型" + file.getContentType());
            log.info("获取表单中文件组件的名字" + file.getName());
            log.info("获取上传文件的名称：" + file.getOriginalFilename());
            log.info("文件的类型：" + String.valueOf(file.getClass()));
            log.info("获得文件的大小：" + String.valueOf(file.getSize()));
            log.info(String.valueOf(file.getResource()));
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            log.info("后缀名：" + suffix);
            String path = "D:\\test\\" + UUID.randomUUID().toString() +suffix;
            Path path1 = Path.of(path);
            try {
                file.transferTo(path1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*try {
                file.transferTo(new File(path));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        /*log.info(String.valueOf(file.getSize()));
        File file1 = FileUtil.fileConversion(file);
        fileService.importSong(file1);*/
    }

    @GetMapping(value = "/model")
    public void downloadModel() {
        //创建ServletRequestAttributes
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        //获取response响应
        HttpServletResponse response = attributes.getResponse();
        assert response != null;
        //设置响应类型位excel文件类型
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //设置响应头，允许文件在浏览器中下载
        response.setHeader("Content-Disposition","attachment");
        //在页面中显示
        //response.setHeader("Content-Disposition","inline");
        //在页面中下载文件，文件名为filename.jpg
        //response.setHeader("Content-Disposition","attachment; filename=filename.jpg");
        //导出模板
        ExcelUtils.downloadModel(response, fileService.downloadSongModel(), "音乐模板");

    }
}
