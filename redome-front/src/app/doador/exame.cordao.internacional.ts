import { Exame } from '../paciente/cadastro/exame/exame.';
import { CordaoInternacional } from './cordao.internacional';


/**
 * Classe de domínio de exame cordão internacional
 * 
 * @export
 * @class ExameCordaoInternacional
 * @extends {BaseEntidade}
 */
export class ExameCordaoInternacional extends Exame {

    private _cordao: CordaoInternacional;

    /**
     * Getter cordao
     * @return {CordaoInternacional}
     */
    public get cordao(): CordaoInternacional {
        return this._cordao;
    }

    /**
     * Setter cordao
     * @param {CordaoInternacional} value
     */
    public set cordao(value: CordaoInternacional) {
        this._cordao = value;
    }

    public jsonToEntity(res: any): ExameCordaoInternacional {

        if (res.cordao) {
            this.cordao = new CordaoInternacional().jsonToEntity(res.cordao);
        }
        super.jsonToEntity(res);

        return this;
    }

    protected get type(): string {
        return 'exameCordaoInternacional';
    }

}