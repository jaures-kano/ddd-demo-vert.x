package com.cmc.y3group.ddd.domain.subdomain.buyer.model;

import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table
public class Buyer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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
}
