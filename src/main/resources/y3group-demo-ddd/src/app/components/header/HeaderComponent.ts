import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {Title} from "@angular/platform-browser";
import {UserService} from "../../domain/user/service/UserService";
import {environment} from "../../../environments/environment";

@Component({
	selector: 'div.header-nav',
	templateUrl: './header.component.html',
	styleUrls: ["./header.component.css"]
})
export class HeaderComponent implements OnInit {
	environmentPage = environment.PAGE

	constructor(
		public userService: UserService
	) {
	}

	ngOnInit(): void {
	}

}
