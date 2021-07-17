import { RespostaPendenciaDTO } from '../resposta.pendencia.dto';
import { ExameService } from '../../cadastro/exame/exame.service';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { TranslateService } from '@ngx-translate/core';
import { BaseForm } from '../../../shared/base.form';
import { TipoPendencia } from '../../../shared/dominio/tipo.pendencia';
import { ErroMensagem } from '../../../shared/erromensagem';
import { PendenciaService } from '../pendencia.service';
import { RespostaPendencia } from '../resposta.pendencia';
import { Avaliacao } from '../avaliacao';
import { Pendencia } from '../pendencia';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { Observable } from 'rxjs';
import { Component, Input, ViewChild, EventEmitter, Output, OnInit, ViewEncapsulation } from '@angular/core';
import { DialogoComponent } from '../../../shared/dialogo/dialogo.component';

/**
 * Classe Component para padronizar o modal
 * @author Fillipe Queiroz
 */
@Component({
    moduleId: module.id,
    selector: 'dialogo-pendencia',
    templateUrl: './pendencia.dialogo.component.html',
    styleUrls: ['./pendencia.dialogo.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class PendenciaDialogoComponent extends BaseForm<Object> implements OnInit {

    @Input("pendenciaAtual")
    private _pendenciaAtual: Pendencia;

    @Input("avaliacao")
    private _avaliacao: Avaliacao;

    // @Input("respostasAvaliador")
    // private respostasAvaliador: RespostaPendenciaDTO[] = [];
    @ViewChild("dialogoComponent")
    public dialogoComponent: DialogoComponent;

    private _usernameUsuarioLogado: string;
    public dialogoForm: FormGroup;
    public esconderCaixaResponderPendencia: boolean = true;
    exibePerguntaSeResponde: boolean = false;

    @Output()
    public finalizarRespostaSucesso: EventEmitter<String> = new EventEmitter<String>();

    @Output()
    public finalizarRespostaErro: EventEmitter<String> = new EventEmitter<String>();

    @Output()
    public cancelarRespostaEmitter: EventEmitter<String> = new EventEmitter<String>();

    @ViewChild("exameDescartadoModal")
    private modalMsgExameDescartado;

    /*
    * Mensagem que deverá ser exibida no {{exameDescartadoModal}}
    */
    mensagemModal: string;

    ngOnInit() {
        this.translate.get("dialogoForm").subscribe(res => {
            this._formLabels = res;
            super.criarMensagemValidacaoPorFormGroup(this.dialogoForm);
        });
    }

    constructor(private _fb: FormBuilder, private autenticacaoService: AutenticacaoService,
        private pendenciaService: PendenciaService, private router: Router, translate: TranslateService, private exameService: ExameService) {
        super();
        this.translate = translate;
        this.dialogoForm = this._fb.group({
            'comentario': [null, Validators.required]
        });
    }

    /**
     *Verificar aqui se o usuario logado é o mesmo da avaliacao
     *Se sim, as resposta do usuario avaliador serão o balão verde
     *Caso contrario é o login do medico e deve exibir o balão verde para
     *os comentários do médico.
     * @private
     * @param {any} idUsuario 
     * @returns {string} 
     * @memberof DialogoComponente
     */
    getEstiloBalaoConversa(usernameRespostaAtual?: string): string {
        if (usernameRespostaAtual) {
            return this.buscarEstiloPorTextoAtual(usernameRespostaAtual);
        } else {
            return this.buscarEstiloPorUsuarioLogado();
        }
    }

    /**
     * Busca somente pelo usuario logado, para a descrição da pendencia
     * 
     * @private
     * @returns {string} 
     * @memberof DialogoComponente
     */
    private buscarEstiloPorUsuarioLogado(): string {
        if (this._avaliacao) {
            this._usernameUsuarioLogado = this.autenticacaoService.usuarioLogado().username;
            if (this._avaliacao.medicoResponsavel) {
                if (this._usernameUsuarioLogado == this._avaliacao.medicoResponsavel.usuario.username) {
                    return "balaoPendenciaResposta";
                }
            }
            return "balaoPendenciaPergunta";
        }
        return "";
    }

    /**
     * Busca o estilo por cada texto da conversa, pois cada texto é de 
     * um usuario diferente
     * 
     * @private
     * @param {string} [usernameRespostaAtual] 
     * @returns {string} 
     * @memberof DialogoComponente
     */
    private buscarEstiloPorTextoAtual(usernameRespostaAtual?: string): string {
        if (usernameRespostaAtual == this._usernameUsuarioLogado) {
            return "balaoPendenciaResposta";
        }
        return "balaoPendenciaPergunta";
    }
    /**
     * Direciona para tela de exames caso exame não tenha sido descartado.
     * @param  {string} input
     */
    public irParaDetalhesExame(input: string) {
        let idExame: string = input.substring(input.lastIndexOf("/") + 1, input.length)
        this.exameService.obterExame(Number(idExame))
            .then(result => {
                this.router.navigateByUrl(input);
            }, (error: ErroMensagem) => {
                this.mensagemModal = error.listaCampoMensagem[0].mensagem;
                this.modalMsgExameDescartado.abrirModal();
            });
    }

    /**
     * Abre a caixa para poder inputar um comentário
     * 
     * @private
     * @memberof DialogoComponente
     */
    public abrirCaixaParaResponder() {
        this.setFieldRequired(this.form(), 'comentario')
        if (this.autenticacaoService.temPerfilMedico() && this.ehMedicoResponsavelPaciente()) {
            this.exibePerguntaSeResponde = true;
        }

        this.formErrors = {}
        this.esconderCaixaResponderPendencia = false;
    }

    private ehMedicoResponsavelPaciente() {
        if (this._avaliacao && this._avaliacao.paciente && this._avaliacao.paciente.medicoResponsavel && this._avaliacao.paciente.medicoResponsavel.usuario) {
            return this._avaliacao.paciente.medicoResponsavel.usuario.username == this.autenticacaoService.usuarioLogado().username;
        }
        else {
            return false;
        }

    }

    /**
     * Fecha a caixa para não poder mais inputar comentários
     * 
     * @private
     * @memberof DialogoComponente
     */
    public fecharCaixaParaResponder() {
        this.form().markAsUntouched();
        this.esconderCaixaResponderPendencia = true;
        this.resetFieldRequired(this.form(), 'comentario')
        this.cancelarRespostaEmitter.emit();
    }

    /**
     * Associa um novo comentário na pendência selecionada.
     */
    comentarPendencia(): void {
        this.atualizarPendencia(false);
    }

    /**
     * Associa um novo comentário e marca a pendência como RESPONDIDA
     * na pendência selecionada.
     */
    private comentarEResponderPendencia(): void {
        this.atualizarPendencia(true);
    }

    /**
     * Atualiza a pendência, de acordo com o status atual e com o parâmetro informado.
     * 
     * @param sinalizarPendenciaComoRespondida indica se a resposta finaliza (status: respondida) a pendência.
     */
    private atualizarPendencia(sinalizarPendenciaComoRespondida: boolean) {
        if (super.validateForm()) {
            let respostaPendencia: RespostaPendencia = new RespostaPendencia();
            respostaPendencia.resposta = this.dialogoForm.get("comentario").value;
            respostaPendencia.pendencias = [];
            respostaPendencia.pendencias.push(this._pendenciaAtual);
            if (this.autenticacaoService.temPerfilMedico() && this.ehMedicoResponsavelPaciente()) {
                respostaPendencia.respondePendencia = sinalizarPendenciaComoRespondida;
                this.pendenciaService.responderPendencia(this._pendenciaAtual.id, respostaPendencia).then(res => {
                    this.dialogoForm.get("comentario").setValue("")
                    this.form().markAsUntouched();
                    this.finalizarRespostaSucesso.emit(res.mensagem);

                }, (error: ErroMensagem) => {
                    let mensagem;
                    error.listaCampoMensagem.forEach(obj => {
                        mensagem = obj.mensagem;
                    })
                    this.finalizarRespostaErro.emit(mensagem);

                });
            }
            else {
                this.pendenciaService.reabrirPendencia(this._pendenciaAtual.id, respostaPendencia).then(res => {
                    this.dialogoForm.get("comentario").setValue("")
                    this.form().markAsUntouched();
                    this.finalizarRespostaSucesso.emit(res.mensagem);

                }, (error: ErroMensagem) => {
                    let mensagem;
                    error.listaCampoMensagem.forEach(obj => {
                        mensagem = obj.mensagem;
                    })
                    this.finalizarRespostaErro.emit(mensagem);

                });
            }


        }

    }

    /**
     * Retorna o estilo do icone da resposta caso ela possua um exame/evolução
     * 
     * @author Bruno Sousa
     * @param {TipoPendencia} tipoPendencia 
     * @param {RespostaPendencia} respostaPendencia 
     * @returns {string} 
     * 
     * @memberOf AvaliacaoComponent
     */
    private getEstiloIconeAnexo(tipoPendencia: TipoPendencia, respostaPendencia: RespostaPendenciaDTO): string {

        let estilo = "";
        if (respostaPendencia) {
            if (tipoPendencia.descricao == 'EVOLUÇÃO' && respostaPendencia.evolucao) {
                estilo = 'glyphicon glyphicon-hourglass linkExameEvolucao';
            }
            else if (tipoPendencia.descricao == 'EXAME' && respostaPendencia.exame) {
                estilo = 'glyphicon glyphicon-tint linkExameEvolucao';
            }
        }
        return estilo;
    }

    public preencherDtoComLinkExameSeNecessario(respostas: RespostaPendenciaDTO[]) {

        respostas.forEach(resposta => {
            resposta.link = this.recuperaLinkConsultaExameEvolucaoPelaRespostaPendencia(this._pendenciaAtual.tipoPendencia, resposta);
            if (resposta.link)
                resposta.estiloIcone = this.getEstiloIconeAnexo(this._pendenciaAtual.tipoPendencia, resposta);

        })
        console.log(respostas)
    }

    /**
     * Abre a tela de consulta de exame ou evolução dependendo do tipo da pendencia e se a resposta da pendencia possui exame ou evolução.
     * Esse método é executado na tela de pendência
     * 
     * @author Bruno Sousa
     * @private
     * @param {TipoPendencia} tipoPendencia 
     * @param {RespostaPendencia} respostaPendencia 
     * 
     * @memberOf AvaliacaoComponent
     */
    private recuperaLinkConsultaExameEvolucaoPelaRespostaPendencia(tipoPendencia: TipoPendencia, respostaPendencia: RespostaPendenciaDTO) {
        if (tipoPendencia.descricao == 'EVOLUÇÃO' && respostaPendencia.evolucao) {
            return '/pacientes/' + this._avaliacao.paciente.rmr + '/evolucoes/' + this.obterIdAnexoRespostaPendencia(tipoPendencia, respostaPendencia);
        }
        else if (tipoPendencia.descricao == 'EXAME' && respostaPendencia.exame) {
            let idExame: number = this.obterIdAnexoRespostaPendencia(tipoPendencia, respostaPendencia);
            // this.exameService.obterExame(idExame)
            //     .then(result => {
            //     }, (error: ErroMensagem) => {
            //         this.mensagemModal = error.listaCampoMensagem[0].mensagem;
            //         this.modalMsgExameDescartado.abrirModal();
            //     });
            return '/pacientes/' + this._avaliacao.paciente.rmr + '/exames/' + idExame;


        }

    }

    /**
     * Retorna o id do exame/evolução dependendo do tipo da pendencia e se a resposta contém um exame/evolução
     * 
     * @author Bruno Sousa
     * @private
     * @param {TipoPendencia} tipoPendencia 
     * @param {RespostaPendencia} respostaPendencia 
     * @returns {number} 
     * 
     * @memberOf AvaliacaoComponent
     */
    private obterIdAnexoRespostaPendencia(tipoPendencia: TipoPendencia, respostaPendencia: RespostaPendenciaDTO): number {
        let idAnexo: number = 0;
        if (tipoPendencia.descricao == 'EVOLUÇÃO' && respostaPendencia.evolucao) {
            idAnexo = respostaPendencia.evolucao
        }
        else if (tipoPendencia.descricao == 'EXAME' && respostaPendencia.exame) {
            idAnexo = respostaPendencia.exame;
        }

        return idAnexo;
    }

    /**
     * Exibe o icone de exame/evolução no balão de resposta de acordo como o tipo da pendencia e se existe exame/evolução atribuído à resposta
     * 
     * @author Bruno Sousa
     * @private
     * @param {TipoPendencia} tipoPendencia 
     * @param {RespostaPendencia} respostaPendencia 
     * @returns {boolean} 
     * 
     * @memberOf AvaliacaoComponent
     */
    private exibeIconeAnexoRespostaPendencia(tipoPendencia: TipoPendencia, respostaPendencia: RespostaPendenciaDTO): boolean {
        let mostra: boolean = false;
        if (tipoPendencia.descricao == 'EVOLUÇÃO' && respostaPendencia.evolucao) {
            mostra = true;
        }
        else if (tipoPendencia.descricao == 'EXAME' && respostaPendencia.exame) {
            mostra = true;
        }
        return mostra;
    }


    public form(): FormGroup {
        return this.dialogoForm;
    }

    public preencherFormulario(entidade: Pendencia): void {
        throw new Error("Method not implemented.");
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.NovoExameComponent];
    }

	public get pendenciaAtual(): Pendencia {
		return this._pendenciaAtual;
	}

	public get avaliacao(): Avaliacao {
		return this._avaliacao;
	}
    



}