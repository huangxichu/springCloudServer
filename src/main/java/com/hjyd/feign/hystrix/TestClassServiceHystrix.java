package com.hjyd.feign.hystrix;

import com.hjyd.feign.TestClassServiceFeign;
import com.hjyd.hjmodule.pojo.TestClass;
import com.hjyd.response.HjHttpResponse;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.stereotype.Component;

/**
 * @program：ServiceA
 * @description：
 * @author：黄细初
 * @create：2019-05-05 14:23
 */
@Component
public class TestClassServiceHystrix implements TestClassServiceFeign {

    @Override
    public HjHttpResponse<PageQuery<TestClass>> get(String name,
                                                    String status,
                                                    Integer pageNum,
                                                    Integer pageSize) {
        return null;
    }
}
