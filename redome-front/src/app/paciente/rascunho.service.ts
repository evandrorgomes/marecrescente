import { environment } from 'environments/environment';
import { BaseService } from '../shared/base.service';
import { ErroMensagem } from '../shared/erromensagem';
import { HttpClient } from '../shared/httpclient.service';
import { Response } from '@angular/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { Paciente } from './paciente';
import { CampoMensagem } from '../shared/campo.mensagem';

/**
 * Classe de Serviço utilizada para acessar os serviços REST de draft (rascunho) de paciente. 
 * @author Rafael Pizão
 */
@Injectable()
export class RascunhoService extends BaseService{
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
     * Salva um novo draft para o paciente informado. 
     * 
     * @param paciente paciente que será salvo como draft.
     */
    public salvar(paciente:Paciente):void{
        let data = JSON.stringify(paciente);
        this.http.post(environment.endpoint + "rascunhos", data)
                 .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Recupera o rascunho, para o usuário logado, no back-end.
     * 
     * @return Paciente associado ao rascunho encontrado. 
     */
    public recuperar():Promise<any>{
        return this.http.get(environment.endpoint + "rascunhos")
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método para deletar o draft associado ao usuário.
     * 
     * @param {number} idUsuario 
     * @memberof RascunhoService
     */
    public excluir():void{
        this.http.delete(environment.endpoint + "rascunhos").catch(this.tratarErro);
    }
}