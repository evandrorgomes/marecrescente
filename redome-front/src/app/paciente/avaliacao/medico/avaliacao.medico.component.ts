import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { PendenciaDialogoComponent } from '../pendenciadialogo/pendencia.dialogo.component';
import { HeaderPacienteComponent } from '../../consulta/identificacao/header.paciente.component';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { PendenciaDialogoBuilder } from '../../../shared/dialogo/pendencia.dialogo.builder';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { StatusPendencias } from "../../../shared/enums/status.pendencia";
import { TipoPendencias } from '../../../shared/enums/tipo.pendencia';
import { ErroMensagem } from '../../../shared/erromensagem';
import { Avaliacao } from "../avaliacao";
import { AvaliacaoService } from '../avaliacao.service';
import { Pendencia } from '../pendencia';
import { PendenciaService } from '../pendencia.service';
import { RespostaPendencia } from '../resposta.pendencia';
import { CampoMensagem } from '../../../shared/campo.mensagem';
import { Paginacao } from '../../../shared/paginacao';
import { BuscaService } from '../../busca/busca.service';
import { Evolucao } from '../../cadastro/evolucao/evolucao';
import { PacienteService } from '../../paciente.service';
import { RespostaPendenciaDTO } from '../resposta.pendencia.dto';

/**
 * Component responsavel avaliação do paciente pelo médico do centro avaliador
 * @author Bruno Sousa
 * @export
 * @class AvaliacaoComponent
 * @implements {OnInit}
 */
@Component({
    selector: 'avaliacao',
    templateUrl: './avaliacao.medico.component.html'
})
export class AvaliacaoMedicoComponent implements OnInit, OnDestroy, PermissaoRotaComponente {

    private _avaliacao: Avaliacao;
    private _evolucao: Evolucao;
    idRmrSelecionado:number;
    pendenciaAtual:Pendencia;
    private dialogoForm: FormGroup;

    @ViewChild(PendenciaDialogoComponent)
    private dialogoPendenciaComponent: PendenciaDialogoComponent;
    

    @ViewChild("modalPendencia")
    private modalPendencia;

    @ViewChild("modalMsg")
    private modalMsg;

    @ViewChild("modalMsgErroAvaliacao")
    private modalMsgErroAvaliacao;

    @ViewChild(HeaderPacienteComponent)
    private headerPaciente: HeaderPacienteComponent;

    paginacao: Paginacao;
    quantidadeRegistro: number = 10; 
    private respostasAvaliador:RespostaPendenciaDTO[] = [];

    exibirBotaoNovaResposta:boolean = false;
    exibirBotaoNovoExame:boolean = false;
    exibirBotaoNovaEvolucao:boolean = false;
    
    /**
     * Indica se o status da avaliação deve ser exibido, ou seja, 
     * se avaliação foi realizada ou não (atributo aprovado é nulo).
     */
    exibirStatusAvaliacao:boolean;

    /**
     * Indica quando status está aprovado ou não.
     */
    avaliacaoAprovada:boolean;


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
            private pendenciaService: PendenciaService, private activatedRouter:ActivatedRoute, 
            private router:Router, private buscaService:BuscaService) {
        this.paginacao = new Paginacao('', 0, this.quantidadeRegistro);        
        this.paginacao.number = 1;
    }

    public ngOnDestroy(): void {}

    /**
     *  
     * 
     * @memberOf AvaliacaoComponent
     */
    ngOnInit(): void {
        this.activatedRouter.params.subscribe(params => {
            this.idRmrSelecionado = params['idPaciente']; 
            this.dialogoForm = this.dialogoPendenciaComponent.dialogoForm;
            this.servicePaciente.obterAvaliacaoAtual(this.idRmrSelecionado)
            .then(res => {
                this._avaliacao = new Avaliacao();
                Object.assign(this._avaliacao, res.avaliacaoAtual);

                this.exibirStatusAvaliacao = this._avaliacao.aprovado != null;
                
                if(this.exibirStatusAvaliacao){
                    this.avaliacaoAprovada = this._avaliacao.aprovado;
                }

                this._evolucao = new Evolucao();
                Object.assign(this._evolucao, res.ultimaEvolucao);

                Promise.resolve(this.headerPaciente).then(() => {
                    this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.idRmrSelecionado);
                });
    

                this.listarPendencias(this.paginacao.number);
            })
            .catch((error: ErroMensagem) => {
                if(error.listaCampoMensagem){
                    error.listaCampoMensagem.forEach((campoMensagem: CampoMensagem) => {
                    this.modalMsgErroAvaliacao.mensagem = campoMensagem.mensagem;    
                    })
                    
                    this.modalMsgErroAvaliacao.abrirModal();
                }
                
            });
        });
        
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
    private quantidadeDeExamesNaPendenciaRespondida(pendencia: Pendencia): number {
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
     * Método para listar as pendências de uma avaliação
     * 
     * @private
     * @param {number} pagina 
     * 
     * @memberOf AvaliacaoComponent
     */
    private listarPendencias(pagina: number) {
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
                        this.modalMsgErroAvaliacao.mensagem = campoMensagem.mensagem;    
                    });
                }
                else {
                    this.modalMsgErroAvaliacao.mensagem = erro.mensagem.toString();
                }
                this.modalMsgErroAvaliacao.abrirModal();
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


    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.AvaliacaoMedicoComponent];
    }

    /**
     * Metodo que inicializar o modal com as variaveis apenas de visualização de pendencia
     * 
     * @private
     * @param {Pendencia} pendencia 
     * @memberof AvaliacaoMedicoComponent
     */
    private visualizarPendencia(pendencia:Pendencia){
        this.exibirBotaoNovaResposta = true;
        this.abrirModalPendencia(pendencia);
    }

    /**
     * Metodo para abrir o modal de pendencia 
     * 
     * @private
     * @param {Pendencia} pendencia 
     * @memberof AvaliacaoMedicoComponent
     */
    private abrirModalPendencia(pendencia:Pendencia){
        this.pendenciaAtual = pendencia;
        this.respostasAvaliador = [];
        this.pendenciaService.listarHistoricoPendencia(pendencia.id).then(res=>{
        this.habilitarBotaoNovoExameOuEvolucao(pendencia);
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

            // this.respostasAvaliador = res;
        },(error:ErroMensagem)=> {
            this.modalPendencia.hide();
            error.listaCampoMensagem.forEach(obj => {
                this.modalMsg.mensagem = obj.mensagem;
            })
            
            this.modalMsg.abrirModal();
        });

        this.modalPendencia.show();
    }

    private habilitarBotaoNovoExameOuEvolucao(pendencia:Pendencia){
        if (TipoPendencias.EXAME == pendencia.tipoPendencia.id) {
            this.exibirBotaoNovoExame = true
            this.exibirBotaoNovaEvolucao = false;
        } else if (TipoPendencias.EVOLUCAO == pendencia.tipoPendencia.id) {
            this.exibirBotaoNovoExame = false;
            this.exibirBotaoNovaEvolucao = true;
        } else {
            this.exibirBotaoNovoExame = false;
            this.exibirBotaoNovaEvolucao = false;
        }
    }

    /**
     * Fecha o modal da pendencia.
     * 
     * @private
     * @memberof AvaliacaoMedicoComponent
     */
    fecharModalPendencia(){
        this.dialogoPendenciaComponent.fecharCaixaParaResponder();
        this.dialogoForm.get("comentario").setValue("")
        this.modalPendencia.hide();
    }

    /**
     * Abre o modal para responder a pendencia
     * 
     * @private
     * @param {Pendencia} pendencia 
     * @memberof AvaliacaoMedicoComponent
     */
    private abrirResponderPendencia(pendencia:Pendencia){
        this.exibirBotaoNovaResposta = false;
        this.dialogoPendenciaComponent.abrirCaixaParaResponder();
        this.abrirModalPendencia(pendencia);
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
     * Abre a caixa para o médico responder a pendência.
     * 
     * @private
     * @memberof AvaliacaoMedicoComponent
     */
    private abrirCaixaParaResponder(){
        this.exibirBotaoNovaResposta = false;
        this.dialogoPendenciaComponent.abrirCaixaParaResponder();
    }

    /**
     * Metodo para esconde a caixa de dialogo
     * 
     * @private
     * @memberof AvaliacaoMedicoComponent
     */
    cancelarResposta(){
        this.exibirBotaoNovaResposta = true;
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
    private quantidadeDeEvolcoesNaPendenciaRespondida(pendencia: Pendencia): number {
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

    exibirBotoesSeStatusAberta():boolean{
        if(this.pendenciaAtual.statusPendencia.id == StatusPendencias.ABERTA){
            return true
        }
        return false
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
     * Exibe o botão para criar se a pendencia for do tipo exame
     * 
     * @private
     * @param {Pendencia} pendencia 
     * @returns {boolean} 
     * @memberof AvaliacaoMedicoComponent
     */
    private exibeBotaoNovoExame(pendencia:Pendencia):boolean{
        return pendencia.tipoPendencia.id == TipoPendencias.EXAME 
            && pendencia.statusPendencia.id == StatusPendencias.ABERTA
    }

    /**
     * Exibe o botão de nova evolução se o tipo da pendencia for de evolução
     * 
     * @private
     * @param {Pendencia} pendencia 
     * @returns {boolean} 
     * @memberof AvaliacaoMedicoComponent
     */
    private exibeBotaoNovaEvolucao(pendencia:Pendencia):boolean{
        return pendencia.tipoPendencia.id == TipoPendencias.EVOLUCAO 
            && pendencia.statusPendencia.id == StatusPendencias.ABERTA
    }

	public get avaliacao(): Avaliacao {
		return this._avaliacao;
    }

	public get evolucao(): Evolucao {
		return this._evolucao;
	}
    
    

}