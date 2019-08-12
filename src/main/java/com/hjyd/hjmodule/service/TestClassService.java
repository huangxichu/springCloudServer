package com.hjyd.hjmodule.service;

import com.hjyd.hjmodule.dto.TestClassDTO;
import com.hjyd.hjmodule.pojo.TestClass;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

/**
 * @program：ServiceA
 * @description：
 * @author：黄细初
 * @create：2019-04-28 15:18
 */
public interface TestClassService {

    List<TestClass> queryList(TestClass testClass);

    TestClass save(TestClass testClass);


    TestClass update(TestClass testClass);

    Boolean delete(Integer id);

    PageQuery<TestClass> queryList(TestClassDTO testClassDTO);

}
