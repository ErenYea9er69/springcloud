package com.rayen.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator myRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/api/tournaments/**")
                        .uri("lb://TOURNAMENT"))
                .route(p -> p
                        .path("/api/teams/**")
                        .filters(f -> f.circuitBreaker(config ->
                                config.setName("teamCircuitBreaker")
                                        .setFallbackUri("forward:/contactAdmin")))
                        .uri("lb://TEAM"))
                .build();
    }
}
