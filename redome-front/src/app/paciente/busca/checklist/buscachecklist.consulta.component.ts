import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ChecklistService } from 'app/shared/service/cheklist.service';
import { UsuarioService } from '../../../admin/usuario/usuario.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { BuscaChecklistDTO } from '../../../shared/component/listamatch/classes/busca.checklist.dto';
import { UsuarioLogado } from '../../../shared/dominio/usuario.logado';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { ErroMensagem } from '../../../shared/erromensagem';
import { MessageBox } from '../../../shared/modal/message.box';
import { Paginacao } from '../../../shared/paginacao';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { UrlParametro } from '../../../shared/url.parametro';
import { ErroUtil } from '../../../shared/util/erro.util';
import { BuscaService } from '../busca.service';

/**
 * Classe que representa o componente de busca.
 * @author Filipe Paes
 */
@Component({
    selector: "buscachecklist-consulta",
    templateUrl: './buscachecklist.consulta.component.html'
})
export class BuscaChecklistConsultaComponent implements PermissaoRotaComponente, OnInit {

    paginacao: Paginacao;
    quantidadeRegistro: number = 10;


    // Form com os filtros da combo de analista e ordenação.
    filtroListasForm: FormGroup;

    public loginAnalistaSelecionado: string;

    public idTipoBuscaCheckListSelecionado: number;

    public listaAnalistasBusca: UsuarioLogado[];

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
                private autenticacaoService: AutenticacaoService,
                private checklistService: ChecklistService) {
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

            filtros.push(new UrlParametro("loginAnalista", this.loginAnalistaSelecionado));

            if(this.idTipoBuscaCheckListSelecionado){
                filtros.push(new UrlParametro("idTipoChecklist", this.idTipoBuscaCheckListSelecionado));
            }


            this.checklistService.listarChecklist(filtros,pagina - 1, this.quantidadeRegistro)
            .then(res => {
                let checklistsDTO: BuscaChecklistDTO[] = res.content;
                this.paginacao.content = checklistsDTO;
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
    carregaTelaDetalhe(rmr: number, identificacao: number, matchativo: boolean, fase: string) {
        this.router.navigateByUrl(`/pacientes/${rmr}/matchs?identificacaoDoador=${identificacao}&matchativo=${matchativo}&fase=${fase}`);
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
