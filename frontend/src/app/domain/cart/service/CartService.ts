import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject} from "rxjs";
import {CookieService} from "../../../system/support/CookieService";
import {CartRepository} from "../repository/CartRepository";
import {UserService} from "../../user/service/UserService";

@Injectable({providedIn: 'root'})
export class CartService {
	public cartSubject: BehaviorSubject<number>;

	constructor(
		private router: Router,
		private http: HttpClient,
		private cartRepository: CartRepository,
		private userService: UserService,
		private cookieService: CookieService
	) {
		// @ts-ignore
		this.cartSubject = new BehaviorSubject<number>(0);
	}

	public get number(): number {
		return this.cartSubject.value;
	}

	addToCart(id: number) {
		this.cartRepository.add(id).subscribe(response => {
			if (response.data === void 0 || response.data.totalItem === void 0) {
				this.cartSubject.next(0)
			} else {
				this.cartSubject.next(response.data.totalItem)
			}
		})
	}

	info() {
		this.cartRepository.getCart().subscribe(response => {
			if (response.data === void 0 || response.data.totalItem === void 0) {
				this.cartSubject.next(0)
			} else {
				this.cartSubject.next(response.data.totalItem)
			}
		});
	}

	remove(id: number) {
		return this.cartRepository.remove(id)
	}
}
