package com.hjyd.hjmodule.dao;


import com.hjyd.hjmodule.pojo.TestStudent;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;

@SqlResource("testStudent")
public interface TestStudentDao extends BaseMapper<TestStudent> {


}