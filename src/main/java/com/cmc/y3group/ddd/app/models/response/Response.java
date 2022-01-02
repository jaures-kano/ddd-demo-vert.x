package com.cmc.y3group.ddd.app.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
	private Integer code;
	private Integer status;
	private String message;
	private Object data;

	public static final class CODE {
		public static Integer OK = 0;
		public static Integer ERROR = 99;

	}

	public static final class STATUS {
		public static Integer SUCCESS = 0;
		public static Integer FAILURE = 1;
	}
}
