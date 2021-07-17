import { Injectable } from "@angular/core";
import { BaseService } from "app/shared/base.service";
import { HttpClient } from "app/shared/httpclient.service";
import { environment } from "environments/environment";

/**
 * Classe de serviço para Tipo de Amostra
 */
@Injectable()
export class TipoAmostraService extends BaseService {

    constructor(protected http: HttpClient) {
        super(http);
    }

    public listarTiposDeAmostra(): Promise<any> {
        return this.http.get(`${environment.endpoint}tiposamostra/`)
            .then(super.extrairDado)
            .catch(super.tratarErro);
    }
}
