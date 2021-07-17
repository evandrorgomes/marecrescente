import { Injectable } from "@angular/core";
import { ArquivoPedidoAdicionalWorkup } from "app/doador/cadastro/resultadoworkup/resultado/arquivo.pedido.adicional.workup";
import { PedidoAdicionalWorkup } from "app/doador/cadastro/resultadoworkup/resultado/pedido.adicional.workup";
import { FileItem } from "ng2-file-upload";
import { environment } from "../../../environments/environment";
import { BaseService } from "../base.service";
import { AprovarPlanoWorkupDTO } from "../dto/aprovar.plano.workup.dto";
import { PlanoWorkupInternacionalDTO } from "../dto/plano.workup.internacional.dto";
import { PlanoWorkupNacionalDTO } from "../dto/plano.workup.nacional.dto";
import { HttpClient } from "../httpclient.service";

@Injectable()
export class PedidoWorkupService extends BaseService {

   addressArquivos: string = "arquivopedidoworkup"

   constructor(protected http: HttpClient) {
      super(http);
   }

   definirCentroColetaPorPedidoWorkup(idPedidoWorkup: number, idCentroColeta: number): Promise<any>{
      return this.http.post(environment.workupEndpoint +"pedidoworkup/" + idPedidoWorkup + "/centrocoleta", idCentroColeta)
              .then(this.extrairDado)
              .catch(this.tratarErro);
  }

    /**
     * Obtém o pedido de workup.
     * @param  {number} idPedido
     * @returns Promise
     */
   obterPedidoWorkup(idPedido: number): Promise<any> {
      return this.http.get(`${environment.workupEndpoint}pedidoworkup/${idPedido}`)
          .then(this.extrairDado)
          .catch(this.tratarErro);
   }

    /**
     * Obtém o plano de pedido de workup nacional.
     * @param  {number} idPedido de workup
     * @returns Promise
     */
   obterPlanoWorkupNacional(idPedido: number): Promise<any> {
      return this.http.get(`${environment.workupEndpoint}pedidoworkup/${idPedido}/planoworkupnacional`)
          .then(this.extrairDado)
          .catch(this.tratarErro);
   }


   aprovarPlanoDeWorkup(idPedido: number, aprovarPlanoDeWorkup: AprovarPlanoWorkupDTO): Promise<any> {
   return this.http.post(`${environment.workupEndpoint}pedidoworkup/${idPedido}/aprovacaoplanoworkup`,aprovarPlanoDeWorkup)
       .then(this.extrairDado)
       .catch(this.tratarErro);
   }

       /**
     *
     * Baixa o arquivo do plano de pedido workup.
     * @param  {number} id
     * @param  {string} nomeArquivo
     * @returns void
     */
   baixarArquivoPlanoWorkup(id: Number, nomeArquivo: String): void {
      super.workupDownload(environment.workupEndpoint, this.addressArquivos + "/" + id + "/download", false, "" + nomeArquivo);
   }

   salvarPlanoWorkupNacional(idPedidoWorkup: number, planoWorkupNacionalDTO: PlanoWorkupNacionalDTO, arquivo?: FileItem): Promise<any> {
      const data: FormData = new FormData();
      if(arquivo){
         data.append("file", arquivo._file, arquivo.file.name);
      }

      data.append("planoWorkup", new Blob([JSON.stringify(planoWorkupNacionalDTO)], {
          type: 'application/json'
      }), '');

      return this.http.fileUpload(`${environment.workupEndpoint}pedidoworkup/${idPedidoWorkup}/planoworkupnacional`,data)
         .then(super.extrairDado)
         .catch(super.tratarErro);
  }

  salvarPlanoWorkupInternacional(idPedidoWorkup: number, planoWorkupInternacionalDTO: PlanoWorkupInternacionalDTO, arquivo: FileItem): Promise<any> {
   const data: FormData = new FormData();

   data.append("file", arquivo._file, arquivo.file.name);

   data.append("planoWorkup", new Blob([JSON.stringify(planoWorkupInternacionalDTO)], {
       type: 'application/json'
   }), '');

   return this.http.fileUpload(`${environment.workupEndpoint}pedidoworkup/${idPedidoWorkup}/planoworkupinternacional`,data)
      .then(super.extrairDado)
      .catch(super.tratarErro);
  }

  /* ######################### PEDIDO ADICIONAL WORKUP ############################# */

  finalizarPedidoAdicionalWorkup(pedidoAdicionalWorkup: PedidoAdicionalWorkup):Promise<any>{
    let data: FormData = new FormData();

    data.append("pedidoAdicionalWorkup", new Blob([JSON.stringify(pedidoAdicionalWorkup)], {
        type: 'application/json'
    }), '');

    return this.http.fileUpload(`${environment.workupEndpoint}pedidoadicionalworkup/finalizarpedidoadicionalworkup`, data)
        .then(this.extrairDado)
        .catch(this.tratarErro);
  }

  salvarArquivosExamesAdicionais(idPedidoAdicional: number, arquivosExamesAdicionais: ArquivoPedidoAdicionalWorkup[]):Promise<any>{
    let data: FormData = new FormData();

    data.append("idPedidoAdicional", new Blob([idPedidoAdicional], {
      type: 'application/json'
    }), '');

    data.append("arquivosExamesAdicionais", new Blob([JSON.stringify(arquivosExamesAdicionais)], {
        type: 'application/json'
    }), '');

    return this.http.fileUpload(`${environment.workupEndpoint}pedidoadicionalworkup`, data)
        .then(this.extrairDado)
        .catch(this.tratarErro);
  }

  salvarArquivoStorage(data: FormData): Promise<any> {
    return this.http.fileUpload(`${environment.workupEndpoint}pedidoadicionalworkup/arquivo`, data)
        .catch(this.tratarErro)
        .then(this.extrairDado);
  }

  removerArquivoStorage(idArquivoAdicional: number, caminho: String): Promise<any> {
    let data: FormData = new FormData();
    data.append("idArquivoAdicional", new Blob([idArquivoAdicional], {
        type: 'application/json'
    }), '');

    data.append("caminho", new Blob([caminho], {
        type: 'application/json'
    }), '');

    return this.http.fileUploadWithDelete(`${environment.workupEndpoint}pedidoadicionalworkup/arquivo`, data)
        .then(this.extrairDado).catch(this.tratarErro);
  }

  /**
   * Método para solicitar exames adicionais nacional.
   *
   * @param  {number} idResultadoWorkup
   * @param  {string} descricao
   */
  criarPedidoAdicionalWorkupDoadorNacional(idPedidoWorkup: number, descricao:string){
    let data: FormData = new FormData();
    data.append("idPedidoWorkup", new Blob([idPedidoWorkup], {
        type: 'application/json'
    }), '');

    data.append("descricao", new Blob([descricao], {
      type: 'application/json'
    }), '');

    return this.http.fileUpload(`${environment.workupEndpoint}pedidoadicionalworkup/nacional/exameadicional`, data)
        .then(this.extrairDado)
        .catch(this.tratarErro);
  }

  /**
   * Método para solicitar exames adicionais internacional.
   *
   * @param  {number} idPedidoWorkup
   * @param  {string} descricao
   */
  criarPedidoAdicionalWorkupDoadorInternacional(idPedidoWorkup: number, descricao:string){
    let data: FormData = new FormData();
    data.append("idPedidoWorkup", new Blob([idPedidoWorkup], {
        type: 'application/json'
    }), '');

    data.append("descricao", new Blob([descricao], {
      type: 'application/json'
    }), '');

    return this.http.fileUpload(`${environment.workupEndpoint}pedidoadicionalworkup/internacional/exameadicional`, data)
        .then(this.extrairDado)
        .catch(this.tratarErro);
  }

  obterPedidoAdicionalWorkup(idPedidoAdicional: number): Promise<any> {
    return this.http.get(`${environment.workupEndpoint}pedidoadicionalworkup/${idPedidoAdicional}`)
        .then(this.extrairDado)
        .catch(this.tratarErro);
  }

  listarArquivosExamesAdicionais(idPedidoAdicional: number): Promise<any> {
    return this.http.get(`${environment.workupEndpoint}pedidoadicionalworkup/arquivosexamesadicionais?idPedidoAdicional=${idPedidoAdicional}`)
        .then(this.extrairDado)
        .catch(this.tratarErro);
  }

  /**
   * Obtém um pedido adicional de workup.
   *
   * @param  {number} idPedidoWorkup
   * @returns Promise<any>
   */
  listarPedidosAdicionaisWorkup(idPedidoWorkup: number): Promise<any> {
    return this.http.get(`${environment.workupEndpoint}pedidoadicionalworkup?idPedidoWorkup=${idPedidoWorkup}`)
            .then(this.extrairDado)
            .catch(this.tratarErro);
  }

    /**
     * Método para baixar arquivo adicional workup.
     * @param idArquivoAdicional - id do arquivo adicional a ser baixado.
     * @param nomeArquivo - nome do arquivo a ser baixado.
     */
    baixarArquivoAdicionalWorkup(idArquivoAdicional:number, nomeArquivo:string){
      this.workupDownload( environment.workupEndpoint,`pedidoadicionalworkup/${idArquivoAdicional}/download`, false, nomeArquivo);
  }

  /* ############################################################################### */

}
