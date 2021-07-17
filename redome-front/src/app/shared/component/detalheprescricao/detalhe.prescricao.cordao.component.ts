import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from "@angular/core";
import { TranslateService } from '@ngx-translate/core';
import { ICordao } from "app/doador/ICordao";
import { EvolucaoDTO } from "app/paciente/consulta/evolucao/evolucao.dto";
import { Pais } from "app/shared/dominio/pais";
import { ArquivoPrescricaoDTO } from "app/shared/dto/arquivo.prescricao.dto";
import { PrescricaoCordaoDTO } from "app/shared/dto/prescricao.cordao.dto";
import { FormatterUtil } from "app/shared/util/formatter.util";
import { ArquivoPrescricao } from "../../../doador/consulta/workup/arquivo.prescricao";
import { AvaliarPrescricaoService } from "../../../paciente/cadastro/prescricao/avaliacao/avaliar.prescricao.service";
import { TiposDoador } from "../../enums/tipos.doador";
import { EnderecoContatoComponent } from "../endereco/endereco.contato.component";
import { DetalhePrescricaoDataEventService } from "./detalhe.prescricao.data.event.service";
import { Doador } from "app/doador/doador";
import {Observable} from "rxjs";

/**
 * Componente para exibir o detalhe da prescrição cordão..
 * @author ergomes
 */
@Component({
   selector: "detalhe-prescricao-cordao",
   templateUrl: './detalhe.prescricao.cordao.component.html'
})
export class DetalhePrescricaoCordaoComponent implements OnInit, OnDestroy {

   @ViewChild('enderecoEntregaCP')
   private enderecoContatoComponent: EnderecoContatoComponent;

   public titulos: Map<number, string> = new Map<number, string>();
   private _titulo: string;
   private enderecoEntregaSubscriber: any;

   constructor(
      private translate: TranslateService,
      private avaliarPrescricaoService: AvaliarPrescricaoService,
      private detalhePrescricaoDataEventService: DetalhePrescricaoDataEventService) {

      this.translate.get("prescricao.visualizar").subscribe(res => {
         this.titulos.set(TiposDoador.CORDAO_NACIONAL, res['cordaoNacional']);
         this.titulos.set(TiposDoador.CORDAO_INTERNACIONAL, res['cordaoInternacional']);
      });

   }

   ngOnInit() {
      this.enderecoEntregaSubscriber = this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.enderecoEntrega.subscribe(res => {
         if(this.enderecoContatoComponent){
            this.enderecoContatoComponent.preencherFormulario(res);
         }
      });
   }

   ngOnDestroy() {
      this.enderecoEntregaSubscriber.unsubscribe();
   }

   get prescricaoCordao(): PrescricaoCordaoDTO {
      return this.detalhePrescricaoDataEventService ? this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.prescricaoCordaoDTO : null;
   }

   get evolucao(): EvolucaoDTO{
      return this.detalhePrescricaoDataEventService ? this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.evolucaoDTO : null;
   }

   get cordaoDoador(): ICordao {
      return this.detalhePrescricaoDataEventService ? this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.dadosDoador : null;
   }

   get arquivosPrescricao(): ArquivoPrescricaoDTO[] {
      return this.detalhePrescricaoDataEventService ? this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.arquivosPrescricaoDTO : [];
   }

   get arquivosJustificativa(): ArquivoPrescricaoDTO[] {
      return this.detalhePrescricaoDataEventService ? this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.arquivosPrescricaoJustificativaDTO : [];
   }

   public baixarArquivo(arquivoPrescricao: ArquivoPrescricao) {
      this.avaliarPrescricaoService.baixarArquivoPrescricao(arquivoPrescricao.id, arquivoPrescricao.nomeSemTimestamp);
   }

   obterPesoFormatado(): string {
      if(this.cordaoDoador && this.cordaoDoador.peso){
         return FormatterUtil.obterPesoFormatado(String(this.cordaoDoador.peso), 2);
      }
      return "0,0";
   }

   public isBrasil(pais: Pais): boolean{
         return pais && pais.id == 1;
   }

   public get titulo(): string {
      return this._titulo;
   }

}
