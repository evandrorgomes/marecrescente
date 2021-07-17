import { BaseEntidade } from '../base.entidade';
import { EnderecoContatoDoador } from "../../doador/endereco.contato.doador";
import { ContatoTelefonicoDoador } from "../../doador/contato.telefonico.doador";
import { EmailContatoDoador } from "../../doador/email.contato.doador";
import { ArquivoVoucherLogistica } from "../model/arquivo.voucher.logistica";
import { CentroTransplante } from "../dominio/centro.transplante";
import { TransporteTerrestre } from '../model/transporte.terrestre';

/**
 * Informação dos detalhes da logística necessária a determinado pedido de logística.
 */
export class LogisticaDTO extends BaseEntidade {
	public jsonToEntity(res: any) {
		throw new Error("Method not implemented.");
	}
    private _idDoador: number;
	private _pedidoLogisticaId: number;
	private _tarefaId: number;

	private _dataInicio: Date;
	private _dataFinal: Date;

	private _enderecos: EnderecoContatoDoador[];
	private _telefones: ContatoTelefonicoDoador[];
	private _emails: EmailContatoDoador[];

	private _transporteTerrestre: TransporteTerrestre[];
	private _aereos: ArquivoVoucherLogistica[];
	private _hospedagens: ArquivoVoucherLogistica[];

	private _centroColeta: CentroTransplante;

	private _observacao: string;

	private _rmr:number;

	private _nomeDoador:string;

	private _dmr:number;


	public get idDoador(): number {
		return this._idDoador;
	}

	public set idDoador(value: number) {
		this._idDoador = value;
	}

	public get pedidoLogisticaId(): number {
		return this._pedidoLogisticaId;
	}

	public set pedidoLogisticaId(value: number) {
		this._pedidoLogisticaId = value;
	}

	public get dataInicio(): Date {
		return this._dataInicio;
	}

	public set dataInicio(value: Date) {
		this._dataInicio = value;
	}

	public get dataFinal(): Date {
		return this._dataFinal;
	}

	public set dataFinal(value: Date) {
		this._dataFinal = value;
	}

	public get enderecos(): EnderecoContatoDoador[] {
		return this._enderecos;
	}

	public set enderecos(value: EnderecoContatoDoador[]) {
		this._enderecos = value;
	}

	public get telefones(): ContatoTelefonicoDoador[] {
		return this._telefones;
	}

	public set telefones(value: ContatoTelefonicoDoador[]) {
		this._telefones = value;
	}

	public get emails(): EmailContatoDoador[] {
		return this._emails;
	}

	public set emails(value: EmailContatoDoador[]) {
		this._emails = value;
	}

	public get transporteTerrestre(): TransporteTerrestre[] {
		return this._transporteTerrestre;
	}

	public set transporteTerrestre(value: TransporteTerrestre[]) {
		this._transporteTerrestre = value;
	}

	public get aereos(): ArquivoVoucherLogistica[] {
		return this._aereos;
	}

	public set aereos(value: ArquivoVoucherLogistica[]) {
		this._aereos = value;
	}

	public get hospedagens(): ArquivoVoucherLogistica[] {
		return this._hospedagens;
	}

	public set hospedagens(value: ArquivoVoucherLogistica[]) {
		this._hospedagens = value;
	}

	public get centroColeta(): CentroTransplante {
		return this._centroColeta;
	}

	public set centroColeta(value: CentroTransplante) {
		this._centroColeta = value;
	}

	public get observacao(): string {
		return this._observacao;
	}

	public set observacao(value: string) {
		this._observacao = value;
	}

	public get tarefaId(): number {
		return this._tarefaId;
	}

	public set tarefaId(value: number) {
		this._tarefaId = value;
	}

	public get rmr(): number {
		return this._rmr;
	}

	public set rmr(value: number) {
		this._rmr = value;
	}

	public get nomeDoador(): string {
		return this._nomeDoador;
	}

	public set nomeDoador(value: string) {
		this._nomeDoador = value;
	}

	public get dmr(): number {
		return this._dmr;
	}

	public set dmr(value: number) {
		this._dmr = value;
	}


}
