import { BaseEntidade } from "app/shared/base.entidade";
import { EnderecoContato } from "../classes/endereco.contato";
import { ConvertUtil } from "../util/convert.util";
import { DetalheLogisticaMaterialAereoDTO } from "./detalhe.logistica.material.aereo.dto";


/**
 * Classe que representa o detalhe do material que consiste em:
 * Informações de coleta, previsão de retirada e a transportadora responsável.
 *
 * @author Pizão
 *
 */
export class DetalheLogisticaMaterialDTO extends BaseEntidade {

  private _idPedidoLogistica:number;
  private _rmr:number;
  private _idDoador:number;
  private _identificacao:number;
  private _nomeDoador:string;
  private _dataColeta: string;
	private _idTipoDoador: number;
	private _nomeFonteCelula: string;
	private _nomeCentroTransplante: string;
  private _nomeLocalRetirada: string;
  private _enderecoRetiradaBancoCordao: string;
  private _enderecoLocalRetirada: EnderecoContato;
	private _enderecoLocalEntrega: EnderecoContato;
	private _contatosLocalRetirada: string[];
  private _justificativa: string;
  private _prosseguirComPedidoLogistica: boolean;
  private _materialAereo: DetalheLogisticaMaterialAereoDTO;
	private _transportadora: number;
	private _horaPrevistaRetirada: Date;


    /**
     * Getter idDoador
     * @return {number}
     */
	public get idDoador(): number {
		return this._idDoador;
	}

    /**
     * Getter rmr
     * @return {number}
     */
	public get rmr(): number {
		return this._rmr;
	}

    /**
     * Getter idPedidoLogistica
     * @return {number}
     */
	public get idPedidoLogistica(): number {
		return this._idPedidoLogistica;
	}

    /**
     * Getter dataColeta
     * @return {string}
     */
	public get dataColeta(): string {
		return this._dataColeta;
	}

    /**
     * Getter nomeLocalRetirada
     * @return {string}
     */
	public get nomeLocalRetirada(): string {
		return this._nomeLocalRetirada;
	}

    /**
     * Getter nomeCentroTransplante
     * @return {string}
     */
	public get nomeCentroTransplante(): string {
		return this._nomeCentroTransplante;
	}

    /**
     * Getter idTipoDoador
     * @return {number}
     */
	public get idTipoDoador(): number {
		return this._idTipoDoador;
	}

    /**
     * Getter nomeFonteCelula
     * @return {string}
     */
	public get nomeFonteCelula(): string {
		return this._nomeFonteCelula;
	}

    /**
     * Getter contatosLocalRetirada
     * @return {string[]}
     */
	public get contatosLocalRetirada(): string[] {
		return this._contatosLocalRetirada;
	}

    /**
     * Setter idDoador
     * @param {number} value
     */
	public set idDoador(value: number) {
		this._idDoador = value;
	}

    /**
     * Setter rmr
     * @param {number} value
     */
	public set rmr(value: number) {
		this._rmr = value;
	}

    /**
     * Setter idPedidoLogistica
     * @param {number} value
     */
	public set idPedidoLogistica(value: number) {
		this._idPedidoLogistica = value;
	}

    /**
     * Setter dataColeta
     * @param {string} value
     */
	public set dataColeta(value: string) {
		this._dataColeta = value;
	}

    /**
     * Setter nomeLocalRetirada
     * @param {string} value
     */
	public set nomeLocalRetirada(value: string) {
		this._nomeLocalRetirada = value;
	}

    /**
     * Setter nomeCentroTransplante
     * @param {string} value
     */
	public set nomeCentroTransplante(value: string) {
		this._nomeCentroTransplante = value;
	}


    /**
     * Setter idTipoDoador
     * @param {number} value
     */
	public set idTipoDoador(value: number) {
		this._idTipoDoador = value;
	}

    /**
     * Setter nomeFonteCelula
     * @param {string} value
     */
	public set nomeFonteCelula(value: string) {
		this._nomeFonteCelula = value;
	}

    /**
     * Setter contatosLocalRetirada
     * @param {string[]} value
     */
	public set contatosLocalRetirada(value: string[]) {
		this._contatosLocalRetirada = value;
	}

	public get nomeDoador(): string {
		return this._nomeDoador;
	}

	public set nomeDoador(value: string) {
		this._nomeDoador = value;
    }


    /**
     * Getter prosseguirComPedidoLogistica
     * @return {boolean}
     */
	public get prosseguirComPedidoLogistica(): boolean {
		return this._prosseguirComPedidoLogistica;
	}

    /**
     * Setter prosseguirComPedidoLogistica
     * @param {boolean} value
     */
	public set prosseguirComPedidoLogistica(value: boolean) {
		this._prosseguirComPedidoLogistica = value;
	}

    /**
     * Getter justificativa
     * @return {string}
     */
	public get justificativa(): string {
		return this._justificativa;
	}

    /**
     * Setter justificativa
     * @param {string} value
     */
	public set justificativa(value: string) {
		this._justificativa = value;
	}


    /**
     * Getter enderecoLocalRetirada
     * @return {EnderecoContato}
     */
	public get enderecoLocalRetirada(): EnderecoContato {
		return this._enderecoLocalRetirada;
	}

    /**
     * Setter enderecoLocalRetirada
     * @param {EnderecoContato} value
     */
	public set enderecoLocalRetirada(value: EnderecoContato) {
		this._enderecoLocalRetirada = value;
	}


    /**
     * Getter enderecoLocalEntrega
     * @return {EnderecoContato}
     */
	public get enderecoLocalEntrega(): EnderecoContato {
		return this._enderecoLocalEntrega;
	}

    /**
     * Setter enderecoLocalEntrega
     * @param {EnderecoContato} value
     */
	public set enderecoLocalEntrega(value: EnderecoContato) {
		this._enderecoLocalEntrega = value;
	}


    /**
     * Getter identificacao
     * @return {number}
     */
	public get identificacao(): number {
		return this._identificacao;
	}

    /**
     * Setter identificacao
     * @param {number} value
     */
	public set identificacao(value: number) {
		this._identificacao = value;
	}

    /**
     * Getter enderecoRetiradaBancoCordao
     * @return {string}
     */
	public get enderecoRetiradaBancoCordao(): string {
		return this._enderecoRetiradaBancoCordao;
	}

    /**
     * Setter enderecoRetiradaBancoCordao
     * @param {string} value
     */
	public set enderecoRetiradaBancoCordao(value: string) {
		this._enderecoRetiradaBancoCordao = value;
	}

    /**
     * Getter materialAereo
     * @return {DetalheLogisticaMaterialAereoDTO}
     */
	public get materialAereo(): DetalheLogisticaMaterialAereoDTO {
		return this._materialAereo;
	}

    /**
     * Setter materialAereo
     * @param {DetalheLogisticaMaterialAereoDTO} value
     */
	public set materialAereo(value: DetalheLogisticaMaterialAereoDTO) {
		this._materialAereo = value;
	}


    /**
     * Getter transportadora
     * @return {number}
     */
	public get transportadora(): number {
		return this._transportadora;
	}

    /**
     * Setter transportadora
     * @param {number} value
     */
	public set transportadora(value: number) {
		this._transportadora = value;
	}


    /**
     * Getter horaPrevistaRetirada
     * @return {Date}
     */
	public get horaPrevistaRetirada(): Date {
		return this._horaPrevistaRetirada;
	}

    /**
     * Setter horaPrevistaRetirada
     * @param {Date} value
     */
	public set horaPrevistaRetirada(value: Date) {
		this._horaPrevistaRetirada = value;
	}


	public jsonToEntity(res: any): DetalheLogisticaMaterialDTO  {

    if(res.enderecoLocalRetirada){
      this.enderecoLocalRetirada = new EnderecoContato().jsonToEntity(res.enderecoLocalRetirada);
    }

    if(res.enderecoLocalEntrega){
      this.enderecoLocalEntrega = new EnderecoContato().jsonToEntity(res.enderecoLocalEntrega);
    }

    if(res.materialAereo){
      this.materialAereo = new DetalheLogisticaMaterialAereoDTO().jsonToEntity(res.materialAereo);
    }

//    this.contatosLocalRetirada = ConvertUtil.parseJsonParaAtributos(res.contatosLocalRetirada, new String[]);


    this.idPedidoLogistica = ConvertUtil.parseJsonParaAtributos(res.idPedidoLogistica, new Number());
    this.rmr = ConvertUtil.parseJsonParaAtributos(res.rmr, new Number());
    this.idDoador = ConvertUtil.parseJsonParaAtributos(res.idDoador, new Number());
    this.identificacao = ConvertUtil.parseJsonParaAtributos(res.identificacao, new Number());
    this.nomeDoador = ConvertUtil.parseJsonParaAtributos(res.nomeDoador, new String());
    this.dataColeta = ConvertUtil.parseJsonParaAtributos(res.dataColeta, new String());
    this.idTipoDoador = ConvertUtil.parseJsonParaAtributos(res.idTipoDoador, new Number());
    this.nomeFonteCelula = ConvertUtil.parseJsonParaAtributos(res.nomeFonteCelula, new String());
    this.nomeCentroTransplante = ConvertUtil.parseJsonParaAtributos(res.nomeCentroTransplante, new String());
    this.nomeLocalRetirada = ConvertUtil.parseJsonParaAtributos(res.nomeLocalRetirada, new String());
    this.enderecoRetiradaBancoCordao = ConvertUtil.parseJsonParaAtributos(res.enderecoRetiradaBancoCordao, new String());
    this.justificativa = ConvertUtil.parseJsonParaAtributos(res.justificativa, new String());
    this.prosseguirComPedidoLogistica = ConvertUtil.parseJsonParaAtributos(res.prosseguirComPedidoLogistica, new Boolean());

    this.transportadora = ConvertUtil.parseJsonParaAtributos(res.transportadora, new Number());
    this.horaPrevistaRetirada = ConvertUtil.parseJsonParaAtributos(res.horaPrevistaRetirada, new Date());

    return this;

  }
}
