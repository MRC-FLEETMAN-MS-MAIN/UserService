package com.example.demo;

import com.example.demo.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
public class JwtmsApplication {
	
	@Autowired
	private UserRepository repository;
	

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry){
				registry.addMapping("/*").allowedHeaders("*").allowedOrigins("*").allowedMethods("*").allowCredentials(true);
			}


		};
	}

	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}



	public static void main(String[] args) {
		SpringApplication.run(JwtmsApplication.class, args);
	}

}
