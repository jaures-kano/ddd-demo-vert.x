package com.cmc.y3group.ddd.domain.subdomain.cart.model;

import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {
	@Id
	private String id;

	private Integer quantity;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Product product;

	private String cartId;

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private Timestamp updatedAt;
}
