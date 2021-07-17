import {HistoricoNavegacao} from '../../../../shared/historico.navegacao';
import {TranslateService} from '@ngx-translate/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Component, OnInit, ViewChild} from '@angular/core';
import {DoadorNacional} from '../../../doador.nacional';
import {TarefaService} from '../../../../shared/tarefa.service';
import {HeaderDoadorComponent} from '../../../consulta/header/header.doador.component';
import {AvaliacaoResultadoWorkupService} from '../../resultadoworkup/avaliacao/avaliacao.resultado.workup.service';
import {RessalvaComponent} from '../../contato/ressalvas/ressalva.component';
import {EventEmitterService} from '../../../../shared/event.emitter.service';
import {InativacaoModalComponent} from '../../../inativacao/inativacao.modal.component';
import {PermissaoRotaComponente} from "../../../../shared/permissao.rota.componente";
import {AvaliacaoPedidoColetaService} from "../../../../shared/service/avalaiacao.pedido.coleta.service";
import {RouterUtil} from "../../../../shared/util/router.util";
import {MessageBox} from "../../../../shared/modal/message.box";
import {VisualizarResultadoWorkupComponent} from "../../../../shared/component/visualizar/resultadoworkup/visualizar.resultado.workup.component";
import {VisualizarResultadoWorkupDataEventService} from "../../../../shared/component/visualizar/resultadoworkup/visualizar.resultado.workup.data.event.service";
import {ResultadoWorkup} from "../../resultadoworkup/resultado/resultado.workup";
import {PrescricaoService} from "../../../solicitacao/prescricao.service";
import {VisualizarPrescricaoDataEventService} from "../../../../shared/component/visualizar/prescricao/visualizar.prescricao.data.event.service";
import {ErroMensagem} from "../../../../shared/erromensagem";
import {ErroUtil} from "../../../../shared/util/erro.util";
import {ModalJustificativaComponent} from "../../../../shared/component/modaljustificativa/modal.justificativa.component";

/**
 * Classe que representa o componente para agendar pedido de coleta.
 * @author Bruno Sousa
 */
@Component({
    selector: "avaliar-doador",
    templateUrl: './avaliar.pedido.coleta.component.html'
})
export class AvaliarPedidoColetaComponent implements OnInit, PermissaoRotaComponente {

    @ViewChild(InativacaoModalComponent)
    private modalInativacao: InativacaoModalComponent;

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;

    @ViewChild('ressalva')
    private ressalvaComponent: RessalvaComponent;

    @ViewChild('visualizarResultadoWorkupComponent')
    private visualizarResultadoWorkupComponent: VisualizarResultadoWorkupComponent;

    private _idAvaliacaoResultadoWorkup: number;
    private _idTarefa: number;
    private _idDoador: number;

    public _avaliacaoProssegue: Boolean;
    public _justificaticaAvaliacao: String;
    private _coletaInviavel: Boolean;

    private _mensagensStatusAvaliacao: any;

    constructor(private router: Router, private tarefaService: TarefaService,
        private translate: TranslateService, private activatedRouter: ActivatedRoute,
        private avaliacaoResultadoWorkupService: AvaliacaoResultadoWorkupService,
        private avaliacaoPedidoColetaService: AvaliacaoPedidoColetaService,
        private messageBox: MessageBox,
        private prescricaoService: PrescricaoService,
        private dataEventService: VisualizarResultadoWorkupDataEventService,
        private visualizarPrescricaoDataEventService: VisualizarPrescricaoDataEventService) {
    }

    /**
     * Inicializa a classe com os dados buscando no serviço REST
     *
     * @memberOf AgendarPedidoColetaComponent
     */
    ngOnInit(): void {

       this.translate.get(['workup.coleta.avaliar.prosseguir.coletainviavel',
          'workup.coleta.avaliar.naoprosseguir.coletainviavel', 'workup.coleta.avaliar.naoprosseguir.coletaviavel']).subscribe(res => {

             this._mensagensStatusAvaliacao = res;

       });


        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter,
           ['idTarefa', 'idAvaliacaoResultadoWorkup', 'rmr', 'idDoador']).then(res => {
               this._idTarefa = res['idTarefa'];
               this._idAvaliacaoResultadoWorkup = res['idAvaliacaoResultadoWorkup'];
               this._idDoador = res['idDoador'];
               let rmr = res['rmr'];

               this.headerDoador.clearCache();
               Promise.resolve(this.headerDoador).then(() => {
                  this.headerDoador.popularCabecalhoIdentificacaoPorDoador(this._idDoador);
               });

               Promise.resolve(this.ressalvaComponent).then(() => {
                  this.ressalvaComponent.popularRessalvas(this._idDoador);
                  this.ressalvaComponent.recarregarCabecalhoDoador = true;
               });

               this.avaliacaoResultadoWorkupService.obterAvaliacaoResultadoWorkupPorId(this._idAvaliacaoResultadoWorkup).then( res => {

                  let resultadoWorkup = new ResultadoWorkup().jsonToEntity(res.resultadoWorkupDTO);

                  this._avaliacaoProssegue = res.avaliacaoProsseguir;
                  this._justificaticaAvaliacao = res.justificativaAvaliacaoResultadoWorkup;
                  this._coletaInviavel = resultadoWorkup.coletaInviavel;

                  this.visualizarPrescricaoDataEventService.dataEvent.carregarPrescricao(res.idPrescricao, false);

                  Promise.resolve(this.visualizarResultadoWorkupComponent).then(res1 => {
                      this.dataEventService.dataEvent.carregarResultadoWorkup(resultadoWorkup);
                  });

               });
        });

        EventEmitterService.get(RessalvaComponent.RECARREGAR_CABECALHO).subscribe((): void => {
            this.headerDoador.clearCache();
            this.headerDoador.popularCabecalhoIdentificacaoPorDoador(this._idDoador);
        });
    }

    nomeComponente(): string {
        return "AvaliarPedidoColetaComponent"
    }

    voltar() {
        this.tarefaService.removerAtribuicaoTarefa(this._idTarefa).then(() => {
            this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
        });
    }

    public prosseguirPedidoColeta() {
        this.avaliacaoPedidoColetaService.prosseguirPedidoColeta(this._idAvaliacaoResultadoWorkup).then(res => {
            this.messageBox.alert(res)
               .withCloseOption(() => {
                  this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
               })
               .show();
        },
        (error: ErroMensagem) => {
           ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

   public naoProsseguirPedidoColeta() {
      if (this._avaliacaoProssegue) {
         let data = {
            confirmar: (justificativa) => {
               this.naoProsseguir(justificativa);
            }
         };

         this.messageBox.dynamic(data, ModalJustificativaComponent).show();
      }
      else {
         this.naoProsseguir();
      }
   }

    inativarDoador(): void {
        let doador:DoadorNacional = new DoadorNacional();
        doador.id = this._idDoador;
        this.modalInativacao.doador = doador;
        this.modalInativacao.abrirModal();

        EventEmitterService.get(InativacaoModalComponent.FINALIZAR_EVENTO).subscribe((doador: DoadorNacional): void => {

           this.headerDoador.clearCache();
           this.naoProsseguir("Doador inativado!!");


        });
    }

    naoProsseguir(justificativa?: string) {
       this.avaliacaoPedidoColetaService.naoProsseguirPedidoColeta(this._idAvaliacaoResultadoWorkup, justificativa).then(res => {
             this.messageBox.alert(res)
                .withCloseOption(() => {
                   this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                })
                .show();
          },
          (error: ErroMensagem) => {
             ErroUtil.exibirMensagemErro(error, this.messageBox);
          });
    }


   obterStatusAvalacao(): String {
       if (this._avaliacaoProssegue !== undefined && this._coletaInviavel !== undefined) {
          if (this._avaliacaoProssegue && this._coletaInviavel) {
             return this._mensagensStatusAvaliacao['workup.coleta.avaliar.prosseguir.coletainviavel'];
          }
          else if (!this._avaliacaoProssegue && this._coletaInviavel) {
             return this._mensagensStatusAvaliacao['workup.coleta.avaliar.naoprosseguir.coletainviavel'];
          }
          else {
             return this._mensagensStatusAvaliacao['workup.coleta.avaliar.naoprosseguir.coletaviavel'];
          }

       }
       return ""
   }

};
