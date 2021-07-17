import {ErroMensagem} from '../../../../shared/erromensagem';
import {Paginacao} from '../../../../shared/paginacao';
import {Router} from '@angular/router';
import {TarefaService} from '../../../../shared/tarefa.service';
import {Component, OnInit} from '@angular/core';
import {MessageBox} from "../../../../shared/modal/message.box";
import {AvaliacaoPedidoColetaService} from "../../../../shared/service/avalaiacao.pedido.coleta.service";
import {ErroUtil} from "../../../../shared/util/erro.util";
import {PermissaoRotaComponente} from "../../../../shared/permissao.rota.componente";


/**
 * Classe que representa o componente para avaliação do pedido de coleta.
 * @author Filipe Paes
 */
@Component({
    selector: "avaliacao-pedido-coleta",
    templateUrl: './avaliacao.pedido.coleta.component.html'
})
export class AvaliacaoPedidoColetaComponent implements OnInit, PermissaoRotaComponente {

    public paginacaoAvaliacoes: Paginacao;
    public qtdRegistroAvaliacoes: number = 10;

    constructor(private router: Router,
                private messageBx: MessageBox,
                private tarefaService: TarefaService,
                private avaliacaoPedidoColetaService: AvaliacaoPedidoColetaService) {

        this.paginacaoAvaliacoes = new Paginacao('', 0, this.qtdRegistroAvaliacoes);
        this.paginacaoAvaliacoes.number = 1;
    }


    ngOnInit(): void {
        this.listarAvaliacoes(this.paginacaoAvaliacoes.number);
    }

    listarAvaliacoes(pagina: any): any {
        this.avaliacaoPedidoColetaService.listarTarefas (pagina - 1, this.qtdRegistroAvaliacoes).then(res => {
            this.paginacaoAvaliacoes.content = res.content;
            this.paginacaoAvaliacoes.totalElements = res.totalElements;
            this.paginacaoAvaliacoes.quantidadeRegistro = this.qtdRegistroAvaliacoes;
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBx);
        });
    }

    carregarAvaliacaoPedidoColeta(item: any){
        this.router.navigateByUrl(`/doadores/workup/coletas/avaliacao/${item.idAvaliacaoResultadoWorkup}?idTarefa=${item.idTarefa}&rmr=${item.rmr}&idDoador=${item.idDoador}`);
    }

    selecionaQuantidadeAvaliacoesPorPagina(event: any) {
        this.listarAvaliacoes(1);
    }

    mudarPaginaAvaliacoes(event: any) {
        this.listarAvaliacoes(event.page);
    }

    nomeComponente(): string {
        return "AvaliacaoPedidoColetaComponent";
    }


}