package com.cmc.y3group.ddd.domain.subdomain.order.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = "orders", indexes = {@Index(name = "user_id", columnList = "userId")})
public class Order {
	@Id
	private String id;

	@OneToOne(cascade = ALL, mappedBy = "order", fetch = FetchType.EAGER)
	private OrderAddress address;

	@OneToMany(cascade = ALL, mappedBy = "order", fetch = FetchType.EAGER)
	private List<OrderItem> items;

	@OneToOne(cascade = ALL, mappedBy = "order", fetch = FetchType.EAGER)
	private OrderBuyer buyer;

	@Column(nullable = false)
	private String userId;

	private String voucher;

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private Timestamp updatedAt;

//	public void create() {
//		orderService.save(this);
//		OrderEvent evt = OrderEventBuilder.builder()
//			.setOrder(this)
//			.setType(OrderEvent.TYPE.CREATE)
//			.build();
////		DomainEvents.Order.raise(evt);
//	}

//	public void update() {
//		orderService.save(this);
//		OrderEvent evt = OrderEventBuilder.builder()
//			.setOrder(this)
//			.setType(OrderEvent.TYPE.UPDATE)
//			.build();
////		DomainEvents.Order.raise(evt);
//	}

	public Integer totalQuantity() {
		return Objects.isNull(items) ? 0 : items.stream().mapToInt(OrderItem::getQuantity).sum();
	}

	public Integer totalItem() {
		return Objects.isNull(items) ? 0 : items.size();
	}

	public Double totalPrice() {
		return Objects.isNull(items) ?
			0D :
			items.stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
	}

//	public void update() {
//		OrderEvent event = new OrderEvent(this, "update");
//		DomainEvents.Order.Raise(event);
//	}
//
//	public void payment() {
//		OrderEvent event = new OrderEvent(this, "payment");
//		DomainEvents.Order.Raise(event);
//	}
//
//	public void reject() {
//		OrderEvent event = new OrderEvent(this, "reject");
//		DomainEvents.Order.Raise(event);
//	}
}
