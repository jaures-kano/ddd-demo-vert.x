package com.cmc.y3group.ddd.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.webconfig")
public class WebProperties {
	private String host = "http://127.0.0.1";
	private Integer port = 80;
	private String staticPath;
}
