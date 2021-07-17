import { RequestOptionsArgs, Response, ResponseContentType } from '@angular/http';
import { environment } from 'environments/environment';
import * as FileSaver from 'file-saver';
import 'rxjs/add/operator/map';
import { ErroMensagem } from './erromensagem';
import { HttpClient } from './httpclient.service';
import { UrlParametro } from './url.parametro';
import { StringUtil } from './util/string.util';
import { MessageBox } from './modal/message.box';
import { CampoMensagem } from './campo.mensagem';

/**
 * Classe base para os serviços no front-end. 
 * @author Rafael Pizão
 */
export class BaseService {
    
    constructor(protected http: HttpClient, private messageBox?: MessageBox) {}

    /**
     * Recuperar informações via GET no back-end.
     *  
     * @param url 
     * @param parametros 
     */
    get(url: string, sucessoCallBack?: (result: any) => void, 
            falhaCallBack?: (e: ErroMensagem) => void, parametros?: UrlParametro[]): void{
        let parametrosUrl: String = this.toURL(parametros);
        
        this.http.get(environment.endpoint + url + "?" + parametrosUrl)
                    .then(this.extrairDado)
                        .then(
                            result => { 
                                if(sucessoCallBack){
                                    sucessoCallBack(result);
                                }
                            },
                            (error: ErroMensagem) => {
                                falhaCallBack(error);
                            }
                        );
    }

    /**
     * Enviar informações via POST ao back-end. 
     * TODO: Utilizar o método POST para envios ao back-end.
     * @param url 
     * @param parametros 
     */
    post(url: string, sucessoCallBack?: (result: any) => void, 
            falhaCallBack?: (e: ErroMensagem) => void, parametros?: any): void{
        this.http.post(environment.endpoint + url, JSON.stringify(parametros) )
                    .then(this.extrairDado)
                    .then(
                        result => { 
                            if(sucessoCallBack){
                                sucessoCallBack(result);
                            }
                        },
                        (error: ErroMensagem) => {
                            falhaCallBack(error);
                        }
                    );
    }

    /**
     * Método para a extração do json contido no response
     * @param res 
     */
    protected extrairDado(res: any) {
        let jsonParsed:any;

        try {
            let body = res.json();
            jsonParsed = body || { };
        } catch (error) {
            try {
                let body = JSON.parse(res.toString());
                jsonParsed = body || { };
            } catch (error) {
                try {
                    jsonParsed = StringUtil.isNullOrEmpty(res.text()) ? null : res._body;
                }
                catch (error) {
                    try {
                        jsonParsed = JSON.parse(res); 
                    }
                    catch (error) {
                        jsonParsed = res;
                    }
                }
            }
        }
        return jsonParsed;
    }

        /**
     * Método para a extração do json contido no response
     * @param res 
     */
    protected extrairDadoContent(res: Response) {
        let jsonParsed:any;

        try {
            let body = res.json()['content'];    
            jsonParsed = body || { };
        } catch (error) {
            jsonParsed = res;
        }
        return jsonParsed;
    }

    /**
     * Método para tratar erro dos serviços
     * 
     * @param {any} res 
     * @memberof BaseService
     */
    public tratarErro(res) {
        //TODO tratar erros de aplicação aqui
        throw res;
        
    }


    /**
     * Transforma os parâmetro no formato esperado pela URL.
     * 
     * @param parametros
     * @return Retorna uma parâmetro string no formato esperado pela URL.
     * Ex.: ...?clienteId=1,2,3,4
     */
    protected toURL(parametros: UrlParametro[]): String{
        if(parametros == null || parametros.length == 0){
            return null;
        }

        let parametroString:String = "";

        parametros.forEach(parametro => {
            let isUltimoParametro: Boolean = 
                (parametros.lastIndexOf(parametro) == (parametros.length - 1));
            parametroString += parametro.toUrl() + (isUltimoParametro ? "": "&");
        });

        return parametroString;
    }

    /**
     * Realiza o chamado ao serviço para download de arquivo. 
     * 
     * @param url URL para o serviço REST
     * @param visualizarSePossivel indica se deve abrir para visualização ou não
     * @param nomeArquivo nome do arquivo sugerido para download 
     * (não utilizado, caso opção acima seja marcada)
     */
    public workupDownload(endpoint:string, url:string, visualizarSePossivel:boolean = false, 
                    nomeArquivo:string = "file.download"):void{

        let requestOptions: RequestOptionsArgs = {
            responseType: ResponseContentType.Blob
        };

        if(visualizarSePossivel){
            this.http.openWindow(endpoint + url, nomeArquivo);
        } else {
            this.http.get(endpoint + url, requestOptions)
                    .then(response => {
                        FileSaver.saveAs(response.blob(), nomeArquivo);
                    (error: ErroMensagem) => {
                        this.exibirMensagemErro(error);
                    }
                }).catch((error: ErroMensagem) => { 
                    this.exibirMensagemErro(error);
                });
        }
        
    }
    


    /**
     * Realiza o chamado ao serviço para download de arquivo. 
     * 
     * @param url URL para o serviço REST
     * @param visualizarSePossivel indica se deve abrir para visualização ou não
     * @param nomeArquivo nome do arquivo sugerido para download 
     * (não utilizado, caso opção acima seja marcada)
     */
    public download(url:string, visualizarSePossivel:boolean = false, 
                    nomeArquivo:string = "file.download"):void{

        let requestOptions: RequestOptionsArgs = {
            responseType: ResponseContentType.Blob
        };

        if(visualizarSePossivel){
            this.http.openWindow(environment.endpoint + url, nomeArquivo);
        } else {
            this.http.get(environment.endpoint + url, requestOptions)
                    .then(response => {
                        FileSaver.saveAs(response.blob(), nomeArquivo);
                    (error: ErroMensagem) => {
                        this.exibirMensagemErro(error);
                    }
                }).catch((error: ErroMensagem) => { 
                    this.exibirMensagemErro(error);
                });
        }
        
    }

    private exibirMensagemErro(error: ErroMensagem) {
        if(error.listaCampoMensagem){
            let errorMsg: CampoMensagem = error.listaCampoMensagem[0];
            this.messageBox.alert(errorMsg.mensagem).show();
        }
        else{
        this.messageBox.alert("Ocorreu um erro ao baixar arquivo.").show();
        }
    }

    protected removerArquivo(usuarioId: number, nomeArquivo: string): Promise<Response> {
        return this.http.delete(environment.endpoint
             + "arquivosexame/" + nomeArquivo + "/usuario/" + usuarioId);
    }
}