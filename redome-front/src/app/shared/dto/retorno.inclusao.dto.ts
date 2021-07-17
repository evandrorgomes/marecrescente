import { BaseEntidade } from "../base.entidade";
import { StatusRetornoInclusaoDTO } from "./status.retorno.inclusao.dto";
import { ConvertUtil } from "app/shared/util/convert.util";

export class RetornoInclusaoDTO extends BaseEntidade {

    private _idObjeto: number;
	private _mensagem: string;
	private _stringRetorno: string;
    private _status: StatusRetornoInclusaoDTO;

    /**
     * Getter idObjeto
     * @return {number}
     */
	public get idObjeto(): number {
		return this._idObjeto;
	}

    /**
     * Setter idObjeto
     * @param {number} value
     */
	public set idObjeto(value: number) {
		this._idObjeto = value;
	}

    /**
     * Getter mensagem
     * @return {string}
     */
	public get mensagem(): string {
		return this._mensagem;
	}

    /**
     * Setter mensagem
     * @param {string} value
     */
	public set mensagem(value: string) {
		this._mensagem = value;
	}

    /**
     * Getter stringRetorno
     * @return {string}
     */
	public get stringRetorno(): string {
		return this._stringRetorno;
	}

    /**
     * Setter stringRetorno
     * @param {string} value
     */
	public set stringRetorno(value: string) {
		this._stringRetorno = value;
	}

    /**
     * Getter status
     * @return {StatusRetornoInclusaoDTO}
     */
	public get status(): StatusRetornoInclusaoDTO {
		return this._status;
	}

    /**
     * Setter status
     * @param {StatusRetornoInclusaoDTO} value
     */
	public set status(value: StatusRetornoInclusaoDTO) {
		this._status = value;
	}

    public jsonToEntity(res: any): RetornoInclusaoDTO {

        if (res.status) {
            this.status = StatusRetornoInclusaoDTO.valueOf(res.status);
        };

        this.idObjeto = ConvertUtil.parseJsonParaAtributos(res.idObjeto, new Number());
        this.mensagem  = ConvertUtil.parseJsonParaAtributos(res.mensagem, new String());
        this.stringRetorno  = ConvertUtil.parseJsonParaAtributos(res.stringRetorno, new String());
        
        return this;
    }

}