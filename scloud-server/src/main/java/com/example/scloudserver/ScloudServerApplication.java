package com.example.scloudserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
// 作为注册中心
@EnableEurekaServer
public class ScloudServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScloudServerApplication.class, args);
    }

}
