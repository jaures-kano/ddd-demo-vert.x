package com.cmc.y3group.ddd.app.models.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
	private Long id;
	private String username;
	private String session;
}
