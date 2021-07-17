import { forEach } from '@angular/router/src/utils/collection';
import { LocusExame } from './../paciente/cadastro/exame/locusexame';
import { DoadorInternacional } from './doador.internacional';
import { Exame } from '../paciente/cadastro/exame/exame.';
import { ConvertUtil } from '../shared/util/convert.util';
import { Metodologia } from '../paciente/cadastro/exame/metodologia';
import { ExameDoador } from 'app/shared/classes/exame.doador';


/**
 * Classe de domínio de exame doador internacional
 * 
 * @export
 * @class ExameDoadorInternacional
 * @extends {BaseEntidade}
 */
export class ExameDoadorInternacional extends ExameDoador {
    

    /**
     * Getter doador
     * @return {DoadorInternacional}
     */
	public get doador(): DoadorInternacional {
		return <DoadorInternacional> this._doador;
	}

    /**
     * Setter doador
     * @param {DoadorInternacional} value
     */
	public set doador(value: DoadorInternacional) {
		this._doador = value;
	}


    public jsonToEntity(res: any): ExameDoadorInternacional {
        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
        if (res.doador) {
            this.doador = new DoadorInternacional().jsonToEntity(res.doador);
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
                //let locusE: LocusExame = new LocusExame(l.id.locus.codigo, l.primeiroAlelo, l.segundoAlelo);
                //this.locusExames.push(locusE);
            })
        }
        this.statusExame = ConvertUtil.parseJsonParaAtributos(res.statusExame, new Number());
        return this;
    }

    protected get type(): string {
        return 'exameDoadorInternacional';
    }
}