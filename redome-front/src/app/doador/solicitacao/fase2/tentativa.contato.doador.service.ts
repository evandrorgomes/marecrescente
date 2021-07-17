import { Injectable } from '@angular/core';
import { UrlParametro } from 'app/shared/url.parametro';
import { environment } from 'environments/environment';
import { BaseService } from '../../../shared/base.service';
import { HttpClient } from '../../../shared/httpclient.service';
/**
 * Registra as chamadas ao back-end envolvendo a entidade Tentativa de Contato de DoadorNacional.
 * 
 * @author Filipe Paes
 */
@Injectable()
export class TentativaContatoDoadorService extends BaseService {    
    
    constructor(protected http: HttpClient) {
        super(http);
    }

    carregarTentativaContato(tentativaContatoId:number): Promise<any>{
        return this .http.get(environment.endpoint +
             "tentativascontato/" + tentativaContatoId)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    public atribuirTarefa(idTtentativaContato: number, idTipoTarefa: number): Promise<any>{
        return this .http.put(environment.endpoint +
            "tentativascontato/" + idTtentativaContato + "/tarefa/atribuir?idTipoTarefa="+ idTipoTarefa)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    public atribuirTarefaAoUsuarioLogado(idTtentativaContato: number, idTarefa: number): Promise<any>{
        return this .http.put(environment.endpoint +
            "tentativascontato/" + idTtentativaContato + "/tarefa/atribuirAoUsuarioLogado?idTarefa="+ idTarefa)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método que finalizar
     *
     * @author Bruno Sousa
     * @param {number} id identificador da tentativa de contato do doador.
     * @returns 
     * @memberof TentativaContatoDoadorService
     */
    public finalizarTentativaContatoCriarProximaTentativa(id: number, parametros: UrlParametro[]) {

        let param: String = this.toURL(parametros);

        return this .http.put(environment.endpoint + "tentativascontato/" + id + (param != null ? '?' + param : '')   )
                .then(this.extrairDado).catch(this.tratarErro);
    }


}