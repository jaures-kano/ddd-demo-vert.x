import {Injectable} from "@angular/core";
import {environment} from "../../../environments/environment";

@Injectable({
	providedIn: 'root',
})
export class Str {
	public cutString(str: string, start: number = 0, end: number): string {
		let ss = str.slice(start, end)
		let i = ss.lastIndexOf(" ");
		if (ss.length === str.length)
			return ss

		return ss.slice(0, i) + "..."
	}

	public mergerURL(str: string): string {
		if (str.startsWith("http"))
			return str;
		return environment.URL + (str.startsWith("/") ? "" : "/") + str;
	}
}
