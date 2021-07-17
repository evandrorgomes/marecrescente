import { Injectable } from "@angular/core";
import { BaseService } from "app/shared/base.service";
import { HttpClient } from "app/shared/httpclient.service";
import { Diagnostico } from "app/paciente/cadastro/diagnostico/diagnostico";
import { environment } from "environments/environment";

/**
 * Centraliza as chamadas aos serviços do controller homônino no back-end.
 * 
 * @author Pizão
 */
@Injectable()
export class BuscaChecklistService extends BaseService {
    
    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     */
    constructor(http: HttpClient) {
        super(http);
    }

    /**
     * @description Realiza a marcação de visto para o checklist do doador.     
     * @param {number} id
     * @returns {Promise<any>}
     */
    marcarVisto(id: number): Promise<any> {
        return this.http.put(`${environment.endpoint}checklist/${id}/visto`)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    /**
     * @description Realiza a marcação de visto para uma lista de checklist do doador.
     * @param {number} id
     * @returns {Promise<any>}
     */
    marcarListaDeVistos(listaIdsChecklists: number[]): Promise<any> {
        let idsString: string = listaIdsChecklists.join(",");
        let params = "?listaIdsChecklists=" + idsString;
        return this.http.put(`${environment.endpoint}checklist/vistos` + params)
        .catch(this.tratarErro)
        .then(this.extrairDado);
    }

    /**
     * @description Realiza a marcação de visto para o checklist da analise da busca.
     * @param {number} id - id do doador
     * @returns {Promise<any>}
     */
    marcarVistoDaAnaliseBusca(id: number): Promise<any> {
        return this.http.put(`${environment.endpoint}checklist/${id}/analise/visto`)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    /**
     * @description Realiza a marcação de visto para o checklist da busca pelo tipo de checklist.
     * @param {number} idBusca - id da Busca
     * @param {number} idTipoChecklist - id do TipoChecklist
     * @returns {Promise<any>}
     */
    marcarVistoDaBusca(idBusca: number, idTipoChecklist: number): Promise<any> {
        return this.http.get(environment.endpoint + "checklist/busca/visto?idBusca=" + idBusca + "&idTipoChecklist=" + idTipoChecklist)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

}