import { StatusDoador } from './../../dominio/status.doador';
import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from '../../base.entidade';
import { TiposDoador } from '../../enums/tipos.doador';
import { BuscaChecklistDTO } from './classes/busca.checklist.dto';
import { IFiltrosOrdenacaoBuscaDTO } from 'app/shared/dto/interface.filtros.ordenacao.busca.dto';
import { GenotipoDTO } from 'app/paciente/genotipo.dto';


/**
 * @description DTO que representa um item no carrousel de matchs.
 *
 * @author Pizão
 * @export
 * @class MatchDTO
 * @extends {BaseEntidade}
 */
export class MatchDTO extends BaseEntidade implements IFiltrosOrdenacaoBuscaDTO {


	private _id: number;
	private _idDoador: number;
    private _dmr: number;
	private _nome: string;
	private _registroOrigem: string;
    private _sexo: string;
    private _dataNascimento: Date;
    private _peso: number;
    private _abo: string;
    private _mismatch: string;
    private _classificacao: string;
	private _outrosProcessos: number;
	private _possuiRessalva: boolean;
	private _possuiComentario: boolean = false;
	private _ehFavorito:boolean = false;
	private _dataAtualizacao: Date;
	private _idRegistro: string;

	private _temPrescricao: boolean;

	private _idProcesso: number;
	private _idPedidoExame: number;
	private _idTarefa: number;
	private _idTipoTarefa:number;
	private _idSolicitacao:number;

	private _quantidadeTCNPorKilo: number;
	private _quantidadeCD34PorKilo: number;
	private _tipoDoador: TiposDoador;
	private _idBscup: string;

	private _buscaChecklist: BuscaChecklistDTO[];

	private _locusPedidoExameParaPaciente:string;
	private _locusPedidoExame: string;
	private _rmrPedidoExame:number;

	private _possuiGenotipoDivergente: boolean;

	private _fase: string;
	private _disponibilizado: boolean;
	private _tipoSolicitacao: number;

	private _genotipoDoador: GenotipoDTO[];
	private _respostaQtdGestacaoDoador: number;
	private _somaQualificacao: number;
  private _statusDoador: StatusDoador;

  private _ordenacaoWmdaMatch: number;

	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get nome(): string {
		return this._nome;
	}

	public set nome(value: string) {
		this._nome = value;
	}

    /**
     * Getter idDoador
     * @return {number}
     */
	public get idDoador(): number {
		return this._idDoador;
	}

    /**
     * Setter idDoador
     * @param {number} value
     */
	public set idDoador(value: number) {
		this._idDoador = value;
	}


	public get dmr(): number {
		return this._dmr;
	}

	public set dmr(value: number) {
		this._dmr = value;
	}

	public set sexo(value: string) {
		this._sexo = value;
	}

	public get sexo(): string {
		return this._sexo;
	}

	public get dataNascimento(): Date {
		return this._dataNascimento;
	}

	public set dataNascimento(value: Date) {
		this._dataNascimento = value;
	}

	public get peso(): number {
		return this._peso;
	}

	public set peso(value: number) {
		this._peso = value;
	}

	public get abo(): string {
		return this._abo;
	}

	public set abo(value: string) {
		this._abo = value;
	}

	public get mismatch(): string {
		return this._mismatch;
	}

	public set mismatch(value: string) {
		this._mismatch = value;
	}

	public get classificacao(): string {
		return this._classificacao;
	}

	public set classificacao(value: string) {
		this._classificacao = value;
    }

	public get outrosProcessos(): number {
		return this._outrosProcessos;
	}

	public set outrosProcessos(value: number) {
		this._outrosProcessos = value;
	}


	public get possuiRessalva(): boolean {
		return this._possuiRessalva;
	}

	public set possuiRessalva(value: boolean) {
		this._possuiRessalva = value;
	}

	public get possuiComentario(): boolean {
		return this._possuiComentario;
	}

	public set possuiComentario(value: boolean) {
		this._possuiComentario = value;
	}
	public get ehFavorito(): boolean {
		return this._ehFavorito;
	}

	public set ehFavorito(value: boolean) {
		this._ehFavorito = value;
	}
	public get dataAtualizacao(): Date {
		return this._dataAtualizacao;
	}

	public set dataAtualizacao(value: Date) {
		this._dataAtualizacao = value;
	}


	public get temPrescricao(): boolean {
		return this._temPrescricao;
	}

	public set temPrescricao(value: boolean) {
		this._temPrescricao = value;
	}

    /**
     * Getter idRegistro
     * @return {string}
     */
	public get idRegistro(): string {
		return this._idRegistro;
	}

    /**
     * Setter idRegistro
     * @param {string} value
     */
	public set idRegistro(value: string) {
		this._idRegistro = value;
	}

    /**
     * Getter registroOrigem
     * @return {string}
     */
	public get registroOrigem(): string {
		return this._registroOrigem;
	}

    /**
     * Setter registroOrigem
     * @param {string} value
     */
	public set registroOrigem(value: string) {
		this._registroOrigem = value;
	}


    /**
     * Getter idProcesso
     * @return {number}
     */
	public get idProcesso(): number {
		return this._idProcesso;
	}

    /**
     * Setter idProcesso
     * @param {number} value
     */
	public set idProcesso(value: number) {
		this._idProcesso = value;
	}

    /**
     * Getter idPedidoExame
     * @return {number}
     */
	public get idPedidoExame(): number {
		return this._idPedidoExame;
	}

    /**
     * Setter idPedidoExame
     * @param {number} value
     */
	public set idPedidoExame(value: number) {
		this._idPedidoExame = value;
	}

    /**
     * Getter idTarefa
     * @return {number}
     */
	public get idTarefa(): number {
		return this._idTarefa;
	}

    /**
     * Setter idTarefa
     * @param {number} value
     */
	public set idTarefa(value: number) {
		this._idTarefa = value;
	}

    /**
     * Getter idTipoTarefa
     * @return {number}
     */
	public get idTipoTarefa(): number {
		return this._idTipoTarefa;
	}

    /**
     * Setter idTipoTarefa
     * @param {number} value
     */
	public set idTipoTarefa(value: number) {
		this._idTipoTarefa = value;
	}

    /**
     * Getter idSolicitacao
     * @return {number}
     */
	public get idSolicitacao(): number {
		return this._idSolicitacao;
	}

    /**
     * Setter idSolicitacao
     * @param {number} value
     */
	public set idSolicitacao(value: number) {
		this._idSolicitacao = value;
	}

    /**
     * Getter quantidadeTCNPorKilo
     * @return {number}
     */
	public get quantidadeTCNPorKilo(): number {
		return this._quantidadeTCNPorKilo;
	}

    /**
     * Setter quantidadeTCNPorKilo
     * @param {number} value
     */
	public set quantidadeTCNPorKilo(value: number) {
		this._quantidadeTCNPorKilo = value;
	}

    /**
     * Getter quantidadeCD34PorKilo
     * @return {number}
     */
	public get quantidadeCD34PorKilo(): number {
		return this._quantidadeCD34PorKilo;
	}

    /**
     * Setter quantidadeCD34PorKilo
     * @param {number} value
     */
	public set quantidadeCD34PorKilo(value: number) {
		this._quantidadeCD34PorKilo = value;
	}

    /**
     * Getter tipoDoador
     * @return {TiposDoador}
     */
	public get tipoDoador(): TiposDoador {
		return this._tipoDoador;
	}

    /**
     * Setter tipoDoador
     * @param {TiposDoador} value
     */
	public set tipoDoador(value: TiposDoador) {
		this._tipoDoador = value;
	}


	/**
	 * Verifica se o match é correspondente a
	 * medula.
	 * @return TRUE se for medula.
	 */
	isMedula(): boolean{
		return TiposDoador.NACIONAL == this.tipoDoador
					|| TiposDoador.INTERNACIONAL == this.tipoDoador;
	}

	/**
	 * Verifica se o match é correspondente a
	 * cordao.
	 * @return TRUE se for cordao.
	 */
	isCordao(): boolean{
		return TiposDoador.CORDAO_NACIONAL == this.tipoDoador
					|| TiposDoador.CORDAO_INTERNACIONAL == this.tipoDoador;
	}

	/**
	 * @description Indica se o match possui itens de checklist
	 * que o analista de busca precisa tomar ciência e dar como visto.
	 * @type {BuscaChecklistDTO[]}
	 */
	public get buscaChecklist(): BuscaChecklistDTO[] {
		return this._buscaChecklist;
	}

	public set buscaChecklist(value: BuscaChecklistDTO[]) {
		this._buscaChecklist = value;
	}


    /**
     * Getter locusPedidoExameParaPaciente
     * @return {string}
     */
	public get locusPedidoExameParaPaciente(): string {
		return this._locusPedidoExameParaPaciente;
	}

    /**
     * Setter locusPedidoExameParaPaciente
     * @param {string} value
     */
	public set locusPedidoExameParaPaciente(value: string) {
		this._locusPedidoExameParaPaciente = value;
	}

    /**
     * Getter locusPedidoExame
     * @return {string}
     */
	public get locusPedidoExame(): string {
		return this._locusPedidoExame;
	}

    /**
     * Setter locusPedidoExame
     * @param {string} value
     */
	public set locusPedidoExame(value: string) {
		this._locusPedidoExame = value;
	}

    /**
     * Getter rmrPedidoExame
     * @return {number}
     */
	public get rmrPedidoExame(): number {
		return this._rmrPedidoExame;
	}

    /**
     * Setter rmrPedidoExame
     * @param {number} value
     */
	public set rmrPedidoExame(value: number) {
		this._rmrPedidoExame = value;
	}

    /**
     * Getter possuiGenotipoDivergente
     * @return {boolean}
     */
	public get possuiGenotipoDivergente(): boolean {
		return this._possuiGenotipoDivergente;
	}

    /**
     * Setter possuiGenotipoDivergente
     * @param {boolean} value
     */
	public set possuiGenotipoDivergente(value: boolean) {
		this._possuiGenotipoDivergente = value;
	}

    /**
     * Getter fase
     * @return {string}
     */
	public get fase(): string {
		return this._fase;
	}

    /**
     * Setter fase
     * @param {string} value
     */
	public set fase(value: string) {
		this._fase = value;
	}

    /**
     * Getter disponibilizado
     * @return {boolean}
     */
	public get disponibilizado(): boolean {
		return this._disponibilizado;
	}

    /**
     * Setter disponibilizado
     * @param {boolean} value
     */
	public set disponibilizado(value: boolean) {
		this._disponibilizado = value;
	}

    /**
     * Getter tipoSolicitacao
     * @return {number}
     */
	public get tipoSolicitacao(): number {
		return this._tipoSolicitacao;
	}

    /**
     * Setter tipoSolicitacao
     * @param {number} value
     */
	public set tipoSolicitacao(value: number) {
		this._tipoSolicitacao = value;
	}


    /**
     * Getter idBscup
     * @return {string}
     */
	public get idBscup(): string {
		return this._idBscup;
	}

    /**
     * Setter idBscup
     * @param {string} value
     */
	public set idBscup(value: string) {
		this._idBscup = value;
	}

	/**
     * Getter genotipoDoador
     * @return {GenotipoDTO[]}
     */
	public get genotipoDoador(): GenotipoDTO[] {
		return this._genotipoDoador;
	}

    /**
     * Setter genotipoDoador
     * @param {GenotipoDTO[]} value
     */
	public set genotipoDoador(value: GenotipoDTO[]) {
		this._genotipoDoador = value;
	}

    /**
     * Getter somaQualificacao
     * @return {number}
     */
	public get somaQualificacao(): number {
		return this._somaQualificacao;
	}

    /**
     * Setter somaQualificacao
     * @param {number} value
     */
	public set somaQualificacao(value: number) {
		this._somaQualificacao = value;
	}

	/**
     * Getter statusDoador
     * @return {StatusDoador}
     */
	public get statusDoador(): StatusDoador {
		return this._statusDoador;
	}

	/**
     * Setter statusDoador
     * @param {StatusDoador} value
     */
	public set statusDoador(value: StatusDoador) {
		this._statusDoador = value;
	}

    /**
     * Getter respostaQtdGestacaoDoador
     * @return {number}
     */
	public get respostaQtdGestacaoDoador(): number {
		return this._respostaQtdGestacaoDoador;
	}

    /**
     * Setter respostaQtdGestacaoDoador
     * @param {number} value
     */
	public set respostaQtdGestacaoDoador(value: number) {
		this._respostaQtdGestacaoDoador = value;
	}


    /**
     * Getter ordenacaoWmdaMatch
     * @return {number}
     */
	public get ordenacaoWmdaMatch(): number {
		return this._ordenacaoWmdaMatch;
	}

    /**
     * Setter ordenacaoWmdaMatch
     * @param {number} value
     */
	public set ordenacaoWmdaMatch(value: number) {
		this._ordenacaoWmdaMatch = value;
	}


	public jsonToEntity(res: any): MatchDTO {

		if (res.tipoDoador != null) {
			this.tipoDoador = TiposDoador.valueOf(res.tipoDoador);
			//let idTipoDoador: string = new String(res.tipoDoador).valueOf();
			//this.tipoDoador = TiposDoador[idTipoDoador as keyof typeof TiposDoador];
		}

		this.statusDoador = new StatusDoador().jsonToEntity(res);

		if (res.buscaChecklist) {
			this.buscaChecklist = [];
			res.buscaChecklist.forEach(check => {
				this.buscaChecklist.push(new BuscaChecklistDTO().jsonToEntity(check));
			})
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id , new Number());
		this.idDoador = ConvertUtil.parseJsonParaAtributos(res.idDoador , new Number());
		this.dmr = ConvertUtil.parseJsonParaAtributos(res.dmr , new Number());
		this.nome = ConvertUtil.parseJsonParaAtributos(res.nome , new String());
		this.registroOrigem = ConvertUtil.parseJsonParaAtributos(res.registroOrigem , new String());
		this.sexo = ConvertUtil.parseJsonParaAtributos(res.sexo , new String());
		this.dataNascimento = ConvertUtil.parseJsonParaAtributos(res.dataNascimento , new Date());
		this.peso = ConvertUtil.parseJsonParaAtributos(res.peso , new Number());
		this.abo = ConvertUtil.parseJsonParaAtributos(res.abo , new String());
		this.mismatch = ConvertUtil.parseJsonParaAtributos(res.mismatch , new String());
		this.classificacao = ConvertUtil.parseJsonParaAtributos(res.classificacao , new String());
		this.outrosProcessos = ConvertUtil.parseJsonParaAtributos(res.outrosProcessos , new Number());
		this.possuiRessalva = ConvertUtil.parseJsonParaAtributos(res.possuiRessalva , new Boolean());
		this.possuiComentario = ConvertUtil.parseJsonParaAtributos(res.possuiComentario , new Boolean());
		this.ehFavorito = ConvertUtil.parseJsonParaAtributos(res.ehFavorito , new Boolean());
		this.dataAtualizacao = ConvertUtil.parseJsonParaAtributos(res.dataAtualizacao , new Date());
		this.idRegistro = ConvertUtil.parseJsonParaAtributos(res.idRegistro , new String());

		this.temPrescricao = ConvertUtil.parseJsonParaAtributos(res.temPrescricao , new Boolean());

		this.idProcesso = ConvertUtil.parseJsonParaAtributos(res.idProcesso , new Number());
		this.idPedidoExame = ConvertUtil.parseJsonParaAtributos(res.idPedidoExame , new Number());
		this.idTarefa = ConvertUtil.parseJsonParaAtributos(res.idTarefa , new Number());
		this.idTipoTarefa = ConvertUtil.parseJsonParaAtributos(res.idTipoTarefa , new Number());
		this.idSolicitacao = ConvertUtil.parseJsonParaAtributos(res.idSolicitacao , new Number());

		this.quantidadeTCNPorKilo = ConvertUtil.parseJsonParaAtributos(res.quantidadeTCNPorKilo , new Number());
		this.quantidadeCD34PorKilo = ConvertUtil.parseJsonParaAtributos(res.quantidadeCD34PorKilo , new Number());
		this.idBscup = ConvertUtil.parseJsonParaAtributos(res.idBscup , new String());
		this.locusPedidoExame = ConvertUtil.parseJsonParaAtributos(res.locusPedidoExame , new String());
		this.locusPedidoExameParaPaciente = ConvertUtil.parseJsonParaAtributos(res.locusPedidoExameParaPaciente , new String());
		this.rmrPedidoExame = ConvertUtil.parseJsonParaAtributos(res.rmrPedidoExame , new Number());
		this.possuiGenotipoDivergente = ConvertUtil.parseJsonParaAtributos(res.possuiGenotipoDivergente, new Boolean());
		this.fase = ConvertUtil.parseJsonParaAtributos(res.fase, new String());
		this.disponibilizado = ConvertUtil.parseJsonParaAtributos(res.disponibilizado, new Boolean());
		this.tipoSolicitacao = ConvertUtil.parseJsonParaAtributos(res.tipoSolicitacao, new Number());
		this.genotipoDoador = res.genotipoDoadores? res.genotipoDoadores: null;
		this.somaQualificacao = ConvertUtil.parseJsonParaAtributos(res.somaPesoQualificacao, new Number());
		this.statusDoador = ConvertUtil.parseJsonParaAtributos(res.statusDoador, new String());
		this.respostaQtdGestacaoDoador = ConvertUtil.parseJsonParaAtributos(res.respostaQtdGestacaoDoador, new Number());
    this.ordenacaoWmdaMatch = ConvertUtil.parseJsonParaAtributos(res.ordenacaoWmdaMatch, new Number());

        return this;
    }

}
