// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
	production: false,
	TITLE_POSTFIX: " - Y3Group demo DDD",
	PAGE: {
		LASH: "/",
		SIGNIN: "signin",
		SIGNUP: "signup",
		PRODUCT: "product",
		PRODUCT_CREATE: "product/create",
		CART: "cart",
		ORDER: "order",
		HISTORY: "history"
	},
	// HOST
	API_HOST: 'http://127.0.0.1:8081',
	URL: 'http://127.0.0.1:8081',
	// API AUTH
	API_SIGNIN: '/api/user/signin',
	API_SIGNUP: '/api/user/signup',
	API_USER_INFO: '/api/user/info',

	// API PRODUCTS
	API_PRODUCT: '/api/product',
	API_PRODUCT_CREATE: '/api/product/create',

	// Cart API
	API_CART: '/api/cart',
	API_CART_INFO: '/api/cart/info',
	API_CART_ADD: '/api/cart/add',
	API_CART_REMOVE: '/api/cart/remove',

	// Order API
	API_ORDER: '/api/order',
	API_ORDER_CREATE: '/api/order/create',

};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
