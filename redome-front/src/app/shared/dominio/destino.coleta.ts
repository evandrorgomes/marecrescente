import { BaseEntidade } from "../base.entidade";

export class DestinoColeta extends BaseEntidade {
    private _id:number;
    private _descricao:String;

  constructor(id?: number, descricao?: string) {
    super();
    this._id = id;
    this._descricao = descricao;
  }

	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get descricao(): String {
		return this._descricao;
	}

	public set descricao(value: String) {
		this._descricao = value;
	}

	public jsonToEntity(res:any):this{

		return this;
	}

}
