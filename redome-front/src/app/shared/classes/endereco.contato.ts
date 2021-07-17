import { Pais } from '../dominio/pais';
import { BaseEntidade } from '../base.entidade';
import { Input } from '@angular/core/src/metadata/directives';
import { ConvertUtil } from '../util/convert.util';
import { Municipio } from 'app/shared/dominio/municipio';

/** 
 * @author Rafael
 * Objeto referente ao país, domínio carregado a partir dos correios.
 */
export class EnderecoContato extends BaseEntidade {

	private _id: number;
	private _pais: Pais;
	private _cep: number;
	private _tipoLogradouro: string;
	private _nomeLogradouro: string;
	private _numero: string;
	private _complemento: string;
	private _bairro: string;
	private _municipio: Municipio;
	private _principal: boolean;
	private _correspondencia: boolean;
	private _excluido: boolean;

	// Obrigatório, caso o endereço não seja nacional.
	private _enderecoEstrangeiro: string;


	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf ContatoEndereco
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoEndereco
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {Pais}
	 * @memberOf ContatoEndereco
	 */
	public get pais(): Pais {
		return this._pais;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoEndereco
	 */
	public set pais(value: Pais) {
		this._pais = value;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf ContatoEndereco
	 */
	public get cep(): number {
		return this._cep;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoEndereco
	 */
	public set cep(value: number) {
		this._cep = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf ContatoEndereco
	 */
	public get tipoLogradouro(): string {
		return this._tipoLogradouro;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoEndereco
	 */
	public set tipoLogradouro(value: string) {
		this._tipoLogradouro = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf ContatoEndereco
	 */
	public get nomeLogradouro(): string {
		return this._nomeLogradouro;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoEndereco
	 */
	public set nomeLogradouro(value: string) {
		this._nomeLogradouro = value;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf ContatoEndereco
	 */
	public get numero(): string {
		return this._numero;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoEndereco
	 */
	public set numero(value: string) {
		this._numero = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf ContatoEndereco
	 */
	public get complemento(): string {
		return this._complemento;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoEndereco
	 */
	public set complemento(value: string) {
		this._complemento = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf ContatoEndereco
	 */
	public get bairro(): string {
		return this._bairro;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoEndereco
	 */
	public set bairro(value: string) {
		this._bairro = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf ContatoEndereco
	 */
	public get municipio(): Municipio {
		return this._municipio;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoEndereco
	 */
	public set municipio(value: Municipio) {
		this._municipio = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf ContatoEndereco
	 */
	public get enderecoEstrangeiro(): string {
		return this._enderecoEstrangeiro;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf ContatoEndereco
	 */
	public set enderecoEstrangeiro(value: string) {
		this._enderecoEstrangeiro = value;
	}

	/**
	 * 
	 * 
	 * @type {boolean}
	 * @memberOf EnderecoContato
	 */
	public get principal(): boolean {
		return this._principal;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf EnderecoContato
	 */
	public set principal(value: boolean) {
		this._principal = value;
	}

	/**
	 * 
	 * 
	 * @type {boolean}
	 * @memberOf EnderecoContato
	 */
	public get correspondencia(): boolean {
		return this._correspondencia;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf EnderecoContato
	 */
	public set correspondencia(value: boolean) {
		this._correspondencia = value;
	}


	/**
	 * 
	 * 
	 * @type {boolean}
	 * @memberOf EnderecoContato
	 */
	public get excluido(): boolean {
		return this._excluido;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf EnderecoContato
	 */
	public set excluido(value: boolean) {
		this._excluido = value;
	}


	/**
	 * Retorna o texto formatado para exibição completa.
	 * Contendo endereço, número, uf...
	 */
	public exibirTextoCompleto(): string{
		return this.tipoLogradouro + ' ' + this.nomeLogradouro + ' | ' +
					(this.municipio  ?  this.municipio.descricao  + ' | ' : '') + (this.municipio && this.municipio.uf ?  this.municipio.uf.sigla : '');
	}

	public jsonToEntity(res: any): EnderecoContato {

		if (res.pais) {
			this.pais = new Pais().jsonToEntity(res.pais);
		}

		if (res.municipio) {
			this.municipio = new Municipio().jsonToEntity(res.municipio);
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.cep = ConvertUtil.parseJsonParaAtributos(res.cep, new Number());
		this.tipoLogradouro = ConvertUtil.parseJsonParaAtributos(res.tipoLogradouro, new String());
		this.nomeLogradouro = ConvertUtil.parseJsonParaAtributos(res.nomeLogradouro, new String());
		this.numero = ConvertUtil.parseJsonParaAtributos(res.numero, new String());
		this.complemento = ConvertUtil.parseJsonParaAtributos(res.complemento, new String());
		this.bairro = ConvertUtil.parseJsonParaAtributos(res.bairro, new String());
		this.principal = ConvertUtil.parseJsonParaAtributos(res.principal, new Boolean());
		this.correspondencia = ConvertUtil.parseJsonParaAtributos(res.correspondencia, new Boolean());
		this.excluido = ConvertUtil.parseJsonParaAtributos(res.excluido, new Boolean());
		this.enderecoEstrangeiro = ConvertUtil.parseJsonParaAtributos(res.enderecoEstrangeiro, new String());		

        return this;
    }
}