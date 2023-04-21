package com.initcloud.gateway.common.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class InitCloudFilter extends AbstractGatewayFilterFactory<InitCloudFilter.FilterConfig> {

	@Getter
	@AllArgsConstructor
	public static class FilterConfig {
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}

	public InitCloudFilter() {
		super(FilterConfig.class);
	}

	/**
	 * GW Client -> GW Handler Mapping -> GW Web Handler -> Filter -> Service
	 * Filter 구현체
	 */
	@Override
	public GatewayFilter apply(FilterConfig config) {
		return (exchange, chain) -> {
			ServerHttpRequest req = exchange.getRequest();
			ServerHttpResponse res = exchange.getResponse();

			log.info("Init Cloud Filter request - {}", req.getId());
			log.info("Request {}: Filter message - {}", req.getId(), config.getBaseMessage());

			return chain.filter(exchange)
				.then(Mono.fromRunnable(() -> {
						if (config.isPostLogger())
							log.info("Request {}: Filter response Status - {}", req.getId(), res.getStatusCode());
					}
				));
		};

	}
}
