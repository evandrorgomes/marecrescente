import { Injectable } from "@angular/core";
import { HttpClient } from "app/shared/httpclient.service";
import { Diagnostico } from "app/paciente/cadastro/diagnostico/diagnostico";
import { environment } from "environments/environment";
import { UrlParametro } from '../url.parametro';
import { BaseService } from "../base.service";

/**
 * Classe de Serviço utilizada para acessar os serviços REST de diagnostico
 * @author brunosousa
 */
@Injectable()
export class LocusExameService extends BaseService {

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
    inativarPorDescarte(buscaId, examesId: number[], locus: string): Promise<any> {

        const data: FormData = new FormData();

        data.append('examesId', new Blob([JSON.stringify(examesId)], {
            type: 'application/json'
        }), '');

        data.append('locus', new Blob([JSON.stringify(locus)], {
            type: 'application/json'
        }), '');

        return this.http.fileUploadWithPut(environment.endpoint + 'exames/doador/descartarLocus', data, [new UrlParametro('buscaId', buscaId+'')])
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }
}
