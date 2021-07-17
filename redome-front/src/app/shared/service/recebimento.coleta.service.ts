import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { BaseService } from '../base.service';
import { HttpClient } from '../httpclient.service';
import { RecebimentoColeta } from '../dominio/recebimento.coleta';


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
     * Método para salvar o recebimento de coleta.
     * @param recebimentoColeta - entidade com os dados a serem atualizados.
     */
    public salvarRecebimentoColeta(recebimentoColeta: RecebimentoColeta): Promise<any> {
        let data = JSON.stringify(recebimentoColeta);
        return this.http.put(`${environment.workupEndpoint}recebimentocoleta/`, data)
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }


}
