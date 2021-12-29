import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./components/home/HomeComponent";
import {GuestGuard} from "./domain/user/authentication/guard/GuestGuard";
import {SignInComponent} from "./components/signin/SignInComponent";
import {SignUpComponent} from "./components/signup/SignUpComponent";
import {AuthGuard} from "./domain/user/authentication/guard/AuthGuard";
import {environment} from "../environments/environment";

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
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule {
}
