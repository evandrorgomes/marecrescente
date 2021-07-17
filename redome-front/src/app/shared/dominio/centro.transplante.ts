import { CentroTransplanteUsuario } from './centro.transplante.usuario';
import { forEach } from '@angular/router/src/utils/collection';
import { FuncaoTransplante } from './funca.transplante';
import { Laboratorio } from './laboratorio';
import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";
import { EmailContatoCentroTransplante } from '../classes/email.contato.centrotransplante';
import { ContatoTelefonicoCentroTransplante } from '../model/contato.telefonico.centro.transplante';
import { EnderecoContatoCentroTransplante } from '../model/endereco.contato.centro.transplante';

/**
 * Classe utilizada para representar a entidade centro transplante (avaliador ou transplantador),
 * destinada a fornecer quem irá avaliacao o cadastro do paciente não aparentado.
 *
 * @author Pizão
 */
export class CentroTransplante extends BaseEntidade {

	private _id: number;
	private _nome: string;

	/**
	 * CNPJ do centro transplante.
	 * Na integração com REREME poderá ser utilizado como chave.
	 */
	private _cnpj: string;

	/**
	 * CNES (Cadastro Nacional de Estabelecimentos de Saúde) ao qual
	 * está inserido o centro transplante.
	 * Na integração com REREME poderá ser utilizado como chave.
	 */
	private _cnes: string;

	private _contatosTelefonicos: ContatoTelefonicoCentroTransplante[];

	private _enderecos: EnderecoContatoCentroTransplante[];

	private _laboratorioPreferencia: Laboratorio;

	private _funcoes: FuncaoTransplante[];

	private _centroTransplanteUsuarios: CentroTransplanteUsuario[];

	private _emails: EmailContatoCentroTransplante[];


	constructor() {
		super();
	}

	/**
	 * Método de acesso para obter o identificador único do centro de transplante
	 *
	 * @type {number}
	 * @memberOf CentroTransplante
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 *
	 * Método de acesso para definir o identificador único do centro de transplante
	 *
	 * @memberOf CentroTransplante
	 */
	public set id(value: number) {
		this._id = value;
	}

    /**
	 * Método de acesso para obter o nome do Centro de Transplante
	 *
	 * @type {string}
	 * @memberOf CentroTransplante
	 */
	public get nome(): string {
		return this._nome;
	}

	/**
	 *
     * Método de acesso para definir o nome do Centro de Transplante
	 *
	 * @memberOf CentroTransplante
	 */
	public set nome(nome: string) {
		this._nome = nome;
	}

    /**
	 * Método de acesso para obter o cnpj do Centro de Transplante
	 *
	 * @type {string}
	 * @memberOf CentroTransplante
	 */
	public get cnpj(): string {
		return this._cnpj;
	}

	/**
	 *
     * Método de acesso para definir o cnpj do Centro de Transplante
	 *
	 * @memberOf CentroTransplante
	 */
	public set cnpj(cnpj: string) {
		this._cnpj = cnpj;
	}

	/**
	 * Método de acesso para obter o cnes do Centro de Transplante
	 *
	 * @type {string}
	 * @memberOf CentroTransplante
	 */
	public get cnes(): string {
		return this._cnes;
	}

	/**
	 *
     * Método de acesso para definir o cnes do Centro de Transplante
	 *
	 * @memberOf CentroTransplante
	 */
	public set cnes(cnes: string) {
		this._cnes = cnes;
	}

	public get contatosTelefonicos(): ContatoTelefonicoCentroTransplante[] {
		return this._contatosTelefonicos;
	}

	public set contatosTelefonicos(value: ContatoTelefonicoCentroTransplante[]) {
		this._contatosTelefonicos = value;
	}

	public get enderecos(): EnderecoContatoCentroTransplante[] {
		return this._enderecos;
	}

	public set enderecos(value: EnderecoContatoCentroTransplante[]) {
		this._enderecos = value;
	}

    /**
     * Getter laboratorioPreferencia
     * @return {Laboratorio}
     */
	public get laboratorioPreferencia(): Laboratorio {
		return this._laboratorioPreferencia;
	}

    /**
     * Setter laboratorioPreferencia
     * @param {Laboratorio} value
     */
	public set laboratorioPreferencia(value: Laboratorio) {
		this._laboratorioPreferencia = value;
	}

    /**
     * Getter funcoes
     * @return {FuncaoTransplante[]}
     */
	public get funcoes(): FuncaoTransplante[] {
		return this._funcoes;
	}

    /**
     * Setter funcoes
     * @param {FuncaoTransplante[]} value
     */
	public set funcoes(value: FuncaoTransplante[]) {
		this._funcoes = value;
	}

    /**
     * Getter centroTransplanteUsuarios
     * @return {CentroTransplanteUsuario[]}
     */
	public get centroTransplanteUsuarios(): CentroTransplanteUsuario[] {
		return this._centroTransplanteUsuarios;
	}

    /**
     * Setter centroTransplanteUsuarios
     * @param {CentroTransplanteUsuario[]} value
     */
	public set centroTransplanteUsuarios(value: CentroTransplanteUsuario[]) {
		this._centroTransplanteUsuarios = value;
	}

    /**
     * Getter emails
     * @return {EmailContatoCentroTransplante[]}
     */
	public get emails(): EmailContatoCentroTransplante[] {
		return this._emails;
	}

    /**
     * Setter emails
     * @param {EmailContatoCentroTransplante[]} value
     */
	public set emails(value: EmailContatoCentroTransplante[]) {
		this._emails = value;
	}

	public jsonToEntity(res:any):this{

		if (res.emails) {
			this.emails = [];
			res.emails.forEach((email: any) => {
				this.emails.push(new EmailContatoCentroTransplante().jsonToEntity(email));
			});
		}

		if (res.contatosTelefonicos) {
			this.contatosTelefonicos = [];
			res.contatosTelefonicos.forEach((contato: any) => {
				this.contatosTelefonicos.push(new ContatoTelefonicoCentroTransplante().jsonToEntity(contato));
			});
		}

		if (res.enderecos) {
			this.enderecos = [];
			res.enderecos.forEach((endereco: any) => {
				this.enderecos.push(new EnderecoContatoCentroTransplante().jsonToEntity(endereco));
			});
		}

		if (res.laboratorioPreferencia) {
			this.laboratorioPreferencia = new Laboratorio().jsonToEntity(res.laboratorioPreferencia);
		}

		if (res.funcoes) {
			this.funcoes = [];
			res.funcoes.forEach(funcao => {
				this.funcoes.push(new FuncaoTransplante().jsonToEntity(funcao));
			})
		}

		if(res.centroTransplanteUsuarios){
			this.centroTransplanteUsuarios = [];
			res.centroTransplanteUsuarios.forEach( usuario => {
				this.centroTransplanteUsuarios.push(new CentroTransplanteUsuario().jsonToEntity(usuario));
			});
		}

		this.id  = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
		this.cnpj = ConvertUtil.parseJsonParaAtributos(res.cnpj, new String());
		this.cnes = ConvertUtil.parseJsonParaAtributos(res.cnes, new String());

		return this;
	}
}
