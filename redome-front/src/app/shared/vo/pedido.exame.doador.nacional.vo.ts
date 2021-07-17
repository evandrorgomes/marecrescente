import { BaseEntidade } from '../base.entidade';
import { ConvertUtil } from '../util/convert.util';

export class PedidoExameDoadorNacionalVo extends BaseEntidade {
 
    private _idTarefa: number;
    private _tipoTarefa: number;
    private _idBusca: number;
    private _rmr: number;
    private _nomePaciente: string;
    private _idSolicitacao: number;
    private _tipoSolicitacaoDescricao: string;
    private _statusSolicitacaoDescricao: string;
    private _idDoador: number;
    private _tipoDoador: number;
    private _dmr: number;
    private _nomeDoador: string;
    private _nomeLaboratorio: string;
    private _idPedidoExame: number;
    private _dataCriacao: Date;
    private _tipoExameDescricao: string;
    private _statusDescricao: string;
    private _idPedidoEnriquecimento: number;
    private _idPedidoContato: number;
    private _idPedidoContatoSms: number;

    /**
     * Getter idTarefa
     * @return {number}
     */
    public get idTarefa(): number {
        return this._idTarefa;
    }

    /**
     * Getter tipoTarefa
     * @return {number}
     */
    public get tipoTarefa(): number {
        return this._tipoTarefa;
    }

    /**
     * Getter idBusca
     * @return {number}
     */
    public get idBusca(): number {
        return this._idBusca;
    }

    /**
     * Getter rmr
     * @return {number}
     */
    public get rmr(): number {
        return this._rmr;
    }

    /**
     * Getter nomePaciente
     * @return {string}
     */
    public get nomePaciente(): string {
        return this._nomePaciente;
    }

    /**
     * Getter idSolicitacao
     * @return {number}
     */
    public get idSolicitacao(): number {
        return this._idSolicitacao;
    }

    /**
     * Getter idDoador
     * @return {number}
     */
    public get idDoador(): number {
        return this._idDoador;
    }

    /**
     * Getter tipoDoador
     * @return {number}
     */
    public get tipoDoador(): number {
        return this._tipoDoador;
    }

    /**
     * Getter dmr
     * @return {number}
     */
    public get dmr(): number {
        return this._dmr;
    }

    /**
     * Getter nomeDoador
     * @return {string}
     */
    public get nomeDoador(): string {
        return this._nomeDoador;
    }

    /**
     * Getter nomeLaboratorio
     * @return {string}
     */
    public get nomeLaboratorio(): string {
        return this._nomeLaboratorio;
    }

    /**
     * Getter idPedidoExame
     * @return {number}
     */
    public get idPedidoExame(): number {
        return this._idPedidoExame;
    }

    /**
     * Getter tipoExameDescricao
     * @return {string}
     */
    public get tipoExameDescricao(): string {
        return this._tipoExameDescricao;
    }

    /**
     * Getter statusDescricao
     * @return {string}
     */
    public get statusDescricao(): string {
        return this._statusDescricao;
    }

    /**
     * Getter idPedidoEnriquecimento
     * @return {number}
     */
    public get idPedidoEnriquecimento(): number {
        return this._idPedidoEnriquecimento;
    }

    /**
     * Getter idPedidoContato
     * @return {number}
     */
    public get idPedidoContato(): number {
        return this._idPedidoContato;
    }

    /**
     * Setter idTarefa
     * @param {number} value
     */
    public set idTarefa(value: number) {
        this._idTarefa = value;
    }

    /**
     * Setter tipoTarefa
     * @param {number} value
     */
    public set tipoTarefa(value: number) {
        this._tipoTarefa = value;
    }

    /**
     * Setter idBusca
     * @param {number} value
     */
    public set idBusca(value: number) {
        this._idBusca = value;
    }

    /**
     * Setter rmr
     * @param {number} value
     */
    public set rmr(value: number) {
        this._rmr = value;
    }

    /**
     * Setter nomePaciente
     * @param {string} value
     */
    public set nomePaciente(value: string) {
        this._nomePaciente = value;
    }

    /**
     * Setter idSolicitacao
     * @param {number} value
     */
    public set idSolicitacao(value: number) {
        this._idSolicitacao = value;
    }

    /**
     * Setter idDoador
     * @param {number} value
     */
    public set idDoador(value: number) {
        this._idDoador = value;
    }

    /**
     * Setter tipoDoador
     * @param {number} value
     */
    public set tipoDoador(value: number) {
        this._tipoDoador = value;
    }

    /**
     * Setter dmr
     * @param {number} value
     */
    public set dmr(value: number) {
        this._dmr = value;
    }

    /**
     * Setter nomeDoador
     * @param {string} value
     */
    public set nomeDoador(value: string) {
        this._nomeDoador = value;
    }

    /**
     * Setter nomeLaboratorio
     * @param {string} value
     */
    public set nomeLaboratorio(value: string) {
        this._nomeLaboratorio = value;
    }

    /**
     * Setter idPedidoExame
     * @param {number} value
     */
    public set idPedidoExame(value: number) {
        this._idPedidoExame = value;
    }

    /**
     * Setter tipoExameDescricao
     * @param {string} value
     */
    public set tipoExameDescricao(value: string) {
        this._tipoExameDescricao = value;
    }

    /**
     * Setter statusDescricao
     * @param {string} value
     */
    public set statusDescricao(value: string) {
        this._statusDescricao = value;
    }

    /**
     * Setter idPedidoEnriquecimento
     * @param {number} value
     */
    public set idPedidoEnriquecimento(value: number) {
        this._idPedidoEnriquecimento = value;
    }

    /**
     * Setter idPedidoContato
     * @param {number} value
     */
    public set idPedidoContato(value: number) {
        this._idPedidoContato = value;
    }

    /**
     * Getter tipoSolicitacaoDescricao
     * @return {string}
     */
    public get tipoSolicitacaoDescricao(): string {
        return this._tipoSolicitacaoDescricao;
    }

    /**
     * Setter tipoSolicitacaoDescricao
     * @param {string} value
     */
    public set tipoSolicitacaoDescricao(value: string) {
        this._tipoSolicitacaoDescricao = value;
    }

    /**
     * Getter statusSolicitacaoDescricao
     * @return {string}
     */
    public get statusSolicitacaoDescricao(): string {
        return this._statusSolicitacaoDescricao;
    }

    /**
     * Setter statusSolicitacaoDescricao
     * @param {string} value
     */
    public set statusSolicitacaoDescricao(value: string) {
        this._statusSolicitacaoDescricao = value;
    }

    /**
     * Getter dataCriacao
     * @return {Date}
     */
    public get dataCriacao(): Date {
        return this._dataCriacao;
    }

    /**
     * Setter dataCriacao
     * @param {Date} value
     */
    public set dataCriacao(value: Date) {
        this._dataCriacao = value;
    }

    /**
     * Getter idPedidoContatoSms
     * @return {number}
     */
    public get idPedidoContatoSms(): number {
        return this._idPedidoContatoSms;
    }

    /**
     * Setter idPedidoContatoSms
     * @param {number} value
     */
    public set idPedidoContatoSms(value: number) {
        this._idPedidoContatoSms = value;
    }


    public jsonToEntity(res: any): this {

        this.idTarefa = ConvertUtil.parseJsonParaAtributos(res.idTarefa, new Number());
        this.tipoTarefa = ConvertUtil.parseJsonParaAtributos(res.tipoTarefa, new Number());
        this.idBusca = ConvertUtil.parseJsonParaAtributos(res.idBusca, new Number());
        this.rmr = ConvertUtil.parseJsonParaAtributos(res.rmr, new Number());
        this.nomePaciente = ConvertUtil.parseJsonParaAtributos(res.nomePaciente, new String());
        this.idSolicitacao = ConvertUtil.parseJsonParaAtributos(res.idSolicitacao, new Number());
        this.tipoSolicitacaoDescricao = ConvertUtil.parseJsonParaAtributos(res.tipoSolicitacaoDescricao, new String());
        this.statusSolicitacaoDescricao = ConvertUtil.parseJsonParaAtributos(res.statusSolicitacaoDescricao, new String());
        this.idDoador = ConvertUtil.parseJsonParaAtributos(res.idDoador, new Number());
        this.tipoDoador = ConvertUtil.parseJsonParaAtributos(res.tipoDoador, new Number());
        this.dmr = ConvertUtil.parseJsonParaAtributos(res.dmr, new Number());
        this.nomeDoador = ConvertUtil.parseJsonParaAtributos(res.nomeDoador, new String());
        this.nomeLaboratorio = ConvertUtil.parseJsonParaAtributos(res.nomeLaboratorio, new String());
        this.idPedidoExame = ConvertUtil.parseJsonParaAtributos(res.idPedidoExame, new Number());
        this.tipoExameDescricao = ConvertUtil.parseJsonParaAtributos(res.tipoExameDescricao, new String());
        this.statusDescricao = ConvertUtil.parseJsonParaAtributos(res.statusDescricao, new String());
        this.idPedidoEnriquecimento = ConvertUtil.parseJsonParaAtributos(res.idPedidoEnriquecimento, new Number());
        this.idPedidoContato = ConvertUtil.parseJsonParaAtributos(res.idPedidoContato, new Number());
        this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
        this.idPedidoContatoSms = ConvertUtil.parseJsonParaAtributos(res.idPedidoContatoSms, new Number());

        return this;
    }
}