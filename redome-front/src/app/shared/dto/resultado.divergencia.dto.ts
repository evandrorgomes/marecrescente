import { BaseEntidade } from "../base.entidade";

export class ResultadoDivergenciaDTO extends BaseEntidade {

    private _divergencia: boolean;
	private _idDemaisExames: number[];
	private _idExamesSelecionados: number[];
	private _locus: string;
    private _idBusca: number;

    constructor(divergencia: boolean) {
        super();
        this._divergencia = divergencia;
    }

    /**
     * Getter divergencia
     * @return {boolean}
     */
	public get divergencia(): boolean {
		return this._divergencia;
	}

    /**
     * Getter idDemaisExames
     * @return {number[]}
     */
	public get idDemaisExames(): number[] {
		return this._idDemaisExames;
	}

    /**
     * Setter idDemaisExames
     * @param {number[]} value
     */
	public set idDemaisExames(value: number[]) {
		this._idDemaisExames = value;
	}

    /**
     * Getter idExamesSelecionados
     * @return {number[]}
     */
	public get idExamesSelecionados(): number[] {
		return this._idExamesSelecionados;
	}

    /**
     * Setter idExamesSelecionados
     * @param {number[]} value
     */
	public set idExamesSelecionados(value: number[]) {
		this._idExamesSelecionados = value;
	}

    /**
     * Getter locus
     * @return {string}
     */
	public get locus(): string {
		return this._locus;
	}

    /**
     * Setter locus
     * @param {string} value
     */
	public set locus(value: string) {
		this._locus = value;
	}

    /**
     * Getter idBusca
     * @return {number}
     */
	public get idBusca(): number {
		return this._idBusca;
	}

    /**
     * Setter idBusca
     * @param {number} value
     */
	public set idBusca(value: number) {
		this._idBusca = value;
    }
    
    public jsonToEntity(res: any) {
        throw new Error('Method not implemented.');
    }

}