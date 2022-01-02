import {NgModule} from '@angular/core';
import {ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {RouterModule, Routes} from "@angular/router";
import {ProductCreateComponent} from "./components/create/ProductCreateComponent";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {JwtInterceptor} from "../../system/interceptor/JwtInterceptor";
import {ErrorInterceptor} from "../../system/interceptor/ErrorInterceptor";

const routes: Routes = [
	{
		path: 'create',
		component: ProductCreateComponent
	},
	{
		path: 'update/:id',
		component: ProductCreateComponent
	},
];

@NgModule({
	declarations: [
		ProductCreateComponent
	],
	imports: [
		CommonModule,
		// CKEditorModule,
		RouterModule.forChild(routes),
		ReactiveFormsModule,
	],
	providers: [
		{provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
		{provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}
	],
	exports: [RouterModule]
})

export class ProductModule {
}
