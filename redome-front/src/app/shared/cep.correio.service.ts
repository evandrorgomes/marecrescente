import { HttpClientNoAutentication } from './httpclient.noautentication.service';
import { ErroMensagem } from './erromensagem';
import { Observable } from 'rxjs';
import { HttpClient } from './httpclient.service';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { CepCorreio } from "./cep.correio";

/**
 * Classe de serviço para interação com a base dos correios
 */
@Injectable()
export class CepCorreioService {
    addressCep: string = "correio/cep";

    constructor(private http: HttpClient, private httpNoAuthentication: HttpClientNoAutentication) {}

    /**
     * Método que retorna um logradouro através do CEP
     * @param cep 
     */
    getLogradouroPorCep(cep:String): Promise<CepCorreio> {
        return this.httpNoAuthentication.get(environment.publicEndpoint + this.addressCep + "?cep=" + cep)
            .then(this.extrairDado);
    }

    /**
     * Método para a extração do json contido no response
     * @param res 
     */
    public extrairDado(res: Response) {
        if(res instanceof Observable){
            res.subscribe();
        }
        let body = res.json();
        return body || { };
    }

}