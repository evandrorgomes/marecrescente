import { UrlParametro } from './url.parametro';
import { AutenticacaoService } from './autenticacao/autenticacao.service';
import { AuthHttp } from 'angular2-jwt';
import { EventEmitterService } from './event.emitter.service';
import { RequestOptionsArgs, Http, Headers, Response, RequestOptions } from '@angular/http';
import { Observable, Subscription } from 'rxjs';
import { Injectable } from '@angular/core';
import { ErroMensagem } from './erromensagem'
import { CampoMensagem } from './campo.mensagem';
import { read } from 'fs';
import { AbstractHttpService } from './abstract.http.service';


/**
 * Classe utilitária que realiza as solicitações ajax no servidor REST
 * @author Filipe Paes
 */
@Injectable()
export class HttpClient extends AbstractHttpService {
   
    /**
  * Método construtor.
  * @param http atributo do tipo Http que faz o acesso não configurado ao servidor REST
  * @author Filipe Paes, bruno.sousa
  */
    constructor(public http: AuthHttp, private autenticacaoService: AutenticacaoService) {
        super();
    }


    /**
  * Método que faz as solicitações via GET no servidor REST.
  * @param url endereço do serviço a ser acessado
  * @return Observable<Response> retorno da solicitação GET feita ao servidor
  * @author Filipe Paes
  */
    get(url: string, requestOptions?:RequestOptionsArgs): Promise<Response> { 

        if (this.autenticacaoService.tokenExpirado()) {
            return this.redirecionaPaginaLogin();
        }

        let requestOptionsArgs = requestOptions ? requestOptions : null;

        let responsePromise:Promise<Response> = 
            this.http.get(url, requestOptionsArgs)
                     .catch(error => this.handleError(error)).toPromise();
                     
        responsePromise["url"] = url;
        EventEmitterService.get("onSubmitLoading").emit(responsePromise);

        return responsePromise;
    }

    /** 
  * Método que faz as solicitações via POST no servidor REST.
  * @param url endereço do serviço a ser acessado
  * @param data dados JSON para serem submetidos ao servidor 
  * @return Observable<Response> retorno da solicitação GET feita ao servidor
  * @author Filipe Paes
  */
    post(url: string, data?, requestOptions?:RequestOptionsArgs): Promise<Response> {  
        if (this.autenticacaoService.tokenExpirado()) {
            return this.redirecionaPaginaLogin();
        }

        let responsePromise:Promise<Response> = this.http.post(url, data, requestOptions)
            .catch(error => this.handleError(error)).toPromise();
        responsePromise["url"] = url;
        EventEmitterService.get("onSubmitLoading").emit(responsePromise);

        return responsePromise;
    }

    /** 
  * Método que faz as solicitações via PUT no servidor REST.
  * @param url endereço do serviço a ser acessado
  * @param data dados JSON para serem submetidos ao servidor 
  * @return Observable<Response> retorno da solicitação GET feita ao servidor
  * @author Fillipe Queiroz
  */
    put(url: string, data?, requestOptions?:RequestOptionsArgs): Promise<Response> {  
        if (this.autenticacaoService.tokenExpirado()) {
            return this.redirecionaPaginaLogin();
        }

        let responsePromise:Promise<Response> = this.http.put(url, data, requestOptions)
            .catch(error => this.handleError(error)).toPromise();
        responsePromise["url"] = url;
        EventEmitterService.get("onSubmitLoading").emit(responsePromise);

        return responsePromise;
    }

    /** 
  * Método que faz as solicitações de deleção no servidor REST.
  * @param url endereço do serviço a ser acessado
  * @param data dados JSON para serem submetidos ao servidor 
  * @return Observable<Response> retorno da solicitação GET feita ao servidor
  * @author Fillipe Queiroz
  */
    delete(url: string, requestOptions?:RequestOptionsArgs): Promise<Response> {
        if (this.autenticacaoService.tokenExpirado()) {
            return this.redirecionaPaginaLogin();
        }
        let responsePromise:Promise<Response> = 
            this.http.delete(url, requestOptions).catch(error => this.handleError(error)).toPromise();
        responsePromise["url"] = url;
        EventEmitterService.get("onSubmitLoading").emit(responsePromise);

        return responsePromise;
    }

    /**
     * Abre uma nova janela do navegador já com o token
     * 
     * @param {string} url 
     * @param {string} [nomeArquivo=""] 
     * @memberof HttpClient
     */
    openWindow(url : string, nomeArquivo : string = "" ){
        if (this.autenticacaoService.tokenExpirado()) {
            return this.redirecionaPaginaLogin();
        }
        window.open(url + this.autenticacaoService.obterTokenParaUrl(), nomeArquivo, "replace=true");
    }

    /**
     * Método que envia arquivos via POST no servidor REST.
     * 
     * @param {string} url 
     * @param {FormData} data 
     * @returns {Observable<Response>} 
     * 
     * @memberOf HttpClient
     */
    fileUpload(url: string, data: FormData, stringParams?:UrlParametro[]): Promise<Response> {

        if (this.autenticacaoService.tokenExpirado()) {
            return this.redirecionaPaginaLogin();
        }

        let responsePromise:Promise<Response> = new Promise<Response>((resolve, reject) => {
            let XMLHttp: XMLHttpRequest = new XMLHttpRequest();

            XMLHttp.onreadystatechange = function () {
                if (XMLHttp.readyState === 4) {
                    if (XMLHttp.status === 200) {
                        resolve(XMLHttp.response);
                    } else {

                        if (XMLHttp.response) {
                            try {
                                reject(JSON.parse(XMLHttp.response))
                            } catch (e) {
                                reject(XMLHttp.response)
                            }
                        }
                    }
                }
            }
            let parametros:string = "";

            if(stringParams){
                parametros = "&";
                stringParams.forEach(p=>{
                    parametros += p.toUrl();
                });
            }

            XMLHttp.open("post", url + this.autenticacaoService.obterTokenParaUrl() + parametros, true);
            XMLHttp.send(data)
        }).catch(error => this.handleErrorUpload(error));
        responsePromise["url"] = url;
        EventEmitterService.get("onSubmitLoading").emit(responsePromise);

        return responsePromise;
    }

    /**
     * Método que envia arquivos via PUT no servidor REST.
     * 
     * @param {string} url 
     * @param {FormData} data 
     * @returns {Observable<Response>} 
     * 
     * @memberOf HttpClient
     */
    fileUploadWithPut(url: string, data: FormData, stringParams?:UrlParametro[]): Promise<Response> {

        if (this.autenticacaoService.tokenExpirado()) {
            return this.redirecionaPaginaLogin();
        }

        let responsePromise:Promise<Response> = new Promise<Response>((resolve, reject) => {
            let XMLHttp: XMLHttpRequest = new XMLHttpRequest();

            XMLHttp.onreadystatechange = function () {
                if (XMLHttp.readyState === 4) {
                    if (XMLHttp.status === 200) {
                        resolve(XMLHttp.response);
                    } else {

                        if (XMLHttp.response) {
                            try {
                                reject(JSON.parse(XMLHttp.response))
                            } catch (e) {
                                reject(XMLHttp.response)
                            }
                        }
                    }
                }
            }
            let parametros:string = "";

            if(stringParams){
                parametros = "&";
                stringParams.forEach(p=>{
                    parametros += p.toUrl();
                });
            }

            XMLHttp.open("put", url + this.autenticacaoService.obterTokenParaUrl() + parametros, true);
            XMLHttp.send(data)
        }).catch(error => this.handleErrorUpload(error));
        responsePromise["url"] = url;
        EventEmitterService.get("onSubmitLoading").emit(responsePromise);

        return responsePromise;
    }

    /**
     * Método que envia arquivos via PUT no servidor REST.
     *
     * @param {string} url
     * @param {FormData} data
     * @returns {Observable<Response>}
     *
     * @memberOf HttpClient
     */
    fileUploadWithDelete(url: string, data: FormData, stringParams?:UrlParametro[]): Promise<Response> {

        if (this.autenticacaoService.tokenExpirado()) {
            return this.redirecionaPaginaLogin();
        }

        let responsePromise:Promise<Response> = new Promise<Response>((resolve, reject) => {
            let XMLHttp: XMLHttpRequest = new XMLHttpRequest();

            XMLHttp.onreadystatechange = function () {
                if (XMLHttp.readyState === 4) {
                    if (XMLHttp.status === 200) {
                        resolve(XMLHttp.response);
                    } else {

                        if (XMLHttp.response) {
                            try {
                                reject(JSON.parse(XMLHttp.response))
                            } catch (e) {
                                reject(XMLHttp.response)
                            }
                        }
                    }
                }
            }
            let parametros:string = "";

            if(stringParams){
                parametros = "&";
                stringParams.forEach(p=>{
                    parametros += p.toUrl();
                });
            }

            XMLHttp.open("delete", url + this.autenticacaoService.obterTokenParaUrl() + parametros, true);
            XMLHttp.send(data)
        }).catch(error => this.handleErrorUpload(error));
        responsePromise["url"] = url;
        EventEmitterService.get("onSubmitLoading").emit(responsePromise);

        return responsePromise;
    }
    

    private redirecionaPaginaLogin(): Promise<any> {
        let erroMensagem: ErroMensagem = new ErroMensagem(8888, "");
        EventEmitterService.get("mensagemAppModal").emit("Sessão expirada!");
        return Observable.throw(erroMensagem).toPromise();
    }
}