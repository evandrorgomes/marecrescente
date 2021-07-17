import { BaseEntidade } from "../shared/base.entidade";
import { Locus } from './cadastro/exame/locus';
import { GenotipoDoadorComparadoDTO } from './genotipo.comparado.doador.dto';
import { GenotipoDTO } from './genotipo.dto';


/**
 * Classe que representa um genotipo, associada ao paciente.
 * @author Fillipe Queiroz
 */
export class GenotipoComparadoDTO extends BaseEntidade {

	private _rmr: number;
	private _nomePaciente: string;
	private _dataNascimento: Date;
	private _idade: string;
	private _sexo: string;
	private _peso: number;
	private _abo: string; 

	private _genotipoPaciente: GenotipoDTO[];
	private _genotiposDoadores: GenotipoDoadorComparadoDTO[]; 
	private _listaLocus: Locus[];

	/**
	 * @returns number
	 */
	public get rmr(): number {
		return this._rmr;
	}
	/**
	 * @param  {number} value
	 */
	public set rmr(value: number) {
		this._rmr = value;
	}
	/**
	 * @returns GenotipoDTO
	 */
	public get genotipoPaciente(): GenotipoDTO[] {
		return this._genotipoPaciente;
	}
	/**
	 * @param  {GenotipoDTO[]} value
	 */
	public set genotipoPaciente(value: GenotipoDTO[]) {
		this._genotipoPaciente = value;
	}

	public get genotiposDoadores(): GenotipoDoadorComparadoDTO[] {
		return this._genotiposDoadores;
	}

	public set genotiposDoadores(value: GenotipoDoadorComparadoDTO[]) {
		this._genotiposDoadores = value;
	}

    /**
     * Getter listaLocus
     * @return {Locus[]}
     */
	public get listaLocus(): Locus[] {
		return this._listaLocus;
	}

    /**
     * Setter listaLocus
     * @param {Locus[]} value
     */
	public set listaLocus(value: Locus[]) {
		this._listaLocus = value;
	}

	private pacientePossuiGenotipoComLocus(locus: Locus, comValor: boolean): boolean {
		if (this.genotipoPaciente) {
			return this.genotipoPaciente.some(genotipo => {
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

	public pacientePossuiGenotipoComLocusEValor(locus: Locus): boolean {
		return this.pacientePossuiGenotipoComLocus(locus, true);
	}
	
	public pacientePossuiGenotipoComLocusESemValor(locus: Locus): boolean {
		return this.pacientePossuiGenotipoComLocus(locus, false);
	}


	public obterValorAleloGenotipoPaciente(codigoLocus: string): GenotipoDTO {
		if (this.genotipoPaciente) {
			return this.genotipoPaciente.filter(genotipo => genotipo.locus == codigoLocus)[0];
		}
	}

    /**
     * Getter dataNascimento
     * @return {Date}
     */
	public get dataNascimento(): Date {
		return this._dataNascimento;
	}

    /**
     * Setter dataNascimento
     * @param {Date} value
     */
	public set dataNascimento(value: Date) {
		this._dataNascimento = value;
	}

    /**
     * Getter sexo
     * @return {string}
     */
	public get sexo(): string {
		return this._sexo;
	}

    /**
     * Setter sexo
     * @param {string} value
     */
	public set sexo(value: string) {
		this._sexo = value;
	}

    /**
     * Getter peso
     * @return {number}
     */
	public get peso(): number {
		return this._peso;
	}

    /**
     * Setter peso
     * @param {number} value
     */
	public set peso(value: number) {
		this._peso = value;
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
     * Getter idade
     * @return {string}
     */
	public get idade(): string {
		return this._idade;
	}

    /**
     * Setter idade
     * @param {string} value
     */
	public set idade(value: string) {
		this._idade = value;
	}

    /**
     * Getter nomePaciente
     * @return {string}
     */
	public get nomePaciente(): string {
		return this._nomePaciente;
	}

    /**
     * Setter nomePaciente
     * @param {string} value
     */
	public set nomePaciente(value: string) {
		this._nomePaciente = value;
	}
	
	

	public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }
}