import { UsuarioLogado } from '../../shared/dominio/usuario.logado';
import {AutenticacaoService} from '../../shared/autenticacao/autenticacao.service';
import { RespostaPendencia } from './resposta.pendencia';
import { Pendencia } from './pendencia';
import { TipoPendencia } from '../../shared/dominio/tipo.pendencia';
import { HttpClient } from '../../shared/httpclient.service';
import { environment } from 'environments/environment';
import { BaseService } from '../../shared/base.service';
import { Injectable } from '@angular/core';

/**
 * Classe de Serviço utilizada para acessar os serviços REST de pendência
 * 
 * @author Bruno  Sousa
 * @export
 * @class PendenciaService
 * @extends {BaseService}
 */
@Injectable()
export class PendenciaService  extends BaseService {
    private address: string = 'pendencias';
    private fecharPendenciaAddress = '/fechar';
    private cancelarPendenciaAddress = '/cancelar';
    private reabrirPendenciaAddress = '/reabrir';
    private tiposPendenciaAddress: string = '/tipos';

    constructor(http: HttpClient, private autenticacaoService:AutenticacaoService) {
        super(http);
    }

    /**
     * Método que vai ao servidor REST e cancela uma pendência
     * 
     * @param {number} id 
     * @returns 
     * 
     * @memberOf PendenciaService
     */
    cancelarPendencia(id: number) {
        return this.http.put(environment.endpoint + this.address + "/" + id + this.cancelarPendenciaAddress)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método que vai ao servidor REST e fecha uma pendência
     * 
     * @param {number} id 
     * @returns 
     * 
     * @memberOf PendenciaService
     */
    fecharPendencia(id: number) {
        return this.http.put(environment.endpoint + this.address + "/" + id + this.fecharPendenciaAddress)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Lista a conversa entre o medico e o avaliador
     * 
     * @param {number} idPendencia 
     * @memberof PendenciaService
     */
    listarHistoricoPendencia(idPendencia: number){
        return this.http.get(environment.endpoint + this.address + "/" + idPendencia + "/respostas")
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Cria uma nova conversa entre o medico e o avaliador
     * 
     * @param {number} idPendencia 
     * @param {RespostaPendencia} respostaPendencia 
     * @returns 
     * @memberof PendenciaService
     */
    responderPendencia(idPendencia: number, respostaPendencia:RespostaPendencia){
        let data = JSON.stringify(respostaPendencia);
        return this.http.post(environment.endpoint + this.address + "/" + idPendencia+"/responder", data)
            .then(this.extrairDado).catch(this.tratarErro);
        
    }

/**
     * Cria uma nova conversa entre o medico e o avaliador
     * 
     * @param {number} idPendencia 
     * @param {RespostaPendencia} respostaPendencia 
     * @returns 
     * @memberof PendenciaService
     */
    reabrirPendencia(idPendencia: number, respostaPendencia:RespostaPendencia){
        let data = JSON.stringify(respostaPendencia);
        
        return this.http.post(environment.endpoint + this.address + "/" + idPendencia+"/reabrir", data)
            .then(this.extrairDado).catch(this.tratarErro);
        
    }



    /**
     * Retorna a lista de tipos de pendência.
     * 
     */
    listarTiposPendencia():Promise<TipoPendencia[]>{
        return this.http.get(environment.endpoint + this.address + this.tiposPendenciaAddress)
            .then(this.extrairDado).catch(this.tratarErro);
    }

}