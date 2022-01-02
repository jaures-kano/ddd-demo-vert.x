package com.cmc.y3group.ddd.app.models.response.product;

import com.cmc.y3group.ddd.domain.subdomain.product.model.Image;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.cmc.y3group.ddd.domain.subdomain.product.service.impl.ProductServiceImpl.STATIC_IMAGE;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
	private String id;
	private String title;
	private String slug;
	private String description;
	private Double price;
	private List<String> images;

	public ProductResponse(){}

	public ProductResponse(Product product){
		this.setId(product.getId());
		this.setTitle(product.getTitle());
		this.setSlug(product.getSlug());
		this.setDescription(product.getDiscription());
		this.setPrice(product.getPrice());

		if (!Objects.isNull(product.getImages()) && !product.getImages().isEmpty()) {
			for (Image image : product.getImages()) {
				String extension = FilenameUtils.getExtension(image.getFilename());
				String imageUri = String.format(
					STATIC_IMAGE,
					product.getId(),
					image.getId(),
					extension);
				this.addImage(imageUri);
			}
		} else this.setImages(Collections.EMPTY_LIST);
	}

	public void addImage(String image) {
		if (Objects.isNull(images))
			images = new ArrayList<>();

		images.add(image);
	}
}
