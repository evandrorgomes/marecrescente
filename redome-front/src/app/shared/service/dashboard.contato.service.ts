import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import 'rxjs/add/operator/map';
import { BaseService } from '../base.service';
import { HttpClient } from '../httpclient.service';
import { DateMoment } from 'app/shared/util/date/date.moment';

/**
 * Classe de Serviço utilizada para acessar os serviços REST do dashboard de contato
 * 
 * @author Brunosousa
 */
@Injectable()
export class DashboardContatoService extends BaseService{

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo.
     */
    constructor(http: HttpClient) {
        super(http);
    }

    /**
     * Lista todos os bancos de sangue de cordão disponíveis
     * base do Redome.
     * @return json com os perfis presentes na base.
     */
    obterTotaisContato(dataInicial: Date, dataFinal: Date): Promise<any> {
        let param: string = "";
        let separador: string = "?"        
        if (dataInicial) {
            param += separador + "dataInicio=" + DateMoment.getInstance().formatWithPattern(dataInicial, "DDMMYYYY");
            separador = "&";
        }
        if (dataFinal) {
            param += separador + "dataFinal=" +  DateMoment.getInstance().formatWithPattern(dataFinal, "DDMMYYYY");
            separador = "&";
        }
        return this .http.get(`${environment.endpoint}dashboard/contatos${param}`)
                    .then(this.extrairDado).catch(this.tratarErro);
    }

}