import { ErroUtil } from '../../../shared/util/erro.util';
import { PedidoWorkup } from '../workup/pedido.workup';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { TarefaBase } from '../../../shared/dominio/tarefa.base';
import { StatusTarefas } from '../../../shared/enums/status.tarefa';
import { OrdenacaoTarefa } from '../../../shared/enums/ordenacao.tarefas';
import { PerfilUsuario } from '../../../shared/enums/perfil.usuario';
import { TiposTarefa } from '../../../shared/enums/tipos.tarefa';
import { ErroMensagem } from '../../../shared/erromensagem';
import { Paginacao } from '../../../shared/paginacao';
import { TarefaService } from '../../../shared/tarefa.service';
import { TarefaPedidoColeta } from './tarefa.pedido.coleta';
import { PedidoColeta } from './pedido.coleta';
import { Doador } from '../../doador';
import { TiposDoador } from '../../../shared/enums/tipos.doador';
import { Prescricao } from '../workup/prescricao';
import { DateMoment } from '../../../shared/util/date/date.moment';
import { MessageBox } from '../../../shared/modal/message.box';
import { PedidoColetaService } from './pedido.coleta.service';

/**
 * Classe que registra o comportamento do componente visual
 * que lista as tarefas de coleta de workup do doador.
 *
 * @author bruno.sousa
 */
@Component({
    selector: "pedido-coleta-workup",
    moduleId: module.id,
    templateUrl: "./pedido.coleta.component.html",
})
export class PedidoColetaComponent implements OnInit {

    public paginacao: Paginacao;
    public qtdRegistroPorPagina: number = 10;
    // Labels trazidos da internacionalização
    private labels: any;

    protected _atribuidoAMim: boolean = false;

    constructor(private fb: FormBuilder,
        protected tarefaService: TarefaService,
        protected pedidoColetaService: PedidoColetaService,
        private router:Router,
        private autenticacaoService: AutenticacaoService,
        private translate: TranslateService,
        protected messageBox: MessageBox){

        /* this.translate.get('textosGenericos').subscribe(res => {
            this.labels = res;
        }); */

        this.paginacao = new Paginacao('', 0, this.qtdRegistroPorPagina);
        this.paginacao.number = 1;
    }

    ngOnInit() {
        this.listarTarefasPedidoColeta(this.paginacao.number);
    }

    nomeComponente(): string {
        return "PedidoColetaComponent";
    }

    /**
     * Listar as tarefas envolvendo logística a
     * serem realizadas para o doador.
     *
     * @param pagina numero da pagina a ser consultada
     */
    listarTarefasPedidoColeta(pagina: number) {

        this.pedidoColetaService.listarTarefasAgendadas(pagina - 1, this.qtdRegistroPorPagina)
        .then(res => {
            if(res.content) {
                let lista: TarefaBase[] = [];
                res.content.forEach(entity => {
                    let tarefa: TarefaBase = new TarefaBase().jsonToEntity(entity);
                    lista.push(tarefa);
                });
                this.paginacao.content = lista;
                this.paginacao.totalElements = res.totalElements;
                this.paginacao.quantidadeRegistro = this.qtdRegistroPorPagina;
            }
        },
        (error:ErroMensagem)=> {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    /**
     * Detalha a tarefa selecionada.
     * Caso a tarefa ainda não tenha um usuário atribuído, deverá
     * assumir a responsabilidade da tarefa.
     * Caso não esteja disponível (já atribuída a alguém), deverá alertar o
     * usuário da impossibilidade.
     *
     * @param  {TarefaBase} tarefa associada ao item selecionado.
     */
    public detalharTarefa(tarefa: TarefaBase){

        this.tarefaService.atribuirTarefaParaUsuarioLogado(tarefa.id)
            .then(res => {
                this.router.navigateByUrl('/doadores/workup/coleta/' + tarefa.objetoRelacaoEntidade.id + "/agendar?tarefaId=" + tarefa.id);
            })
            .catch((error:ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox, () => {
                    this.listarTarefasPedidoColeta(1);
                });
            });
    }


    /**
     * Muda a paginação para exibir os dados da página informada no parâmetro.
     */
    mudarPagina(event: any) {
        this.listarTarefasPedidoColeta(event.page);
    }

    /**
     * Muda a quantidade de registros exibidos por página e traz a exibição
     * para primeira página.
     */
    mudarQuantidadeRegistroPorPagina(event: any, modal: any) {
        this.listarTarefasPedidoColeta(1);
    }

	public get atribuidoAMim(): boolean {
		return this._atribuidoAMim;
	}

	public set atribuidoAMim(value: boolean) {
        this._atribuidoAMim = value;
    }

    public temPerfilAnalistaWorkupInternacional(): boolean {
        return this.autenticacaoService.temPerfilAnalistaWorkupInternacional();
    }

    public temPerfilAnalistaWorkup(): boolean {
        return this.autenticacaoService.temPerfilAnalistaWorkup();
    }

    public ehMedula(pedidoColeta: PedidoColeta): boolean {
        let doador: Doador = pedidoColeta.pedidoWorkup ? pedidoColeta.pedidoWorkup.solicitacao.match.doador :
            pedidoColeta.solicitacao.match.doador;
        if (doador) {
            return doador.tipoDoador == TiposDoador.NACIONAL || doador.tipoDoador == TiposDoador.INTERNACIONAL;
        }
        return null;
    }

    public ehCordao(pedidoColeta: PedidoColeta): boolean {
        let doador: Doador = pedidoColeta.pedidoWorkup ? pedidoColeta.pedidoWorkup.solicitacao.match.doador :
            pedidoColeta.solicitacao.match.doador;
        if (doador) {
            return doador.tipoDoador == TiposDoador.CORDAO_NACIONAL || doador.tipoDoador == TiposDoador.CORDAO_INTERNACIONAL;
        }
        return null;
    }

    public menorDataColetaPrescricao(prescricao: Prescricao) {
        let dateMoment: DateMoment = DateMoment.getInstance();
        if (dateMoment.isDateBefore(prescricao.dataColeta1, prescricao.dataColeta2)) {
            return prescricao.dataColeta1;
        }
        else {
            return prescricao.dataColeta2
        }
    }

}
