import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from "@ngx-translate/core";
import { VisualizarPrescricaoDataEventService } from "app/shared/component/visualizar/prescricao/visualizar.prescricao.data.event.service";
import { PedidoWorkupService } from "app/shared/service/pedido.workup.service";
import { HeaderPacienteComponent } from "../../../../paciente/consulta/identificacao/header.paciente.component";
import { DetalhePrescricaoDataEventService } from "../../../../shared/component/detalheprescricao/detalhe.prescricao.data.event.service";
import { ErroMensagem } from '../../../../shared/erromensagem';
import { HistoricoNavegacao } from "../../../../shared/historico.navegacao";
import { MessageBox } from "../../../../shared/modal/message.box";
import { PermissaoRotaComponente } from "../../../../shared/permissao.rota.componente";
import { TarefaService } from '../../../../shared/tarefa.service';
import { ErroUtil } from "../../../../shared/util/erro.util";
import { RouterUtil } from "../../../../shared/util/router.util";
import { ResultadoWorkupService } from "../../../consulta/workup/resultado/resultadoworkup.service";
import { DoadorService } from "../../../doador.service";
import { PrescricaoService } from "../../../solicitacao/prescricao.service";
import { PedidoAdicionalWorkup } from "../resultado/pedido.adicional.workup";
import { ResultadoWorkup } from "../resultado/resultado.workup";
import { AvaliacaoResultadoWorkupService } from "./avaliacao.resultado.workup.service";
import { ModalDescricaoExameAdicionalComponent } from "./modal/modal.descricao.exame.adicional.component";
import { ModalJustificativaComponent } from "../../../../shared/component/modaljustificativa/modal.justificativa.component";

/**
 * Classe que representa o componente de avaliações de resultado de workup
 * @author ergomes
 */
@Component({
    selector: "avaliar-resultado-workup",
    moduleId: module.id,
    templateUrl: "./avaliar.resultado.workup.internacional.component.html",
    styleUrls: ['./download.component.css']
})
export class AvaliarResultadoWorkupInternacionalComponent implements OnInit, PermissaoRotaComponente {

    private idResultadoWorkup: number;
    private idTarefa: number;

    private _pedidosAdicionais: PedidoAdicionalWorkup[] = [];

    @ViewChild('headerPaciente')
    private headerPaciente: HeaderPacienteComponent;

    public resultadoWorkup: ResultadoWorkup;

    constructor(private fb: FormBuilder,
        private resultadoWorkupService: ResultadoWorkupService,
        private router: Router, private activatedRouter: ActivatedRoute,
        private translate: TranslateService,
        private avaliacaoResultadoWorkupService: AvaliacaoResultadoWorkupService,
        private tarefaService:TarefaService,
        private messageBox: MessageBox,
        protected detalhePrescricaoDataEventService: DetalhePrescricaoDataEventService,
        private prescricaoService: PrescricaoService,
	private visualizarPrescricaoDataEventService: VisualizarPrescricaoDataEventService,
	private doadorService: DoadorService,
        private pedidoWorkupService: PedidoWorkupService) {

    }

    /**
     * Método de inicialização do componente.
     */
    ngOnInit() {

        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ['idResultadoWorkup', 'idTarefa', 'rmr', 'idPrescricao']).then(res => {
            this.idResultadoWorkup = res['idResultadoWorkup'];
            this.idTarefa = res['idTarefa'];
            let rmr = res['rmr'];
            let idPrescricao = res['idPrescricao'];
            Promise.resolve(this.headerPaciente).then(() => {
                this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(rmr);
            });

            this.visualizarPrescricaoDataEventService.dataEvent.carregarPrescricao(idPrescricao, false);

            this.resultadoWorkupService.obterResultadoWorkup(this.idResultadoWorkup).then(res => {
                this.resultadoWorkup = new ResultadoWorkup().jsonToEntity(res);
                this.carregarListaPedidosAdicionais(this.resultadoWorkup.idPedidoWorkup);
             },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });

        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });

    }

    obterNomeArquivo(caminho:string):String{
        if(!caminho){
          return;
        }
        let splitCaminho:String[] = caminho.split("/");
        let nomeArquivo:String = splitCaminho[splitCaminho.length - 1];
        return nomeArquivo.substring(nomeArquivo.indexOf("_")+1,nomeArquivo.length);
    }

    /**
     * Método para voltar para home
     *
     * @memberof ConferenciaComponent
     */
    public irParaHome() {
        this.router.navigateByUrl('/home');
    }

    baixarArquivoDelaudo(idDoArquivo:number, nomeArquivo:string){
        this.avaliacaoResultadoWorkupService.baixarArquivoDeResultadoWorkup(idDoArquivo, nomeArquivo);
    }

    baixarArquivoAdicionalWorkup(idDoArquivo:number, nomeArquivo:string){
      this.pedidoWorkupService.baixarArquivoAdicionalWorkup(idDoArquivo, nomeArquivo);
    }

    /**
     * Retorna o nome do componente
     * @returns string
     */
    nomeComponente(): string {
        return "AvaliarResultadoWorkupInternacionalComponent";
    }

    /**
     * Volta para componente anterior
     */
    public voltarConsulta() {
       this.tarefaService.removerAtribuicaoTarefa(this.idTarefa).then(res => {
          this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
       });
    }

    carregarListaPedidosAdicionais(idPedidoWorkup: number){
      this.pedidoWorkupService.listarPedidosAdicionaisWorkup(idPedidoWorkup).then(res => {
          this._pedidosAdicionais = res;
      }, (error: ErroMensagem) => {
        ErroUtil.exibirMensagemErro(error, this.messageBox);
      });
    }

    prosseguir() {
        if(this.resultadoWorkup.coletaInviavel) {
            let data = {
               confirmar: (justificativa) => {
                  this.avaliacaoResultadoWorkupService.prosseguirAvaliacaoResultadoWorkupInternacional(this.idResultadoWorkup, justificativa).then(res => {
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
        else {
           this.avaliacaoResultadoWorkupService.prosseguirAvaliacaoResultadoWorkupInternacional(this.idResultadoWorkup).then(res => {
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
            this.avaliacaoResultadoWorkupService.naoProsseguirAvaliacaoResultadoWorkupInternacional(this.idResultadoWorkup, justificativa).then(res => {
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

    examesAdicionais(){
      let data = {
        confirmar: (descricao) => {
           this.pedidoWorkupService.criarPedidoAdicionalWorkupDoadorInternacional(this.resultadoWorkup.idPedidoWorkup, descricao).then(res => {
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

    exibirExamesAdicionais(): boolean  {
      return this._pedidosAdicionais && this._pedidosAdicionais.length > 0;
    }
}

