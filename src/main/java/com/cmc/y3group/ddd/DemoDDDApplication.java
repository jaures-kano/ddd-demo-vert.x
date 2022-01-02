package com.cmc.y3group.ddd;

import com.cmc.y3group.ddd.config.properties.WebProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties({WebProperties.class})
public class DemoDDDApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoDDDApplication.class, args);
	}

}
