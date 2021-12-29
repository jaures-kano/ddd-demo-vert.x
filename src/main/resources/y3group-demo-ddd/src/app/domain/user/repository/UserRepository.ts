import {Injectable} from "@angular/core";
import {UserDTO} from "../../../components/signin/UserDTO";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {Observable} from "rxjs";
import {SignInRes} from "../response/SignInRes";
import {Response} from "../../../system/support/Response";
import {UserSignUpDTO} from "../../../components/signup/UserSignUpDTO";

@Injectable({providedIn: 'root'})
export class UserRepository {
	constructor(
		private http: HttpClient
	) {
	}

	login(user: UserDTO): Observable<Response<SignInRes>> {
		return this.http.post<any>(`${environment.API_HOST}${environment.API_SIGNIN}`, user);
	}

	signup(userSignUpDTO: UserSignUpDTO): Observable<Response<SignInRes>> {
		return this.http.post<any>(`${environment.API_HOST}${environment.API_SIGNUP}`, userSignUpDTO);
	}

	info(): Observable<Response<SignInRes>> {
		return this.http.get<any>(`${environment.API_HOST}${environment.API_USER_INFO}`);
	}
}
