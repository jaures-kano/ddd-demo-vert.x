import {Component, ViewEncapsulation} from '@angular/core';
import {AppToastService} from "./system/support/UI/toasts/AppToastService";

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	encapsulation: ViewEncapsulation.None
})
export class AppComponent {
	title = 'y3group-demo-ddd';

	constructor(public toastService: AppToastService) {
	}
}
