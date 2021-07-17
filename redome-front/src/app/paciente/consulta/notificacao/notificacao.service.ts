import { Injectable } from "@angular/core";
import { environment } from "environments/environment";
import { BaseService } from "../../../shared/base.service";
import { HttpClient } from "../../../shared/httpclient.service";


/**
 * Classe de Serviço utilizada para acessar os serviços REST de notificação
 * @author Queiroz
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

    public marcarCienteNaNotificacaoSelecionada(idNotificacao: number) {
        return this.http.put(environment.notificacaoEndpoint + 'notificacoes/' + idNotificacao + '/lida')
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }


}