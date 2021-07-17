import { Component } from "@angular/core";
import { AvaliacaoResultadoWorkupService } from "../../../../doador/cadastro/resultadoworkup/avaliacao/avaliacao.resultado.workup.service";
import { PedidoAdicionalWorkup } from "../../../../doador/cadastro/resultadoworkup/resultado/pedido.adicional.workup";
import { ResultadoWorkup } from "../../../../doador/cadastro/resultadoworkup/resultado/resultado.workup";
import { Formulario } from "../../../classes/formulario";
import { TiposFormulario } from "../../../enums/tipo.formulario";
import { ErroMensagem } from "../../../erromensagem";
import { MessageBox } from "../../../modal/message.box";
import { FormularioService } from "../../../service/formulario.service";
import { PedidoWorkupService } from "../../../service/pedido.workup.service";
import { ErroUtil } from "../../../util/erro.util";
import { VisualizarResultadoWorkupDataEventService } from "./visualizar.resultado.workup.data.event.service";

@Component({
   selector: "visualizar-resultado-workup-nacional",

   moduleId: module.id,
   templateUrl: "./visualizar.resultado.workup.component.html"
})
export class VisualizarResultadoWorkupComponent {

   public _pedidosAdicionais: PedidoAdicionalWorkup[] = [];
   public _resultadoWorkup: ResultadoWorkup;
   public _formulario: Formulario;
   public _dmr: string;

   constructor(private dataEventService: VisualizarResultadoWorkupDataEventService,
               private pedidoWorkupService: PedidoWorkupService,
               private formularioService: FormularioService,
               private avaliacaoResultadoWorkupService: AvaliacaoResultadoWorkupService,
               private messageBox: MessageBox) {

      this.dataEventService.dataEvent.carregarResultadoWorkup = (resultadoWorkup: ResultadoWorkup, dmr?: string) => {

         this._resultadoWorkup = resultadoWorkup;

         if (dmr !== undefined && dmr !== null) {
            this._dmr = dmr;
         }

         this.carregarListaPedidosAdicionais(this._resultadoWorkup.idPedidoWorkup);
         this.carregarDadosFormulario(this._resultadoWorkup.idPedidoWorkup);

      }

      this.dataEventService.dataEvent.existePedidosAdicionais = (): boolean => {
         return this._pedidosAdicionais && this._pedidosAdicionais.length != 0;
      }


   }

   carregarListaPedidosAdicionais(idPedidoWorkup: number){
      this.pedidoWorkupService.listarPedidosAdicionaisWorkup(idPedidoWorkup).then(res => {
        this._pedidosAdicionais = res;
      }, (error: ErroMensagem) => {
         ErroUtil.exibirMensagemErro(error, this.messageBox);
      });
   }

   carregarDadosFormulario(idPedidoWorkup: number){
      this.formularioService.obterFormulario(idPedidoWorkup, TiposFormulario.RESULTADO_WORKUP).then(res => {
         this._formulario = new Formulario().jsonToEntity(res);
      })
         .catch((erro: ErroMensagem) => {
            if (erro.listaCampoMensagem && erro.listaCampoMensagem.length > 0) {
               if (erro.listaCampoMensagem[0].campo == 'erro' && erro.listaCampoMensagem[0].mensagem == "Nenhum questionário encontrado.") {
                  this._formulario = null;
               }
            }
         });
   }


   existeExamesAdicionais(): boolean  {
      return this._pedidosAdicionais && this._pedidosAdicionais.length > 0;
   }

   obterNomeArquivo(caminho:string):String{
      if(!caminho){
         return;
      }
      let splitCaminho:String[] = caminho.split("/");
      let nomeArquivo:String = splitCaminho[splitCaminho.length - 1];
      return nomeArquivo.substring(nomeArquivo.indexOf("_")+1,nomeArquivo.length);
   }


   baixarArquivoAdicionalWorkup(idDoArquivo:number, nomeArquivo:string){
      this.pedidoWorkupService.baixarArquivoAdicionalWorkup(idDoArquivo, nomeArquivo);
   }



}
