import { Exame } from './exame.';
import { Laboratorio } from '../../../shared/dominio/laboratorio';
import { Paciente } from '../../paciente';
import { ArquivoExame } from './arquivo.exame';


/**
 * Classe de domínio de exame
 * 
 * @export
 * @class Exame
 * @extends {BaseEntidade}
 */
export class ExamePaciente extends Exame {
    
    /**
     * Laboratorio que fez o exame.
     * 
     * @private
     * @type {Laboratorio}
     * @memberof Exame
     */
    private _laboratorio: Laboratorio;

    /**
     * Flag que identifica se o exame foi feito em um laboratorio fora da rede do redome.
     * 
     * @private
     * @type {Boolean}
     * @memberof Exame
     */
    private _laboratorioParticular: Boolean;

    /**
     * Data da Coleta da Amostra
     * 
     * @private
     * @type {Date}
     * @memberof Exame
     */
    private _dataColetaAmostra: Date;

    /**
     * Data Formata para ser exibida como estã na base
     */
    private _dataColetaAmostraFormatada: string;

    /**
     * 
     * Data da realização do exame pelo paciente
     * @private
     * @type {Date}@memberof Exame
     */
    private _dataExame: Date;

    /**
     * Data Formata para ser exibida como estã na base
     */
    private _dataExameFormatada: string;

    /**
     * 
     * Paciente examinado
     * @private
     * @type {Paciente}@memberof Exame
     */
    private _paciente: Paciente;
    
    /**
     * Lista de arquivos de laudo para upload
     * 
     * @private
     * @type {ArquivoExame[]}@memberof Exame
     */
    private _arquivosExame: ArquivoExame[] = [];

    /**
     * Tipo de amostra CT
     *
     * @private
     * @type {number}
     * @memberof ExamePaciente
     */
    private _tipoAmostra: number;

    public get dataExame(): Date {
        return this._dataExame;
    }

    /**
     * 
     * 
     * @memberof Exame
     */
    public set dataExame(value: Date) {
        this._dataExame = value;
    }

    /**
     * 
     * 
     * @type {Paciente}@memberof Exame
     */
    public get paciente(): Paciente {
        return this._paciente;
    }
    /**
     * 
     * 
     * @memberof Exame
     */
    public set paciente(value: Paciente) {
        this._paciente = value;
    }

	/**
     * 
     * 
     * @type {ArquivoExame[]}@memberof Exame
     */
    public get arquivosExame(): ArquivoExame[] {
		return this._arquivosExame;
	}

	/**
     * 
     * 
     * @memberof Exame
     */
    public set arquivosExame(value: ArquivoExame[]) {
		this._arquivosExame = value;
	}
    
    /**
     * 
     * Retorna uma data formatada.
     * @type {string}
     * @memberof Exame
     */
    public get dataExameFormatada(): string {
		return this._dataExameFormatada;
	}

    /**
     * Define uma data formatada.
     * @memberof Exame
     */
	public set dataExameFormatada(value: string) {
		this._dataExameFormatada = value;
    }
    
    /**
     * Getter laboratorio
     * @return {Laboratorio}
     */
	public get laboratorio(): Laboratorio {
		return this._laboratorio;
	}

    /**
     * Setter laboratorio
     * @param {Laboratorio} value
     */
	public set laboratorio(value: Laboratorio) {
		this._laboratorio = value;
	}

    /**
     * Getter laboratorioParticular
     * @return {Boolean}
     */
	public get laboratorioParticular(): Boolean {
		return this._laboratorioParticular;
	}

    /**
     * Setter laboratorioParticular
     * @param {Boolean} value
     */
	public set laboratorioParticular(value: Boolean) {
		this._laboratorioParticular = value;
	}

    /**
     * Getter dataColetaAmostra
     * @return {Date}
     */
	public get dataColetaAmostra(): Date {
		return this._dataColetaAmostra;
	}

    /**
     * Setter dataColetaAmostra
     * @param {Date} value
     */
	public set dataColetaAmostra(value: Date) {
		this._dataColetaAmostra = value;
	}

    /**
     * Getter dataColetaAmostraFormatada
     * @return {string}
     */
	public get dataColetaAmostraFormatada(): string {
		return this._dataColetaAmostraFormatada;
	}

    /**
     * Setter dataColetaAmostraFormatada
     * @param {string} value
     */
	public set dataColetaAmostraFormatada(value: string) {
		this._dataColetaAmostraFormatada = value;
	}

    public jsonToEntity(res: any): ExamePaciente {
        throw new Error("Method not implemented.");
    }

    protected  get type(): string {
        return 'examePaciente';
    }

    /**
     * Getter tipoAmostra
     * @return {number}
     */
	public get tipoAmostra(): number {
		return this._tipoAmostra;
	}

    /**
     * Setter tipoAmostra
     * @param {number} value
     */
	public set tipoAmostra(value: number) {
		this._tipoAmostra = value;
	}
   
}