import { Injectable } from "@angular/core";
import { BaseService } from "../base.service";
import { HttpClient } from "../httpclient.service";
import { ComentarioMatch } from "../classes/comentario.match";
import { EventEmitterService } from "../event.emitter.service";
import { environment } from "environments/environment";

/**
 * @description Classe de serviço para interação com a classe de comentário match
 * 
 * @author Bruno Sousa
 * @export
 * @class ComentarioMatchService
 * @extends {BaseService}
 */
@Injectable()
export class ComentarioMatchService extends BaseService{
    address: string = "comentariosmatch";

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Bruno Sousa
     */
    constructor(http: HttpClient) {
        super(http);
    }

    incluirComentarioMatch(comentarioMatch: ComentarioMatch): Promise<any> {
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        let body = JSON.stringify(comentarioMatch)
        console.log(body);
        return this .http.post(environment.endpoint + this.address , body)
                .then(this.extrairDado).catch(this.tratarErro);
    }

}