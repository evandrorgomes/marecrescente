import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import 'rxjs/add/operator/map';
import { BaseService } from '../../shared/base.service';
import { HttpClient } from '../../shared/httpclient.service';

/**
 * Classe de Serviço utilizada para acessar os serviços REST do Perfil de Acesso.
 * 
 * @author Thiago Moraes
 */
@Injectable()
export class PerfilService extends BaseService{

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
    listarPerfis(): Promise<any> {
        return this .http.get(environment.endpoint + "perfis")
                    .then(this.extrairDado).catch(this.tratarErro);
    }

    listarUsuarios(id: number): Promise<any> {
        return this.http.get(`${environment.authEndpoint}api/perfil/${id}/usuarios`)
           .then(this.extrairDado).catch(this.tratarErro);
    }


}