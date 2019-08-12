package com.hjyd.feign;

import com.hjyd.feign.hystrix.TestClassServiceHystrix;
import com.hjyd.hjmodule.pojo.TestClass;
import com.hjyd.response.HjHttpResponse;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;

/**
 * @program：ServiceA
 * @description：
 * @author：黄细初
 * @create：2019-05-05 14:12
 */
@FeignClient(value = "SERVICEB",configuration = TestClassServiceFeign.MultipartSupportConfig.class,
    fallback = TestClassServiceHystrix.class)
public interface TestClassServiceFeign {


    @GetMapping(value = "/test/class/list/page/get",consumes = "application/json")
    HjHttpResponse<PageQuery<TestClass>> get(@RequestParam("name") String name,
                                             @RequestParam("status") String status,
                                             @RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize);


    @Configuration
    class MultipartSupportConfig extends FeignClientsConfiguration{

        @Bean
        public Encoder feignFormEncoder(){
            Encoder springEncoder = super.feignEncoder();
            return new SpringFormEncoder(){
                @Override
                public void encode(Object object, Type bodyType,
                                   RequestTemplate template)throws EncodeException {
                    if(!bodyType.equals(MultipartFile.class)){
                        springEncoder.encode(object,bodyType,template);
                    }else{
                        super.encode(object,bodyType,template);
                    }
                }
            };
        }

    }

}
