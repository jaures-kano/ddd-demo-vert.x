import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {Title} from "@angular/platform-browser";
import {AFilter} from "src/app/system/support/AFilter";
import {environment} from "../../../environments/environment";
import {ProductRepository} from "../../domain/product/repository/ProductRepository";
import {Str} from "../../system/support/Str";
import {CartService} from "../../domain/cart/service/CartService";
import {OrderService} from "../../domain/order/service/OrderService";

@Component({
	selector: 'div.app-home',
	templateUrl: './history.component.html',
	styleUrls: ["./history.component.css"]
})
export class HistoryComponent implements OnInit {
	environment = environment
	pageNumber: number = 1;
	pageSize: number = 20;
	collectionSize: number = 0
	dataPage: any;
	items: any;

	constructor(
		private title: Title,
		public str: Str,
		public orderService: OrderService
	) {
		let filter = new AFilter();
		orderService.findByFilter(filter).subscribe(response => {
			this.dataPage = response.data
			this.collectionSize = response.data.totalPages
			// this.pageSize = response.data.totalPages
			this.items = response.data.contents;
		});
	}

	ngOnInit(): void {
		this.title.setTitle('History' + environment.TITLE_POSTFIX);
	}

	refresh() {
		//
	}
}
