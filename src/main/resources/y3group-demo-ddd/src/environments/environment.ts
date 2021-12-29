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
		PRODUCTION: "product",
		CART: "cart",
		ORDER: "order",
	},
	// HOST
	API_HOST: 'http://127.0.0.1:8081',
	// API AUTH
	API_SIGNIN: '/api/user/signin',
	API_SIGNUP: '/api/user/signup',
	API_USER_INFO: '/api/user/info',
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.


