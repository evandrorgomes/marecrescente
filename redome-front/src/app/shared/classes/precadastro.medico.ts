import { BaseEntidade } from 'app/shared/base.entidade';
import { ConvertUtil } from 'app/shared/util/convert.util';
import { PreCadastroMedicoEndereco } from 'app/shared/classes/precadastro.medico.endereco';
import { PreCadastroMedicoEmail } from 'app/shared/classes/precadastro.medico.email';
import { PreCadastroMedicoTelefone } from 'app/shared/classes/precadastro.medico.telefone';
import { CentroTransplante } from '../dominio/centro.transplante';
import { DateMoment } from 'app/shared/util/date/date.moment';

export class PreCadastroMedico extends BaseEntidade {

    private _id: number;
    private _crm: number;
    private _nome: string;
    private _login: string;
    private _arquivoCrm: string;
    private _endereco: PreCadastroMedicoEndereco;
    private _emails: PreCadastroMedicoEmail[];
    private _telefones: PreCadastroMedicoTelefone[];
    private _centrosReferencia: CentroTransplante[];
	private _dataSolicitacao: Date;

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
     * Getter crm
     * @return {number}
     */
	public get crm(): number {
		return this._crm;
	}

    /**
     * Setter crm
     * @param {number} value
     */
	public set crm(value: number) {
		this._crm = value;
	}

    /**
     * Getter login
     * @return {string}
     */
	public get login(): string {
		return this._login;
	}

    /**
     * Setter login
     * @param {string} value
     */
	public set login(value: string) {
		this._login = value;
	}

    /**
     * Getter nome
     * @return {string}
     */
	public get nome(): string {
		return this._nome;
	}

     /**
     * Setter nome
     * @param {string} value
     */
	public set nome(value: string) {
		this._nome = value;
	}

    /**
     * Getter arquivoCrm
     * @return {string}
     */
	public get arquivoCrm(): string {
		return this._arquivoCrm;
	}

    /**
     * Setter arquivoCrm
     * @param {string} value
     */
	public set arquivoCrm(value: string) {
		this._arquivoCrm = value;
	}

    /**
     * Getter endereco
     * @return {PreCadastroMedicoEndereco}
     */
	public get endereco(): PreCadastroMedicoEndereco {
		return this._endereco;
	}

    /**
     * Setter endereco
     * @param {PreCadastroMedicoEndereco} value
     */
	public set endereco(value: PreCadastroMedicoEndereco) {
		this._endereco = value;
	}

    /**
     * Getter emails
     * @return {PreCadastroMedicoEmail[]}
     */
	public get emails(): PreCadastroMedicoEmail[] {
		return this._emails;
	}

    /**
     * Setter emails
     * @param {PreCadastroMedicoEmail[]} value
     */
	public set emails(value: PreCadastroMedicoEmail[]) {
		this._emails = value;
	}

    /**
     * Getter telefones
     * @return {PreCadastroMedicoTelefone[]}
     */
	public get telefones(): PreCadastroMedicoTelefone[] {
		return this._telefones;
	}

    /**
     * Setter telefones
     * @param {PreCadastroMedicoTelefone[]} value
     */
	public set telefones(value: PreCadastroMedicoTelefone[]) {
		this._telefones = value;
    }
    
    /**
     * Getter centrosReferencia
     * @return {CentroTransplante[]}
     */
	public get centrosReferencia(): CentroTransplante[] {
		return this._centrosReferencia;
    }

    /**
     * Getter dataSolicitacao
     * @return {Date}
     */
	public get dataSolicitacao(): Date {
		return this._dataSolicitacao;
	}

    /**
     * Setter centrosReferencia
     * @param {CentroTransplante[]} value
     */
	public set centrosReferencia(value: CentroTransplante[]) {
		this._centrosReferencia = value;
	}


    /**
     * Setter dataSolicitacao
     * @param {Date} value
     */
	public set dataSolicitacao(value: Date) {
		this._dataSolicitacao = value;
	}
    

    public jsonToEntity(res: any): PreCadastroMedico {

        if (res.endereco) {
            this.endereco = new PreCadastroMedicoEndereco().jsonToEntity(res.endereco);
        }

        if (res.telefones) {
            this.telefones = [];
            res.telefones.forEach(telefone => {
                this.telefones.push(new PreCadastroMedicoTelefone().jsonToEntity(telefone));
            })
        }

        if (res.emails) {
            this.emails = [];
            res.emails.forEach(email => {
                this.emails.push(new PreCadastroMedicoEmail().jsonToEntity(email));
            })
        }

        if (res.centrosReferencia) {
            this.centrosReferencia = [];
            res.centrosReferencia.forEach(centro => {
                this.centrosReferencia.push(new CentroTransplante().jsonToEntity(centro));
            });        
        }
        
        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.crm = ConvertUtil.parseJsonParaAtributos(res.crm, new Number());
        this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
        this.login = ConvertUtil.parseJsonParaAtributos(res.login, new String());
        this.arquivoCrm = ConvertUtil.parseJsonParaAtributos(res.arquivoCrm, new String());
        this.dataSolicitacao = ConvertUtil.parseJsonParaAtributos(res.dataSolicitacao, new Date());

        return this;
    }

}
