import { TipoTransplante } from "app/shared/dominio/tipoTransplante";
import { ConvertUtil } from "app/shared/util/convert.util";
import { DataUtil } from "app/shared/util/data.util";
import { DateTypeFormats } from "app/shared/util/date/date.type.formats";
import { Evolucao } from "../../cadastro/evolucao/evolucao";

/**
 * Classe que representa a tela de consulta de evoluções 
 * (paciente, lista de evoluções e evolução selecionada).
 * @author Pizão
 */
export class EvolucaoDTO {

	private _evolucoes: Evolucao[];
    private _evolucaoSelecionada: Evolucao;
	private _pacienteEmObito:boolean;

	private _tiposTransplante: TipoTransplante[] = [];
	private _dataUltimoTransplante: Date;
	private _dataUltimoTransplanteFormatada:String;
	private _peso: number;	

	/**
	 * Retorna as evoluções que existem para o paciente selecionado.
	 * @return
	 */
	public get evolucoes(): Evolucao[] {
		return this._evolucoes;
	}

	/**
	 * Informa quais são as evoluções selecionadas
	 * @return 
	 */
	public set evolucoes(value: Evolucao[]) {
		this._evolucoes = value;
	}
	
	/**
	 * Representa a evolução detalhada na tela.
	 * @return
	 */
	public get evolucaoSelecionada(): Evolucao {
		return this._evolucaoSelecionada;
	}

	/**
	 * Informa a evolução selecionada e que será detalhada na tela.
	 * 
	 * @param
	 */
	public set evolucaoSelecionada(value: Evolucao) {
		this._evolucaoSelecionada = value;
	}

	/**
	 * Recupera se paciente esta em obito
	 * 
	 * @type {boolean}
	 * @memberof ExameDTO
	 */
	public get pacienteEmObito(): boolean {
		return this._pacienteEmObito;
	}

	/**
	 * Seta se paciente esta em obito
	 * 
	 * @memberof ExameDTO
	 */
	public set pacienteEmObito(value: boolean) {
		this._pacienteEmObito = value;
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
     * Getter peso
     * @return {peso }
     */
	public get peso(): number  {
		return this._peso;
	}

    /**
     * Setter peso
     * @param {peso } value
     */
	public set peso(value: number ) {
		this._peso = value;
	}

	public jsonToEntity(res:any): EvolucaoDTO{

		if (res.tiposTransplante) {
		  res.tiposTransplante.forEach(t=>{
			this.tiposTransplante.push(new TipoTransplante().jsonToEntity(t));
		  });
		}
		this._dataUltimoTransplante = ConvertUtil.parseJsonParaAtributos(res.dataUltimoTransplante, new Date());
		this._dataUltimoTransplanteFormatada = DataUtil.toDateFormat(this._dataUltimoTransplante, DateTypeFormats.DATE_ONLY);
		this._peso = ConvertUtil.parseJsonParaAtributos(res.peso, new Number());

		return this;
	}
}

