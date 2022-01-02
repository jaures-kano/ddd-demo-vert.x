export interface SignInRes {
	id: number;
	username: string;
	session: string;
	accessToken: string;
	resetToken: string;
}
