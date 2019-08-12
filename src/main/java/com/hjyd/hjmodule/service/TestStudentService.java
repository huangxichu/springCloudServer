package com.hjyd.hjmodule.service;

import com.hjyd.hjmodule.pojo.TestStudent;

import java.util.List;

/**
 * @program：ServiceA
 * @description：
 * @author：黄细初
 * @create：2019-04-28 15:18
 */
public interface TestStudentService {

    List<TestStudent> queryList(TestStudent student);

    TestStudent save(TestStudent student);

    TestStudent update(TestStudent student);

    Boolean delete(String id);

}
