import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {UserService} from "../../domain/user/service/UserService";
import {environment} from "../../../environments/environment";
import {CartService} from "../../domain/cart/service/CartService";

@Component({
	selector: 'div.header-nav',
	templateUrl: './header.component.html',
	styleUrls: ["./header.component.css"]
})
export class HeaderComponent implements OnInit {
	environmentPage = environment.PAGE

	constructor(
		public userService: UserService,
		public cartService: CartService
	) {
	}

	ngOnInit(): void {
		this.cartService.info();
	}

}
