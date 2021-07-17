import { RessalvaDoadorDTO } from './../../../../shared/dto/ressalva.doador.dto';
import { HttpClient } from '../../../../shared/httpclient.service';
import { BaseService } from "../../../../shared/base.service";
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { RessalvaDoador } from '../../../ressalva.doador';


/**
 * Registra as chamadas ao back-end envolvendo a entidade DoadorNacional.
 * 
 * @author Rafael Pizão
 */
@Injectable()
export class RessalvaService extends BaseService {

    address: string = "ressalvas";

    constructor(protected http: HttpClient) {
        super(http);
    }

    /**
     * @param  {RessalvaDoador} ressalvaDoador
     */
    public salvarRessalva(ressalvaDoador: RessalvaDoadorDTO) {
        let data = JSON.stringify(ressalvaDoador);
        return this.http.post(environment.endpoint + this.address, data)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * @param  {RessalvaDoador} ressalvaDoador
     */
    public excluirRessalva(ressalvaId: number) {
        return this.http.delete(environment.endpoint + this.address + '/' + ressalvaId)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Listar as ressalvas associadas ao doador.
     * 
     * @param  {number} id ID do doador.
     */
    public listarRessalvas(idDoador: number) {
        return this.http.get(environment.endpoint + this.address + '/' + idDoador + "/ressalvas")
            .then(this.extrairDado).catch(this.tratarErro);
    }
}