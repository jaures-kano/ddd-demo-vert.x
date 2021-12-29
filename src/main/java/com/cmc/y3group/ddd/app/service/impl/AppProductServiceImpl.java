package com.cmc.y3group.ddd.app.service.impl;

import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.app.service.AppProductService;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import com.cmc.y3group.ddd.domain.subdomain.product.repositories.ProductRepository;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AppProductServiceImpl implements AppProductService {
	@Autowired
	private ProductRepository productRpository;

	@Override
	public Future<Page<Product>> findByFilter(AppFilter filter) {
		return Future.future(future -> {
			int pageNumber = Math.max(0, filter.getPageNumber() - 1);
			Pageable pageable = PageRequest.of(pageNumber, filter.getPageSize());
			Page<Product> page = productRpository.findAll(pageable);
			future.complete(page);
		});
	}
}
