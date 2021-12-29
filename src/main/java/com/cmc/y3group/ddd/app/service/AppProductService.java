package com.cmc.y3group.ddd.app.service;

import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import io.vertx.core.Future;
import org.springframework.data.domain.Page;

public interface AppProductService {
	Future<Page<Product>> findByFilter(AppFilter filter);
}
