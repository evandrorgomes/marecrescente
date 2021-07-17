import { Injectable } from "@angular/core";
import { UrlParametro } from 'app/shared/url.parametro';
import { environment } from 'environments/environment';
import { BaseService } from "../base.service";
import { RespostaChecklist } from "../dominio/resposta.checklist";
import { HttpClient } from "../httpclient.service";

/**
 * Classe de acesso aos métodos REST de tipochecklist.
 *
 * @author ergomes
 *
 */
@Injectable()
export class ChecklistService extends BaseService {

    constructor(protected http: HttpClient) {
        super(http);
    }

    /**
     * Retorna lista pagina de checklistts por analista e tipo.
     * @param filtros filtros da consulta.
     * @param pagina pagina a ser listada.
     * @param quantidadeRegistros quantidade de resgistros.
     */
    public listarChecklist(filtros: UrlParametro[], pagina: number, quantidadeRegistros:number):Promise<any>{
            let parametros = "";
            let url: string = environment.endpoint + "buscas";


            parametros += "?pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistros;

            filtros.forEach(filtro => {
                parametros += "&" + filtro.toUrl();
            });

            return this.http.get(environment.endpoint + 'checklist/' + parametros).then(this.extrairDado).catch(this.tratarErro);
    }

    /* ################################################################################################# */

    /**
     * Obtém uma listagem de respostas de checklist por id de pedido de logística.
     * @param idPedidoLogistica.
     */
    public obterRespostasPorPedidoLogistica(idPedidoLogistica:number): Promise<any>{
        return this.http.get(`${environment.workupEndpoint}checklist/respostas?idPedidoLogistica=${idPedidoLogistica}`)
        .then(this.extrairDado)
        .catch(this.tratarErro);
    }

        /**
     * Obtem um tipo de checklist por id.
     * @param idTipo
     */
    public obterChecklistPorTipo(idTipo:number): Promise<any>{
      return this.http.get(`${environment.workupEndpoint}checklist/${idTipo}`)
              .then(this.extrairDado)
              .catch(this.tratarErro);
  }

  /**
   * Envia a resposta do item clicado para o servidor.
   * @param resposta
   */
  public salvarChecklist(resposta: RespostaChecklist){
      let data = JSON.stringify(resposta);
      return this.http.post(`${environment.workupEndpoint}checklist`, data)
      .then(this.extrairDado)
      .catch(this.tratarErro);
  }

}
