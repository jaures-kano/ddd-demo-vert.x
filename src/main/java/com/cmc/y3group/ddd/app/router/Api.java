package com.cmc.y3group.ddd.app.router;

import com.cmc.y3group.ddd.app.http.controller.api.cart.*;
import com.cmc.y3group.ddd.app.http.controller.api.order.*;
import com.cmc.y3group.ddd.app.http.controller.api.product.*;
import com.cmc.y3group.ddd.app.http.controller.api.user.*;
import com.cmc.y3group.ddd.infrastructure.system.vertx.http.ApiRouter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Api extends ApiRouter {

	@PostConstruct
	public void registry() {
		/**
		 * User API
		 * */
		post(SignInApi.PATH, SignInApi.class);
		post(SignUpApi.PATH, SignUpApi.class);
		get(UserInfoApi.PATH, UserInfoApi.class);

		/**
		 * Product API
		 * */
		get(ProductApi.PATH, ProductApi.class);

		/**
		 * Cart API
		 * */
		get(CartApi.PATH, CartApi.class);
		get(CartAddApi.PATH, CartAddApi.class);
		get(CartInfoApi.PATH, CartInfoApi.class);
		get(CartRemoveApi.PATH, CartRemoveApi.class);

		/**
		 * Order API
		 * */
		get(OrderApi.PATH, OrderApi.class);
		post(OrderCreateApi.PATH, OrderCreateApi.class);
	}
}
