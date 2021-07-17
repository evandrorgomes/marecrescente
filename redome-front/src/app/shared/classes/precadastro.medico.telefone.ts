import { BaseEntidade } from 'app/shared/base.entidade';
import { PreCadastroMedico } from 'app/shared/classes/precadastro.medico';
import { ConvertUtil } from 'app/shared/util/convert.util';

export class PreCadastroMedicoTelefone extends BaseEntidade {
    
    private _id: number = 0;
	private _principal: boolean = false;
	private _tipo: number;
	private _codArea: number;
	private _codInter: number;
	private _numero: number;
	private _complemento: string;
    private _nome: string;
    private _preCadastroMedico: PreCadastroMedico;

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
     * Getter preCadastroMedico
     * @return {PreCadastroMedico}
     */
	public get preCadastroMedico(): PreCadastroMedico {
		return this._preCadastroMedico;
	}

    /**
     * Setter preCadastroMedico
     * @param {PreCadastroMedico} value
     */
	public set preCadastroMedico(value: PreCadastroMedico) {
		this._preCadastroMedico = value;
	}

    
    public jsonToEntity(res: any): PreCadastroMedicoTelefone {
        
        if (res.preCadastroMedico) {
            this.preCadastroMedico = new PreCadastroMedico().jsonToEntity(res.preCadastroMedico);
        }

        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.principal = ConvertUtil.parseJsonParaAtributos(res.principal, new Boolean());
		this.tipo = ConvertUtil.parseJsonParaAtributos(res.tipo, new Number());
		this.codArea = ConvertUtil.parseJsonParaAtributos(res.codArea, new Number());
		this.codInter = ConvertUtil.parseJsonParaAtributos(res.codInter, new Number());
		this.numero = ConvertUtil.parseJsonParaAtributos(res.numero, new Number());
		this.complemento = ConvertUtil.parseJsonParaAtributos(res.complemento, new String());
		this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
	
		return this;
    }

}