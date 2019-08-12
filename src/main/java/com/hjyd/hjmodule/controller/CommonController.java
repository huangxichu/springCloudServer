package com.hjyd.hjmodule.controller;

import com.hjyd.common.fileupload.IFileUpload;
import com.hjyd.feign.IDWorkerServiceFeign;
import com.hjyd.response.HjHttpResponse;
import com.hjyd.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @program：server_demo
 * @description：公共demo
 * @author：黄细初
 * @create：2019-05-09 15:17
 */
@RestController
@RequestMapping("/common")
@Api(value = "Common",tags = "公共接口")
public class CommonController {

    @Autowired
    private IDWorkerServiceFeign idWorkerServiceFeign;

    @Autowired
    private IFileUpload fileUpload;

    @PutMapping("/upload")
    @ApiOperation(value = "上传",notes = "上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "文件名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="file",value = "文件",required = true,dataType = "file",paramType = "form")
    })
    public HjHttpResponse<Boolean> upload(String name,
                                        @RequestParam(value = "file",required = false)
                                                MultipartFile[] file){

        return ResultUtils.ok(true,"新增成功");
    }

    @PutMapping("/file/dele")
    @ApiOperation(value = "删除",notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name="path",value = "文件路径",required = true,dataType = "String",paramType = "query")
    })
    public HjHttpResponse<Boolean> delete(String path){
        List<String> paths = new ArrayList<>();
        paths.add(path);
        return ResultUtils.ok(fileUpload.delete(paths),"新增成功");
    }

    @ApiOperation(value = "获取ID接口", notes = "获取ID接口")
    @GetMapping("/id/get")
    public HjHttpResponse<String> getOne() {
        return idWorkerServiceFeign.get();
    }
}
