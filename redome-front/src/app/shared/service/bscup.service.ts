import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import 'rxjs/add/operator/map';
import { BaseService } from '../base.service';
import { HttpClient } from '../httpclient.service';

/**
 * Classe de Serviço utilizada para acessar os serviços REST da entidade
 * BancoSangueCordao.
 * 
 * @author Pizão
 */
@Injectable()
export class BscupService extends BaseService{

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo.
     */
    constructor(http: HttpClient) {
        super(http);
    }

    /**
     * Lista todos os bancos de sangue de cordão disponíveis
     * base do Redome.
     * @return json com os perfis presentes na base.
     */
    listarBscups(): Promise<any> {
        return this .http.get(environment.endpoint + "bscup")
                    .then(this.extrairDado).catch(this.tratarErro);
    }

}