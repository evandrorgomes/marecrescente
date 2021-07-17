import { BaseEntidade } from '../base.entidade';
import { ConvertUtil } from '../util/convert.util';

/**
 * Classe Bean utilizada para definir os campos de contato do Paciente
 * 
 * @export
 * @class ContatoTelefonico
 */
export class ContatoTelefonico extends BaseEntidade {

	private _id: number = 0;
	private _principal: boolean = false;
	private _tipo: number;
	private _codArea: number;
	private _codInter: number;
	private _numero: number;
	private _complemento: string;
	private _nome: string;
	private _excluido:boolean;

	constructor(id?: number, principal?: boolean, tipo?: number, codArea?: number, codInter?: number, numero?: number, complemento?: string, nome?: string, excluido?:boolean) {
		super();
		this._id = id;
		this._principal = principal;
		this._tipo = tipo;
		this._codArea = codArea;
		this._codInter = codInter;
		this._numero = numero;
		this._complemento = complemento;
		this._nome = nome;
		this._excluido = excluido;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf ContatoTelefonico
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoTelefonico
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {boolean}
	 * @memberOf ContatoTelefonico
	 */
	public get principal(): boolean {
		return this._principal;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoTelefonico
	 */
	public set principal(value: boolean) {
		this._principal = value;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf ContatoTelefonico
	 */
	public get tipo(): number {
		return this._tipo;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoTelefonico
	 */
	public set tipo(value: number) {
		this._tipo = value;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf ContatoTelefonico
	 */
	public get codArea(): number {
		return this._codArea;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoTelefonico
	 */
	public set codArea(value: number) {
		this._codArea = value;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf ContatoTelefonico
	 */
	public get codInter(): number {
		return this._codInter;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoTelefonico
	 */
	public set codInter(value: number) {
		this._codInter = value;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf ContatoTelefonico
	 */
	public get numero(): number {
		return this._numero;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoTelefonico
	 */
	public set numero(value: number) {
		this._numero = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf ContatoTelefonico
	 */
	public get complemento(): string {
		return this._complemento;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoTelefonico
	 */
	public set complemento(value: string) {
		this._complemento = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf ContatoTelefonico
	 */
	public get nome(): string {
		return this._nome;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoTelefonico
	 */
	public set nome(value: string) {
		this._nome = value;
	}
	

	/**
	 * Retorna o contato formatado de forma linear, unindo c�digo internacional, ddd e n�mero.
	 * @return contato formatado.
	 */
	public retornarContatoFormatado(): string {
		return ('+' + this.codInter + ' ' +
					this.codArea + ' ' + this.numero + ' | ' +
						this.complemento);
	}


	public get excluido(): boolean {
		return this._excluido;
	}

	public set excluido(value: boolean) {
		this._excluido = value;
	}

	public get telefoneFormatado(): string{
		return '+'  + this.codInter + ' ' + this.codArea + ' ' + this.numero + ' | '  + this.nome;		
}
	

	public jsonToEntity(res: any): ContatoTelefonico {

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.principal = ConvertUtil.parseJsonParaAtributos(res.principal, new Boolean());
		this.tipo = ConvertUtil.parseJsonParaAtributos(res.tipo, new Number());
		this.codArea = ConvertUtil.parseJsonParaAtributos(res.codArea, new Number());
		this.codInter = ConvertUtil.parseJsonParaAtributos(res.codInter, new Number());
		this.numero = ConvertUtil.parseJsonParaAtributos(res.numero, new Number());
		this.complemento = res.complemento? ConvertUtil.parseJsonParaAtributos(res.complemento, new String()):'';
		this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
		this.excluido = ConvertUtil.parseJsonParaAtributos(res.excluido, new Boolean());
	
		return this;
    }
}