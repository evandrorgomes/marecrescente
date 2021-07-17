import { DataUtil } from 'app/shared/util/data.util';
import { DateTypeFormats } from 'app/shared/util/date/date.type.formats';
import { BaseEntidade } from '../../../shared/base.entidade';
import { StatusPedidoColeta } from '../../../shared/dominio/status.pedido.coleta';
import { UsuarioLogado } from '../../../shared/dominio/usuario.logado';
import { ConvertUtil } from '../../../shared/util/convert.util';
import { Solicitacao } from '../../solicitacao/solicitacao';
import { Disponibilidade } from '../workup/disponibilidade';
import { PedidoWorkup } from '../workup/pedido.workup';

/**
 * Classe que representa um pedido de coleta.
 *
 * @author ergomes
 * @export
 * @class PedidoColeta
 * @extends {BaseEntidade}
 */
export class PedidoColeta extends BaseEntidade {

    /**
	 * Identificador do pedido de coleta.
	 */
	private _id: number;

	/**
	 * Usuário responsável pelo pedido de coleta.
	 */
	private _usuario: UsuarioLogado;

	/**
	 * Data da criação do pedido.
	 */
	private _dataCriacao: Date;

	/**
	 * Data em que o doador deverá estar disponível no centro de coleta.
	 */
	private _dataDisponibilidadeDoador: Date;

	/**
	 * Data em que o doador será liberado pelo centro de coleta.
	 */
	private _dataLiberacaoDoador: Date;

	/**
	 * Indica se necessita de logistica para o doador.
	 */
	private _necessitaLogisticaDoador: boolean;

	/**
	 * Indica se necessita de logistica para o material.
	 */
	private _necessitaLogisticaMaterial: boolean;

	/**
	 * Pedido de workup associado a coleta, no caso de medula óssea.
	 */
	private _pedidoWorkup: PedidoWorkup;

	/**
	 * Centro de transplante responsável pela coleta.
	 */
	private _centroColeta: number;

	/**
	 * Data da ultima alteração do status.
	 */
	private _dataUltimoStatus: Date;

	/**
	 * Status do pedido.
	 */
	private _statusPedidoColeta: StatusPedidoColeta;

	/**
	 * Solicitação realizada para o procedimento de workup/coleta
	 * ou somente coleta, para os casos de cordão (ainda será implementado).
	 */
	private _solicitacao: Solicitacao;

  /**
  * Ultima disponibilidade.
  */
	private _ultimaDisponibilidade: Disponibilidade;

  /**
  * Data da coleta do material do doador.
  */
	private _dataColeta: Date;

	/**
	 * Tipo de produto selecionado 0-MO | 1-PBSC | DLI
	 */
	private _tipoProdudo: number;

	/**
	 * Data e hora de início do G-CSF do doador.
	 */
	private _dataInicioGcsf: Date;

	/**
	 * Data e hora da Internação do doador.
	 */
	private _dataInternacao: Date;

	/**
	 * Setor ou andar do endereço da G-CSF.
	 */
	private _gcsfSetorAndar: string;

	/**
	 * Procurar por nome indicação no endereço da G-CSF.
	 */
	private _gcsfProcurarPor: string;

	/**
	 * Setor ou andar do endereço da Internacao.
	 */
	private _internacaoSetorAndar: string;

	/**
	 * Procurar por nome indicação no endereço de Internacao.
	 */
	private _internacaoProcurarPor: string;

	/**
	 * Flag doador em jejum informado no agendamento de coleta.
	 */
	private _estaEmJejum: Boolean;

	/**
	 * Define a quantidade de horas em jejum.
	 */
	private _quantasHorasEmJejum: number;

	/**
	 * Define se o doador esta totalmente em jejum.
	 */
	private _estaTotalmenteEmJejum: Boolean;

  private _dataColetaFormatada: string;

  private _dataInternacaoFormatada: string;


	/**
	 * Informações adicionais sobre os agendamento.
	 */
	private _informacoesAdicionais: string;

  constructor(id?: number) {
    super();
    this._id = id;
  }

  public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get usuario(): UsuarioLogado {
		return this._usuario;
	}

	public set usuario(value: UsuarioLogado) {
		this._usuario = value;
	}

	public get dataCriacao(): Date {
		return this._dataCriacao;
	}

	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}

	public get dataDisponibilidadeDoador(): Date {
		return this._dataDisponibilidadeDoador;
	}

	public set dataDisponibilidadeDoador(value: Date) {
		this._dataDisponibilidadeDoador = value;
	}

	public get dataLiberacaoDoador(): Date {
		return this._dataLiberacaoDoador;
	}

	public set dataLiberacaoDoador(value: Date) {
		this._dataLiberacaoDoador = value;
	}

	public get necessitaLogisticaDoador(): boolean {
		return this._necessitaLogisticaDoador;
	}

	public set necessitaLogisticaDoador(value: boolean) {
		this._necessitaLogisticaDoador = value;
	}

	public get necessitaLogisticaMaterial(): boolean {
		return this._necessitaLogisticaMaterial;
	}

	public set necessitaLogisticaMaterial(value: boolean) {
		this._necessitaLogisticaMaterial = value;
	}

  /**
   * Getter pedidoWorkup
   * @return {PedidoWorkup}
   */
	public get pedidoWorkup(): PedidoWorkup {
		return this._pedidoWorkup;
	}

  /**
   * Setter pedidoWorkup
   * @param {PedidoWorkup} value
   */
	public set pedidoWorkup(value: PedidoWorkup) {
		this._pedidoWorkup = value;
	}

  /**
   * Getter centroColeta
   * @return {number}
   */
	public get centroColeta(): number {
		return this._centroColeta;
	}

  /**
   * Setter centroColeta
   * @param {number} value
   */
	public set centroColeta(value: number) {
		this._centroColeta = value;
	}

	public get dataUltimoStatus(): Date {
		return this._dataUltimoStatus;
	}

	public set dataUltimoStatus(value: Date) {
		this._dataUltimoStatus = value;
	}

	public get statusPedidoColeta(): StatusPedidoColeta {
		return this._statusPedidoColeta;
	}

	public set statusPedidoColeta(value: StatusPedidoColeta) {
		this._statusPedidoColeta = value;
	}

	public get solicitacao(): Solicitacao {
		return this._solicitacao;
	}

	public set solicitacao(value: Solicitacao) {
		this._solicitacao = value;
	}

    /**
     * Getter ultimaDisponibilidade
     * @return {Disponibilidade}
     */
	public get ultimaDisponibilidade(): Disponibilidade {
		return this._ultimaDisponibilidade;
	}

    /**
     * Setter ultimaDisponibilidade
     * @param {Disponibilidade} value
     */
	public set ultimaDisponibilidade(value: Disponibilidade) {
		this._ultimaDisponibilidade = value;
	}

    /**
     * Getter dataColeta
     * @return {Date}
     */
	public get dataColeta(): Date {
		return this._dataColeta;
	}

    /**
     * Setter dataColeta
     * @param {Date} value
     */
	public set dataColeta(value: Date) {
		this._dataColeta = value;
	}


    /**
     * Getter tipoProdudo
     * @return {number}
     */
	public get tipoProdudo(): number {
		return this._tipoProdudo;
	}

    /**
     * Getter dataInicioGcsf
     * @return {Date}
     */
	public get dataInicioGcsf(): Date {
		return this._dataInicioGcsf;
	}

    /**
     * Getter dataInternacao
     * @return {Date}
     */
	public get dataInternacao(): Date {
		return this._dataInternacao;
	}

    /**
     * Getter gcsfSetorAndar
     * @return {string}
     */
	public get gcsfSetorAndar(): string {
		return this._gcsfSetorAndar;
	}

    /**
     * Getter gcsfProcurarPor
     * @return {string}
     */
	public get gcsfProcurarPor(): string {
		return this._gcsfProcurarPor;
	}

    /**
     * Getter internacaoSetorAndar
     * @return {string}
     */
	public get internacaoSetorAndar(): string {
		return this._internacaoSetorAndar;
	}

    /**
     * Getter internacaoProcurarPor
     * @return {string}
     */
	public get internacaoProcurarPor(): string {
		return this._internacaoProcurarPor;
	}

    /**
     * Getter estaEmJejum
     * @return {Boolean}
     */
	public get estaEmJejum(): Boolean {
		return this._estaEmJejum;
	}

    /**
     * Getter quantasHorasEmJejum
     * @return {number}
     */
	public get quantasHorasEmJejum(): number {
		return this._quantasHorasEmJejum;
	}

    /**
     * Getter estaTotalmenteEmJejum
     * @return {Boolean}
     */
	public get estaTotalmenteEmJejum(): Boolean {
		return this._estaTotalmenteEmJejum;
	}

    /**
     * Getter informacoesAdicionais
     * @return {string}
     */
	public get informacoesAdicionais(): string {
		return this._informacoesAdicionais;
	}

    /**
     * Setter tipoProdudo
     * @param {number} value
     */
	public set tipoProdudo(value: number) {
		this._tipoProdudo = value;
	}

    /**
     * Setter dataInicioGcsf
     * @param {Date} value
     */
	public set dataInicioGcsf(value: Date) {
		this._dataInicioGcsf = value;
	}

    /**
     * Setter dataInternacao
     * @param {Date} value
     */
	public set dataInternacao(value: Date) {
		this._dataInternacao = value;
	}

    /**
     * Setter gcsfSetorAndar
     * @param {string} value
     */
	public set gcsfSetorAndar(value: string) {
		this._gcsfSetorAndar = value;
	}

    /**
     * Setter gcsfProcurarPor
     * @param {string} value
     */
	public set gcsfProcurarPor(value: string) {
		this._gcsfProcurarPor = value;
	}

    /**
     * Setter internacaoSetorAndar
     * @param {string} value
     */
	public set internacaoSetorAndar(value: string) {
		this._internacaoSetorAndar = value;
	}

    /**
     * Setter internacaoProcurarPor
     * @param {string} value
     */
	public set internacaoProcurarPor(value: string) {
		this._internacaoProcurarPor = value;
	}

    /**
     * Setter estaEmJejum
     * @param {Boolean} value
     */
	public set estaEmJejum(value: Boolean) {
		this._estaEmJejum = value;
	}

    /**
     * Setter quantasHorasEmJejum
     * @param {number} value
     */
	public set quantasHorasEmJejum(value: number) {
		this._quantasHorasEmJejum = value;
	}

    /**
     * Setter estaTotalmenteEmJejum
     * @param {Boolean} value
     */
	public set estaTotalmenteEmJejum(value: Boolean) {
		this._estaTotalmenteEmJejum = value;
	}

    /**
     * Setter informacoesAdicionais
     * @param {string} value
     */
	public set informacoesAdicionais(value: string) {
		this._informacoesAdicionais = value;
	}


    /**
     * Getter dataColetaFormatada
     * @return {string}
     */
	public get dataColetaFormatada(): string {
		return this._dataColetaFormatada;
	}

    /**
     * Setter dataColetaFormatada
     * @param {string} value
     */
	public set dataColetaFormatada(value: string) {
		this._dataColetaFormatada = value;
	}

    /**
     * Getter dataInternacaoFormatada
     * @return {string}
     */
	public get dataInternacaoFormatada(): string {
		return this._dataInternacaoFormatada;
	}

    /**
     * Setter dataInternacaoFormatada
     * @param {string} value
     */
	public set dataInternacaoFormatada(value: string) {
		this._dataInternacaoFormatada = value;
	}


	public jsonToEntity(res:any): PedidoColeta {

		if (res.usuario) {
			this.usuario =  new UsuarioLogado().jsonToEntity(res.usuario);
		}

		if (res.pedidoWorkup) {
			this.pedidoWorkup =  new PedidoWorkup().jsonToEntity(res.pedidoWorkup);
		}

		if (res.statusPedidoColeta) {
			this.statusPedidoColeta = new StatusPedidoColeta().jsonToEntity(res.statusPedidoColeta);
		}

		if (res.solicitacao) {
			this.solicitacao = new Solicitacao().jsonToEntity(res.solicitacao);
		}

		if (res.ultimaDisponibilidade) {
			this.ultimaDisponibilidade = new Disponibilidade().jsonToEntity(res.ultimaDisponibilidade);
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
		this.dataDisponibilidadeDoador = ConvertUtil.parseJsonParaAtributos(res.dataDisponibilidadeDoador, new Date());
		this.dataLiberacaoDoador = ConvertUtil.parseJsonParaAtributos(res.dataLiberacaoDoador, new Date());
		this.necessitaLogisticaDoador = ConvertUtil.parseJsonParaAtributos(res.necessitaLogisticaDoador, new Boolean());;
		this.necessitaLogisticaMaterial = ConvertUtil.parseJsonParaAtributos(res.necessitaLogisticaMaterial, new Boolean());;
		this.dataUltimoStatus = ConvertUtil.parseJsonParaAtributos(res.dataUltimoStatus, new Date());
		this.dataColeta = ConvertUtil.parseJsonParaAtributos(res.dataColeta, new Date());
    this.tipoProdudo = ConvertUtil.parseJsonParaAtributos(res.tipoProdudo, new Number());
		this.dataInicioGcsf = ConvertUtil.parseJsonParaAtributos(res.dataInicioGcsf, new Date());
		this.dataInternacao = ConvertUtil.parseJsonParaAtributos(res.dataInternacao, new Date());
	  this.gcsfSetorAndar = ConvertUtil.parseJsonParaAtributos(res.gcsfSetorAndar, new String());
	  this.gcsfProcurarPor = ConvertUtil.parseJsonParaAtributos(res.gcsfProcurarPor, new String());
    this.internacaoSetorAndar = ConvertUtil.parseJsonParaAtributos(res.internacaoSetorAndar, new String());
    this.internacaoProcurarPor = ConvertUtil.parseJsonParaAtributos(res.internacaoProcurarPor, new String());
    this.estaEmJejum = ConvertUtil.parseJsonParaAtributos(res.estaEmJejum, new Number());
    this.quantasHorasEmJejum = ConvertUtil.parseJsonParaAtributos(res.quantasHorasEmJejum, new Number());
    this.estaTotalmenteEmJejum = ConvertUtil.parseJsonParaAtributos(res.estaTotalmenteEmJejum, new Number());
    this.informacoesAdicionais = ConvertUtil.parseJsonParaAtributos(res.informacoesAdicionais, new String());
    this.centroColeta = ConvertUtil.parseJsonParaAtributos(res.centroColeta, new Number());
    this.dataColetaFormatada = DataUtil.toDateFormat(this.dataColeta, DateTypeFormats.DATE_ONLY);
    this.dataInternacaoFormatada = DataUtil.toDateFormat(this.dataInternacao, DateTypeFormats.DATE_ONLY);

		return this;
	}

}
