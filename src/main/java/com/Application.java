package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * EnableCaching(开启注解)
 * @program：
 * @description：启动程序
 * @author：黄细初
 * @create：2019-04-28 11:26
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.hjyd"})
@EnableDiscoveryClient
@EnableFeignClients(defaultConfiguration = FeignClientsConfiguration.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
