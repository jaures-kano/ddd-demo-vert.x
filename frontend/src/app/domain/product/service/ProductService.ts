import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {AFilter} from "../../../system/support/AFilter";
import { ProductRepository } from "../repository/ProductRepository";

@Injectable({providedIn: 'root'})
export class ProductService {
	constructor(
		private router: Router,
		private http: HttpClient,
		private productRepository: ProductRepository,
	) {
	}

	create(from: FormData) {
		return this.productRepository.create(from);
	}
}
