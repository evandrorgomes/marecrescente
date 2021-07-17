import { Injectable } from "@angular/core";
import { environment } from 'environments/environment';
import { BaseService } from "../base.service";
import { Usuario } from "../dominio/usuario";
import { HttpClient } from "../httpclient.service";
import { UrlParametro } from "../url.parametro";
import { ArrayUtil } from "../util/array.util";


/**
 * Classe de serviço para interação com a tabela de laboratorio
 * 
 * @author Bruno Sousa
 * @export
 * @class LaboratorioService
 * @extends {BaseService}
 */
@Injectable()
export class LaboratorioService extends BaseService {

    address: string = "laboratorios";

/**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Bruno Sousa
     */
    constructor(http: HttpClient) {
        super(http);
    }

    listarLaboratorios(): Promise<any> {
        return this.http.get(environment.endpoint + "laboratorios").then(this.extrairDado).catch(this.tratarErro);
    }

    listarLaboratoriosCT(parametros: UrlParametro[] = null, pagina?: number, quantidadeRegistros?: number): Promise<any> {

        let queryString: string = '?pagina=' + pagina + '&quantidadeRegistros=' + quantidadeRegistros;

        if(ArrayUtil.isNotEmpty(parametros)){
            queryString += "&" + super.toURL(parametros);
        }        

        return this.http.get(environment.endpoint + "laboratorios/ct" + queryString)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    listarLaboratoriosCTExame(): Promise<any> {
        return this.http.get(environment.endpoint + "laboratorios/ct/exame").then(this.extrairDado).catch(this.tratarErro);
    }

/**
     * Método para enviar email para laboratorio com exame divergente.
     * 
     * @param {FormData} data 
     * @param {Exame} exame 
     * @returns {Promise<any>} 
     * @memberof ExameService
     */
    public enviarEmailExameDivergente(id, idMatch: number, destinatarios, texto: string): Promise<any> {
        const data: FormData = new FormData();
        data.append("idMatch", new Blob([JSON.stringify(idMatch)], {
            type: 'application/json'
        }), '');
        data.append("destinatarios", new Blob([JSON.stringify(destinatarios)], {
            type: 'application/json'
        }), '');
        data.append("texto", new Blob([JSON.stringify(texto)], {
            type: 'application/json'
        }), '');


        return this.http.fileUpload(environment.endpoint + "laboratorios/" + id + "/enviaremailexamedivergente", data)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Obtém um laboratorio por id.
     */
    public obterLaboratorio(idLaboratorio:number): Promise<any>{
        return this.http.get(environment.endpoint + 'laboratorios/' + idLaboratorio)
                    .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Atualiza lista de usuarios de laboratório.
     * @param idLaboratorio id do laboratório a ser atualizada.
     * @param usuarios lista de usuarios novos.
    */
    public atualizarUsuarios(idLaboratorio: number, usuarios: Usuario[]): Promise<any>{
        return this.http.put(environment.endpoint + 'laboratorios/' + idLaboratorio + '/atualizarusuarios', usuarios)
            .then(this.extrairDado).catch(this.tratarErro);
    }

}