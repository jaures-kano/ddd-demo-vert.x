package com.cmc.y3group.ddd.domain.subdomain.product.cache;

import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import com.cmc.y3group.ddd.domain.subdomain.product.repositories.ProductRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class ProductCache {
	private final LoadingCache<String, Product> cache;

	@Autowired
	private ProductRepository productRepository;

	public ProductCache() {
		this.cache = CacheBuilder.newBuilder()
			.maximumSize(1000)
			.expireAfterWrite(10, TimeUnit.HOURS)
			.build(new CacheLoader<>() {
				@Override
				public Product load(String key) {
					return productRepository.findById(key).orElse(null);
				}
			});
	}

	public Product findById(String id) {
		try {
			return cache.get(id);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void put(String id, Product product) {
		cache.put(id, product);
	}
}
