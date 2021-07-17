import { DoadorNacional } from './doador.nacional';
import { ConvertUtil } from './../shared/util/convert.util';
import { Doador } from './doador';
import { BaseEntidade } from '../shared/base.entidade';

export class RessalvaDoador extends BaseEntidade {
	
	private _id: number;
	private _observacao: string;
	private _doador: Doador;

	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get observacao(): string {
		return this._observacao;
	}

	public set observacao(value: string) {
		this._observacao = value;
	}

	public get doador(): Doador {
		return this._doador;
	}

	public set doador(value: Doador) {
		this._doador = value;
	}

	public jsonToEntity(res: any): this {
    this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.observacao = ConvertUtil.parseJsonParaAtributos(res.observacao, new String());
		return this;
    }
}