package com.hjyd.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @program：ServiceA
 * @description：文件转换工具
 * @author：林黎明
 * @create：2019-05-21 11:37
 */
@Slf4j
public class FileUtils {

    /**
     * MultipartFile 转换成File For SpringBoot
     *
     * @param multfile 原文件类型
     * @return File
     * @throws IOException
     */
    public static File multipartToFile(MultipartFile multfile) throws IOException {

        //创建文件对象
        File file = null;
        File dfile = null;
        try {
            dfile = File.createTempFile("prefix", "_" + multfile.getOriginalFilename());
            multfile.transferTo(dfile);
            file = dfile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
