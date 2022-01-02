package com.cmc.y3group.ddd.domain.subdomain.order.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_items", indexes = {@Index(name = "product_id", columnList = "productId")})
public class OrderItem {
	@Id
	private String id;

	private String productId;

	private String title;

	private Integer quantity;

	private Double price;

	private String image;

	private String voucher;

	@Lob
	private String discription;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Order order;
}
