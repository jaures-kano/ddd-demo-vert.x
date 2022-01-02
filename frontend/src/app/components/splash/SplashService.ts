import {Injectable} from "@angular/core";
import {BehaviorSubject} from "rxjs";

@Injectable({providedIn: 'root'})
export class SplashService {
	public splashSubject: BehaviorSubject<boolean>;

	constructor() {
		this.splashSubject = new BehaviorSubject<boolean>(false);
	}

	public get show(): boolean {
		return this.splashSubject.value;
	}

	on() {
		this.splashSubject.next(true)
		document.body.classList.add('overflow-hidden')
	}

	off() {
		this.splashSubject.next(false)
		document.body.classList.remove('overflow-hidden')
	}
}
