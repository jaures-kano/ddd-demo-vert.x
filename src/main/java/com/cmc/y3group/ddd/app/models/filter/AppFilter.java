package com.cmc.y3group.ddd.app.models.filter;

import lombok.Data;

@Data
public class AppFilter {
	private Integer pageNumber = 1;
	private Integer pageSize = 20;
	private String sort;
}
