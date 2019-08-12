package com.hjyd.hjmodule.controller;

import com.hjyd.hjmodule.pojo.TestStudent;
import com.hjyd.response.HjHttpResponse;
import com.hjyd.hjmodule.service.TestStudentService;
import com.hjyd.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @program：ServiceA
 * @description：
 * @author：黄细初
 * @create：2019-04-28 15:09
 */
@RestController
@RequestMapping("/test/student")
@Api(value = "TestStudent",tags = "测试接口2")
public class TestStudentController {

    @Autowired
    private TestStudentService testStudentService;

    @ApiOperation(value = "新增",notes = "新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name="age",value = "年龄",required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name="cId",value = "班级id",required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name="cName",value = "班级name",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="name",value = "姓名",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="sex",value = "性别",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="status",value = "状态【0-正常 1-停用】",required = true,dataType = "String",paramType = "query")
    })
    @PostMapping("/save")
    public HjHttpResponse<TestStudent> save(@ApiIgnore TestStudent student){
        return ResultUtils.ok(testStudentService.save(student),"新增成功");
    }

    @ApiOperation(value = "修改",notes = "修改")
    @PutMapping("/update")
    public HjHttpResponse<TestStudent> update(TestStudent student){
        return ResultUtils.ok(testStudentService.update(student),"修改成功");
    }

    @ApiOperation(value = "删除",notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "主键",required = true,dataType = "String",paramType = "query")
    })
    @DeleteMapping("/delete")
    public HjHttpResponse<Boolean> delete(@ApiIgnore String id){
        return ResultUtils.ok(testStudentService.delete(id),"删除成功");
    }


    @ApiOperation(value = "查询列表",notes = "查询列表")
    @GetMapping("/list/get")
    public HjHttpResponse<List<TestStudent>> get(TestStudent student){
        return ResultUtils.ok(testStudentService.queryList(student),"查询成功");
    }



}
