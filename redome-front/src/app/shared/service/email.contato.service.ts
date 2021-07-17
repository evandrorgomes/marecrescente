import { Observable } from 'rxjs';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { Injectable } from '@angular/core';
import { environment } from "environments/environment";
import { HttpClient } from "../httpclient.service";
import { BaseService } from "../base.service";

/**
 * Classe de serviço para interação com a tabela de email
 */
@Injectable()
export class EmailContatoService extends BaseService{
    address: string = "emailscontato";

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
     * @param idEmailContato 
     */
    excluirEmailContatoPorId(idEmailContato:number): Promise<any> {
        return this.http.delete(environment.endpoint + this.address + '/'+idEmailContato)
        .then(this.extrairDado).catch(this.tratarErro);
    }

}