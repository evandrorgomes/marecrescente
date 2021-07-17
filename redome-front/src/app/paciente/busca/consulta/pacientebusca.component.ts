import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { UsuarioLogado } from 'app/shared/dominio/usuario.logado';
import { UsuarioService } from '../../../admin/usuario/usuario.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { ErroMensagem } from '../../../shared/erromensagem';
import { MessageBox } from '../../../shared/modal/message.box';
import { Paginacao } from '../../../shared/paginacao';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { UrlParametro } from '../../../shared/url.parametro';
import { ErroUtil } from '../../../shared/util/erro.util';
import { BuscaService } from '../busca.service';
import { BuscaDTO } from './busca.dto';

/**
 * Classe que representa o componente de busca.
 * @author Filipe Paes
 */
@Component({
    selector: "pacientebusca",
    templateUrl: './pacientebusca.component.html'
})
export class PacienteBuscaComponent implements PermissaoRotaComponente, OnInit {
    
    paginacao: Paginacao;
    quantidadeRegistro: number = 10; 


    // Form com os filtros da combo de analista e ordenação.
    filtroListasForm: FormGroup;
    
    // Filtros possíveis nesta tela.
    // Login do analista de busca selecionado na combo.
    public loginAnalistaSelecionado: string;
    // RMR para ser utilizado por filtro.
    public rmrSelecionado: string;
    // Nome para ser utilizado por filtro.
    public nomeSelecionado: string;
    // Item do checklist selecionado para o filtro.
    public idTipoBuscaCheckListSelecionado: number;

    // Lista de todos os analistas de busca do Redome.
    // Utilizado para filtrar, caso seja necessário visualizar a fila de pacientes de outro analista.
    public listaAnalistasBusca: UsuarioLogado[];

    // Lista de tipos de busca check list a serem exibidos como filtro na combo. 
    public listaTiposBuscaCheckList: any[];

    /**
     * Cria uma instancia de PacienteBuscaComponent.
     * @param {BuscaService} buscaService 
     * @param {Router} router 
     * @memberof PacienteBuscaComponent
     */
    constructor(private _fb: FormBuilder,
                private buscaService:BuscaService,
                private router:Router,
                private activatedRouter:ActivatedRoute,
                private translate: TranslateService,
                private messageBox: MessageBox,
                private usuarioService: UsuarioService,
                private autenticacaoService: AutenticacaoService) {
        this.paginacao = new Paginacao('', 0, this.quantidadeRegistro);
        this.paginacao.number = 1;

        this.idTipoBuscaCheckListSelecionado = 0;
    }

    /**
     * Inicializa a classe com os dados buscando no serviço REST
     * 
     * @memberof PacienteBuscaComponent
     */
    ngOnInit(): void {
        this.filtroListasForm = this._fb.group({
            'analistaBusca': null,
            'itemCheckList': null
        });

        this.loginAnalistaSelecionado = 
            this.autenticacaoService.usuarioLogado().username;

        this.atualizarListaBuscas();
        this.listarAnalistasBuscaRedome();
        this.listarTiposBuscaChecklist();
    }

    /**
     * Método para listar as notificações
     * 
     * @private
     * @param {number} pagina 
     * @memberof PacienteBuscaComponent
     */
    private listarBuscas(pagina: number) {
        this.activatedRouter.queryParamMap.subscribe(queryParam => {
            let filtros: UrlParametro[] = [];

            filtros.push(new UrlParametro("loginAnalistaBusca", this.loginAnalistaSelecionado));
            
            if(this.idTipoBuscaCheckListSelecionado){
                filtros.push(new UrlParametro("idTipoBuscaCheckList", this.idTipoBuscaCheckListSelecionado));
            }

            if(this.rmrSelecionado){
                filtros.push(new UrlParametro("rmr", this.rmrSelecionado));
            }
            if(this.nomeSelecionado){
                filtros.push(new UrlParametro("nome", this.nomeSelecionado));
            }

            this.buscaService.listarBuscas(
                pagina - 1, this.quantidadeRegistro, filtros).then(res => {
                let buscasDTO: BuscaDTO[] = res.content;
                this.paginacao.content = buscasDTO;
                this.paginacao.totalElements = res.totalElements;
                this.paginacao.quantidadeRegistro = this.quantidadeRegistro;
                this.paginacao.number = pagina;
            },
            (error: ErroMensagem)=> {
                this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
            });
        });
    }
     /**
     * Carrega a tela de detalhe e salva a busca atual para ser utilizada quando voltar
     * 
     * @param {number} rmr 
     * 
     * @memberOf PacienteBuscaComponent
     */
    carregaTelaDetalhe(rmr: number) {
        this.router.navigateByUrl("/pacientes/" + rmr + "/matchs");
    }

    /**
     * Retorna o nome do componente
     * 
     * @returns {string} 
     * @memberof PacienteBuscaComponent
     */
    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.PacienteBuscaComponent];
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
        this.listarBuscas(event.page);
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
        this.listarBuscas(1);
    }

    /**
     * Método para voltar para home
     * 
     * @memberof PacienteBuscaComponent
     */
    public irParaHome() {
        this.router.navigateByUrl('/home');
    }

    /**
     * @description Lista todos os analistas do busca do Redome
     * para popular o filtro de analistas, onde é possível selecionar e ver a lista
     * de pacientes, independente do analista (ligação realizada através da relação
     * do analista com centro avaliador).
     * @author Pizão
     * @private
     */
    private listarAnalistasBuscaRedome(): void{
        this.usuarioService.listarAnalistasBusca().then(retorno => {
            this.listaAnalistasBusca = [];
            if (retorno) {
                retorno.forEach(usuario => {
                    this.listaAnalistasBusca.push(new UsuarioLogado().jsonToEntity(usuario));
                });
            }
        }, (error: ErroMensagem) => {
            this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
        });
    }

    /**
     * @description Força a atualização a lista de buscas, 
     * reaplicando os filtros no resultado.
     * @author Pizão
     */

     public atualizarListaBuscas(): void{
        this.listarBuscas(this.paginacao.number);
    }

    /**
     * @description Lista todos os tipos de checklist de busca
     * que podem estar envolvidos os itens de busca selecionados.
     * @author Pizão
     * @private
     */
    private listarTiposBuscaChecklist(): void{
        this.buscaService.listarTipoBuscaChecklist().then(listaTipos => {
            this.listaTiposBuscaCheckList = listaTipos;
        }, (error: ErroMensagem) => {
            this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
        });
    }

};