import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {Title} from "@angular/platform-browser";
import {AFilter} from "src/app/system/support/AFilter";
import {environment} from "../../../environments/environment";
import {ProductRepository} from "../../domain/product/repository/ProductRepository";
import {Str} from "../../system/support/Str";
import {CartService} from "../../domain/cart/service/CartService";

@Component({
	selector: 'div.app-home',
	templateUrl: './home.component.html',
	styleUrls: ["./home.component.css"]
})
export class HomeComponent implements OnInit {
	environmentPage = environment.PAGE
	environment = environment
	pageNumber: number = 1;
	pageSize: number = 20;
	collectionSize: number = 0
	dataPage: any;
	items: any;

	constructor(
		private title: Title,
		private productRepository: ProductRepository,
		public str: Str,
		public cartService: CartService
	) {
		let filter = new AFilter();
		productRepository.findByFilter(filter).subscribe(response => {
			this.dataPage = response.data
			this.collectionSize = response.data.totalElements
			this.pageSize = response.data.totalPages
			this.items = response.data.contents;
			// contents: [{title: "Sản phẩm 1", slug: "san-pham-1-tthZ8boLCCggV0NO",…}]
			// totalElements: 1
			// totalPages: 1
		});
	}

	ngOnInit(): void {
		this.title.setTitle('Home' + environment.TITLE_POSTFIX);
	}

	addToCart(product: any) {
		this.cartService.addToCart(product.id);
	}

	refresh() {
		//
	}

}
