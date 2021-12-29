import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {UserService} from "../../domain/user/service/UserService";
import {CookieService} from "../../system/support/CookieService";
import {UserRepository} from "../../domain/user/repository/UserRepository";

@Component({
	selector: 'div.splash',
	templateUrl: './splash.component.html',
	styleUrls: ["./splash.component.css"]
})
export class SplashComponent implements OnInit {
	show = false;

	constructor(
		public userService: UserService,
		public cookieService: CookieService,
		private userRepository: UserRepository
	) {
		this.show = userService.user === null && cookieService.get("session") !== '';
	}

	ngOnInit(): void {
		this.userRepository.info().subscribe(success => {
			this.userService.userSubject.next(success.data);
			this.show = false;
		})
	}
}
