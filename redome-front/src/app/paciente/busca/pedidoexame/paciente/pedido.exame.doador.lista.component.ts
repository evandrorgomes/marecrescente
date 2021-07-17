import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {Recursos} from 'app/shared/enums/recursos';
import {PedidoExameDoadorInternacionalVo} from 'app/shared/vo/pedido.exame.doador.internacional.vo';
import {SolicitacaoService} from '../../../../doador/solicitacao/solicitacao.service';
import {PedidoExameService} from '../../../../laboratorio/pedido.exame.service';
import {AutenticacaoService} from '../../../../shared/autenticacao/autenticacao.service';
import {TiposDoador} from '../../../../shared/enums/tipos.doador';
import {TiposSolicitacao} from '../../../../shared/enums/tipos.solicitacao';
import {TiposTarefa} from '../../../../shared/enums/tipos.tarefa';
import {ErroMensagem} from '../../../../shared/erromensagem';
import {MessageBox} from '../../../../shared/modal/message.box';
import {Paginacao} from '../../../../shared/paginacao';
import {TarefaService} from '../../../../shared/tarefa.service';
import {ArrayUtil} from '../../../../shared/util/array.util';
import {ErroUtil} from '../../../../shared/util/erro.util';
import {PedidoExameModal} from '../../modalcustom/modal/pedido.exame.modal';
import {CancelarPedidoExameFase3Component} from '../doadorInternacional/cancelar/cancelar.pedido.exame.fase3.component';

@Component({
    selector: 'pedido-exame-doador-lista',
    templateUrl: './pedido.exame.doador.lista.component.html'
})
export class PedidoExameDoadorListaComponent implements OnInit {

    private _quantidadeRegistrosSolicitacao = 10;
    public quantidadeRegistrosSolicitacaoNacionais = 10;

    //  Form com os filtros da combo de fases e ordenação.
    filtroListasForm: FormGroup;

    private _pagina = 1;
    @Input("busca")
    private _idBusca: number;
    private _tipoDoador: number = 1;
    private _tarefaId:number;

    paginacaoSolicitacoesDoadores: Paginacao;
    paginacaoSolicitacoesDoadoresNacionais: Paginacao;

    private _permissaoCancelarFase2Internacional: boolean;
    private _permissaoCancelarFase3Internacional: boolean;
    private _permissaoCancelarFase3Nacional: boolean;
    private _permissaoCancelarFase2Nacional: boolean;
    private _permissaoCadastrarResultadoFase3Internacional: boolean;
    private _permissaoCadastrarResultadoFase2Internacional: boolean;
    private _permissaoEditarFase2Internacional: boolean;
    private _temPerfilAnalistaBusca: boolean;
    private _temPerfilMedico: boolean;

    // Exibir ou não todos os pedidos, independente se estão em andamento ou finalizados.
    public exibirHistorico: boolean = false;

    // Item do Tipo de exame selecionado para o filtro.
    public idTipoSolicitacaoSelecionado: number;

    constructor(private pedidoExameService: PedidoExameService, private messageBox: MessageBox, private _fb: FormBuilder,
        private router:Router, private tarefaService:TarefaService, private translate: TranslateService,
        private autenticacaoService: AutenticacaoService, private solicitacaoService: SolicitacaoService ) {
        this.paginacaoSolicitacoesDoadores = new Paginacao('', 0, this._quantidadeRegistrosSolicitacao);
        this.paginacaoSolicitacoesDoadores.number = 1;

        this.paginacaoSolicitacoesDoadoresNacionais = new Paginacao('', 0, this._quantidadeRegistrosSolicitacao);
        this.paginacaoSolicitacoesDoadoresNacionais.number = 1;

        this._permissaoCancelarFase2Internacional = this.autenticacaoService.temRecurso(Recursos.CANCELAR_FASE_2_INTERNACIONAL);
        this._permissaoCancelarFase3Internacional = this.autenticacaoService.temRecurso(Recursos.CANCELAR_FASE_3_INTERNACIONAL);
        this._permissaoCancelarFase2Nacional = this.autenticacaoService.temRecurso(Recursos.CANCELAR_FASE_2);
        this._permissaoCancelarFase3Nacional = this.autenticacaoService.temRecurso(Recursos.CANCELAR_FASE_3);
        this._permissaoCadastrarResultadoFase2Internacional = this.autenticacaoService.temRecurso(Recursos.CADASTRAR_RESULTADO_PEDIDO_FASE_2_INTERNACIONAL);
        this._permissaoEditarFase2Internacional = this.autenticacaoService.temRecurso(Recursos.EDITAR_FASE2_INTERNACIONAL);
        this._permissaoCadastrarResultadoFase3Internacional = this.autenticacaoService.temRecurso(Recursos.CADASTRAR_RESULTADO_PEDIDO_CT);

        this._temPerfilAnalistaBusca = this.autenticacaoService.temPerfilAnalistaBusca();
        this._temPerfilMedico = this.autenticacaoService.temPerfilMedico();

        this.idTipoSolicitacaoSelecionado = 0;
    }

    ngOnInit(): void {
        this.filtroListasForm = this._fb.group({
            'itemTipoExame': null
        });
        this.idTipoSolicitacaoSelecionado = 0;
        //this.listaFasesTipoSolicitacao();
    }

    /**
     * Lista Solicitacoes para executar resultado.
     *
     * @param pagina página que deve ser exibida na paginação.
     * @returns
     */
    public listarSolicitacoesDePedidosDeExameInternacional(pagina: any): void{
        const tipoSolicitacao = this.listaFasesTipoSolicitacao().find(tipo => tipo.id == this.idTipoSolicitacaoSelecionado);
        this.pedidoExameService.listarSolicitacoesDePedidosDeExameInternacional(
            this._idBusca, this.exibirHistorico, tipoSolicitacao.tipoSolicitacaoInternacional, pagina - 1, this._quantidadeRegistrosSolicitacao)

            .then(res => {
                this.paginacaoSolicitacoesDoadores.content = res.content;
                this.paginacaoSolicitacoesDoadores.totalElements = res.totalElements;
                this.paginacaoSolicitacoesDoadores.quantidadeRegistro = this._quantidadeRegistrosSolicitacao;
                this.paginacaoSolicitacoesDoadores.number = pagina;
            },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
    }


    /**
     * @description Lista as Solicitacoes de pedido de exame para doadores nacionais.
     *
     * @author Pizão
     * @private
     * @param {*} pagina
     */
    public listarSolicitacoesDePedidosDeExameNacional(pagina: any): void{
        const tipoSolicitacao = this.listaFasesTipoSolicitacao().find(tipo => tipo.id == this.idTipoSolicitacaoSelecionado);
        this.pedidoExameService.listarSolicitacoesDePedidosDeExameNacional(null, null,
            this._idBusca, this.exibirHistorico, tipoSolicitacao.tipoSolicitacaoNacional, pagina - 1, this.quantidadeRegistrosSolicitacaoNacionais)

            .then(res => {
                this.paginacaoSolicitacoesDoadoresNacionais.content = res.content;
                this.paginacaoSolicitacoesDoadoresNacionais.totalElements = res.totalElements;
                this.paginacaoSolicitacoesDoadoresNacionais.quantidadeRegistro = this.quantidadeRegistrosSolicitacaoNacionais;
                this.paginacaoSolicitacoesDoadoresNacionais.number = pagina;
            },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
    }

    public editar(idPedido:number, idTarefa:number, idProcesso:number):void{
        this._tarefaId = idTarefa;

        this.tarefaService.atribuirTarefaParaUsuarioLogado(idTarefa).then(res=>{
            let json:any = {};
            json.idPedido = idPedido;
            json.acao = PedidoExameModal.ACAO_MODAL_PEDIDO_EXAME_EDITAR;

            let modal = this.messageBox.dynamic(json, PedidoExameModal);
            modal.target = this;
            modal.withCloseOption(this.resultadoModal)
            modal.show();
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    /**
     * Cancela o pedido de exame, se ainda for possível.
     *
     * @param idPedidoExame ID do pedido de exame.
     */
    public cancelar(pedido: PedidoExameDoadorInternacionalVo): void{
        this._tarefaId = pedido.idTarefa;
        if (TiposTarefa.CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL.id == pedido.tipoTarefa ||
            TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.id == pedido.tipoTarefa ||
            TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL.id == pedido.tipoTarefa) {
            this.tarefaService.atribuirTarefaParaUsuarioLogado(pedido.idTarefa).then(res=>{
               let target = {
                   'atualizaMatch': () => {
                       this.atualizarListaBuscas();
                   }
               };

                let modal = this.messageBox.dynamic({'idPedidoExame': pedido.idPedidoExame,
                                    'idTipoTarefa':pedido.tipoTarefa,
                                    'idSolicitacao':pedido.idSolicitacao},
                                    CancelarPedidoExameFase3Component);
                modal.target = target;
                modal.closeOption = this.resultadoModal;
                modal.show();
            },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
        else if (TiposTarefa.CADASTRAR_RESULTADO_CT.id == pedido.tipoTarefa ||
            TiposTarefa.CONTACTAR_FASE_3.id == pedido.tipoTarefa) {
            this.solicitacaoService.cancelarFase3Nacional(pedido.idSolicitacao)
                .then(res => {
                    this.listarSolicitacoesDePedidosDeExameNacional(1);
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
        }
        else if (TiposTarefa.CADASTRAR_RESULTADO_EXAME.id == pedido.tipoTarefa ||
            TiposTarefa.CONTACTAR_FASE_2.id == pedido.tipoTarefa) {
            this.solicitacaoService.cancelarFase2Nacional(pedido.idSolicitacao)
                .then(res => {
                    this.listarSolicitacoesDePedidosDeExameNacional(1);
                }, (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
        }

    }

    /**
     * Abre a tela de cadastro do resultado de acordo com o pedido de exame clicado.
     * Podendo ser: Pedido de Exame Fase 2, CT ou IDM.
     *
     * @param tarefa tarefa relacionada ao pedido que se deseja cadastrar o resultado.
     */
    public cadastrar(pedido: PedidoExameDoadorInternacionalVo):void{
        if(pedido.tipoTarefa == TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.id){
            this.router.navigateByUrl('/doadoresinternacionais/pedidoexame/' +
                pedido.idSolicitacao + '/resultadoIDM?idTarefa=' + pedido.idTarefa + '&idDoador=' + pedido.idDoador);
        } else if (TiposSolicitacao.FASE_3_INTERNACIONAL == pedido.idTipoSolicitacao) {
            this.router.navigateByUrl('/doadoresinternacionais/pedidoexame/' +
                pedido.idSolicitacao + '/resultadoCT?idTarefa=' + pedido.idTarefa + '&idDoador=' + pedido.idDoador);
        }
        else {
            this.tarefaService.atribuirTarefaParaUsuarioLogado(pedido.idTarefa);
            this.router.navigateByUrl('/doadoresinternacionais/pedidoexame/' +
                pedido.idSolicitacao + '/resultadoExame?idTarefa=' + pedido.idTarefa + '&idDoador=' + pedido.idDoador);
        }
    }

    /**
     * Método executado no fechamento do modal de recebimento.
     * @param target
     */
    resultadoModal(target:any):void{
        target.tarefaService.cancelarTarefa(target._tarefaId).then(res=>{
            target.listarSolicitacoesDePedidosDeExameInternacional(1);
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, target.messageBox);
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
        this.listarSolicitacoesDePedidosDeExameInternacional(event.page);
    }

    mudarPaginaNacionais(event: any) {
        this.listarSolicitacoesDePedidosDeExameNacional(event.page);
    }

    /**
     * Método acionado quando é alterado a quantidade de registros por página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    selecionaQuantidadeRegistros(event: any) {
        this.listarSolicitacoesDePedidosDeExameInternacional(1);
    }

    selecionaQuantidadeRegistrosNacionais(event: any) {
        this.listarSolicitacoesDePedidosDeExameNacional(1);
    }

    public get quantidadeRegistrosSolicitacao(){
        return this._quantidadeRegistrosSolicitacao;
    }

    public set quantidadeRegistrosSolicitacao(valor: number){
        this._quantidadeRegistrosSolicitacao = valor;
    }

    /**
     * Getter idBusca
     * @return {number}
     */
	public get idBusca(): number {
		return this._idBusca;
	}

    /**
     * Setter idBusca
     * @param {number} value
     */
	public set idBusca(value: number) {
		this._idBusca = value;
	}

    /**
     * Getter tipoDoador
     * @return {number }
     */
	public get tipoDoador(): number  {
		return this._tipoDoador;
	}

    /**
     * Setter tipoDoador
     * @param {number } value
     */
	public set tipoDoador(value: number ) {
		this._tipoDoador = value;
    }

    /**
     * Retorna a lista de lócus formatados com vírgula entre os valores.
     *
     * @param listaLocus lista de lócus.
     * @return texto com lócus formatados.
     *
     */
    obterListaLocusFormatado(pedido: any): string{
        if(TiposTarefa.CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL.id == pedido.tipoTarefa){
            return "CT";
        }
        else if(TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.id == pedido.tipoTarefa){
            return "IDM";
        }
        return pedido.exame;
    }

    /**
     * Verifica se existe histórico para os doadores.
     * @return TRUE se NÃO houver.
     */
    listaVazia(): boolean {
        return this.paginacaoSolicitacoesDoadores && ArrayUtil.isEmpty(this.paginacaoSolicitacoesDoadores.content);
    }

    listaNacionaisVazia(): boolean {
        return this.paginacaoSolicitacoesDoadoresNacionais && ArrayUtil.isEmpty(this.paginacaoSolicitacoesDoadoresNacionais.content);
    }

    ehEditavel(pedido: PedidoExameDoadorInternacionalVo): boolean {
        return TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL.id == pedido.tipoTarefa;
    }

    ehMedula(tipoDoador: any): boolean {
        return tipoDoador == TiposDoador.NACIONAL || tipoDoador == TiposDoador.INTERNACIONAL;
    }

    ehCordao(tipoDoador: any): boolean {
        return tipoDoador == TiposDoador.CORDAO_NACIONAL || tipoDoador == TiposDoador.CORDAO_INTERNACIONAL;
    }

    public temPermissaoCancelar(pedido: PedidoExameDoadorInternacionalVo): boolean {

        if (TiposTarefa.CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL.id == pedido.tipoTarefa ||
            TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.id == pedido.tipoTarefa){
            return this._permissaoCancelarFase3Internacional;
        }
        else if (TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL.id == pedido.tipoTarefa) {
            return this._permissaoCancelarFase2Internacional;
        }
        else if (TiposTarefa.CADASTRAR_RESULTADO_CT.id == pedido.tipoTarefa ||
            TiposTarefa.CONTACTAR_FASE_3.id == pedido.tipoTarefa) {
            return this._permissaoCancelarFase3Nacional;
        }
        else if (TiposTarefa.CADASTRAR_RESULTADO_EXAME.id == pedido.tipoTarefa ||
            TiposTarefa.CONTACTAR_FASE_2.id == pedido.tipoTarefa) {
            return this._permissaoCancelarFase2Nacional;
        }
        return false;
    }

    public temPermissaoCadastrarResultado(pedido: PedidoExameDoadorInternacionalVo): boolean {
        if (TiposTarefa.CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL.id == pedido.tipoTarefa ||
            TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.id == pedido.tipoTarefa){
            return this._permissaoCadastrarResultadoFase3Internacional;
        }
        else if (TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL.id == pedido.tipoTarefa) {
            return this._permissaoCadastrarResultadoFase2Internacional;
        }
        return false;
    }

    public temPermissaoEditarFase2Internacional(pedido: PedidoExameDoadorInternacionalVo): boolean {
        if (TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL.id == pedido.tipoTarefa) {
            return this._permissaoEditarFase2Internacional;
        }
        return false;
    }

    public temPerfilAnalistaBusca(): boolean {
        return this._temPerfilAnalistaBusca;
    }

    public temPerfilMedico() : boolean{
        return this._temPerfilMedico;
    }

    /**
     * @description Atualiza a lista de pedidos nacionais exibindo ou não
     * o histórico completo do paciente (todos os doadores que já tiveram
     * algum pedido realizado para o mesmo).
     * @author Pizão
     * @param {boolean} exibirPedidosJaRealizados Se TRUE, exibir histórico
     * completo, se FALSE, exibir somente os que estão em andamento.
     */
    public atualizarPedidosExame(exibirPedidosJaRealizados: boolean): void{
        this.exibirHistorico = exibirPedidosJaRealizados;
        this.listarSolicitacoesDePedidosDeExameInternacional(1);
        this.listarSolicitacoesDePedidosDeExameNacional(1);
    }

       /**
     * @description Força a atualização a lista de buscas,
     * reaplicando os filtros no resultado.
     * @author ergomes
     */
    public atualizarListaBuscas(): void{
        this.listarSolicitacoesDePedidosDeExameInternacional(1);
        this.listarSolicitacoesDePedidosDeExameNacional(1);
    }

        /**
     * @description Lista todos os tipos de exames
     * @author ergomes
     * @public
     */
    public listaFasesTipoSolicitacao(): any[] {

        const todos = {
            id: 0,
            tipoSolicitacaoNacional: 0,
            tipoSolicitacaoInternacional: 0,
            descricao: ''
        };
        const fase2 = {
            id: 1,
            tipoSolicitacaoNacional: TiposSolicitacao.FASE_2,
            tipoSolicitacaoInternacional: TiposSolicitacao.FASE_2_INTERNACIONAL,
            descricao: ''
        };
        const fase3 = {
            id: 2,
            tipoSolicitacaoNacional: TiposSolicitacao.FASE_3,
            tipoSolicitacaoInternacional: TiposSolicitacao.FASE_3_INTERNACIONAL,
            descricao: ''
        };
        const idm = {
            id: 3,
            tipoSolicitacaoNacional: -1,
            tipoSolicitacaoInternacional: -1,
            descricao: ''
        };

        this.translate.get("pedidoexamedoador.todos").subscribe(texto =>{
            todos.descricao = texto;
        });

        this.translate.get("textosGenericos.fases.fase2").subscribe(texto =>{
            fase2.descricao = texto;
        });

        this.translate.get("textosGenericos.fases.fase3").subscribe(texto =>{
            fase3.descricao = texto;
        });

        this.translate.get("textosGenericos.idm").subscribe(texto =>{
            idm.descricao = texto;
        });

        return [todos, fase2, fase3, idm];
    }
}
