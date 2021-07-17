import { Observable } from 'rxjs';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { Injectable } from '@angular/core';
import { environment } from "environments/environment";
import { HttpClient } from "../httpclient.service";
import { BaseService } from "../base.service";
import { ArquivoRelatorioInternacional } from '../classes/arquivo.relatorio.internacional';
import { FileItem } from 'ng2-file-upload';

/**
 * Classe de serviço para interação com a tabela de Arquivo Relatorio Internacional
 */
@Injectable()
export class ArquivoRelatorioInternacionalService extends BaseService {
    
    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Rafael Pizão
     */
    constructor(http: HttpClient) {
        super(http);
    }

    /**
     * Método que deleta um email de contato pelo identificador.
     * @param id - ArquivoVoucherLogistica 
     */
    excluirPorId(id: number): Promise<Object> {
        return this.http.delete(environment.endpoint + 'arquivosrelatoriointernacional/' + id)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método para persistir um novo arquivo de relatório da busca internacional.
     * 
     * @param {FormData} data 
     * @param {Exame} exame 
     * @returns {Promise<any>} 
     * @memberof ExameService
     */
    public salvar(arquivo: FileItem, arquivoRelatorioInternacional: ArquivoRelatorioInternacional): Promise<any> {
        const data: FormData = new FormData();

        data.append("file", arquivo._file, arquivo.file.name);

        data.append("arquivoRelatorioInternacional", new Blob([JSON.stringify(arquivoRelatorioInternacional)], {
            type: 'application/json'
        }), '');

        return this.http.fileUpload(environment.endpoint + 'arquivosrelatoriointernacional', data)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    public downloadZip(id: number, nomeArquivo: string) {
        super.download('arquivosrelatoriointernacional/' +
            id + "/downloadzip" ,
            false, nomeArquivo + ".zip");
    }

    public downloadArquivo(id: number, nomeArquivo: string) {
        super.download('arquivosrelatoriointernacional/' +
            id + "/download" ,
            false, nomeArquivo);
    }

}