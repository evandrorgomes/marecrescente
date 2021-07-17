import { DateMoment } from '../../../../shared/util/date/date.moment';
import { Observable } from 'rxjs';
import { StatusPedidoColeta } from '../../../../shared/dominio/status.pedido.coleta';
import { CentroTransplante } from '../../../../shared/dominio/centro.transplante';
import { Paginacao } from '../../../../shared/paginacao';
import { OnInit, Component, ViewChild } from "@angular/core";
import { FormBuilder } from '@angular/forms';
import { TarefaService } from '../../../../shared/tarefa.service';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { TiposTarefa } from '../../../../shared/enums/tipos.tarefa';
import { PerfilUsuario } from '../../../../shared/enums/perfil.usuario';
import { Processo } from '../../../../shared/dominio/processo';
import { TipoTarefa } from '../../../../shared/dominio/tipo.tarefa';
import { Perfil } from '../../../../shared/dominio/perfil';
import { UsuarioLogado } from '../../../../shared/dominio/usuario.logado';
import { PedidoColeta } from '../pedido.coleta';
import { DataUtil } from '../../../../shared/util/data.util';
import { PedidoWorkup } from '../../workup/pedido.workup';
import { Solicitacao } from '../../../solicitacao/solicitacao';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { Perfis } from '../../../../shared/enums/perfis';
import { AtributoOrdenacao } from '../../../../shared/util/atributo.ordenacao';
import { AtributoOrdenacaoDTO } from '../../../../shared/util/atributo.ordenacao.dto';
import { TarefaPedidoWorkup } from '../tarefa.pedido.workup';
import { BsModalRef, BsModalService } from 'ngx-bootstrap';
import { AlertaModalComponente } from '../../../../shared/modal/factory/alerta.modal.component';
import { DoadorNacional } from '../../../doador.nacional';
import {AutenticacaoService} from "../../../../shared/autenticacao/autenticacao.service";


/**
 * Classe que registra o comportamento do componente visual
 * que lista as tarefas de coleta de workup do doador.
 *
 * @author bruno.sousa
 */
@Component({
    selector: "cancelar-agendamento-pedido-coleta",
    moduleId: module.id,
    templateUrl: "./cancelar.agendamento.component.html"
})
export class CancelarAgendamentoPedidoColetaComponent implements OnInit {

    private modalErro: BsModalRef;

    public paginacao: Paginacao;
    public qtdRegistroPorPagina: number = 10;
    // Labels trazidos da internacionalização
    private labels: any;
    public static TAREFA_ABERTA: number=1;

    private _atribuidoAMim: boolean = false;

    //@ViewChild('modalErro')
    //private modalErro: MensagemModalComponente;

    private _tipoTarefa: number;

    constructor(private fb: FormBuilder,
        private tarefaService: TarefaService,
        private router:Router, private activatedRouter: ActivatedRoute,
        private translate: TranslateService, private modalService: BsModalService){

        this.paginacao = new Paginacao('', 0, this.qtdRegistroPorPagina);
        this.paginacao.number = 1;
    }

    ngOnInit() {

        this.activatedRouter.queryParams.subscribe(params => {
            if (params && params["tipo"]) {
                this._tipoTarefa = Number(params["tipo"]);
                this.listarTarefasNotificacaoCancelamentoPedidoColeta(this.paginacao.number);
            }
            else {
                this.translate.get("mensagem.erro.requisicao").subscribe(res => {
                    this.modalErro = this.modalService.show(AlertaModalComponente, {mensagem: res});
                });
            }
        })
    }

    nomeComponente(): string {
        return "CancelarAgendamentoPedidoColetaComponent";
    }

    /**
     * Listar as tarefas envolvendo logística a
     * serem realizadas para o doador.
     *
     * @param pagina numero da pagina a ser consultada
     */
    public listarTarefasNotificacaoCancelamentoPedidoColeta(pagina: number) {
        let atributos:AtributoOrdenacao[] = [];
        let atributoOrdenacao:AtributoOrdenacao = new AtributoOrdenacao();
        atributoOrdenacao.nomeAtributo = "objetoRelacaoEntidade.pedidoWorkup.dataPrevistaDisponibilidadeDoador";
        atributoOrdenacao.asc = true;
        atributos.push(atributoOrdenacao)

        let atributosOrdenacaoDTO:AtributoOrdenacaoDTO = new AtributoOrdenacaoDTO();
        atributosOrdenacaoDTO.atributos = atributos;

        this.tarefaService.listarTarefasPaginadas(
            this._tipoTarefa, PerfilUsuario.ANALISTA_WORKUP, null,
            pagina - 1, this.qtdRegistroPorPagina, this._atribuidoAMim, atributosOrdenacaoDTO, CancelarAgendamentoPedidoColetaComponent.TAREFA_ABERTA)
        .then(res => {
            if(res.content) {
                let lista: TarefaPedidoWorkup[] = [];
                res.content.forEach(entity => {
                    let tarefaPedidoWorkup: TarefaPedidoWorkup = new TarefaPedidoWorkup();
                    tarefaPedidoWorkup.id = entity.id;
                    tarefaPedidoWorkup.processo = new Processo(entity.processo.id);
                    tarefaPedidoWorkup.tipoTarefa = new TipoTarefa(entity.tipoTarefa.id);
                    tarefaPedidoWorkup.status = entity.status;
                    tarefaPedidoWorkup.aging = entity.aging;
                    tarefaPedidoWorkup.perfilResponsavel = new Perfil(entity.perfilResponsavel.id);
                    if (entity.usuarioResponsavel) {
                        let usuarioResponsavel: UsuarioLogado = new UsuarioLogado();
                        usuarioResponsavel.username = entity.usuarioResponsavel.username;
                        usuarioResponsavel.nome = entity.usuarioResponsavel.nome
                        tarefaPedidoWorkup.usuarioResponsavel = usuarioResponsavel;
                    }
                    let pedidoWorkup: PedidoWorkup = new PedidoWorkup();
                    pedidoWorkup.id = entity.objetoRelacaoEntidade.id;
                    pedidoWorkup.dataPrevistaDisponibilidadeDoador = DataUtil.dataStringToDate(entity.objetoRelacaoEntidade.dataPrevistaDisponibilidadeDoador)

                    if (this._tipoTarefa == TiposTarefa.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA.id) {
                        let centroColeta: CentroTransplante = new CentroTransplante();
                        if(entity.objetoRelacaoEntidade.centroColeta){
                            centroColeta.id = entity.objetoRelacaoEntidade.centroColeta.id;
                            centroColeta.nome = entity.objetoRelacaoEntidade.centroColeta.nome;
                        }
                        pedidoWorkup.centroColeta = centroColeta;
                    }
                    else if (this._tipoTarefa == TiposTarefa.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR.id) {


                        let solicitacao: Solicitacao = new Solicitacao();
                        solicitacao.id = entity.objetoRelacaoEntidade.solicitacao.id;
                        let doador: DoadorNacional = new DoadorNacional();
                        doador.id = entity.objetoRelacaoEntidade.solicitacao.doador.id;
                        doador.dmr = entity.objetoRelacaoEntidade.solicitacao.doador.dmr;
                        doador.nome = entity.objetoRelacaoEntidade.solicitacao.doador.nome;

                        solicitacao.doador = doador;
                        pedidoWorkup.solicitacao = solicitacao;
                    }

                    tarefaPedidoWorkup.pedidoWorkup = pedidoWorkup;

                    lista.push(tarefaPedidoWorkup)
                });
                this.paginacao.content = lista;
                this.paginacao.totalElements = res.totalElements;
                this.paginacao.quantidadeRegistro = this.qtdRegistroPorPagina;
            }
        },
        (error:ErroMensagem)=> {
            this.exibirMensagemErro(error);
        });
    }

    private exibirMensagemErro(error: ErroMensagem) {
        let mennsagemErro: string = "";
        error.listaCampoMensagem.forEach(obj => {
            mennsagemErro = obj.mensagem;
            //this.modalErro.mensagem = obj.mensagem;
        })
        this.modalErro = this.modalService.show(AlertaModalComponente, {mensagem: mennsagemErro});
        //this.modalErro.abrirModal();
    }

    /**
     * Detalha a tarefa selecionada.
     * Caso a tarefa ainda não tenha um usuário atribuído, deverá
     * assumir a responsabilidade da tarefa.
     * Caso não esteja disponível (já atribuída a alguém), deverá alertar o
     * usuário da impossibilidade.
     *
     * @param  {TarefaPedidoLogistica} tarefa de logística associada ao item selecionado.
     */
    public detalharTarefa(tarefa: TarefaPedidoWorkup){

        this.tarefaService.atribuirTarefaParaUsuarioLogado(tarefa.id)
            .then(res => {
                this.router.navigateByUrl('/doadores/workup/coleta/' + tarefa.pedidoWorkup.id + "/cancelaragendamento?processoId=" + tarefa.processo.id + "&tarefaId=" + tarefa.id + "&tipo=" + tarefa.tipoTarefa.id);
            })
            .catch((error:ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
    }


    /**
     * Muda a paginação para exibir os dados da página informada no parâmetro.
     */
    mudarPagina(event: any) {
        this.listarTarefasNotificacaoCancelamentoPedidoColeta(event.page);
    }

    /**
     * Muda a quantidade de registros exibidos por página e traz a exibição
     * para primeira página.
     */
    mudarQuantidadeRegistroPorPagina(event: any, modal: any) {
        this.listarTarefasNotificacaoCancelamentoPedidoColeta(1);
    }

	public get atribuidoAMim(): boolean {
		return this._atribuidoAMim;
	}

	public set atribuidoAMim(value: boolean) {
        this._atribuidoAMim = value;
    }

    public ehNotificacaoCancelamentoPedidoColetaDoador(): boolean {
        return this._tipoTarefa == TiposTarefa.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR.id;
    }

    public ehNotificacaoCancelamentoPedidoColetaCentroColeta(): boolean {
        return this._tipoTarefa == TiposTarefa.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA.id;
    }


}
