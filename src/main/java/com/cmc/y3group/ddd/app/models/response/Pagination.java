package com.cmc.y3group.ddd.app.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pagination<T> {
	private List<T> contents = new ArrayList<>();
	private Integer totalPages;
	private Long totalElements;

	public Pagination() {
	}

	public Pagination(List<T> contents, Integer totalPages, Long totalElements) {
		this.contents = contents;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
	}
}
