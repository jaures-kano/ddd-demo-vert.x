import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SignInComponent} from "./components/signin/SignInComponent";
import {SignUpComponent} from './components/signup/SignUpComponent';
import {HomeComponent} from "./components/home/HomeComponent";
import {HeaderComponent} from "./components/header/HeaderComponent";
import {SplashComponent} from "./components/splash/SplashComponent";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {JwtInterceptor} from "./system/interceptor/JwtInterceptor";
import {ErrorInterceptor} from "./system/interceptor/ErrorInterceptor";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {CartComponent} from "./components/cart/CartComponent";
import {OrderComponent} from "./components/order/OrderComponent";
import {HistoryComponent} from "./components/history/HistoryComponent";

@NgModule({
	declarations: [
		AppComponent,
		HeaderComponent,
		HomeComponent,
		SignInComponent,
		SignUpComponent,
		SplashComponent,
		CartComponent,
		OrderComponent,
		HistoryComponent
	],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        HttpClientModule,
        NgbModule,
        FormsModule
    ],
	providers: [
		{provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
		{provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}
	],
	bootstrap: [AppComponent]
})
export class AppModule {
}
