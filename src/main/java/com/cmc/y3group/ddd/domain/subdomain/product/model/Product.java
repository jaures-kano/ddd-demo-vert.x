package com.cmc.y3group.ddd.domain.subdomain.product.model;

import com.cmc.y3group.ddd.infrastructure.support.Str;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = "products", indexes = {
	@Index(name = "user_id", columnList = "userId")
})
public class Product {
	@Id
	private String id;

	@Column(unique = true, length = 512, nullable = false)
	private String slug;

	@Column(length = 512, nullable = false)
	private String title;

	@Column(nullable = false)
	private Boolean isPublish;

	@Column(nullable = false)
	private Boolean isDelete;

	@Lob
	private String discription;

	@Column(nullable = false)
	private Double price;

	@OneToMany(cascade = ALL, mappedBy = "productId", fetch = FetchType.EAGER)
	private List<Image> images;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(nullable = false)
	@UpdateTimestamp
	private Timestamp updatedAt;

	public void setTitle(String title) {
		this.title = title;
		this.slug = Str.slug(title, "-") + "-" + Str.random(16);
	}

	public void addImage(Image image) {
		if (Objects.isNull(images)) images = new ArrayList<>();

		images.add(image);
	}
}
