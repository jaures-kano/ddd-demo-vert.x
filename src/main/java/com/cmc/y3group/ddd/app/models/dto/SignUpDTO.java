package com.cmc.y3group.ddd.app.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpDTO {
	private String username;
	private String phone;
	private String email;
	private String password;
}