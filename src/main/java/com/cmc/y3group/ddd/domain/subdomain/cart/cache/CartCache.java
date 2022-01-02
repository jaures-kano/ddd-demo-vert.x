package com.cmc.y3group.ddd.domain.subdomain.cart.cache;

import com.cmc.y3group.ddd.domain.subdomain.cart.factories.CartBuilder;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import com.cmc.y3group.ddd.domain.subdomain.cart.repository.CartRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class CartCache {
	private final LoadingCache<String, Cart> cache;

	@Autowired
	private CartRepository cartRepository;

	public CartCache() {
		this.cache = CacheBuilder.newBuilder()
			.maximumSize(1000)
			.expireAfterWrite(10, TimeUnit.HOURS)
			.build(new CacheLoader<>() {
				@Override
				public Cart load(String key) {
					Optional<Cart> optionalCart = cartRepository.findById(key);
					if (optionalCart.isEmpty())
						return CartBuilder.builder().setId(key).build();
					return optionalCart.get();
				}
			});
	}

	public Cart findById(String id) {
		try {
			return cache.get(id);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
