import {BaseEntidade} from '../base.entidade';
import { ConvertUtil } from '../util/convert.util';

/**
 * Classe que representa um tipo de tarefa
 * 
 * @author Bruno Sousa
 * @export
 * @class TipoTarefa
 * @extends {BaseEntidade}
 */
export class TipoTarefa  extends BaseEntidade {

	private _id: number;
	private _descricao: string;
	private _classEntidadeRelacionamento: string;
	
	constructor(id:number = null){
		super();
		this._id = id;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf TipoPendencia
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf TipoPendencia
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf TipoPendencia
	 */
	public get descricao(): string {
		return this._descricao;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf TipoPendencia
	 */
	public set descricao(value: string) {
		this._descricao = value;
	}

    /**
     * Getter classEntidadeRelacionamento
     * @return {string}
     */
	public get classEntidadeRelacionamento(): string {
		return this._classEntidadeRelacionamento;
	}

    /**
     * Setter classEntidadeRelacionamento
     * @param {string} value
     */
	public set classEntidadeRelacionamento(value: string) {
		this._classEntidadeRelacionamento = value;
	}
    

	public jsonToEntity(res:any):this{
		
		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());	
		this.classEntidadeRelacionamento = ConvertUtil.parseJsonParaAtributos(res.classEntidadeRelacionamento, new String());

		return this;
	}

}