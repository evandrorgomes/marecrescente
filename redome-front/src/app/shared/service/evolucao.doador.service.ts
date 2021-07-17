import { BaseService } from "app/shared/base.service";
import { environment } from "environments/environment";
import { Injectable } from "@angular/core";
import { HttpClient } from 'app/shared/httpclient.service';
import { IAnaliseMedicaFinalizadaVo } from "app/shared/vo/interface.analise.medica.finalizada.vo";
import { EvolucaoDoadorVo } from "../vo/evolucao.doador.vo";

/**
 * Registra as chamadas ao back-end envolvendo a entidade EvolucaoDoador.
 * 
 * @author brunosousa
 */
@Injectable()
export class EvolucaoDoadorService extends BaseService {


    constructor(protected http: HttpClient) {
        super(http);
    }

    criarNovaEvolucaoDoador(evolucaoDoadorVo: EvolucaoDoadorVo): Promise<any> {
        let data = JSON.stringify(evolucaoDoadorVo)
        return this.http.post(`${environment.endpoint}evolucoesdoador`, data )
                .then(this.extrairDado).catch(this.tratarErro);
	}
    

}