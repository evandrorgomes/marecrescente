import { Injectable } from "@angular/core";
import { BaseService } from "app/shared/base.service";
import { HttpClient } from "app/shared/httpclient.service";
import { environment } from "environments/environment";
import { Formulario } from 'app/shared/classes/formulario';
import { TiposFormulario } from "../enums/tipo.formulario";
import { PedidoContatoFinalizadoVo } from '../vo/pedido.contato.finalizado.vo';

/**
 * Classe de serviço para interação com a tabela de pedido de contato.
 * 
 * @author Bruno Sousa
 * @export
 * @class PedidoContatoService
 * @extends {BaseService}
 */
@Injectable()
export class PedidoContatoService extends BaseService {

/**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Bruno Sousa
     */
    constructor(http: HttpClient) {
        super(http);
    }

    obterPrimeiroPedidoContatoDaFila(tipoTarefaId: number): Promise<any> {
        return this.http.get(environment.endpoint + "pedidoscontato/primeiro?tipoTarefaId=" + tipoTarefaId)
                                .then(this.extrairDado).catch(this.tratarErro);
    }

    obterPedidoContatoPoridTarefa(tarefaId: number, tentativaId: number): Promise<any> {
        return this.http.get(environment.endpoint + "pedidoscontato/tarefa?tarefaId=" + tarefaId + '&tentativaId=' + tentativaId)
                                .then(this.extrairDado).catch(this.tratarErro);
    }

    finalizarContato(idPedidoContato: number, pedidoContatoFinalizadoVo: PedidoContatoFinalizadoVo): Promise<any> {
        let data = JSON.stringify(pedidoContatoFinalizadoVo)
        /* let data = {
            contactado: pedidoContatoFinalizadoVo.contactado,
            contactadoPorTerceiro: pedidoContatoFinalizadoVo.contactadoPorTerceiro,
            acao: pedidoContatoFinalizadoVo.acao.id
        } */
        return this.http.post(environment.endpoint + "pedidoscontato/" + idPedidoContato, data )
                                .then(this.extrairDado).catch(this.tratarErro);
    }

}