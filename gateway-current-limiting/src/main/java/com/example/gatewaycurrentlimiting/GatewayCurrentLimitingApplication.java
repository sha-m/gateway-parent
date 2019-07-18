package com.example.gatewaycurrentlimiting;

import com.example.gatewaycurrentlimiting.resolve.HostAddrKeyResolver;
import com.example.gatewaycurrentlimiting.resolve.UriKeyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayCurrentLimitingApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayCurrentLimitingApplication.class, args);
    }


    @Bean
    public HostAddrKeyResolver hostAddrKeyResolver() {
        return new HostAddrKeyResolver();
    }
//
//    @Bean
//    public UriKeyResolver uriKeyResolver() {
//        return new UriKeyResolver();
//    }
//
//
//    @Bean
//    KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
//    }

}
