import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";

export class ConsultaDoadorNacionalVo extends BaseEntidade {
 
    private _idDoador: number;
    private _idTentativa: number;
    private _idTarefa: number;
    private _idEnriquecimento: number;
    private _idStatusTarefa: number;
    private _dmr: number;
    private _nomeDoador: string;
    private _idStatusDoador: number;
    private _descricaoStatusDoador: string;
    private _idMotivoDoador: number;
    private _descricaoMotivoDoador: string;
    private _dataRetornoInatividade: Date;
    private _contactado: Boolean;
    private _tipoContato: number;
    private _idProcesso: number;
    private _passivo: Boolean;
    private _tipoFluxo: number;

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

    /**
     * Getter idTentativa
     * @return {number}
     */
	public get idTentativa(): number {
		return this._idTentativa;
	}

    /**
     * Setter idTentativa
     * @param {number} value
     */
	public set idTentativa(value: number) {
		this._idTentativa = value;
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
     * Getter dmr
     * @return {number}
     */
	public get dmr(): number {
		return this._dmr;
	}

    /**
     * Setter dmr
     * @param {number} value
     */
	public set dmr(value: number) {
		this._dmr = value;
	}

    /**
     * Getter nomeDoador
     * @return {string}
     */
	public get nomeDoador(): string {
		return this._nomeDoador;
	}

    /**
     * Setter nomeDoador
     * @param {string} value
     */
	public set nomeDoador(value: string) {
		this._nomeDoador = value;
	}

    /**
     * Getter idStatusDoador
     * @return {number}
     */
	public get idStatusDoador(): number {
		return this._idStatusDoador;
	}

    /**
     * Setter idStatusDoador
     * @param {number} value
     */
	public set idStatusDoador(value: number) {
		this._idStatusDoador = value;
	}

    /**
     * Getter descricaoStatusDoador
     * @return {string}
     */
	public get descricaoStatusDoador(): string {
		return this._descricaoStatusDoador;
	}

    /**
     * Setter descricaoStatusDoador
     * @param {string} value
     */
	public set descricaoStatusDoador(value: string) {
		this._descricaoStatusDoador = value;
	}

    /**
     * Getter idMotivoDoador
     * @return {number}
     */
	public get idMotivoDoador(): number {
		return this._idMotivoDoador;
	}

    /**
     * Setter idMotivoDoador
     * @param {number} value
     */
	public set idMotivoDoador(value: number) {
		this._idMotivoDoador = value;
	}

    /**
     * Getter descricaoMotivoDoador
     * @return {string}
     */
	public get descricaoMotivoDoador(): string {
		return this._descricaoMotivoDoador;
	}

    /**
     * Setter descricaoMotivoDoador
     * @param {string} value
     */
	public set descricaoMotivoDoador(value: string) {
		this._descricaoMotivoDoador = value;
	}

    /**
     * Getter dataRetornoInatividade
     * @return {Date}
     */
	public get dataRetornoInatividade(): Date {
		return this._dataRetornoInatividade;
	}

    /**
     * Setter dataRetornoInatividade
     * @param {Date} value
     */
	public set dataRetornoInatividade(value: Date) {
		this._dataRetornoInatividade = value;
	}

    /**
     * Getter idStatusTarefa
     * @return {number}
     */
	public get idStatusTarefa(): number {
		return this._idStatusTarefa;
	}

    /**
     * Setter idStatusTarefa
     * @param {number} value
     */
	public set idStatusTarefa(value: number) {
		this._idStatusTarefa = value;
	}

    /**
     * Getter contactado
     * @return {Boolean}
     */
	public get contactado(): Boolean {
		return this._contactado;
	}

    /**
     * Setter contactado
     * @param {Boolean} value
     */
	public set contactado(value: Boolean) {
		this._contactado = value;
	}

    /**
     * Getter tipoContato
     * @return {number}
     */
	public get tipoContato(): number {
		return this._tipoContato;
	}

    /**
     * Setter tipoContato
     * @param {number} value
     */
	public set tipoContato(value: number) {
		this._tipoContato = value;
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
     * Getter passivo
     * @return {Boolean}
     */
	public get passivo(): Boolean {
		return this._passivo;
	}

    /**
     * Setter passivo
     * @param {Boolean} value
     */
	public set passivo(value: Boolean) {
		this._passivo = value;
	}


    /**
     * Getter tipoFluxo
     * @return {number}
     */
	public get tipoFluxo(): number {
		return this._tipoFluxo;
	}

    /**
     * Setter tipoFluxo
     * @param {number} value
     */
	public set tipoFluxo(value: number) {
		this._tipoFluxo = value;
	}

    /**
     * Getter idEnriquecimento
     * @return {number}
     */
	public get idEnriquecimento(): number {
		return this._idEnriquecimento;
	}

    /**
     * Setter idEnriquecimento
     * @param {number} value
     */
	public set idEnriquecimento(value: number) {
		this._idEnriquecimento = value;
	}

    public jsonToEntity(res: any): this {

        this.idDoador = ConvertUtil.parseJsonParaAtributos(res.idDoador, new Number());
        this.idTentativa = ConvertUtil.parseJsonParaAtributos(res.idTentativa, new Number());
        this.idTarefa = ConvertUtil.parseJsonParaAtributos(res.idTarefa, new Number());
        this.idEnriquecimento = ConvertUtil.parseJsonParaAtributos(res.idEnriquecimento, new Number());
        this.idStatusTarefa = ConvertUtil.parseJsonParaAtributos(res.idStatusTarefa, new Number());
        this.idStatusDoador = ConvertUtil.parseJsonParaAtributos(res.idStatusDoador, new Number());
        this.idMotivoDoador = ConvertUtil.parseJsonParaAtributos(res.idMotivoDoador, new Number());
        this.idProcesso = ConvertUtil.parseJsonParaAtributos(res.idProcesso, new Number());
        this.dmr = ConvertUtil.parseJsonParaAtributos(res.dmr, new Number());
        this.nomeDoador = ConvertUtil.parseJsonParaAtributos(res.nomeDoador, new String());
        this.descricaoStatusDoador = ConvertUtil.parseJsonParaAtributos(res.descricaoStatusDoador, new String());
        this.descricaoMotivoDoador = ConvertUtil.parseJsonParaAtributos(res.descricaoMotivoDoador, new String());
        this.dataRetornoInatividade = ConvertUtil.parseJsonParaAtributos(res.dataRetornoInatividade, new Date());
        this.contactado = ConvertUtil.parseJsonParaAtributos(res.contactado, new Boolean());
        this.tipoContato = ConvertUtil.parseJsonParaAtributos(res.tipoContato, new Number());
        this.passivo = ConvertUtil.parseJsonParaAtributos(res.passivo, new Boolean());
        this.tipoFluxo = ConvertUtil.parseJsonParaAtributos(res.tipoFluxo, new Number());

		return this;
	}
}