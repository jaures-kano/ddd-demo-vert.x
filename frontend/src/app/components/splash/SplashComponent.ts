import {Component, OnInit} from "@angular/core";
import {SplashService} from "./SplashService";

@Component({
	selector: 'div.splash',
	templateUrl: './splash.component.html',
	styleUrls: ["./splash.component.css"]
})
export class SplashComponent {
	constructor(
		public splashService: SplashService
	) {}
}
