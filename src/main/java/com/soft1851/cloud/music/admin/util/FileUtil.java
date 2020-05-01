package com.soft1851.cloud.music.admin.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/4/24
 * @Version 1.0
 */
public class FileUtil {

    /*MultipartFile与File之间的转换*/
    public static File fileConversion(MultipartFile file){
        int n;
        File file1 = new File(file.getOriginalFilename());
        try {
            InputStream in = file.getInputStream();
            OutputStream os = new FileOutputStream(file1);
            byte[] bytes = new byte[4096];
            while ((n = in.read(bytes,0,4096)) != -1){
                os.write(bytes,0,n);
                File file2 = new File(file1.toURI());
                file2.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file1;
    }
}
