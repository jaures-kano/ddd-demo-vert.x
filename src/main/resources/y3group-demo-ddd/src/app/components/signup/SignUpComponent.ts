import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {Title} from "@angular/platform-browser";
import {environment} from "../../../environments/environment";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {UserService} from "../../domain/user/service/UserService";
import {UserSignUpDTO} from "./UserSignUpDTO";
import {CookieService} from "../../system/support/CookieService";

@Component({
	selector: 'div.signup-page',
	templateUrl: './signup.component.html',
	styleUrls: ["./signup.component.css"]
})
export class SignUpComponent implements OnInit {
	form: FormGroup;

	pageSignIn: string = environment.PAGE.SIGNIN

	constructor(
		private title: Title,
		private formBuilder: FormBuilder,
		private router: Router,
		private userService: UserService,
		private cookieService: CookieService
	) {
		this.form = this.formBuilder.group({
			username: ['', [
				Validators.required,
				Validators.minLength(3)]],
			password: ['', [
				Validators.required,
				Validators.minLength(3)]],
			confirmPassword: ['', [
				Validators.required,
				Validators.minLength(3),
				this.comparePassword.bind(this)]],
		});
		console.log(this.form)
	}

	ngOnInit(): void {
		this.title.setTitle('Sign Up' + environment.TITLE_POSTFIX);
	}

	comparePassword(check: AbstractControl) {
		let formValue = this.form?.value;
		const v = check.value;
		if (!!formValue && check.value !== formValue.password) {
			return {
				password_not_match: true
			};
		}
		return null
	}

	onSubmit() {
		if (!this.form.invalid) {
			let userSignUpDTO: UserSignUpDTO = this.form.value;
			console.log(userSignUpDTO);

			this.userService.signup(userSignUpDTO).subscribe(
				response => {
					this.cookieService.set("session", response.data.session);
					this.userService.userSubject.next(response.data);
					this.router.navigateByUrl("/")
				},
				error => {
					console.log(error)
				});
		}
	}
}
