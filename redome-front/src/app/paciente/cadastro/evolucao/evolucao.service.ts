import { promise } from 'protractor';
import { Pendencia } from '../../avaliacao/pendencia';
import { environment } from 'environments/environment';
import { Evolucao } from './evolucao';
import { HttpClient } from '../../../shared/httpclient.service';
import { Injectable } from '@angular/core';
import { BaseService } from '../../../shared/base.service';
import { UrlParametro } from '../../../shared/url.parametro';
import { Response } from '@angular/http';
import { ErroMensagem } from '../../../shared/erromensagem';
import { Observable } from 'rxjs';
import { FileItem } from 'ng2-file-upload';

/**
 * Classe utilitária que realiza as solicitações REST as informações de Evolucao.
 * @author Rafael Pizão
 */
@Injectable()
export class EvolucaoService extends BaseService {
    public static URL: string = "evolucoes";
    private addressSalvarArquivoDraft: string =  "arquivosevolucao";
    private recurso_download: string = "download";
    private caminhoArquivoExame:string = "arquivosevolucao";

    constructor(protected http: HttpClient) {
        super(http);
    }

    /**
     * Método que vai ao servidor REST e inclui uma nova evolução
     * @param evolucao referência de uma evolucao recebido da tela
     * @param arquivosEvolucao arquivos de evolução para serem enviados junto a evolução
     * @return Promise<CampoMensagem[]> retorno ajax com campos de mensagem
     * @author Filipe Paes
     */
    incluir(evolucao: Evolucao, arquivosEvolucao: Map<string, FileItem>): Promise<any> {
        let data: FormData = new FormData();

        data.append("evolucao", new Blob([JSON.stringify(evolucao)], {
            type: 'application/json'
        }), '');
    
        if (arquivosEvolucao) {
            arquivosEvolucao.forEach((item: FileItem, key: string) => {
                data.append("file", item._file, item.file.name);
            });
        }

        return this.http.fileUpload(environment.endpoint + EvolucaoService.URL + "/salvar", data)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método para obter uma evolução por id
     * 
     * @param {number} id 
     * @returns {Promise<Evolucao>} Evolução localizada por id
     * @memberof EvolucaoService
     */
    obterEvolucao(id: number): Promise<Evolucao>{
        return this.http.get(environment.endpoint + EvolucaoService.URL + "/" +id ).then(this.extrairDado).catch(this.tratarErro);
    }

     /**
     * Método para obter a ultima evolução por rmr
     * 
     * @param {number} rmr 
     * @returns {Promise<Evolucao>} Evolução localizada por rmr
     * @memberof EvolucaoService
     */
    obterUltimaEvolucao(rmr: number): Promise<Evolucao>{
        return this.http.get(environment.endpoint + EvolucaoService.URL + "/ultima/" +rmr ).then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método que vai ao servidor REST e inclui uma nova evolução respondendo uma ou mais pendências
     * @param evolucao referência de uma evolucao recebido da tela
     * @return Promise<CampoMensagem[]> retorno ajax com campos de mensagem
     * @author Bruno Sousa
     */
    incluirRespondendoPendencia(evolucao: Evolucao, pendencias: Pendencia[], resposta: string, respondePendencia: boolean, arquivosEvolucao: Map<string, FileItem>): Promise<Object> {
        let data: FormData = new FormData();
        data.append("evolucao", new Blob([JSON.stringify(evolucao)], {
            type: 'application/json'
        }), '');

        data.append("pendencias", new Blob([JSON.stringify(pendencias)], {
            type: 'application/json'
        }), '');

        data.append("resposta", new Blob([JSON.stringify(resposta)], {
            type: 'application/json'
        }), '');

        data.append("respondePendencia", new Blob([JSON.stringify(respondePendencia)], {
            type: 'application/json'
        }), '');

        if (arquivosEvolucao) {
            arquivosEvolucao.forEach((item: FileItem, key: string) => {
                data.append("file", item._file, item.file.name);
            });
        }

        return this.http.fileUpload(environment.endpoint + EvolucaoService.URL + "/respostas", data).then(this.extrairDado).catch(this.tratarErro);
    }

 /**
     * Método que vai salvar o arquivo
     * @param data arquivo a ser enviado.
     * @return promise retorno ajax com campos de mensagem
     * @author Filipe Paes
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

     /**
     * Realiza o download do arquivo associado ao ID do arquivo avaliação informado.
     * 
     * @param id ID do arquivo evolução a ser pesquisado.
     */
    public baixarArquivoEvolucao(arquivoAvaliacaoId: Number, nomeArquivo:String):void{
        super.download(this.caminhoArquivoExame + "/" + arquivoAvaliacaoId + "/" + this.recurso_download, 
                    false, "" + nomeArquivo);
    }
}