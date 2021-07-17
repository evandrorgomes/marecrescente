import { Observable } from 'rxjs';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { Injectable } from '@angular/core';
import { environment } from "environments/environment";
import { HttpClient } from "../httpclient.service";
import { BaseService } from "../base.service";
import { EnderecoContatoMedico } from 'app/shared/classes/endereco.contato.medico';

/**
 * Classe de serviço para interação com a tabela de endereço de contato de centro transplante.
 */
@Injectable()
export class EnderecoContatoMedicoService extends BaseService{

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é
     * injetada e atribuída como atributo
     */
    constructor(http: HttpClient) {
        super(http);
    }

    /**
     * Método que atualiza um endereço de contato pelo identificador.
     * @param id - identificador do endereço de contato do centro de Transplante
     * @param enderecoContato - Endereço de contato a ser alterado.
     */
    atualizarEnderecoContato(id: number, enderecoContato: EnderecoContatoMedico): Promise<any> {
        return this.http.put(environment.endpoint + 'enderecoscontatosmedico/'+id, enderecoContato)
            .then(this.extrairDado).catch(this.tratarErro);
    }

}
