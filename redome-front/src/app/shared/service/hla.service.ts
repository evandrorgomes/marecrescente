import { Observable } from 'rxjs';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { Injectable } from '@angular/core';
import { environment } from "environments/environment";
import { HttpClient } from "../httpclient.service";
import { BaseService } from "../base.service";

/**
 * Classe de serviço para interação com valores de hla
 */
@Injectable()
export class HlaService extends BaseService{
    address: string = "hla";

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Rafael Pizão
     */
    constructor(http: HttpClient) {
        super(http);
    }

    /**
     * Método para o obter valor nmdp por codigo
     * @param codigo
     */
    obterSubTiposNmdp(codigo: string): Promise<any> {
        return this.http.get(environment.endpoint + this.address + '/nmdp/'+ codigo)
        .then((res: Response) => { return res.text() } ).catch(this.tratarErro);
        
    }

    /**
     * Método para obter grupo G ou P
     * @param tipo
     * @param locusCodigo
     * @param nomeGrupo 
     */
    obterGrupo(tipo: number, locusCodigo: string, nomeGrupo: string): Promise<any> {
        if (tipo == 3) {
            return this.http.get(environment.endpoint + this.address + '/g?locus='+ locusCodigo + "&grupo=" + nomeGrupo)
                .then((res: Response) => { return res.text() } ).catch(this.tratarErro);
        }
        else {
            return this.http.get(environment.endpoint + this.address + '/p?locus='+ locusCodigo + "&grupo=" + nomeGrupo)
                .then((res: Response) => { return res.text() } ).catch(this.tratarErro);
        }
    }


}