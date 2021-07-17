import { HttpClient } from '../../../shared/httpclient.service';
import { BaseService } from '../../../shared/base.service';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { RecebimentoColeta } from 'app/shared/dominio/recebimento.coleta';


/**
 * Registra as chamadas ao back-end envolvendo a RecebimentoColeta.
 *
 * @author Filipe Paes
 */
@Injectable()
export class RecebimentoColetaService extends BaseService {

    constructor(protected http: HttpClient) {
        super(http);
    }

    /**
     * Método para obter recebimento de coleta por id.
     * @param idRecebimento - id do recebimento a ser carregado.
     */
    public obterRecebimentoPorId(idRecebimento:number):Promise<any>{
        return this.http.get(environment.endpoint + "recebimentodecoletas/" + idRecebimento)
        .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método para atualizar o recebimento de coleta.
     * @param recebimentoColeta - entidade com os dados a serem atualizados.
     */
    public atualizarRecebimentoColeta(recebimentoColeta:RecebimentoColeta): Promise<any> {
        let data = JSON.stringify(recebimentoColeta);
        return this.http.put(environment.endpoint + "recebimentodecoletas/" + recebimentoColeta.id, data)
                        .then(this.extrairDado).catch(this.tratarErro);
    }


}
