import { BaseEntidade } from "../base.entidade";
import { ArquivoPedidoWorkupDTO } from "./arquivo.pedido.workup.dto";

export abstract class PlanoWorkupDTO extends BaseEntidade {
	private _id: number;
	private _dataExame: Date;
	private _dataResultado: Date;
	private _dataColeta: Date;
    private _arquivosPedidoWorkup: ArquivoPedidoWorkupDTO[] = [];


    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Getter dataExame
     * @return {Date}
     */
	public get dataExame(): Date {
		return this._dataExame;
	}

    /**
     * Getter dataResultado
     * @return {Date}
     */
	public get dataResultado(): Date {
		return this._dataResultado;
	}

    /**
     * Getter dataColeta
     * @return {Date}
     */
	public get dataColeta(): Date {
		return this._dataColeta;
	}

    /**
     * Getter arquivosPedidoWorkup
     * @return {ArquivoPedidoWorkupDTO[] }
     */
	public get arquivosPedidoWorkup(): ArquivoPedidoWorkupDTO[]  {
		return this._arquivosPedidoWorkup;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Setter dataExame
     * @param {Date} value
     */
	public set dataExame(value: Date) {
		this._dataExame = value;
	}

    /**
     * Setter dataResultado
     * @param {Date} value
     */
	public set dataResultado(value: Date) {
		this._dataResultado = value;
	}

    /**
     * Setter dataColeta
     * @param {Date} value
     */
	public set dataColeta(value: Date) {
		this._dataColeta = value;
	}

    /**
     * Setter arquivosPedidoWorkup
     * @param {ArquivoPedidoWorkupDTO[] } value
     */
	public set arquivosPedidoWorkup(value: ArquivoPedidoWorkupDTO[] ) {
		this._arquivosPedidoWorkup = value;
	}

    public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }


}
