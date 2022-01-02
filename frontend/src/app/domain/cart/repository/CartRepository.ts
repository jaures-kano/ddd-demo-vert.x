import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {Observable} from "rxjs";
import {Response} from "../../../system/support/Response";
import {AFilter} from "../../../system/support/AFilter";

@Injectable({providedIn: 'root'})
export class CartRepository {
	constructor(
		private http: HttpClient
	) {
	}

	getNumberedItems(): Observable<Response<any>> {
		return this.http.get<any>(`${environment.API_HOST}${environment.API_CART}`);
	}

	getCart(): Observable<Response<any>> {
		return this.http.get<any>(`${environment.API_HOST}${environment.API_CART}`);
	}

	info(): Observable<Response<any>> {
		return this.http.get<any>(`${environment.API_HOST}${environment.API_CART_INFO}`);
	}

	add(productId: number): Observable<Response<any>> {
		return this.http.get<any>(`${environment.API_HOST}${environment.API_CART_ADD}?id=${productId}`);
	}

	remove(productId: number): Observable<Response<any>> {
		return this.http.get<any>(`${environment.API_HOST}${environment.API_CART_REMOVE}?id=${productId}`);
	}
}
