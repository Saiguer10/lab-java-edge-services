package com.api_gateway.demo.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("student_route", r -> r
                        .path("/api/students/**")
                        .filters(f -> f.rewritePath("/api/students/(?<segment>.*)", "/students/${segment}"))
                        .uri("lb://student-info-service")
                )
                .route("grades_route", r -> r
                        .path("/api/grades/**", "/api/courses/**")
                        .filters(f -> f
                                .rewritePath("/api/grades/(?<segment>.*)", "/grades/${segment}")
                                .rewritePath("/api/courses/(?<segment>.*)", "/courses/${segment}")
                        )
                        .uri("lb://grades")
                )
                .route("catalog_route", r -> r
                        .path("/api/catalog/**")
                        .filters(f -> f.rewritePath("/api/catalog/(?<segment>.*)", "/catalog/${segment}"))
                        .uri("lb://student-catalog-service")
                )
                .build();
    }
}
