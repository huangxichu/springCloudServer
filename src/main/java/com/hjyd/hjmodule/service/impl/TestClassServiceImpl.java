package com.hjyd.hjmodule.service.impl;

import com.hjyd.hjmodule.dao.TestClassDao;
import com.hjyd.hjmodule.dto.TestClassDTO;
import com.hjyd.hjmodule.pojo.TestClass;
import com.hjyd.hjmodule.service.TestClassService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
public class TestClassServiceImpl implements TestClassService {


    @Autowired
    private TestClassDao testClassDao;

//    @Override
//    @Cacheable(value = "testClasses", key = "(#testClass.name == null ? '' : #testClass.name)+" +
//            "(#testClass.status == null ? '' : #testClass.status)", unless = "#result.size()<=2")
    public List<TestClass> queryList(TestClass testClass) {
        return testClassDao.template(testClass);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public TestClass save(TestClass testClass) {

        testClassDao.insert(testClass);
        return testClass;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public TestClass update(TestClass testClass) {
        testClassDao.updateById(testClass);
        return testClass;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean delete(Integer id) {
        testClassDao.deleteById(id);
        return true;
    }

    @Override
    public PageQuery<TestClass> queryList(TestClassDTO testClassDTO) {
        PageQuery<TestClass> query = new PageQuery<>();
        query.setPageNumber(testClassDTO.getPageNum());
        query.setPageSize(testClassDTO.getPageSize());
        testClassDao.selectByPage(query, testClassDTO);
        return query;
    }

}
