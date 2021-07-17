import { BaseEntidade } from 'app/shared/base.entidade';
import { Pais } from 'app/shared/dominio/pais';
import { PreCadastroMedico } from './precadastro.medico';
import { ConvertUtil } from 'app/shared/util/convert.util';
import { Municipio } from '../dominio/municipio';

export class PreCadastroMedicoEndereco extends BaseEntidade {

    private _id: number;
	private _pais: Pais;
	private _cep: number;
	private _tipoLogradouro: string;
	private _nomeLogradouro: string;
	private _numero: number;
	private _complemento: string;
	private _bairro: string;
	private _municipio: Municipio;
    private _preCadastroMedico: PreCadastroMedico;

    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Getter pais
     * @return {Pais}
     */
	public get pais(): Pais {
		return this._pais;
	}

    /**
     * Setter pais
     * @param {Pais} value
     */
	public set pais(value: Pais) {
		this._pais = value;
	}

    /**
     * Getter cep
     * @return {number}
     */
	public get cep(): number {
		return this._cep;
	}

    /**
     * Setter cep
     * @param {number} value
     */
	public set cep(value: number) {
		this._cep = value;
	}

    /**
     * Getter tipoLogradouro
     * @return {string}
     */
	public get tipoLogradouro(): string {
		return this._tipoLogradouro;
	}

    /**
     * Setter tipoLogradouro
     * @param {string} value
     */
	public set tipoLogradouro(value: string) {
		this._tipoLogradouro = value;
	}

    /**
     * Getter nomeLogradouro
     * @return {string}
     */
	public get nomeLogradouro(): string {
		return this._nomeLogradouro;
	}

    /**
     * Setter nomeLogradouro
     * @param {string} value
     */
	public set nomeLogradouro(value: string) {
		this._nomeLogradouro = value;
	}

    /**
     * Getter numero
     * @return {number}
     */
	public get numero(): number {
		return this._numero;
	}

    /**
     * Setter numero
     * @param {number} value
     */
	public set numero(value: number) {
		this._numero = value;
	}

    /**
     * Getter complemento
     * @return {string}
     */
	public get complemento(): string {
		return this._complemento;
	}

    /**
     * Setter complemento
     * @param {string} value
     */
	public set complemento(value: string) {
		this._complemento = value;
	}

    /**
     * Getter bairro
     * @return {string}
     */
	public get bairro(): string {
		return this._bairro;
	}

    /**
     * Setter bairro
     * @param {string} value
     */
	public set bairro(value: string) {
		this._bairro = value;
	}

    /**
     * Getter municipio
     * @return {Municipio}
     */
	public get municipio(): Municipio {
		return this._municipio;
	}

    /**
     * Setter municipio
     * @param {Municipio} value
     */
	public set municipio(value: Municipio) {
		this._municipio = value;
	}

    /**
     * Getter preCadastro
     * @return {PreCadastro}
     */
	public get preCadastroMedico(): PreCadastroMedico {
		return this._preCadastroMedico;
	}

    /**
     * Setter preCadastro
     * @param {PreCadastro} value
     */
	public set preCadastroMedico(value: PreCadastroMedico) {
		this._preCadastroMedico = value;
	}

    public jsonToEntity(res: any): PreCadastroMedicoEndereco {
        
        if (res.pais) {
			this.pais = new Pais().jsonToEntity(res.pais);
        }

        if (res.municipio) {
            this.municipio = new Municipio().jsonToEntity(res.municipio);
        }
        
        if (res.preCadastroMedico) {
            this.preCadastroMedico = new PreCadastroMedico().jsonToEntity(res.preCadastroMedico);
        }

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.cep = ConvertUtil.parseJsonParaAtributos(res.cep, new Number());
		this.tipoLogradouro = ConvertUtil.parseJsonParaAtributos(res.tipoLogradouro, new String());
		this.nomeLogradouro = ConvertUtil.parseJsonParaAtributos(res.nomeLogradouro, new String());
		this.numero = ConvertUtil.parseJsonParaAtributos(res.numero, new Number());
		this.complemento = ConvertUtil.parseJsonParaAtributos(res.complemento, new String());
		this.bairro = ConvertUtil.parseJsonParaAtributos(res.bairro, new String());

        return this;
    }

}