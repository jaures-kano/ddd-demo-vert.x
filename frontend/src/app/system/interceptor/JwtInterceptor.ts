import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {CookieService} from "../support/CookieService";
import {UserService} from "../../domain/user/service/UserService";

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
	constructor(
		private userService: UserService,
		private cookieService: CookieService,
	) {
	}

	intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		let session = this.userService.user?.session;
		session = !!session ? session : this.cookieService.get("session");
		if (!!session) {
			request = request.clone({
				setHeaders: {session}
			});
		}

		return next.handle(request);
	}
}
