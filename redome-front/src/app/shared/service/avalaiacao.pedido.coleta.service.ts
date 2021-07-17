import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { BaseService } from '../base.service';
import { HttpClient } from '../httpclient.service';
import { AvaliacaoPedidoColeta } from '../../doador/consulta/coleta/avaliacao/avaliacao.pedido.coleta';


/**
 * Classe de representação de avaliação de pedido de coleta.
 *
 * @export
 * @class AvaliacaoPedidoColeta
 * @author Filipe Paes
 */
@Injectable()
export class AvaliacaoPedidoColetaService  extends BaseService {

    address: string = "avaliacoespedidocoleta";

    constructor(protected http: HttpClient) {
        super(http);
    }

    listarTarefas(pagina, quantidadeRegistros): Promise<any> {
        return this.http.get(`${environment.workupEndpoint}avaliacoespedidoscoleta?pagina=${pagina}&quantidadeRegistros=${quantidadeRegistros}`)
           .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método para gravação de avaliação do pedido de coleta
     * @param idAvaliacaoPedidoColeta - id do pedido a ser gravado.
     * @param avaliacaoPedidoColeta - avaliacao a ser gravada.
     */
    prosseguirPedidoColeta(idAvaliacaoResultadoWorkup:number): Promise<any>{
        return this.http.post(`${environment.workupEndpoint}avaliacoespedidoscoleta/prosseguir`, idAvaliacaoResultadoWorkup)
           .then(this.extrairDado).catch(this.tratarErro);
    }

    naoProsseguirPedidoColeta(idAvaliacaoResultadoWorkup:number, justificativa?: string): Promise<any>{

        let data = new FormData();
        data.append("idAvaliacaoResultadoWorkup", new Blob([idAvaliacaoResultadoWorkup], {
            type: 'application/json'
        }), '');
        if (justificativa) {
            data.append("justificativa", new Blob([justificativa], {
                type: 'application/json'
            }), '');
        }

        return this.http.fileUpload(`${environment.workupEndpoint}/avaliacoespedidoscoleta/naoprosseguir`, data)
           .then(this.extrairDado).catch(this.tratarErro);
    }
}
