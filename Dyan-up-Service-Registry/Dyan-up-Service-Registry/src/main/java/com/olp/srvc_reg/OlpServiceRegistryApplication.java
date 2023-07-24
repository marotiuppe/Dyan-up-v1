package com.olp.srvc_reg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class OlpServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlpServiceRegistryApplication.class, args);
	}

}
