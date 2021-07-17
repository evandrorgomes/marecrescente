import { FonteCelula } from '../../../shared/dominio/fonte.celula';
import { Raca } from '../../../shared/dominio/raca';
import { Etnia } from '../../../shared/dominio/etnia';
import { UF } from '../../../shared/dominio/uf';
import { RessalvaDoador } from "../../ressalva.doador";
import { MotivoStatusDoador } from '../../inativacao/motivo.status.doador';
import { PedidoWorkup } from './pedido.workup';
import { BaseEntidade } from '../../../shared/base.entidade';

export class ArquivoPrescricao extends BaseEntidade {
	
  private _id: Number;
  private _caminho: String;
  private _justificativa:boolean;
  private _nomeSemTimestamp:string;

	public get id(): Number {
		return this._id;
	}

	public set id(value: Number) {
		this._id = value;
	}

	public get caminho(): String {
		return this._caminho;
	}

	public set caminho(value: String) {
		this._caminho = value;
	}

	public get justificativa(): boolean {
		return this._justificativa;
	}

	public set justificativa(value: boolean) {
		this._justificativa = value;
	}


	public get nomeSemTimestamp(): string {
		return this._nomeSemTimestamp;
	}

	public set nomeSemTimestamp(value: string) {
		this._nomeSemTimestamp = value;
	}
	
	public jsonToEntity(res: any): ArquivoPrescricao {
        throw new Error("Method not implemented.");
    }
}