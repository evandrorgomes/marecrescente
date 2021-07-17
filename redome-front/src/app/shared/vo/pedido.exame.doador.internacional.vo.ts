import { BaseEntidade } from '../base.entidade';
import { ConvertUtil } from '../util/convert.util';

export class PedidoExameDoadorInternacionalVo extends BaseEntidade {
 
    private _idBusca: number;
    private _idSolicitacao: number;
    private _idStatusSolicitacao: number;
    private _idTipoSolicitacao: number;

    private _idDoador: number;
    private _veioDoEmdis: number;
    private _idOrigem: string;
    private _tipoDoador: number;

    private _idPedidoExame: number;
    private _idExame: number;
    private _origem: string;

    private _exame: string;

    private _idTarefa: number;
    private _idProcesso: number;
    private _tipoTarefa: number;

    private _dataCriacao: Date;

    private _grid: string;

    /**
     * Getter idBusca
     * @return {number}
     */
    public get idBusca(): number {
        return this._idBusca;
    }

    /**
     * Getter idSolicitacao
     * @return {number}
     */
    public get idSolicitacao(): number {
        return this._idSolicitacao;
    }

    /**
     * Getter idStatusSolicitacao
     * @return {number}
     */
    public get idStatusSolicitacao(): number {
        return this._idStatusSolicitacao;
    }

    /**
     * Getter idDoador
     * @return {number}
     */
    public get idDoador(): number {
        return this._idDoador;
    }

    /**
     * Getter VeioDoEmdis
     * @return {number}
     */
    public get veioDoEmdis(): number {
        return this._veioDoEmdis;
    }

    /**
     * Getter idOrigem
     * @return {string}
     */
    public get idOrigem(): string {
        return this._idOrigem;
    }

    /**
     * Getter tipoDoador
     * @return {number}
     */
    public get tipoDoador(): number {
        return this._tipoDoador;
    }

    /**
     * Getter idPedidoExame
     * @return {number}
     */
    public get idPedidoExame(): number {
        return this._idPedidoExame;
    }

    /**
     * Getter idExame
     * @return {number}
     */
    public get idExame(): number {
        return this._idExame;
    }

    /**
     * Getter Origem
     * @return {string}
     */
    public get origem(): string {
        return this._origem;
    }

    /**
     * Getter exame
     * @return {string}
     */
    public get exame(): string {
        return this._exame;
    }

    /**
     * Getter idTarefa
     * @return {number}
     */
    public get idTarefa(): number {
        return this._idTarefa;
    }

    /**
     * Getter idProcesso
     * @return {number}
     */
    public get idProcesso(): number {
        return this._idProcesso;
    }

    /**
     * Getter tipoTarefa
     * @return {number}
     */
    public get tipoTarefa(): number {
        return this._tipoTarefa;
    }

    /**
     * Getter dataCriacao
     * @return {Date}
     */
    public get dataCriacao(): Date {
        return this._dataCriacao;
    }

    /**
     * Setter idBusca
     * @param {number} value
     */
    public set idBusca(value: number) {
        this._idBusca = value;
    }

    /**
     * Setter idSolicitacao
     * @param {number} value
     */
    public set idSolicitacao(value: number) {
        this._idSolicitacao = value;
    }

    /**
     * Setter idStatusSolicitacao
     * @param {number} value
     */
    public set idStatusSolicitacao(value: number) {
        this._idStatusSolicitacao = value;
    }

    /**
     * Setter idDoador
     * @param {number} value
     */
    public set idDoador(value: number) {
        this._idDoador = value;
    }

    /**
     * Setter VeioDoEmdis
     * @param {number} value
     */
    public set veioDoEmdis(value: number) {
        this._veioDoEmdis = value;
    }

    /**
     * Setter idOrigem
     * @param {string} value
     */
    public set idOrigem(value: string) {
        this._idOrigem = value;
    }

    /**
     * Setter tipoDoador
     * @param {number} value
     */
    public set tipoDoador(value: number) {
        this._tipoDoador = value;
    }

    /**
     * Setter idPedidoExame
     * @param {number} value
     */
    public set idPedidoExame(value: number) {
        this._idPedidoExame = value;
    }

    /**
     * Setter idExame
     * @param {number} value
     */
    public set idExame(value: number) {
        this._idExame = value;
    }

    /**
     * Setter Origem
     * @param {string} value
     */
    public set origem(value: string) {
        this._origem = value;
    }

    /**
     * Setter exame
     * @param {string} value
     */
    public set exame(value: string) {
        this._exame = value;
    }

    /**
     * Setter idTarefa
     * @param {number} value
     */
    public set idTarefa(value: number) {
        this._idTarefa = value;
    }

    /**
     * Setter idProcesso
     * @param {number} value
     */
    public set idProcesso(value: number) {
        this._idProcesso = value;
    }

    /**
     * Setter tipoTarefa
     * @param {number} value
     */
    public set tipoTarefa(value: number) {
        this._tipoTarefa = value;
    }

    /**
     * Setter dataCriacao
     * @param {Date} value
     */
    public set dataCriacao(value: Date) {
        this._dataCriacao = value;
    }

    /**
     * Getter idTipoSolicitacao
     * @return {number}
     */
    public get idTipoSolicitacao(): number {
        return this._idTipoSolicitacao;
    }

    /**
     * Setter idTipoSolicitacao
     * @param {number} value
     */
    public set idTipoSolicitacao(value: number) {
        this._idTipoSolicitacao = value;
    }

    /**
     * Getter grid
     * @return {string}
     */
	public get grid(): string {
		return this._grid;
	}

    /**
     * Setter grid
     * @param {string} value
     */
	public set grid(value: string) {
		this._grid = value;
	}


    public jsonToEntity(res: any): this {

        this.idBusca = ConvertUtil.parseJsonParaAtributos(res.idBusca, new Number());
        this.idSolicitacao = ConvertUtil.parseJsonParaAtributos(res.idSolicitacao, new Number());
        this.idStatusSolicitacao = ConvertUtil.parseJsonParaAtributos(res.idStatusSolicitacao, new Number());
        this.idTipoSolicitacao = ConvertUtil.parseJsonParaAtributos(res.idTipoSolicitacao, new Number());

        this.idDoador = ConvertUtil.parseJsonParaAtributos(res.idDoador, new Number());
        this.veioDoEmdis = ConvertUtil.parseJsonParaAtributos(res.veioDoEmdis, new Number());
        this.idOrigem = ConvertUtil.parseJsonParaAtributos(res.idOrigem, new String());
        this.tipoDoador = ConvertUtil.parseJsonParaAtributos(res.tipoDoador, new Number());

        this.idPedidoExame = ConvertUtil.parseJsonParaAtributos(res.idPedidoExame, new Number());
        this.idExame = ConvertUtil.parseJsonParaAtributos(res.idExame, new Number());
        this.origem = ConvertUtil.parseJsonParaAtributos(res.origem, new String());

        this.exame = ConvertUtil.parseJsonParaAtributos(res.exame, new String());

        this.idTarefa = ConvertUtil.parseJsonParaAtributos(res.idTarefa, new Number());
        this.idProcesso = ConvertUtil.parseJsonParaAtributos(res.idProcesso, new Number());
        this.tipoTarefa = ConvertUtil.parseJsonParaAtributos(res.tipoTarefa, new Number());

        this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
        this.grid = ConvertUtil.parseJsonParaAtributos(res.grid, new String());

        return this;
    }
}