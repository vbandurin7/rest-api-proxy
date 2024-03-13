package com.vk.restapiproxy;

import com.vk.restapiproxy.configuration.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@EnableConfigurationProperties(ApplicationProperties.class)
@EnableJpaAuditing
@EnableCaching
public class RestApiProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiProxyApplication.class, args);
	}

}
