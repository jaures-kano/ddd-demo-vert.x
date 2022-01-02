package com.cmc.y3group.ddd.domain.subdomain.order.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
@Table
public class OrderBuyer {
	@Id
	private String id;
	private String fullName;
	private String phone;
	private String email;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Order order;
}
