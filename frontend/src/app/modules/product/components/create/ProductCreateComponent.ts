import {Component, OnInit, ViewEncapsulation} from "@angular/core";
import {DomSanitizer, Title} from "@angular/platform-browser";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {environment} from "../../../../../environments/environment";
import {ProductService} from "src/app/domain/product/service/ProductService";
import {Router} from "@angular/router";
import {SplashService} from "../../../../components/splash/SplashService";

@Component({
	selector: 'div.app-home',
	templateUrl: './product-create.component.html',
	styleUrls: ["./product-create.component.css"]
})
export class ProductCreateComponent implements OnInit {
	fileToUpload: File[] = [];
	form: FormGroup;

	constructor(
		private router: Router,
		private title: Title,
		private formBuilder: FormBuilder,
		private sanitizer: DomSanitizer,
		private productService: ProductService,
		public splashService: SplashService
	) {
		this.form = this.formBuilder.group({
			title: ['', [
				Validators.required,
				Validators.minLength(3)]],
			description: ['', [
				Validators.required,
				Validators.minLength(3)]],
			price: ['', [
				Validators.required,
				Validators.minLength(3)]],
		});
	}

	ngOnInit(): void {
		this.title.setTitle('Create product' + environment.TITLE_POSTFIX);
	}

	onSubmit() {
		if (!this.form.invalid) {
			this.splashService.on();
			let values = this.form.value;
			let formData = new FormData();
			for (const [key, value] of Object.entries(values)) {
				// @ts-ignore
				formData.append(key, value);
			}

			if (this.fileToUpload.length > 0) {
				for (let file of this.fileToUpload) {
					formData.append("upload", file, file.name);
				}
			}

			this.productService.create(formData).subscribe(
				response => {
					console.log(response)
					setTimeout(() => {
						this.router.navigateByUrl("/")
						this.splashService.off();
					}, 500);
				},
				error => {
					console.log(error)
					this.splashService.off();
				});
		}
	}

	handleFileInput(event: Event) {
		// @ts-ignore
		let files = event.target.files
		for (let i = 0; i < files.length; i++) {
			let file: File = files.item(i);
			this.fileToUpload.push(file);
		}
	}

	getResourceUrl(file: File) {
		return this.sanitizer.bypassSecurityTrustResourceUrl(URL.createObjectURL(file));
	}

	removeAttachment(file: File) {
		let index = this.fileToUpload.indexOf(file);
		if (index !== -1) {
			this.fileToUpload.splice(index, 1)
		}
	}
}
