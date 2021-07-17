import { Exame } from './exame.';
import { BaseEntidade } from '../../../shared/base.entidade';
import { Locus } from './locus';
import { ExamePaciente } from './exame.paciente';
import { ExameDoadorNacional } from '../../../doador/exame.doador.nacional';
import { ExameDoadorInternacional } from 'app/doador/exame.doador.internacional';

/**
 * Classe de chave primária para locus exame
 * 
 * @export
 * @class LocusExamePk
 * @extends {BaseEntidade}
 */
export class LocusExamePk extends BaseEntidade {

    /**
     * Atributo de locus para chave primaria
     * 
     * @private
     * @type {Locus}@memberof LocusExamePk
     */
    private _locus: Locus;

    /**
     * Atributo de exame para chave primaria
     * 
     * @private
     * @type {Exame}@memberof LocusExamePk
     */
    private _exame: Exame;



	/**
     * 
     * 
     * @type {Locus}@memberof LocusExamePk
     */
    public get locus(): Locus {
        return this._locus;
    }

	/**
     * 
     * 
     * @memberof LocusExamePk
     */
    public set locus(value: Locus) {
        this._locus = value;
    }

	/**
     * 
     * 
     * @type {Exame}@memberof LocusExamePk
     */
    public get exame(): Exame {
        return this._exame;
    }

	/**
     * 
     * 
     * @memberof LocusExamePk
     */
    public set exame(value: Exame) {
        this._exame = value;
    }



    public jsonToEntity(res: any) :this{
        this.locus = new Locus().jsonToEntity(res.locus);
        if(res.exame && res.exame.type){
            if (res.exame.type == "examePaciente") {
                this.exame = new ExamePaciente().jsonToEntity(res.exame);
            }
            else if (res.exame.type == "exameDoadorNacional") {
                this.exame = new ExameDoadorNacional().jsonToEntity(res.exame);
            }
            else if (res.exame.type == "exameDoadorInternacional") {
                this.exame = new ExameDoadorInternacional().jsonToEntity(res.exame);
            }
            else {
                new Error("Method not implemented.");
            }
        }
        return this;
    }

}