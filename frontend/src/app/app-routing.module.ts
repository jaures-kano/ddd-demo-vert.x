import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./components/home/HomeComponent";
import {AuthGuard} from "./domain/user/authentication/guard/AuthGuard";
import {SignInComponent} from "./components/signin/SignInComponent";
import {environment} from "../environments/environment";
import {GuestGuard} from "./domain/user/authentication/guard/GuestGuard";
import {SignUpComponent} from "./components/signup/SignUpComponent";
import {CartComponent} from "./components/cart/CartComponent";
import {OrderComponent} from "./components/order/OrderComponent";
import {HistoryComponent} from "./components/history/HistoryComponent";

const routes: Routes = [
	{
		path: '',
		component: HomeComponent,
		canActivate: [AuthGuard]
	},
	{
		path: environment.PAGE.SIGNIN,
		component: SignInComponent,
		canActivate: [GuestGuard]
	},
	{
		path: environment.PAGE.SIGNUP,
		component: SignUpComponent,
		canActivate: [GuestGuard]
	},
	{
		path: environment.PAGE.CART,
		component: CartComponent,
		canActivate: [AuthGuard]
	},
	{
		path: environment.PAGE.ORDER,
		component: OrderComponent,
		canActivate: [AuthGuard]
	},

	{
		path: environment.PAGE.HISTORY,
		component: HistoryComponent,
		canActivate: [AuthGuard]
	},

	{
		path: 'product',
		loadChildren: () => import('./modules/product/product.module').then(m => m.ProductModule)
	},
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule {
}
