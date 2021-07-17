import { Component } from "@angular/core";
import { DoadorNacional } from "../../../../doador/doador.nacional";
import { DoadorService } from "../../../../doador/doador.service";
import { PrescricaoService } from "../../../../doador/solicitacao/prescricao.service";
import { PrescricaoDTO } from "../../../dto/prescricao.dto";
import { PrescricaoEvolucaoDTO } from "../../../dto/prescricao.evolucao.dto";
import { ErroMensagem } from "../../../erromensagem";
import { MessageBox } from "../../../modal/message.box";
import { ErroUtil } from "../../../util/erro.util";
import { DetalhePrescricaoDataEventService } from "../../detalheprescricao/detalhe.prescricao.data.event.service";
import { VisualizarPrescricaoDataEventService } from "./visualizar.prescricao.data.event.service";
import { TiposPrescricao } from "app/shared/enums/tipos.prescricao";

@Component({
   selector: "visualizar-prescricao",
   moduleId: module.id,
   templateUrl: "./visualizar.prescricao.component.html"
})
export class VisualizarPrescricaoComponent {

   public _rmr: number;
   public _showRmr: Boolean = true;

   constructor(private detalhePrescricaoDataEventService: DetalhePrescricaoDataEventService,
               private prescricaoService: PrescricaoService,
               private doadorService: DoadorService,
               private dataEventService: VisualizarPrescricaoDataEventService,
               private messageBox: MessageBox) {

        this.dataEventService.dataEvent.carregarPrescricao = (id: number, showRmr?: Boolean) => {
           this.prescricaoService.obterPrescricaoComEvolucaoPorIdPrecricao(id).then(res => {

                    let prescricaoEvolucao = new PrescricaoEvolucaoDTO().jsonToEntity(res);
                    let prescricao: PrescricaoDTO = prescricaoEvolucao.prescricao;
                    this._rmr = prescricao.rmr;
                    if (showRmr !== undefined && showRmr !== null) {
                       this._showRmr = showRmr;
                    }

                    this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.prescricaoMedulaDTO = prescricao.medula;
                    this.doadorService.obterDetalheDoadorParaPrescricao(prescricao.idDoador)
                       .then(result => {
                          if (prescricaoEvolucao.prescricao.idTipoPrescricao == TiposPrescricao.MEDULA) {
                             this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.dadosDoador = new DoadorNacional().jsonToEntity(result);
                          }
                       }, (error: ErroMensagem) => {
                          ErroUtil.exibirMensagemErro(error, this.messageBox);
                       });
                    this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.evolucaoDTO = prescricaoEvolucao.evolucao;
                    this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.arquivosPrescricaoDTO = prescricao.arquivosPrescricao;
                    this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.arquivosPrescricaoJustificativaDTO = prescricao.arquivosPrescricaoJustificativa;
                 },
               error => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                 });
        }

   }




}
