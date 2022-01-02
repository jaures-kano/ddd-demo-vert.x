package com.cmc.y3group.ddd.app.service;

import com.cmc.y3group.ddd.app.models.response.Pagination;
import com.cmc.y3group.ddd.app.models.response.product.ProductResponse;
import com.cmc.y3group.ddd.domain.subdomain.product.dto.ProductDTO;
import com.cmc.y3group.ddd.app.models.filter.AppFilter;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import io.vertx.core.Future;

public interface AppProductService {
	Future<Pagination<ProductResponse>> findByFilter(AppFilter filter);
	Future<ProductResponse> create(ProductDTO productDTO, User user);
}
