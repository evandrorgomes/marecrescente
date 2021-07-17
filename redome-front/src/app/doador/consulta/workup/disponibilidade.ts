import { CentroTransplante } from '../../../shared/dominio/centro.transplante';
import { BaseEntidade } from "../../../shared/base.entidade";
import { ConvertUtil } from '../../../shared/util/convert.util';

export class Disponibilidade extends BaseEntidade {
		
	private _id: number;	
	private _descricao: string;
	private _dataColeta: Date;
	private _dataLimiteWorkup: Date;
	private _dataCriacao: Date;
	private _dataAceite: Date;
	private _centroTransplanteQuerColetar: boolean;	
	private _centroColeta: CentroTransplante;

	/**
	 * id do pedido de workup
	 * @returns number
	 */
	public get id(): number {
		return this._id;
	}
	/**
	 * id do pedido de workup
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

	public get dataColeta(): Date {
		return this._dataColeta;
	}

	public set dataColeta(value: Date) {
		this._dataColeta = value;
	}

	public get dataLimiteWorkup(): Date {
		return this._dataLimiteWorkup;
	}

	public set dataLimiteWorkup(value: Date) {
		this._dataLimiteWorkup = value;
	}

	public get dataCriacao(): Date {
		return this._dataCriacao;
	}

	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}

	public get dataAceite(): Date {
		return this._dataAceite;
	}

	public set dataAceite(value: Date) {
		this._dataAceite = value;
	}

	public get centroTransplanteQuerColetar(): boolean {
		return this._centroTransplanteQuerColetar;
	}

	public set centroTransplanteQuerColetar(value: boolean) {
		this._centroTransplanteQuerColetar = value;
	}
	
	public get centroColeta(): CentroTransplante {
		return this._centroColeta;
	}

	public set centroColeta(value: CentroTransplante) {
		this._centroColeta = value;
	}

	public jsonToEntity(res: any): Disponibilidade {

		if (res.centroColeta) {
			this.centroColeta = new CentroTransplante().jsonToEntity(res.centroColeta);
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());	
		this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());	
		this.dataColeta = ConvertUtil.parseJsonParaAtributos(res.dataColeta, new Date());
		this.dataLimiteWorkup = ConvertUtil.parseJsonParaAtributos(res.dataLimiteWorkup, new Date());
		this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
		this.dataAceite = ConvertUtil.parseJsonParaAtributos(res.dataAceite, new Date());
		this.centroTransplanteQuerColetar = ConvertUtil.parseJsonParaAtributos(res.centroTransplanteQuerColetar, new Boolean());	
		
		return this;
		
	}
	
}