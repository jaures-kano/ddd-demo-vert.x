import {Injectable} from "@angular/core";
import {Toast} from "./Toast";

@Injectable({providedIn: 'root'})
export class AppToastService {
	toasts: Toast[] = [];

	show(toast: Toast) {
		this.toasts.push(toast);
	}

	remove(toast: Toast) {
		this.toasts = this.toasts.filter(t => t != toast);
	}
}
