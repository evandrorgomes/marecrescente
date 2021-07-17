import { forEach } from '@angular/router/src/utils/collection';
import {BaseEntidade} from '../base.entidade';
import { UsuarioLogado } from './usuario.logado';
import { CentroTransplante } from './centro.transplante';
import { EnderecoContatoMedico } from 'app/shared/classes/endereco.contato.medico';
import { ContatoTelefonicoMedico } from 'app/shared/classes/contato.telefonico.medico';
import { EmailContatoMedico } from 'app/shared/classes/email.contato.medico';
import { ConvertUtil } from 'app/shared/util/convert.util';

/**
 * Classe que representa um médico
 * 
 * @author Bruno Sousa
 * @export
 * @class Medico
 * @extends {BaseEntidade}
 */
export class Medico  extends BaseEntidade {

    private _id: number;
    private _crm: string
    private _nome: string;
    private _usuario: UsuarioLogado;
	private _centroAvaliador: CentroTransplante;
	private _arquivoCrm: string;
	private _contatosTelefonicos: ContatoTelefonicoMedico[];
	private _endereco: EnderecoContatoMedico;
	private _emails: EmailContatoMedico[];	
	private _centrosReferencia: CentroTransplante[];

	/**
	 * @param {number} id 
	 * 
	 * @memberOf Avaliacao
	 */
	constructor(id: number = null) {
		super();
		this._id = id;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf Medico
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Medico
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf Medico
	 */
	public get nome(): string {
		return this._nome;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Medico
	 */
	public set nome(value: string) {
		this._nome = value;
	}

	/**
	 * 
	 * 
	 * @type {UsuarioLogado}
	 * @memberOf Medico
	 */
	public get usuario(): UsuarioLogado {
		return this._usuario;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Medico
	 */
	public set usuario(value: UsuarioLogado) {
		this._usuario = value;
	}

	/**
	 * 
	 * 
	 * @type {CentroTransplante}
	 * @memberOf Medico
	 */
	public get centroAvaliador(): CentroTransplante {
		return this._centroAvaliador;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Medico
	 */
	public set centroAvaliador(value: CentroTransplante) {
		this._centroAvaliador = value;
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
     * @return {EnderecoContatoMedico}
     */
	public get endereco(): EnderecoContatoMedico {
		return this._endereco;
	}

    /**
     * Setter endereco
     * @param {EnderecoContatoMedico} value
     */
	public set endereco(value: EnderecoContatoMedico) {
		this._endereco = value;
	}

    /**
     * Getter contatosTelefonicos
     * @return {ContatoTelefonicoMedico[]}
     */
	public get contatosTelefonicos(): ContatoTelefonicoMedico[] {
		return this._contatosTelefonicos;
	}

    /**
     * Setter contatosTelefonicos
     * @param {ContatoTelefonicoMedico[]} value
     */
	public set contatosTelefonicos(value: ContatoTelefonicoMedico[]) {
		this._contatosTelefonicos = value;
	}

    /**
     * Getter emails
     * @return {EmailContatoMedico[]}
     */
	public get emails(): EmailContatoMedico[] {
		return this._emails;
	}

    /**
     * Setter emails
     * @param {EmailContatoMedico[]} value
     */
	public set emails(value: EmailContatoMedico[]) {
		this._emails = value;
	}

    /**
     * Getter centrosReferencia
     * @return {CentroTransplante[]}
     */
	public get centrosReferencia(): CentroTransplante[] {
		return this._centrosReferencia;
	}

    /**
     * Setter centrosReferencia
     * @param {CentroTransplante[]} value
     */
	public set centrosReferencia(value: CentroTransplante[]) {
		this._centrosReferencia = value;
	}
	
	public get crm(): string {
		return this._crm;
	}

	public set crm(value: string) {
		this._crm = value;
	}
 
	public jsonToEntity(res:any): Medico {
		if (res.usuario) {
			this.usuario = new UsuarioLogado().jsonToEntity(res.usuario);
		}
		if (res.centroAvaliador) {
			this.centroAvaliador = new CentroTransplante().jsonToEntity(res.centroAvaliador);
		}

		if (res.endereco) {
			this.endereco = new EnderecoContatoMedico().jsonToEntity(res.endereco);
		}

		if (res.contatosTelefonicos) {
			this.contatosTelefonicos = [];
			res.contatosTelefonicos.forEach(contato => {
				this.contatosTelefonicos.push( new ContatoTelefonicoMedico().jsonToEntity(contato));
			})		
		}

		if (res.emails) {
			this.emails = [];
			res.emails.forEach(email => {
				this.emails.push( new EmailContatoMedico().jsonToEntity(email));	
			});
		}

		if (res.centrosReferencia) {
			this.centrosReferencia = [];
			res.centrosReferencia.forEach(centro => {
				this.centrosReferencia.push( new CentroTransplante().jsonToEntity(centro));
			});
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.crm = ConvertUtil.parseJsonParaAtributos(res.crm, new String());
		this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
		this.arquivoCrm = ConvertUtil.parseJsonParaAtributos(res.arquivoCrm, new String());

		return this;
	}

}