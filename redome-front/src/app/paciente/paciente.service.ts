import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import * as NodeSchedule from 'node-schedule';
import { Job } from 'node-schedule';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import { CordaoInternacional } from '../doador/cordao.internacional';
import { PedidoExame } from '../laboratorio/pedido.exame';
import { BaseService } from '../shared/base.service';
import { IntervaloTempo } from '../shared/classes/schedule/intervalo.tempo';
import { MismatchDTO } from '../shared/dto/mismatch.dto';
import { FiltroMatch } from '../shared/enums/filtro.match';
import { TipoPendencias } from '../shared/enums/tipo.pendencia';
import { TiposExame } from '../shared/enums/tipos.exame';
import { EventEmitterService } from '../shared/event.emitter.service';
import { HttpClient } from '../shared/httpclient.service';
import { EnderecoContatoPaciente } from './cadastro/contato/endereco/endereco.contato.paciente';
import { ContatoTelefonicoPaciente } from './cadastro/contato/telefone/contato.telefonico.paciente';
import { EvolucaoService } from './cadastro/evolucao/evolucao.service';
import { ContatoPacienteDTO } from './editar/contato.paciente.dto';
import { Paciente } from './paciente';


/**
 * Classe de Serviço utilizada para acessar os serviços REST de paciente
 * @author Rafael Pizão
 */
@Injectable()
export class PacienteService extends BaseService{
    addressVerifDupl: string = "pacientes/verificarduplicidade";
    addressAvaliacaoAtual: string = "/avaliacoes/atual";
    addressPendencias: string = "/pendencias";
    addressExames: string = "/exames";

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
     * Método que vai ao servidor REST e inclui um novo paciente
     * @param paciente referência do paciente recebido da tela
     * @return Observable<CampoMensagem[]> retorno ajax com campos de mensagem
     * @author Rafael Pizão
     */
    incluir(paciente: Paciente): Promise<Object> {
        let data = JSON.stringify(paciente);

        return this.http.post(environment.endpoint + "pacientes", data)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    /**
     * Método que vai ao servidor REST e verififa se já existe paciente com as mesmas características.
     * @param paciente referência do paciente recebido da tela
     * @return Observable<Boolean> retorno informado se existe (TRUE) ou não (FALSE)
     * @author Rafael Pizão
     */
    verificarDuplicidade(paciente: Paciente): Promise<any> {
        let data = JSON.stringify(paciente);
        return this.http.post(environment.endpoint + this.addressVerifDupl, data)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método que obtem do servidor o paciente pelo rmr
     *
     * @param {number} rmr identificador do paciente
     * @returns {Promise<Paciente>}
     * @author Bruno Sousa
     *
     */
    obterFichaPaciente(rmr: number): Promise<any> {
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        return this.http.get(environment.endpoint + "pacientes/" + rmr)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Lista as evoluções associadas ao paciente (RMR) informado
     *
     * @param rmr referência do paciente a ser pesquisado
     */
    public listarEvolucoesPorPaciente(rmr:number){
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        return this.http.get(environment.endpoint + "pacientes/" + rmr + "/" + EvolucaoService.URL)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Lista as exames associados ao paciente (RMR) informado
     *
     * @param rmr referência do paciente a ser pesquisado
     */
    listarExamesPorPaciente(rmr:number){
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        return this.http.get(environment.endpoint + "pacientes/" + rmr +  this.addressExames)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Método para buscar a ultima evolução do paciente para abrir a tela de cadastro de evolução
     *
     * @param {number} rmr - id do paciente para buscar a ultima evolucao
     * @memberof EvolucaoService
     */
    obterUltimaEvolucaoPorPaciente(rmr: number) {
        setTimeout(() => {
            EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        }, 1);
        return this.http.get(environment.endpoint + "pacientes/" + rmr + "/ultimaEvolucao").then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método para buscar o status do paciente.
     *
     * @param {number} rmr - id do paciente para buscar a ultima evolucao
     * @memberof EvolucaoService
     */
    obterStatusPacientePorRmr(rmr: number): Promise<any> {
        return this.http.get(environment.endpoint + "pacientes/" + rmr + "/statuspaciente").then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método para obter lista paginada de paciente
     *
     * @param {number} rmr
     * @param {string} nome
     * @param {number} pagina
     * @param {number} quantidadeRegistros
     * @returns
     * @memberof PacienteService
     */
    listarPacientePorRmrOuNome(rmr: number, nome: string, meusPacientes: boolean,
        idFuncaoTransplante: number, idCentroAvaliador: number, pagina: number, quantidadeRegistros: number) {
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");

        let parametros: string = '?rmr=' + (rmr ? rmr: '')
            + '&nome=' + (nome ? nome: '');
        if (meusPacientes == true || meusPacientes == false) {
            parametros += '&meusPacientes=' + meusPacientes;
        }
        if (idFuncaoTransplante != null && idFuncaoTransplante != undefined) {
            parametros += '&idFuncaoTransplante=' + idFuncaoTransplante;
        }
        if (idCentroAvaliador != null && idCentroAvaliador != undefined) {
            parametros += '&idCentroAvaliador=' + idCentroAvaliador;
        }
        parametros += '&pagina=' + pagina
            + '&quantidadeRegistros=' + quantidadeRegistros;
        return this.http.get(environment.endpoint + "pacientes" + parametros)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Recupera os dados de identificação de um paciente por rmr
     *
     * @param {number} rmr
     * @returns
     * @memberof PacienteService
     */
    obterIdentificacaoPacientePorRmr(rmr: number){
        return this .http.get(environment.endpoint + "pacientes/" + rmr + "/identificacao")
                    .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método para a extração do json contido no response
     * @param res
     */
    public extrairDadoUpload(res: any) {
        if(res instanceof Observable){
            res.subscribe();
        }
        let body = JSON.parse(res);
        return body || { };
    }

    public salvarDraft(paciente:Paciente):void{}

    /**
     * Método para agendamento de Jobs schedulados no angular.
     * Ficou definido que nosso tempo seria somente em segundos.
     *
     * @param intervalo Define a regra de timeout (em segundos) para execução do Job.
     * @param metodo Método que será chamado a cada timeout.
     */
    public agendarJob(intervalo:IntervaloTempo, metodo:() => void):Job{
        return NodeSchedule.scheduleJob(intervalo, metodo);
    }

    /**
     * Recupera os dados da avaliação atual do paciente
     *
     * @param {number} rmr
     * @returns
     *
     * @memberOf PacienteService
     */
    obterAvaliacaoAtual(rmr: number) {
        return this.http.get(environment.endpoint +
            "pacientes/" + rmr + this.addressAvaliacaoAtual)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    listarPendenciasEmAbertoPorTipo(rmr: number, tipoPendenica: TipoPendencias ) {
        return this.http.get(
                environment.endpoint + "pacientes/" + rmr + this.addressAvaliacaoAtual + this.addressPendencias + "?idTipoPendencia=" + tipoPendenica
            ).then(this.extrairDado).catch(this.tratarErro);
    }

    listarPendencias(idCentroAvalaidor: number, pagina: number, quantidadeRegistros: number) {
        let filtro = '';
        let separador = '?';
        if (idCentroAvalaidor != null) {
            filtro = separador + 'idCentroAvalaidor=' + idCentroAvalaidor;
            separador = '&';
        }

        filtro += separador + 'pagina=' + pagina + '&quantidadeRegistros=' + quantidadeRegistros;

        return this.http.get(environment.endpoint + 'pacientes/pendencias' + filtro)
            .then(this.extrairDado).catch(this.tratarErro);

    }

    public marcarCienteNaNotificacaoSelecionada(idTarefa:number){
        return this.http.put(environment.endpoint + 'pacientes/tarefas/' +idTarefa).then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método que obtem o contato do paciente (Endereço e Telefones)
     * representado no RMR informado no parâmetro.
     *
     * @param {number} rmr identificador do paciente
     * @returns {Promise<Paciente>}
     */
    obterContatoPaciente(rmr: number): Promise<ContatoPacienteDTO> {
        //EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        return this.http.get(environment.endpoint + "pacientes/" + rmr + "/contatos")
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método que obtem o contato do paciente (Endereço e Telefones)
     * representado no RMR informado no parâmetro.
     *
     * @param {number} rmr identificador do paciente
     * @returns {Promise<Paciente>}
     * @author Bruno Sousa
     */
    obterIdentificaoEDadosPessoais(rmr: number): Promise<any> {
        //EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        return this.http.get(environment.endpoint + "pacientes/" + rmr + "/dadospessoais")
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método que vai ao servidor REST e atualiza a identificacao e os dados pessoais
     * @param paciente referência do paciente recebido da tela
     * @return Observable<CampoMensagem[]> retorno ajax com campos de mensagem
     * @author Bruno Sousa
     */
    salvarIdentificacaoEDadosPessoais(paciente: Paciente): Promise<Object> {
        let data = JSON.stringify(paciente);

        return this.http.put(environment.endpoint + "pacientes/" + paciente.rmr + "/dadospessoais", data)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }


    /**
     * Atualiza o paciente com o objeto informado.
     *
     * @param {number} paciente paciente com os dados atualizados.
     */
    public atualizarPaciente(rmr:number, enderecosContato:EnderecoContatoPaciente[], email:string,
                            telefones:ContatoTelefonicoPaciente[]): Promise<string> {
        let paciente:Paciente = new Paciente();
        paciente.rmr = rmr;
        paciente.enderecosContato = enderecosContato;
        paciente.email = email;
        paciente.contatosTelefonicos = telefones;

        let data = JSON.stringify(paciente);
        return this.http.put(environment.endpoint + "pacientes/" + rmr + "/contatos", data)
                        .catch(this.tratarErro)
                        .then(this.extrairDado);
    }

    /**
     * Método que obtem do servidor o dto com as listas de matchs de um paciente
     *
     * @param {number} rmr identificador do paciente
     * @returns {Promise<any>}
     * @author Bruno Sousa
     *
     */
    obterMatchsSobAnalise(rmr: number, filtroMatch: FiltroMatch): Promise<any> {
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        return this.http.get(environment.endpoint + "pacientes/" + rmr + "/matchs?filtro=" + filtroMatch)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método para verificar se a ultima evolução do paciente está atualizada.
     *
     * @param {number} rmr - id do paciente
     * @memberof EvolucaoService
     */
    verificarUltimaEvolucaoAtualizada(rmr: number) {
        setTimeout(() => {
            EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        }, 1);
        return this.http.get(environment.endpoint + "pacientes/" + rmr + "/ultimaevolucaoatualizada")
           .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * @description Lista o log de evolução do paciente.
     * @author Pizão
     * @param {number} rmr
     * @returns {Promise<any>}
     */
    public listarLogEvolucao(pagina: number, quantidadeRegistro: number, rmr: number): Promise<any>{
        let parametros = "";
        let url: string = environment.endpoint + 'pacientes/' + rmr + '/historico';

        parametros += "?pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistro;

        return this.http.get(url + parametros).then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Lista os pacientes com prescrição por status e centro de transplante do usuário logado.
     *
     * @author Bruno Sousa
     * @param {number[]} status
     * @param {number} pagina
     * @param {number} quantidadeRegistro
     * @returns
     * @memberof PacienteService
     */

    /**
     * Recupera o detalhe da avaliação do paciente para ser utilizado
     * na tela de confirmação de centro de transplante.
     *
     * @param rmr identificador do paciente.
     */
    public obterDetalheAvaliacaoPaciente(rmr: number): Promise<any>{
        return this.http.get(environment.endpoint + 'pacientes/' + rmr + '/detalhesAvaliacao' )
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }

    public confirmarCentroTransplante(rmr: number, centroTransplanteId: number): Promise<any>{
        return this.http.put(environment.endpoint + 'pacientes/' +
                            rmr + '/centrosTransplante/' + centroTransplanteId + '/confirmar')
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }

    public indefinirCentroTransplante(rmr: number): Promise<any>{
        return this.http.put(environment.endpoint + 'pacientes/' + rmr + '/centrosTransplante/indefinir')
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }

    /**
     * Recusa, com justificativa, o CT para a busca.
     * Esse passo acontece depois da confirmação do centro pelo
     * analista, porém ocorreu algo que precisou ser recusado.
     */
    public recusarCT(rmr: number, justificativa: string) {
        return this.http.put(environment.endpoint + "pacientes/" + rmr + '/centrosTransplante/recusar', justificativa)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Recupera o detalhe da avaliação do paciente para ser utilizado
     * na tela de confirmação de centro de transplante.
     *
     * @param rmr identificador do paciente.
     */
    public listarHistoricoRecusasPeloCT(rmr: number): Promise<any>{
        return this.http.get(environment.endpoint + 'pacientes/' + rmr + '/centrosTransplante/recusas' )
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }



    /**
     * Salva o cordão internacional na base do Redome.
     *
     * @param cordao cordao com todos os dados que serão salvos.
     * @param rmr Identificador do paciente que irá terá o doador reservado.
     */
    public salvarCordaoInternacional(cordao: CordaoInternacional, rmr: number, tipoExame: TiposExame, pedidoExame: PedidoExame): Promise<any>{
        let data: FormData = new FormData();
        data.append("cordao", new Blob([JSON.stringify(cordao)], {
            type: 'application/json'
        }), '');
        data.append("tipoExame", new Blob([JSON.stringify(tipoExame)], {
            type: 'application/json'
        }), '');
        data.append("pedidoExame", new Blob([JSON.stringify(pedidoExame)], {
            type: 'application/json'
        }), '');

        return this.http.fileUpload(environment.endpoint + "pacientes/" + rmr + "/cordaoInternacional", data)
                    .then(this.extrairDado).catch(this.tratarErro);

    }

       /**
     * Salva o doador internacional na base do Redome.
     *
     * @param doador doador com todos os dados que serão salvos.
     * @param rmr Identificador do paciente que irá terá o doador reservado.
     */
    public transferirCentroAvaliador(rmr, idCentroAvalaidorDestino: number): Promise<any>{
        return this.http.put(environment.endpoint + "pacientes/" + rmr + "/transferircentroavaliador", idCentroAvalaidorDestino)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método que obtem o diagnostico do paciente
     * representado no RMR informado no parâmetro.
     *
     * @param {number} rmr identificador do paciente
     * @returns {Promise<Diagnostico>}
     * @author Bruno Sousa
     */
    obterDiagnostico(rmr: number): Promise<any> {
        return this.http.get(environment.endpoint + "pacientes/" + rmr + "/diagnostico")
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método que obtem o dados de mismatch do paciente
     * representado no RMR informado no parâmetro.
     *
     * @author Bruno Sousa
     * @param {number} rmr
     * @returns {Promise<any>}
     * @memberof PacienteService
     */
    obterDadosMismatch(rmr: number): Promise<any> {
        return this.http.get(environment.endpoint + "pacientes/" + rmr + "/dadosmismatch")
            .then(this.extrairDado).catch(this.tratarErro);
    }

    alterarDadosMismatch(rmr: number, mismatchDTO: MismatchDTO): Promise<any> {
        let data = JSON.stringify(mismatchDTO);
        return this.http.put(environment.endpoint + "pacientes/" + rmr + "/dadosmismatch", data)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    /**
     * Solicita uma nova busca para o paciente que esta,
     * necessariamente, com busca inativa (cancelado, transplantado, etc)
     * e precisa realizar um novo acompanhamento, independente do motivo.
     *
     * @param rmr identificador do paciente.
     */
    public solicitarNovaBusca(rmr: number): Promise<any>{
        return this.http.post(environment.endpoint + 'pacientes/' + rmr + '/novabusca')
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }

    /**
     * Método que obtem do servidor MatchDTO com as listas de matchs descartadas de um paciente
     *
     * @param {number} rmr identificador do paciente
     * @returns {Promise<any>}
     * @author Bruno Sousa
     *
     */
    obterMatchsDescartadosSobAnalise(rmr: number, filtroMatch: FiltroMatch, fase: string): Promise<any> {
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        return this.http.get(environment.endpoint + "pacientes/" + rmr + "/matchs/inativos?filtro=" + filtroMatch + "&fase=" + fase)
            .then(this.extrairDado).catch(this.tratarErro);
    }


}
