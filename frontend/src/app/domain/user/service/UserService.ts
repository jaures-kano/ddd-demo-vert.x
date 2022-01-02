import {Injectable} from "@angular/core";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {UserRepository} from "../repository/UserRepository";
import {User} from "../model/User";
import {UserDTO} from "../../../components/signin/UserDTO";
import {BehaviorSubject, Observable} from "rxjs";
import {Response} from "../../../system/support/Response";
import {SignInRes} from "../response/SignInRes";
import {UserSignUpDTO} from "../../../components/signup/UserSignUpDTO";
import {CookieService} from "../../../system/support/CookieService";
import {environment} from "../../../../environments/environment";

@Injectable({providedIn: 'root'})
export class UserService {
	public userSubject: BehaviorSubject<any>;

	constructor(
		private router: Router,
		private http: HttpClient,
		private userRepository: UserRepository,
		private cookieService: CookieService
	) {
		// @ts-ignore
		this.userSubject = new BehaviorSubject<User>(null);
	}

	public get user(): User | null {
		return this.userSubject.value;
	}

	login(userDto: UserDTO): Observable<Response<SignInRes>> {
		return this.userRepository.login(userDto);
	}

	signup(userSignUpDTO: UserSignUpDTO): Observable<Response<SignInRes>> {
		return this.userRepository.signup(userSignUpDTO);
	}

	logout() {
		this.userSubject.next(null);
		this.cookieService.delete('session');
		this.router.navigateByUrl(environment.PAGE.SIGNIN)
	}

	guard(): Observable<Response<SignInRes>> | null {
		let loadable = this.user === null && this.cookieService.get("session") !== '';
		if (loadable) {
			return this.userRepository.info();
		}
		return null;
	}
}
