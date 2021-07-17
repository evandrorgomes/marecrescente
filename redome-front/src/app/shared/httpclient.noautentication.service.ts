import { Injectable } from '@angular/core';
import { Http, RequestOptionsArgs, Response } from '@angular/http';
import { AbstractHttpService } from './abstract.http.service';
import { EventEmitterService } from './event.emitter.service';
import { UrlParametro } from './url.parametro';


/**
 * Classe utilitária que realiza as solicitações ajax no servidor REST
 * @author Filipe Paes
 */
@Injectable()
export class HttpClientNoAutentication extends AbstractHttpService {
   
    /**
  * Método construtor.
  * @param http atributo do tipo Http que faz o acesso não configurado ao servidor REST
  * @author bruno.sousa
  */
    constructor(public http: Http) {
        super();
    }


    /**
     * Método que faz as solicitações via GET no servidor REST.
     * @param url endereço do serviço a ser acessado
     * @return Promise<Response> retorno da solicitação GET feita ao servidor
     * @author bruno.sousa
     */
    get(url: string, requestOptions?:RequestOptionsArgs): Promise<Response> { 

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
        
        let responsePromise:Promise<Response> = this.http.post(url, data, requestOptions)
            .catch(error => this.handleError(error)).toPromise();
        responsePromise["url"] = url;
        EventEmitterService.get("onSubmitLoading").emit(responsePromise);

        return responsePromise;
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
                parametros = "?";
                let concatenador: string = "";
                stringParams.forEach(p=>{
                    parametros += concatenador + p.toUrl();
                    concatenador = "&"
                });
            }

            XMLHttp.open("post", url + parametros, true);
            XMLHttp.send(data)
        }).catch(error => this.handleErrorUpload(error));
        responsePromise["url"] = url;
        EventEmitterService.get("onSubmitLoading").emit(responsePromise);

        return responsePromise;
    }

}