import { Injectable } from "@angular/core";
import { FileItem } from "ng2-file-upload";
import { environment } from "../../../environments/environment";
import { BaseService } from "../base.service";
import { DetalheLogisticaMaterialDTO } from "../dto/detalhe.logistica.material.dto";
import { DetalheLogisticaColetaInternacionalDTO } from "../dto/detalhe.logitica.coleta.internacional.dto";
import { LogisticaDoadorColetaDTO } from "../dto/logistica.doador.coleta.dto";
import { LogisticaDoadorWorkupDTO } from "../dto/logistica.doador.workup.dto";
import { TarefaLogisticaMaterialDTO } from "../dto/tarefa.logistica.material.dto";
import { HttpClient } from "../httpclient.service";

@Injectable()
export class LogisticaService extends BaseService {

   constructor(protected http: HttpClient) {
      super(http);
   }

    /**
     * Método para incluir uma nova logística de doador.
     * @param  {id} idPedidoLogistica identificação da logística.
     * @param  {dto} DTO contendo as informações da logística.
     * @returns mensagem confirmando a inclusão, em caso de sucesso.
     */
    salvarLogistica(idPedidoLogistica: number, dto: LogisticaDoadorWorkupDTO): Promise<any> {
      let data = JSON.stringify(dto);
      return this.http.put(`${environment.workupEndpoint}pedidologistica/${idPedidoLogistica}`, data)
                      .then(this.extrairDado)
                      .catch(this.tratarErro);
  }


  /**
   * Método para obter logística de doador customizado.
   * @param  {id} idPedidoLogistica identificação da logística.
   * @returns pedidoLogisticaWorkupDTO.
   */
  obterPedidoLogisticaCustomizado(idPedido: number): Promise<any> {
      return this.http.get(`${environment.workupEndpoint}pedidologistica/${idPedido}`)
                      .then(this.extrairDado)
                      .catch(this.tratarErro);
  }

   /**
   * Método para encerrar pedido de logística e finalizar a tarefa do analista responsável.
   * @param  {dto} DTO contendo as informações da logística.
   * @param  {id} idPedidoLogistica identificação da logística.
   * @returns mensagem confirmando o encerramento, em caso de sucesso.
   */
  encerrarLogisticaDoadorWorkup(idPedido: number, dto: LogisticaDoadorWorkupDTO): Promise<any> {
      let data = JSON.stringify(dto);
      return this.http.post(`${environment.workupEndpoint}pedidologistica/${idPedido}`, data)
              .then(this.extrairDado)
              .catch(this.tratarErro);
  }

  /**
   * Atribui, para o analista de logística, a tarefa de logística informada.
   *
   * @param tarefaLogistica tarefa que será atribuída para usuário logado.
   */
  public atribuirTarefa(tarefaLogistica: TarefaLogisticaMaterialDTO): Promise<any> {
      return this.http.put(environment.endpoint + 'pedidoslogistica/atribuir/' + tarefaLogistica.idPedidoLogistica)
          .then(this.extrairDado).catch(this.tratarErro);
  }

//  public listarTarefasLogisticaPaginadas(pagina: number, quantidadeRegistro: number): Promise<any>{
//       let url: string = environment.endpoint + 'pedidoslogistica/tarefas';
//       return this .http.get(url + "?pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistro)
//                   .then(this.extrairDado).catch(this.tratarErro);
//   }

  public listarTarefasLogisticaInternacionalPaginadas(pagina: number, quantidadeRegistro: number): Promise<any>{
      let url: string = environment.endpoint + 'pedidoslogistica/tarefas/internacional';
      return this .http.get(url + "?pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistro)
                  .then(this.extrairDado).catch(this.tratarErro);
  }

  public obterDadosPedidoLogisticaDoador(tarefaPedidoLogisticaId: number): Promise<any>{
      return this .http.get(environment.endpoint + 'pedidoslogistica/' + tarefaPedidoLogisticaId)
                  .then(this.extrairDado).catch(this.tratarErro);
  }

  /**
   * Realiza o download da autorização do paciente.
   * @return response com o resultado consulta ao back-end.
   */
  public downloadAutorizacaoPaciente(idPedidoLogistica: number, nomeArquivo: string): void {
      super.download('pedidoslogistica/' + idPedidoLogistica + '/autorizacaopaciente/download', false, nomeArquivo);
  }


  public alterarLogisticaMaterialAereo(idPedidoLogistica: number
  , tipoAlteracao : number
  , arquivos:  Map<string, FileItem>
  , descricao: string
  , dataColeta: Date
  , rmr: number){

      let data: FormData = new FormData();

      data.append("tipo", new Blob([JSON.stringify(tipoAlteracao)], {
          type: 'application/json'
      }), '');


      arquivos.forEach((item: FileItem, key: string) => {
          data.append("file", item._file, item.file.name);
      });

      data.append("descricao", new Blob([JSON.stringify(descricao)], {
          type: 'application/json'
      }), '');

      data.append("data", new Blob([JSON.stringify(dataColeta)], {
          type: 'application/json'
      }), '');

      data.append("rmr", new Blob([JSON.stringify(rmr)], {
          type: 'application/json'
      }), '');

      return this.http.fileUpload(environment.endpoint + 'pedidoslogistica/' + idPedidoLogistica + '/material/aereo', data)
                      .then(this.extrairDado).catch(this.tratarErro);
  }

  public incluirArquivoVoucherLogistica(data: FormData, pedidoLogisticaId: number): Promise<any> {
    return this.http.fileUpload(`${environment.workupEndpoint}pedidologistica/${pedidoLogisticaId}/voucher`, data)
                    .then(super.extrairDado).catch(super.tratarErro);
  }

  public excluirArquivoVoucherLogistica(nomeArquivo: string, pedidoLogisticaId: number) {
    return this.http.delete(`${environment.workupEndpoint}/pedidologistica/${pedidoLogisticaId}/voucher`, nomeArquivo )
       .then(this.extrairDado).catch(this.tratarErro);
  }


	/* ######################################################################################## */
	/* ########################### LOGISTICA MATERIAL INTERNACIONAL ########################### */

   /**
   * Método para salvar a logística de material internacional.
   * @param  {dto} DTO contendo as informações da logística.
   * @returns mensagem confirmando a inclusão, em caso de sucesso.
   */
  public salvarPedidoLogisticaMaterialColetaInternacional(idPedidoLogistica: number, dto: DetalheLogisticaColetaInternacionalDTO): Promise<any> {
    let data = JSON.stringify(dto);
    return this.http.post(`${environment.workupEndpoint}pedidologistica/${idPedidoLogistica}/coleta/internacional`, data)
          .then(this.extrairDado)
          .catch(this.tratarErro);
  }

   /**
   * Método para finalizar a logística de material, fechando assim as tarefas de logística.
   * @param  {dto} DTO contendo as informações da logística.
   * @returns mensagem confirmando a inclusão, em caso de sucesso.
   */
  public finalizarPedidoLogisticaMaterialColetaInternacional(idPedidoLogistica: number): Promise<any> {
    return this.http.post(`${environment.workupEndpoint}pedidologistica/${idPedidoLogistica}/coleta/internacional/finalizar`)
          .then(this.extrairDado)
          .catch(this.tratarErro);
  }

	/* ################################################################################### */
	/* ########################### LOGISTICA MATERIAL NACIONAL ########################### */

  public listarTarefasPedidoLogisticaMaterialColetaNacionalPaginadas(pagina: number, quantidadeRegistro: number): Promise<any>{
    return this.http.get(`${environment.workupEndpoint}pedidologistica/tarefas?pagina=${pagina}&quantidadeRegistros=${quantidadeRegistro}`)
          .then(this.extrairDado)
          .catch(this.tratarErro);
  }

     /**
   * Método para sakvar a logística de material.
   * @param  {dto} DTO contendo as informações da logística.
   * @returns mensagem confirmando a inclusão, em caso de sucesso.
   */
  public salvarPedidoLogisticaMaterialColetaNacional(dto: DetalheLogisticaMaterialDTO): Promise<any> {
    let data = JSON.stringify(dto);
    return this.http.post(`${environment.workupEndpoint}pedidologistica/${dto.idPedidoLogistica}/material/nacional/salvar`, data)
            .then(this.extrairDado)
            .catch(this.tratarErro);
  }

    /**
   * Método para finalizar a logística de material, fechando assim as tarefas de logística.
   * @param  {dto} DTO contendo as informações da logística.
   * @returns mensagem confirmando a inclusão, em caso de sucesso.
   */
  public finalizarPedidoLogisticaMaterialColetaNacional(dto: DetalheLogisticaMaterialDTO): Promise<any> {
    let data = JSON.stringify(dto);
    return this.http.post(`${environment.workupEndpoint}pedidologistica/${dto.idPedidoLogistica}/material/nacional/finalizar`, data)
            .then(this.extrairDado)
            .catch(this.tratarErro);
  }

  public listarPedidosLogisticaPorTransporteEmAndamento(pagina: number, quantidadeRegistro: number): Promise<any>{
    return this .http.get(`${environment.workupEndpoint}pedidologistica/transportes?pagina=${pagina}&quantidadeRegistros=${quantidadeRegistro}`)
          .then(this.extrairDado)
          .catch(this.tratarErro);
  }

	/* ################################################################################### */
	/* ########################### LOGISTICA DOADOR COLETA ########################### */

    /**
   * Método para obter logística de doador customizado.
   * @param  {id} idPedidoLogistica identificação da logística.
   * @returns pedidoLogisticaWorkupDTO.
   */
  obterPedidoLogisticaDoadorColetaCustomizado(idPedido: number): Promise<any> {
    return this.http.get(`${environment.workupEndpoint}pedidologistica/doador/${idPedido}`)
            .then(this.extrairDado)
            .catch(this.tratarErro);
  }

    /**
   * Método para incluir uma nova logística de doador.
   * @param  {id} idPedidoLogistica identificação da logística.
   * @param  {dto} DTO contendo as informações da logística.
   * @returns mensagem confirmando a inclusão, em caso de sucesso.
   */
  salvarLogisticaDoadorColeta(idPedidoLogistica: number, dto: LogisticaDoadorColetaDTO): Promise<any> {
    let data = JSON.stringify(dto);
    return this.http.put(`${environment.workupEndpoint}pedidologistica/doador/${idPedidoLogistica}`, data)
                    .then(this.extrairDado)
                    .catch(this.tratarErro);
  }

     /**
   * Método para encerrar pedido de logística e finalizar a tarefa do analista responsável.
   * @param  {dto} DTO contendo as informações da logística.
   * @param  {id} idPedidoLogistica identificação da logística.
   * @returns mensagem confirmando o encerramento, em caso de sucesso.
   */
  encerrarLogisticaDoadorColeta(idPedido: number, dto: LogisticaDoadorColetaDTO): Promise<any> {
    let data = JSON.stringify(dto);
    return this.http.post(`${environment.workupEndpoint}pedidologistica/doador/${idPedido}`, data)
            .then(this.extrairDado)
            .catch(this.tratarErro);
  }

}
