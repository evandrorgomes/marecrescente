import { Injectable } from "@angular/core";
import { BaseService } from "app/shared/base.service";
import { HttpClient } from "app/shared/httpclient.service";
import { environment } from "environments/environment";
import { Formulario } from 'app/shared/classes/formulario';
import { TiposFormulario } from "../enums/tipo.formulario";
import { PedidoContatoFinalizadoVo } from '../vo/pedido.contato.finalizado.vo';
import { StatusSms, IStatusSms } from "../enums/status.sms";
import { DataUtil } from "../util/data.util";
import { DateTypeFormats } from '../util/date/date.type.formats';
import { DateMoment } from '../util/date/date.moment';

/**
 * Classe de serviço para interação com a tabela de pedido de contato sms.
 * 
 * @author Bruno Sousa
 * @export
 * @class PedidoContatoSmsService
 * @extends {BaseService}
 */
@Injectable()
export class PedidoContatoSmsService extends BaseService {

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Bruno Sousa
     */
    constructor(http: HttpClient) {
        super(http);
    }

    
    listarSmsEnviados(dmr: number, dataInicial: Date, dataFinal: Date, status: IStatusSms, pagina: number, quantidadeRegistros: number): Promise<any> {
        let param: string = "";
        let separador: string = "?"
        if (dmr != null && dmr != undefined) {
            param += separador + "dmr=" + dmr;
            separador = "&";
        }
        if (dataInicial) {
            param += separador + "dataInicial=" + DateMoment.getInstance().formatWithPattern(dataInicial, "DDMMYYYY");
            separador = "&";
        }
        if (dataFinal) {
            param += separador + "dataFinal=" +  DateMoment.getInstance().formatWithPattern(dataFinal, "DDMMYYYY");
            separador = "&";
        }
        if (status) {
            param += separador + "status=" + status.id;
            separador = "&";
        }

        param += separador + 'pagina=' + pagina;
        separador = "&";
        param += separador + 'quantidadeRegistros=' + quantidadeRegistros;

        return this.http.get(`${environment.endpoint}pedidoscontatosms${param}`)
                                .then(this.extrairDado).catch(this.tratarErro);
    }


}