import { Observable } from 'rxjs';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { Injectable } from '@angular/core';
import { environment } from "environments/environment";
import { HttpClient } from "../httpclient.service";
import { BaseService } from "../base.service";

/**
 * Classe de serviço para interação com a tabela de telefone
 */
@Injectable()
export class ContatoTelefonicoService extends BaseService{
    address: string = "contatostelefonicos";

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
     * Método que deleta um contato telefonico pelo identificador.
     * @param idContatoTelefonico 
     */
    excluirContatoTelefonicoPorId(idContatoTelefonico:number): Promise<any> {
        return this.http.delete(environment.endpoint + this.address + '/'+idContatoTelefonico)
        .then(this.extrairDado).catch(this.tratarErro);
    }

       /**
     * Método que obtem um contato telefonico pelo identificador.
     * @param idContatoTelefonico 
     */
    obterContatoTelefonicoPorId(idContatoTelefonico:number): Promise<any> {
        return this.http.get(environment.endpoint + this.address + '/'+idContatoTelefonico)
        .then(this.extrairDado).catch(this.tratarErro);
    }

}