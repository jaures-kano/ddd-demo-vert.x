package com.cmc.y3group.ddd.domain.subdomain.order.factories;

import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.CartItem;
import com.cmc.y3group.ddd.domain.subdomain.order.dto.OrderDTO;
import com.cmc.y3group.ddd.domain.subdomain.order.model.OrderAddress;
import com.cmc.y3group.ddd.domain.subdomain.order.model.OrderBuyer;
import com.cmc.y3group.ddd.domain.subdomain.order.model.OrderItem;
import com.cmc.y3group.ddd.domain.subdomain.order.model.Order;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Image;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.cmc.y3group.ddd.domain.subdomain.product.service.impl.ProductServiceImpl.STATIC_IMAGE;
import static java.util.Objects.*;

@Getter
public class OrderBuilder {
	private String id;
	private OrderAddress address;
	private List<OrderItem> items;
	private OrderBuyer buyer;
	private String userId;
	private String voucher;

	private OrderBuilder() {
	}

	public static OrderBuilder builder() {
		return new OrderBuilder();
	}

	public static OrderBuilder builder(Cart cart, OrderDTO orderDTO) {
		OrderBuilder orderBuilder = new OrderBuilder();

		// Items
		ArrayList<OrderItem> items = new ArrayList<>();
		if (!isNull(cart.getItems()) && !cart.getItems().isEmpty()) {
			for (CartItem item : cart.getItems()) {
				String imageUri = null;
				if (!isNull(item.getProduct().getImages()) && !item.getProduct().getImages().isEmpty()) {
					Image image = item.getProduct().getImages().get(0);
					String extension = FilenameUtils.getExtension(image.getFilename());
					imageUri = String.format(
						STATIC_IMAGE,
						item.getProduct().getId(),
						image.getId(),
						extension);
				}

				ItemBuilder itemBuilder = ItemBuilder.builder()
					.setProductId(item.getProduct().getId())
					.setTitle(item.getProduct().getTitle())
					.setDiscription(item.getProduct().getDiscription())
					.setPrice(item.getProduct().getPrice())
					.setQuantity(item.getQuantity())
					.setImage(imageUri);

				items.add(itemBuilder.build());
			}
		}

		orderBuilder.setItems(items);

		// Address
		AddressBuilder addrBuilder = AddressBuilder.builder()
			.setAddress(orderDTO.getAddress());
		orderBuilder.setAddress(addrBuilder.build());

		// Buyer
		BuyerBuilder buyerBuilder = BuyerBuilder.builder()
			.setFullName(orderDTO.getFullname())
			.setPhone(orderDTO.getPhone());
		orderBuilder.setBuyer(buyerBuilder.build());

		return orderBuilder;
	}

	public OrderBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public OrderBuilder setAddress(OrderAddress address) {
		this.address = address;
		return this;
	}

	public OrderBuilder setItems(List<OrderItem> items) {
		this.items = items;
		return this;
	}

	public OrderBuilder setBuyer(OrderBuyer buyer) {
		this.buyer = buyer;
		return this;
	}

	public OrderBuilder setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public OrderBuilder setVoucher(String voucher) {
		this.voucher = voucher;
		return this;
	}

	public Order build() {
		Order order = new Order();
		if (isNull(id))
			id = UUID.randomUUID().toString();

		if (!isNull(address))
			address.setOrder(order);

		if (!isNull(items))
			items.forEach(item -> item.setOrder(order));

		if (!isNull(buyer))
			buyer.setOrder(order);

		order.setId(id);
		order.setAddress(address);
		order.setItems(items);
		order.setBuyer(buyer);
		order.setUserId(userId);
		order.setVoucher(voucher);

		Timestamp now = Timestamp.from(Instant.now());
		order.setCreatedAt(now);
		order.setUpdatedAt(now);
		return order;
	}
}
