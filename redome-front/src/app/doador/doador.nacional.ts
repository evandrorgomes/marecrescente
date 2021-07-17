import { Doador } from 'app/doador/doador';
import { Municipio } from 'app/shared/dominio/municipio';
import { TiposDoador } from 'app/shared/enums/tipos.doador';
import { Etnia } from '../shared/dominio/etnia';
import { Pais } from '../shared/dominio/pais';
import { Raca } from '../shared/dominio/raca';
import { ConvertUtil } from '../shared/util/convert.util';
import { EstadoCivil } from './../shared/dominio/estadoCivil';
import { ContatoTelefonicoDoador } from './contato.telefonico.doador';
import { EmailContatoDoador } from './email.contato.doador';
import { EnderecoContatoDoador } from './endereco.contato.doador';
import { ExameDoadorNacional } from './exame.doador.nacional';



export class DoadorNacional extends Doador {

	private _dmr: number;
	private _cpf: string;
	private _rg: string;
	private _orgaoExpedidor: string;
	private _nome: string;
	private _nomeSocial: string;
	private _nomeMae: string;
	private _contatosTelefonicos: ContatoTelefonicoDoador[];
	private _raca: Raca;
	private _etnia: Etnia;
	private _pais: Pais;
	private _naturalidade: Municipio;
	private _emailsContato: EmailContatoDoador[];
	private _enderecosContato: EnderecoContatoDoador[];
	private _altura: number;
	private _nomePai: string;
	private _exames: ExameDoadorNacional[];
	private _estadoCivil: EstadoCivil;
	private _fumante: boolean;
	private _fumanteFormatado: string;

	constructor() {
		super();
		this.tipoDoador = TiposDoador.NACIONAL;
	}

	public get dmr(): number {
		return this._dmr;
	}

	public set dmr(value: number) {
		this._dmr = value;
	}

	public get contatosTelefonicos(): ContatoTelefonicoDoador[] {
		return this._contatosTelefonicos;
	}

	public set contatosTelefonicos(value: ContatoTelefonicoDoador[]) {
		this._contatosTelefonicos = value;
	}

	public get nome(): string {
		return this._nome;
	}

	public set nome(value: string) {
		this._nome = value;
	}

	public get nomeSocial(): string {
		return this._nomeSocial;
	}

	public set nomeSocial(value: string) {
		this._nomeSocial = value;
	}

	public get cpf(): string {
		return this._cpf;
	}

	public set cpf(value: string) {
		this._cpf = value;
	}

	public get rg(): string {
		return this._rg;
	}

	public set rg(value: string) {
		this._rg = value;
	}

	public get orgaoExpedidor(): string {
		return this._orgaoExpedidor;
	}

	public set orgaoExpedidor(value: string) {
		this._orgaoExpedidor = value;
	}

	public get nomeMae(): string {
		return this._nomeMae;
	}

	public set nomeMae(value: string) {
		this._nomeMae = value;
	}

	public get raca(): Raca {
		return this._raca;
	}

	public set raca(value: Raca) {
		this._raca = value;
	}

	public get etnia(): Etnia {
		return this._etnia;
	}

	public set etnia(value: Etnia) {
		this._etnia = value;
	}

	public get pais(): Pais {
		return this._pais;
	}

	public set pais(value: Pais) {
		this._pais = value;
	}

	public get naturalidade(): Municipio {
		return this._naturalidade;
	}

	public set naturalidade(value: Municipio) {
		this._naturalidade = value;
	}

	public get emailsContato(): EmailContatoDoador[] {
		return this._emailsContato;
	}

	public set emailsContato(value: EmailContatoDoador[]) {
		this._emailsContato = value;
	}

	public get enderecosContato(): EnderecoContatoDoador[] {
		return this._enderecosContato;
	}

	public set enderecosContato(value: EnderecoContatoDoador[]) {
		this._enderecosContato = value;
	}

	public get altura(): number {
		return this._altura;
	}

	public set altura(value: number) {
		this._altura = value;
	}

	public get nomePai(): string {
		return this._nomePai;
	}

	public set nomePai(value: string) {
		this._nomePai = value;
	}

    /**
     * Getter exames
     * @return {ExameDoadorNacional[]}
     */
	public get exames(): ExameDoadorNacional[] {
		return this._exames;
	}

    /**
     * Setter exames
     * @param {ExameDoadorNacional[]} value
     */
	public set exames(value: ExameDoadorNacional[]) {
		this._exames = value;
	}

	public get fumante(): boolean {
		return this._fumante;
	}

	public set fumante(value: boolean) {
		this._fumante = value;
	}

    /**
     * Getter fumanteFormatado
     * @return {string}
     */
	public get fumanteFormatado(): string {
		return this._fumante ? 'SIM' : 'NÃO';
	}

    /**
     * Getter estadoCivis
     * @return {EstadoCivil}
     */
	public get estadoCivil(): EstadoCivil {
		return this._estadoCivil;
	}

    /**
     * Setter estadoCivil
     * @param {EstadoCivil} value
     */
	public set estadoCivil(value: EstadoCivil) {
		this._estadoCivil = value;
	}

	get identificacao(): string {
		return new String(this._dmr).valueOf();
	}

	public jsonToEntity(res: any): this {

		if (res.raca) {
			this.raca = new Raca().jsonToEntity(res.raca);
		}

		if (res.etnia) {
			this.etnia = new Etnia().jsonToEntity(res.etnia);
		}

		if (res.pais) {
			this.pais = new Pais().jsonToEntity(res.pais);
		}

		if (res.naturalidade) {
			this.naturalidade = new Municipio().jsonToEntity(res.naturalidade);
		}

		if (res.emailsContato) {
			this.emailsContato = [];
			res.emailsContato.forEach(email => {
				this.emailsContato.push(new EmailContatoDoador().jsonToEntity(email));
			});
		}

		if (res.estadoCivil) {
			this.estadoCivil = new EstadoCivil().jsonToEntity(res.estadoCivil);
		}

		if (res.contatosTelefonicos) {
			this.contatosTelefonicos = [];
			res.contatosTelefonicos.forEach(contato => {
				this.contatosTelefonicos.push(new ContatoTelefonicoDoador().jsonToEntity(contato));
			});
		}

		if (res.enderecosContato) {
			this.enderecosContato = [];
			res.enderecosContato.forEach(endereco => {
				this.enderecosContato.push(new EnderecoContatoDoador().jsonToEntity(endereco));
			})
		}

		if (res.exames) {
			this.exames = [];
			res.exames.forEach(exame => {
				this.exames.push(new ExameDoadorNacional().jsonToEntity(exame));
			})
		}

		this.dmr = ConvertUtil.parseJsonParaAtributos(res.dmr, new Number());
		this.cpf = ConvertUtil.parseJsonParaAtributos(res.cpf, new String());
		this.rg = ConvertUtil.parseJsonParaAtributos(res.rg, new String());
		this.orgaoExpedidor = ConvertUtil.parseJsonParaAtributos(res.orgaoExpedidor, new String());
		this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
		this.nomeSocial = ConvertUtil.parseJsonParaAtributos(res.nomeSocial, new String());
		this.nomeMae = ConvertUtil.parseJsonParaAtributos(res.nomeMae, new String());
		this.altura = ConvertUtil.parseJsonParaAtributos(res.altura, new Number());
		this.nomePai = ConvertUtil.parseJsonParaAtributos(res.nomePai, new String());
		this.fumante = ConvertUtil.parseJsonParaAtributos(res.fumante, new Boolean());

		super.jsonToEntity(res);

        return this;
    }
}
