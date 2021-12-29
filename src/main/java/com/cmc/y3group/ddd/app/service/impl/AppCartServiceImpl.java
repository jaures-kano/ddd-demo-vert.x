package com.cmc.y3group.ddd.app.service.impl;

import com.cmc.y3group.ddd.app.service.AppCartService;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.CartItem;
import com.cmc.y3group.ddd.domain.subdomain.cart.repository.CartRepository;
import com.cmc.y3group.ddd.domain.subdomain.cart.service.CartService;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import com.cmc.y3group.ddd.domain.subdomain.product.repositories.ProductRepository;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import com.cmc.y3group.ddd.domain.subdomain.user.repositories.UserRepository;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AppCartServiceImpl implements AppCartService {
	@Autowired
	private CartService cartService;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	/** {@inheritDoc} */
	@Override
	public Future<Cart> getCart(Long userId) {
		return Future.future(future -> {
			Optional<User> user = userRepository.findById(userId);
			if (user.isPresent()) {
				Cart cart = cartRepository.findByUser(user.get());
				future.complete(cart);
			} else
				future.complete(null);
		});
	}

	@Override
	public Future<Long> add(Long userId, Long productId) {
		return Future.future(future -> {
			long quantity = 0;
			Optional<User> user = userRepository.findById(userId);
			Optional<Product> product = productRepository.findById(productId);
			if (user.isPresent() && product.isPresent()) {
				Cart cart = cartRepository.findByUser(user.get());
				CartItem cartItem = new CartItem();
				cartItem.setProduct(product.get());
				if (Objects.isNull(cart)) {
					cart = new Cart();
					cart.setUser(user.get());
					cart.setItems(Collections.singletonList(cartItem));
				} else {
					List<CartItem> cartItemList = cart.getItems();
					boolean exist = false;
					for (CartItem cartItem1 : cartItemList) {
						Product product1 = cartItem1.getProduct();
						if (product1.getId().equals(product.get().getId())) {
							exist = true;
							break;
						}
					}
					if (!exist) cart.getItems().add(cartItem);
				}
				cart.setCartService(cartService);
				quantity = cart.getItems().size();
				cart.save();
			}
			future.complete(quantity);
		});
	}

	@Override
	public Future<Long> remove(Long userId, Long productId) {
		return Future.future(future -> {
			long quantity = 0;
			Optional<User> user = userRepository.findById(userId);
			if (user.isPresent()) {
				Cart cart = cartRepository.findByUser(user.get());
				if (!Objects.isNull(cart)) {
					List<CartItem> cartItemList = cart.getItems();
					Iterator<CartItem> iter = cartItemList.iterator();
					while (iter.hasNext()) {
						CartItem cartItem = iter.next();

						if (cartItem.getProduct().getId().equals(productId)) {
							iter.remove();
							break;
						}
					}
					cart.setCartService(cartService);
					quantity = cart.getItems().size();
					cart.save();
				}
			}
			future.complete(quantity);
		});
	}
}
