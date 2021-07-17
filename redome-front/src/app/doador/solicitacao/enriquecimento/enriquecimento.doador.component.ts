import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ErroUtil } from 'app/shared/util/erro.util';
import { StatusDoador } from '../../../shared/dominio/status.doador';
import { ErroMensagem } from '../../../shared/erromensagem';
import { EventEmitterService } from '../../../shared/event.emitter.service';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { MensagemModalComponente } from '../../../shared/modal/mensagem.modal.component';
import { TarefaService } from '../../../shared/tarefa.service';
import { DoadorEmailContatoComponent } from '../../cadastro/contato/email/doador.contato.email.component';
import { DoadorContatoEnderecoComponent } from '../../cadastro/contato/endereco/doador.contato.endereco.component';
import { DoadorContatoTelefoneComponent } from '../../cadastro/contato/telefone/doador.contato.telefone.component';
import { DoadorIdentificacaoComponent } from '../../cadastro/identificacao/doador.identificacao.component';
import { HeaderDoadorComponent } from '../../consulta/header/header.doador.component';
import { ContatoTelefonicoDoador } from '../../contato.telefonico.doador';
import { DoadorNacional } from '../../doador.nacional';
import { DoadorService } from '../../doador.service';
import { EmailContatoDoador } from '../../email.contato.doador';
import { EnderecoContatoDoador } from '../../endereco.contato.doador';
import { InativacaoModalComponent } from '../../inativacao/inativacao.modal.component';
import { MessageBox } from './../../../shared/modal/message.box';
import { PedidoEnriquecimentoService } from 'app/shared/service/pedido.enriquecimento.service';
import { TarefaBase } from 'app/shared/dominio/tarefa.base';

/**
 * Classe que representa o componente de busca.
 * @author Filipe Paes
 */
@Component({
    selector: "enriquecimento",
    templateUrl: './enriquecimento.doador.component.html',
    styleUrls: ['./../../doador.css']
})
export class EnriquecimentoDoadorComponent  implements OnInit {

    //private _tarefaId: number;
    private _tarefa: TarefaBase;
    //private _doador: DoadorNacional;
    //private pedidoEnriquecimentoId: number;
    exibirBotaoInativar: boolean;

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;

    @ViewChild('doadorIdentificacaoComponent')
    private doadoridentificacao: DoadorIdentificacaoComponent;

    @ViewChild('doadorContatoTelefoneComponent')
    private doadorContatoTelefoneComponent: DoadorContatoTelefoneComponent;

    @ViewChild('doadorContatoEnderecoComponent')
    private doadorContatoEnderecoComponent: DoadorContatoEnderecoComponent;

    @ViewChild('doadorEmailContatoComponent')
    private doadorEmailContatoComponent: DoadorEmailContatoComponent;

    @ViewChild(InativacaoModalComponent)
    private modalInativacao: InativacaoModalComponent;


    constructor(private router: Router, private tarefaService: TarefaService,
        private pedidoEnriquecimentoService: PedidoEnriquecimentoService,
        private messageBox: MessageBox) {
    }

    /**
     * Inicializa a classe com os dados buscando no serviço REST
     *
     * @memberof PacienteBuscaComponent
     */
    ngOnInit(): void {
        this.carregarProximoPedidoEnriquecimento();
        this.headerDoador.clearCache();

    }

    private carregarProximoPedidoEnriquecimento(): void{
        this.pedidoEnriquecimentoService.obterPrimeiroPedidoEnriquecimentoDaFila().then(res => {

            this._tarefa = new TarefaBase().jsonToEntity(res);
            let doador: DoadorNacional = this._tarefa.objetoRelacaoEntidade.solicitacao.doador;

            Promise.resolve(this.headerDoador).then(() => {
                this.headerDoador.popularCabecalhoIdentificacaoPorDoador(doador.id);
            });

            let status: StatusDoador = doador.statusDoador;
            this.exibirBotaoInativar =
                status.id == StatusDoador.ATIVO ||
                    status.id == StatusDoador.ATIVO_RESSALVA;

            this.doadoridentificacao.preencherFormulario(doador);
            this.doadorContatoTelefoneComponent.preencherDados(doador.id, doador.contatosTelefonicos);
            this.doadorContatoEnderecoComponent.preencherDados(doador.id, doador.enderecosContato);
            this.doadorEmailContatoComponent.preencherDados(doador.id, doador.emailsContato);
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox,  () => {
                this.router.navigate(["home"]);
            });
        });
    }

    /**
     * Nome do componente atual
     *
     * @returns {string}
     * @memberof ConferenciaComponent
     */
    nomeComponente(): string {
        return "EnriquecerDoadorComponent";
    }

    cancelarTarefaEnriquecimento() {
        this.tarefaService.removerAtribuicaoTarefa(this._tarefa.id).then(res => {
            this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    inativarDoador(): void{
        this.modalInativacao.doador = this._tarefa.objetoRelacaoEntidade.solicitacao.doador;
        this.modalInativacao.abrirModal();
        /* this.modalInativacao.addEvento(
            InativacaoModalComponent.FINALIZAR_EVENTO, this.finalizarInativar); */

        EventEmitterService.get(InativacaoModalComponent.FINALIZAR_EVENTO).subscribe((doador: DoadorNacional): void => {
            //this._doador = doador;
            this.exibirBotaoInativar = false;
            this.headerDoador.popularCabecalhoIdentificacaoNoCache(this._tarefa.objetoRelacaoEntidade.solicitacao.doador.id);

            this.salvar();
        });
    }

    salvar(): void {
        this.pedidoEnriquecimentoService.fecharPedidoDeEnriquecimento( this._tarefa.objetoRelacaoEntidade.id )
            .then(res => {
                this.carregarProximoPedidoEnriquecimento();
            }, (error:ErroMensagem)=>{
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
    }

};
