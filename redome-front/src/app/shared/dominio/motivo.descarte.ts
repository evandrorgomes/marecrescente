import { BaseEntidade } from "../base.entidade";

export class MotivoDescarte extends BaseEntidade {

    private _id: number;
    private _descricao: string;

	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get descricao(): string {
		return this._descricao;
	}

	public set descricao(value: string) {
		this._descricao = value;
	}

	public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }
}