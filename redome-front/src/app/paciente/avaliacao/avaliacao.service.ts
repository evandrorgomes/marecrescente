import {Pendencia} from './pendencia';
import { Avaliacao } from './avaliacao';
import { TipoPendencia } from '../../shared/dominio/tipo.pendencia';
import { HttpClient } from '../../shared/httpclient.service';
import { BaseService } from '../../shared/base.service';
import { Injectable } from '@angular/core';
import { environment } from "environments/environment";

/**
 * Classe de Serviço utilizada para acessar os serviços REST de paciente
 * @author Rafael Pizão
 */
@Injectable()
export class AvaliacaoService extends BaseService{
    private address: string = "avaliacoes";
    private addressPendencias: string = "/pendencias"; 
    
    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Fillipe Queiroz
     */
    constructor(http: HttpClient) {
        super(http);
    }

    aprovarAvaliacaoPaciente(avaliacao:Avaliacao){
        avaliacao.aprovado = true;
        return this.http.put(environment.endpoint + this.address+'/'+avaliacao.id, avaliacao)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    reprovarAvaliacaoPaciente(avaliacao:Avaliacao){
        avaliacao.aprovado = false;
        return this.http.put(environment.endpoint + this.address+'/'+avaliacao.id, avaliacao)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    listarPendencias(idAvaliacao: number, pagina: number, quantidadeRegistros: number) {
        return this.http.get(environment.endpoint + this.address + "/" + idAvaliacao + this.addressPendencias + 
                    '?pagina=' + pagina 
                    + '&quantidadeRegistros=' + quantidadeRegistros)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Cria a pendência, informada no parâmetro, associada a avaliação informada.
     * 
     * @param avaliacaoId 
     * @param pendencia 
     */
    public salvarPendencia(pendencia:Pendencia) {
        return this.http.post(
                    environment.endpoint + this.address + '/' + pendencia.avaliacao.id + this.addressPendencias, 
                    pendencia).then(this.extrairDado);
    }

    /**
     * Metodo para listar os pacientes com avaliações pendentes.
     * Essa é a lista de tarefas que o avaliador possui.
     * 
     * @returns 
     * @memberof PacienteService
     */
    public listarAvaliacoesPacientes(pagina: number, quantidadeRegistros: number, status:string, idCentroTransplante: number){
        return this.http.get(environment.endpoint + 'avaliacoes/'+ status + '?pagina='
            + pagina + '&quantidadeRegistros=' + quantidadeRegistros + '&idCentroTransplante=' + idCentroTransplante)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * * Método para iniciar a avaliação do paciente para o avaliador logado
     * @param avaliacaoId 
     */
    public atribuirAvaliacaoAoAvaliadorLogado(avaliacaoId:number){
        return this.http.put(environment.endpoint + this.address + '/' + avaliacaoId + '/iniciar')
            .catch(this.tratarErro)
            .then(this.extrairDado);       
    }

}