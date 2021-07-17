import {EventEmitterService} from '../shared/event.emitter.service';
import { Notificacao } from './notificacao';
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
 * Classe de Serviço utilizada para acessar os serviços REST de notificação. 
 * @author Rafael Pizão
 */
@Injectable()
export class NotificacaoService extends BaseService {
    
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
     * Marca a ciência na notificação (foi visualizada e está OK).
     * 
     * @param notificacao notificação a ser atualizada.
     */
    public marcarCienteNaNotificacaoSelecionada(idNotificacao:number){
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        return this.http.put(environment.notificacaoEndpoint + "notificacoes/" + idNotificacao+"/lida")
            .then(this.extrairDado).catch(this.tratarErro);
    }

    public listarNotificacoes(idCentroTransplante, rmr: number, meusPacientes: boolean, pagina, quantidadeRegistro: number): Promise<any> {
        let parametros: String = "";
        if (idCentroTransplante != null) {
            parametros = "?idCentroTransplante=" + idCentroTransplante;
        }
        if (rmr != null) {
            parametros += (parametros == "" ? "?" : "&") + "rmr=" + rmr;
        }
        if (meusPacientes != null) {
            parametros += (parametros == "" ? "?" : "&") + "meusPacientes=" + meusPacientes;
        }

        parametros += (parametros == "" ? "?" : "&") + "pagina=" + pagina + "&quantidadeRegistro=" + quantidadeRegistro;

        return this.http.get(environment.notificacaoEndpoint + "notificacoes" + parametros)
            .then(this.extrairDado).catch(this.tratarErro);
    }

}