import { CordaoInternacional } from './cordao.internacional';
import { Injectable } from '@angular/core';
import { DoadorNacional } from './doador.nacional';
import { ContatoTelefonico } from "../shared/classes/contato.telefonico";
import { BaseService } from '../shared/base.service';
import { HttpClient } from '../shared/httpclient.service';
import { environment } from 'environments/environment';
import { Formulario } from '../shared/classes/formulario';
import { TiposFormulario } from '../shared/enums/tipo.formulario';
import { EventEmitterService } from '../shared/event.emitter.service';
import { EmailContatoDoador } from './email.contato.doador';
import { EnderecoContatoDoador } from './endereco.contato.doador';
import { TentativaContatoDoador } from './solicitacao/fase2/tentativa.contato.doador';
import { Doador } from './doador';
import { DoadorInternacional } from './doador.internacional';
import { ExameDoadorInternacional } from './exame.doador.internacional';
import { ExameCordaoInternacional } from './exame.cordao.internacional';

/**
 * Registra as chamadas ao back-end envolvendo a entidade DoadorNacional.
 *
 * @author Rafael Pizão
 */
@Injectable()
export class DoadorService extends BaseService {

    constructor(protected http: HttpClient) {
        super(http);
    }

    registrarTentativaContato(doador: DoadorNacional, tentativaContato: TentativaContatoDoador): Promise<any>{
        let data: FormData = new FormData();
        data.append("tentativaContato", new Blob([JSON.stringify(tentativaContato)], {
            type: 'application/json'
        }), '');

        return this.http.fileUpload(environment.endpoint + "doadores/" + doador.id + "/tentativascontato", data)
                    .then(this.extrairDado).catch(this.tratarErro);
    }


    inativar(doador: DoadorNacional): Promise<any> {
        let requestParams: string = '?motivoStatusId=' + doador.motivoStatusDoador.id;

        if(doador.dataRetornoInatividade != null){
            requestParams += '&timeRetornoInatividade=' + doador.dataRetornoInatividade.getTime();
        }

        return this.http.put(
            environment.endpoint + 'doadores/' + doador.id + '/inativar' + requestParams)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    public carregarProximoPedidoContato(perfilId: number): Promise<any>{
        return this .http.put(environment.endpoint +
            "processos/tarefas/primeira/atribuir?perfilId=" + perfilId)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    carregarDoadorAssociadoAoPedido(pedidoContatoId:number): Promise<any>{
        return this .http.get(environment.endpoint +
                    "pedidoscontato/" + pedidoContatoId + "/doadores")
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    carregarDoadorAssociadoATentativaDeContato(tentativaContatoId:number): Promise<any>{
        return this .http.get(environment.endpoint +
                    "tentativascontato/" + tentativaContatoId + "/doadores")
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    incluirContatoTelefone(idDoador:number, contatoTelefonico:ContatoTelefonico){
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        let body = JSON.stringify(contatoTelefonico)
        return this .http.post(environment.endpoint +
                    "doadores/" + idDoador + "/contatostelefonicos", body)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    adicionarEndereco(idDoador: number, endereco: EnderecoContatoDoador): Promise<any>{
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        let body = JSON.stringify(endereco);
        return this.http.post(environment.endpoint + "doadores/" + idDoador + "/enderecocontato", body)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    adicionarEmail(idDoador: number, email: EmailContatoDoador): Promise<any>{
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        let body = JSON.stringify(email);
        return this.http.post(environment.endpoint + "doadores/" + idDoador + "/emailscontato", body)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    alterarIdentificacao(idDoador: number, doador:DoadorNacional){
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        let body = JSON.stringify(doador)
        return this .http.put(environment.endpoint +
                    "doadores/" + idDoador + "/identificacao", body)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    alterarDadosPessoais(idDoador: number, doador:DoadorNacional){
        EventEmitterService.get("mudarEstiloImagemLoading").emit("ng-busy");
        let body = JSON.stringify(doador)
        return this .http.put(environment.endpoint +
                    "doadores/" + idDoador + "/dadospessoais", body)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    finalizarTentativaContato(idDoador: number, tentativaContato: TentativaContatoDoador, formulario?: Formulario){
        let data: FormData = new FormData();
        data.append("tentativaContato", new Blob([JSON.stringify(tentativaContato)], {
            type: 'application/json'
        }), '');

        if (formulario) {
            data.append("formulario", new Blob([JSON.stringify(formulario)], {
                type: 'application/json'
            }), '');
        }

        return this.http.fileUpload(environment.endpoint
            + "doadores/" + idDoador + "/tentativascontato", data)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Recupera os dados de identificação de um doador por dmr
     *
     * @param {number} dmr
     * @returns
     * @memberof DoadorService
     */
    obterIdentificacaoDoadorPorId(idDoador: number){
        return this .http.get(environment.endpoint + "doadores/" + idDoador + "/identificacao")
                    .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Recupera os dados de um doador por dmr.
     *
     * @param {number} dmr
     * @returns  DoadorNacional
     * @memberof DoadorService
     */
    obterDoadorPorId(idDoador:number, exibirUltimaFase?:boolean): Promise<any>{
        let params:string;
        if(exibirUltimaFase){
            params = "?exibirUltimaFase="+exibirUltimaFase;
        }else{
            params = "?exibirUltimaFase="+false;
        }
        return this .http.get(environment.endpoint +
                    "doadores/" + idDoador + '/contato' + params)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    public listarEnderecoDoador(idDoador: number) {
        return this.http.get(environment.endpoint + 'doadores/' + idDoador  + '/enderecoscontato')
            .then(this.extrairDado).catch(this.tratarErro);
    }

   /**
     * Método para listar os contatos dos doadores.
     *
     * @param  {number} pagina
     * @param  {number} quantidadeRegistros
     * @param  {number} dmr
     * @param  {String} nome
     * @param  {String} cpf
     */
    public listarContatoDoadores(pagina: number, quantidadeRegistros: number, dmr: number, nome: String, cpf: String) {

        let params: string = "pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistros;
        if (dmr) {
            params += "&dmr=" + dmr;
        }
        if (nome) {
            params += "&nome=" + nome;
        }
        if (cpf) {
            params += "&cpf=" + cpf;
        }

        return this.http.get(environment.endpoint + "doadores" + '?' + params)
            .then(this.extrairDado).catch(this.tratarErro);
    }

   /**
     * Método para listar os doadores nacionais.
     *
     * @param  {number} pagina
     * @param  {number} quantidadeRegistros
     * @param  {number} dmr
     * @param  {String} nome
     */
    public listarDoadoresNacionais(pagina: number, quantidadeRegistros: number, dmr: number, nome: String) {

        let params: string = "pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistros;
        if (dmr) {
            params += "&dmr=" + dmr;
        }
        if (nome) {
            params += "&nome=" + nome;
        }

        return this.http.get(environment.endpoint + "doadores/nacionais?" + params)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Atualizar o status do doador para qualquer status.
     * @param  {DoadorNacional} doador
     * @returns Promise
     */
    public atualizarStatusDoador(doador: Doador): Promise<any> {
        let requestParams: string = '?statusId=' + doador.statusDoador.id;

        if(doador.motivoStatusDoador  && doador.motivoStatusDoador.id){
            requestParams += '&motivoStatusId=' + doador.motivoStatusDoador.id;
        }

        if(doador.dataRetornoInatividade != null){
            requestParams += '&timeRetornoInatividade=' + doador.dataRetornoInatividade.getTime();
        }

        return this.http.put(
            environment.endpoint + 'doadores/' + doador.id + '/status/alterar' + requestParams)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    public listarDoadoresInternacionais(pagina: number, quantidadeRegistros: number, id: number, idRegistro:number, grid:string) {

        let params: string = "pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistros;
        if (id) {
            params += "&id=" + id;
        }
        if (idRegistro) {
            params += "&idRegistro=" + idRegistro;
        }
        if (grid) {
            params += "&grid=" + grid;
        }

        return this.http.get(environment.endpoint + 'doadores/internacionais?' + params)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    alterarDoador(idDoador: number, doador:DoadorInternacional){

        let body = JSON.stringify(doador)
        return this .http.put(environment.endpoint +
                    "doadores/" + idDoador + "/internacional", body)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    alterarDadosPessoaisDoador(idDoador: number, doador:DoadorInternacional){

        let body = JSON.stringify(doador)
        return this.http.put(environment.endpoint +
                    "doadores/" + idDoador + "/dadospessoais/internacional", body)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    enviarResultadoExameDoadorInternacional(idDoador: number, exame: ExameDoadorInternacional): Promise<any>{

        let body = JSON.stringify(exame);

        return this.http.post(environment.endpoint +"doadores/" + idDoador + "/exame/internacional", exame)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    enviarResultadoExameCordaoInternacional(idDoador: number, exame: ExameCordaoInternacional): Promise<any>{

        let body = JSON.stringify(exame);

        return this.http.post(environment.endpoint +"doadores/" + idDoador + "/exame/cordaointernacional", exame)
                .then(this.extrairDado).catch(this.tratarErro);
    }

   /**
    * Obtém volumes relativos a TCN e CD34 disponível para o cordão.
    * @return um DTO contendo as informações necessárias para tela.
    */
   public obterDetalheDoadorParaPrescricao(idDoador: number): Promise<any> {
      return this.http.get(environment.endpoint + "doadores/" + idDoador + "/detalheprescricao")
         .then(this.extrairDado).catch(this.tratarErro);
   }


   /**
     * Obtém volumes relativos a TCN e CD34 disponível para o cordão.
     * @return um DTO contendo as informações necessárias para tela.
     */
    public obterDoadorNacionalPorId(idDoador: number): Promise<any> {
        return this.http.get(environment.endpoint + "doadores/" + idDoador + "/nacional")
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Obtém o doador internacional a partir do ID.
     *
     * @param {number} ID do doador.
     * @returns  doador internacional (medula ou cordão)
     * @memberof DoadorService
     */
    obterDoadorInternacional(idDoador: number): Promise<any>{
        return this.http.get(environment.endpoint +
            "doadores/" + idDoador + '/internacional')
                .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Obtém listagem de exames de doador por id.
     *
     * @param {number} ID do doador.
     * @returns  listagem de exames
     * @memberof DoadorService
     */
    listarExamesDoadorInternacional(idDoador: number): Promise<any>{
        return this.http.get(environment.endpoint +
            "doadores/" + idDoador + '/exame/internacional')
                .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Obtém listagem de exames de doador por id.
     *
     * @param {number} ID do doador.
     * @returns  listagem de exames
     * @memberof DoadorService
     */
    listarExamesCordaoInternacional(idDoador: number): Promise<any>{
        return this.http.get(environment.endpoint +
            "doadores/" + idDoador + '/exame/cordaointernacional')
                .then(this.extrairDado).catch(this.tratarErro);
    }

     /**
     * Obtém o doador internacional a partir do grid.
     *
     * @param {string} grid do doador.
     * @returns  doador internacional (medula ou cordão)
     * @memberof DoadorService
     */
    obterDoadorInternacionalPorGrid(grid: string): Promise<any>{
        return this.http.get(environment.endpoint +
            "doadores/internacional?grid=" + grid)
                .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Obtém listagem de exames de doador por id.
     *
     * @param {number} ID do doador.
     * @returns  listagem de exames
     * @memberof DoadorService
     */
    listarExamesOrdernadoPorDataCriacaoDecrescente(idDoador: number): Promise<any>{
        return this.http.get(environment.endpoint +
            'doadores/' + idDoador + '/exames')
                .then(this.extrairDado).catch(this.tratarErro);
    }

    listarPedidosExameParaResolverDivergencia(idDoador: number): Promise<any> {
        return this.http.get(environment.endpoint +
            'doadores/' + idDoador + '/pedidosexame/resolverdivergencia')
                .then(this.extrairDado).catch(this.tratarErro);
    }

    obterDataUltimoPedidoContatoFechado(idDoador: number): Promise<any> {
        return this.http.get(environment.endpoint +
            'doadores/' + idDoador + '/dataultimopedidocontatofechado')
                .then(this.extrairDado).catch(this.tratarErro);
    }

    listarEvolucoesOrdernadasPorDataCriacaoDecrescente(idDoador: number): Promise<any> {
        return this.http.get(`${environment.endpoint}doadores/${idDoador}/evolucoes`)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    criarPedidoContatoPassivo(idDoador: number): Promise<any> {
        return this.http.post(environment.endpoint + 'doadores/' + idDoador + '/doadorContatoPassivo').
            then(this.extrairDado).catch(this.tratarErro);
    }


    public obterDetalhesDoador(idDoador: number): Promise<any> {
    return this.http.get(environment.endpoint + "doadores/" + idDoador + "/detalhes")
       .then(this.extrairDado).catch(this.tratarErro);
 }

}
