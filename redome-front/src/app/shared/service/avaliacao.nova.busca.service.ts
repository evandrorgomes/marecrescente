import { Injectable } from "@angular/core";
import { environment } from "environments/environment";
import { BaseService } from "../base.service";
import { HttpClient } from "../httpclient.service";

/**
 * @description Chamadas ao back-end envolvendo a entidade
 * AvaliacaoNovaBusca. 
 * 
 * @author Pizão
 * @extends {BaseService}
 */
@Injectable()
export class AvaliacaoNovaBuscaService extends BaseService{
    
    constructor(http: HttpClient) {
        super(http);
    }

    /**
     * Lista todas as tarefas pendentes de avaliação.
     * 
     * @param pagina número da página a ser exibida.
     * @param qtdRegistros quantidade registros por página.
     */
    public listarTarefas(pagina: number, qtdRegistros: number): Promise<any> {
        return this.http.get(
            environment.endpoint + "avaliacoesnovabusca/tarefas?pagina=" + pagina + "&quantidadeRegistros=" + qtdRegistros)
                        .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * @description Aprova a avaliação para nova busca do paciente.
     * @author Pizão
     * @param {number} idAvaliacao
     * @returns {Promise<any>}
     */
    public aprovar(idAvaliacao: number): Promise<any> {
        return this.http.put(
            environment.endpoint + "avaliacoesnovabusca/" + idAvaliacao + "/aprovar")
                        .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * @description Reprova a avaliação e recusa a criação da nova busca.
     * @author Pizão
     * @param {number} idAvaliacao
     * @param {string} justificativa
     * @returns {Promise<any>}
     */
    public reprovar(idAvaliacao: number, justificativa: string): Promise<any> {
        return this.http.put(
            environment.endpoint + "avaliacoesnovabusca/" + idAvaliacao + "/reprovar?justificativa=" + justificativa)
                        .then(super.extrairDado).catch(super.tratarErro);
    }

}