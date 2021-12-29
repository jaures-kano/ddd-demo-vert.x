import {Component, OnInit} from "@angular/core";
import {Title} from "@angular/platform-browser";
import {environment} from "../../../environments/environment";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../../domain/user/service/UserService";
import {UserDTO} from "./UserDTO";
import {CookieService} from "../../system/support/CookieService";
import {AppToastService} from "../../system/support/UI/toasts/AppToastService";
import {Toast} from "../../system/support/UI/toasts/Toast";

@Component({
	selector: 'div.signin-page',
	templateUrl: './signin.component.html',
	styleUrls: ["./signin.component.css"]
})
export class SignInComponent implements OnInit {
	form: FormGroup;

	pageSignUp: string = environment.PAGE.SIGNUP

	constructor(
		private title: Title,
		private formBuilder: FormBuilder,
		private router: Router,
		private userService: UserService,
		private cookieService: CookieService,
		private toastService: AppToastService
	) {
		this.form = this.formBuilder.group({
			username: ['', [Validators.required, Validators.minLength(3)]],
			password: ['', [Validators.required, Validators.minLength(3)]],
			remember_me: [false],
		});
	}

	ngOnInit(): void {
		this.title.setTitle('Sign In' + environment.TITLE_POSTFIX);
	}

	onSubmit() {
		if (!this.form.invalid) {
			let userDto: UserDTO = this.form.value;
			console.log(userDto);

			this.userService.login(userDto).subscribe(
				response => {
					// if (userDto.remember_me)
						this.cookieService.set("session", response.data.session);

					this.userService.userSubject.next(response.data);

					console.log(response)
					// this.authentication.setToken(response.data.token);
					// this.authentication.userSubject.next(response.data.user)
					// this.authentication.startRefreshTokenTimer();
					this.router.navigateByUrl("/")
					// this.toast.show('Login success.', {
					// 	class: 'bg-success text-white'
					// })
				},
				errResponse => {
					console.log(errResponse)
					let toast = new Toast();
					toast.header = errResponse.statusText;
					toast.body = errResponse.error.message;
					this.toastService.show(toast);
				});
		}
	}

}
