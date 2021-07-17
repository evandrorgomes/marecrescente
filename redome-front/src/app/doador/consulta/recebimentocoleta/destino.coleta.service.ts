import { HttpClient } from '../../../shared/httpclient.service';
import { BaseService } from '../../../shared/base.service';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';



/**
 * Registra as chamadas ao back-end envolvendo a DestinoColeta.
 * 
 * @author Filipe Paes
 */
@Injectable()
export class DestinoColetaService extends BaseService {

    constructor(protected http: HttpClient) {
        super(http);
    }


    /**
     * Método para listar destinos de coleta.
     */
    public listarDestinos():Promise<any>{
        return this.http.get(environment.endpoint + "destinoscoleta/")
        .then(this.extrairDado).catch(this.tratarErro);
    }

}