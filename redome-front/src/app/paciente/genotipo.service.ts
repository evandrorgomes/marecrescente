import { PacienteTarefaDto } from './consulta/notificacao/paciente.tarefa.dto';
import { Pendencia } from './avaliacao/pendencia';
import { Notificacao } from './notificacao';
import { TipoPendencias } from '../shared/enums/tipo.pendencia';
import { IntervaloTempo } from '../shared/classes/schedule/intervalo.tempo';
import { EventEmitterService } from '../shared/event.emitter.service';
import { environment } from 'environments/environment';
import { BaseService } from '../shared/base.service';
import { ErroMensagem } from '../shared/erromensagem';
import { HttpClient } from '../shared/httpclient.service';
import { Response } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import { Injectable } from '@angular/core';
import { Paciente } from './paciente';
import { CampoMensagem } from '../shared/campo.mensagem';
import { EvolucaoService } from './cadastro/evolucao/evolucao.service';
import { ExameService } from './cadastro/exame/exame.service';
import * as NodeSchedule from 'node-schedule';
import { Job } from 'node-schedule';
import { Tarefa } from "./consulta/pendencia/tarefa";
import { ResultadoDivergenciaDTO } from 'app/shared/dto/resultado.divergencia.dto';

/**
 * Classe de Serviço utilizada para acessar os serviços REST de paciente 
 * @author Rafael Pizão
 */
@Injectable()
export class GenotipoService extends BaseService {
    address: string = "genotipo";

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
     * Obter o genótipo do paciente.
     * @param rmr
     */
    obterGenotipo(rmr: number) {
        return this.http.get(environment.endpoint + this.address + '/' + rmr)
            .then(this.extrairDado).catch(this.tratarErro);
    }


    /**
     * Obter o genótipo de doador.
     * @param idDoador
     */
    obterGenotipoDoador(idDoador: number) {
        return this.http.get(environment.endpoint + this.address + '/doador/' + idDoador)
            .then(this.extrairDado).catch(this.tratarErro);
    }
    
    /**
     * Busca os genótipos comparados por paciente e lista de dmrs
     * @param  {number} rmr
     * @param  {number[]} dmrs
     */
    downloadGenotiposComparados(rmr: number, listaIdsDoadores: number[], nomeArquivo: string) {
        let idsString: string = listaIdsDoadores.join(",");
        let params = "?listaIdsDoadores=" + idsString;
        // return this.http.get(environment.endpoint + this.address + '/pacientes/impressao/' + rmr + params)
        //     .then(this.extrairDado).catch(this.tratarErro);
        super.download(this.address + '/pacientes/impressao/' + rmr + params, false, nomeArquivo);
    }

   /**
     * Busca os genótipos comparados por paciente e lista de dmrs
     * @param  {number} rmr
     * @param  {number[]} dmrs
     */
    listarGenotiposComparados(rmr: number, listaIdsDoadores: number[]) {
        let idsString: string = listaIdsDoadores.join(",");
        let params = "?listaIdsDoadores=" + idsString;
        return this.http.get(environment.endpoint + this.address + '/pacientes/' + rmr + params)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Busca os genótipos comparados por busca prelimnar e lista de dmrs
     * @param  {number} idBuscaPreliminar
     * @param  {number[]} dmrs
     */
    listarGenotiposComparadosBuscaPreliminar(idBuscaPreliminar: number, listaIdsDoadores: number[]) {
        let idsString: string = listaIdsDoadores.join(",");
        let params = "?listaIdsDoadores=" + idsString;
        return this.http.get(environment.endpoint + this.address + '/buscaspreliminares/' + idBuscaPreliminar + params)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Obter o genótipo de doador.
     * @param idDoador
     */
    obterGenotipoDivergenteDoador(idDoador: number) {
        return this.http.get(environment.endpoint + 'genotipo/divergente/doador/' + idDoador)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    resolverDivergenciaGenotipoDoador(idDoador: number, resultadoDivergenciaDTO: ResultadoDivergenciaDTO): Promise<any> {

        return this.http.post(environment.endpoint + 'genotipo/doador/' + idDoador + '/resolverdivergencia',
            JSON.stringify(resultadoDivergenciaDTO)).then(this.extrairDado).catch(this.tratarErro);

    }


}