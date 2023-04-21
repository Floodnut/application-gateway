package com.initcloud.gateway.common.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class GatewayConfiguration {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
			.route(rl ->
				rl.path("/")
					.uri("http://localhost:5555"))
			.route(rl ->
				rl.path("/api/v1")
					.uri("http://localhost:9090"))
			.route(rl ->
				rl.path("/api/v2")
					.uri("http://localhost:9091"))
			.build();
	}
}