import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { BaseService } from '../../../shared/base.service';
import { TiposTarefa } from '../../../shared/enums/tipos.tarefa';
import { HttpClient } from '../../../shared/httpclient.service';
import { TarefaService } from '../../../shared/tarefa.service';
import { Disponibilidade } from '../workup/disponibilidade';
import { PedidoColeta } from './pedido.coleta';
import { DataUtil } from 'app/shared/util/data.util';

/**
 * Registra as chamadas ao back-end envolvendo a entidade PedidoColeta e tarefas relacionadas.
 *
 * @author Bruno Sousa
 * @class PedidoColetaService
 * @extends {BaseService}
 */
@Injectable()
export class PedidoColetaService extends BaseService {

    constructor(protected http: HttpClient, private tarefaService: TarefaService) {
        super(http);
    }

    public listarTarefasPedidoColeta(tipoTarefa: TiposTarefa,
                pagina: number, quantidadeRegistro: number): Promise<any>{
        return this.tarefaService.listarTarefasPaginadas(
            tipoTarefa.id,
            null, null,
            pagina - 1, quantidadeRegistro, true);
    }

    /**
     * Obtém a disponilidade informado pelo analista de workup.
     * @param  {number} idPedido
     * @returns Promise
     */
    public obterDisponibilidade(idPedidoColeta: number): Promise<any> {
        return this.http.get(environment.endpoint + "pedidoscoleta/" + idPedidoColeta + "/disponibilidade")
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Adicionar uma nova sugestão de datas para realização da coleta.
     * Utilizado pelo médico do CT.
     *
     * @param pedidoColetaId
     */
    public incluirDisponibilidadeCT(pedidoColetaId: number, disponibilidade: Disponibilidade): Promise<any> {
        let data = JSON.stringify(disponibilidade);
        return this.http.post(environment.endpoint + 'pedidoscoleta/' + pedidoColetaId + '/disponibilidade/ct', data)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Cancela o pedido de coleta com o ID informado.
     *
     * @return observer contendo o resultado da chamada ao back-end.
     */
    public cancelarColetaPeloCT(pedidoColetaId: number): Promise<any> {
        return this.http.put(environment.endpoint + 'pedidoscoleta/' + pedidoColetaId + '/cancelar/ct')
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método para enviar a disponibilidade para o CT.
     * @param  {number} idPedido
     * @param  {string} disponibilidade
     */
    public incluirDisponibilidade(idPedido: number, disponibilidade: string): Promise<any> {
        return this.http.post(environment.endpoint + "pedidoscoleta/" + idPedido + '/disponibilidade', disponibilidade)
            .then(this.extrairDado).catch(this.tratarErro);
    }

     /**
     * Alterar data de coleta por pedido de logistica.
     * @param  {number} idPedidoLogistica
     * @param  {date} dataColeta
     */
    public alterarDataColeta(idPedidoLogistica: number, dataColeta: Date): Promise<any> {
        return this.http.put(environment.endpoint + `pedidoscoleta/atualizardatacoleta?idlogistica=${idPedidoLogistica}`, dataColeta)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Listar dados do ct.
     * @param  {number} pedidoId
     * @returns Promise
     */
    public listarDadosCT(idPedido: number): Promise<any> {
        return this.http.get(environment.endpoint + 'pedidoscoleta/' + idPedido + '/dadosct')
            .then(this.extrairDado).catch(this.tratarErro);
    }

    public listarDisponibilidades(idCentroTransplante, pagina, quantidadeRegistro: number): Promise<any> {
        return this.http.get(
            environment.endpoint + "pedidoscoleta/disponibilidades?idCentroTransplante=" + idCentroTransplante
                + "&pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistro)
                        .then(super.extrairDado).catch(super.tratarErro);
    }

    public listarTarefasAgendadasInternacional(pagina: number, quantidadeRegistro: number): Promise<any> {
        return this.http.get(
            environment.endpoint + "pedidoscoleta/tarefas/agendadas/internacional?pagina=" + pagina
                + "&quantidadeRegistros=" + quantidadeRegistro)
                    .then(super.extrairDado).catch(super.tratarErro);
    }

    public listarTarefasAgendadas(pagina: number, quantidadeRegistro: number): Promise<any> {
        return this.http.get(
            environment.endpoint + "pedidoscoleta/tarefas/agendadas?pagina=" + pagina
                + "&quantidadeRegistros=" + quantidadeRegistro)
                    .then(super.extrairDado).catch(super.tratarErro);
    }

    /* ############################# NOVOS SERVIÇOS ############################# */

    /**
     * Método para enviar o agendamento do pedido de coleta.
     * @param  {number} idPedido
     * @param {PedidoColeta} pedidoColeta
     * @returns Promise
     */
    public agendarColeta(idPedidoColeta: number, pedidoColeta: PedidoColeta): Promise<any> {
      let data =  JSON.stringify(pedidoColeta);
      return this.http.put(`${environment.workupEndpoint}pedidocoleta/${idPedidoColeta}`, data)
          .then(this.extrairDado)
          .catch(this.tratarErro);
    }

    /**
     * Obtém o pedido de coleta.
     * @param  {number} idPedidoColeta
     * @returns Promise
     */
    public obterPedidoColeta(idPedidoColeta: Number): Promise<any> {
      return this.http.get(`${environment.workupEndpoint}pedidocoleta/${idPedidoColeta}`)
          .then(this.extrairDado)
          .catch(this.tratarErro);
  }

  /* ############################################################################ */

}
