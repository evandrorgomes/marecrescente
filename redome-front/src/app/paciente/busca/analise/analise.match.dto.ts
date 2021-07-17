import { BuscaChecklistDTO } from 'app/shared/component/listamatch/classes/busca.checklist.dto';
import { BaseEntidade } from '../../../shared/base.entidade';
import { MatchDTO } from "../../../shared/component/listamatch/match.dto";
import { CentroTransplante } from '../../../shared/dominio/centro.transplante';

/**
 * Classe que representa o dto com a listade matchs de um paciente
 *
 * @author Bruno Sousa
 * @export
 * @class AnaliseMatchDTO
 */
export class AnaliseMatchDTO extends BaseEntidade {

    private _rmr: number;
    private _listaFase1: MatchDTO[];
    private _listaFase2: MatchDTO[];
    private _listaFase3: MatchDTO[];
	private _listaDisponibilidade: MatchDTO[];
	private _abo:string;

	private _buscaId: number;
	private _temPrescricao:boolean;
	private _peso: number;
	private _centroTransplanteConfirmado: CentroTransplante;

	private _totalMedula: number;
	private _totalCordao: number;
	private _totalSolicitacaoMedula: number;
	private _totalSolicitacaoCordao: number;

	private _boolAceitaMismatch:boolean ;
	private _aceitaMismatch:string ;
	private _locusMismatch:string ;
	private _arquivoAnticorpo:string;
	private _transplantePrevio:string;


	private _temExameAnticorpo:string;
	private _resultadoExameAnticorpo:string ;

	private _buscaChecklist: BuscaChecklistDTO[];

	private _boolTemExameAnticorpo:boolean;
	private _dataExameAnticorpo:string;

	private _totalHistoricoFase1: number;
	private _totalHistoricoFase2: number;
	private _totalHistoricoFase3: number;

	private _nomeMedicoResponsavel: string;
	private _nomeCentroAvaliador: string;

	private _doadoresPrescritos: string;
	private _quantidadePrescricoes: number;
	private _tipoDoadorComPrescricao: number;

  private _qtdMatchWmdaMedula: number;
	private _qtdMatchWmdaMedulaImportado: number;
	private _qtdMatchWmdaCordao: number;
	private _qtdMatchWmdaCordaoImportado: number;

	public get rmr(): number {
		return this._rmr;
	}

	public set rmr(value: number) {
		this._rmr = value;
	}

	public get listaFase1(): MatchDTO[] {
		return this._listaFase1;
	}

	public set listaFase1(value: MatchDTO[]) {
		this._listaFase1 = value;
	}

	public get listaFase2(): MatchDTO[] {
		return this._listaFase2;
	}

	public set listaFase2(value: MatchDTO[]) {
		this._listaFase2 = value;
	}


	public get listaFase3(): MatchDTO[] {
		return this._listaFase3;
	}

	public set listaFase3(value: MatchDTO[]) {
		this._listaFase3 = value;
	}

	public get listaDisponibilidade(): MatchDTO[] {
		return this._listaDisponibilidade;
	}

	public set listaDisponibilidade(value: MatchDTO[]) {
		this._listaDisponibilidade = value;
	}

	public get buscaId(): number {
		return this._buscaId;
	}
	public get temPrescricao(): boolean {
		return this._temPrescricao;
	}

	public set buscaId(value: number) {
		this._buscaId = value;
	}

	public set temPrescricao(value: boolean) {
		this._temPrescricao = value;
	}

	public get peso(): number {
		return this._peso;
	}

	public set peso(value: number) {
		this._peso = value;
	}

    /**
     * Indica o centro de transplante confirmado para a busca.
     * @return {CentroTransplante}
     */
	public get centroTransplanteConfirmado(): CentroTransplante {
		return this._centroTransplanteConfirmado;
	}

	public set centroTransplanteConfirmado(value: CentroTransplante) {
		this._centroTransplanteConfirmado = value;
	}


    /**
     * Getter abo
     * @return {string}
     */
	public get abo(): string {
		return this._abo;
	}

    /**
     * Setter abo
     * @param {string} value
     */
	public set abo(value: string) {
		this._abo = value;
	}

    /**
     * Getter totalMedula
     * @return {number}
     */
	public get totalMedula(): number {
		return this._totalMedula;
	}

    /**
     * Setter totalMedula
     * @param {number} value
     */
	public set totalMedula(value: number) {
		this._totalMedula = value;
	}

    /**
     * Getter totalCordao
     * @return {number}
     */
	public get totalCordao(): number {
		return this._totalCordao;
	}

    /**
     * Setter totalCordao
     * @param {number} value
     */
	public set totalCordao(value: number) {
		this._totalCordao = value;
	}


    /**
     * Getter aceitaMismatch
     * @return {string }
     */
	public get aceitaMismatch(): string  {
		return this._aceitaMismatch;
	}

    /**
     * Setter aceitaMismatch
     * @param {string } value
     */
	public set aceitaMismatch(value: string ) {
		this._aceitaMismatch = value;
	}

    /**
     * Getter locusMismatch
     * @return {string }
     */
	public get locusMismatch(): string  {
		return this._locusMismatch;
	}

    /**
     * Setter locusMismatch
     * @param {string } value
     */
	public set locusMismatch(value: string ) {
		this._locusMismatch = value;
	}


    /**
     * Getter arquivoAnticorpo
     * @return {string}
     */
	public get arquivoAnticorpo(): string {
		return this._arquivoAnticorpo;
	}

    /**
     * Setter arquivoAnticorpo
     * @param {string} value
     */
	public set arquivoAnticorpo(value: string) {
		this._arquivoAnticorpo = value;
	}


    /**
     * Getter transplantePrevio
     * @return {string}
     */
	public get transplantePrevio(): string {
		return this._transplantePrevio;
	}

    /**
     * Setter transplantePrevio
     * @param {string} value
     */
	public set transplantePrevio(value: string) {
		this._transplantePrevio = value;
	}

    /**
     * Getter boolAceitaMismatch
     * @return {boolean }
     */
	public get boolAceitaMismatch(): boolean  {
		return this._boolAceitaMismatch;
	}

    /**
     * Setter boolAceitaMismatch
     * @param {boolean } value
     */
	public set boolAceitaMismatch(value: boolean ) {
		this._boolAceitaMismatch = value;
	}


    /**
     * Getter temExameAnticorpo
     * @return {string}
     */
	public get temExameAnticorpo(): string {
		return this._temExameAnticorpo;
	}

    /**
     * Setter temExameAnticorpo
     * @param {string} value
     */
	public set temExameAnticorpo(value: string) {
		this._temExameAnticorpo = value;
	}


    /**
     * Getter resultadoExameAnticorpo
     * @return {string }
     */
	public get resultadoExameAnticorpo(): string  {
		return this._resultadoExameAnticorpo;
	}

    /**
     * Setter resultadoExameAnticorpo
     * @param {string } value
     */
	public set resultadoExameAnticorpo(value: string ) {
		this._resultadoExameAnticorpo = value;
	}

	get doadoresPrescritos(): string {
		return this._doadoresPrescritos;
	}

	set doadoresPrescritos(value: string) {
		this._doadoresPrescritos = value;
	}

	/**
     * Getter boolTemExameAnticorpo
     * @return {boolean}
     */
	public get boolTemExameAnticorpo(): boolean {
		return this._boolTemExameAnticorpo;
	}

    /**
     * Setter boolTemExameAnticorpo
     * @param {boolean} value
     */
	public set boolTemExameAnticorpo(value: boolean) {
		this._boolTemExameAnticorpo = value;
	}

	public get buscaChecklist(): BuscaChecklistDTO[] {
		return this._buscaChecklist;
	}

	public set buscaChecklist(value: BuscaChecklistDTO[]) {
		this._buscaChecklist = value;
	}


    /**
     * Getter dataExameAnticorpo
     * @return {string}
     */
	public get dataExameAnticorpo(): string {
		return this._dataExameAnticorpo;
	}

    /**
     * Setter dataExameAnticorpo
     * @param {string} value
     */
	public set dataExameAnticorpo(value: string) {
		this._dataExameAnticorpo = value;
	}

    /**
     * Getter totalHistoricoFase1
     * @return {number}
     */
	public get totalHistoricoFase1(): number {
		return this._totalHistoricoFase1;
	}

    /**
     * Setter totalHistoricoFase1
     * @param {number} value
     */
	public set totalHistoricoFase1(value: number) {
		this._totalHistoricoFase1 = value;
	}

    /**
     * Getter totalHistoricoFase2
     * @return {number}
     */
	public get totalHistoricoFase2(): number {
		return this._totalHistoricoFase2;
	}

    /**
     * Setter totalHistoricoFase2
     * @param {number} value
     */
	public set totalHistoricoFase2(value: number) {
		this._totalHistoricoFase2 = value;
	}

    /**
     * Getter totalHistoricoFase3
     * @return {number}
     */
	public get totalHistoricoFase3(): number {
		return this._totalHistoricoFase3;
	}

    /**
     * Setter totalHistoricoFase3
     * @param {number} value
     */
	public set totalHistoricoFase3(value: number) {
		this._totalHistoricoFase3 = value;
	}

	public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
	}

    /**
     * Getter totalSolicitacaoMedula
     * @return {number}
     */
	public get totalSolicitacaoMedula(): number {
		return this._totalSolicitacaoMedula;
	}

    /**
     * Setter totalSolicitacaoMedula
     * @param {number} value
     */
	public set totalSolicitacaoMedula(value: number) {
		this._totalSolicitacaoMedula = value;
	}

    /**
     * Getter totalSolicitacaoCordao
     * @return {number}
     */
	public get totalSolicitacaoCordao(): number {
		return this._totalSolicitacaoCordao;
	}

    /**
     * Setter totalSolicitacaoCordao
     * @param {number} value
     */
	public set totalSolicitacaoCordao(value: number) {
		this._totalSolicitacaoCordao = value;
	}

    /**
     * Getter nomeMedicoResponsavel
     * @return {string}
     */
	public get nomeMedicoResponsavel(): string {
		return this._nomeMedicoResponsavel;
	}

    /**
     * Setter nomeMedicoResponsavel
     * @param {string} value
     */
	public set nomeMedicoResponsavel(value: string) {
		this._nomeMedicoResponsavel = value;
	}

    /**
     * Getter nomeCentroAvaliador
     * @return {string}
     */
	public get nomeCentroAvaliador(): string {
		return this._nomeCentroAvaliador;
	}

    /**
     * Setter nomeCentroAvaliador
     * @param {string} value
     */
	public set nomeCentroAvaliador(value: string) {
		this._nomeCentroAvaliador = value;
	}

	public get quantidadePrescricoes(): number {
		return this._quantidadePrescricoes;
	}

	public set quantidadePrescricoes(value: number) {
		this._quantidadePrescricoes = value;
	}

	get tipoDoadorComPrescricao(): number {
		return this._tipoDoadorComPrescricao;
	}

	set tipoDoadorComPrescricao(value: number) {
		this._tipoDoadorComPrescricao = value;
  }


    /**
     * Getter qtdMatchWmdaMedula
     * @return {number}
     */
	public get qtdMatchWmdaMedula(): number {
		return this._qtdMatchWmdaMedula;
	}

    /**
     * Setter qtdMatchWmdaMedula
     * @param {number} value
     */
	public set qtdMatchWmdaMedula(value: number) {
		this._qtdMatchWmdaMedula = value;
	}

    /**
     * Getter qtdMatchWmdaMedulaImportado
     * @return {number}
     */
	public get qtdMatchWmdaMedulaImportado(): number {
		return this._qtdMatchWmdaMedulaImportado;
	}

    /**
     * Setter qtdMatchWmdaMedulaImportado
     * @param {number} value
     */
	public set qtdMatchWmdaMedulaImportado(value: number) {
		this._qtdMatchWmdaMedulaImportado = value;
	}


    /**
     * Getter qtdMatchWmdaCordao
     * @return {number}
     */
	public get qtdMatchWmdaCordao(): number {
		return this._qtdMatchWmdaCordao;
	}

    /**
     * Setter qtdMatchWmdaCordao
     * @param {number} value
     */
	public set qtdMatchWmdaCordao(value: number) {
		this._qtdMatchWmdaCordao = value;
	}


    /**
     * Getter qtdMatchWmdaCordaoImportado
     * @return {number}
     */
	public get qtdMatchWmdaCordaoImportado(): number {
		return this._qtdMatchWmdaCordaoImportado;
	}

    /**
     * Setter qtdMatchWmdaCordaoImportado
     * @param {number} value
     */
	public set qtdMatchWmdaCordaoImportado(value: number) {
		this._qtdMatchWmdaCordaoImportado = value;
	}

}
