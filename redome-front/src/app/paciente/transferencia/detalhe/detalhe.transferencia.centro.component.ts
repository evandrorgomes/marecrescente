import { ModalRecusarTransferenciaCentroComponent } from './modal/modal.recusar.transferencia.centro.component';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { PedidoTransferenciaCentro } from 'app/shared/classes/pedido.transferencia.centro';
import { PedidoTransferenciaCentroDTO } from 'app/shared/dto/pedido.transferencia.centro.dto';
import { MessageBox } from 'app/shared/modal/message.box';
import { PermissaoRotaComponente } from 'app/shared/permissao.rota.componente';
import { PedidoTransferenciaCentroService } from 'app/shared/service/pedido.transferencia.centro.service';
import { TarefaService } from 'app/shared/tarefa.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { ErroUtil } from '../../../shared/util/erro.util';
import { Evolucao } from '../../cadastro/evolucao/evolucao';
import { HeaderPacienteComponent } from '../../consulta/identificacao/header.paciente.component';
import { PacienteService } from '../../paciente.service';
import { ErroMensagem } from './../../../shared/erromensagem';
import { RouterUtil } from './../../../shared/util/router.util';
import { Recursos } from 'app/shared/enums/recursos';

/**
 * Component responsavel transferência centro do paciente pelo médico do centro avaliador
 * @author Bruno Sousa
 * @export
 * @class TransferenciaCentroComponent
 * @implements {OnInit}
 */
@Component({
    selector: 'detalhe-transferencia-centro-avaliador',
    templateUrl: './detalhe.transferencia.centro.component.html'
})
export class DetalheTransferenciaCentroComponent implements PermissaoRotaComponente, OnInit {

    private _PedidoTransferenciaCentroDTO: PedidoTransferenciaCentroDTO;
    private _idTarefa;

    @ViewChild(HeaderPacienteComponent)
    private headerPaciente: HeaderPacienteComponent;

    /**
     * Cria uma instancia de AvaliacaoComponent.
     * @param {FormBuilder} _fb
     * @param {PacienteService} servicePaciente
     * @param {Router} router
     * @param {TranslateService} translate
     *
     * @memberOf AvaliacaoComponent
     */
    constructor(private router:Router, private activatedRouter: ActivatedRoute,
            private autenticacaoService: AutenticacaoService, private messageBox: MessageBox,
            private tarefaService: TarefaService,
            private pedidoTransferenciaCentroService: PedidoTransferenciaCentroService ) {
    }

    /**
     *
     *
     * @memberOf AvaliacaoComponent
     */
    ngOnInit(): void {
        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ["idPedidoTransferenciaCentro", "idTarefa"]).then(res => {
            this._idTarefa = res["idTarefa"];
            let idPedidoTransferenciaCentro =  res["idPedidoTransferenciaCentro"];

            this.pedidoTransferenciaCentroService.obterTransferencia(idPedidoTransferenciaCentro).then(res => {

                this._PedidoTransferenciaCentroDTO = new PedidoTransferenciaCentroDTO().jsonToEntity(res);

                setTimeout(() => {
                    Promise.resolve(this.headerPaciente).then(() => {
                        this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this._PedidoTransferenciaCentroDTO.pedidoTransferenciaCentro.paciente.rmr);
                    });
                }, 1);
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        });
    }

    /**
     * Método criar somente para a utilização do botão voltar.
     *
     * @memberOf AvaliacaoComponent
     */
    voltarConsulta() {
        this.tarefaService.removerAtribuicaoTarefa(this._idTarefa).then(res => {
            this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
        }, (error: ErroMensagem) =>{
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.DetalheTransferenciaCentroComponent];
    }

	public get pedidoTransferenciaCentro(): PedidoTransferenciaCentro {
		return this._PedidoTransferenciaCentroDTO ? this._PedidoTransferenciaCentroDTO.pedidoTransferenciaCentro : null;
    }

	public get evolucao(): Evolucao {
		return this._PedidoTransferenciaCentroDTO ? this._PedidoTransferenciaCentroDTO.ultimaEvolucao : null;
    }

    temPermissaoParaRecusarPedidoTransferencia(): boolean {
        return this.autenticacaoService.temRecurso(Recursos.RECUSAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR);
    }

    recusarPedidoTransferencia(): void {
        let data: any = {
            idPedidoTransferenciaCentro: this.pedidoTransferenciaCentro.id,
            fechar: () => {
                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
            }
        }
        this.messageBox.dynamic(data, ModalRecusarTransferenciaCentroComponent)
            .withTarget(this)
            .show();
    }

    temPermissaoParaAceitarPedidoTransferencia(): boolean {
        return this.autenticacaoService.temRecurso(Recursos.ACEITAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR);
    }

    aceitarPedidoTransferencia(): void {
        this.pedidoTransferenciaCentroService.aceitarPedidoTransferencia(this.pedidoTransferenciaCentro.id).then(res => {
            this.messageBox.alert(res.mensagem)
                .withTarget(this)
                .withCloseOption((target: any) => {
                    this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                })
                .show();
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });

    }


}
