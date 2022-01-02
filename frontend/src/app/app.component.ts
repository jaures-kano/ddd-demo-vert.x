import { Component } from '@angular/core';
import {AppToastService} from "./system/support/UI/toasts/AppToastService";
import {Title} from "@angular/platform-browser";
import {environment} from "../environments/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
	constructor(
		public toastService: AppToastService,
		private title: Title
	){
		this.title.setTitle('Home' + environment.TITLE_POSTFIX);
	}
}
