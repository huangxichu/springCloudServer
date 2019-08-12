package com.hjyd.hjmodule.controller;

import com.hjyd.hjmodule.dto.TestClassDTO;
import com.hjyd.feign.TestClassServiceFeign;
import com.hjyd.hjmodule.pojo.TestClass;
import com.hjyd.response.HjHttpResponse;
import com.hjyd.hjmodule.service.TestClassService;
import com.hjyd.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.beetl.sql.core.engine.PageQuery;
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
@RequestMapping("/test/class")
@Api(value = "TestClass",tags = "测试接口")
public class TestClassController {

    @Autowired
    private TestClassService testClassService;

    @Autowired
    private TestClassServiceFeign testClassServiceFeign;

    @ApiOperation(value = "新增",notes = "新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "班级名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="named",value = "班级名称d",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="status",value = "状态【0-正常 1-停用】",required = true,dataType = "String",paramType = "query")
    })
    @PostMapping("/save")
    public HjHttpResponse<TestClass> save(@ApiIgnore TestClass testClass){
        return ResultUtils.ok(testClassService.save(testClass),"新增成功");
    }

    @ApiOperation(value = "修改",notes = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "主键",required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name="name",value = "班级名称",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="status",value = "状态【0-正常 1-停用】",required = true,dataType = "String",paramType = "query")
    })
    @PutMapping("/update")
    public HjHttpResponse<TestClass> update(@ApiIgnore TestClass testClass){
        return ResultUtils.ok(testClassService.update(testClass),"修改成功");
    }

    @ApiOperation(value = "删除",notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "主键",required = true,dataType = "int",paramType = "query")
    })
    @DeleteMapping("/delete")
    public HjHttpResponse<Boolean> delete(@ApiIgnore Integer id){
        return ResultUtils.ok(testClassService.delete(id),"删除成功");
    }


    @ApiOperation(value = "查询列表",notes = "查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "班级名称",required = false,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="status",value = "状态【0-正常 1-停用】",required = false,dataType = "String",paramType = "query")
    })
    @GetMapping("/list/get")
    public HjHttpResponse<List<TestClass>> get(@ApiIgnore TestClass testClass){
        return ResultUtils.ok(testClassService.queryList(testClass),"查询成功");
    }

    @ApiOperation(value = "查询列表(分页)",notes = "查询列表(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "班级名称",required = false,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="status",value = "状态【0-正常 1-停用】",required = false,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pageNum",value = "第几页",required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name="pageSize",value = "每页大小",required = true,dataType = "int",paramType = "query")
    })
    @GetMapping("/list/page/get")
    public HjHttpResponse<PageQuery<TestClass>> getByPage(@ApiIgnore TestClassDTO testClassDTO){
        return ResultUtils.ok(testClassService.queryList(testClassDTO),"查询成功");
    }

    @ApiOperation(value = "查询列表(分页 FEIGN)",notes = "查询列表(分页 FEIGN)")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "班级名称",required = false,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="status",value = "状态【0-正常 1-停用】",required = false,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name="pageNum",value = "第几页",required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name="pageSize",value = "每页大小",required = true,dataType = "int",paramType = "query")
    })
    @GetMapping("/feign/list/page/get")
    public HjHttpResponse<PageQuery<TestClass>> getByPageAndFeign(@ApiIgnore TestClassDTO testClassDTO){
        HjHttpResponse<PageQuery<TestClass>> result = testClassServiceFeign.get(testClassDTO.getName(),
                testClassDTO.getStatus(),
                testClassDTO.getPageNum(),
                testClassDTO.getPageSize());
        return ResultUtils.success(result,"查询成功");
    }

}
