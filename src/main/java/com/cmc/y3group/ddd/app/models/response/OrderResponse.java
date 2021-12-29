package com.cmc.y3group.ddd.app.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
	private Long id;
	private String phone;
	private String address;
	private List<OrderItem> items;
	private Integer quantity;
	private Double price;
	private String createdAt;
	private String updatedAt;

	@Data
	public static class OrderItem {
		private String name;
		private String discription;
		private Double price;
	}
}
