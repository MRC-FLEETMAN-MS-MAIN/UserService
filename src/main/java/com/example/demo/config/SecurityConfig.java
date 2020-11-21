package com.example.demo.config;

import com.example.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private Environment env;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;



	
	@Autowired
	private CustomUserDetailsService userDetailsService;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().disable();
		http.csrf().disable().authorizeRequests()
				// static ip address of zuul gateway to permit only requests from it
				.antMatchers("/**").hasIpAddress("34.195.41.137")
				.and()
				.addFilter(getAuthenticationFilter());
		http.headers().frameOptions().disable();

	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception{

		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userDetailsService,env,authenticationManager());
		return authenticationFilter;
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception{

		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}






}
