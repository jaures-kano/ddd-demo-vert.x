package com.cmc.y3group.ddd.domain.subdomain.product.service.impl;

import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import com.cmc.y3group.ddd.domain.subdomain.product.repositories.ProductRepository;
import com.cmc.y3group.ddd.domain.subdomain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRpository;

	@Override
	public void save(Product product) {
		productRpository.save(product);
	}
}
