import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import 'rxjs/add/operator/map';
import { BaseService } from '../base.service';
import { HttpClient } from '../httpclient.service';


/**
 * Classe de Serviço utilizada para acessar os serviços REST do Municipio.
 *
 * @author Bruno Sousa
 * @export
 * @class MunicipioService
 * @extends {BaseService}
 */
@Injectable()
export class MunicipioService extends BaseService {

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
     * Lista todos os municipios contidos na base do Redome.
     * @return json com os municipios presentes na base.
     */
    listarMunicipios(): Promise<any> {
        return this.http.get(environment.endpoint + "municipios")
            .then(this.extrairDado).catch(this.tratarErro);
    }

}