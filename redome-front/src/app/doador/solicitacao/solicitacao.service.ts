import { Solicitacao } from './solicitacao';
import {BaseService} from '../../shared/base.service';
import {HttpClient} from '../../shared/httpclient.service';
import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { environment } from 'environments/environment';
import { SolicitacaoDTO } from '../../paciente/busca/analise/solicitacao.dto';
import { FileItem } from 'ng2-file-upload';
import { MotivoStatusDoador } from '../inativacao/motivo.status.doador';
import { Locus } from 'app/paciente/cadastro/exame/locus';

/**
 * Registra as chamadas ao back-end envolvendo a entidade Solicitacao.
 *
 * @author Fillipe Queiroz
 */
@Injectable()
export class SolicitacaoService extends BaseService {

    private address: string = "solicitacoes"

    constructor(protected http: HttpClient) {
        super(http);
    }

    listarMotivosStatusDoador(idRecurso: string) {
        let parametro: string = idRecurso ? '?siglaRecurso=' + idRecurso : "";
        return this.http.get(environment.endpoint + "statusdoador" + parametro)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    public listarHemoEntidadesPorUf(uf: string) {
        return this.http.get(environment.endpoint + 'hemoentidades?uf=' + uf)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    public obterPedidoExamePorSolicitacaoId(idSolicitacao:number){
        return this.http.get(environment.endpoint + this.address +  "/" + idSolicitacao
                + "/pedidoexame/internacional")
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    public cancelarFase3Paciente(idSolicitacao: number, justificativa: string): Promise<any> {
        return this.http.post(environment.endpoint + "solicitacoes/" + idSolicitacao + "/cancelar/fase3/paciente", justificativa)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    public cancelarFase2Nacional(idSolicitacao: number): Promise<any> {
        return this.http.post(environment.endpoint + "solicitacoes/" + idSolicitacao + "/cancelar/fase2/nacional")
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    public cancelarFase3Nacional(idSolicitacao: number): Promise<any> {
        return this.http.post(environment.endpoint + "solicitacoes/" + idSolicitacao + "/cancelar/fase3/nacional")
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    public cancelarFase3Internacional(idSolicitacao: number,
        dataCancelamento: Date,  motivoCancelamento?: MotivoStatusDoador,
        tempoAfastamento?: Date): Promise<any> {

        let data: FormData = new FormData();
        data.append("dataCancelamento", new Blob([JSON.stringify(dataCancelamento)], {
            type: 'application/json'
        }), '');

        if(motivoCancelamento){
            data.append("motivoStatusId", new Blob([JSON.stringify(motivoCancelamento.id)], {
                type: 'application/json'
            }), '');

            if(tempoAfastamento){
                data.append("timeRetornoInatividade", new Blob([JSON.stringify(tempoAfastamento.getTime())], {
                    type: 'application/json'
                }), '');
            }
        }

        return this.http.fileUpload(environment.endpoint + "solicitacoes/" + idSolicitacao
                        + "/cancelar/fase3/internacional", data)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    public cancelarFase2Internacional(idSolicitacao: number, motivo: string,
            motivoCancelamento?: MotivoStatusDoador, tempoAfastamento?: Date): Promise<any> {
        let data: FormData = new FormData();
        data.append("justificativa", new Blob([JSON.stringify(motivo)], {
            type: 'application/json'
        }), '');

        if(motivoCancelamento){
            data.append("motivoStatusId", new Blob([JSON.stringify(motivoCancelamento.id)], {
                type: 'application/json'
            }), '');

            if(tempoAfastamento){
                data.append("timeRetornoInatividade", new Blob([JSON.stringify(tempoAfastamento.getTime())], {
                    type: 'application/json'
                }), '');
            }
        }

        return this.http.fileUpload(environment.endpoint + "solicitacoes/" + idSolicitacao + "/cancelar/fase2/internacional", data)
                        .then(this.extrairDado).catch(this.tratarErro);
    }


    public solicitarFase2Nacional(idMatch, idTipoExame: number) {

        const data: FormData = new FormData();
        data.append('idMatch', new Blob([JSON.stringify(idMatch)], {
            type: 'application/json'
        }), '');
        data.append('idTipoExame', new Blob([JSON.stringify(idTipoExame)], {
            type: 'application/json'
        }), '');

        return this.http.fileUpload(environment.endpoint + this.address + '/fase2/nacional', data)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    public solicitarFase2Internacional(idMatch: number, locusSelecionados: Locus[]) {
        let data: FormData = new FormData();
        data.append("idMatch", new Blob([JSON.stringify(idMatch)], {
            type: 'application/json'
        }), '');

        data.append("locusSolicitados", new Blob([JSON.stringify(locusSelecionados)], {
            type: 'application/json'
        }), '');

        return this.http.fileUpload(environment.endpoint + this.address + "/fase2/internacional", data)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    public solicitarFase3Nacional(idMatch, idLaboratorio: number, resolverDivergencia: boolean) {
        let data: FormData = new FormData();
        data.append("idMatch", new Blob([JSON.stringify(idMatch)], {
            type: 'application/json'
        }), '');

        data.append("idLaboratorio", new Blob([JSON.stringify(idLaboratorio)], {
            type: 'application/json'
        }), '');
        if (!resolverDivergencia) {
            return this.http.fileUpload(environment.endpoint + this.address + '/fase3/nacional', data)
                        .then(this.extrairDado).catch(this.tratarErro);
        }
        else {
            return this.http.fileUpload(environment.endpoint + this.address + '/fase3/nacional/resolverdivergencia', data)
                .then(this.extrairDado).catch(this.tratarErro);
        }
    }

    public solicitarFase3Internacional(idMatch: number) {
        return this.http.post(environment.endpoint + this.address + "/fase3/internacional", idMatch)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    public cancelarPedidoIdm(idSolicitacao: number,
        dataCancelamento: Date,  motivoCancelamento?: MotivoStatusDoador,
        tempoAfastamento?: Date): Promise<any> {

        let data: FormData = new FormData();
        data.append("dataCancelamento", new Blob([JSON.stringify(dataCancelamento)], {
            type: 'application/json'
        }), '');

        if(motivoCancelamento){
            data.append("motivoStatusId", new Blob([JSON.stringify(motivoCancelamento.id)], {
                type: 'application/json'
            }), '');

            if(tempoAfastamento){
                data.append("timeRetornoInatividade", new Blob([JSON.stringify(tempoAfastamento.getTime())], {
                    type: 'application/json'
                }), '');
            }
        }

        return this.http.fileUpload(environment.endpoint + "solicitacoes/" + idSolicitacao
                        + "/cancelar/idm/internacional", data)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    public solicitarFase3Paciente(idBusca, idLaboratorio: number, idTipoExame: number): Promise<any> {
        let data: FormData = new FormData();
        data.append("idBusca", new Blob([JSON.stringify(idBusca)], {
            type: 'application/json'
        }), '');
        data.append("idLaboratorio", new Blob([JSON.stringify(idLaboratorio)], {
            type: 'application/json'
        }), '');
        data.append("idTipoExame", new Blob([JSON.stringify(idTipoExame)], {
            type: 'application/json'
        }), '');

        return this.http.fileUpload(environment.endpoint + "solicitacoes/fase3/paciente", data)
                        .then(this.extrairDado).catch(this.tratarErro);


    }

}
