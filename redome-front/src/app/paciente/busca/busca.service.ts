import { NovoExameComponent } from '../cadastro/exame/novoexame.component';
import { CancelamentoBusca } from './cancelamento.busca';
import { environment } from 'environments/environment';
import { HttpClient } from '../../shared/httpclient.service';
import { BaseService } from '../../shared/base.service';
import { Response } from '@angular/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { UrlParametro } from '../../shared/url.parametro';

/**
 * Classe de Serviço utilizada para acessar os serviços REST de busca de paciente.
 * @author Fillipe Queiroz
 */
@Injectable()
export class BuscaService extends BaseService {
    endereco: string = "buscas";

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é
     * injetada e atribuída como atributo
     * @author Rafael Pizão
     */
    constructor(http: HttpClient) {
        super(http);
    }

    listarMotivosCancelamentoBusca() {
        return this.http.get(environment.endpoint + this.endereco + '/motivoscancelamento')
            .then(this.extrairDado).catch(this.tratarErro);
    }

    cancelarBuscaPaciente(rmr: number, cancelamentoBusca: CancelamentoBusca) {
        let data = JSON.stringify(cancelamentoBusca);
        return this.http.put(environment.endpoint + this.endereco + '/' + rmr + '/cancelar', data)
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }

    atribuirBusca(idBusca: number) {
        return this.http.put(environment.endpoint + this.endereco + '/' + idBusca + '/atribuir')
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }
    /**
     * Método para obter busca por RMR
     *
     * @param {number} rmr
     * @returns Busca populada
     * @memberof BuscaService
     */
    obterBuscaPorRMR(rmr: number) {
        return this.http.get(environment.endpoint + this.endereco + '/' + rmr + '/obter/' )
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }

    /**
     * Lista as buscas ativas, associadas através do centro avaliador, para determinado
     * analista de busca.
     *
     * @param {number} pagina - página a ser listada
     * @param {number} quantidadeRegistro - quantidade de registros a serem listados.
     * @param {string} [ordem] - campo que servirá de parâmetro para a ordenação.
     * @param {number} [status] - campo que servirá de parâmetro para o status de busca.
     * @param {string} loginAnalistaBusca - login do analista associado as buscas.
     * @returns listagem de tarefas d busca
     * @memberof BuscaService
     * @author Filipe Paes
     */

    public listarBuscas(pagina: number, quantidadeRegistro: number,
            filtros: UrlParametro[]) {
        let parametros = "";
        let url: string = environment.endpoint + "buscas";


        parametros += "?pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistro;

        filtros.forEach(filtro => {
            parametros += "&" + filtro.toUrl();
        });

        return this.http.get(url + parametros).then(this.extrairDado).catch(this.tratarErro);
    }

    public obterUltimoPedidoExame(buscaId: number): Promise<any> {
        return this.http.get(environment.endpoint + 'buscas/' + buscaId + "/ultimopedidoexame").then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * @description Lista os tipos de busca checklist que serão exibidos na combo
     * de filtro.
     * @author Pizão
     * @param {number} buscaId
     * @returns {Promise<any>}
     */
    public listarTipoBuscaChecklist(): Promise<any> {
        return this.http.get(environment.endpoint + 'buscas/tiposBuscaChecklist')
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * @description Lista os arquivos de relatório da busca internacional.
     * @author Bruno Sousa
     * @param {number} buscaId
     * @returns {Promise<any>}
     */
    public listarArquivosRelatorioInternacional(buscaId: number): Promise<any> {
        return this.http.get(environment.endpoint + 'buscas/' + buscaId + '/relatoriosinternacionais')
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * @description download de todos os arquivos de relatório da busca internacional.
     * @author Bruno Sousa
     * @param {number} buscaId
     */
    public downloadArquivosRelatorioInternacional(buscaId: number): void {

        super.download('buscas/' + buscaId + '/downloadinternacionalzip',
            false, new String(buscaId).valueOf()+ '.zip');

    }


}
