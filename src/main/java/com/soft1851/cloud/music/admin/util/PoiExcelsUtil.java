package com.soft1851.cloud.music.admin.util;

import com.soft1851.cloud.music.admin.common.ResultCode;
import com.soft1851.cloud.music.admin.exception.CustomException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * @author wf
 * @create 2020/1/20
 * @description POI生成excel并导出
 */
public class PoiExcelsUtil {

    public static HSSFWorkbook exportExcel(List<Map<String, Object>> data) throws IOException {
        File file = new File("D:\\text.xls");
            FileOutputStream out = new FileOutputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook();
            createExcel(workbook, data);
            workbook.write(out);
            out.flush();
            out.close();
            return workbook;
    }

    public static List<String> readExcel(int index) throws IOException {
        List<String> list = new ArrayList<>();
        File file = new File("D:\\sort.xls");
        FileInputStream fs = new FileInputStream(file);
        BufferedInputStream buffer = new BufferedInputStream(fs);
        POIFSFileSystem fileSystem = new POIFSFileSystem(buffer);
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
        HSSFSheet sheet = workbook.getSheetAt(index);
        Row row = sheet.getRow(1);
        row.createCell(7).setCellValue("123");
        FileOutputStream out = new FileOutputStream("D:\\sort.xls");
        workbook.write(out);
        fs.close();
        return list;
    }
    public static void createExcel( HSSFWorkbook workbook,List<Map<String, Object>> data){
        HSSFSheet sheet = workbook.getSheetAt(0);
        CellStyle style = workbook.createCellStyle();
            HSSFRow row = sheet.createRow(0);
            /*创建姓名、学号和头像表头*/
            String[] strings = {"歌单号", "名称", "缩略图", "播放量", "类型", "歌曲数量", "创建时间"};
            int i;
            int len = strings.length;
            for (i = 0; i < len; i++) {
                CellRangeAddress region = new CellRangeAddress(0, 1, i, i);
                sheet.addMergedRegion(region);
                row.createCell(i).setCellValue(strings[i]);
                row.getCell(i).setCellStyle(style);
            }
        /*添加数据*/
        for(int j =0,rowIndex = 1, len1 = data.size(); j < len1; j++,rowIndex++){
            Row row2 = sheet.createRow(rowIndex);
            Map<String, Object> map = data.get(j);
            if(map != null) {
                row2.createCell(0).setCellValue(map.get("song_list_id").toString());
                row2.createCell(1).setCellValue(map.get("song_list_name").toString());
                row2.createCell(2).setCellValue(map.get("thumbnail").toString());
                row2.createCell(3).setCellValue(map.get("plays_counts").toString());
                row2.createCell(4).setCellValue(map.get("type").toString());
                row2.createCell(5).setCellValue(map.get("song_count").toString());
                row2.createCell(6).setCellValue(map.get("create_time").toString());
            }else {
                throw new CustomException("excel导出获取数据异常", ResultCode.DATABASE_ERROR);
            }
        }
    }
    public static void main(String[] args) throws SQLException {

    }
}
