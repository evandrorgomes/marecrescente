import { BaseService } from "app/shared/base.service";
import { environment } from "environments/environment";
import { Injectable } from "@angular/core";
import { HttpClient } from 'app/shared/httpclient.service';
import { IAnaliseMedicaFinalizadaVo } from "app/shared/vo/interface.analise.medica.finalizada.vo";

/**
 * Registra as chamadas ao back-end envolvendo a entidade Analise Medica.
 * 
 * @author Filipe Paes
 */
@Injectable()
export class AnaliseMedicaService extends BaseService {
    private urlBase: String = 'analisesmedica';


    constructor(protected http: HttpClient) {
        super(http);
    }

    /**
     * Obtém lista de tarefas abertas e atribuídas para o médico do redome.
     * @param pagina pagina a ser listada.
     * @param quantidadeRegistros quantidade de registros por página.
     */
    listarTarefas(pagina: number, quantidadeRegistros: number): Promise<any> {
        return this.http.get(`${environment.endpoint}${this.urlBase}/tarefas?pagina=${pagina}&quantidadeRegistro=${quantidadeRegistros}`)
            .then(super.extrairDado)
            .catch(super.tratarErro);
    }

    obterAnaliseMedica(idAnaliseMedica: number): Promise<any> {
        return this.http.get(`${environment.endpoint}analisesmedica/${idAnaliseMedica}`)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    finalizar(idAnaliseMedica: number, analiseMedicaFinalizadaVo: IAnaliseMedicaFinalizadaVo): Promise<any> {
        let data = JSON.stringify(analiseMedicaFinalizadaVo)
        return this.http.put(`${environment.endpoint}analisesmedica/${idAnaliseMedica}`, data )
                .then(this.extrairDado).catch(this.tratarErro);
	}
    

}