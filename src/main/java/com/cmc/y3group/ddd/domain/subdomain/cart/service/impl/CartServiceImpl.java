package com.cmc.y3group.ddd.domain.subdomain.cart.service.impl;

import com.cmc.y3group.ddd.domain.event.DomainEvents;
import com.cmc.y3group.ddd.domain.subdomain.cart.cache.CartCache;
import com.cmc.y3group.ddd.domain.subdomain.cart.event.CartDomainEvent;
import com.cmc.y3group.ddd.domain.subdomain.cart.event.CartEvent;
import com.cmc.y3group.ddd.domain.subdomain.cart.event.CartEventBuilder;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.Cart;
import com.cmc.y3group.ddd.domain.subdomain.cart.model.CartItem;
import com.cmc.y3group.ddd.domain.subdomain.cart.repository.CartRepository;
import com.cmc.y3group.ddd.domain.subdomain.cart.repository.ItemRepository;
import com.cmc.y3group.ddd.domain.subdomain.cart.service.CartService;
import com.cmc.y3group.ddd.domain.subdomain.product.cache.ProductCache;
import com.cmc.y3group.ddd.domain.subdomain.product.model.Product;
import com.cmc.y3group.ddd.domain.subdomain.user.model.User;
import io.vertx.core.eventbus.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartCache cartCache;

	@Autowired
	private ProductCache productCache;

	@Autowired
	private ItemRepository itemRepository;

	public CartServiceImpl() {
		DomainEvents.evtBus.consumer(CartDomainEvent.TOPIC, this::onChangeCart);
	}

	private void onChangeCart(Message<ArrayList<CartEvent>> msg) {
		ArrayList<CartEvent> events = msg.body();

		CartEvent evtFirst = events.get(0);
		Cart cart = cartCache.findById(evtFirst.getUserId());
		Set<CartItem> itemNeedDelete = new HashSet<>();

		if (CartEvent.TYPE.CLEAN.equals(evtFirst.getType()))
			itemNeedDelete.addAll((List<CartItem> ) evtFirst.getData());
		else {
			boolean needSave = false;

			for (CartEvent event : events) {
				if (CartEvent.TYPE.ADD.equals(event.getType())) needSave = true;

				if (CartEvent.TYPE.REMOVE.equals(event.getType()))
					itemNeedDelete.add((CartItem) event.getData());

				if (CartEvent.TYPE.CLEAN.equals(event.getType()))
					itemNeedDelete.addAll((List<CartItem> ) event.getData());
			}

			if (needSave)
				cartRepository.save(cart);
		}
		itemRepository.deleteAll(itemNeedDelete);
	}

	@Override
	public void save(Cart cart) {
		cartRepository.save(cart);
	}

	@Override
	public void remove(Cart cart) {
		cartRepository.delete(cart);
	}

	@Override
	public Cart findCartByUser(User user) {
		return findCartByUserId(user.getId());
	}

	@Override
	public Cart findCartByUserId(String userId) {
		return cartCache.findById(userId);
	}

	@Override
	public Cart addProductToCart(User user, String productId) {
		Product product = productCache.findById(productId);
		Cart cart = cartCache.findById(user.getId());
		cart.addProduct(product);
		CartEvent evt = CartEventBuilder.builder().setType(CartEvent.TYPE.ADD).setUserId(user.getId()).setData(product).build();
		DomainEvents.Cart.raise(evt);

		return cart;
	}

	@Override
	public Cart removeProduct(User user, String productId) {
		Product product = productCache.findById(productId);
		Cart cart = cartCache.findById(user.getId());
		CartItem cartItem = cart.removeProduct(product);

		// Raise
		CartEvent evt = CartEventBuilder.builder().setType(CartEvent.TYPE.REMOVE).setUserId(user.getId()).setData(cartItem).build();
		DomainEvents.Cart.raise(evt);

		return cart;
	}
}
