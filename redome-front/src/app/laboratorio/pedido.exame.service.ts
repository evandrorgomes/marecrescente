import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { FileItem } from 'ng2-file-upload';
import { ExameCordaoInternacional } from '../doador/exame.cordao.internacional';
import { ExameDoadorInternacional } from '../doador/exame.doador.internacional';
import { BaseService } from '../shared/base.service';
import { HttpClient } from '../shared/httpclient.service';
import { PedidoExame } from './pedido.exame';
/**
 * Registra as chamadas ao back-end envolvendo a entidade PedidoExame.
 *
 * @author Filipe Paes
 */
@Injectable()
export class PedidoExameService extends BaseService {

    constructor(protected http: HttpClient) {
        super(http);
    }

    /**
     * Método para receber um pedido de exame no laboratório.
     * @param idPedido - identificação do pedido a ser recebido.
     * @param pedidoExame - pedido de exame a ser recebido.
     */
    public receberPedidoExame(idPedido:number, pedidoExame:PedidoExame): Promise<any> {
        return this.http.put(environment.endpoint + "pedidosexame/" + idPedido + "/receber/?idLaboratorio="
        + pedidoExame.laboratorio.id, pedidoExame)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

     /**
     * Método para persistir um novo exame.
     *
     * @param {FormData} data arquivo a ser persistido.
     * @param {PedidoExame} pedidoExame pedido a ser salvo
     * @returns {Promise<any>}
     * @memberof PedidoExameService
     */
    public enviarResultado(data: FormData, pedidoExame:PedidoExame):Promise<any>{
        data.append("pedidoExame", new Blob([JSON.stringify(pedidoExame)], {
            type: 'application/json'
        }), '');


        return this.http.fileUpload(environment.endpoint +  "pedidosexame", data)
                        .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Obtem um pedido de exame carregado por id.
     * @param idPedido id do pedido a ser carregado.
     * @returns {Promise<any>}
     */
    public carregarPedidoExame(idPedido:number): Promise<any> {
        return this.http.get(environment.endpoint + "pedidosexame/" + idPedido)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    public salvarPedidoExame(pedidoExame: PedidoExame): Promise<any> {
        let data = JSON.stringify(pedidoExame);
        return this.http.post(environment.endpoint + "pedidosexame", data).then(this.extrairDado).catch(this.tratarErro);

    }

    /**
     * Atualiza o pedido de exame quanto aos locus de pedido internacional.
     * @param pedidoExame
     */
    public atualizarPedidoExameInternacional(idPedido: number,pedidoExame: PedidoExame): Promise<any> {
        let data = JSON.stringify(pedidoExame);
        return this.http.put(environment.endpoint  + "pedidosexame/" + idPedido , data).then(this.extrairDado).catch(this.tratarErro);

    }

    public alterLaboratorioPedidoExame(idPedidoExame: number, idLaboratorio: number): Promise<any> {
        let data = JSON.stringify(idLaboratorio);
        return this.http.put(environment.endpoint + "pedidosexame/" + idPedidoExame + "/alterar/laboratorio" , data).then(this.extrairDado).catch(this.tratarErro);

    }

    public downloadInstrucaoColetaSangueCTSwab(buscaId: number, idTipoExame: number, nomeArquivo: string) {
        super.download( "pedidosexame/instrucaocoletasanguectswab/download?idBusca=" + buscaId + "&idTipoExame=" + idTipoExame, false, nomeArquivo);
    }

    public downloadPedidoExameTesteconfirmatorio(buscaId: number) {
        super.download( "pedidosexame/testeconfirmatorio/download?idBusca=" + buscaId, false, "pedido_exame_teste_confirmatorio.pdf");
    }

    /**
     * @description Lista as solicitações dos pedidos de exame doadores nacionais.
     *
     * @author ergomes
     * @param {number} idDoador
     * @param {number} idPaciente
     * @param {number} idBusca
     * @param {boolean} [exibirHistorico=false]
     * @param {number} filtroTipoExame
     * @param {number} pagina
     * @param {number} quantidadeRegistros
     * @returns {Promise<any>}
     * @memberof PedidoExameService
     */
    public listarSolicitacoesDePedidosDeExameNacional(
        idDoador: number, idPaciente: number, idBusca: number, exibirHistorico: boolean = false, filtroTipoExame : number,
        pagina: number, quantidadeRegistros: number): Promise<any> {

        let params: string = "exibirHistorico=" + exibirHistorico + "&filtroTipoExame=" + filtroTipoExame +
            "&pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistros;

        if (idDoador) {
            params += "&idDoador=" + idDoador;
        }
        if (idPaciente) {
            params += "&idPaciente=" + idPaciente;
        }
        if(idBusca){
            params += "&idBusca=" + idBusca;
        }

    return this.http.get(
        environment.endpoint + "pedidosexame/nacional/pedidos?" + params)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * @description Lista as solicitações dos pedidos de exame doadores internacionais.
     *
     * @author ergomes
     * @param {number} idDoador
     * @param {number} idPaciente
     * @param {number} idBusca
     * @param {boolean} [exibirHistorico=false]
     * @param {number} filtroTipoExame
     * @param {number} pagina
     * @param {number} quantidadeRegistros
     * @returns {Promise<any>}
     * @memberof PedidoExameService
     */
    public listarSolicitacoesDePedidosDeExameInternacional(
        idBusca: number, exibirHistorico: boolean = false, filtroTipoExame : number,
        pagina: number, quantidadeRegistros: number): Promise<any> {

        let params: string = "exibirHistorico=" + exibirHistorico + "&filtroTipoExame=" + filtroTipoExame +
            "&pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistros;

        if(idBusca){
            params += "&idBusca=" + idBusca;
        }

    return this.http.get(
        environment.endpoint + "pedidosexame/internacional/pedidos?" + params)
            .then(this.extrairDado).catch(this.tratarErro);
    }


   /**
     * @description Lista os pedidos de exame do doador.
     *
     * @author ergomes
     * @param {number} idDoador
     * @param {number} pagina
     * @param {number} quantidadeRegistros
     * @returns {Promise<any>}
     */
    public listarAndamentoDePedidosExamesPorDoador(
        idDoador: number, pagina: number, quantidadeRegistros: number): Promise<any> {
        let params: string = "pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistros;
        if (idDoador) {
            params += "&idDoador=" + idDoador;
        }
    return this.http.get(
        environment.endpoint + "pedidosexame/andamento/solicitacao/nacional?" + params)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Baixa o PDF de pedido de exame
     * @param pedidoExameId
     */
    public downloadRelaotorioPedidoExameCT(pedidoExameId: number) {
        super.download("pedidosexame/" + pedidoExameId + "/ct/download", false, "pedido_exame_ct.pdf");
    }

    /**
     * Obtem um pedido de exame por id.
     * @param idPedido - id do pedido a ser carregado.
     */
    public obterPedidoExamePorId(idPedido:number): Promise<any>{
        return this.http.get(environment.endpoint + "pedidosexame/" + idPedido).then(this.extrairDado).catch(this.tratarErro);
    }


    /**
     * Método para persistir o resultado de pedido de exame do doador internacional.
     *
     * @author Bruno Sousa
     * @param {number} idPedidoExame
     * @param {ExameDoadorInternacional} exame
     * @param {number} motivoStatusId
     * @param {number} timeRetornoInatividade
     * @returns {Promise<any>}
     * @memberof PedidoExameService
     */
    public enviarResultadoPedidoExameDoadorInternacional(idPedidoExame: number, exame: ExameDoadorInternacional,
        motivoStatusId: number, timeRetornoInatividade: number):Promise<any>{
        let data: FormData = new FormData();
        data.append("exame", new Blob([JSON.stringify(exame)], {
            type: 'application/json'
        }), '');
        data.append("motivoStatusId", new Blob([JSON.stringify(motivoStatusId)], {
            type: 'application/json'
        }), '');
        data.append("timeRetornoInatividade", new Blob([JSON.stringify(timeRetornoInatividade)], {
            type: 'application/json'
        }), '');

        return this.http.fileUpload(environment.endpoint + "pedidosexame/" + idPedidoExame + "/resultado/doadorinternacional", data)
                        .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Método para persistir o resultado de pedido de exame do doador internacional.
     *
     * @author Bruno Sousa
     * @param {number} idPedidoExame
     * @param {ExameDoadorInternacional} exame
     * @param {number} motivoStatusId
     * @param {number} timeRetornoInatividade
     * @returns {Promise<any>}
     * @memberof PedidoExameService
     */
    public enviarResultadoPedidoExameCTDoadorInternacional(idPedidoExame: number, exame: ExameDoadorInternacional,
        motivoStatusId: number, timeRetornoInatividade: number, arquivos:Map<string, FileItem>):Promise<any>{
        let data: FormData = new FormData();
        data.append("exame", new Blob([JSON.stringify(exame)], {
            type: 'application/json'
        }), '');
        data.append("motivoStatusId", new Blob([JSON.stringify(motivoStatusId)], {
            type: 'application/json'
        }), '');
        data.append("timeRetornoInatividade", new Blob([JSON.stringify(timeRetornoInatividade)], {
            type: 'application/json'
        }), '');

        if (arquivos) {
            arquivos.forEach((item: FileItem, key: string) => {
                data.append("file", item._file, item.file.name);
            });
        }


        return this.http.fileUpload(environment.endpoint + "pedidosexame/" + idPedidoExame + "/resultado/ct/doadorinternacional", data)
                        .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Método para persistir o resultado de pedido de exame do cordão internacional.
     *
     * @author Bruno Sousa
     * @param {number} idPedidoExame
     * @param {ExameCordaoInternacional} exame
     * @param {number} motivoStatusId
     * @param {number} timeRetornoInatividade
     * @returns {Promise<any>}
     * @memberof PedidoExameService
     */
    public enviarResultadoPedidoExameCTCordaoInternacional(idPedidoExame: number, exame: ExameCordaoInternacional,
        motivoStatusId: number, timeRetornoInatividade: number, arquivos:Map<string, FileItem>):Promise<any>{
        let data: FormData = new FormData();
        data.append("exame", new Blob([JSON.stringify(exame)], {
            type: 'application/json'
        }), '');
        data.append("motivoStatusId", new Blob([JSON.stringify(motivoStatusId)], {
            type: 'application/json'
        }), '');
        data.append("timeRetornoInatividade", new Blob([JSON.stringify(timeRetornoInatividade)], {
            type: 'application/json'
        }), '');

        if (arquivos) {
            arquivos.forEach((item: FileItem, key: string) => {
                data.append("file", item._file, item.file.name);
            });
        }


        return this.http.fileUpload(environment.endpoint + "pedidosexame/" + idPedidoExame + "/resultado/ct/cordaointernacional", data)
                        .then(super.extrairDado).catch(super.tratarErro);
    }

}
