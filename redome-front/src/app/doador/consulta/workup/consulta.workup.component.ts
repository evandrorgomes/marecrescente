import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TiposFormulario } from 'app/shared/enums/tipo.formulario';
import { TiposTarefa } from 'app/shared/enums/tipos.tarefa';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { ComponenteRecurso } from "../../../shared/enums/componente.recurso";
import { ErroMensagem } from '../../../shared/erromensagem';
import { MessageBox } from '../../../shared/modal/message.box';
import { PacienteUtil } from "../../../shared/paciente.util";
import { Paginacao } from '../../../shared/paginacao';
import { TarefaService } from '../../../shared/tarefa.service';
import { PedidoWorkup } from './pedido.workup';
import { WorkupService } from './workup.service';


/**
 * Classe que representa o componente de consulta de workup
 * @author Fillipe Queiroz
 */
@Component({
    selector: "pedido-workup",
    moduleId: module.id,
    templateUrl: "./consulta.workup.component.html"
})
export class ConsultaWorkupComponent implements OnInit {

    public paginacaoTarefasWorkup: Paginacao;
    public qtdRegistroTarefasWorkup: number = 10;


    public paginacaoSolicitacoesWorkup: Paginacao;
    public qtdRegistroWorkupAtribuido: number = 10;

    private _ehDoadorInternacional: boolean = false;
    private _rmr: number;

    /**
     * Construtor
     * @param serviceDominio - serviço de dominio para buscar os dominios
     * @param correioService - serviço de cep para consultar os endereços
     * @author Rafael Pizão
     */
    constructor(private fb: FormBuilder,
        private autenticacaoService: AutenticacaoService
        , private tarefaService: TarefaService
        , private router:Router
        , private activatedRouter:ActivatedRoute
        , protected workupService: WorkupService
        , private messageBox: MessageBox){
        this.paginacaoTarefasWorkup = new Paginacao(null, 0, this.qtdRegistroTarefasWorkup);
        this.paginacaoTarefasWorkup.number = 1;
        this.paginacaoSolicitacoesWorkup = new Paginacao(null, 0, this.qtdRegistroWorkupAtribuido);
        this.paginacaoSolicitacoesWorkup.number = 1;
    }
    /**
     * Método de inicialização do componente.
     */
    ngOnInit() {
        this.listarTarefasWorkupAtribuidos(this.paginacaoSolicitacoesWorkup.number);
        this.listarSolicitacoesWorkup(this.paginacaoSolicitacoesWorkup.number);

    }

    /**
     * Retorna o nome do componente
     * @returns string
     */
    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaWorkupComponent];
    }

    /**
     * Lista os workups atribuídos (em andamento) para o usuário logado.
     *
     * @param pagina numero da pagina a ser consultada
     *
     * @memberOf ConsultaComponent
     */
    protected listarTarefasWorkupAtribuidos(pagina: number) {
        this.workupService.listarTarefasWorkup(pagina - 1, this.qtdRegistroWorkupAtribuido)
            .then(res=>{
                   this.paginacaoTarefasWorkup.content = res.content;
                   this.paginacaoTarefasWorkup.totalElements = res.totalElements;
                   this.paginacaoTarefasWorkup.quantidadeRegistro = this.qtdRegistroWorkupAtribuido;
            },
            (error:ErroMensagem)=> {
                this.exibirMensagemErro(error);
            });
    }

    /**
     * Lista os todos os workups pendentes (em aberto).
     *
     * @param pagina numero da pagina a ser consultada
     *
     * @memberOf ConsultaComponent
     */
    protected listarSolicitacoesWorkup(pagina: number) {
        this.workupService.listarSolicitacoesWorkup(pagina - 1, this.qtdRegistroTarefasWorkup)
            .then(res=>{
                   this.paginacaoSolicitacoesWorkup.content = res.content;
                   this.paginacaoSolicitacoesWorkup.totalElements = res.totalElements;
                   this.paginacaoSolicitacoesWorkup.quantidadeRegistro = this.qtdRegistroTarefasWorkup;
            },
            (error:ErroMensagem)=> {
                this.exibirMensagemErro(error);
            });
    }

    protected exibirMensagemErro(error: ErroMensagem) {
        if(error.listaCampoMensagem){
            this.messageBox.alert(error.listaCampoMensagem[0].mensagem).show();
        }
    }

    /**
     * Método acionado quando muda a página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    mudarPaginaTarefasWorkupAtribuidas(event: any) {
        this.listarTarefasWorkupAtribuidos(event.page);
    }

    /**
       * Método acionado quando é alterado a quantidade de registros por página
       *
       * @param {*} event
       *
       * @memberOf ConsultaComponent
       */
    selecionaQuantidadeTarefasWorkupPorPagina(event: any) {
        this.listarTarefasWorkupAtribuidos(1);
    }

    /**
     * Método acionado quando muda a página
     *
     * @param {*} event
     *
     * @memberOf ConsultaComponent
     */
    mudarPaginaSolicitacoesWorkup(event: any) {
        this.listarSolicitacoesWorkup(event.page);
    }

    /**
       * Método acionado quando é alterado a quantidade de registros por página
       *
       * @param {*} event
       *
       * @memberOf ConsultaComponent
       */
    selecionaQuantidadeSolicitacoesWorkupsPorPagina(event: any) {
        this.listarSolicitacoesWorkup(1);
    }

    /**
     * Tela que ira para a tela de pedido de workup.
     */
    abrirDetalheWorkup(pedidoWorkup:PedidoWorkup){
        if(pedidoWorkup){
            this.router.navigateByUrl('/doadores/workup/agendar?idDoador=' + pedidoWorkup.solicitacao.doador.id+'&idPedido='+pedidoWorkup.id);
        }
    }

    irParaAcaoSelecionada(tarefa: any){
        if(tarefa.idTipoTarefa){
            switch (tarefa.idTipoTarefa){
                case TiposTarefa.CADASTRAR_FORMULARIO_DOADOR.id:
                    this.router.navigateByUrl('/doadores/workup/formulario/visualiza?idPedidoWorkup='+tarefa.idRelacaoEntidade+'&idTipoFormulario='+TiposFormulario.RECEPTIVIDADE_WORKUP+'&idPrescricao='+tarefa.idPrescricao);
                    break;
                case TiposTarefa.DEFINIR_CENTRO_COLETA.id:
                    this.router.navigateByUrl('/doadores/workup/centrocoleta/definir?idPedidoWorkup='+tarefa.idRelacaoEntidade+'&idPrescricao='+tarefa.idPrescricao);
                    break;
                case TiposTarefa.INFORMAR_PLANO_WORKUP_INTERNACIONAL.id:
                    this.router.navigateByUrl('/doadores/workup/centrotransplante/informar?idPedidoWorkup='+tarefa.idRelacaoEntidade+'&idPrescricao='+tarefa.idPrescricao+'&idTipoTarefa='+tarefa.idTipoTarefa+'&idCentroTransplante='+tarefa.idCentroTransplante);
                    break;
                case TiposTarefa.INFORMAR_RESULTADO_WORKUP_INTERNACIONAL.id:
                    this.router.navigateByUrl(`/doadoresinternacionais/workup/resultado/cadastrar?idPedidoWorkup=${tarefa.idRelacaoEntidade}&idDoador=${tarefa.idDoador}`);
                    break;
                case TiposTarefa.INFORMAR_LOGISTICA_DOADOR_WORKUP.id:
                    this.router.navigateByUrl(`/doadores/workup/logistica/detalhar?idPedidoLogistica=${tarefa.idRelacaoEntidade}&idDoador=${tarefa.idDoador}`);
                    break;
                case TiposTarefa.INFORMAR_EXAME_ADICIONAL_WORKUP_NACIONAL.id:
                    this.router.navigateByUrl(`/doadores/workup/exameadicional/cadastro?idPedidoAdicional=${tarefa.idRelacaoEntidade}&idDoador=${tarefa.idDoador}`);
                    break;
                case TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_INTERNACIONAL.id:
                    this.router.navigateByUrl(`/doadores/material/logistica/internacional?idPedidoLogistica=${tarefa.idRelacaoEntidade}&idDoador=${tarefa.idDoador}`);
                    break;
                case TiposTarefa.INFORMAR_LOGISTICA_DOADOR_COLETA.id:
                  this.router.navigateByUrl(`/doadores/logistica/${tarefa.idRelacaoEntidade}/doador?idDoador=${tarefa.idDoador}`);
                  break;
            }
        }

    }


    /*public getEstiloDoStatusPedidoWorkup(pedidoWorkup:PedidoWorkup):string{
        if(pedidoWorkup){
            switch (pedidoWorkup.statusPedidoWorkup.id) {
                case StatusPedidosWorkup.INICIAL:
                    return "workup-status inicial";
                case StatusPedidosWorkup.AGENDADO:
                    return "workup-status agendado";
                case StatusPedidosWorkup.AGUARDANDO_CT:
                    return "workup-status aguardandoct";
                case StatusPedidosWorkup.CANCELADO:
                    return "workup-status cancelado";
                case StatusPedidosWorkup.EM_ANALISE:
                    return "workup-status emanalise";
                case StatusPedidosWorkup.REALIZADO:
                    return "workup-status realizado";
                case StatusPedidosWorkup.CONFIRMADO_CT:
                    return "workup-status emanalise";
            }
        }
        return "";
    }*/

    formatarData(data: string): string {
        if (!data) {
            return '';
        }
        return PacienteUtil.converterStringEmData(data).toLocaleDateString();
    }

    /**
    * Getter rmr
    * @return {number }
    */
   public get rmr(): number {
    return this._rmr;
 }

}
