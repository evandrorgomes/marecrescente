import { BaseEntidade } from "../base.entidade";
import { FonteCelula } from "./fonte.celula";
import { DestinoColeta } from "./destino.coleta";
import { PedidoColeta } from "app/doador/consulta/coleta/pedido.coleta";

export class RecebimentoColeta extends BaseEntidade {

  private _id:number;

	private _dataDescarte:Date;

	private _dataInfusao:Date;

	private _dataPrevistaInfusao:Date;

	private _dataRecebimento:Date;

	private _recebeuColeta:Boolean;

	private _totalTotalCd34:number;

	private _totalTotalTcn:number;

	private _justificativaCongelamento:String;

	private _justificativaDescarte:String;

	private _destinoColeta:DestinoColeta;

	private _fonteCelula: FonteCelula;

  private _pedidoColeta:PedidoColeta;

  private _numeroDeBolsas: number;

  private _volume: number;

  private _produtoColetadoDeAcordo: boolean;

  private _motivoProdutoColetadoIncorreto: string;

  private _identificacaoDoadorConfere: boolean;

  private _motivoIdentificacaoDoadorIncorreta: string;

  private _produdoAcondicionadoCorreto: boolean;

  private _motivoProdudoAcondicionadoIncorreto: string;

  private _produdoEventoAdverso: boolean;

  private _descrevaProdudoEventoAdverso: string;

  private _comentarioAdicional: string;

	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
    }

	public get dataDescarte(): Date {
		return this._dataDescarte;
	}

	public set dataDescarte(value: Date) {
		this._dataDescarte = value;
	}


	public get dataInfusao(): Date {
		return this._dataInfusao;
	}

	public set dataInfusao(value: Date) {
		this._dataInfusao = value;
	}

	public get dataPrevistaInfusao(): Date {
		return this._dataPrevistaInfusao;
	}

	public set dataPrevistaInfusao(value: Date) {
		this._dataPrevistaInfusao = value;
	}

	public get dataRecebimento(): Date {
		return this._dataRecebimento;
	}

	public set dataRecebimento(value: Date) {
		this._dataRecebimento = value;
	}

	public get recebeuColeta(): Boolean {
		return this._recebeuColeta;
	}

	public set recebeuColeta(value: Boolean) {
		this._recebeuColeta = value;
	}

	public get totalTotalCd34(): number {
		return this._totalTotalCd34;
	}

	public set totalTotalCd34(value: number) {
		this._totalTotalCd34 = value;
	}

	public get totalTotalTcn(): number {
		return this._totalTotalTcn;
	}

	public set totalTotalTcn(value: number) {
		this._totalTotalTcn = value;
	}

	public get justificativaCongelamento(): String {
		return this._justificativaCongelamento;
	}

	public set justificativaCongelamento(value: String) {
		this._justificativaCongelamento = value;
	}

	public get justificativaDescarte(): String {
		return this._justificativaDescarte;
	}

	public set justificativaDescarte(value: String) {
		this._justificativaDescarte = value;
	}

	public get fonteCelula(): FonteCelula {
		return this._fonteCelula;
	}

	public set fonteCelula(value: FonteCelula) {
		this._fonteCelula = value;
	}

	public get pedidoColeta(): PedidoColeta {
		return this._pedidoColeta;
	}

	public set pedidoColeta(value: PedidoColeta) {
		this._pedidoColeta = value;
	}
	public get destinoColeta(): DestinoColeta {
		return this._destinoColeta;
	}

	public set destinoColeta(value: DestinoColeta) {
		this._destinoColeta = value;
	}


  /**
   * Getter numeroDeBolsas
   * @return {number}
   */
	public get numeroDeBolsas(): number {
		return this._numeroDeBolsas;
	}

  /**
   * Getter volume
   * @return {number}
   */
	public get volume(): number {
		return this._volume;
	}

  /**
   * Setter numeroDeBolsas
   * @param {number} value
   */
	public set numeroDeBolsas(value: number) {
		this._numeroDeBolsas = value;
	}

  /**
   * Setter volume
   * @param {number} value
   */
	public set volume(value: number) {
		this._volume = value;
	}


    /**
     * Getter produtoColetadoDeAcordo
     * @return {boolean}
     */
	public get produtoColetadoDeAcordo(): boolean {
		return this._produtoColetadoDeAcordo;
	}

    /**
     * Getter motivoProdutoColetadoIncorreto
     * @return {string}
     */
	public get motivoProdutoColetadoIncorreto(): string {
		return this._motivoProdutoColetadoIncorreto;
	}

    /**
     * Setter produtoColetadoDeAcordo
     * @param {boolean} value
     */
	public set produtoColetadoDeAcordo(value: boolean) {
		this._produtoColetadoDeAcordo = value;
	}

    /**
     * Setter motivoProdutoColetadoIncorreto
     * @param {string} value
     */
	public set motivoProdutoColetadoIncorreto(value: string) {
		this._motivoProdutoColetadoIncorreto = value;
	}

    /**
     * Getter identificacaoDoadorConfere
     * @return {boolean}
     */
	public get identificacaoDoadorConfere(): boolean {
		return this._identificacaoDoadorConfere;
	}

    /**
     * Getter motivoIdentificacaoDoadorIncorreta
     * @return {string}
     */
	public get motivoIdentificacaoDoadorIncorreta(): string {
		return this._motivoIdentificacaoDoadorIncorreta;
	}

    /**
     * Setter identificacaoDoadorConfere
     * @param {boolean} value
     */
	public set identificacaoDoadorConfere(value: boolean) {
		this._identificacaoDoadorConfere = value;
	}

    /**
     * Setter motivoIdentificacaoDoadorIncorreta
     * @param {string} value
     */
	public set motivoIdentificacaoDoadorIncorreta(value: string) {
		this._motivoIdentificacaoDoadorIncorreta = value;
	}


    /**
     * Getter produdoAcondicionadoCorreto
     * @return {boolean}
     */
	public get produdoAcondicionadoCorreto(): boolean {
		return this._produdoAcondicionadoCorreto;
	}

    /**
     * Getter motivoProdudoAcondicionadoIncorreto
     * @return {string}
     */
	public get motivoProdudoAcondicionadoIncorreto(): string {
		return this._motivoProdudoAcondicionadoIncorreto;
	}

    /**
     * Setter produdoAcondicionadoCorreto
     * @param {boolean} value
     */
	public set produdoAcondicionadoCorreto(value: boolean) {
		this._produdoAcondicionadoCorreto = value;
	}

    /**
     * Setter motivoProdudoAcondicionadoIncorreto
     * @param {string} value
     */
	public set motivoProdudoAcondicionadoIncorreto(value: string) {
		this._motivoProdudoAcondicionadoIncorreto = value;
	}


    /**
     * Getter produdoEventoAdverso
     * @return {boolean}
     */
	public get produdoEventoAdverso(): boolean {
		return this._produdoEventoAdverso;
	}

    /**
     * Getter descrevaProdudoEventoAdverso
     * @return {string}
     */
	public get descrevaProdudoEventoAdverso(): string {
		return this._descrevaProdudoEventoAdverso;
	}

    /**
     * Setter produdoEventoAdverso
     * @param {boolean} value
     */
	public set produdoEventoAdverso(value: boolean) {
		this._produdoEventoAdverso = value;
	}

    /**
     * Setter descrevaProdudoEventoAdverso
     * @param {string} value
     */
	public set descrevaProdudoEventoAdverso(value: string) {
		this._descrevaProdudoEventoAdverso = value;
	}


    /**
     * Getter comentarioAdicional
     * @return {string}
     */
	public get comentarioAdicional(): string {
		return this._comentarioAdicional;
	}

    /**
     * Setter comentarioAdicional
     * @param {string} value
     */
	public set comentarioAdicional(value: string) {
		this._comentarioAdicional = value;
	}



	public jsonToEntity(res:any):this{

		return this;
	}
}
