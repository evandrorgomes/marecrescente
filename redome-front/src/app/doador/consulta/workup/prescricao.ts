import { TipoAmostraPrescricao } from 'app/shared/classes/tipo.amostra.prescricao';
import { AvaliacaoPrescricao } from '../../../paciente/cadastro/prescricao/avaliacao/avaliacao.prescricao';
import { BaseEntidade } from '../../../shared/base.entidade';
import { FonteCelula } from '../../../shared/dominio/fonte.celula';
import { ConvertUtil } from '../../../shared/util/convert.util';
import { DateMoment } from '../../../shared/util/date/date.moment';
import { Solicitacao } from '../../solicitacao/solicitacao';
import { ArquivoPrescricao } from './arquivo.prescricao';
import { PedidoWorkup } from './pedido.workup';

export class Prescricao extends BaseEntidade {

	private _id:number;
	private _dataColeta1:Date;
	private _dataColeta2:Date;
	private _dataResultadoWorkup1:Date;
	private _dataResultadoWorkup2:Date;
	private _dataCricao:Date;
	private _pedidoWorkup:PedidoWorkup;
	private _fonteCelulaOpcao1: FonteCelula;
	private _fonteCelulaOpcao2: FonteCelula;
	private _quantidadeTotalOpcao1:number;
	private _quantidadeTotalOpcao2:number;
	private _quantidadePorKgOpcao1:number;
	private _quantidadePorKgOpcao2:number;
	private _arquivosPrescricao:ArquivoPrescricao[];
	private _arquivosPrescricaoJustificativa:ArquivoPrescricao[];
	private _avaliacaoPrescricao: AvaliacaoPrescricao;
	private _solicitacao: Solicitacao;
	private _amostras:TipoAmostraPrescricao[];

	private _nomeContatoParaReceber: String;
	private _nomeContatoUrgente: String;
	private _codigoAreaUrgente: number;
	private _telefoneUrgente: number;
  


	/**
	 * Identificador de prescricao.
	 * @returns number
	 */
	public get id(): number {
		return this._id;
	}
	/**
	 * Identificador de prescricao.
	 * @param  {number} value
	 */
	public set id(value: number) {
		this._id = value;
	}
	/**
	 * @returns Date
	 */
	public get dataColeta1(): Date {
		return this._dataColeta1;
	}
	/**
	 * @param  {Date} value
	 */
	public set dataColeta1(value: Date) {
		this._dataColeta1 = value;
	}
	/**
	 * @returns Date
	 */
	public get dataColeta2(): Date {
		return this._dataColeta2;
	}
	/**
	 * @param  {Date} value
	 */
	public set dataColeta2(value: Date) {
		this._dataColeta2 = value;
	}
	/**
	 * @returns Date
	 */
	public get dataResultadoWorkup1(): Date {
		return this._dataResultadoWorkup1;
	}
	/**
	 * @param  {Date} value
	 */
	public set dataResultadoWorkup1(value: Date) {
		this._dataResultadoWorkup1 = value;
	}
	/**
	 * @returns Date
	 */
	public get dataResultadoWorkup2(): Date {
		return this._dataResultadoWorkup2;
	}
	/**
	 * @param  {Date} value
	 */
	public set dataResultadoWorkup2(value: Date) {
		this._dataResultadoWorkup2 = value;
	}
	/**
	 * @returns Date
	 */
	public get dataCricao(): Date {
		return this._dataCricao;
	}
	/**
	 * @param  {Date} value
	 */
	public set dataCricao(value: Date) {
		this._dataCricao = value;
	}
	/**
	 * @returns PedidoWorkup
	 */
	public get pedidoWorkup(): PedidoWorkup {
		return this._pedidoWorkup;
	}
	/**
	 * @param  {PedidoWorkup} value
	 */
	public set pedidoWorkup(value: PedidoWorkup) {
		this._pedidoWorkup = value;
	}


	public get fonteCelulaOpcao1(): FonteCelula {
		return this._fonteCelulaOpcao1;
	}

	public set fonteCelulaOpcao1(value: FonteCelula) {
		this._fonteCelulaOpcao1 = value;
	}

	public get fonteCelulaOpcao2(): FonteCelula {
		return this._fonteCelulaOpcao2;
	}

	public set fonteCelulaOpcao2(value: FonteCelula) {
		this._fonteCelulaOpcao2 = value;
	}

	public get quantidadeTotalOpcao1(): number {
		return this._quantidadeTotalOpcao1;
	}

	public set quantidadeTotalOpcao1(value: number) {
		this._quantidadeTotalOpcao1 = value;
	}

	public get quantidadeTotalOpcao2(): number {
		return this._quantidadeTotalOpcao2;
	}

	public set quantidadeTotalOpcao2(value: number) {
		this._quantidadeTotalOpcao2 = value;
	}

	public get arquivosPrescricao(): ArquivoPrescricao[] {
		return this._arquivosPrescricao;
	}

	public set arquivosPrescricao(value: ArquivoPrescricao[]) {
		this._arquivosPrescricao = value;
	}

	public get arquivosPrescricaoJustificativa(): ArquivoPrescricao[] {
		return this._arquivosPrescricaoJustificativa;
	}

	public set arquivosPrescricaoJustificativa(value: ArquivoPrescricao[]) {
		this._arquivosPrescricaoJustificativa = value;
	}

	public get quantidadePorKgOpcao1(): number {
		return this._quantidadePorKgOpcao1;
	}

	public set quantidadePorKgOpcao1(value: number) {
		this._quantidadePorKgOpcao1 = value;
	}

	public get quantidadePorKgOpcao2(): number {
		return this._quantidadePorKgOpcao2;
	}

	public set quantidadePorKgOpcao2(value: number) {
		this._quantidadePorKgOpcao2 = value;
	}

    /**
     * Getter avaliacaoPrescricao
     * @return {AvaliacaoPrescricao}
     */
	public get avaliacaoPrescricao(): AvaliacaoPrescricao {
		return this._avaliacaoPrescricao;
	}

    /**
     * Setter avaliacaoPrescricao
     * @param {AvaliacaoPrescricao} value
     */
	public set avaliacaoPrescricao(value: AvaliacaoPrescricao) {
		this._avaliacaoPrescricao = value;
	}

    /**
     * Getter solicitacao
     * @return {Solicitacao}
     */
	public get solicitacao(): Solicitacao {
		return this._solicitacao;
	}

    /**
     * Setter solicitacao
     * @param {Solicitacao} value
     */
	public set solicitacao(value: Solicitacao) {
		this._solicitacao = value;
	}


    /**
     * Getter amostras
     * @return {TipoAmostraPrescricao[]}
     */
	public get amostras(): TipoAmostraPrescricao[] {
		return this._amostras;
	}

    /**
     * Setter amostras
     * @param {TipoAmostraPrescricao[]} value
     */
	public set amostras(value: TipoAmostraPrescricao[]) {
		this._amostras = value;
	}

	
	public get nomeContatoUrgente(): String {
		return this._nomeContatoUrgente;
	}

	public set nomeContatoUrgente(value: String) {
		this._nomeContatoUrgente = value;
	}

	public get codigoAreaUrgente(): number {
		return this._codigoAreaUrgente;
	}

	public set codigoAreaUrgente(value: number) {
		this._codigoAreaUrgente = value;
	}

	public get telefoneUrgente(): number {
		return this._telefoneUrgente;
	}

	public set telefoneUrgente(value: number) {
		this._telefoneUrgente = value;
	}

	public menorDataColeta(): Date {
		if (DateMoment.getInstance().isDateBefore(this.dataColeta1, this.dataColeta2)) {
			return this.dataColeta1;
		} else {
			return this.dataColeta2;
		}
	}

	public get nomeContatoParaReceber(): String {
		return this._nomeContatoParaReceber;
	}

	public set nomeContatoParaReceber(value: String) {
		this._nomeContatoParaReceber = value;
	}

	public jsonToEntity(res: any): Prescricao {

		if (res.pedidoWorkup) {
			this.pedidoWorkup = new PedidoWorkup().jsonToEntity(res.pedidoWorkup);
		}

		if (res.fonteCelulaOpcao1) {
			this.fonteCelulaOpcao1 = new FonteCelula().jsonToEntity(res.fonteCelulaOpcao1);
		}

		if (res.fonteCelulaOpcao2) {
			this.fonteCelulaOpcao2 = new FonteCelula().jsonToEntity(res.fonteCelulaOpcao2);
		}

		if (res.solicitacao) {
			this.solicitacao = new Solicitacao().jsonToEntity(res.solicitacao);
		}

		if (res.avaliacaoPrescricao) {
			this.avaliacaoPrescricao = new AvaliacaoPrescricao().jsonToEntity(res.avaliacaoPrescricao);
		}

		if (res.arquivosPrescricao) {
			this.arquivosPrescricao = [];
			res.arquivosPrescricao.forEach(arquivo => {
				this.arquivosPrescricao.push(new ArquivoPrescricao().jsonToEntity(arquivo));
			});
		}

		if (res.arquivosPrescricaoJustificativa) {
			this.arquivosPrescricaoJustificativa = [] ;
			res.arquivosPrescricaoJustificativa.forEach(arquivo => {
				this.arquivosPrescricaoJustificativa.push(new ArquivoPrescricao().jsonToEntity(arquivo));
			});
		}

		if(res.amostras){
			this.amostras.forEach(a =>{
				this.amostras.push(new TipoAmostraPrescricao().jsonToEntity(a));
			})
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.dataColeta1 = ConvertUtil.parseJsonParaAtributos(res.dataColeta1, new Date());
		this.dataColeta2 = ConvertUtil.parseJsonParaAtributos(res.dataColeta2, new Date());
		this.dataResultadoWorkup1 = ConvertUtil.parseJsonParaAtributos(res.dataResultadoWorkup1, new Date());
		this.dataResultadoWorkup2 = ConvertUtil.parseJsonParaAtributos(res.dataResultadoWorkup2, new Date());
		this.dataCricao = ConvertUtil.parseJsonParaAtributos(res.dataCricao, new Date());
		this.quantidadeTotalOpcao1 = ConvertUtil.parseJsonParaAtributos(res.quantidadeTotalOpcao1, new Number());
		this.quantidadeTotalOpcao2 = ConvertUtil.parseJsonParaAtributos(res.quantidadeTotalOpcao2, new Number());
		this.quantidadePorKgOpcao1 = ConvertUtil.parseJsonParaAtributos(res.quantidadePorKgOpcao1, new Number());
		this.quantidadePorKgOpcao2 = ConvertUtil.parseJsonParaAtributos(res.quantidadePorKgOpcao2, new Number());

		this.nomeContatoParaReceber = ConvertUtil.parseJsonParaAtributos(res.nomeContatoParaReceber, new String());
		this.nomeContatoUrgente = ConvertUtil.parseJsonParaAtributos(res.nomeContatoUrgente, new String());
		this.codigoAreaUrgente = ConvertUtil.parseJsonParaAtributos(res.codigoAreaUrgente, new Number());
		this.telefoneUrgente = ConvertUtil.parseJsonParaAtributos(res.telefoneUrgente, new Number());

		return this;
	}

}
