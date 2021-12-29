package com.cmc.y3group.ddd.domain.subdomain.order.model;

import com.cmc.y3group.ddd.domain.event.DomainEvents;
import com.cmc.y3group.ddd.domain.subdomain.order.event.OrderEvent;
import com.cmc.y3group.ddd.domain.subdomain.order.event.OrderEventBuilder;
import com.cmc.y3group.ddd.domain.subdomain.order.service.OrderService;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String phone;

	@OneToOne(cascade = ALL, mappedBy = "order")
	private OrderAddress orderAddress;

	@OneToMany(cascade = ALL, mappedBy = "order")
	private List<OrderItem> items;

	@ManyToOne
	@JoinColumn(nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;


	//
	private transient OrderService orderService;

	public void create() {
		orderService.save(this);
		OrderEvent evt = OrderEventBuilder.builder()
			.setOrder(this)
			.setType(OrderEvent.TYPE.CREATE)
			.build();
		DomainEvents.Order.Raise(evt);
	}

	public void update() {
		orderService.save(this);
		OrderEvent evt = OrderEventBuilder.builder()
			.setOrder(this)
			.setType(OrderEvent.TYPE.UPDATE)
			.build();
		DomainEvents.Order.Raise(evt);
	}

	public Integer getQuantity() {
		return this.getItems().size();
	}

	public Double getPrice() {
		Double price = 0D;
		for (OrderItem orderItem : this.getItems())
			price += orderItem.getPrice();

		return price;
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
