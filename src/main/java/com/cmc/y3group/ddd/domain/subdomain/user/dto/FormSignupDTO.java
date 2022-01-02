package com.cmc.y3group.ddd.domain.subdomain.user.dto;

import lombok.Data;

@Data
public class FormSignupDTO extends CredentialDTO {
	private String name;
	private String email;
	private String phone;
}
