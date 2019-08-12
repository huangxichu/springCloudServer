package com.hjyd.hjmodule.controller;

import com.hjyd.common.fileupload.IFileUpload;
import com.hjyd.response.HjHttpResponse;
import com.hjyd.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description：ftp 测试
 * @Author：黄细初
 * @Date：2019/6/23
 */
@RestController
@RequestMapping("/ftp/pturl")
public class FtpServerController {

    @Autowired
    IFileUpload fileUpload;
 
    /**
     * 上传
     *
     * @param file
     * @param romte
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HjHttpResponse<Boolean> fileUpload(@RequestPart("file") MultipartFile file,
                                              @RequestParam("romte") String romte) {
        String path = fileUpload.makeFilePath(romte,"1");
        fileUpload.upload(file, path);
        return ResultUtils.ok(true,"上传成功");
    }
 
    /**
     * 下载
     *
     * @param romte
     * @param request
     * @throws Exception
     */
    @GetMapping(value = "/download")
    public void download(@RequestParam("romte") String romte, HttpServletRequest request, HttpServletResponse response){
        fileUpload.download(romte, request,response);
    }


    //删除
    @PostMapping(value = "/remove")
    public HjHttpResponse<Boolean> fileUpload(@RequestParam("romte") String romte) {
        fileUpload.delete(romte);
        return ResultUtils.ok(true,"删除成功");
    }

}