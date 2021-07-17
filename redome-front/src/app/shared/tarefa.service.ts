import { AutenticacaoService } from './autenticacao/autenticacao.service';
import { PerfilUsuario } from './enums/perfil.usuario';
import { environment } from 'environments/environment';
import { HttpClient } from './httpclient.service';
import { BaseService } from '../shared/base.service';
import { Injectable } from '@angular/core';
import { AtributoOrdenacao } from './util/atributo.ordenacao';
import { AtributoOrdenacaoDTO } from './util/atributo.ordenacao.dto';

/**
 * Classe para chamadas aos serviços de tarefa do back
 *
 * @author bruno.sousa
 * @export
 * @class TarefaService
 * @extends {BaseService}
 */
@Injectable()
export class TarefaService extends BaseService {

    constructor(protected http: HttpClient) {
        super(http);
    }

    removerAtribuicaoTarefa(tarefaId: number): Promise<any> {
        return this.http.post(environment.tarefaEndpoint + 'tarefa/' + tarefaId + '/remveratribuicao')
            .then(this.extrairDado).catch(this.tratarErro);
    }


    /**
     * Método para listar tarefas paginadas de acordo com seus tipos.
     * @param {number} tipotarefa - tipo de tarefa que se deseja obter a lista.
     * @param {number} pagina - página a ser listada
     * @param {number} quantidadeRegistro - quantidade de registros a serem listados.
     * @returns listagem de tarefas d busca
     * @memberof BuscaService
     * @author Filipe Paes
     */
    public listarTarefasPaginadas(tipotarefa: number,
            perfil: PerfilUsuario,
            idUsuarioLogado:number,
            pagina: number,
            quantidadeRegistro: number,
            buscaPorUsuarioLogado?:boolean,
            atributoOrdenacaoDTO?:AtributoOrdenacaoDTO,
            statusTarefa?:number,
            parceiro?:number,
            ordenacao?:number,
            parceiroPorUsuarioLogado?: boolean,
            perfilPorUsuarioLogado?: boolean,
            rmr?: number): Promise<any> {
        let parametros = "";
        let url: string = "";
        url = environment.endpoint + 'processos/tarefas';
        parametros +="?tipoTarefa=" + tipotarefa
        + "&pagina=" + pagina
        + "&quantidadeRegistros=" + quantidadeRegistro;

        if(perfil){
            parametros += "&perfilId=" + perfil
        }
        if(idUsuarioLogado){
            parametros += "&usuarioId=" + idUsuarioLogado;
        }

        if(buscaPorUsuarioLogado){
            parametros += "&buscaPorUsuarioLogado="+buscaPorUsuarioLogado;
        }

        if (statusTarefa) {
            parametros += "&statusTarefa=" + statusTarefa;
        }
        if(parceiro){
            parametros += "&parceiroId=" + parceiro;
        }

        if (ordenacao) {
            parametros += "&ordenacao=" + ordenacao;
        }

        if(parceiroPorUsuarioLogado){
            parametros += "&parceiroPorUsuarioLogado=" + parceiroPorUsuarioLogado;
        }

        if(perfilPorUsuarioLogado){
            parametros += "&perfilPorUsuarioLogado=" + perfilPorUsuarioLogado;
        }

        if (rmr) {
            parametros += "&rmr=" + rmr;
        }

        if(atributoOrdenacaoDTO){
            let dadosAtributosOrdenacao = JSON.stringify(atributoOrdenacaoDTO);
            parametros += "&atributoOrdenacaoDTO="+dadosAtributosOrdenacao;
        }

        return this.http.get(url + parametros).then(this.extrairDado).catch(this.tratarErro);
    }

    public listarTarefasDeColetaPedidoExameLaboratorio(pagina: number, quantidadeRegistro: number): Promise<any> {
        let parametros = "";
        let url: string = "";
        url = environment.endpoint + 'pedidosexame/laboratorio/coleta/tarefas';
        parametros +="?pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistro;
        return this.http.get(url + parametros).then(this.extrairDado).catch(this.tratarErro);
    }

    public listarTarefasResultadosPaginadas(pagina: number, quantidadeRegistro: number): Promise<any> {
        let parametros = "";
        let url: string = "";
        url = environment.endpoint + 'pedidosexame/laboratorio/resultado/tarefas';
        parametros +="?pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistro;
        return this.http.get(url + parametros).then(this.extrairDado).catch(this.tratarErro);
    }

    atribuirTarefaParaUsuarioLogado(tarefaId:number): Promise<any> {

        return this.http.post(environment.tarefaEndpoint + 'tarefa/'+tarefaId+'/atribuirusuariologado')
            .then(this.extrairDado).catch(this.tratarErro);

    }

    public atribuirPrimeiraTarefaDaFila(perfilId: number, tipoTarefaId:number): Promise<any>{
        let params = "?perfilId=" + perfilId;
        if(tipoTarefaId){
            params += "&tipoTarefaId=" + tipoTarefaId;
        }
        return this .http.put(environment.endpoint +
            "processos/tarefas/primeira/atribuir" + params)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    fecharTarefa(processoId: number, tarefaId:number): Promise<any> {

        return this.http.put(environment.endpoint + 'processos/'+processoId+'/tarefa/'+tarefaId+'/fechar')
            .then(this.extrairDado).catch(this.tratarErro);

    }

    obterTarefaParaUsuarioLogado(tarefaId: number): Promise<any> {
        return this.http.put(environment.endpoint + 'processos/tarefas/'+tarefaId+'/atribuir')
           .then(this.extrairDado).catch(this.tratarErro);
    }
}
