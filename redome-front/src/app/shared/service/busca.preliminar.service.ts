import { Injectable } from "@angular/core";
import { BaseService } from "app/shared/base.service";
import { BuscaPreliminar } from "app/shared/model/busca.preliminar";
import { environment } from "environments/environment";
import { HttpClient } from "app/shared/httpclient.service";
import { FiltroMatch } from "app/shared/enums/filtro.match";
import { EventEmitterService } from "app/shared/event.emitter.service";

/**
 * @description Classe de serviço para interação com a classe de busca preliminar
 * 
 * @author Bruno Sousa
 * @export
 * @class BuscaPreliminarService
 * @extends {BaseService}
 */
@Injectable()
export class BuscaPreliminarService extends BaseService {

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Rafael Pizão
     */
    constructor(http: HttpClient) {
        super(http);
    }


    salvarBuscarPreliminar(buscaPreliminar: BuscaPreliminar): Promise<any> {
        let body = JSON.stringify(buscaPreliminar)
        return this .http.post(environment.endpoint + "buscaspreliminares" , body)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método que obtem do servidor o dto com as listas de matchs de um paciente
     * 
     * @param {number} rmr identificador do paciente
     * @returns {Promise<any>} 
     * @author Bruno Sousa
     * 
     */
    obterAvaliacoesMatch(id: number, filtroMatch: FiltroMatch): Promise<any> {
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        return this.http.get(environment.endpoint + "buscaspreliminares/" + id + "/matchs?filtro=" + filtroMatch)
            .then(this.extrairDado).catch(this.tratarErro);
    }
    /**
     * Obtém a busca preliminar a partir do ID.
     * 
     * @param idBuscaPreliminar ID da busca preliminar.
     * @return a busca devidamente preenchida para ser reutilizada no cadastro do paciente.
     */
    obterBuscarPreliminar(idBuscaPreliminar: number): Promise<any> {
        return this .http.get(environment.endpoint + "buscaspreliminares/" + idBuscaPreliminar)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Serviço fake temporário para simular quando não ocorre 
     * matchs na busca preliminar.
     * 
     * @param buscaPreliminar busca preliminar.
     */
    simularBuscarPreliminar(buscaPreliminar: BuscaPreliminar): Promise<any> {
        let body = JSON.stringify(buscaPreliminar)
        return this .http.post(environment.endpoint + "buscaspreliminares/nomatchs" , body)
                    .then(this.extrairDado).catch(this.tratarErro);
    }


}