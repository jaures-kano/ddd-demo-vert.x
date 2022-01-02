import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {OrderRepository} from "../repository/OrderRepository";
import {AFilter} from "../../../system/support/AFilter";

@Injectable({providedIn: 'root'})
export class OrderService {
	constructor(
		private router: Router,
		private http: HttpClient,
		private orderRepository: OrderRepository,
	) {
	}

	create(order: any) {
		return this.orderRepository.create(order);
	}

	findByFilter (filter: AFilter) {
		return this.orderRepository.findByFilter(filter);
	}
}
