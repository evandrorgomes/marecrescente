import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";

export class ArquivoPedidoWorkupDTO extends BaseEntidade {
	
  private _id: Number;
  private _caminho: String;
  private _nomeSemTimestamp: String;

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


    /**
     * Getter nomeSemTimestamp
     * @return {String}
     */
	public get nomeSemTimestamp(): String {
		return this._nomeSemTimestamp;
	}

    /**
     * Setter nomeSemTimestamp
     * @param {String} value
     */
	public set nomeSemTimestamp(value: String) {
		this._nomeSemTimestamp = value;
	}

	
	public jsonToEntity(res: any): ArquivoPedidoWorkupDTO {
				
		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.caminho = ConvertUtil.parseJsonParaAtributos(res.caminho, new String());
		this.nomeSemTimestamp = ConvertUtil.parseJsonParaAtributos(res.nomeSemTimestamp, new String());

		return this;
    }
}