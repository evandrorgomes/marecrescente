import { BaseEntidade } from "../../../shared/base.entidade";
import { LocusExame } from './locusexame';
import { Metodologia } from './metodologia';


/**
 * Classe de domínio de exame
 * 
 * @export
 * @class Exame
 * @extends {BaseEntidade}
 */
export abstract class Exame extends BaseEntidade {
    /**
     * 
     * Id do exame
     * @private
     * @type {number}@memberof Exame
     */
    private _id: number;

    /**
     * 
     * Data de quando foi gravado o registro de exame no sistema
     * @private
     * @type {Date}@memberof Exame
     */
    private _dataCriacao: Date;

    /**
     * Lista de metodologias usadas para exame
     * 
     * @private
     * @type {Metodologia[]}@memberof Exame
     */
    private _metodologias: Metodologia[] = [];
    
    /**
     * Lista de relacionamento entre Locus e Exame
     * 
     * @private
     * @type {LocusExame[]}@memberof Exame
     */
    private _locusExames: LocusExame[] = [];

    /**
     * O status atual do exame.
     */
    private _statusExame:number;


    /**
     * 
     * 
     * @type {number}@memberof Exame
     */
    public get id(): number {
        return this._id;
    }
    /**
     * 
     * 
     * @memberof Exame
     */
    public set id(value: number) {
        this._id = value;
    }

    /**
     * 
     * 
     * @type {Date}@memberof Exame
     */
    public get dataCriacao(): Date {
        return this._dataCriacao;
    }

    /**
     * 
     * 
     * @memberof Exame
     */
    public set dataCriacao(value: Date) {
        this._dataCriacao = value;
    }

	/**
     * 
     * 
     * @type {Metodologia[]}@memberof Exame
     */
    public get metodologias(): Metodologia[] {
		return this._metodologias;
	}

	/**
     * 
     * 
     * @memberof Exame
     */
    public set metodologias(value: Metodologia[]) {
		this._metodologias = value;
    }

	/**
     * 
     * 
     * @type {LocusExame[]}@memberof Exame
     */
    public get locusExames(): LocusExame[] {
		return this._locusExames;
    }
    
	/**
     * 
     * 
     * @memberof Exame
     */
    public set locusExames(value: LocusExame[]) {
		this._locusExames = value;
	}
        
    /**
     * Retorna o status atual do exame.
     * @return ID do enum referente ao status do exame.
     */
    public get statusExame(): number {
		return this._statusExame;
	}

	public set statusExame(value: number) {
		this._statusExame = value;
    }
    public jsonToEntity(res: any): Exame {
        throw new Error("Method not implemented.");
    }

    protected abstract get type(): string;

    
}