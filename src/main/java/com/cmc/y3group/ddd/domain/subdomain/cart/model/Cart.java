package com.cmc.y3group.ddd.domain.subdomain.cart.model;

import com.cmc.y3group.ddd.domain.event.DomainEvents;
import com.cmc.y3group.ddd.domain.subdomain.cart.event.CartEvent;
import com.cmc.y3group.ddd.domain.subdomain.cart.event.CartEventBuilder;
import com.cmc.y3group.ddd.domain.subdomain.cart.factories.ItemBuilder;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = "carts")
public class Cart {
	@Id
	private String id;

	@OneToMany(cascade = ALL, mappedBy = "cartId", fetch = FetchType.EAGER)
	private List<CartItem> items;

	public void addProduct(Product product) {
		if (Objects.isNull(items)) items = new ArrayList<>();

		boolean exist = false;
		for (CartItem item : items) {
			Product productInCart = item.getProduct();
			if (productInCart.getId().equals(product.getId())) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			ItemBuilder itemBuilder = ItemBuilder.builder().setCart(this).setProduct(product);

			items.add(itemBuilder.build());
		}
	}

	public CartItem cartItem(Product product) {
		if (Objects.isNull(items))
			return null;

		for (CartItem item : items)
			if (product.getId().equals(item.getProduct().getId()))
				return item;

		return null;
	}

	public CartItem removeProduct(Product product) {
		if (Objects.isNull(items)) items = new ArrayList<>();
		Iterator<CartItem> itemIter = items.iterator();
		while (itemIter.hasNext()) {
			CartItem item = itemIter.next();
			if (product.getId().equals(item.getProduct().getId())) {
				itemIter.remove();
				return item;
			}
		}
		return null;
	}

	public Integer totalQuantity() {
		return Objects.isNull(items) ? 0 : items.stream().mapToInt(CartItem::getQuantity).sum();
	}

	public Integer totalItem() {
		return Objects.isNull(items) ? 0 : items.size();
	}

	public Double totalPrice() {
		return Objects.isNull(items) ? 0D : items.stream().mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice()).sum();
	}

	public void clean() {
		List<CartItem> items = this.items;

		this.items = new ArrayList<>();
		CartEvent evt = CartEventBuilder.builder()
			.setType(CartEvent.TYPE.CLEAN)
			.setUserId(getId())
			.setData(items)
			.build();
		DomainEvents.Cart.raise(evt);
	}
}
