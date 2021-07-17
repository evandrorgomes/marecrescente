import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs';
import { BaseService } from '../../../shared/base.service';
import { environment } from 'environments/environment';
import { HttpClient } from '../../../shared/httpclient.service';

/**
 * Classe utilitária que realiza as solicitações ao controller ArquivoExame no back-end.
 * @author Rafael Pizão
 */
@Injectable()
export class ArquivoExameService extends BaseService {
    private caminho: string = "arquivosexame";
    private recurso_download: string = "download";
    private recurso_download_zipado: string = "download/zip";
    private addressSalvarArquivoDraft: string = this.caminho + "/salvarArquivo";

    constructor(_httpClient: HttpClient) {
        super(_httpClient);
    }

    /**
     * Realiza a visualização do arquivo (se o tipo for suportado pelo browser, 
     * caso contrário, irá baixá-lo) associado ao ID do arquivo exame informado.
     * 
     * @param id ID do exame a ser pesquisado.
     */
    public visualizarArquivoExame(arquivoExameId: Number):void{
        super.download(this.caminho + "/" + 
                    arquivoExameId + "/" + this.recurso_download, true);
    }

    /**
     * Realiza o download do arquivo associado ao ID do arquivo exame informado.
     * 
     * @param id ID do exame a ser pesquisado.
     */
    public baixarArquivoExame(arquivoExameId: Number, nomeArquivo:String):void{
        super.download(this.caminho + "/" + 
                    arquivoExameId + "/" + this.recurso_download, 
                    false, "" + nomeArquivo);
    }

    /**
     * Realiza o download do arquivo zipado associado ao ID do arquivo exame informado.
     * 
     * @param id ID do exame a ser pesquisado.
     */
    public baixarArquivoExameZipado(arquivoExameId: Number, nomeArquivo:String):void{
        super.download(this.caminho + "/" + 
                    arquivoExameId + "/" + this.recurso_download_zipado, 
                    false, nomeArquivo + ".zip");
    }

    public removerArquivo(usuarioId: number, nomeArquivo: string): Promise<Response> {
        return super.removerArquivo(usuarioId, nomeArquivo);
    }

    /**
     * Método que vai salvar o arquivo
     * @param idUsuario referência do usuário logado
     * @return Observable<CampoMensagem[]> retorno ajax com campos de mensagem
     * @author Rafael Pizão
     */
    salvarArquivo(data: FormData): Promise<Object> {
        
        return this.http.fileUpload(environment.endpoint + this.addressSalvarArquivoDraft, data)
            .catch(this.tratarErro)
            .then(this.extrairDadoUpload);
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


}