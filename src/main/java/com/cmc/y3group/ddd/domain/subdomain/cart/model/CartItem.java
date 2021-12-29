package com.cmc.y3group.ddd.domain.subdomain.cart.model;

import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
@Table
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Product product;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(nullable = false)
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	private Cart cart;
}
