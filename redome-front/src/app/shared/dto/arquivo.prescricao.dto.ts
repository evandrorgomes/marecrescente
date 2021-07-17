import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";

export class ArquivoPrescricaoDTO extends BaseEntidade {
	
  private _id: Number;
  private _caminho: String;
  private _justificativa: boolean;
  private _autorizacaoPaciente: boolean;
  private _nomeSemTimestamp: string;

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

	public get autorizacaoPaciente(): boolean {
		return this._autorizacaoPaciente;
	}

	public set autorizacaoPaciente(value: boolean) {
		this._autorizacaoPaciente = value;
	}

	public get nomeSemTimestamp(): string {
		return this._nomeSemTimestamp;
	}

	public set nomeSemTimestamp(value: string) {
		this._nomeSemTimestamp = value;
	}
	
	public jsonToEntity(res: any): ArquivoPrescricaoDTO {
				
		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.caminho = ConvertUtil.parseJsonParaAtributos(res.caminho, new String());
		this.justificativa = ConvertUtil.parseJsonParaAtributos(res.justificativa, new Boolean());
		this.autorizacaoPaciente = ConvertUtil.parseJsonParaAtributos(res.autorizacaoPaciente, new Boolean());
		this.nomeSemTimestamp = ConvertUtil.parseJsonParaAtributos(res.nomeSemTimestamp, new String());

		return this;
    }
}