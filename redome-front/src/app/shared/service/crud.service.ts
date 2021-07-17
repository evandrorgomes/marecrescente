import { BaseService } from "../base.service";
import { HttpClient } from "../httpclient.service";
import { Injectable } from "@angular/core";
import { environment } from 'environments/environment';


/**
 * Classe de serviço para interação com o crud
 * 
 * @author Bruno Sousa
 * @export
 * @class CrudService
 * @extends {BaseService}
 */
@Injectable()
export class CrudService extends BaseService {
    
/**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Bruno Sousa
     */
    constructor(http: HttpClient) {
        super(http);
    }

    listar(entidade: string, pagina: number, quantidadeRegistros: number): Promise<any> {
        return this.http.get(environment.endpoint + entidade + '?pagina='
            + pagina + '&quantidadeRegistros=' + quantidadeRegistros)
            .then(this.extrairDado).catch(this.tratarErro);
    }

}