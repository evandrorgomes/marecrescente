import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";

export class RessalvaDoadorDTO extends BaseEntidade{

   private _id: number;
   private _idDoador: number;
   private _observacao: string;

    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Getter idDoador
     * @return {number}
     */
	public get idDoador(): number {
		return this._idDoador;
	}

    /**
     * Getter observacao
     * @return {string}
     */
	public get observacao(): string {
		return this._observacao;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Setter idDoador
     * @param {number} value
     */
	public set idDoador(value: number) {
		this._idDoador = value;
	}

    /**
     * Setter observacao
     * @param {string} value
     */
	public set observacao(value: string) {
		this._observacao = value;
	}

   jsonToEntity(res: any): RessalvaDoadorDTO {
      this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
      this.idDoador = ConvertUtil.parseJsonParaAtributos(res.idDoador, new Number());
      this.observacao = ConvertUtil.parseJsonParaAtributos(res.observacao, new String());
      
		return this;
   }
}
