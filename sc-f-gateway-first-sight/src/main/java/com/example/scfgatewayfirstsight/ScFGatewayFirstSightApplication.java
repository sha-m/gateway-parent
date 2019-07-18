package com.example.scfgatewayfirstsight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class ScFGatewayFirstSightApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScFGatewayFirstSightApplication.class, args);
    }

    /*
        使用RouteLocator的Bean进行路由转发，将请求进行处理，最后转发到目标的下游服务
        这里转发到httpbin.org:80
    */
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://httpbin.org:80"))
                .build();
    }
    /*
        使用了一个RouteLocatorBuilder的bean去创建路由，除了创建路由RouteLocatorBuilder
        可以让你添加各种predicates和filters，predicates断言的意思，
        顾名思义就是根据具体的请求的规则，由具体的route去处理，filters是各种过滤器，用来对请求做各种判断和修改。
    */

    @Bean
    public RouteLocator myRoutesOne(RouteLocatorBuilder builder) {
        String httpUri = "http://httpbin.org:80";
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(httpUri))
                .route(p -> p
                        .host("*.hystrix.com")
                        .filters(f -> f
                                .hystrix(config -> config
                                        .setName("mycmd")
                                        .setFallbackUri("forward:/fallback")))
                        .uri(httpUri))
                .build();
    }
    /*
        使用了另外一个router，该router使用host去断言请求是否进入该路由，
        当请求的host有“*.hystrix.com”，都会进入该router，该router中有一个hystrix的filter,
        该filter可以配置名称、和指向性fallback的逻辑的地址，比如本案例中重定向到了“/fallback”。
    */
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
    /*
        请求curl --dump-header - --header Host:www.hystrix.com http://localhost:8080/delay/3
        返回fallback
    */

}
