import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import 'rxjs/add/operator/map';
import { BaseService } from '../base.service';
import { HttpClient } from '../httpclient.service';

/**
 * Classe de Serviço utilizada para acessar os serviços REST do Sistema.
 * 
 * @author Bruno Sousa
 */
@Injectable()
export class TipoExameService extends BaseService {

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
     * Lista todos os tipos de exame para fase 2 nacional. 
     * 
     * @return json com os tipos de exames.
     */
    listarTiposExameFase2Nacional(): Promise<any> {
        return this.http.get(environment.endpoint + "tiposexame/fase2nacional")
                    .then(this.extrairDado).catch(this.tratarErro);
    }

}