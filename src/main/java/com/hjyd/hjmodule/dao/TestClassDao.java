package com.hjyd.hjmodule.dao;


import com.hjyd.hjmodule.dto.TestClassDTO;
import com.hjyd.hjmodule.pojo.TestClass;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;


/**
 * @program：server_demo
 * @description：
 * @author：黄细初
 * @create：2019-05-15 10:29
 */
@SqlResource("testClass")
public interface TestClassDao extends BaseMapper<TestClass> {

    /**
     * 分页查询
     * @param query
     * @param testClass
     * @return
     */
    PageQuery<TestClass> selectByPage(PageQuery query,
                                      @Param("_root") TestClassDTO testClass);

}