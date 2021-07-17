import { BaseEntidade } from "../base.entidade";
import { ConvertUtil } from "../util/convert.util";
import { ArquivoPedidoWorkupDTO } from "./arquivo.pedido.workup.dto";

export class AprovarPlanoWorkupDTO extends BaseEntidade {
	private _id: number;
	private _idCentroColeta: number;
    private _idTipo: number;

	private _dataExame: Date;
	private _dataResultado: Date;
	private _dataInternacao: Date;
	private _dataColeta: Date;
	private _observacaoPlanoWorkup: string;
	private _identificacao: string;
	private _rmr: number;

	private _dataCondicionamento: Date;
	private _dataInfusao: Date;
	private _criopreservacao: boolean;
	private _observacaoAprovaPlanoWorkup: string;

    private _arquivosPedidoWorkup: ArquivoPedidoWorkupDTO[] = [];

    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Getter idCentroColeta
     * @return {number}
     */
	public get idCentroColeta(): number {
		return this._idCentroColeta;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Setter idCentroColeta
     * @param {number} value
     */
	public set idCentroColeta(value: number) {
		this._idCentroColeta = value;
	}


    /**
     * Getter dataExame
     * @return {Date}
     */
	public get dataExame(): Date {
		return this._dataExame;
	}

    /**
     * Getter dataResultado
     * @return {Date}
     */
	public get dataResultado(): Date {
		return this._dataResultado;
	}

    /**
     * Getter dataInternacao
     * @return {Date}
     */
	public get dataInternacao(): Date {
		return this._dataInternacao;
	}

    /**
     * Getter dataColeta
     * @return {Date}
     */
	public get dataColeta(): Date {
		return this._dataColeta;
	}

    /**
     * Getter observacaoPlanoWorkup
     * @return {string}
     */
	public get observacaoPlanoWorkup(): string {
		return this._observacaoPlanoWorkup;
	}

    /**
     * Setter dataExame
     * @param {Date} value
     */
	public set dataExame(value: Date) {
		this._dataExame = value;
	}

    /**
     * Setter dataResultado
     * @param {Date} value
     */
	public set dataResultado(value: Date) {
		this._dataResultado = value;
	}

    /**
     * Setter dataInternacao
     * @param {Date} value
     */
	public set dataInternacao(value: Date) {
		this._dataInternacao = value;
	}

    /**
     * Setter dataColeta
     * @param {Date} value
     */
	public set dataColeta(value: Date) {
		this._dataColeta = value;
	}

    /**
     * Setter observacaoPlanoWorkup
     * @param {string} value
     */
	public set observacaoPlanoWorkup(value: string) {
		this._observacaoPlanoWorkup = value;
	}

    /**
     * Getter identificacao
     * @return {string}
     */
	public get identificacao(): string {
		return this._identificacao;
	}

    /**
     * Setter identificacao
     * @param {string} value
     */
	public set identificacao(value: string) {
		this._identificacao = value;
	}

    /**
     * Getter rmr
     * @return {number}
     */
	public get rmr(): number {
		return this._rmr;
	}

    /**
     * Setter rmr
     * @param {number} value
     */
	public set rmr(value: number) {
		this._rmr = value;
	}


    /**
     * Getter dataCondicionamento
     * @return {Date}
     */
	public get dataCondicionamento(): Date {
		return this._dataCondicionamento;
	}

    /**
     * Getter dataInfusao
     * @return {Date}
     */
	public get dataInfusao(): Date {
		return this._dataInfusao;
	}

    /**
     * Getter criopreservacao
     * @return {boolean}
     */
	public get criopreservacao(): boolean {
		return this._criopreservacao;
	}

    /**
     * Getter observacaoAprovaPlanoWorkup
     * @return {string}
     */
	public get observacaoAprovaPlanoWorkup(): string {
		return this._observacaoAprovaPlanoWorkup;
	}

    /**
     * Setter dataCondicionamento
     * @param {Date} value
     */
	public set dataCondicionamento(value: Date) {
		this._dataCondicionamento = value;
	}

    /**
     * Setter dataInfusao
     * @param {Date} value
     */
	public set dataInfusao(value: Date) {
		this._dataInfusao = value;
	}

    /**
     * Setter criopreservacao
     * @param {boolean} value
     */
	public set criopreservacao(value: boolean) {
		this._criopreservacao = value;
	}

    /**
     * Setter observacaoAprovaPlanoWorkup
     * @param {string} value
     */
	public set observacaoAprovaPlanoWorkup(value: string) {
		this._observacaoAprovaPlanoWorkup = value;
	}


    /**
     * Getter idTipoWorkup
     * @return {number}
     */
	public get idTipo(): number {
		return this._idTipo;
	}

    /**
     * Setter idTipo
     * @param {number} value
     */
	public set idTipo(value: number) {
		this._idTipo = value;
	}


    /**
     * Getter arquivosPedidoWorkup
     * @return {ArquivoPedidoWorkupDTO[] }
     */
	public get arquivosPedidoWorkup(): ArquivoPedidoWorkupDTO[]  {
		return this._arquivosPedidoWorkup;
	}

    /**
     * Setter arquivosPedidoWorkup
     * @param {ArquivoPedidoWorkupDTO[] } value
     */
	public set arquivosPedidoWorkup(value: ArquivoPedidoWorkupDTO[] ) {
		this._arquivosPedidoWorkup = value;
	}


	public jsonToEntity(res: any): AprovarPlanoWorkupDTO {

        if(res.arquivosPedidoWorkup){
            res.arquivosPedidoWorkup.forEach(arquivo =>{
                this.arquivosPedidoWorkup.push(new ArquivoPedidoWorkupDTO().jsonToEntity(arquivo));
            })
        }

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.idCentroColeta = ConvertUtil.parseJsonParaAtributos(res.idCentroColeta, new Number());
		this.dataExame = ConvertUtil.parseJsonParaAtributos(res.dataExame, new Date());
		this.dataResultado = ConvertUtil.parseJsonParaAtributos(res.dataResultado, new Date());
		this.dataInternacao = ConvertUtil.parseJsonParaAtributos(res.dataInternacao, new Date());
		this.dataColeta = ConvertUtil.parseJsonParaAtributos(res.dataColeta, new Date());
		this.observacaoPlanoWorkup = ConvertUtil.parseJsonParaAtributos(res.observacaoPlanoWorkup, new String());
		this.identificacao = ConvertUtil.parseJsonParaAtributos(res.identificacao, new String());
		this.rmr = ConvertUtil.parseJsonParaAtributos(res.rmr, new Number());

		this.dataCondicionamento = ConvertUtil.parseJsonParaAtributos(res.dataCondicionamento, new Date());
		this.dataInfusao = ConvertUtil.parseJsonParaAtributos(res.dataInfusao, new Date());
		this.criopreservacao = ConvertUtil.parseJsonParaAtributos(res.criopreservacao, new Boolean());
		this.observacaoAprovaPlanoWorkup = ConvertUtil.parseJsonParaAtributos(res.observacaoAprovaPlanoWorkup, new String());

        this.idTipo = ConvertUtil.parseJsonParaAtributos(res.idTipo, new Number());

		return this;
	}
}
