package com.cmc.y3group.ddd.domain.subdomain.product.model;

import com.cmc.y3group.ddd.domain.subdomain.product.service.ProductService;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Lob
	private String discription;
	private Double price;

	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	private transient ProductService productService;

	public void save() {
		productService.save(this);
	};
}
