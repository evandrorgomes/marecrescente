import { BuscaPreliminar } from 'app/shared/model/busca.preliminar';
import { BaseEntidade } from "app/shared/base.entidade";
import { MatchDTO } from "app/shared/component/listamatch/match.dto";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * Classe que representa o dto com a listade matchs preliminares para uma busca preliniar de um paciente
 * 
 * @author Bruno Sousa
 * @export
 * @class AnaliseMatchPreliminarDTO
 */
export class AnaliseMatchPreliminarDTO extends BaseEntidade {

    private _buscaPreliminar: BuscaPreliminar;
    private _listaFase1: MatchDTO[];
    private _listaFase2: MatchDTO[];
    private _listaFase3: MatchDTO[];

    private _totalMedula: number;
    private _totalCordao: number;
    
    /**
     * Getter buscaPreliniar
     * @return {number}
     */
	public get buscaPreliminar(): BuscaPreliminar {
		return this._buscaPreliminar;
	}

    /**
     * Setter buscaPreliniar
     * @param {number} value
     */
	public set buscaPreliminar(value: BuscaPreliminar) {
		this._buscaPreliminar = value;
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

	public jsonToEntity(res: any): AnaliseMatchPreliminarDTO {
        
        if (res.buscaPreliminar) {
            this.buscaPreliminar = new BuscaPreliminar().jsonToEntity(res.buscaPreliminar);
        }
        
        if (res.listaFase1) {
            this.listaFase1 = [];
            res.listaFase1.forEach(matchDTO => {
                this.listaFase1.push(new MatchDTO().jsonToEntity(matchDTO));
            });
        }

        if (res.listaFase2) {
            this.listaFase2 = [];
            res.listaFase2.forEach(matchDTO => {
                this.listaFase2.push(new MatchDTO().jsonToEntity(matchDTO));
            });
        }
        if (res.listaFase3) {
            this.listaFase3 = [];
            res.listaFase3.forEach(matchDTO => {
                this.listaFase3.push(new MatchDTO().jsonToEntity(matchDTO));
            });
        }
    
        this.totalMedula = ConvertUtil.parseJsonParaAtributos(res.totalMedula, new Number());
        this.totalCordao = ConvertUtil.parseJsonParaAtributos(res.totalCordao, new Number());

        return this;
    }
}