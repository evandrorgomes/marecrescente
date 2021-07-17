import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Doador } from 'app/doador/doador';
import { AutenticacaoService } from 'app/shared/autenticacao/autenticacao.service';
import { UsuarioLogado } from 'app/shared/dominio/usuario.logado';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { Paginacao } from 'app/shared/paginacao';
import { DoadorService } from '../doador.service';

/**
 * Classe que representa o componente de consulta de doador
 * @author Ergomes
 */
@Component({
    selector: 'consulta-doador',
    moduleId: module.id,
    templateUrl: './consulta.doador.nacional.component.html'
})
export class ConsultaDoadorNacionalComponent implements OnInit {

    public paginacaoDoadores: Paginacao;
    public qtdRegistroDoadores: number = 10;
    public consultaForm: FormGroup;
    protected usuario: UsuarioLogado = null;

    @ViewChild('modalErro')
    public modalErro;

    constructor(private fb: FormBuilder,
        private doadorService: DoadorService,
        private router: Router, private translate: TranslateService, 
        private autenticacao: AutenticacaoService,
        protected messageBox: MessageBox) {
            this.paginacaoDoadores = new Paginacao('', 0, this.qtdRegistroDoadores);
            this.paginacaoDoadores.number = 1;
            this.usuario = autenticacao.usuarioLogado();
        }

   /**
     * Método para construir o formulario de consulta de doador.
     */
    buildForm() {
        this.consultaForm = this.fb.group({
            'dmr': [null, null],
            'nome': [null, null],
        });
        return this.consultaForm;
    }

    /**
     * Método de inicialização do componente.
     */
    ngOnInit(): void {
        this.buildForm();
    }

    /**
     * Método para listar os doadores nacionais.
     * 
     * @param pagina numero da pagina a ser consultada
     * 
     * @memberOf ConsultaComponent
     */
    private listarDoadoresNacionais(pagina: number) {

        this.doadorService.listarDoadoresNacionais(
            pagina - 1, this.qtdRegistroDoadores, this.consultaForm.get('dmr').value, this.consultaForm.get('nome').value)
        .then(res => {
            this.paginacaoDoadores.content = res.content;
            this.paginacaoDoadores.totalElements = res.totalElements;
            this.paginacaoDoadores.quantidadeRegistro = this.qtdRegistroDoadores;
            if (this.paginacaoDoadores.totalElements == 0) {
                this.translate.get('doadores.consulta.nenhumResultado')
                .subscribe( res => {
                        this.modalErro.mensagem = res;
                        this.modalErro.abrirModal();
                });
            }
        },
        (error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }

    /**
     * Método para direcionar a seleção para tela de detalhamento.
     * 
     * @param doador - doador selecionado na lista.
     */
    public irParaDetalhamento(doador: Doador) {
        this.router.navigateByUrl("/doadores/atualizarDoadorNacional/visualizar?idDoador="+doador.id);
    }

    /**
     * Método para exibir mensagem de erro no modal.
     * 
     * @param error - mensagem de erro.
     */
    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalErro.mensagem = obj.mensagem;
        } );
        this.modalErro.abrirModal();
    }

    /**
     * Método acionado na mudança de página.
     * 
     * @param {*} event
     * @param {*} modal
     * 
     * @memberOf ConsultaComponent
     */
    mudarPaginaDoadores(event: any) {
        this.listarDoadoresNacionais(event.page);
    }

    /**
       * Método acionado na alteração da quantidade de registros por página.
       * 
       * @param {*} event
       * @param {*} modal
       * 
       * @memberOf ConsultaComponent
       */
    selecionaQuantidadeDoadoresPorPagina(event: any, modal: any) {
        this.listarDoadoresNacionais(1);
    }

    /**
     * Método é acionado pelo butão de busca de doadores.
     */
    buscar() {
        this.listarDoadoresNacionais(this.paginacaoDoadores.number);
    }

    /**
     * Retorna o nome do componente
     * @returns string
     */
    nomeComponente(): string {
        return "ConsultaDoadorNacionalComponent";
    }

}