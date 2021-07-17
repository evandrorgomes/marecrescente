import { Injectable } from "@angular/core";
import { BaseService } from "app/shared/base.service";
import { HttpClient } from "app/shared/httpclient.service";
import { environment } from "environments/environment";
import { Formulario } from 'app/shared/classes/formulario';
import { TiposFormulario } from "../enums/tipo.formulario";
import { PedidoContatoFinalizadoVo } from '../vo/pedido.contato.finalizado.vo';

/**
 * Classe de serviço para interação com a tabela de pedido de enriquecimento.
 * 
 * @author Bruno Sousa
 * @export
 * @class PedidoEnriquecimentoService
 * @extends {BaseService}
 */
@Injectable()
export class PedidoEnriquecimentoService extends BaseService {

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Bruno Sousa
     */
    constructor(http: HttpClient) {
        super(http);
    }

    
    obterPrimeiroPedidoEnriquecimentoDaFila(): Promise<any> {
        return this.http.get(`${environment.endpoint}pedidosenriquecimento/primeiro`)
                                .then(this.extrairDado).catch(this.tratarErro);
    }

    fecharPedidoDeEnriquecimento(pedidoEnriquecimentoId: number): Promise<any>{
        return this.http.put(`${environment.endpoint}pedidosenriquecimento/${pedidoEnriquecimentoId}`)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    fecharPedidoDeEnriquecimentoNaConsultaDoadorNacional(pedidoEnriquecimentoId: number, doadorId: number ) : Promise<any>{
        return this.http.put(`${environment.endpoint}pedidosenriquecimento/${pedidoEnriquecimentoId}/doador/${doadorId}`)
                .then(this.extrairDado).catch(this.tratarErro);
    }

}