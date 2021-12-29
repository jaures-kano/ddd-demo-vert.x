package com.cmc.y3group.ddd.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.webconfig")
public class WebProperties {
	private Integer port = 80;
	private String staticPath;
}
