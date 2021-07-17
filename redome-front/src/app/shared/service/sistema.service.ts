import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import 'rxjs/add/operator/map';
import { BaseService } from '../base.service';
import { HttpClient } from '../httpclient.service';

/**
 * Classe de Serviço utilizada para acessar os serviços REST do Sistema.
 * 
 * @author Pizão
 */
@Injectable()
export class SistemaService extends BaseService{

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Thiago Moraes
     */
    constructor(http: HttpClient) {
        super(http);
    }

    /**
     * Lista todos os perfis ativos contidos na 
     * base do Redome.
     * @return json com os perfis presentes na base.
     */
    listarSistemas(): Promise<any> {
        return this .http.get(environment.endpoint + "sistemas")
                    .then(this.extrairDado).catch(this.tratarErro);
    }

}