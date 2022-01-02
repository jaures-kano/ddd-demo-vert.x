package com.cmc.y3group.ddd.domain.subdomain.user.dto;

import lombok.Data;

@Data
public class CredentialDTO {
	private String ip;
	private Integer port;
	private String agent;

	private String username;
	private String password;
	private Boolean remember_me = false;
}
