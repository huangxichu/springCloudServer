package com.hjyd.feign.hystrix;

import com.hjyd.feign.IDWorkerServiceFeign;
import com.hjyd.response.HjHttpResponse;
import org.springframework.stereotype.Component;

/**
 * @program：ServiceA
 * @description：
 * @author：黄细初
 * @create：2019-05-05 14:23
 */
@Component
public class IDWorkerServiceHystrix implements IDWorkerServiceFeign {

    @Override
    public HjHttpResponse<String> get() {
        return null;
    }


}
