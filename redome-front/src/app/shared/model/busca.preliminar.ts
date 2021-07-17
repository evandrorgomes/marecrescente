import { forEach } from '@angular/router/src/utils/collection';
import { BaseEntidade } from "../base.entidade";
import { LocusExamePreliminar } from "app/shared/model/locus.exame.preliminar";
import { ConvertUtil } from "app/shared/util/convert.util";

export class BuscaPreliminar extends BaseEntidade {

    private _id: number;
    private _nomePaciente: string;
    private _dataNascimento: Date;
    private _abo: string;
    private _peso: number;
    private _locusExamePreliminar: LocusExamePreliminar[];

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
     * Getter locusExamePreliminar
     * @return {LocusExamePreliminar[]}
     */
	public get locusExamePreliminar(): LocusExamePreliminar[] {
		return this._locusExamePreliminar;
	}

    /**
     * Setter locusExamePreliminar
     * @param {LocusExamePreliminar[]} value
     */
	public set locusExamePreliminar(value: LocusExamePreliminar[]) {
		this._locusExamePreliminar = value;
	}


    public jsonToEntity(res: any): BuscaPreliminar {

        if (res.locusExamePreliminar) {
            this.locusExamePreliminar  = [];
            res.locusExamePreliminar.forEach(locusExame => {
                this.locusExamePreliminar.push(new LocusExamePreliminar().jsonToEntity(locusExame));
            });            
        }

        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.nomePaciente = ConvertUtil.parseJsonParaAtributos(res.nomePaciente, new String());
        this.dataNascimento = ConvertUtil.parseJsonParaAtributos(res.dataNascimento, new Date());
        this.abo = ConvertUtil.parseJsonParaAtributos(res.abo, new String());
        this.peso = ConvertUtil.parseJsonParaAtributos(res.peso, new Number());
        
        return this;
    }



}