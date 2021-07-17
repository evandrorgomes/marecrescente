import {Component, OnInit, ViewChild} from "@angular/core";
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateService} from "@ngx-translate/core";
import {DoadorService} from "app/doador/doador.service";
import {VisualizarPrescricaoDataEventService} from "app/shared/component/visualizar/prescricao/visualizar.prescricao.data.event.service";
import {HistoricoNavegacao} from "app/shared/historico.navegacao";
import {MessageBox} from "app/shared/modal/message.box";
import {PermissaoRotaComponente} from "app/shared/permissao.rota.componente";
import {QuestionarioComponent} from "app/shared/questionario/questionario.component";
import {FormularioService} from "app/shared/service/formulario.service";
import {PedidoWorkupService} from "app/shared/service/pedido.workup.service";
import {ErroUtil} from "app/shared/util/erro.util";
import {HeaderPacienteComponent} from "../../../../paciente/consulta/identificacao/header.paciente.component";
import {ErroMensagem} from '../../../../shared/erromensagem';
import {TarefaService} from '../../../../shared/tarefa.service';
import {RouterUtil} from "../../../../shared/util/router.util";
import {ResultadoWorkupService} from "../../../consulta/workup/resultado/resultadoworkup.service";
import {PedidoAdicionalWorkup} from "../resultado/pedido.adicional.workup";
import {ResultadoWorkup} from "../resultado/resultado.workup";
import {AvaliacaoResultadoWorkupService} from "./avaliacao.resultado.workup.service";
import {ModalDescricaoExameAdicionalComponent} from "./modal/modal.descricao.exame.adicional.component";
import {ModalJustificativaComponent} from "../../../../shared/component/modaljustificativa/modal.justificativa.component";
import {VisualizarResultadoWorkupDataEventService} from "../../../../shared/component/visualizar/resultadoworkup/visualizar.resultado.workup.data.event.service";

/**
 * Classe que representa o componente de avaliações de resultado de workup
 * @author ergomes
 */
@Component({
    selector: "avaliar-resultado-workup-nacional",
    moduleId: module.id,
    templateUrl: "./avaliar.resultado.workup.nacional.component.html"
})
export class AvaliarResultadoWorkupNacionalComponent implements OnInit, PermissaoRotaComponente {

  @ViewChild('headerPaciente')
   private headerPaciente: HeaderPacienteComponent;

   @ViewChild('questionario')
   protected questionarioComponent: QuestionarioComponent;

   public _resultadoWorkup: ResultadoWorkup;

   private _idResultadoWorkup: number;
   private _idTarefa: number;

   constructor(
      private translate: TranslateService,
      private fb: FormBuilder,
      private router: Router,
      private activatedRouter: ActivatedRoute,
      private messageBox: MessageBox,
      private doadorService: DoadorService,
      private formularioService: FormularioService,
      private resultadoWorkupService: ResultadoWorkupService,
      private avaliacaoResultadoWorkupService: AvaliacaoResultadoWorkupService,
      private tarefaService: TarefaService,
      private visualizarPrescricaoDataEventService: VisualizarPrescricaoDataEventService,
      private visualizarResultadoWorkupDataEventService: VisualizarResultadoWorkupDataEventService,
      private pedidoWorkupService: PedidoWorkupService) {
   }

   /**
    * Retorna o nome do componente
    * @returns string
    */
   nomeComponente(): string {
      return "AvaliarResultadoWorkupNacionalComponent";
   }

   /**
    * Método de inicialização do componente.
    */
   ngOnInit() {

      RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ['idResultadoWorkup', 'idTarefa', 'rmr', 'idPrescricao', 'identificacaoDoador']).then(res => {
         this._idResultadoWorkup = res['idResultadoWorkup'];
         this._idTarefa = res['idTarefa'];
         let dmr = res['identificacaoDoador'];
         let rmr = res['rmr'];
         let idPrescricao = res['idPrescricao'];

         this.resultadoWorkupService.obterResultadoWorkupNacional(this._idResultadoWorkup).then(res => {
            this._resultadoWorkup = new ResultadoWorkup().jsonToEntity(res);
            this.visualizarResultadoWorkupDataEventService.dataEvent.carregarResultadoWorkup(this._resultadoWorkup, dmr);
         }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
         });

         this.visualizarPrescricaoDataEventService.dataEvent.carregarPrescricao(idPrescricao, false);

         Promise.resolve(this.headerPaciente).then(() => {
            this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(rmr);
         });

      });
   }

   prosseguir() {
      if (this._resultadoWorkup.coletaInviavel) {
         let data = {
            confirmar: (justificativa) => {
               this.avaliacaoResultadoWorkupService.prosseguirColetaInviavelAvaliacaoResultadoWorkupNacional(this._idResultadoWorkup, justificativa).then(res => {
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
         };
         this.messageBox.dynamic(data, ModalJustificativaComponent).show();
      } else {
         this.avaliacaoResultadoWorkupService.prosseguirAvaliacaoResultadoWorkupNacional(this._idResultadoWorkup).then(res => {
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
   }

   naoProsseguir() {
      let data = {
         confirmar: (justificativa) => {
            this.avaliacaoResultadoWorkupService.naoProsseguirAvaliacaoResultadoWorkupNacional(this._idResultadoWorkup, justificativa).then(res => {
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
      };
      this.messageBox.dynamic(data, ModalJustificativaComponent).show();
   }

   examesAdicionais() {
      let data = {
         confirmar: (descricao) => {
            this.pedidoWorkupService.criarPedidoAdicionalWorkupDoadorNacional(this._resultadoWorkup.idPedidoWorkup, descricao).then(res => {
                  this.messageBox.alert(res)
                     .withCloseOption(() => {
                        this.router.navigateByUrl('/doadores/workup/centrocoleta/consulta');
                     })
                     .show();
               },
               (error: ErroMensagem) => {
                  ErroUtil.exibirMensagemErro(error, this.messageBox);
               });
         }
      };
      this.messageBox.dynamic(data, ModalDescricaoExameAdicionalComponent).show();
   }

   /**
    * Volta para componente anterior
    */
   public voltarConsulta() {
      this.tarefaService.removerAtribuicaoTarefa(this._idTarefa).then(res => {
         this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
      });
   }

   exibirExamesAdicionais(): boolean {
      return this.visualizarResultadoWorkupDataEventService.dataEvent.existePedidosAdicionais();
   }

   obterNomeArquivo(caminho: string): String {
      if (!caminho) {
         return "";
      }
      let splitCaminho: String[] = caminho.split("/");
      let nomeArquivo: String = splitCaminho[splitCaminho.length - 1];
      return nomeArquivo.substring(nomeArquivo.indexOf("_") + 1, nomeArquivo.length);
   }

}
