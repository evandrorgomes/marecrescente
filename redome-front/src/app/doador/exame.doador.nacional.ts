import { Exame } from '../paciente/cadastro/exame/exame.';
import { Laboratorio } from '../shared/dominio/laboratorio';
import { DoadorNacional } from './doador.nacional';
import { ExameDoador } from 'app/shared/classes/exame.doador';
import { ConvertUtil } from 'app/shared/util/convert.util';
import { Metodologia } from 'app/paciente/cadastro/exame/metodologia';
import { LocusExame } from 'app/paciente/cadastro/exame/locusexame';


/**
 * Classe de domínio de exame doador nacional
 * 
 * @export
 * @class ExameDoadorNacional
 * @extends {BaseEntidade}
 */
export class ExameDoadorNacional extends ExameDoador {
    
    /**
     * Laboratorio que fez o exame.
     * 
     * @private
     * @type {Laboratorio}
     * @memberof Exame
     */
    private _laboratorio: Laboratorio;

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
     * Getter dataExame
     * @return {Date}
     */
	public get dataExame(): Date {
		return this._dataExame;
	}

    /**
     * Setter dataExame
     * @param {Date} value
     */
	public set dataExame(value: Date) {
		this._dataExame = value;
	}

    /**
     * Getter dataExameFormatada
     * @return {string}
     */
	public get dataExameFormatada(): string {
		return this._dataExameFormatada;
	}

    /**
     * Setter dataExameFormatada
     * @param {string} value
     */
	public set dataExameFormatada(value: string) {
		this._dataExameFormatada = value;
	}

    /**
     * Getter doador
     * @return {DoadorNacional}
     */
	public get doador(): DoadorNacional {
		return <DoadorNacional> this._doador;
	}

    /**
     * Setter doador
     * @param {DoadorNacional} value
     */
	public set doador(value: DoadorNacional) {
		this._doador = value;
	}
    
    public jsonToEntity(res: any): ExameDoadorNacional {
        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
        if (res.doador) {
            this.doador = new DoadorNacional().jsonToEntity(res.doador);
        }

        if(res.metodologias){
            this.metodologias = [];
            res.metodologias.forEach(m=> {
                let metodologia: Metodologia = new Metodologia().jsonToEntity(m);
                this.metodologias.push(metodologia);
            });
        }
        if(res.locusExames){
            this.locusExames = [];
            res.locusExames.forEach(l=>{
                this.locusExames.push(new LocusExame().jsonToEntity(l));                
            })
        }
        if (res.laboratorio) {
            this.laboratorio = new Laboratorio().jsonToEntity(res.laboratorio);
        }

        this.dataExame = ConvertUtil.parseJsonParaAtributos(res.dataExame, new Date());
        this.statusExame = ConvertUtil.parseJsonParaAtributos(res.statusExame, new Number());
        return this;
    }

    protected get type(): string {
        return 'exameDoadorNacional';
    }

}
