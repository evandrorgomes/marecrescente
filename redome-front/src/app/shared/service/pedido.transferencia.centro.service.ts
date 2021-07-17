import { Injectable } from "@angular/core";
import { BaseService } from "app/shared/base.service";
import { HttpClient } from "app/shared/httpclient.service";
import { environment } from "environments/environment";

/**
 * Classe de serviço para interação com a tabela de pedido de transferencia de centro.
 * 
 * @author Bruno Sousa
 * @export
 * @class LaboratorioService
 * @extends {BaseService}
 */
@Injectable()
export class PedidoTransferenciaCentroService extends BaseService {

/**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Bruno Sousa
     */
    constructor(http: HttpClient) {
        super(http);
    }

    listarTarefas(atribuidosAMin: boolean, idParceiro, pagina, quantidadeRegistros: number): Promise<any> {
        let parametros: string = "?atribuidosAMin=" + (atribuidosAMin ? 1 : 0);
        parametros += "&idParceiro=" + idParceiro;
        parametros += "&pagina=" + pagina;
        parametros += "&quantidadeRegistros=" + quantidadeRegistros;

        return this.http.get(environment.endpoint + "pedidostransferenciacentro/tarefas" + parametros)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    obterTransferencia(idPedidoTransferenciaCentro: number): Promise<any> {
        return this.http.get(environment.endpoint + "pedidostransferenciacentro/" + idPedidoTransferenciaCentro)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    recusarPedidoTransferencia(idPedidoTransferenciaCentro: number, justificativa: string): Promise<any> {
        return this.http.put(environment.endpoint + "pedidostransferenciacentro/" + idPedidoTransferenciaCentro + "/recusar", justificativa)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    aceitarPedidoTransferencia(idPedidoTransferenciaCentro: number): Promise<any> {
        return this.http.put(environment.endpoint + "pedidostransferenciacentro/" + idPedidoTransferenciaCentro + "/aceitar")
            .then(this.extrairDado).catch(this.tratarErro);
    }


}