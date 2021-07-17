import { Solicitacao } from './solicitacao';
import {BaseService} from '../../shared/base.service';
import {HttpClient} from '../../shared/httpclient.service';
import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { environment } from 'environments/environment';
import { EventEmitterService } from '../../shared/event.emitter.service';
import { PedidoExame } from '../../laboratorio/pedido.exame';
import { stringify } from 'querystring';

/**
 * Registra as chamadas ao back-end envolvendo a entidade Match.
 *
 * @author Filipe Paes
 */
@Injectable()
export class MatchService extends BaseService {

    address: string = "matchs";

    constructor(protected http: HttpClient) {
        super(http);
    }

    criarMatch(rmr:number, idDoador:number): Promise<any> {
        let data = {"rmr":rmr,"idDoador":idDoador}
        return this.http.post(environment.endpoint + this.address, data)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Lista de comentários associadas ao match (id) informado.
     *
     * @param id referência do match a ser pesquisado
     */
    public listarComentarios(id: number) {
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        return this.http.get(environment.endpoint + this.address + "/" + id + "/comentarios")
            .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Salva um novo pedido de exame internacional
     * para o doador associado ao match informado.
     *
     * @param matchId ID do match
     * @param pedidoExame pedido de exame com os devidos lócus a serem solicitados.
     */
    public salvarPedidoExameInternacional(matchId: number, pedidoExame: PedidoExame): Promise<any>{
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        let data = JSON.stringify(pedidoExame);
        return this.http.post(environment.endpoint + "matchs/" + matchId + "/pedidoexame/internacional", data)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Obtém o match ativo entre paciente e doador.
     *
     * @param rmr identificador do paciente.
     * @param doadorId identificação do doador.
     */
    public obterMatchAtivo(rmr: number, doadorId: number): Promise<any> {
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        return this.http.get(environment.endpoint + "matchs/ativo?" + "rmr=" + rmr + "&doadorId=" + doadorId)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    public obterMatchDTOPorId(id): Promise<any> {
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        return this.http.get(`${environment.endpoint}matchs/${id}`)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    public disponibilizarDoador(idMatch: number) {
        return this.http.put(`${environment.endpoint}matchs/${idMatch}/disponibilizar`)
           .then(this.extrairDado).catch(this.tratarErro);
    }

}
