package com.initcloud.gateway.common.config;

import javax.annotation.PostConstruct;

import org.springframework.core.env.Environment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserEnvironments {

	private final Environment environment;

	public static final String USER_DIR = "user.dir";
	public static final String SSL_KEYSTORE = "server.ssl.key-store";
	public static final String SSL_KEYSTORE_PASSWORD = "server.ssl.key-store-password";
	public static final String OAUTH_JWT = "spring.security.oauth.jwt";
	public static final String OAUTH_JWT_KEYSTORE_NAME = "spring.security.oauth.jwt.keystore.name";
	public static final String OAUTH_JWT_KEYSTORE_PASSWORD = "spring.security.oauth.jwt.keystore.password";
	public static final String OAUTH_JWT_KEYSTORE_ALIAS = "spring.security.oauth.jwt.keystore.alias";

	@Getter
	private String pwd;

	@Getter
	private String alias;

	@Getter
	private String keystore;

	@Getter
	private String path;

	@Getter
	private String jwt;

	@Getter
	private String jwtSecret;

	@Getter
	private String jwtClientId;

	@PostConstruct
	public void initUserEnvironments() {
		this.path = System.getProperty(USER_DIR);
		this.jwt = environment.getProperty(OAUTH_JWT);
		this.pwd = environment.getProperty(OAUTH_JWT_KEYSTORE_PASSWORD);
		this.alias = environment.getProperty(OAUTH_JWT_KEYSTORE_ALIAS);
		this.keystore = environment.getProperty(OAUTH_JWT_KEYSTORE_NAME);

		this.jwtSecret = environment.getProperty(jwtSecret);
		this.jwtClientId = environment.getProperty(jwtClientId);
	}
}
