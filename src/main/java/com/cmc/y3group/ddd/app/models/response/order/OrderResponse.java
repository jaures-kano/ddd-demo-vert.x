package com.cmc.y3group.ddd.app.models.response.order;

import com.cmc.y3group.ddd.config.constants.TimeConstant;
import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import com.cmc.y3group.ddd.domain.subdomain.order.model.OrderAddress;
import com.cmc.y3group.ddd.domain.subdomain.order.model.OrderBuyer;
import com.cmc.y3group.ddd.domain.subdomain.order.model.OrderItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
	private String id;
	private List<ItemResponse> items;
	private AddressResponse address;
	private BuyerResponse buyer;
	private Integer totalQuantity;
	private Integer totalItem;
	private Double totalPrice;
	private String createAt;
	private String updateAt;

	public OrderResponse() {
	}

	public OrderResponse(Order order) {
		if (Objects.isNull(order)) return;

		this.id = order.getId();
		this.totalQuantity = order.totalQuantity();
		this.totalPrice = order.totalPrice();
		this.totalItem = order.totalItem();

		this.createAt = order.getCreatedAt().toLocalDateTime().format(TimeConstant.formatterDate);
		this.updateAt = order.getUpdatedAt().toLocalDateTime().format(TimeConstant.formatterDate);

		this.address = new AddressResponse(order.getAddress());
		this.buyer = new BuyerResponse(order.getBuyer());

		for (OrderItem item : order.getItems())
			this.addItem(new ItemResponse(item));
	}

	public void addItem(ItemResponse itemRes) {
		if (Objects.isNull(items)) items = new ArrayList<>();
		items.add(itemRes);
	}

	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private static class AddressResponse {
		private String address;

		public AddressResponse() {
		}

		public AddressResponse(OrderAddress address) {
			this.address = address.getAddress();
		}
	}

	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private static class BuyerResponse {
		private String fullName;
		private String phone;
		private String email;

		public BuyerResponse() {
		}

		public BuyerResponse(OrderBuyer buyer) {
			this.fullName = buyer.getFullName();
			this.phone = buyer.getPhone();
			this.email = buyer.getEmail();
		}
	}

	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private static class ItemResponse {
		private String title;
		private String description;
		private String image;
		private Integer quantity;
		private Double price;

		public ItemResponse() {
		}

		public ItemResponse(OrderItem item) {
			this.title = item.getTitle();
			this.description = item.getDiscription();
			this.image = item.getImage();
			this.quantity = item.getQuantity();
			this.price = item.getPrice();
		}
	}
}
