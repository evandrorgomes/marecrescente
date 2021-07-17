import { HttpClient } from '../../../shared/httpclient.service';
import { BaseService } from '../../../shared/base.service';
import { DialogoBusca } from './dialogo.busca';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
/**
 * Classe de Serviço utilizada para acessar os serviços REST de diálogo de busca de paciente. 
 * @author Filipe Paes
 */
@Injectable()
export class DialogoBuscaService extends BaseService {
    endereco: string = "dialogosbusca";

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
     * Lista os diálogos de acordo com a busca.
     * @param idBusca - busca do diálogo.
     */
    listarDialogos(idBusca:number) {
        return this.http.get(environment.endpoint + this.endereco + "/" + idBusca)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Grava o diálogo de busca.
     * @param dialogoBusca - diálogo a ser registrado.
     */
    gravarDialogo(dialogoBusca:DialogoBusca){
        let data = JSON.stringify(dialogoBusca);
        return this.http.post(environment.endpoint + this.endereco, data)
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }
}