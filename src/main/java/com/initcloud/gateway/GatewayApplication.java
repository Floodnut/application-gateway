package com.initcloud.gateway;

import java.io.File;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.initcloud.gateway.common.config.UserEnvironments;
import com.initcloud.gateway.common.enums.SSLProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class GatewayApplication {

	static class GatewayKeyStore {
		private String keyStore;
		private String keyStorePassword;

		public GatewayKeyStore(String jks, String pw) {
			this.keyStore = jks;
			this.keyStorePassword = pw;
		}

		public void setKeyStoreProperties(String userPath) {
			String path = System.getProperty(userPath);
			System.setProperty(SSLProperties.SSL_TRUST_STORE.getProperty(), path + File.separator + this.keyStore);
			System.setProperty(SSLProperties.SSL_TRUST_STORE_PASSWORD.getProperty(), this.keyStorePassword);
		}
	}

	static {

		GatewayKeyStore gwKeyStore = new GatewayKeyStore("keystore.jks", "springboot");
		gwKeyStore.setKeyStoreProperties(UserEnvironments.USER_DIR);

		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				log.info("Gateway Application Peering verified. - {}", hostname);
				return true;
			}
		});
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}
