import { IFiltrosOrdenacaoBuscaDTO } from 'app/shared/dto/interface.filtros.ordenacao.busca.dto';
import { BaseEntidade } from "../shared/base.entidade";
import { Locus } from './cadastro/exame/locus';
import { GenotipoDTO } from './genotipo.dto';


/**
 * Classe que representa um genotipo, associada ao doador.
 * @author Fillipe Queiroz
 */
export class GenotipoDoadorComparadoDTO extends BaseEntidade implements IFiltrosOrdenacaoBuscaDTO {

	private _id: number;
	private _idDoador: number;
	private _dmr: number;
	private _genotipoDoador: GenotipoDTO[];
	private _sexo: string;
	private _sexoFormatado: string;
	private _dataNascimento: Date;
	private _peso: number;
	private _abo: string;
	private _idade: string;
	private _classificacao: string;
	private _mismatch: string;
	private _fase: string;

	private _idRegistro: string;
	private _registroOrigem: string;
	private _quantidadeTCNPorKilo: number;
	private _quantidadeCD34PorKilo: number;
	private _tipoDoador: number;
	private _idBscup: string;

	private _dataAtualizacao: Date;
	private _somaQualificacao: number;

  private _descricaoTipoPermissividade: string;
  private _ordenacaoWmdaMatch: number;

  private _identificadorDoador: string;

	/**
	 * @returns number
	 */
	public get dmr(): number {
		return this._dmr;
	}
	/**
	 * @param  {number} value
	 */
	public set dmr(value: number) {
		this._dmr = value;
	}
	/**
	 * @returns GenotipoDTO
	 */
	public get genotipoDoador(): GenotipoDTO[] {
		return this._genotipoDoador;
	}
	/**
	 * @param  {GenotipoDTO[]} value
	 */
	public set genotipoDoador(value: GenotipoDTO[]) {
		this._genotipoDoador = value;
	}
	/**
	 * @returns string
	 */
	public get sexo(): string {
		return this._sexo;
	}
	/**
	 * @param  {string} value
	 */
	public set sexo(value: string) {
		this._sexo = value;
	}
	/**
	 * @returns Date
	 */
	public get dataNascimento(): Date {
		return this._dataNascimento;
	}
	/**
	 * @param  {Date} value
	 */
	public set dataNascimento(value: Date) {
		this._dataNascimento = value;
	}
	/**
	 * @returns number
	 */
	public get peso(): number {
		return this._peso;
	}
	/**
	 * @param  {number} value
	 */
	public set peso(value: number) {
		this._peso = value;
	}
	/**
	 * @returns string
	 */
	public get abo(): string {
		return this._abo;
	}
	/**
	 * @param  {string} value
	 */
	public set abo(value: string) {
		this._abo = value;
	}
	/**
	 * @returns string
	 */
	public get idade(): string {
		return this._idade;
	}
	/**
	 * @param  {string} value
	 */
	public set idade(value: string) {
		this._idade = value;
	}
	/**
	 * @returns string
	 */
	public get classificacao(): string {
		return this._classificacao;
	}
	/**
	 * @param  {string} value
	 */
	public set classificacao(value: string) {
		this._classificacao = value;
	}

	private doadorPossuiGenotipoComLocus(locus: Locus, comValor: boolean): boolean {
		if (this.genotipoDoador) {
			return this.genotipoDoador.some(genotipo => {
				let possui = genotipo.locus == locus.codigo;
				if (possui) {
					if (comValor) {
						if (!genotipo.primeiroAlelo || genotipo.primeiroAlelo == "") {
							possui = false;
						}
					}
					else {
						if (!genotipo.primeiroAlelo || genotipo.primeiroAlelo == "") {
							possui = true;
						}
						else {
							possui = false;
						}
					}
				}
				return possui;
			});
		}
		return false;
	}

	public doadorPossuiGenotipoComLocusEValor(locus: Locus): boolean {
		return this.doadorPossuiGenotipoComLocus(locus, true);
	}

	public doadorPossuiGenotipoComLocusESemValor(locus: Locus): boolean {
		return this.doadorPossuiGenotipoComLocus(locus, false);
	}

	public obterValorAleloGenotipoDoador(codigoLocus: string): GenotipoDTO {
		if (this.genotipoDoador) {
      return this.genotipoDoador.filter(genotipo => genotipo.locus == codigoLocus)[0];
		}
	}

	/**
	 * Getter idDoador
	 * @return {number}
	 */
	public get idDoador(): number {
		return this._idDoador;
	}

	/**
	 * Setter idDoador
	 * @param {number} value
	 */
	public set idDoador(value: number) {
		this._idDoador = value;
	}

	/**
	 * Getter idRegistro
	 * @return {string}
	 */
	public get idRegistro(): string {
		return this._idRegistro;
	}

	/**
	 * Setter idRegistro
	 * @param {string} value
	 */
	public set idRegistro(value: string) {
		this._idRegistro = value;
	}

	/**
	 * Getter registroOrigem
	 * @return {string}
	 */
	public get registroOrigem(): string {
		return this._registroOrigem;
	}

	/**
	 * Setter registroOrigem
	 * @param {string} value
	 */
	public set registroOrigem(value: string) {
		this._registroOrigem = value;
	}

	/**
	 * Getter quantidadeTCNPorKilo
	 * @return {number}
	 */
	public get quantidadeTCNPorKilo(): number {
		return this._quantidadeTCNPorKilo;
	}

	/**
	 * Setter quantidadeTCNPorKilo
	 * @param {number} value
	 */
	public set quantidadeTCNPorKilo(value: number) {
		this._quantidadeTCNPorKilo = value;
	}

	/**
	 * Getter quantidadeCD34PorKilo
	 * @return {number}
	 */
	public get quantidadeCD34PorKilo(): number {
		return this._quantidadeCD34PorKilo;
	}

	/**
	 * Setter quantidadeCD34PorKilo
	 * @param {number} value
	 */
	public set quantidadeCD34PorKilo(value: number) {
		this._quantidadeCD34PorKilo = value;
	}

	/**
	 * Getter tipoDoador
	 * @return {number}
	 */
	public get tipoDoador(): number {
		return this._tipoDoador;
	}

	/**
	 * Setter tipoDoador
	 * @param {number} value
	 */
	public set tipoDoador(value: number) {
		this._tipoDoador = value;
	}

    /**
     * Getter idBscup
     * @return {string}
     */
	public get idBscup(): string {
		return this._idBscup;
	}

    /**
     * Setter idBscup
     * @param {string} value
     */
	public set idBscup(value: string) {
		this._idBscup = value;
	}



	/**
	 * Getter mismatch
	 * @return {string}
	 */
	public get mismatch(): string {
		return this._mismatch;
	}

	/**
	 * Getter dataAtualizacao
	 * @return {Date}
	 */
	public get dataAtualizacao(): Date {
		return this._dataAtualizacao;
	}

	/**
	 * Setter mismatch
	 * @param {string} value
	 */
	public set mismatch(value: string) {
		this._mismatch = value;
	}

	/**
	 * Setter dataAtualizacao
	 * @param {Date} value
	 */
	public set dataAtualizacao(value: Date) {
		this._dataAtualizacao = value;
	}

    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}

    /**
     * Getter fase
     * @return {string}
     */
	public get fase(): string {
		return this._fase;
	}

    /**
     * Setter fase
     * @param {string} value
     */
	public set fase(value: string) {
		this._fase = value;
	}


    /**
     * Getter sexoFormatado
     * @return {string}
     */
	public get sexoFormatado(): string {
		return this._sexoFormatado;
	}

    /**
     * Setter sexoFormatado
     * @param {string} value
     */
	public set sexoFormatado(value: string) {
		this._sexoFormatado = value;
	}



    /**
     * Getter somaQualificacao
     * @return {number}
     */
	public get somaQualificacao(): number {
		return this._somaQualificacao;
	}

    /**
     * Setter somaQualificacao
     * @param {number} value
     */
	public set somaQualificacao(value: number) {
		this._somaQualificacao = value;
	}

    /**
     * Getter descricaoTipoPermissividade
     * @return {string}
     */
	public get descricaoTipoPermissividade(): string {
		return this._descricaoTipoPermissividade;
	}

    /**
     * Setter descricaoTipoPermissividade
     * @param {string} value
     */
	public set descricaoTipoPermissividade(value: string) {
		this._descricaoTipoPermissividade = value;
	}


    /**
     * Getter ordenacaoWmdaMatch
     * @return {number}
     */
	public get ordenacaoWmdaMatch(): number {
		return this._ordenacaoWmdaMatch;
	}

    /**
     * Setter ordenacaoWmdaMatch
     * @param {number} value
     */
	public set ordenacaoWmdaMatch(value: number) {
		this._ordenacaoWmdaMatch = value;
	}

  /**
   * Getter identificadorDoador
   * @return {string}
   */
	public get identificadorDoador(): string {
		return this._identificadorDoador;
	}

  /**
   * Setter identificadorDoador
   * @param {string} value
   */
	public set identificadorDoador(value: string) {
		this._identificadorDoador = value;
	}

	public jsonToEntity(res: any) {
		throw new Error("Method not implemented.");
	}
}
