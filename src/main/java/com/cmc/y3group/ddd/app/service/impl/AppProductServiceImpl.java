package com.cmc.y3group.ddd.app.service.impl;

import com.cmc.y3group.ddd.app.models.response.Pagination;
import com.cmc.y3group.ddd.app.models.response.product.ProductResponse;
import com.cmc.y3group.ddd.config.properties.WebProperties;
import com.cmc.y3group.ddd.domain.subdomain.product.dto.ProductDTO;
import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.app.service.AppProductService;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import com.cmc.y3group.ddd.domain.subdomain.product.service.ProductService;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import io.vertx.core.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AppProductServiceImpl implements AppProductService {
	@Autowired
	private ProductService productService;

	@Autowired
	private WebProperties webProperties;

	@Override
	public Future<Pagination<ProductResponse>> findByFilter(AppFilter filter) {
		return Future.future(future -> {
			Page<Product> pageProduct = productService.findByFilter(filter);

			List<ProductResponse> productResList = new ArrayList<>();

			if (!pageProduct.getContent().isEmpty()) {
				for (Product product : pageProduct.getContent())
					productResList.add(new ProductResponse(product));
			}

			Pagination<ProductResponse> pageProductRes = new Pagination<>(
				productResList, pageProduct.getTotalPages(), pageProduct.getTotalElements());
			future.complete(pageProductRes);
		});
	}

	@Override
	public Future<ProductResponse> create(ProductDTO productDTO, User user) {
		return Future.future(future -> {
			Product product = productService.create(productDTO, user);
			future.complete(new ProductResponse(product));
		});
	}
}
