package com.cmc.y3group.ddd.domain.subdomain.product.factories;

import com.cmc.y3group.ddd.domain.subdomain.product.model.Image;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ProductBuilder {
	private String id;
	private String title;
	private Boolean publish = true;
	private Boolean delete = false;
	private String discription;
	private Double price;
	private List<Image> images;
	private String userId;

	private ProductBuilder() {
	}

	public ProductBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public ProductBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	public ProductBuilder setPublish(Boolean publish) {
		this.publish = publish;
		return this;
	}

	public ProductBuilder setDelete(Boolean delete) {
		this.delete = delete;
		return this;
	}

	public ProductBuilder setDiscription(String discription) {
		this.discription = discription;
		return this;
	}

	public ProductBuilder setPrice(Double price) {
		this.price = price;
		return this;
	}

	public ProductBuilder setImages(List<Image> images) {
		this.images = images;
		return this;
	}

	public ProductBuilder setUserId(String userId) {
		this.userId = userId;
		return this;
	}


	public static ProductBuilder builder() {
		return new ProductBuilder();
	}

	public Product build() {
		Product product = new Product();

		// TODO: Validate ...

		if (Objects.isNull(id))
			id = UUID.randomUUID().toString();

		product.setId(id);
		product.setTitle(title);
		product.setImages(images);
		product.setPrice(price);
		product.setDiscription(discription);
		product.setIsPublish(publish);
		product.setIsDelete(delete);
		product.setUserId(userId);

		Timestamp now = Timestamp.from(Instant.now());
		product.setCreatedAt(now);
		product.setUpdatedAt(now);

		return product;
	}
}
