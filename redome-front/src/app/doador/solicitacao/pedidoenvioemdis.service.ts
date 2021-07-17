import { Injectable } from '@angular/core';
import {BaseService} from '../../shared/base.service';
import { HttpClient } from '../../shared/httpclient.service';
import { environment } from '../../../environments/environment';

/**
 * Registra as chamadas ao back-end envolvendo a entidade PedidoEnvioEmdis.
 * 
 * @author Filipe Paes
 */
@Injectable()
export class PedidoEnvioEmdisService extends BaseService {

    private address: string = 'pedidosenvioemdis';
    
    constructor(protected http: HttpClient) {
        super(http);
    }

    enviarPacienteParaEmdis(buscaid:number):Promise<any>{
        return this.http.post(`${environment.endpoint}${this.address}/?idbusca=${buscaid}`)
        .then(this.extrairDado).catch(this.tratarErro);
    }
}