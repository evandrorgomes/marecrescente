import { ResultadoWorkup } from '../../../cadastro/resultadoworkup/resultado/resultado.workup';
import { CampoMensagem } from '../../../../shared/campo.mensagem';
import { UrlParametro } from '../../../../shared/url.parametro';
import { Observable } from 'rxjs';
import { ArquivoResultadoWorkup } from '../../../cadastro/resultadoworkup/resultado/arquivo.resultado.workup';
import { environment } from 'environments/environment';
import { HttpClient } from '../../../../shared/httpclient.service';
import { BaseService } from '../../../../shared/base.service';
import { Injectable } from '@angular/core';

/**
 * Registra as chamadas ao back-end envolvendo a entidade Resultado de Workup.
 *
 * @author Filipe Paes
 */
@Injectable()
export class ResultadoWorkupService extends BaseService {

    address: string = "resultadosworkup";

    constructor(protected http: HttpClient) {
        super(http);
    }

    /**
     * Método para buscar doador com resultado de workup em aberto.
     *
     * @param dmr - id do doador.
     * @param centro - id do centro para pesquisa
     * @param aberto - se o resultado do exame está em aberto.
     */
    carregarDoadorPorResultadoWorkup(dmr:number, centro:number, aberto:boolean): Promise<any>{
        return this .http.get(environment.endpoint +
            this.address + "?dmr="+ dmr + "&centro=" + (centro == null? "" : centro) +  "&aberto="+aberto)
                .then(this.extrairDado).catch(this.tratarErro);
    }
    /**
     * Obtém um resultado de workup aberto.
     *
     * @param  {number} idResultadoWorkup
     * @returns Promise<any>
     */
    obterResultadoWorkup(idResultadoWorkup: number): Promise<any> {
        return this .http.get(`${environment.workupEndpoint}resultadoworkup/${idResultadoWorkup}/internacional`)
                .then(this.extrairDado).catch(this.tratarErro);
    }



    /**
     * Método que vai salvar o arquivo de exame de workup
     * @param idCentro referencia ao centro de transplante
     * @param data MultipartFormData com cada um dos itens que serão submetidos ao back.
     * @return Observable<CampoMensagem[]> retorno ajax com campos de mensagem
     */
    salvarArquivo(data: FormData): Promise<any> {

        return this.http.fileUpload(`${environment.workupEndpoint}resultadosworkup/arquivo`, data)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }
    /**
     * Método que vai remover o arquivo de exame de workup
     * @param idCentro referencia ao centro de transplante
     * @param caminho ArquivoResultadoWorkup com cada um dos itens que serão submetidos ao back.
     * @return Observable<CampoMensagem[]> retorno ajax com campos de mensagem
     */
    removerArquivoResultadoWorkup(idPedidoWorkup: number, caminho: String): Promise<any> {
        let data: FormData = new FormData();
        data.append("idPedidoWorkup", new Blob([idPedidoWorkup], {
            type: 'application/json'
        }), '');

        data.append("caminho", new Blob([caminho], {
            type: 'application/json'
        }), '');

        return this.http.fileUploadWithDelete(`${environment.workupEndpoint}resultadosworkup/arquivo`, data)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método para submeter dados de resultado de exame de workup.
     * @param idCentro referencia ao centro de transplante
     * @param resultado
     */
    salvarResultado(idPedidoWorkup: number, resultado: ResultadoWorkup):Promise<any>{
        let data: FormData = new FormData();

        data.append("idPedidoWorkup", new Blob([idPedidoWorkup], {
            type: 'application/json'
        }), '');

        data.append("resultadoWorkup", new Blob([JSON.stringify(resultado)], {
            type: 'application/json'
        }), '');

        return this.http.fileUpload(`${environment.workupEndpoint}resultadosworkup`, data)
            .then(this.extrairDado).catch(this.tratarErro);
    }


    /**
     * Método para buscar a tarefa relacionada ao resultado.
     *
     * @param idResultado - id do resultado.
     */
    obterTarefaWorkupNacional(dmr:number, centro:number): Promise<any> {
        let params:string = "?dmr="+dmr+"&centro="+centro+"&aberto=true";
        return this.http.get(environment.endpoint +
            this.address + "/tarefa" + params)
            .then(this.extrairDado).catch(this.tratarErro);
    }


    listarTarefas(idCentroColeta, pagina, quantidadeRegistros: number): Promise<any> {
        return this.http.get(environment.endpoint + this.address + "/tarefas?pagina=" + pagina + "&quantidadeRegistro=" + quantidadeRegistros)
            .then(super.extrairDado)
            .catch(super.tratarErro);
   }


     /**
     * Método para a extração do json contido no response
     * @param res
     */
    public extrairDadoUpload(res: any) {
        if(res instanceof Observable){
            res.subscribe();
        }
        let jsonParsed:any;

        try {
            jsonParsed = res.json();
        } catch (error) {
            jsonParsed = res;
        }
        return jsonParsed;

    }

    public listarTarefasCadastroResultado(pagina: number, quantidadeRegistro: number): Promise<any> {
        return this.http.get(
            environment.endpoint + this.address + "/tarefas/cadastroResultado?pagina=" + pagina +
                "&quantidadeRegistros=" + quantidadeRegistro)
                    .then(super.extrairDado).catch(super.tratarErro);
    }

    /*########## NOVOS SERVICOS ##########*/
    /**
     * Obtém um resultado de workup nacional.
     *
     * @param  {number} idResultadoWorkup
     * @returns Promise<any>
     */
    obterResultadoWorkupNacional(idResultadoWorkup: number): Promise<any> {
      return this.http.get(`${environment.workupEndpoint}resultadoworkup/${idResultadoWorkup}/nacional`)
              .then(this.extrairDado)
              .catch(this.tratarErro);
    }

}
