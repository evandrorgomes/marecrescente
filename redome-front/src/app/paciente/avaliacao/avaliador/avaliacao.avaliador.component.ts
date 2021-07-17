import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { PendenciaDialogoComponent } from '../pendenciadialogo/pendencia.dialogo.component';
import { HeaderPacienteComponent } from '../../consulta/identificacao/header.paciente.component';
import { Paciente } from '../../paciente';
import { PendenciaDialogoBuilder } from '../../../shared/dialogo/pendencia.dialogo.builder';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { StatusPendencias } from "../../../shared/enums/status.pendencia";
import { ErroMensagem } from '../../../shared/erromensagem';
import { BaseForm } from '../../../shared/base.form';
import { TipoPendencia } from '../../../shared/dominio/tipo.pendencia';
import { Avaliacao } from "../avaliacao";
import { AvaliacaoService } from '../avaliacao.service';
import { Pendencia } from '../pendencia';
import { PendenciaService } from '../pendencia.service';
import { RespostaPendencia } from '../resposta.pendencia';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { CampoMensagem } from '../../../shared/campo.mensagem';
import { Medico } from '../../../shared/dominio/medico';
import { StatusPendencia } from '../../../shared/dominio/status.pendencia';
import { UsuarioLogado } from '../../../shared/dominio/usuario.logado';
import { Perfis } from '../../../shared/enums/perfis';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { PacienteUtil } from '../../../shared/paciente.util';
import { Paginacao } from '../../../shared/paginacao';
import { Evolucao } from '../../cadastro/evolucao/evolucao';
import { PacienteService } from '../../paciente.service';
import { RespostaPendenciaDTO } from '../resposta.pendencia.dto';
import { MessageBox } from '../../../shared/modal/message.box';
import { ModalAprovarReprovarAvaliacaoComponent } from './modal/modal.aprovar.reprovar.avaliacao.component';
import { ErroUtil } from 'app/shared/util/erro.util';
import { FormatterUtil } from 'app/shared/util/formatter.util';

/**
 * Component responsavel avaliação do paciente pelo médico do centro avaliador
 * @author Bruno Sousa
 * @export
 * @class AvaliacaoComponent
 * @implements {OnInit}
 */
@Component({
    selector: 'avaliacao',
    templateUrl: './avaliacao.avaliador.component.html',
    styleUrls: ['./avaliacao.avaliador.component.css']
})
export class AvaliacaoAvaliadorComponent extends BaseForm<Avaliacao> implements OnInit, OnDestroy {


    public mensagem: string;

    private consultaAvaliacaoForm: FormGroup;
    public criarPendenciaForm:FormGroup;

    private _avaliacao: Avaliacao;
    private _evolucao: Evolucao;
    modalAprovarAvaliacao:boolean;
    esconderBotoesDialogo:boolean = false;
    pendenciaAtual:Pendencia;

    private respostasAvaliador:RespostaPendenciaDTO[] = [];

    public mensagemModalAprovacao:string;

    @ViewChild("modalPendencia")
    private modalPendencia;

    @ViewChild("modalMsg")
    private modalMsg;

    @ViewChild(HeaderPacienteComponent)
    private headerPaciente: HeaderPacienteComponent;

    public paginacao: Paginacao;
    public quantidadeRegistro: number = 10;

    @ViewChild("modalCancelaPendencia")
    private modalCancelaPendencia;

    protected idPedenciaASerCancelada: number;

    @ViewChild("modalFechaPendencia")
    private modalFechaPendencia;

    @ViewChild("modalAprovacaoPendenciaAberta")
    private modalAprovacaoPendenciaAberta;

    @ViewChild("modalReprovacaoPendenciaAberta")
    private modalReprovacaoPendenciaAberta;

    protected idPedenciaASerFechada: number;

    @ViewChild("criarPendenciaModal")
    private criarPendenciaModal;

    @ViewChild('inputResposta')
    private inputResposta;

    private _tempoParaTransplante: number;
    private _observacao: string;

    private dialogoForm: FormGroup;

    @ViewChild(PendenciaDialogoComponent)
    private dialogoPendenciaComponent: PendenciaDialogoComponent;

    temAvaliador: boolean = false;


    private _tiposPendencia:TipoPendencia[];

    private rmr: any;

    /**
     * Cria uma instancia de AvaliacaoComponent.
     * @param {FormBuilder} _fb
     * @param {PacienteService} servicePaciente
     * @param {Router} router
     * @param {TranslateService} translate
     *
     * @memberOf AvaliacaoComponent
     */
    constructor(private _fb: FormBuilder, private servicePaciente: PacienteService,
            private avaliacaoService: AvaliacaoService, translate: TranslateService,
            private pendenciaService: PendenciaService, private router:Router,
            private autenticacaoService: AutenticacaoService,
            private activatedRouter: ActivatedRoute, private messageBox: MessageBox) {

		super();
        this.paginacao = new Paginacao('', 0, this.quantidadeRegistro);
        this.paginacao.number = 1;

        this.consultaAvaliacaoForm = this._fb.group({
            'rmr': [null, null]
        });
         this.criarPendenciaForm = this._fb.group({
            'tipoPendencia' : [null, Validators.required],
            'descricaoPendencia' : [null, Validators.required]
        });

        this.criarMensagemValidacaoPorFormGroup(this.criarPendenciaForm);

        this.translate = translate;
    }

    /**
     * Método utilizado para fazer a consulta
     *
     * @private
     * @returns {boolean}
     *
     * @memberOf AvaliacaoComponent
     */
    private onSubmit(): boolean {
        if (!this.consultaAvaliacaoForm.get('rmr').value && !this.rmr) {
            return false;
        }

        let rmr = this.consultaAvaliacaoForm.get('rmr').value;

        if(!rmr) {
            rmr = this.rmr;
        }


        this.servicePaciente.obterAvaliacaoAtual(rmr)
            .then(res => {
                this._avaliacao = new Avaliacao(res.avaliacaoAtual.id);
                this._avaliacao.status = res.avaliacaoAtual.status;
                this._avaliacao.aprovado = res.avaliacaoAtual.aprovado;

                this.temAvaliador = res.avaliacaoAtual.medicoResponsavel ? true : false;

                let paciente:Paciente = new Paciente();
                paciente.rmr = res.avaliacaoAtual.paciente.rmr;
                this._avaliacao.paciente = paciente;

                if(this.temAvaliador){
                    this._avaliacao.medicoResponsavel = new Medico(res.avaliacaoAtual.medicoResponsavel.id);
                    let usuario:UsuarioLogado = new UsuarioLogado(res.avaliacaoAtual.medicoResponsavel.usuario.username,"",[],[]);
                    this._avaliacao.medicoResponsavel.usuario = usuario;
                }

                this._evolucao = new Evolucao().jsonToEntity(res.ultimaEvolucao);
                // this._evolucao.altura = res.ultimaEvolucao.altura;
                // this._evolucao.cmv = res.ultimaEvolucao.cmv;
                // this._evolucao.condicaoAtual = res.ultimaEvolucao.condicaoAtual;
                // this._evolucao.condicaoPaciente = res.ultimaEvolucao.condicaoPaciente;
                // this._evolucao.motivo = res.ultimaEvolucao.motivo;
                // this._evolucao.peso = res.ultimaEvolucao.peso;

                // this._evolucao.tiposTransplante = res.ultimaEvolucao.tiposTransplante;
                // this._evolucao.tratamentoAnterior = res.ultimaEvolucao.tratamentoAnterior;
                // this._evolucao.tratamentoAtual = res.ultimaEvolucao.tratamentoAtual;

                setTimeout(() => {
                        Promise.resolve(this.headerPaciente).then(() => {
                            this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(rmr);
                        });

                    }, 1);
                this.listarPendencias(1);
            })
            .catch((error: ErroMensagem) => {
                this.mensagem = "";
                error.listaCampoMensagem.forEach((campoMensagem: CampoMensagem) => {
                    if(this.mensagem == ""){
                        this.mensagem = campoMensagem.mensagem;
                    }else{
                        this.mensagem += campoMensagem.mensagem;
                    }


                })

                this.modalMsg.abrirModal();
            });

        return false;
    }

    private recuperarParametroDaRota() {
        this.activatedRouter.params.subscribe(params => {
            this.rmr = params['idPaciente'];

            if (this.rmr) {
                this.onSubmit();
            }

        });
    }

    private recuperarParametroDaUrl() {
        this.activatedRouter.queryParams.subscribe(params => {
            this.rmr = params['rmr'];

            if (this.rmr) {
                this.onSubmit();
            }

        });
    }

    /**
     *
     *
     * @memberOf AvaliacaoComponent
     */
    ngOnInit(): void {
        this.recuperarParametroDaRota();
        if(!this.rmr){
            this.recuperarParametroDaUrl();
        }


        this.dialogoForm = this.dialogoPendenciaComponent.dialogoForm;
        this.translate.get("avaliacaoPaciente").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.criarPendenciaForm);
            this.setMensagensErro(this.criarPendenciaForm);
        });

        this.pendenciaService.listarTiposPendencia()
            .then(res => {
                this._tiposPendencia = res;
            })
            .catch((error: ErroMensagem) => {
                this.alterarMensagem.emit(error.mensagem);
            });


    }

    public ngOnDestroy(): void {}

    /**
     * Método criar somente para a utilização do botão voltar.
     * Deverá ser removido
     *
     * @memberOf AvaliacaoComponent
     */
    voltarConsulta() {

        if(this.rmr) {
            this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
        }

        this.consultaAvaliacaoForm.get("rmr").setValue("");
        this._avaliacao = null;
        this._evolucao = null;
    }

    /**
     * Modal para aprovar uma avaliação
     *
     * @memberof AvaliacaoComponent
     */
    abrirModalAprovarAvaliacao(){
        let data = {
            aprovarAvaliacao: true,
            confirmar: (value: any) => {
                this._tempoParaTransplante = value['tempoParaTransplante']
                this._observacao = value['observacao'];
                if(this.existePendenciaEmAbertoOuRespondido(this.paginacao.content)){
                    this.pedirConfirmacaoParaAprovarComPendenciasAbertas();
                }
                else{
                    this.finalizarAprovacaoDaAvaliacao();
                }
            }
        }
        this.messageBox.dynamic(data, ModalAprovarReprovarAvaliacaoComponent)
        .withCloseOption(() => {

        })
        .show();
    }

    /**
     * Modal para reprovar uma avaliação
     *
     * @memberof AvaliacaoComponent
     */
    abrirModalReprovarAvaliacao(){
        let data = {
            aprovarAvaliacao: false,
            confirmar: (value: any) => {
                this._observacao = value['observacao'];
                if(this.existePendenciaEmAbertoOuRespondido(this.paginacao.content)){
                    this.pedirConfirmacaoParaReprovarComPendenciasAbertas();
                }
                else {
                    this.finalizarReprovacaoDaAvaliacao();
                }
            }
        }
        this.messageBox.dynamic(data, ModalAprovarReprovarAvaliacaoComponent)
        .withCloseOption(() => {

        })
        .show();
    }

    /**
     * Verifica no array de pendencias se exite alguma pendencia
     * que esteja no status aberta ou respondida
     *
     * @param {Pendencia[]} pendencias
     * @returns {boolean}
     * @memberof AvaliacaoComponent
     */
    existePendenciaEmAbertoOuRespondido(pendencias:Pendencia[]):boolean{

        if(pendencias) {
            return pendencias.some(pendencia => {
                return this.isPendenciaAbertaOuRespondida(pendencia);
            })
        }
        else {
            return false;
        }

    }

    /**
     * Verifica se uma pendencia está no status aberta ou
     * respondida
     *
     * @param {Pendencia} pendencia
     * @returns {boolean}
     * @memberof AvaliacaoComponent
     */
    isPendenciaAbertaOuRespondida(pendencia:Pendencia):boolean{
        return pendencia.statusPendencia.id == StatusPendencias.ABERTA
            || pendencia.statusPendencia.id == StatusPendencias.RESPONDIDA;
    }

    /**
     * Abrir modal para o usuário confirmar a aprovação
     * mesmo que tenha pendencias não finalizadas.
     *
     * @memberof AvaliacaoComponent
     */
    pedirConfirmacaoParaAprovarComPendenciasAbertas(){
        this.modalAprovacaoPendenciaAberta.abrirModal();
    }

    /**
     * Método executado após fechar o modal de confirmação
     * reseta também o campo observação
     *
     * @memberof AvaliacaoComponent
     */
    fecharModalReprovacaoAvaliacao(){
        this.form().get('observacao').setValue("");
    }

    /**
     * Abrir modal para o usuário confirmar a reprovação
     * mesmo que tenha pendencias não finalizadas.
     *
     * @memberof AvaliacaoComponent
     */
    pedirConfirmacaoParaReprovarComPendenciasAbertas(){
        this.modalReprovacaoPendenciaAberta.abrirModal();
    }


    /**
     * Chama o serviço para aprovar a avaliação
     *
     * @memberof AvaliacaoComponent
     */
    finalizarAprovacaoDaAvaliacao(){
        let avaliacao:Avaliacao = new Avaliacao(this._avaliacao.id);
        avaliacao.observacao = this._observacao;
        avaliacao.paciente = new Paciente();
        avaliacao.paciente.tempoParaTransplante = this._tempoParaTransplante;
        this.avaliacaoService.aprovarAvaliacaoPaciente(avaliacao).then(res=>{
            this.messageBox.alert(res.mensagem)
                .withCloseOption(() => {
                    this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                })
                .show();
        },(error:ErroMensagem)=> {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    /**
     * Método para finalizar a reprovação da avaliação
     *
     * @memberof AvaliacaoComponent
     */
    finalizarReprovacaoDaAvaliacao(){
        this.setFieldRequired(this.form(),'observacao');
        let avaliacao:Avaliacao = new Avaliacao(this._avaliacao.id);
        avaliacao.observacao = this._observacao;
        this.avaliacaoService.reprovarAvaliacaoPaciente(avaliacao).then(res=>{
            this.messageBox.alert(res.mensagem)
                .withCloseOption(() => {
                    this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                })
                .show();
        },(error:ErroMensagem)=> {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    /**
     * Método para listar as pendências de uma avaliação
     *
     * @private
     * @param {number} pagina
     *
     * @memberOf AvaliacaoComponent
     */
    private listarPendencias(pagina: number) {
        this.mensagem = "";
        this.avaliacaoService.listarPendencias(this._avaliacao.id, pagina - 1, this.quantidadeRegistro)
            .then((res: any) => {
                this.paginacao.content = res.content;
                this.paginacao.totalElements = res.totalElements;
                this.paginacao.quantidadeRegistro = this.quantidadeRegistro;
                this.paginacao.number = pagina;
            })
            .catch((erro: ErroMensagem)  => {
                if (erro.listaCampoMensagem.length != 0) {
                    erro.listaCampoMensagem.forEach((campoMensagem: CampoMensagem) => {
                        if(this.mensagem === undefined || this.mensagem == ""){
                            this.mensagem = campoMensagem.mensagem;
                        }else{
                            this.mensagem += campoMensagem.mensagem;
                        }
                    });
                }
                else {
                    this.mensagem = erro.mensagem.toString();
                }
                this.modalMsg.abrirModal();
            });
    }

    /**
     * Método acionado quando muda a página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    mudarPagina(event: any) {
        this.listarPendencias(event.page);
    }

    /**
     * Método acionado quando é alterado a quantidade de registros por página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    selecinaQuantidadePorPagina(event: any, modal: any) {
        this.listarPendencias(1);
    }

    private cancelarPendencia(id: number) {
        this.idPedenciaASerCancelada = id;
        this.modalCancelaPendencia.abrirModal();
    }

    /**
     * Método utilizado quando o modal de cancelamento for confirmado
     *
     *
     * @memberOf AvaliacaoComponent
     */
    executarCancelarPendencia() {
        this.pendenciaService.cancelarPendencia(this.idPedenciaASerCancelada)
            .then(res => {
                this.idPedenciaASerCancelada = null;
                this.modalMsg.mensagem = res.mensagem;
                this.listarPendencias(this.paginacao.number);
            })
            .catch((erro: ErroMensagem) => {
                this.mensagem = "";
                if (erro.listaCampoMensagem && erro.listaCampoMensagem.length >= 0 ) {
                    erro.listaCampoMensagem.forEach((campoMensagem: CampoMensagem) => {
                        this.mensagem += campoMensagem.mensagem;
                    })
                }
                else {
                    this.mensagem = erro.mensagem.toString();
                }
                this.modalMsg.abrirModal();
            });
    }

    /**
     * Método utilizado quando o modal do cancelamento for fechado sem confirmar
     *
     *
     * @memberOf AvaliacaoComponent
     */
    fecharModalCancelarPendencia() {
        this.idPedenciaASerCancelada = null;
    }

    private fecharPendencia(id: number) {
        this.idPedenciaASerFechada = id;
        this.modalFechaPendencia.abrirModal();
    }


    /**
     * Método utilizado quando o modal para fechar a pendencia for confirmado
     *
     *
     * @memberOf AvaliacaoComponent
     */
    executarFecharPendencia() {
        this.pendenciaService.fecharPendencia(this.idPedenciaASerFechada)
            .then(res => {
                this.idPedenciaASerFechada = null;
                this.modalMsg.mensagem = res.mensagem;
                this.listarPendencias(this.paginacao.number)
            })
            .catch((erro: ErroMensagem) => {
                this.mensagem = "";
                if (erro.listaCampoMensagem && erro.listaCampoMensagem.length >= 0 ) {
                    erro.listaCampoMensagem.forEach((campoMensagem: CampoMensagem) => {
                        this.mensagem += campoMensagem.mensagem;
                    })
                }
                else {
                    this.mensagem = erro.mensagem.toString();
                }
                this.modalMsg.abrirModal();
            });
    }

    /**
     * Método utilizado quando o modal para fechar a pendencia for fechado sem confirmar
     *
     *
     * @memberOf AvaliacaoComponent
     */
    fecharModalFecharPendencia() {
        this.idPedenciaASerFechada = null;
    }

    retornarParaLista(){
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

    /**
     *
     * Método obrigatorio que retorna o formulario
     * @returns {FormGroup}
     * @memberof AvaliacaoComponent
     */
    public form(): FormGroup {
        return this.consultaAvaliacaoForm;
    }

    /**
     *
     * Método para preencher o formulario caso seja necessário uma edição
     * @param {Avaliacao} entidade
     * @memberof AvaliacaoComponent
     */
    public preencherFormulario(entidade: Avaliacao): void {
        throw new Error("Method not implemented.");
    }

    /**
     * Método que valida os campos de todo formulário, alterando o comportamento do mesmo,
     * de acordo com as suas obrigatoriedades, validações customizadas, tudo que envolva
     * a marcação do campo em vermelho (comportamento atual)
     *
     * @return TRUE se o formulário é válido
     */
    public validarForm(form:FormGroup):boolean{
        this.clearMensagensErro(form);
        let valid: boolean = this.validateFields(form);
        this.setMensagensErro(form);
        return valid;
    }

    /**
     * Método chamado para inclusão de uma nova pendência
     * associada a avaliação.
     *
     */
    public criarPendencia():void{

        if(this.validarFormularioPendencia()){
            let tipoPendenciaId:string =
                this.criarPendenciaForm.controls["tipoPendencia"].value;
            let descricao:string =
                this.criarPendenciaForm.controls["descricaoPendencia"].value;

            let pendencia:Pendencia = new Pendencia();
            pendencia.avaliacao = this._avaliacao;
            pendencia.dataCriacao = new Date();
            pendencia.descricao = descricao;
            pendencia.tipoPendencia = new TipoPendencia(+tipoPendenciaId);

            this.avaliacaoService.salvarPendencia(pendencia)
                .then(res => {
                    this.modalMsg.mensagem = res.mensagem;
                    this.fecharModalCriarPendencia();
                    this.listarPendencias(this.paginacao.number);
                },
                (error: ErroMensagem) => {
                    this.fecharModalCriarPendencia();
                    this.marcarCamposInvalidosRetornadosBackend(error.listaCampoMensagem);
                });
        }
    }

    /**
     * Marca os campos de todas as etapas com erro, identificadas de acordo
     * com a mensagem retornada do backend
     *
     * @param mensagens
     */
    private marcarCamposInvalidosRetornadosBackend(mensagens:CampoMensagem[]){
        mensagens.forEach(mensagemErro => {
            this.formErrors[mensagemErro.campo] = mensagemErro.mensagem;
        });
    }

    /**
     * Metodo para fechar o modal
     *
     * @param {any} modalAprovacaoAvaliacao
     * @memberof AvaliacaoComponent
     */
    fecharModalCriarPendencia(){
        this.clearMensagensErro(this.criarPendenciaForm);
        this.criarPendenciaForm.reset();
        this.criarPendenciaModal.hide();
    }

    /**
     * Valida o formulário criação de pendências.
     *
     */
    private validarFormularioPendencia():Boolean{
        this.clearMensagensErro(this.criarPendenciaForm);
        let preenchimentoOk: boolean = this.validateFields(this.criarPendenciaForm);
        this.setMensagensErro(this.criarPendenciaForm);
        return preenchimentoOk;
    }

    public limparFormErrosCriacaoPendencia(nomeCampo: string) {
        this.clearMensagensErro(this.criarPendenciaForm, null, nomeCampo);
        this.setMensagensErro(this.criarPendenciaForm, null, nomeCampo);
    }

    /**
     * Antes de abrir o modal deve fechar a caixa de respostsa
     *
     * @private
     * @param {Pendencia} pendencia
     * @memberof AvaliacaoComponent
     */
    private visualizarDialogo(pendencia:Pendencia){
        this.dialogoPendenciaComponent.fecharCaixaParaResponder();
        this.esconderBotoes();
        this.abrirModalPendencia(pendencia);
    }

    private abrirModalPendencia(pendencia:Pendencia){

        this.pendenciaAtual = pendencia;
        this.respostasAvaliador = [];
        this.pendenciaService.listarHistoricoPendencia(pendencia.id).then(res=>{

            if (res) {
                res.forEach(element => {

                    let resposta:RespostaPendenciaDTO = new RespostaPendenciaDTO();
                    resposta.resposta = element.resposta;
                    resposta.usuario = element.usuario;
                    resposta.dataFormatadaDialogo = element.dataFormatadaDialogo;
                    if (element.evolucao) {
                        resposta.evolucao = element.evolucao;
                    }
                    if (element.exame) {
                        resposta.exame = element.exame;
                    }
                    this.respostasAvaliador.push(resposta);
                    this.dialogoPendenciaComponent.preencherDtoComLinkExameSeNecessario(this.respostasAvaliador);
                    this.dialogoPendenciaComponent.dialogoComponent.dialogoBuilder = new PendenciaDialogoBuilder().buildComentarios(this.respostasAvaliador);
                });
            }

        },(error:ErroMensagem)=> {
            this.modalPendencia.hide();
            error.listaCampoMensagem.forEach(obj => {
                this.modalMsg.mensagem = obj.mensagem;
            })

            this.modalMsg.abrirModal();
        });

        this.modalPendencia.show();
    }

    private abrirCaixaParaResponder(){
        this.esconderBotoesDialogo = true;
        this.dialogoPendenciaComponent.abrirCaixaParaResponder();
    }

    esconderBotoes(){
        this.esconderBotoesDialogo = false;

    }

    private fecharPendenciaPorModalResposta(){
        this.idPedenciaASerFechada = this.pendenciaAtual.id;
        this.modalPendencia.hide();
        this.executarFecharPendencia();
    }

    private cancelarPendenciaPorModalResposta(){
        this.idPedenciaASerCancelada = this.pendenciaAtual.id;
        this.modalPendencia.hide();
        this.executarCancelarPendencia();
    }

    exibirBotaoModalFecharPendencia():boolean{
        if(this.pendenciaAtual){
            return this.autorizarFecharPendencia(this.pendenciaAtual) && !this.esconderBotoesDialogo;
        } else {
            return false;
        }

    }

    exibirBotesModalPendencia():boolean{
        if(this.pendenciaAtual && this.pendenciaAtual.statusPendencia){
            return (this.pendenciaAtual.statusPendencia.descricao == 'ABERTA' ||
             this.pendenciaAtual.statusPendencia.descricao == 'RESPONDIDA') && !this.esconderBotoesDialogo;
        }
        return false;

    }

    fecharModalPendencia(){
        this.esconderBotoes();
        this.dialogoForm.get("comentario").setValue("")
        this.modalPendencia.hide();
    }

    private abrirResponderPendencia(pendencia:Pendencia){
        this.abrirCaixaParaResponder();
        this.abrirModalPendencia(pendencia);
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.AvaliacaoComponent];
    }


    /**
     * Quantidade de exames de uma pendencia
     *
     * @author Bruno Sousa
     * @private
     * @param {Pendencia} pendencia
     *
     * @memberOf AvaliacaoComponent
     */
    quantidadeDeExamesNaPendenciaRespondida(pendencia: Pendencia): number {
        let quantidade = 0;
        if (pendencia && pendencia.tipoPendencia.descricao == 'EXAME') {
            if (pendencia.respostas) {
                pendencia.respostas.forEach((responsta: RespostaPendencia) => {
                    if (responsta.exame && responsta.exame.id) {
                        quantidade++;
                    }
                })
            }
        }
        return quantidade;
    }

    /**
     * retrna o id do exame para uma pendencia somente com um exame
     *
     *
     * @author Bruno Sousa
     * @private
     * @param {Pendencia} pendencia
     *
     * @memberOf AvaliacaoComponent
     */
    private obterIdExamePendenciaRespondidaComUmExame(pendencia: Pendencia): number {
        let idExame: number = 0;
        if (pendencia.tipoPendencia.descricao == 'EXAME') {
            let quantidade = 0;
            if (pendencia.respostas) {
                pendencia.respostas.forEach((responsta: RespostaPendencia) => {
                    if (responsta.exame && responsta.exame.id) {
                        quantidade++;
                        idExame = responsta.exame.id;
                    }
                })
            }
            if (quantidade > 1 ) {
                return 0;
            }
        }
        return idExame;
    }

    /**
     * retorna o id da evolução para uma pendencia respondida somente com uma evolução
     *
     *
     * @author Bruno Sousa
     * @private
     * @param {Pendencia} pendencia
     *
     * @memberOf AvaliacaoComponent
     */
    private obterIdEvolucaoPendenciaRespondidaComUmaEvolucao(pendencia: Pendencia): number {
        let idEvolucao: number = 0;
        if (pendencia && pendencia.tipoPendencia.descricao == 'EVOLUÇÃO') {
            let quantidade = 0;
            if (pendencia.respostas) {
                pendencia.respostas.forEach((responsta: RespostaPendencia) => {
                    if (responsta.evolucao && responsta.evolucao.id) {
                        quantidade++;
                        idEvolucao = responsta.evolucao.id;
                    }
                })
            }
            if (quantidade > 1 ) {
                return 0;
            }
        }
        return idEvolucao;
    }

    /**
     * Retorna a quantidade de evoluçoes de uma pendencia
     *
     * @author Bruno Sousa
     * @private
     * @param {Pendencia} pendencia
     *
     * @memberOf AvaliacaoComponent
     */
    private quantidadeDeEvolucoesNaPendenciaRespondida(pendencia: Pendencia): number {
        let quantidade = 0;
        if (pendencia && pendencia.tipoPendencia.descricao == 'EVOLUÇÃO') {
            if (pendencia.respostas) {
                pendencia.respostas.forEach((responsta: RespostaPendencia) => {
                    if (responsta.evolucao && responsta.evolucao.id) {
                        quantidade++;
                    }
                })
            }
        }
        return quantidade;
    }

    /**
     * Abre a tela de consulta de exames.
     * Executado no grid de pendencias
     *
     * @author Bruno Sousa
     * @private
     * @param {Pendencia} pendencia
     *
     * @memberOf AvaliacaoComponent
     */
    private abrirConsultaExame(pendencia: Pendencia) {
        let quantidade: number = this.quantidadeDeExamesNaPendenciaRespondida(pendencia);
        if (quantidade == 1) {
            this.router.navigateByUrl('/pacientes/' + this._avaliacao.paciente.rmr + '/exames/' + this.obterIdExamePendenciaRespondidaComUmExame(pendencia))
        }
        else if (quantidade > 1) {
            this.router.navigateByUrl('/pacientes/' + this._avaliacao.paciente.rmr + '/exames')
        }
    }

    /**
     * Abre a tela de consulta evolução.
     * executado no grid de pendencias
     *
     * @author Bruno Sousa
     * @private
     * @param {Pendencia} pendencia
     *
     * @memberOf AvaliacaoComponent
     */
    private abrirConsultaEvolucao(pendencia: Pendencia) {
        let quantidade: number = this.quantidadeDeEvolucoesNaPendenciaRespondida(pendencia);
        if (quantidade == 1) {
            this.router.navigateByUrl('/pacientes/' + this._avaliacao.paciente.rmr + '/evolucoes/' + this.obterIdEvolucaoPendenciaRespondidaComUmaEvolucao(pendencia))
        }
        else if (quantidade > 1) {
            this.router.navigateByUrl('/pacientes/' + this._avaliacao.paciente.rmr + '/evolucoes')
        }

    }


    /**
     * Fecha o modal de resposta exibindo mensagem
     *
     * @private
     * @param {any} event
     * @memberof AvaliacaoMedicoComponent
     */
    finalizarRespostaSucesso(){
        this.fecharModalPendencia();
        this.listarPendencias(this.paginacao.number);
    }

    /**
     * Fecha o modal de resposta exibindo erro
     *
     * @private
     * @param {any} event
     * @memberof AvaliacaoMedicoComponent
     */
    finalizarRespostaErro(event){
        this.fecharModalPendencia();
        this.modalMsg.mensagem = event;
        this.modalMsg.abrirModal();
    }

    /**
     * Retorna a quantidade de evoluçoes de uma pendencia
     *
     * @author Bruno Sousa
     * @private
     * @param {Pendencia} pendencia
     *
     * @memberOf AvaliacaoComponent
     */
    quantidadeDeEvolcoesNaPendenciaRespondida(pendencia: Pendencia): number {
        let quantidade = 0;
        if (pendencia && pendencia.tipoPendencia.descricao == 'EVOLUÇÃO') {
            if (pendencia.respostas) {
                pendencia.respostas.forEach((responsta: RespostaPendencia) => {
                    if (responsta.evolucao && responsta.evolucao.id) {
                        quantidade++;
                    }
                })
            }
        }
        return quantidade;
    }

    /**
     * Define se tem acesso a aprovar ou reprovar a avaliação.
     * @return TRUE se autoriza acesso e FALSE, caso não possua.
     */
    public autorizarAprovarOuReprovar():boolean{
        return (this._avaliacao &&
                    this._avaliacao.status == 1 &&
                        this.verificarSeUsuarioLogadoEhAvaliador() && this.temAvaliador);
    }

    /**
     * Verificar se o usuário logado é o médico avaliador responsável por avaliacao
     * o cadastro do paciente.
     * @return TRUE se possui acesso, FALSE caso não possua.
     */
    verificarSeUsuarioLogadoEhAvaliador():boolean{
        if(this._avaliacao){
            // let avaliador:Medico = this.avaliacao.medicoResponsavel;
            let usuarioLogado:UsuarioLogado = this.autenticacaoService.usuarioLogado();
            return usuarioLogado.perfis.some(perfil => perfil == Perfis.AVALIADOR)
            // return (avaliador.usuario.username == usuarioLogado.username);
        }
        return false;
    }

    /**
     * Verifica se possui autorização para RESPONDER uma pendência.
     *
     * @param pendencia a ser utilização para verificação.
     */
    public autorizarResponderPendencia(pendencia:Pendencia):boolean{
        if(pendencia == null){
            return false;
        }
        let statusPendencia:StatusPendencia = pendencia.statusPendencia;
        return statusPendencia.id == StatusPendencias.ABERTA ||
                (statusPendencia.id == StatusPendencias.RESPONDIDA &&
                    this.verificarSeUsuarioLogadoEhAvaliador());
    }

    /**
     * Verifica se possui autorização para FECHAR uma pendência.
     *
     * @param pendencia a ser utilização para verificação.
     */
    public autorizarFecharPendencia(pendencia:Pendencia): boolean{
        if(pendencia == null){
            return false;
        }
        let statusPendencia:StatusPendencia = pendencia.statusPendencia;
        return statusPendencia.id == StatusPendencias.RESPONDIDA &&
                    this.verificarSeUsuarioLogadoEhAvaliador();
    }

    /**
     * Verifica se possui autorização para CANCELAR uma pendência.
     *
     * @param pendencia a ser utilização para verificação.
     */
    public autorizarCancelarPendencia(pendencia:Pendencia): boolean{
        if(pendencia == null){
            return false;
        }
        let statusPendencia:StatusPendencia = pendencia.statusPendencia;
        return (statusPendencia.descricao == 'ABERTA'
                    || statusPendencia.descricao == 'RESPONDIDA') &&
                        this.verificarSeUsuarioLogadoEhAvaliador();
    }

    /**
     * Formata a data de criação com dia/mes/ano min:seg.
     *
     * @param pendencia pendência onde está contida a data de criação.
     */
    private formatarDataCriacao(pendencia:Pendencia): Date{
        if(pendencia){
            let dataCriacao:Date = PacienteUtil.parserDataComHora(pendencia.dataCriacao);
            return dataCriacao;
        }
        return null;
    }

    /**
     * Método para iniciar a avaliação do paciente para o avaliador logado
     */
    private atribuirAvaliacaoAoAvaliadorLogado(){
        this.avaliacaoService.atribuirAvaliacaoAoAvaliadorLogado(this._avaliacao.id).then(res => {
            this._avaliacao.medicoResponsavel = new Medico();
            this._avaliacao.medicoResponsavel.usuario = this.autenticacaoService.usuarioLogado();
            this.temAvaliador = true;
        })
        .catch((error: ErroMensagem) => {
            this.mensagem = "";
            error.listaCampoMensagem.forEach((campoMensagem: CampoMensagem) => {
                if(this.mensagem == ""){
                    this.mensagem = campoMensagem.mensagem;
                }else{
                    this.mensagem += campoMensagem.mensagem;
                }

            })

            this.modalMsg.abrirModal();
        });
    }

	public get avaliacao(): Avaliacao {
		return this._avaliacao;
    }

	public get evolucao(): Evolucao {
		return this._evolucao;
    }

	public get tiposPendencia(): TipoPendencia[] {
		return this._tiposPendencia;
	}


}
