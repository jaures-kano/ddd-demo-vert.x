package com.cmc.y3group.ddd.domain.subdomain.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.vertx.ext.web.FileUpload;
import lombok.Data;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
	private String title;
	private Double price;
	private String description;
	private Set<FileUpload> fileUploads;
}
