import {Injectable} from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {environment} from "../../../../../environments/environment";
import {UserService} from "../../service/UserService";
import {CookieService} from "../../../../system/support/CookieService";

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {
	constructor(
		private router: Router,
		private userService: UserService,
		private cookieService: CookieService
	) {
	}

	async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
		let guardOsv = this.userService.guard();
		if (guardOsv !== null) {
			await guardOsv.toPromise().then(response => {
				let user = response.data;
				user.session = this.cookieService.get("session");
				console.log(user);

				this.userService.userSubject.next(user)
			}, err => {
				console.log(err);
			})
		}
		const user = this.userService.user;
		if (user) {
			return true;
		} else {
			this.router.navigate([environment.PAGE.SIGNIN]);
			return false;
		}
	}
}
