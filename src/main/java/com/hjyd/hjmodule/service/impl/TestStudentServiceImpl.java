package com.hjyd.hjmodule.service.impl;

import com.hjyd.hjmodule.dao.TestStudentDao;
import com.hjyd.hjmodule.pojo.TestStudent;
import com.hjyd.hjmodule.service.TestStudentService;
import lombok.extern.slf4j.Slf4j;
import org.beetl.sql.ext.SnowflakeIDWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @program：ServiceA
 * @description：
 * @author：黄细初
 * @create：2019-04-28 15:18
 */

@Service
@Slf4j
public class TestStudentServiceImpl implements TestStudentService {


    @Autowired
    private TestStudentDao testStudentDao;

    @Autowired
    private SnowflakeIDWorker snowflakeIDWorker;

    @Override
    public List<TestStudent> queryList(TestStudent student) {
        return testStudentDao.template(student);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public TestStudent save(TestStudent student) {
        student.setId(snowflakeIDWorker.nextId() + "");
        testStudentDao.insert(student, true);
//        throw new ApplicationException()
        return student;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public TestStudent update(TestStudent student) {
        testStudentDao.updateTemplateById(student);
        return student;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean delete(String id) {
        testStudentDao.deleteById(id);
        return true;
    }


}
