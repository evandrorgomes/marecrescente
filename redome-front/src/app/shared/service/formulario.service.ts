import { Injectable } from "@angular/core";
import { BaseService } from "app/shared/base.service";
import { Formulario } from 'app/shared/classes/formulario';
import { HttpClient } from "app/shared/httpclient.service";
import { environment } from "environments/environment";
import { ResultadoWorkupNacionalDTO } from "../dto/resultado.workup.nacional.dto";
import { TiposFormulario } from "../enums/tipo.formulario";

/**
 * Classe de serviço para interação com formulário.
 *
 * @author ergomes
 * @export
 * @class FormularioService
 * @extends {BaseService}
 */
@Injectable()
export class FormularioService extends BaseService {

/**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é
     * injetada e atribuída como atributo
     * @author ergomes
     */
    constructor(http: HttpClient) {
        super(http);
    }

    public salvarFormularioComPedidoWorkupEValidacao(id: number, formulario: Formulario, comValidacao: boolean): Promise<any> {
        formulario.comValidacao = comValidacao;
        return this.http.post(environment.endpoint + "formularios/" + id + "/formulariopedidoworkup", formulario).
            then(this.extrairDado).catch(this.tratarErro);
    }

    public salvarFormularioComPedidoContatoEValidacao(idPedidoContato: number, formulario: Formulario, comValidacao: boolean): Promise<any> {
        formulario.comValidacao = comValidacao;
        return this.http.post(environment.endpoint + "formularios/" + idPedidoContato + "/formulariopedidocontato", formulario).
            then(this.extrairDado).catch(this.tratarErro);
    }

    public obterFormulario(id: number, tipoFormulario: TiposFormulario): Promise<any> {

        return this.http.get(environment.endpoint + "formularios/" + id + "/formulario?tipo=" + tipoFormulario)
                                .then(this.extrairDado).catch(this.tratarErro);
    }

    public finalizarFormularioResultadoWorkup(id: number, resultadoWorkupNacionalDTO: ResultadoWorkupNacionalDTO, formulario: Formulario): Promise<any> {

      let data: FormData = new FormData();
      data.append("resultadoWorkupNacionalDTO", new Blob([JSON.stringify(resultadoWorkupNacionalDTO)], {
        type: 'application/json'
      }), '');

      data.append("formulario", new Blob([JSON.stringify(formulario)], {
        type: 'application/json'
      }), '');

      return this.http.fileUpload(environment.endpoint + "formularios/" + id + "/finalizarformularioresultadoworkup", data)
          .then(this.extrairDado)
          .catch(this.tratarErro);
    }

    public finalizarFormularioPedidoWorkup(id: number, formulario: Formulario): Promise<any> {
      return this.http.post(environment.endpoint + "formularios/" + id + "/finalizarformulariopedidoworkup", formulario).
          then(this.extrairDado).catch(this.tratarErro);
    }

}
