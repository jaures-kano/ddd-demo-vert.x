package com.cmc.y3group.ddd.domain.subdomain.product.factories;

import com.cmc.y3group.ddd.domain.subdomain.product.model.Image;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class ImageBuilder {
	private String id;
	private String filename;
	private String contentType;
	private Long size;
	private String productId;

	private ImageBuilder() {
	}

	public ImageBuilder setId(String id) {
		this.id = id;
		return this;
	}

	public ImageBuilder setFilename(String filename) {
		this.filename = filename;
		return this;
	}

	public ImageBuilder setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	public ImageBuilder setSize(Long size) {
		this.size = size;
		return this;
	}

	public ImageBuilder setProductId(String productId) {
		this.productId = productId;
		return this;
	}

	public static ImageBuilder builder() {
		return new ImageBuilder();
	}

	public Image build() {
		Image image = new Image();

		// TODO: Validate ...

		if (Objects.isNull(id))
			id = UUID.randomUUID().toString();

		image.setId(id);
		image.setFilename(filename);
		image.setContentType(contentType);
		image.setSize(size);
		image.setProductId(productId);

		Timestamp now = Timestamp.from(Instant.now());
		image.setCreatedAt(now);
		image.setUpdatedAt(now);

		return image;
	}
}
