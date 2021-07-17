import {BaseEntidade} from '../../../shared/base.entidade';

export class MotivoCancelamentoWorkup extends BaseEntidade {
	public jsonToEntity(res: any) {
		throw new Error("Method not implemented.");
	}

	private _id: number;
	private _descricao: string;

	/**
	 * @returns number
	 */
	public get id(): number {
		return this._id;
	}
	/**
	 * @param  {number} value
	 */
	public set id(value: number) {
		this._id = value;
	}
	/**
	 * @returns string
	 */
	public get descricao(): string {
		return this._descricao;
	}
	/**
	 * @param  {string} value
	 */
	public set descricao(value: string) {
		this._descricao = value;
	}


}
