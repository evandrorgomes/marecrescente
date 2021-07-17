import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { BaseService } from '../../../../shared/base.service';
import { HttpClient } from '../../../../shared/httpclient.service';

/**
 * Registra as chamadas ao back-end envolvendo a entidade Resultado de Workup.
 *
 * @author Filipe Paes
 */
@Injectable()
export class AvaliacaoResultadoWorkupService extends BaseService {

    constructor(protected http: HttpClient) {
        super(http);
    }
    /**
     * Método para solicitar um pedido de coleta.
     *
     * @param  {number} idAvaliacaoResultadoWorkup
     * @param  {string} justificativa
     */
    public prosseguirAvaliacaoResultadoWorkupInternacional(idResultadoWorkup:number, justificativa?:string){
       let data: FormData = new FormData();
       data.append("idResultadoWorkup", new Blob([idResultadoWorkup], {
          type: 'application/json'
       }), '');
       if (justificativa) {
          data.append("justificativa", new Blob([justificativa], {
             type: 'application/json'
          }), '');
       }

       return this.http.fileUpload(`${environment.workupEndpoint}avaliacoesresultadoworkup/internacional/prosseguir`, data)
          .then(this.extrairDado).catch(this.tratarErro);

    }
    /**
     * Método que descarta o doador atual como inviável para transplante.
     *
     * @param  {number} idAvaliacaoResultadoWorkup
     * @param  {string} justificativa
     */
    public naoProsseguirAvaliacaoResultadoWorkupInternacional(idResultadoWorkup:number, justificativa: string){
       let data: FormData = new FormData();
       data.append("idResultadoWorkup", new Blob([idResultadoWorkup], {
          type: 'application/json'
       }), '');

       data.append("justificativa", new Blob([justificativa], {
          type: 'application/json'
       }), '');

       return this.http.fileUpload(`${environment.workupEndpoint}avaliacoesresultadoworkup/internacional/naoprosseguir`, data)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método para baixar arquivo de laudo.
     * @param idArquivoLaudo - id do arquivo a ser baixado.
     * @param nomeArquivo - nome do arquivo a ser baixado.
     */
    baixarArquivoDeResultadoWorkup(idArquivoLaudo:number, nomeArquivo:string){
        this.workupDownload( environment.workupEndpoint,`arquivoresultadoworkup/${idArquivoLaudo}/download`, false, nomeArquivo);
    }
    /**
     * Adiciona um pedido adicional.
     * @param  {number} idAvaliacaoResultadoWorkup
     * @param  {string} texto
     */
    salvarPedidoAdicional(idAvaliacaoResultadoWorkup:number, texto:string){
        return this.http.post(environment.endpoint +
            'avaliacoesresultadoworkup/'+ idAvaliacaoResultadoWorkup+'/pedidoadicional',texto)
                .then(this.extrairDado).catch(this.tratarErro);
    }

   listarTarefas(idCentroTransplante, pagina, quantidadeRegistros: number): Promise<any> {
        return this.http.get(environment.endpoint + "avaliacoesresultadoworkup/tarefas" +
            "?idCentroTransplante=" + idCentroTransplante + "&pagina=" + pagina + "&quantidadeRegistro=" + quantidadeRegistros)
            .then(super.extrairDado)
            .catch(super.tratarErro);
   }

   /* ################### NOVOS SERVIÇOS ################### */

    /**
     * Serviço para obter uma avaliação de resultado de workup pelo identificador.
     * @param  {number} idAvaliacaoResultadoWorkup
     * @returns Promise<any>
     */
    obterAvaliacaoResultadoWorkupPorId(idAvaliacaoResultadoWorkup:number): Promise<any>{
      return this .http.get(`${environment.workupEndpoint}avaliacaoresultadoworkup/${idAvaliacaoResultadoWorkup}/nacional`)
            .then(this.extrairDado)
            .catch(this.tratarErro);
  }

  /**
   * Método para solicitar um pedido de coleta.
   *
   * @param  {number} idAvaliacaoResultadoWorkup
   * @param  {string} justificativa
   */
  public prosseguirAvaliacaoResultadoWorkupNacional(idResultadoWorkup: number, justificativa?:string){
    let data: FormData = new FormData();
    data.append("idResultadoWorkup", new Blob([idResultadoWorkup], {
        type: 'application/json'
    }), '');
    if (justificativa) {
        data.append("justificativa", new Blob([justificativa], {
          type: 'application/json'
        }), '');
    }
    return this.http.fileUpload(`${environment.workupEndpoint}avaliacoesresultadoworkup/nacional/prosseguir`, data)
        .then(this.extrairDado).catch(this.tratarErro);
  }

/**
   * Método para solicitar aprovação do médico redome.
   *
   * @param  {number} idAvaliacaoResultadoWorkup
   * @param  {string} justificativa
   */
  public prosseguirColetaInviavelAvaliacaoResultadoWorkupNacional(idResultadoWorkup: number, justificativa:string){
    let data: FormData = new FormData();
    data.append("idResultadoWorkup", new Blob([idResultadoWorkup], {
        type: 'application/json'
    }), '');

    data.append("justificativa", new Blob([justificativa], {
      type: 'application/json'
    }), '');

    return this.http.fileUpload(`${environment.workupEndpoint}avaliacoesresultadoworkup/nacional/prosseguircoletainviavel`, data)
        .then(this.extrairDado)
        .catch(this.tratarErro);
  }

  /**
  * Método que descarta o doador atual como inviável para transplante.
  *
  * @param  {number} idAvaliacaoResultadoWorkup
  * @param  {string} justificativa
  */
  public naoProsseguirAvaliacaoResultadoWorkupNacional(idResultadoWorkup: number, justificativa: string){
    let data: FormData = new FormData();
    data.append("idResultadoWorkup", new Blob([idResultadoWorkup], {
        type: 'application/json'
    }), '');

    data.append("justificativa", new Blob([justificativa], {
        type: 'application/json'
    }), '');

    return this.http.fileUpload(`${environment.workupEndpoint}avaliacoesresultadoworkup/nacional/naoprosseguir`, data)
              .then(this.extrairDado).catch(this.tratarErro);
  }

}
