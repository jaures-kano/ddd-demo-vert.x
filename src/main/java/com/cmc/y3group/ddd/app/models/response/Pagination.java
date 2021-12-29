package com.cmc.y3group.ddd.app.models.response;

import com.cmc.y3group.ddd.infrastructure.support.Function;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pagination<I, O> {
	private List<O> contents = new ArrayList<>();
	private Integer totalPages;
	private Long totalElements;

	public Pagination() {
	}

	public Pagination(Page<I> page, Function.BitFunction<I, O> function) {
		totalPages = page.getTotalPages();
		totalElements = page.getTotalElements();

		if (!CollectionUtils.isEmpty(page.getContent())) {
			page.getContent().forEach(content -> {
				O output = function.apply(content);
				contents.add(output);
			});
		}
	}
}
