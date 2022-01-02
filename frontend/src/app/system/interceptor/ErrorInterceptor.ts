import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {CookieService} from "../support/CookieService";
import {UserService} from "../../domain/user/service/UserService";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
	constructor(
		private userService: UserService,
		private cookieService: CookieService
	) {
	}

	intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		return next.handle(request).pipe(catchError(err => {
			console.error(err);
			if ([401, 403].includes(err.status) && this.userService.user) {
				this.userService.userSubject.next(null);
				this.cookieService.delete("session");
			}
			return throwError(err);
		}));
	}
}
