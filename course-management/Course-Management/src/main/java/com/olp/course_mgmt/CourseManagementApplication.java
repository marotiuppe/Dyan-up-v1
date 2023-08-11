package com.olp.course_mgmt;

import java.util.Calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@PropertySources({ @PropertySource("classpath:restmessages.properties"),
	@PropertySource("classpath:queries.properties") })
public class CourseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseManagementApplication.class, args);

		System.out.println("*******************************************************************");
		System.out.println("Course Management Application Started @" + Calendar.getInstance().getTime());
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
