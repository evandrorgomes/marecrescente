import { BaseEntidade } from '../../../../shared/base.entidade';
import { ConvertUtil } from '../../../../shared/util/convert.util';
import { ArquivoResultadoWorkup } from './arquivo.resultado.workup';
import { PedidoAdicionalWorkup } from './pedido.adicional.workup';


/**
 * Classe para armazenamento de resultados de exames de workup.
 * @author Filipe Paes
 */
export class ResultadoWorkup extends BaseEntidade {

    private _coletaInviavel: boolean;
    private _doadorIndisponivel: boolean;
    private _motivoInviabilidade: string;
    private _idPedidoWorkup: number;
    private _arquivosResultadoWorkup: ArquivoResultadoWorkup[];

    public get coletaInviavel(): boolean {
        return this._coletaInviavel;
    }

    public set coletaInviavel(value: boolean) {
        this._coletaInviavel = value;
    }

    public get doadorIndisponivel(): boolean {
        return this._doadorIndisponivel;
    }

    public set doadorIndisponivel(value: boolean) {
        this._doadorIndisponivel = value;
    }

    public get arquivosResultadoWorkup(): ArquivoResultadoWorkup[] {
        return this._arquivosResultadoWorkup;
    }

    public set arquivosResultadoWorkup(value: ArquivoResultadoWorkup[]) {
        this._arquivosResultadoWorkup = value;
    }

    /**
     * Getter motivoInviabilidade
     * @return {string}
     */
    public get motivoInviabilidade(): string {
      return this._motivoInviabilidade;
    }

    /**
     * Setter motivoInviabilidade
     * @param {string} value
     */
    public set motivoInviabilidade(value: string) {
      this._motivoInviabilidade = value;
    }

    /**
     * Getter idPedidoWorkup
     * @return {number}
     */
    public get idPedidoWorkup(): number {
      return this._idPedidoWorkup;
    }

    /**
     * Setter idPedidoWorkup
     * @param {number} value
     */
    public set idPedidoWorkup(value: number) {
      this._idPedidoWorkup = value;
    }

    public jsonToEntity(res:any) :ResultadoWorkup {

        if (res.arquivosResultadoWorkup) {
            this._arquivosResultadoWorkup = [];
            res.arquivosResultadoWorkup.forEach(arquivo => {
                this._arquivosResultadoWorkup.push(new ArquivoResultadoWorkup().jsonToEntity(arquivo));
            });
        }

        this._idPedidoWorkup = ConvertUtil.parseJsonParaAtributos(res.idPedidoWorkup, new Number());
        this._coletaInviavel = ConvertUtil.parseJsonParaAtributos(res.coletaInviavel, new Boolean());
        this._doadorIndisponivel = ConvertUtil.parseJsonParaAtributos(res.doadorIndisponivel, new Boolean());
        this._motivoInviabilidade = ConvertUtil.parseJsonParaAtributos(res.motivoInviabilidade, new String());

        return this;
	}

}
