package com.cmc.y3group.ddd.domain.subdomain.product.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "product_images")
public class Image {
	@Id
	private String id;

	private String filename;
	private String contentType;
	private Long size;

	private String productId;

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private Timestamp updatedAt;
}
