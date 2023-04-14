package com.initcloud.gateway.oauth.config;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	private AuthenticationManager authenticationManager;
	private Environment environment;

	/* 인가서버에 클라이언트 등록 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("__Client_ID__") // 클라이언트 ID
			.secret("__Client_Secret__") // 클라이언트 암호
			.scopes("", "") // Scope : Email, Profile 등
			.authorizedGrantTypes("client_credentials", "password", "refresh_token").accessTokenValiditySeconds(6000);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		String useJwt = environment.getProperty("spring.security.oauth.jwt");
		if (useJwt != null && "true".equalsIgnoreCase(useJwt.trim())) {
			endpoints.tokenStore(tokenStore())
				.tokenEnhancer(jwtConeverter())
				.authenticationManager(authenticationManager);
		} else {
			endpoints.authenticationManager(authenticationManager);
		}
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Bean
	public TokenStore tokenStore() {
		String useJwt = environment.getProperty("spring.security.oauth.jwt");
		if (useJwt != null && "true".equalsIgnoreCase(useJwt.trim())) {
			return new JwtTokenStore(jwtConeverter());
		} else {
			return new InMemoryTokenStore();
		}
	}

	@Bean
	protected JwtAccessTokenConverter jwtConeverter() {
		String pwd = environment.getProperty("spring.security.oauth.jwt.keystore.password");
		String alias = environment.getProperty("spring.security.oauth.jwt.keystore.alias");
		String keystore = environment.getProperty("spring.security.oauth.jwt.keystore.name");
		String path = System.getProperty("user.dir");

		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
			new FileSystemResource(new File(path + File.separator + keystore)), pwd.toCharArray());
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias));
		return converter;
	}
}