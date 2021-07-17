import {Registro} from "../dominio/registro";
import {StatusDoadorDTO} from "./status.doador.internacional.dto";
import {LocusExame} from "../../paciente/cadastro/exame/locusexame";
import {ValorGenotipoDTO} from "./valor.genotipo.dto";
import {BaseEntidade} from "../base.entidade";

export class DoadorCordaoInternacionalDto extends BaseEntidade {

   private _id:number;

	private _grid:string;

	private _idade:number;

	private _peso:number;

	private _sexo:string;

	private _abo:string;

	private _dataNascimento:Date;

	private _statusDoador:StatusDoadorDTO;

	private _dataRetornoInatividade:Date;

	private _idRegistro:String;

	private _registroOrigem:Registro;

	private _registroPagamento:Registro;

	private _cadastradoEmdis:Boolean;

	private _quantidadeTotalTCN:number;

	private _quantidadeTotalCD34:number;

	private _volume:number;

	private _rmrAssociado:number;

	private _tipoDoador: number;

	private _locusExames: LocusExame[] = [];

	private _valoresGenotipo: ValorGenotipoDTO[];

	private _ressalvas: string[];

	constructor(tipoDoador: number) {
		super();
		this._tipoDoador = tipoDoador;
	}

    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Getter grid
     * @return {string}
     */
	public get grid(): string {
		return this._grid;
	}

    /**
     * Getter idade
     * @return {number}
     */
	public get idade(): number {
		return this._idade;
	}

    /**
     * Getter peso
     * @return {number}
     */
	public get peso(): number {
		return this._peso;
	}

    /**
     * Getter sexo
     * @return {string}
     */
	public get sexo(): string {
		return this._sexo;
	}

    /**
     * Getter abo
     * @return {string}
     */
	public get abo(): string {
		return this._abo;
	}

    /**
     * Getter dataNascimento
     * @return {Date}
     */
	public get dataNascimento(): Date {
		return this._dataNascimento;
	}

    /**
     * Getter statusDoador
     * @return {StatusDoadorDTO}
     */
	public get statusDoador(): StatusDoadorDTO {
		return this._statusDoador;
	}

    /**
     * Getter dataRetornoInatividade
     * @return {Date}
     */
	public get dataRetornoInatividade(): Date {
		return this._dataRetornoInatividade;
	}

    /**
     * Getter idRegistro
     * @return {String}
     */
	public get idRegistro(): String {
		return this._idRegistro;
	}

    /**
     * Getter registroOrigem
     * @return {Registro}
     */
	public get registroOrigem(): Registro {
		return this._registroOrigem;
	}

    /**
     * Getter registroPagamento
     * @return {Registro}
     */
	public get registroPagamento(): Registro {
		return this._registroPagamento;
	}

    /**
     * Getter cadastradoEmdis
     * @return {Boolean}
     */
	public get cadastradoEmdis(): Boolean {
		return this._cadastradoEmdis;
	}

    /**
     * Getter quantidadeTotalTCN
     * @return {number}
     */
	public get quantidadeTotalTCN(): number {
		return this._quantidadeTotalTCN;
	}

    /**
     * Getter quantidadeTotalCD34
     * @return {number}
     */
	public get quantidadeTotalCD34(): number {
		return this._quantidadeTotalCD34;
	}

    /**
     * Getter volume
     * @return {number}
     */
	public get volume(): number {
		return this._volume;
	}

    /**
     * Getter rmrAssociado
     * @return {number}
     */
	public get rmrAssociado(): number {
		return this._rmrAssociado;
	}

    /**
     * Getter tipoDoador
     * @return {String}
     */
	public get tipoDoador(): number {
		return this._tipoDoador;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Setter grid
     * @param {string} value
     */
	public set grid(value: string) {
		this._grid = value;
	}

    /**
     * Setter idade
     * @param {number} value
     */
	public set idade(value: number) {
		this._idade = value;
	}

    /**
     * Setter peso
     * @param {number} value
     */
	public set peso(value: number) {
		this._peso = value;
	}

    /**
     * Setter sexo
     * @param {string} value
     */
	public set sexo(value: string) {
		this._sexo = value;
	}

    /**
     * Setter abo
     * @param {string} value
     */
	public set abo(value: string) {
		this._abo = value;
	}

    /**
     * Setter dataNascimento
     * @param {Date} value
     */
	public set dataNascimento(value: Date) {
		this._dataNascimento = value;
	}

    /**
     * Setter statusDoador
     * @param {StatusDoadorDTO} value
     */
	public set statusDoador(value: StatusDoadorDTO) {
		this._statusDoador = value;
	}

    /**
     * Setter dataRetornoInatividade
     * @param {Date} value
     */
	public set dataRetornoInatividade(value: Date) {
		this._dataRetornoInatividade = value;
	}

    /**
     * Setter idRegistro
     * @param {String} value
     */
	public set idRegistro(value: String) {
		this._idRegistro = value;
	}

    /**
     * Setter registroOrigem
     * @param {Registro} value
     */
	public set registroOrigem(value: Registro) {
		this._registroOrigem = value;
	}

    /**
     * Setter registroPagamento
     * @param {Registro} value
     */
	public set registroPagamento(value: Registro) {
		this._registroPagamento = value;
	}

    /**
     * Setter cadastradoEmdis
     * @param {Boolean} value
     */
	public set cadastradoEmdis(value: Boolean) {
		this._cadastradoEmdis = value;
	}

    /**
     * Setter quantidadeTotalTCN
     * @param {number} value
     */
	public set quantidadeTotalTCN(value: number) {
		this._quantidadeTotalTCN = value;
	}

    /**
     * Setter quantidadeTotalCD34
     * @param {number} value
     */
	public set quantidadeTotalCD34(value: number) {
		this._quantidadeTotalCD34 = value;
	}

    /**
     * Setter volume
     * @param {number} value
     */
	public set volume(value: number) {
		this._volume = value;
	}

    /**
     * Setter rmrAssociado
     * @param {number} value
     */
	public set rmrAssociado(value: number) {
		this._rmrAssociado = value;
	}

	get locusExames(): LocusExame[] {
		return this._locusExames;
	}

	set locusExames(value: LocusExame[]) {
		this._locusExames = value;
	}

	get valoresGenotipo(): ValorGenotipoDTO[] {
		return this._valoresGenotipo;
	}

	set valoresGenotipo(value: ValorGenotipoDTO[]) {
		this._valoresGenotipo = value;
	}

	get ressalvas(): string[] {
		return this._ressalvas;
	}

	set ressalvas(value: string[]) {
		this._ressalvas = value;
	}

	jsonToEntity(res: any): DoadorCordaoInternacionalDto {
		return null;
	}

}
