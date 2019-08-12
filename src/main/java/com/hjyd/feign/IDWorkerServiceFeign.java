package com.hjyd.feign;

import com.hjyd.feign.hystrix.IDWorkerServiceHystrix;
import com.hjyd.response.HjHttpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program：ServiceA
 * @description：
 * @author：黄细初
 * @create：2019-05-05 14:12
 */
@FeignClient(value = "SFIDWORKER",fallback = IDWorkerServiceHystrix.class)
public interface IDWorkerServiceFeign {


    @GetMapping(value = "/idGenerator/get",consumes = "application/json")
    HjHttpResponse<String> get();

}
