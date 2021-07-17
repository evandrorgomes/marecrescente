import { Injectable } from "@angular/core";
import { BaseService } from "app/shared/base.service";
import { HttpClient } from "app/shared/httpclient.service";
import { Diagnostico } from "app/paciente/cadastro/diagnostico/diagnostico";
import { environment } from "environments/environment";

/**
 * Classe de Serviço utilizada para acessar os serviços REST de diagnostico 
 * @author brunosousa
 */
@Injectable()
export class DiagnosticoService extends BaseService {
    
    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     */
    constructor(http: HttpClient) {
        super(http);
    }

    /**
     * Método que vai ao servidor REST e altera o diagnostico de um paciente
     * @param rmr referência do paciente
     * @param diagnostico Referêncoa do diagnostico recevido da tela.
     * @return Promise<CampoMensagem> retorno a mensagem de sucesso
     */
    alterarDiagnostico(rmr: number, diagnostico: Diagnostico): Promise<any> {
        let data = JSON.stringify(diagnostico);

        return this.http.post(environment.endpoint + "diagnosticos/" + rmr, data)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }
}