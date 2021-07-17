import { StatusPaciente } from './paciente.status';
import { BaseEntidade } from "../shared/base.entidade";
import { CentroTransplante } from '../shared/dominio/centro.transplante';
import { Etnia } from '../shared/dominio/etnia';
import { Medico } from '../shared/dominio/medico';
import { Pais } from '../shared/dominio/pais';
import { Raca } from '../shared/dominio/raca';
import { UF } from '../shared/dominio/uf';
import { ConvertUtil } from '../shared/util/convert.util';
import { Busca } from './busca/busca';
import { EnderecoContatoPaciente } from './cadastro/contato/endereco/endereco.contato.paciente';
import { ContatoTelefonicoPaciente } from './cadastro/contato/telefone/contato.telefonico.paciente';
import { Diagnostico } from './cadastro/diagnostico/diagnostico';
import { Evolucao } from './cadastro/evolucao/evolucao';
import { ExamePaciente } from './cadastro/exame/exame.paciente';
import { Locus } from './cadastro/exame/locus';
import { GenotipoDTO } from './genotipo.dto';
import { Responsavel } from './responsavel';


/**
 * Classe que representa um paciente
 * @author Fillipe Queiroz
 */
export class Paciente extends BaseEntidade {

	private _rmr: number;
	private _dataCadastro: Date;
	private _dataNascimento: Date;
	private _sexo: string;
	private _abo: string;
	private _cns: string;
	private _cpf: string;
	private _email: string;
	private _nome: string;
	private _nomeMae: string;
	private _pais: Pais;
	private _naturalidade: UF;
	private _raca: Raca;
	private _etnia: Etnia;
	private _enderecosContato: EnderecoContatoPaciente[];
	private _contatosTelefonicos: ContatoTelefonicoPaciente[];
	private _responsavel: Responsavel;
	private _diagnostico: Diagnostico;
	private _evolucoes: Evolucao[];
	private _exames: ExamePaciente[] = [];
	private _motivoCadastro: number;
	private _centroAvaliador: CentroTransplante;
	private _aceiteMismatch: number;
	private _nomeAbreviado: string;
	private _medicoResponsavel: Medico;
	private _buscas: Busca[] = [];
	private _statusObito: boolean;
	private _temBuscaAtiva: boolean;
	private _maeDesconhecida: boolean;
	private _status: StatusPaciente;
	private _idTipoExameFase3: number;
	
	// Atributo transient, guarda a quantidade de notificações associadas ao paciente.
	private _quantidadeNotificacoes: number;
	// Atributo transient, guarda a quantidade de notificações não lidas associadas ao paciente.
	private _quantidadeNotificacoesNaoLidas: number;
	private _locusMismatch: Locus[] = [];
	
	// Indica que o paciente possui transferência em aberto
	private _temPedidoTransferenciaCentroAvaliadorEmAberto: boolean;
	
	// Indica quando a última avaliação está iniciada e não avaliada.
	private _temAvaliacaoIniciada: boolean;
	
	// Indica quando a última avaliação está aprovada.
	private _temAvaliacaoAprovada: boolean;
	
	private _score: number;
	
	private _tempoParaTransplante: number;
	
	private _genotipo :GenotipoDTO[] = [];
	/**
	 * Identificador único do paciente
	 * 
	 * @type {number}
	 * @memberOf Paciente
	 */
	public get rmr(): number {
		return this._rmr;
	}

	/**
	 * 
	 * Identificador único do paciente
	 * 
	 * @memberOf Paciente
	 */
	public set rmr(value: number) {
		this._rmr = value;
	}

	/**
	 * 
	 * 
	 * @type {Date}
	 * @memberOf Paciente
	 */
	public get dataCadastro(): Date {
		return this._dataCadastro;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set dataCadastro(value: Date) {
		this._dataCadastro = value;
	}

	/**
	 * 
	 * 
	 * @type {Date}
	 * @memberOf Paciente
	 */
	public get dataNascimento(): Date {
		return this._dataNascimento;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set dataNascimento(value: Date) {
		this._dataNascimento = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf Paciente
	 */
	public get sexo(): string {
		return this._sexo;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set sexo(value: string) {
		this._sexo = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf Paciente
	 */
	public get abo(): string {
		return this._abo;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set abo(value: string) {
		this._abo = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf Paciente
	 */
	public get cns(): string {
		return this._cns;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set cns(value: string) {
		this._cns = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf Paciente
	 */
	public get cpf(): string {
		return this._cpf;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set cpf(value: string) {
		this._cpf = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf Paciente
	 */
	public get email(): string {
		return this._email;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set email(value: string) {
		this._email = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf Paciente
	 */
	public get nome(): string {
		return this._nome;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set nome(value: string) {
		this._nome = value;
	}

	/**
	 * 
	 * 
	 * @type {string}
	 * @memberOf Paciente
	 */
	public get nomeMae(): string {
		return this._nomeMae;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set nomeMae(value: string) {
		this._nomeMae = value;
	}

	/**
	 * 
	 * 
	 * @type {Pais}
	 * @memberOf Paciente
	 */
	public get pais(): Pais {
		return this._pais;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set pais(value: Pais) {
		this._pais = value;
	}

	/**
	 * 
	 * 
	 * @type {UF}
	 * @memberOf Paciente
	 */
	public get naturalidade(): UF {
		return this._naturalidade;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set naturalidade(value: UF) {
		this._naturalidade = value;
	}

	/**
	 * 
	 * 
	 * @type {Raca}
	 * @memberOf Paciente
	 */
	public get raca(): Raca {
		return this._raca;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set raca(value: Raca) {
		this._raca = value;
	}

	/**
	 * 
	 * 
	 * @type {Etnia}
	 * @memberOf Paciente
	 */
	public get etnia(): Etnia {
		return this._etnia;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set etnia(value: Etnia) {
		this._etnia = value;
	}

	/**
	 * 
	 * 
	 * @type {EnderecoContato}
	 * @memberOf Paciente
	 */
	public get enderecosContato(): EnderecoContatoPaciente[] {
		return this._enderecosContato;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set enderecosContato(value: EnderecoContatoPaciente[]) {
		this._enderecosContato = value;
	}

	/**
	 * 
	 * 
	 * @type {ContatoTelefonico[]}
	 * @memberOf Paciente
	 */
	public get contatosTelefonicos(): ContatoTelefonicoPaciente[] {
		return this._contatosTelefonicos;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set contatosTelefonicos(value: ContatoTelefonicoPaciente[]) {
		this._contatosTelefonicos = value;
	}

	/**
	 * 
	 * 
	 * @type {Responsavel}
	 * @memberOf Paciente
	 */
	public get responsavel(): Responsavel {
		return this._responsavel;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set responsavel(value: Responsavel) {
		this._responsavel = value;
	}

	/**
	 * 
	 * 
	 * @type {Diagnostico}
	 * @memberOf Paciente
	 */
	public get diagnostico(): Diagnostico {
		return this._diagnostico;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paciente
	 */
	public set diagnostico(value: Diagnostico) {
		this._diagnostico = value;
	}


	/**
	 * @type {Evolucao[]}
	 * @memberof Paciente
	 */
	public get evolucoes(): Evolucao[] {
		return this._evolucoes;
	}

	/**
	 * @memberof Paciente
	 */
	public set evolucoes(value: Evolucao[]) {
		this._evolucoes = value;
	}


	/**
	 * @type {Exame[]}@memberof Paciente
	 */
	public get exames(): ExamePaciente[] {
		return this._exames;
	}

	/**
	 * @memberof Paciente
	 */
	public set exames(value: ExamePaciente[]) {
		this._exames = value;
	}

	/**
	 * @type {number}
	 * @memberOf Paciente
	 */
	public get motivoCadastro(): number {
		return this._motivoCadastro;
	}

	/**
	 * @memberOf Paciente
	 */
	public set motivoCadastro(value: number) {
		this._motivoCadastro = value;
	}

	/**
	 * @type {CentroAvaliador}
	 * @memberOf Paciente
	 */
	public get centroAvaliador(): CentroTransplante {
		return this._centroAvaliador;
	}

	/**
	 * @memberOf Paciente
	 */
	public set centroAvaliador(value: CentroTransplante) {
		this._centroAvaliador = value;
	}

	/**
	 * @type {number}
	 * @memberof Paciente
	 */
	public get aceiteMismatch(): number {
		return this._aceiteMismatch;
	}

	/**
	 * @memberof Paciente
	 */
	public set aceiteMismatch(value: number) {
		this._aceiteMismatch = value;
	}

	/**
	 * @type {string}
	 * @memberOf Paciente
	 */
	public get nomeAbreviado(): string {
		return this._nomeAbreviado;
	}

	/**
	 * @memberOf Paciente
	 */
	public set nomeAbreviado(value: string) {
		this._nomeAbreviado = value;
	}

	/**
	 * @type {Medico}
	 * @memberOf Paciente
	 */
	public get medicoResponsavel(): Medico {
		return this._medicoResponsavel;
	}

	/**
	 * @memberOf Paciente
	 */
	public set medicoResponsavel(medico: Medico) {
		this._medicoResponsavel = medico
	}

	public get quantidadeNotificacoes(): number {
		return this._quantidadeNotificacoes;
	}

	public set quantidadeNotificacoes(value: number) {
		this._quantidadeNotificacoes = value;
	}

	/**
	 * Buscas do paciente
	 * @return lista de buscas do paciente
	 */
	public get buscas(): Busca[] {
		return this._buscas;
	}

	/**
	 * Buscas do paciente
	 */
	public set buscas(value: Busca[]) {
		this._buscas = value;
	}

	/**
	 * Indica se o paciente está em obito ou não.
	 */
	public get statusObito(): boolean {
		return this._statusObito;
	}

	/**
	 * Indica se o paciente está em obito ou não.
	 */
	public set statusObito(value: boolean) {
		this._statusObito = value;
	}

	public get temBuscaAtiva(): boolean {
		return this._temBuscaAtiva;
	}

	public set temBuscaAtiva(value: boolean) {
		this._temBuscaAtiva = value;
	}

    /**
     * Utilizado somente no front-end, retorna se o nome da mãe
	 * é desconhecido para o cadastro do paciente.
     * @return {boolean}
     */
	public get maeDesconhecida(): boolean {
		return this._maeDesconhecida;
	}

    /**
     * Setter maeDesconhecida
     * @param {boolean} value
     */
	public set maeDesconhecida(value: boolean) {
		this._maeDesconhecida = value;
	}

	  /**
     * Getter locusMismatch
     * @return {Locus[] }
     */
	public get locusMismatch(): Locus[]  {
		return this._locusMismatch;
	}

    /**
     * Setter locusMismatch
     * @param {Locus[] } value
     */
	public set locusMismatch(value: Locus[] ) {
		this._locusMismatch = value;
	}

    /**
     * Retorna TRUE, caso exista uma pedido de transferência em aberto.
     * @return {boolean}
     */
	public get temPedidoTransferenciaCentroAvaliadorEmAberto(): boolean {
		return this._temPedidoTransferenciaCentroAvaliadorEmAberto;
	}

	public set temPedidoTransferenciaCentroAvaliadorEmAberto(value: boolean) {
		this._temPedidoTransferenciaCentroAvaliadorEmAberto = value;
	}

    /**
     * Retorna TRUE quando a última avaliação está em andamento.
     * @return {boolean}
     */
	public get temAvaliacaoIniciada(): boolean {
		return this._temAvaliacaoIniciada;
	}

    /**
     * Setter temAvaliacaoEmAndamento
     * @param {boolean} value
     */
	public set temAvaliacaoIniciada(value: boolean) {
		this._temAvaliacaoIniciada = value;
	}

    /**
     * Getter quantidadeNotificacoesNaoLidas
     * @return {number}
     */
	public get quantidadeNotificacoesNaoLidas(): number {
		return this._quantidadeNotificacoesNaoLidas;
	}

    /**
     * Setter quantidadeNotificacoesNaoLidas
     * @param {number} value
     */
	public set quantidadeNotificacoesNaoLidas(value: number) {
		this._quantidadeNotificacoesNaoLidas = value;
	}
	

    /**
     * Getter score
     * @return {number}
     */
	public get score(): number {
		return this._score;
	}

    /**
     * Setter score
     * @param {number} value
     */
	public set score(value: number) {
		this._score = value;
	}

    /**
     * Getter temAvaliacaoAprovada
     * @return {boolean}
     */
	public get temAvaliacaoAprovada(): boolean {
		return this._temAvaliacaoAprovada;
	}

    /**
     * Setter temAvaliacaoAprovada
     * @param {boolean} value
     */
	public set temAvaliacaoAprovada(value: boolean) {
		this._temAvaliacaoAprovada = value;
	}

    /**
     * Getter tempoParaTransplante
     * @return {number}
     */
	public get tempoParaTransplante(): number {
		return this._tempoParaTransplante;
	}

    /**
     * Setter tempoParaTransplante
     * @param {number} value
     */
	public set tempoParaTransplante(value: number) {
		this._tempoParaTransplante = value;
	}

    /**
     * Getter genotipo
     * @return {GenotipoDTO[] }
     */
	public get genotipo(): GenotipoDTO[]  {
		return this._genotipo;
	}

    /**
     * Setter genotipo
     * @param {GenotipoDTO[] } value
     */
	public set genotipo(value: GenotipoDTO[] ) {
		this._genotipo = value;
	}

    /**
     * Getter status
     * @return {StatusPaciente}
     */
	public get status(): StatusPaciente {
		return this._status;
	}

    /**
     * Setter status
     * @param {StatusPaciente} value
     */
	public set status(value: StatusPaciente) {
		this._status = value;
	}

    /**
     * Getter idTipoExameFase3
     * @return {number}
     */
	public get idTipoExameFase3(): number {
		return this._idTipoExameFase3;
	}

    /**
     * Setter idTipoExameFase3
     * @param {number} value
     */
	public set idTipoExameFase3(value: number) {
		this._idTipoExameFase3 = value;
	}

	public jsonToEntity(res: any): this {

		if (res.locusMismatch) {
			this.locusMismatch = [];
			res.locusMismatch.forEach(locus => {
				this.locusMismatch.push(new Locus().jsonToEntity(locus));
			});
		}

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
			this.naturalidade = new UF().jsonToEntity(res.naturalidade);
		}

		if (res.enderecosContato) {
			this.enderecosContato = [];
			res.enderecosContato.forEach(endereco => {
				this.enderecosContato.push(new EnderecoContatoPaciente().jsonToEntity(endereco));
			})
		}

		if (res.contatosTelefonicos) {
			this.contatosTelefonicos = [];
			res.contatosTelefonicos.forEach(telefone =>{
				this.contatosTelefonicos.push(new ContatoTelefonicoPaciente().jsonToEntity(telefone));
			});
		}

		if (res.medicoResponsavel) {
			this.medicoResponsavel = new Medico().jsonToEntity(res.medicoResponsavel);
		}

		if (res.centroAvaliador) {
			this.centroAvaliador = new CentroTransplante().jsonToEntity(res.centroAvaliador);
		}

		if (res.diagnostico) {
			this.diagnostico = new Diagnostico().jsonToEntity(res.diagnostico);
		}

		if (res.status){
			this.status = new StatusPaciente().jsonToEntity(res.status);
		}
		if(res.responsavel){
			this.responsavel = new Responsavel(null, null, null).jsonToEntity(res.responsavel);
		}

		this.rmr = ConvertUtil.parseJsonParaAtributos(res.rmr, new Number());
		this.dataCadastro = ConvertUtil.parseJsonParaAtributos(res.rdataCadastromr, new Date());
		this.dataNascimento = ConvertUtil.parseJsonParaAtributos(res.dataNascimento, new Date());
		this.sexo = ConvertUtil.parseJsonParaAtributos(res.sexo, new String());
		this.abo = ConvertUtil.parseJsonParaAtributos(res.abo, new String());
		this.cns = ConvertUtil.parseJsonParaAtributos(res.cns, new String());
		this.cpf = ConvertUtil.parseJsonParaAtributos(res.cpf, new String());
		this.email = ConvertUtil.parseJsonParaAtributos(res.email, new String());
		this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
		this.nomeMae = ConvertUtil.parseJsonParaAtributos(res.nomeMae, new String());
		this.motivoCadastro = ConvertUtil.parseJsonParaAtributos(res.motivoCadastro, new Number());
		this.aceiteMismatch = ConvertUtil.parseJsonParaAtributos(res.aceiteMismatch, new Number());
		this.nomeAbreviado = ConvertUtil.parseJsonParaAtributos(res.nomeAbreviado, new String());
		this.statusObito = ConvertUtil.parseJsonParaAtributos(res.statusObito, new Boolean());
		this.temBuscaAtiva = ConvertUtil.parseJsonParaAtributos(res.temBuscaAtiva, new Boolean());
		this.maeDesconhecida = ConvertUtil.parseJsonParaAtributos(!res.nomeMae? true: false, new Boolean());
		this.temPedidoTransferenciaCentroAvaliadorEmAberto = 
			ConvertUtil.parseJsonParaAtributos(res.temPedidoTransferenciaCentroAvaliadorEmAberto, new Boolean());
		this.temAvaliacaoIniciada = ConvertUtil.parseJsonParaAtributos(res.temAvaliacaoIniciada, new Boolean());
		this.quantidadeNotificacoes = ConvertUtil.parseJsonParaAtributos(res.quantidadeNotificacoes, new Number());
		this.score = ConvertUtil.parseJsonParaAtributos(res.score, new Number());
		this.quantidadeNotificacoesNaoLidas = ConvertUtil.parseJsonParaAtributos(res.quantidadeNotificacoesNaoLidas, new Number());
		this.temAvaliacaoAprovada = ConvertUtil.parseJsonParaAtributos(res.temAvaliacaoAprovada, new Boolean());
		this.tempoParaTransplante = ConvertUtil.parseJsonParaAtributos(res.tempoParaTransplante, new Number());
		this.idTipoExameFase3 = ConvertUtil.parseJsonParaAtributos(res.idTipoExameFase3, new Number());

		return this;
	}

}

