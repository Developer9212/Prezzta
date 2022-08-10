package com.fenoreste.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.fenoreste.rest.controller","com.fenoreste.rest.services","com.fenoreste.rest.consumoExterno","com.fenoreste.rest.Util","com.fenoreste.rest.security"})
@EntityScan("com.fenoreste.rest.entity")
@EnableJpaRepositories("com.fenoreste.rest.dao")

public class SpringPrezztaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPrezztaApplication.class, args);
	}

}
