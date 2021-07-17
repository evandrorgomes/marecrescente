import { ConvertUtil } from 'app/shared/util/convert.util';
import { DataUtil } from 'app/shared/util/data.util';
import { DateTypeFormats } from 'app/shared/util/date/date.type.formats';
import { FormatterUtil } from 'app/shared/util/formatter.util';
import { BaseEntidade } from "../../../shared/base.entidade";
import { CondicaoPaciente } from '../../../shared/dominio/condicaoPaciente';
import { EstagioDoenca } from '../../../shared/dominio/estagio.doenca';
import { Motivo } from '../../../shared/dominio/motivo';
import { TipoTransplante } from '../../../shared/dominio/tipoTransplante';
import { Paciente } from '../../paciente';
import { ArquivoEvolucao } from './arquivo.evolucao';

/**
 * Classe Bean utilizada para definir os campos de evolução do Paciente
 *
 * @author Bruno Sousa
 * @export
 * @class Evolucao
 */
export class Evolucao extends BaseEntidade {

  /**
   * Identificador único da classe
   *
   * @private
   * @type {number}
   * @memberOf Evolucao
   */
  private _id: number;

  /**
   * Data da criação da evolução: será preenchida automaticamente
   *
   * @private
   * @type {Date}
   * @memberOf Evolucao
   */
  private _dataCriacao: Date;

  /**
   * Resultado do CMV (Positivo, Negativo)
   *
   * @private
   * @type {number}
   * @memberOf Evolucao
   */
  private _cmv: number;

  /**
   * Existencia de transplante anterior
   *
   * @private
   * @type {boolean}
   * @memberOf Evolucao
   */
  private _tiposTransplante: TipoTransplante[] = [];

  /**
   * Condição Atual do paciente
   *
   * @private
   * @type {string}
   * @memberOf Evolucao
   */
  private _condicaoAtual: string;

  /**
   *  Tratamento anterior do paciente
   *
   * @private
   * @type {String}
   * @memberOf Evolucao
   */
  private _tratamentoAnterior: String;

  /**
   * Tratamento atual do paciente
   *
   * @private
   * @type {string}
   * @memberOf Evolucao
   */
  private _tratamentoAtual: string;

  /**
   * Altura do paciente - este deve conter duas casas decimais
   *
   * @private
   * @type {number}
   * @memberOf Evolucao
   */
  private _altura: number;

  /**
   * Peso do paciente - este deve conter 1 casa decimal;
   *
   * @private
   * @type {number}
   * @memberOf Evolucao
   */
  private _peso: number;

  /**
   * Peso do paciente - este deve conter 1 casa decimal;
   *
   * @private
   * @type {string}
   * @memberOf Evolucao
   */
  private _pesoFormatado: string;

  /**
   * Evolução da condução do paciente
   *
   * @private
   * @type {CondicaoPaciente}
   * @memberOf Evolucao
   */
  private _condicaoPaciente: CondicaoPaciente;

  /**
   * Estágio da doênça de acordo com a cid estabelecida no diagnóstico
   *
   * @private
   * @type {EstagioDoenca}
   * @memberOf Evolucao
   */
  private _estagioDoenca: EstagioDoenca;

  /**
   * Motivo da evolução
   *
   * @private
   * @type {Motivo}
   * @memberOf Evolucao
   */
  private _motivo: Motivo;

  /**
   * Paciente
   *
   * @private
   * @type {Paciente}
   * @memberOf Evolucao
   */
  private _paciente: Paciente;


  /**
   * ArquivoEvolucao[]
   *
   * @private
   * @type {ArquivoEvolucao}
   * @memberOf Evolucao
   */
  private _arquivosEvolucao: ArquivoEvolucao[];

  private _exameAnticorpo: boolean;
	private _dataExameAnticorpo: Date;
  private _resultadoExameAnticorpo: string;

  private _dataUltimoTransplante: Date;

  private _dataUltimoTransplanteFormatada:String;


	/**
     *
     *
     * @type {number}
     * @memberOf Evolucao
     */
  public get id(): number {
    return this._id;
  }

	/**
     *
     *
     *
     * @memberOf Evolucao
     */
  public set id(value: number) {
    this._id = value;
  }

	/**
     *
     *
     * @type {Date}
     * @memberOf Evolucao
     */
  public get dataCriacao(): Date {
    return this._dataCriacao;
  }

	/**
     *
     *
     *
     * @memberOf Evolucao
     */
  public set dataCriacao(value: Date) {
    this._dataCriacao = value;
  }

	/**
     *
     *
     * @type {number}
     * @memberOf Evolucao
     */
  public get cmv(): number {
    return this._cmv;
  }

	/**
     *
     *
     *
     * @memberOf Evolucao
     */
  public set cmv(value: number) {
    this._cmv = value;
  }


    /**
     * Getter tiposTransplante
     * @return {TipoTransplante[] }
     */
	public get tiposTransplante(): TipoTransplante[]  {
		return this._tiposTransplante;
	}

    /**
     * Setter tiposTransplante
     * @param {TipoTransplante[] } value
     */
	public set tiposTransplante(value: TipoTransplante[] ) {
		this._tiposTransplante = value;
	}


	/**
     *
     *
     * @type {string}
     * @memberOf Evolucao
     */
  public get condicaoAtual(): string {
    return this._condicaoAtual;
  }

	/**
     *
     *
     *
     * @memberOf Evolucao
     */
  public set condicaoAtual(value: string) {
    this._condicaoAtual = value;
  }

	/**
     *
     *
     * @type {String}
     * @memberOf Evolucao
     */
  public get tratamentoAnterior(): String {
    return this._tratamentoAnterior;
  }

	/**
     *
     *
     *
     * @memberOf Evolucao
     */
  public set tratamentoAnterior(value: String) {
    this._tratamentoAnterior = value;
  }

	/**
     *
     *
     * @type {string}
     * @memberOf Evolucao
     */
  public get tratamentoAtual(): string {
    return this._tratamentoAtual;
  }

	/**
     *
     *
     *
     * @memberOf Evolucao
     */
  public set tratamentoAtual(value: string) {
    this._tratamentoAtual = value;
  }

	/**
     *
     *
     * @type {number}
     * @memberOf Evolucao
     */
  public get altura(): number {
    return this._altura;
  }

	/**
     *
     *
     *
     * @memberOf Evolucao
     */
  public set altura(value: number) {
    this._altura = value;
  }

	/**
     *
     *
     * @type {number}
     * @memberOf Evolucao
     */
  public get peso(): number {
    return this._peso;
  }

	/**
     *
     *
     *
     * @memberOf Evolucao
     */
  public set peso(value: number) {
    this._peso = value;
  }

	/**
     *
     *
     * @type {CondicaoPaciente}
     * @memberOf Evolucao
     */
  public get condicaoPaciente(): CondicaoPaciente {
    return this._condicaoPaciente;
  }

	/**
     *
     *
     *
     * @memberOf Evolucao
     */
  public set condicaoPaciente(value: CondicaoPaciente) {
    this._condicaoPaciente = value;
  }

	/**
     *
     *
     * @type {EstagioDoenca}
     * @memberOf Evolucao
     */
  public get estagioDoenca(): EstagioDoenca {
    return this._estagioDoenca;
  }

	/**
     *
     *
     *
     * @memberOf Evolucao
     */
  public set estagioDoenca(value: EstagioDoenca) {
    this._estagioDoenca = value;
  }

	/**
     *
     *
     * @type {Motivo}
     * @memberOf Evolucao
     */
  public get motivo(): Motivo {
    return this._motivo;
  }

	/**
     *
     *
     *
     * @memberOf Evolucao
     */
  public set motivo(value: Motivo) {
    this._motivo = value;
  }

	/**
     *
     *
     * @type {Paciente}
     * @memberOf Evolucao
     */
  public get paciente(): Paciente {
    return this._paciente;
  }

	/**
     *
     *
     *
     * @memberOf Evolucao
     */
  public set paciente(value: Paciente) {
    this._paciente = value;
  }


    /**
     * Getter arquivosEvolucao
     * @return {ArquivoEvolucao[]}
     */
	public get arquivosEvolucao(): ArquivoEvolucao[] {
		return this._arquivosEvolucao;
	}

    /**
     * Setter arquivosEvolucao
     * @param {ArquivoEvolucao[]} value
     */
	public set arquivosEvolucao(value: ArquivoEvolucao[]) {
		this._arquivosEvolucao = value;
	}

    /**
     * Getter existeExameAnticorpo
     * @return {number}
     */
	public get exameAnticorpo(): boolean {
		return this._exameAnticorpo;
	}

    /**
     * Setter existeExameAnticorpo
     * @param {number} value
     */
	public set exameAnticorpo(value: boolean) {
		this._exameAnticorpo = value;
	}

    /**
     * Getter dataExameAnticorpo
     * @return {Date}
     */
	public get dataExameAnticorpo(): Date {
		return this._dataExameAnticorpo;
	}

    /**
     * Setter dataExameAnticorpo
     * @param {Date} value
     */
	public set dataExameAnticorpo(value: Date) {
		this._dataExameAnticorpo = value;
	}

    /**
     * Getter resultadoExameAnticorpo
     * @return {string}
     */
	public get resultadoExameAnticorpo(): string {
		return this._resultadoExameAnticorpo;
	}

    /**
     * Setter resultadoExameAnticorpo
     * @param {string} value
     */
	public set resultadoExameAnticorpo(value: string) {
		this._resultadoExameAnticorpo = value;
  }
    /**
     * Getter dataUltimoTransplante
     * @return {Date}
     */
	public get dataUltimoTransplante(): Date {
		return this._dataUltimoTransplante;
	}

    /**
     * Setter dataUltimoTransplante
     * @param {Date} value
     */
	public set dataUltimoTransplante(value: Date) {
		this._dataUltimoTransplante = value;
	}



    /**
     * Getter dataUltimoTransplanteFormatada
     * @return {String}
     */
	public get dataUltimoTransplanteFormatada(): String {
		return this._dataUltimoTransplanteFormatada;
	}

    /**
     * Setter dataUltimoTransplanteFormatada
     * @param {String} value
     */
	public set dataUltimoTransplanteFormatada(value: String) {
		this._dataUltimoTransplanteFormatada = value;
	}

  /**
   * Getter pesoFormatado
   * @return {string}
   */
	public get pesoFormatado(): string {
		return this._pesoFormatado;
	}

  /**
   * Setter pesoFormatado
   * @param {string} value
   */
	public set pesoFormatado(value: string) {
		this._pesoFormatado = value;
	}

  public jsonToEntity(res:any):this{

    if (res.tiposTransplante) {
      res.tiposTransplante.forEach(t=>{
        this.tiposTransplante.push(new TipoTransplante().jsonToEntity(t));
      });
    }

    if (res.condicaoPaciente) {
      this.condicaoPaciente = new CondicaoPaciente().jsonToEntity(res.condicaoPaciente);
    }

    if (res.estagioDoenca) {
      this.estagioDoenca = new EstagioDoenca().jsonToEntity(res.estagioDoenca);
    }

    if (res.motivo) {
      this.motivo = new Motivo().jsonToEntity(res.motivo);
    }

    if (res.paciente) {
      this.paciente = new Paciente().jsonToEntity(res.paciente);
    }

    if (res.arquivosEvolucao) {
      this.arquivosEvolucao = [];
      res.arquivosEvolucao.forEach( arquivo => {
        this.arquivosEvolucao.push(new  ArquivoEvolucao().jsonToEntity(arquivo));
      });
    }

    this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
    this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
    this.cmv = ConvertUtil.parseJsonParaAtributos(res.cmv, new Number());
    this.condicaoAtual = ConvertUtil.parseJsonParaAtributos(res.condicaoAtual, new String());
    this.tratamentoAnterior = ConvertUtil.parseJsonParaAtributos(res.tratamentoAnterior, new String());
    this.tratamentoAtual = ConvertUtil.parseJsonParaAtributos(res.tratamentoAtual, new String());
    this.altura = ConvertUtil.parseJsonParaAtributos(res.altura, new Number());
    this.peso = ConvertUtil.parseJsonParaAtributos(res.peso, new Number());
    this.pesoFormatado = FormatterUtil.obterPesoFormatado(String(res.peso),1);
    this.exameAnticorpo =  ConvertUtil.parseJsonParaAtributos(res.exameAnticorpo, new Boolean());
		this.dataExameAnticorpo =  ConvertUtil.parseJsonParaAtributos(res.dataExameAnticorpo, new Date());
    this.resultadoExameAnticorpo =  ConvertUtil.parseJsonParaAtributos(res.resultadoExameAnticorpo, new String());
    this.dataUltimoTransplante = ConvertUtil.parseJsonParaAtributos(res.dataUltimoTransplante, new Date());
    this.dataUltimoTransplanteFormatada = DataUtil.toDateFormat(this._dataUltimoTransplante, DateTypeFormats.DATE_ONLY);
		return this;
	}
}
