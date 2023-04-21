package com.initcloud.gateway.security.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.httpBasic().disable()
			.formLogin().disable()
			.csrf().disable();

		httpSecurity
			.authorizeRequests().antMatchers("/actuator/**").permitAll()
			.and()
			.authorizeRequests().antMatchers("/**").permitAll()
			// .hasIpAddress("0.0.0.0/0")
			.and()
			.headers().frameOptions().disable();

	}
}
