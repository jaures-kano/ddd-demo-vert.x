import {Component, OnInit} from "@angular/core";
import {Title} from "@angular/platform-browser";
import {environment} from "../../../environments/environment";
import {Str} from "../../system/support/Str";
import {CartRepository} from "../../domain/cart/repository/CartRepository";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {OrderService} from "../../domain/order/service/OrderService";
import {CartService} from "../../domain/cart/service/CartService";
import {Router} from "@angular/router";
import {SplashService} from "../splash/SplashService";

@Component({
	selector: 'div.app-home',
	templateUrl: './order.component.html',
	styleUrls: ["./order.component.css"]
})
export class OrderComponent implements OnInit {
	environment = environment
	cart: any;
	items: any;

	form: FormGroup;

	constructor(
		private title: Title,
		private cartRepository: CartRepository,
		public str: Str,
		private router: Router,
		private formBuilder: FormBuilder,
		public orderService: OrderService,
		private cartService: CartService,
		public splashService: SplashService
	) {
		cartRepository.getCart().subscribe(response => {
			this.cart = response.data
			this.refreshView()
		});

		this.form = this.formBuilder.group({
			fullname: ['', [
				Validators.required,
				Validators.minLength(3)]],
			address: ['', [
				Validators.required,
				Validators.minLength(3)]],
			phone: ['', [
				Validators.required,
				Validators.minLength(3)]],
		});
	}

	ngOnInit(): void {
		this.title.setTitle('Order' + environment.TITLE_POSTFIX);
	}

	refreshView() {
		if (this.cart === void 0) {
			this.items = []
			return;
		}
		this.items = this.cart.items;
	}

	onSubmit() {
		if (!this.form.invalid) {
			this.splashService.on();
			let orderInfor = this.form.value;
			this.orderService.create(orderInfor).subscribe(
				response => {
					this.cartService.cartSubject.next(0);
					setTimeout(() => {
						this.router.navigateByUrl("/" + environment.PAGE.HISTORY)
						this.splashService.off();
					}, 500);
				},
				error => {
					console.log(error)
					this.splashService.off();
				});
		}
	}


}
