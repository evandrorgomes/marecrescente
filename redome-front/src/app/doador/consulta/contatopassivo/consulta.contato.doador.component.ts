import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from '@angular/forms';
import { FormGroup } from '@angular/forms/src/model';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { TentativaContatoDoadorService } from "app/doador/solicitacao/fase2/tentativa.contato.doador.service";
import { AutenticacaoService } from 'app/shared/autenticacao/autenticacao.service';
import { StatusDoador } from 'app/shared/dominio/status.doador';
import { UsuarioLogado } from 'app/shared/dominio/usuario.logado';
import { FluxoPedidoContato } from "app/shared/enums/fluxo.pedido.contato";
import { TiposSolicitacao } from "app/shared/enums/tipos.solicitacao";
import { ModalConfirmation } from "app/shared/modal/factory/modal.factory";
import { MessageBox } from 'app/shared/modal/message.box';
import { PedidoContatoService } from 'app/shared/service/pedido.contato.service';
import { PedidoEnriquecimentoService } from 'app/shared/service/pedido.enriquecimento.service';
import { TarefaService } from 'app/shared/tarefa.service';
import { ErroUtil } from 'app/shared/util/erro.util';
import { ErroMensagem } from '../../../shared/erromensagem';
import { Paginacao } from '../../../shared/paginacao';
import { DoadorService } from '../../doador.service';
import { ConsultaDoadorNacionalVo } from './../../../shared/vo/consulta.doador.nacional.vo';

/**
 * Classe que representa o componente de consulta de contato passivo
 * @author Ergomes
 */
@Component({
    selector: "consulta-contato-doador",
    moduleId: module.id,
    templateUrl: "./consulta.contato.doador.component.html"
})
export class ConsultaContatoDoadorComponent implements OnInit {

    public paginacaoDoadores: Paginacao;
    public qtdRegistroDoadores: number = 10;
    protected usuario: UsuarioLogado = null;
    public _doadorVo: ConsultaDoadorNacionalVo;

    public consultaForm:FormGroup;
    public maskCPF: Array<string | RegExp>
    @ViewChild("modalErro")
    public modalErro;

    constructor(private fb: FormBuilder,
        private doadorService: DoadorService, private tentativaContatoService: TentativaContatoDoadorService,
        private tarefaService: TarefaService, private pedidoContatoService: PedidoContatoService,
        private pedidoEnriquecimentoService: PedidoEnriquecimentoService,
        private router:Router, private translate:TranslateService, private autenticacao: AutenticacaoService,
        protected messageBox: MessageBox){
        this.paginacaoDoadores = new Paginacao('', 0, this.qtdRegistroDoadores);
        this.paginacaoDoadores.number = 1;
        this.usuario = autenticacao.usuarioLogado();
        this.maskCPF = [
            /[0-9]/,/[0-9]/,/[0-9]/,
             '.', /[0-9]/,/[0-9]/,/[0-9]/,
              '.', /[0-9]/,/[0-9]/,/[0-9]/,
               '-', /[0-9]/,/[0-9]/
            ];
    }
    /**
     * Método para construir o formulario de consulta de doador.
     */
    buildForm() {

        this.consultaForm = this.fb.group({
            'dmr': [null, null],
            'nome': [null, null],
            'cpf': [null, null],
        });

        return this.consultaForm;

    }

    buscar(){
        this.listarContatoDoadores(this.paginacaoDoadores.number);
    }
    /**
     * Método de inicialização do componente.
     */
    ngOnInit() {
        this.buildForm();
        // this.listarDoadores(this.paginacaoDoadores.number);
    }

    /**
     * Retorna o nome do componente
     * @returns string
     */
    nomeComponente(): string {
        return "ContatoPassivoComponent";
    }

    /**
     * Método para listar as avaliações pendentes
     *
     * @param pagina numero da pagina a ser consultada
     *
     * @memberOf ConsultaComponent
     */
    private listarContatoDoadores(pagina: number) {

        this.doadorService.listarContatoDoadores(pagina - 1, this.qtdRegistroDoadores, this.consultaForm.get("dmr").value, this.consultaForm.get("nome").value, this.consultaForm.get("cpf").value)
        .then(res=>{
            this.paginacaoDoadores.content = res.content;
            this.paginacaoDoadores.totalElements = res.totalElements;
            this.paginacaoDoadores.quantidadeRegistro = this.qtdRegistroDoadores;
            if(this.paginacaoDoadores.totalElements == 0){
                this.translate.get("doadores.consulta.nenhumResultado").subscribe(res => {
                    this.modalErro.mensagem = res;
                    this.modalErro.abrirModal();
                });
            }
        },
        (error:ErroMensagem)=> {
            this.exibirMensagemErro(error);
        });
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalErro.mensagem = obj.mensagem;
        })
        this.modalErro.abrirModal();
    }

    /**
     * Método acionado quando muda a página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    mudarPaginaDoadores(event: any) {
        this.listarContatoDoadores(event.page);
    }

    /**
       * Método acionado quando é alterado a quantidade de registros por página
       *
       * @param {*} event
       * @param {*} modal
       *
       * @memberOf ConsultaComponent
       */
    selecionaQuantidadeDoadoresPorPagina(event: any, modal: any) {
        this.listarContatoDoadores(1);
    }

    public irParaProximaEtapa(doadorVo:ConsultaDoadorNacionalVo){

        this._doadorVo = new ConsultaDoadorNacionalVo().jsonToEntity(doadorVo);

        if (FluxoPedidoContato.FLUXO_NORMAL.id == this._doadorVo.tipoFluxo) {
            this.atribuirTarefa();

        }else if ( FluxoPedidoContato.FLUXO_ATUALIZACAO.id == this._doadorVo.tipoFluxo ) {
            this.router.navigateByUrl('/doadores/' + this._doadorVo.idDoador + '/atualizarContatoPassivo');

        }else if ( FluxoPedidoContato.FLUXO_CRIACAO.id == this._doadorVo.tipoFluxo ) {
            this.doadorService.criarPedidoContatoPassivo(this._doadorVo.idDoador)
            .then(res=>{
                this._doadorVo.idTentativa = res.idTentativa;
                this._doadorVo.tipoContato = res.tipoContato;
                this.direcionarContato();
            },
            (error:ErroMensagem)=> {
                this.exibirMensagemErro(error);
            });

        }else if ( FluxoPedidoContato.FLUXO_ENRIQUECIMENTO.id === this._doadorVo.tipoFluxo ) {
            console.log(" Enriquecimento: " + this._doadorVo.idEnriquecimento);
            if(this._doadorVo.idStatusDoador == StatusDoador.INATIVO_PERMANENTE ||
                this._doadorVo.idStatusDoador == StatusDoador.INATIVO_PERMANENTE){
                    this.messageBox.alert('Doador inativo e com enriquecimento em aberto.')
                    .withTarget(this)
                    .show();
            }else{
                this.abrirModalConfirmacaoEnriquecimentoDoador();
            }

        }else {
            this.direcionarContato();

        }
    }

    abrirModalConfirmacaoEnriquecimentoDoador(): void{
        this.translate.get("doadores.mensagem.confirmarEnriquecimento").subscribe(mensagem =>{
            let modalConfirmacao: ModalConfirmation = this.messageBox.confirmation(mensagem);
            modalConfirmacao.yesOption = () => {
                this.pedidoEnriquecimentoService.fecharPedidoDeEnriquecimentoNaConsultaDoadorNacional(
                    this._doadorVo.idEnriquecimento,
                    this._doadorVo.idDoador).then(res => {
                        this._doadorVo = new ConsultaDoadorNacionalVo().jsonToEntity(res);
                        this.atribuirTarefa();
                },
                (error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                });
           };
            modalConfirmacao.show();
        });
    }

    private atribuirTarefa(): void {
        this.tarefaService.atribuirTarefaParaUsuarioLogado(this._doadorVo.idTarefa).then(
        res => {
            this.direcionarContato();
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox, () => {
                this.listarContatoDoadores(1);
            });
        });
    }

    public direcionarContato(){
        let params: string = '';
        let url: string = this._doadorVo.tipoContato === TiposSolicitacao.FASE_2 ? '/doadores/contato/fase2' : '/doadores/contato/fase3';

        if(this._doadorVo.idTarefa){
            params =  '&idTarefa=' + this._doadorVo.idTarefa;
        }
        this.router.navigateByUrl(url + '?idTentativa=' + this._doadorVo.idTentativa + '&idStatusDoador=' + this._doadorVo.idStatusDoador + params);
    }

    /**
    * Método para voltar para home
    *
    * @memberof ConferenciaComponent
    */
    public irParaHome() {
        this.router.navigateByUrl('/home');
    }

    public getClassByStatusDoador(idStatusDoador:number){
        return idStatusDoador == StatusDoador.ATIVO || idStatusDoador == StatusDoador.ATIVO_RESSALVA ? 'label label-success' : 'label label-danger'
    }
}
