import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { BaseService } from '../../../shared/base.service';
import { TarefaBase } from '../../../shared/dominio/tarefa.base';
import { TiposTarefa } from '../../../shared/enums/tipos.tarefa';
import { HttpClient } from '../../../shared/httpclient.service';
import { TarefaService } from '../../../shared/tarefa.service';
import { AgendamentoWorkupDTO } from './agendamento.workup.dto';
import { Disponibilidade } from './disponibilidade';

/**
 * Registra as chamadas ao back-end envolvendo a entidade PedidoWorkup e tarefas relacionadas.
 *
 * @author Pizão
 */
@Injectable()
export class WorkupService extends BaseService {

    constructor(protected http: HttpClient, private tarefaService: TarefaService) {
        super(http);
    }

    /**
     * Formata, para o padrão reconhecido nas funcionalidades do front-end, e recupera
     * a data de workup mais recente.
     * Ex.: Formato - recebido: 2018-01-04, formatado: 2018/01/04.
     *
     * @param res tarefas retornadas do back-end
     * @param tarefas lista de tarefas com as datas formatadas e com a data de coleta preenchida.
     */
    public obterDatasWorkup(res: any, tarefas: TarefaBase[]): TarefaBase[] {
        res.content.forEach(element => {
            if(element.objetoRelacaoEntidade){
                element.objetoRelacaoEntidade.solicitacao.prescricao.dataResultadoWorkup1 = element.objetoRelacaoEntidade.solicitacao.prescricao.dataResultadoWorkup1.replace(/-/g, '\/');
                element.objetoRelacaoEntidade.solicitacao.prescricao.dataResultadoWorkup2 = element.objetoRelacaoEntidade.solicitacao.prescricao.dataResultadoWorkup2.replace(/-/g, '\/');
            }
        });
        tarefas = res.content;

        tarefas.forEach(tarefa => {
            if(tarefa.objetoRelacaoEntidade){
                let dataResultadoWorkup1: Date = new Date(tarefa.objetoRelacaoEntidade.solicitacao.prescricao.dataResultadoWorkup1);
                let dataResultadoWorkup2: Date = new Date(tarefa.objetoRelacaoEntidade.solicitacao.prescricao.dataResultadoWorkup2);

                tarefa.objetoRelacaoEntidade.dataColeta = dataResultadoWorkup1 < dataResultadoWorkup2 ? dataResultadoWorkup1 : dataResultadoWorkup2;
            }
        })
        return tarefas;
    }

    public listarTarefasComPendencias(tipoTarefa: TiposTarefa, perfilId: number,
                pagina: number, quantidadeRegistro: number): Promise<any>{
        return this.tarefaService.listarTarefasPaginadas(
            tipoTarefa.id,
            perfilId, null,
            pagina - 1, quantidadeRegistro, true);
    }

    public cancelarWorkupPeloCT(pedidoId: number): Promise<any> {
        return this.http.put(environment.endpoint + 'pedidosworkup/' + pedidoId + '/cancelar/ct')
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Obtém o pedido de workup.
     * @param  {number} idPedido
     * @returns Promise
     */
    public obterPedidoWorkup(idPedido: number): Promise<any> {
        return this.http.get(environment.endpoint + "pedidosworkup/" + idPedido)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método para enviar a disponibilidade para o CT.
     * @param  {number} idPedido
     * @param  {string} disponibilidade
     */
    public incluirDisponibilidade(idPedido: number, disponibilidade: string) {
        return this.http.post(environment.endpoint + "pedidosworkup/" + idPedido + '/disponibilidade', disponibilidade)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Obtém a disponibilidade informada pelo pelo CT, a partir
     * do ID do pedido informado.
     *
     * @param pedidoId
     */
    public obterDisponibilidade(pedidoId: number): Promise<any> {
        return this.http.get(environment.endpoint + 'pedidosworkup/' + pedidoId + '/disponibilidade')
            .then(this.extrairDado).catch(this.tratarErro);
    }


    /**
     * Adicionar uma nova sugestão de datas para realização do workup.
     * Utilizado pelo médico do CT.
     *
     * @param pedidoId
     */
    public incluirDisponibilidadeCT(pedidoId: number, disponibilidade: Disponibilidade): Promise<any> {
        let data = JSON.stringify(disponibilidade);
        return this.http.post(environment.endpoint + 'pedidosworkup/' + pedidoId + '/disponibilidade/ct', data)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Busca a lista de motivos de cancelamento do pedido de workup.
     */
    public listarMotivosCancelamento() {
        return this.http.get(environment.endpoint + "pedidosworkup/motivoscancelamentoworkup")
            .then(super.extrairDado).catch(super.tratarErro);
    }
    /**
     * Serviço para agendar um pedido de workup.
     * @param  {number} pedidoId
     * @param  {AgendamentoWorkupDTO} agendamentoWorkupDTO
     */
    public agendar(pedidoId: number, agendamentoWorkupDTO: AgendamentoWorkupDTO) {
        let data = JSON.stringify(agendamentoWorkupDTO);
        return this.http.put(environment.endpoint + "pedidosworkup/" + pedidoId + '/agendar', data)
            .then(this.extrairDado).catch(this.tratarErro);
    }
    /**
     * Cancelamento do pedido de workup
     * @param  {number} pedidoId
     * @param  {number} motivoId
     * @returns Promise
     */
    public cancelarPedidoWorkup(pedidoId: number, motivoId: number): Promise<any> {
        let params = "?idMotivoCancelamento=" + motivoId;
        return this.http.put(environment.endpoint + 'pedidosworkup/' + pedidoId + '/cancelar' + params)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Atualiza o status do pedido de workup.
     * @param  {number} pedidoId
     * @param  {number} motivoId
     * @returns Promise
     */
    public atualizarPedidoWorkup(idPedido: number): Promise<any> {
        return this.http.put(environment.endpoint + 'pedidosworkup/' + idPedido + '/atribuir')
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Listar dados do ct.
     * @param  {number} pedidoId
     * @returns Promise
     */
    public listarDadosCT(idPedido: number): Promise<any> {
        return this.http.get(environment.endpoint + 'pedidosworkup/' + idPedido + '/dadosct')
            .then(this.extrairDado).catch(this.tratarErro);
    }
    /**
     * Finaliza o pedido de workup
     * @param  {number} idPedido
     * @returns Promise
     */
    public finalizarPedidoWorkup(idPedido: number): Promise<any> {
        return this.http.put(environment.endpoint + 'pedidosworkup/' + idPedido + '/finalizar')
            .then(this.extrairDado).catch(this.tratarErro);
    }

    public listarSolicitacoesWorkup(pagina: number, quantidadeRegistro: number, idCentroTransplante?: number, idFuncaoCentro?: number): Promise<any>{
        let params: string = "pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistro;
        if (idCentroTransplante) {
            params += "&idCentroTransplante=" + idCentroTransplante + "&idFuncaoCentro=" + idFuncaoCentro;
        }
        return this.http.get(environment.workupEndpoint + 'workup/solicitacoes?'+params)
                        .then(super.extrairDado).catch(super.tratarErro);
    }


    public listarTarefasWorkup(pagina: number, quantidadeRegistro: number, idCentroTransplante?: number, idFuncaoCentro?: number): Promise<any>{
        let params: string = "pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistro;
        if (idCentroTransplante) {
            params += "&idCentroTransplante=" + idCentroTransplante + "&idFuncaoCentro=" + idFuncaoCentro;
        }
        return this.http.get(environment.workupEndpoint + 'workup/tarefas?'+params)
                        .then(super.extrairDado).catch(super.tratarErro);
    }

    public listarWorkupsInternacionaisDisponiveis(pagina: number, quantidadeRegistro: number): Promise<any>{
        return this.http.get(
            environment.endpoint + "pedidosworkup/disponiveis/internacional?pagina=" + pagina +
                "&quantidadeRegistros=" + quantidadeRegistro)
                        .then(super.extrairDado).catch(super.tratarErro);
    }


    public listarWorkupsInternacionais(pagina: number, quantidadeRegistro: number): Promise<any>{
        return this.http.get(
            environment.endpoint + "pedidosworkup/atribuidos/internacional?pagina=" + pagina +
                "&quantidadeRegistros=" + quantidadeRegistro)
                        .then(super.extrairDado).catch(super.tratarErro);
    }

    public listarDisponibilidades(idCentroTransplante, pagina, quantidadeRegistro: number): Promise<any> {
        return this.http.get(
            environment.endpoint + "pedidosworkup/disponibilidades?idCentroTransplante=" + idCentroTransplante
                + "&pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistro)
                        .then(super.extrairDado).catch(super.tratarErro);
    }

}
