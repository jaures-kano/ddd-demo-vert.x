export interface Response<D> {
	message: string;
	code: number;
	status: number;
	data: D;
}
