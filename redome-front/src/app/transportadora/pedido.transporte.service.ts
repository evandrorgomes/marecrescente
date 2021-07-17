import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { FileItem } from 'ng2-file-upload';
import { BaseService } from '../shared/base.service';
import { HttpClient } from '../shared/httpclient.service';
import { MessageBox } from '../shared/modal/message.box';
import { ConfirmacaoTransporteDTO } from './tarefas/agendartransportematerial/confirmacao.transporte.dto';


/**
 * Classe utilitária que realiza as solicitações ajax no servidor REST
 * @author Fillipe Queiroz
 */
@Injectable()
export class PedidoTransporteService extends BaseService {

    constructor(http: HttpClient, messageBox: MessageBox) {
        super(http, messageBox);
    }

    /**
     * Obtém um pedido de transporte pelo id.
     *
     * @param id ID do pedido a ser pesquisado.
     */
    public obterPedidoTransporte(id: number): Promise<any> {
        return this.http.get(`${environment.courierEndpoint}pedidotransporte/${id}`)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Busca a lista de pedidos de transporte jah tratados pela transportadora aguardando uma ação de outro.
     *
     */
    public listarPedidosTransporte(pagina: number, quantidadeRegistro: number): Promise<any> {
        return this.http.get(`${environment.courierEndpoint}pedidostransporte/emandamento?pagina=${pagina}&quantidadeRegistros=${quantidadeRegistro}`)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Método que confirma o agendamento de transporte de material.
     * As informações de voo são opiconais, por isso, pode enviar sem os dados e no back deverá fechar
     * a tarefa atual e criar para o analista de logistica.
     * @param  {number} id
     * @param  {ConfirmacaoTransporteDTO} confirmacaoTransporteDTO
     * @returns Promise
     */
    public confirmarAgendamentoTransporteMaterial(id: number, confirmacaoTransporteDTO: ConfirmacaoTransporteDTO): Promise<any> {

        let data = JSON.stringify(confirmacaoTransporteDTO);
        return this.http.post(`${environment.courierEndpoint}pedidotransporte/${id}/confirmaragendamento`, data)
            .then(super.extrairDado).catch(super.tratarErro);

    }

    /**
     * Realiza o download do arquivo associado ao ID do arquivo exame informado.
     *
     * @param id ID do exame a ser pesquisado.
     */
    public imprimirCartaMO(pedidoTransporteId: Number, nomeArquivo: string): void {
        super.workupDownload(environment.courierEndpoint,  `pedidostransporte/${pedidoTransporteId}/download/carta`, false, nomeArquivo);
    }

    /**
     * Realiza o download do arquivo associado ao ID do arquivo exame informado.
     *
     * @param id ID do exame a ser pesquisado.
     */
    public imprimirTransporteRelatorio(pedidoTransporteId: Number, nomeArquivo: string): void {
        super.workupDownload(environment.courierEndpoint,  `pedidostransporte/${pedidoTransporteId}/download/transporte`, false, nomeArquivo);
    }

    /**
     * Realiza o download do arquivo com a autorização da CNT para o
     * deslocamento aéreo do courier.
     *
     * @param pedidoTransporteId ID do pedido de transporte.
     */
    public downloadVoucherAutorizacaoCNT(pedidoTransporteId: Number, nomeArquivo: string): void {
        super.workupDownload(`${environment.courierEndpoint}`, `pedidostransporte/${pedidoTransporteId}/download/voucherCnt`, false, nomeArquivo);
    }

    /**
     * Método que confirma a retirada de transporte de material.
     *
     * @param  {number} id
     * @param  {Date} dataRetirada
     * @returns Promise
     */
    public confirmarRetiradaTransporteMaterial(id: number, dataRetirada: Date): Promise<any> {

        let data = JSON.stringify(dataRetirada);
        console.log(data);
        return this.http.put(environment.endpoint + "pedidostransporte/" + id + "/efetuarretirada", data)
            .then(super.extrairDado).catch(super.tratarErro);

    }

    /**
     * Método que confirma a entrega de transporte de material.
     *
     * @param  {number} id
     * @param  {Date} dataEntrega
     * @returns Promise
     */
    public confirmarEntregaTransporteMaterial(id: number, dataEntrega: Date): Promise<any> {

        let data = JSON.stringify(dataEntrega);
        return this.http.put(environment.endpoint + "pedidostransporte/" + id + "/efetuarentrega", data)
            .then(super.extrairDado).catch(super.tratarErro);

    }

    /**
     * Lista de tarefas .
     *
     * @param  {number} pagina
     * @param  {number} quantidadeRegistro
     * @returns Promise
     */
    public listarTarefas(pagina: number, quantidadeRegistro: number): Promise<any> {
        return this.http.get(`${environment.courierEndpoint}pedidostransporte/tarefas?pagina=${pagina}&quantidadeRegistros=${quantidadeRegistro}`)
            .then(super.extrairDado)
            .catch(super.tratarErro);
    }


    /**
     * Lista de tarefas .
     *
     * @param  {number} pagina
     * @param  {number} quantidadeRegistro
     * @returns Promise
     */
    public listarTarefasMaterialInternacional(pagina: number, quantidadeRegistro: number): Promise<any> {
        return this.http.get(environment.endpoint + "pedidostransporte/tarefas/transportadora/internacional" +
                            "?pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistro)
            .then(super.extrairDado)
            .catch(super.tratarErro);
    }


 	/* ############################################################################### */
	/* ########################### PEDIDO TRANSPORTE AÉREO ########################### */

  atualizarInformacoesTransporteAereo(id: number, arquivos?:Map<string, FileItem>, descricaoAlteracao?: string, dataPrevistaRetirada?: Date): Promise<any> {
    let data: FormData = new FormData();

    data.append("id", new Blob([JSON.stringify(id)], {
        type: 'application/json'
    }), '');


    if (arquivos) {
        arquivos.forEach((item: FileItem, key: string) => {
            data.append("file", item._file, item.file.name);
        });
    }

    if(dataPrevistaRetirada){
      data.append("dataPrevistaRetirada", new Blob([JSON.stringify(dataPrevistaRetirada)], {
        type: 'application/json'
      }), '');
    }

    if(descricaoAlteracao){
      data.append("descricaoAlteracao", new Blob([JSON.stringify(descricaoAlteracao)], {
        type: 'application/json'
      }), '');
    }

    return this.http.fileUpload(`${environment.courierEndpoint}pedidotransporte/${id}/atualizaraereo`, data)
              .then(this.extrairDado)
              .catch(this.tratarErro);
    }
}
