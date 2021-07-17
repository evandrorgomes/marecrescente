import { Injectable } from '@angular/core';
import { BaseService } from '../base.service';
import { HttpClient } from '../httpclient.service';
import { environment } from '../../../environments/environment';

/**
 * Classe de métodos de consumo do endpoint de tipo de ocorrencia de registro.
 * @author Filipe Paes
 */
@Injectable()
export class RegistroTipoOcorrenciaService extends BaseService{
    private address:string = "tipoocorrencia";

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     */
    constructor(http: HttpClient) {
        super(http);
    } 


    /**
     * Lista tipos de ocorrencia.
     */
    listar(): Promise<any>{
        return this.http.get(`${environment.endpoint}${this.address}`)
        .then(this.extrairDado).catch(this.tratarErro);
    }

}