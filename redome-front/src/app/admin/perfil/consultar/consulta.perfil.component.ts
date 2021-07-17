import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { UsuarioLogado } from '../../../shared/dominio/usuario.logado';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { Paginacao } from '../../../shared/paginacao';
import { CampoMensagem } from '../../../shared/campo.mensagem';
import { ErroMensagem } from '../../../shared/erromensagem';
import { EventEmitterService } from '../../../shared/event.emitter.service';
import { FormatterUtil } from '../../../shared/util/formatter.util';
import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Component, OnInit, AfterViewInit, ViewChild, ViewEncapsulation } from '@angular/core';



import { PerfilService } from '../perfil.service';

/**
 * Classe que representa o componente de consulta dos perfis de acesso do sistema.
 * 
 * @author Thiago moraes
 */
@Component({
    selector: 'perfil',
    templateUrl: './consulta.perfil.component.html'
})
export class ConsultaPerfilComponent implements PermissaoRotaComponente, OnInit {
    
    @ViewChild('mensagemModal') mensagemModal;

    private SAVE_KEY: string        = "buscarPerfis";
    private FORM_LABEL_KEY: string  = "manterPerfil";

    protected usuario: UsuarioLogado = null;
    
    private formLabels;
    consultaForm: FormGroup;
    private mensagemLoading: string;

    mensagem: string;

    paginacao: Paginacao;

    private dadosDaBusca= {
        'querystring': '',
        'quantidadeRegistro': 10,
        'pagina': 1
    }

    quantidadeRegistro: number = 10; 

    private reload: boolean = false;

    /**
     * @param FormBuilder
     * @param PacienteService
     * @param Router
     * @param TranslateService
     * @author Bruno Sousa
     */
    constructor(private _fb: FormBuilder,
        private perfilService: PerfilService, private router: Router,
        private translate: TranslateService, private autenticacao: AutenticacaoService) {

        this.paginacao = new Paginacao('', 0, this.quantidadeRegistro);
        this.paginacao.number = 1;
        this.usuario = autenticacao.usuarioLogado();

    }

    /**
     * 
     * @memberOf ConsultaPerfilComponent
     */
    ngOnInit(): void {
        this.consultaForm = this._fb.group({
            'querystring': [null, null]
        });

        this.carregaBuscaAnterior();
        this.translate.get(this.FORM_LABEL_KEY).subscribe(res => {
            this.formLabels = res;
        });

    }

    /**
     * Faz o carregamento da busca anterior caso exista, e exclui a mesma
     * 
     * 
     * @memberOf ConsultaPerfilComponent
     */
    private carregaBuscaAnterior() {
        let buscarPerfis = localStorage.getItem(this.usuario.username + this.SAVE_KEY);
        
        if (buscarPerfis) {
        
            let busca = JSON.parse(buscarPerfis);
            this.dadosDaBusca.querystring   = busca.querystring;
            this.dadosDaBusca.pagina = parseInt(busca.pagina);
            this.dadosDaBusca.quantidadeRegistro = parseInt(busca.quantidadeRegistro);
            this.quantidadeRegistro  = this.dadosDaBusca.quantidadeRegistro;
            this.excluiBuscaAnterior();    
            this.resetaOformularioParaOsDadosAnteriores();
            this.buscarPerfis(this.dadosDaBusca['pagina'], true);                
        }
    }

    /**
     * Exclui a busca anterior
     * 
     * @private
     * 
     * @memberOf ConsultaPerfilComponent
     */
    private excluiBuscaAnterior() {
        localStorage.removeItem(this.usuario.username+ this.SAVE_KEY);
    }

    /**
     * Método acionado pelo botão de consulta
     * 
     * @memberOf ConsultaPerfilComponent
     */
    onSubmit(pagina: number) {
        this.mensagem = '';
        this.dadosDaBusca['querystring']    = this.consultaForm.get('querystring').value;
        this.paginacao.content       = [];
        this.paginacao.totalElements = 0;
        return this.buscarPerfis(pagina);
    }

    /**
     * Método para fechar o modal
     * 
     * @param {*} modal 
     * 
     * @memberOf ConsultaPerfilComponent
     */
    fecharModal() {
        this.mensagemModal.hide();
    }

    /**
     * Método acionado quando muda a página
     * 
     * @param {*} event 
     * @param {*} modal 
     * 
     * @memberOf ConsultaPerfilComponent
     */
    mudarPagina(event: any) {
        this.resetaOformularioParaOsDadosAnteriores();
        this.buscarPerfis(event.page);
    }

    /**
     * Método acionado quando é alterado a quantidade de registros por página
     * 
     * @param {*} event 
     * 
     * @memberOf ConsultaPerfilComponent
     */
    selecionaQuantidadePorPagina(event: any) {
        this.dadosDaBusca['quantidadeRegistro'] = this.quantidadeRegistro;
        this.resetaOformularioParaOsDadosAnteriores();
        this.buscarPerfis(1);
    }

    /**
     * Método para restaurar os valores pesquisados quando é trocado a página ou a quantidade de registros
     * pois o usuário pode ter alterado os termos da busca
     * 
     * @private
     * 
     * @memberOf ConsultaPerfilComponent
     */
    private resetaOformularioParaOsDadosAnteriores() {
        this.consultaForm.get('querystring').setValue(this.dadosDaBusca['querystring'])
    }

    /**
     * Salva a busca atual
     * 
     * @private
     * 
     * @memberOf ConsultaPerfilComponent
     */
    private salvaBusca() {
        localStorage.setItem(this.usuario.username + this.SAVE_KEY,  JSON.stringify(this.dadosDaBusca));
    }

    
    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.PerfilComponent];
    }


    /**
     * Método para recuperar o conjuto de perfis de acesso disponíveis.
     * 
     * @private
     * @param {number} pagina - informações sobre paginação do conjunto que será retornado.
     * @param {*} buscaAnterior - se true, força utilizar os parâmetros da última consulta realizada.
     * 
     * @memberOf ConsultaPerfilComponent
     */
    private buscarPerfis(pagina: number, buscaAnterior?: boolean) {
        
        if (!buscaAnterior) {
            buscaAnterior = false;
        }
        if (this.paginacao.number == pagina && this.paginacao.content.length != 0 && this.dadosDaBusca['quantidadeRegistro'] == this.paginacao.quantidadeRegistro) {
            return;
        }
        
        EventEmitterService.get("onSubmitLoading").emit(
        this.perfilService.listarPerfis()
            .then((res: any) => {
                this.paginacao.content = res.content;
                this.paginacao.totalElements = res.totalElements;
                this.paginacao.quantidadeRegistro = this.dadosDaBusca['quantidadeRegistro'];
                this.paginacao.number = pagina;
                this.salvaBusca();
            })
            .catch((erro: ErroMensagem)  => {
                if (erro.listaCampoMensagem.length != 0) {
                    erro.listaCampoMensagem.forEach((campoMensagem: CampoMensagem) => {
                        this.mensagem += campoMensagem.mensagem;
                    });
                }
                else {
                    this.mensagem = erro.mensagem.toString();
                }
                this.mensagemModal.show();
            }));
            
    }

    /**
     * Carrega a tela de editar um perfil de acesso.
     * 
     * @param {number} id - identificação do perfil de acesso.
     * 
     * @memberOf ConsultaPerfilComponent
     */
    carregaTelaEditarPerfil(id: number) {
        this.salvaBusca();
        this.router.navigateByUrl("/perfil/"+id+"/editar")
    }
    
    /*
    * Remove o  perfil selecionado.
    * 
    * @param {number} id - identificação do perfil de acesso
    * 
     * @memberOf ConsultaPerfilComponent
    */
    excluirPerfil(id: number) {}


    /**
     * Formata a quantidade de usuarios neste perfil.
     * 
     * @param {string} qt 
     * @returns {string} 
     * @memberof ConsultaComponent
     */
    formataQtUsuario(qt: Number): string {

        if(qt == 0) return this.formLabels.consultaForm.tabela.nenhum;
        else if(qt == 1) return '1 '+this.formLabels.consultaForm.tabela.usuario;
        else return qt+' '+this.formLabels.consultaForm.tabela.usuarios;
    }



}