package com.cmc.y3group.ddd.domain.subdomain.product.service;

import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.domain.subdomain.product.dto.ProductDTO;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import org.springframework.data.domain.Page;

public interface ProductService {
	Page<Product> findByFilter(AppFilter filter);

	Product create(ProductDTO productDTO, User user);
}
