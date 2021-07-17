import { HttpClient } from '../../../shared/httpclient.service';
import { BaseService } from '../../../shared/base.service';
import { Response } from '@angular/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';

/**
 * Classe de Serviço utilizada para acessar os serviços REST de classificacao de abo. 
 * @author Fillipe Queiroz
 */
@Injectable()
export class ClassificacaoABOService extends BaseService {
    endereco: string = "classificacoesabo";

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Fillipe Queiroz
     */
    constructor(http: HttpClient) {
        super(http);
    }

    /**
     * Metodo para buscar todas as classificacoes ABO
     */
    public listarClassificacaoABO() {
        return this.http.get(environment.endpoint + this.endereco)
            .then(this.extrairDado).catch(this.tratarErro);
    }



}