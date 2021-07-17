import { Observable } from 'rxjs';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { Injectable } from '@angular/core';
import { environment } from "environments/environment";
import { HttpClient } from "../httpclient.service";
import { BaseService } from "../base.service";
import { EnderecoContato } from '../classes/endereco.contato';

/**
 * Classe de serviço para interação com a tabela de endereço.
 */
@Injectable()
export class EnderecoContatoService extends BaseService{
    address: string = "enderecoscontatos";

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
     * Método que deleta um endereço de contato pelo identificador.
     * @param idEnderecoContato 
     */
    excluirEnderecoContatoPorId(idEnderecoContato:number): Promise<any> {
        return this.http.delete(environment.endpoint + this.address + '/'+idEnderecoContato)
            .then(this.extrairDado).catch(this.tratarErro);
    }

}