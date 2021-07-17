import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { UrlParametro } from 'app/shared/url.parametro';
import { CentroTransplanteService } from '../../../admin/centrotransplante/centrotransplante.service';
import { Tarefa } from '../../../paciente/consulta/pendencia/tarefa';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { UsuarioLogado } from '../../../shared/dominio/usuario.logado';
import { PerfilUsuario } from '../../../shared/enums/perfil.usuario';
import { TiposTarefa } from '../../../shared/enums/tipos.tarefa';
import { ErroMensagem } from '../../../shared/erromensagem';
import { Paginacao } from '../../../shared/paginacao';
import { TarefaService } from '../../../shared/tarefa.service';
import { SelectCentrosComponent } from 'app/shared/component/selectcentros/select.centros.component';


@Component({
    selector: "recebimento-coleta",
    templateUrl: './recebimento.coleta.component.html'
})
export class RecebimentoColetaComponent implements OnInit {
    private TAREFA_ABERTA: any = 1;

    public paginacaoAvaliacoes: Paginacao;
    public qtdRegistroRecebimento: number = 10;

    public consultaForm: FormGroup;

    protected usuario: UsuarioLogado = null;
    protected translate: TranslateService;

    public _centroSelecionado = {
        'id': null,
        'tipo': null
    }

    @ViewChild("modalErro")
    public modalErro;

    @ViewChild("selectCentros")
    private selectCentros: SelectCentrosComponent;

    ngOnInit(): void {}

    constructor(private fb: FormBuilder,
        translate: TranslateService,
        private tarefaService: TarefaService,
        private router: Router,
        private autenticacao: AutenticacaoService,
        private centroTransplanteService: CentroTransplanteService) {
        this.paginacaoAvaliacoes = new Paginacao('', 0, this.qtdRegistroRecebimento);
        this.paginacaoAvaliacoes.number = 1;
        this.usuario = autenticacao.usuarioLogado();
    }

    ngAfterViewInit(): void {
        setTimeout(() => {
            this._centroSelecionado = this.selectCentros.value;
            this.listarRecebimentos(1);
        })
    }

    listarRecebimentos(pagina: any): any {
        this.tarefaService.listarTarefasPaginadas(
            TiposTarefa.INFORMAR_RECEBIMENTO_COLETA.id, PerfilUsuario.CADASTRADOR_RECEBIMENTO_COLETA, null,
            pagina - 1, this.qtdRegistroRecebimento, true,null,null, this._centroSelecionado.id)
            .then(res => {
                this.paginacaoAvaliacoes.content = res.content;
                this.paginacaoAvaliacoes.totalElements = res.totalElements;
                this.paginacaoAvaliacoes.quantidadeRegistro = this.qtdRegistroRecebimento;
            },
        (error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalErro.mensagem = obj.mensagem;
        })
        this.modalErro.abrirModal();
    }

    carregarRecebimentoColeta(idRecebimentoColeta:number, tarefa:Tarefa){
        this.tarefaService.atribuirTarefaParaUsuarioLogado(tarefa.id)
        .then(res => {
            this.router.navigateByUrl('/doadores/workup/recebimentocoleta/' +  idRecebimentoColeta + '?tarefaId=' + tarefa.id);
        })
        .catch((error:ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }
     /**
     * Método acionado quando é alterado a quantidade de registros por página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    selecionaQuantidadeAvaliacoesPorPagina(event: any, modal: any) {
        this.listarRecebimentos(1);
    }
 /**
     * Retorna o nome do componente
     * @returns string
     */
    nomeComponente(): string {
        return "RecebimentoColetaComponent";
    }

     /**
     * Método acionado quando muda a página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    mudarPaginaRecebimento(event: any) {
        this.listarRecebimentos(event.page);
    }

    mudouCentro(value: any) {
        this._centroSelecionado = value;
        this.listarRecebimentos(1);
    }

}
