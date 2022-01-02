import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {AFilter} from "../../../system/support/AFilter";

@Injectable({providedIn: 'root'})
export class OrderRepository {
	constructor(
		private http: HttpClient
	) {
	}

	create(order: any) {
		return this.http.post<any>(`${environment.API_HOST}${environment.API_ORDER_CREATE}`, order);
	}

	findByFilter (filter: AFilter) {
		return this.http.get<any>(`${environment.API_HOST}${environment.API_ORDER}?pageNumber=${filter.pageNumber}`);
	}
}
