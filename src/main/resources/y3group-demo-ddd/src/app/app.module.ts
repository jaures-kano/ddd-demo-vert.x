import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {JwtInterceptor} from "./system/interceptor/JwtInterceptor";
import {ErrorInterceptor} from "./system/interceptor/ErrorInterceptor";
import {HomeComponent} from "./components/home/HomeComponent";
import {SignInComponent} from "./components/signin/SignInComponent";
import {SignUpComponent} from "./components/signup/SignUpComponent";
import {HeaderComponent} from "./components/header/HeaderComponent";
import {ReactiveFormsModule} from "@angular/forms";
import {SplashComponent} from "./components/splash/SplashComponent";

@NgModule({
	declarations: [
		AppComponent,
		HeaderComponent,
		HomeComponent,
		SignInComponent,
		SignUpComponent,
		SplashComponent,
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		NgbModule,
		ReactiveFormsModule,
		HttpClientModule,
	],
	providers: [
		{provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
		{provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}
	],
	bootstrap: [AppComponent]
})
export class AppModule {
}
