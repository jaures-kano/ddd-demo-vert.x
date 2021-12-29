package com.cmc.y3group.ddd.domain.subdomain.cart.model;

import com.cmc.y3group.ddd.domain.subdomain.cart.service.CartService;
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
@Table(name = "carts")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(cascade = ALL, fetch = FetchType.EAGER)
	private List<CartItem> items;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	private transient CartService cartService;

	public void save() {
		cartService.save(this);
	}

	public void clear() {
		cartService.remove(this);
	}
}
