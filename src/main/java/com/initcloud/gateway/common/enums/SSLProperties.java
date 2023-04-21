package com.initcloud.gateway.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SSLProperties {

	SSL_TRUST_STORE("javax.net.ssl.trustStore"),
	SSL_TRUST_STORE_PASSWORD("javax.net.ssl.trustStorePassword"),
	SSL_KEY_STORE("javax.net.ssl.keyStore"),
	SSL_KEY_STORE_TYPE("javax.net.ssl.keyStoreType"),
	SSL_KEY_STORE_PASSWORD("javax.net.ssl.keyStorePassword");

	@Getter
	private String property;
}
