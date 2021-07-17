import { Component, Input } from "@angular/core";
import { TranslateService } from '@ngx-translate/core';
import { Doador } from 'app/doador/doador';
import { EvolucaoDTO } from 'app/paciente/consulta/evolucao/evolucao.dto';
import { ArquivoPrescricaoDTO } from 'app/shared/dto/arquivo.prescricao.dto';
import { PrescricaoMedulaDTO } from 'app/shared/dto/prescricao.medula.dto';
import { ArquivoPrescricao } from "../../../doador/consulta/workup/arquivo.prescricao";
import { AvaliarPrescricaoService } from "../../../paciente/cadastro/prescricao/avaliacao/avaliar.prescricao.service";
import { FontesCelulas } from "../../enums/fontes.celulas";
import { FormatterUtil } from './../../util/formatter.util';
import { DetalhePrescricaoDataEventService } from "./detalhe.prescricao.data.event.service";

/**
 * Componente para exibir o detalhe da prescrição de medula..
 * @author ergomes
 */
@Component({
   selector: "detalhe-prescricao-medula",
   templateUrl: './detalhe.prescricao.medula.component.html'
})
export class DetalhePrecricaoMedulaComponent {

   @Input() showDescarteEAprovacao: boolean = false;

   protected CLASSE_FONTE_ATIVA: string = "ativarFonte";
   protected CLASSE_FONTE_DESCARTADA: string = "desativarFonte";

   public estiloFonteCelulaOpcao1: string = this.CLASSE_FONTE_ATIVA;
   public estiloFonteCelulaOpcao2: string = this.CLASSE_FONTE_ATIVA;

   constructor(
      private translate: TranslateService,
      private avaliarPrescricaoService: AvaliarPrescricaoService,
      private detalhePrescricaoDataEventService: DetalhePrescricaoDataEventService) {

      this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.verificarTodasFontesDescartadas = (): boolean => {
         return this.estiloFonteCelulaOpcao1 == this.CLASSE_FONTE_DESCARTADA
            && this.estiloFonteCelulaOpcao2 == this.CLASSE_FONTE_DESCARTADA
      }

      this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.verificarTodasFontesAceitas = (): boolean => {
         return this.estiloFonteCelulaOpcao1 == this.CLASSE_FONTE_ATIVA
            && this.estiloFonteCelulaOpcao2 == this.CLASSE_FONTE_ATIVA
      }

      this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.obterIdentificadorFonteCelulaDescartada = (): number => {
         if (this.estiloFonteCelulaOpcao1 == this.CLASSE_FONTE_DESCARTADA) {
            return this.prescricaoMedula.fonteCelulaOpcao1.id;
         }
         if (this.estiloFonteCelulaOpcao2 == this.CLASSE_FONTE_DESCARTADA) {
            return this.prescricaoMedula.fonteCelulaOpcao2.id;
         }
         return null;
      }

      this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.obterIdentificadorFonteCelulaAceita = (): number => {
         if (this.estiloFonteCelulaOpcao1 == this.CLASSE_FONTE_ATIVA) {
            return this.prescricaoMedula.fonteCelulaOpcao1.id;
         }
         if (this.estiloFonteCelulaOpcao2 == this.CLASSE_FONTE_ATIVA) {
            return this.prescricaoMedula.fonteCelulaOpcao2.id;
         }
         return null;
      }
   }

   get prescricaoMedula(): PrescricaoMedulaDTO {
      return this.detalhePrescricaoDataEventService ? this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.prescricaoMedulaDTO : null;
   }

   get evolucao(): EvolucaoDTO{
      return this.detalhePrescricaoDataEventService ? this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.evolucaoDTO : null;
   }

   get doadorMedula(): Doador {
      return this.detalhePrescricaoDataEventService ? this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.dadosDoador : null;
   }

   get arquivos(): ArquivoPrescricaoDTO[] {
      return this.detalhePrescricaoDataEventService ? this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.arquivosPrescricaoDTO : [];
   }

   get arquivosJustificativa(): ArquivoPrescricaoDTO[] {
      return this.detalhePrescricaoDataEventService ? this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.arquivosPrescricaoJustificativaDTO : [];
   }

   public ehFonteCelulaMedula(fonteCelulaId: number): boolean {
      return fonteCelulaId == FontesCelulas.MEDULA_OSSEA.id;
   }

   public exibirBotoesDescarteEAprovacao(): boolean {
      if (this.prescricaoMedula && this.detalhePrescricaoDataEventService && this.showDescarteEAprovacao) {
         return this.prescricaoMedula.fonteCelulaOpcao1 != null && this.prescricaoMedula.fonteCelulaOpcao2 != null && this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.mostrarBotoesDescarteEAceite;
      }
      return false;
   }

   public removerEstiloFonteCelulaOpcao1() {
      this.estiloFonteCelulaOpcao1 = this.CLASSE_FONTE_DESCARTADA
   }

   public aceitarEstiloFonteCelulaOpcao1() {
      this.estiloFonteCelulaOpcao1 = this.CLASSE_FONTE_ATIVA
   }

   public removerEstiloFonteCelulaOpcao2() {
      this.estiloFonteCelulaOpcao2 = this.CLASSE_FONTE_DESCARTADA
   }

   public aceitarEstiloFonteCelulaOpcao2() {
      this.estiloFonteCelulaOpcao2 = this.CLASSE_FONTE_ATIVA
   }

   public baixarArquivo(arquivoPrescricao: ArquivoPrescricao) {
      this.avaliarPrescricaoService.baixarArquivoPrescricao(arquivoPrescricao.id, arquivoPrescricao.nomeSemTimestamp);
   }

   obterPesoFormatado(): string {
      if(this.doadorMedula && this.doadorMedula.peso){
         return FormatterUtil.obterPesoFormatado(String(this.doadorMedula.peso), 2);
      }
      return "0,0";
   }

}