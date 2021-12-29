import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {Title} from "@angular/platform-browser";
import {environment} from "../../../environments/environment";

@Component({
	selector: 'div.app-home',
	templateUrl: './home.component.html',
	encapsulation: ViewEncapsulation.None
})
export class HomeComponent implements OnInit {
	constructor(
		private title: Title
	) {
	}

	ngOnInit(): void {
		this.title.setTitle('Home' + environment.TITLE_POSTFIX);
	}

}
