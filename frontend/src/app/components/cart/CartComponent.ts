import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {Title} from "@angular/platform-browser";
import {environment} from "../../../environments/environment";
import {Str} from "../../system/support/Str";
import {CartService} from "../../domain/cart/service/CartService";
import {CartRepository} from "../../domain/cart/repository/CartRepository";

@Component({
	selector: 'div.app-home',
	templateUrl: './cart.component.html',
	styleUrls: ["./cart.component.css"]
})
export class CartComponent implements OnInit {
	environment = environment
	cart: any;
	items: any;

	environmentPage = environment.PAGE

	constructor(
		private title: Title,
		private cartRepository: CartRepository,
		public str: Str,
		public cartService: CartService
	) {
		cartRepository.getCart().subscribe(response => {
			this.cart = response.data
			this.refreshView()
		});
	}

	ngOnInit(): void {
		this.title.setTitle('Cart' + environment.TITLE_POSTFIX);
	}

	remove(product: any) {
		this.cartService.remove(product.id).subscribe(response => {
			this.cart = response.data
			this.refreshView()

			if (response.data === void 0 || response.data.totalItem === void 0) {
				this.cartService.cartSubject.next(0)
			} else {
				this.cartService.cartSubject.next(response.data.totalItem)
			}
		})
	}

	refreshView() {
		if (this.cart === void 0) {
			this.items = []
			return;
		}
		this.items = this.cart.items;
	}

	refresh() {

	}

}
