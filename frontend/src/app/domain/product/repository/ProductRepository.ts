import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {Observable} from "rxjs";
import {Response} from "../../../system/support/Response";
import {AFilter} from "../../../system/support/AFilter";

@Injectable({providedIn: 'root'})
export class ProductRepository {
	constructor(
		private http: HttpClient
	) {
	}

	findByFilter(filter: AFilter): Observable<Response<any>> {
		return this.http.get<any>(`${environment.API_HOST}${environment.API_PRODUCT}?pageNumber=${filter.pageNumber}`);
	}

	create(from: FormData): Observable<Response<any>> {
		return this.http.post<any>(`${environment.API_HOST}${environment.API_PRODUCT_CREATE}`, from);
	}
}
