import { Component, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { HeaderPacienteComponent } from 'app/paciente/consulta/identificacao/header.paciente.component';
import { BaseForm } from '../../../../shared/base.form';
import { DetalhePrescricaoDataEventService } from "../../../../shared/component/detalheprescricao/detalhe.prescricao.data.event.service";
import { TiposDoador } from '../../../../shared/enums/tipos.doador';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../../shared/historico.navegacao';
import { MessageBox } from "../../../../shared/modal/message.box";
import { PermissaoRotaComponente } from "../../../../shared/permissao.rota.componente";
import { ErroUtil } from "../../../../shared/util/erro.util";
import { RouterUtil } from '../../../../shared/util/router.util';
import { AvaliacaoPrescricaoDTO } from "./avaliacao.prescricao.dto";
import { AvaliarPrescricaoService } from './avaliar.prescricao.service';
import { JustificativaAvaliacaoModal } from "./justificativa.avaliacao.modal";
import { PrescricaoDTO } from "app/shared/dto/prescricao.dto";
import { EvolucaoDTO } from "app/paciente/consulta/evolucao/evolucao.dto";
import { TipoPrescricao } from "app/shared/dominio/tipo.prescricao";
import { DoadorService } from "app/doador/doador.service";
import { CordaoNacional } from "app/doador/cordao.nacional";
import { CordaoInternacional } from "app/doador/cordao.internacional";
import { ICordao } from "app/doador/ICordao";
import { Doador } from "app/doador/doador";
import { DoadorNacional } from "app/doador/doador.nacional";
import { DoadorInternacional } from "app/doador/doador.internacional";
import { CentroTransplanteService } from "app/admin/centrotransplante/centrotransplante.service";
import { NumberUtil } from "app/shared/util/number.util";
import {Observable} from "rxjs";
import { EnderecoContatoCentroTransplante } from "app/shared/model/endereco.contato.centro.transplante";


/**
 *
 * @author Fillipe Queiroz
 * @export
 * @class ExameComponent
 * @extends {BaseForm}
 * @implements {OnDestroy}
 */
@Component({
   selector: 'avaliar-prescricao',
   moduleId: module.id,
   templateUrl: './avaliar.prescricao.component.html',
   //styleUrls: ['../paciente.css']
})
export class AvaliarPrescricaoComponent implements OnInit, PermissaoRotaComponente {

   @ViewChild('headerPaciente')
   private headerPaciente: HeaderPacienteComponent;

   public _avaliacaoPrescricao: AvaliacaoPrescricaoDTO;
   private _isCordao: boolean = false;

   private _titulo: string;
   public titulos: Map<number, string> = new Map<number, string>();

   constructor(private translate: TranslateService,
               private avaliarPrescricaoService: AvaliarPrescricaoService,
               private activatedRouter: ActivatedRoute, private router: Router,
               private detalhePrescricaoDataEventService: DetalhePrescricaoDataEventService,
               private doadorService: DoadorService, private centroTransplanteService: CentroTransplanteService,
               private messageBox: MessageBox) {

      this.translate.get("prescricao.visualizar").subscribe(res => {
         this.titulos.set(TiposDoador.NACIONAL, res['medulaNacional']);
         this.titulos.set(TiposDoador.INTERNACIONAL, res['medulaInternacional']);
         this.titulos.set(TiposDoador.CORDAO_NACIONAL, res['cordaoNacional']);
         this.titulos.set(TiposDoador.CORDAO_INTERNACIONAL, res['cordaoInternacional']);
      });

      this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.mostrarBotoesDescarteEAceite = true;
   }

   /**
    * Metodo que inicializa com os dados necessarios.
    */
   ngOnInit() {
      RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idAvaliacaoPrescricao").then(res => {
         let idAvaliacaoPrecricao = Number(res);
         this.avaliarPrescricaoService.obterAvaliacaoPrescricaoPorId(idAvaliacaoPrecricao)
         .then(res => {

            this._avaliacaoPrescricao = new AvaliacaoPrescricaoDTO().jsonToEntity(res);
            let prescricao: PrescricaoDTO = this._avaliacaoPrescricao.prescricaoEvolucao.prescricao;
            let evolucao: EvolucaoDTO = this._avaliacaoPrescricao.prescricaoEvolucao.evolucao;

            if(prescricao.idTipoPrescricao == TipoPrescricao.MEDULA){
               this.populaDataEventMedula(prescricao);
            }else{
               this._isCordao = true;
               this.populaDataEventCordao(prescricao, evolucao);
            }

            this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.evolucaoDTO = evolucao;
            this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.arquivosPrescricaoDTO = prescricao.arquivosPrescricao;
            this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.arquivosPrescricaoJustificativaDTO = prescricao.arquivosPrescricaoJustificativa;

            this._titulo = this.titulos.get(prescricao.idTipoDoador);

            Promise.resolve(this.headerPaciente).then(() => {
               this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(prescricao.rmr);
            });
         },
         (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
         });
      });

   }

   public populaDataEventMedula(prescricao: PrescricaoDTO): void{
      this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.prescricaoMedulaDTO = prescricao.medula;
      this.carregaDetalhesDoador(prescricao.idDoador).then(dadosDoadorMedula => {
         this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.dadosDoador = dadosDoadorMedula;
      });
   }

   public populaDataEventCordao(prescricao: PrescricaoDTO, evolucao: EvolucaoDTO): void{
      this.carregaDetalhesDoador(prescricao.idDoador).then(dadosDoadorCordao => {
         if(dadosDoadorCordao){
            if (evolucao && evolucao.peso) {
               prescricao.cordao.quantidadePorKgOpcao1 = NumberUtil.round(dadosDoadorCordao.quantidadeTotalTCN / evolucao.peso, 2);
               prescricao.cordao.quantidadePorKgOpcao2 = NumberUtil.round(dadosDoadorCordao.quantidadeTotalCD34 / evolucao.peso, 2);
            }
            this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.dadosDoador = dadosDoadorCordao;
         }
      });
      this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.prescricaoCordaoDTO = prescricao.cordao;
      this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.enderecoEntrega = new Observable<EnderecoContatoCentroTransplante>(observer => {
         this.carregaEnderecoEntrega(prescricao.idCentroTransplante).then(endereco => {
            if(endereco){
               observer.next(endereco);
            }
         });
      });

   }

   public carregaDetalhesDoador(idDoador:number): Promise<any> {
      return new Promise((resolve, reject) => {
        this.doadorService.obterDetalheDoadorParaPrescricao(idDoador)
          .then(result => {
            if(this._avaliacaoPrescricao.prescricaoEvolucao.prescricao.idTipoPrescricao == TipoPrescricao.MEDULA){
               if (result.tipoDoador == 'NACIONAL') {
                  resolve(new DoadorNacional().jsonToEntity(result));
               } else {
                  resolve(new DoadorInternacional().jsonToEntity(result));
               }
            }else{
               if (result.tipoDoador == 'NACIONAL') {
                  resolve(new CordaoNacional().jsonToEntity(result));
               } else {
                  resolve(new CordaoInternacional().jsonToEntity(result));
               }
            }
          }, (error: ErroMensagem) => {
            Promise.reject(error);
          });
      });
   }

   public carregaEnderecoEntrega(idCentroTransplante: number): Promise<EnderecoContatoCentroTransplante>{
      return new Promise((resolve, reject) => {
         this.centroTransplanteService.obterEnderecoEntrega(idCentroTransplante)
         .then(result => {
            resolve(new EnderecoContatoCentroTransplante().jsonToEntity(result));
         }, (error: ErroMensagem) => {
            Promise.reject(error);
         });
      });
   }

   public exibirBotoesDescarteEAprovacao(): boolean {
      if (this._avaliacaoPrescricao && this.detalhePrescricaoDataEventService) {
         return this._avaliacaoPrescricao.prescricaoEvolucao.prescricao.medula.fonteCelulaOpcao1 != null && this._avaliacaoPrescricao.prescricaoEvolucao.prescricao.medula.fonteCelulaOpcao2 != null && this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.mostrarBotoesDescarteEAceite;
      }
      return false;
   }

   nomeComponente(): string {
      return "AvaliarPrescricaoComponent";
   }

   private confirmaAprovacao() {

      let data = {
         confirmar: (justificativa: string) => {
            this.finalizarAprovacaoAvaliacao(this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.obterIdentificadorFonteCelulaDescartada(), justificativa);
         }
      }

      this.messageBox.dynamic(data, JustificativaAvaliacaoModal).show();

   }

   private finalizarAprovacaoAvaliacao(idFonte?: number, justificativa?: string) {
      this.avaliarPrescricaoService.aprovarAvaliacao(this._avaliacaoPrescricao.idAvaliacaoPrescricao, idFonte, justificativa).then(res => {
            this.messageBox.alert(res)
               .withCloseOption((target:any) => {
                  this.voltar();
               })
               .show();
         },
         (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
         });
   }

   /**
    * Volta para listagem
    * @returns void
    */
   public voltar(): void {
      this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
   }

   public reprovarAvaliacao(): void {
      let data = {
         confirmar: (justificativa: string) => {
            this.avaliarPrescricaoService.reprovarAvaliacao(this._avaliacaoPrescricao.idAvaliacaoPrescricao, justificativa).then(res => {
                  this.messageBox.alert(res.mensagem)
                     .withCloseOption((target:any) => {
                        this.voltar();
                     })
                     .show();
               },
               (error: ErroMensagem) => {
                  ErroUtil.exibirMensagemErro(error, this.messageBox);
               });

         }
      }
      this.messageBox.dynamic(data, JustificativaAvaliacaoModal).show();
   }

   /**
    * Aprova a avaliacao
    * @returns void
    */
   public aprovarAvaliacao(): void {
      if (this.temDuasFontes()) {
         if (this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.verificarTodasFontesAceitas()) {
            this.finalizarAprovacaoAvaliacao();
         } else if (this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.verificarTodasFontesDescartadas()) {
            this.exibirMensagemErroParaAceitarAoMenosUmaFonte();
         } else {
            let idFonteDescartada = this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.obterIdentificadorFonteCelulaDescartada();
            let idFonteAceita = this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.obterIdentificadorFonteCelulaAceita();
            if (idFonteDescartada && idFonteAceita) {
               this.confirmaAprovacao();
            } else if (!idFonteDescartada) {
               this.confirmaAprovacao();
            } else if (!idFonteAceita) {
               this.exibirMensagemErroParaAceitarAoMenosUmaFonte();
            }
         }
      } else {
         this.finalizarAprovacaoAvaliacao();
      }

   }

   private exibirMensagemErroParaAceitarAoMenosUmaFonte() {
      this.translate.get('prescricao.avaliacoes.detalhe.erroFontesDescartadas').subscribe(res => {
         this.messageBox.alert(res).show();
      });
   }

   private temDuasFontes(): boolean {
      let count: number = 0;
      if (this._avaliacaoPrescricao.prescricaoEvolucao.prescricao.medula.fonteCelulaOpcao1 &&
         this._avaliacaoPrescricao.prescricaoEvolucao.prescricao.medula.fonteCelulaOpcao1.id) {
         count++
      }
      if (this._avaliacaoPrescricao.prescricaoEvolucao.prescricao.medula.fonteCelulaOpcao2 &&
         this._avaliacaoPrescricao.prescricaoEvolucao.prescricao.medula.fonteCelulaOpcao2.id) {
         count++
      }
      return count == 2;
   }

   /**
    * isDoadorNacional
    * @return {boolean}
    */
   public isDoadorNacional(tipoDoador: number): boolean {
      if (tipoDoador == TiposDoador.NACIONAL ||
         tipoDoador == TiposDoador.CORDAO_NACIONAL) {
         return true;
      }
      return false;
   }

   /**
    * Envia para a tela de doador.
    * @returns void
    */
   public goToDetalheDoador(): void {
      this.router.navigateByUrl('/doadores/detalhe?idDoador=' + this._avaliacaoPrescricao.prescricaoEvolucao.prescricao.idDoador);
   }

      /**
    * Getter titulo
    * @return {boolean}
    */
   public get titulo(): string {
      return this._titulo;
   }

   /**
    * isCordao
    * @return {boolean}
    */
   public isCordao(): boolean {
      return this._isCordao;
   }

    /**
    * isMedula
    * @return {boolean}
    */
   public isMedula(): boolean {
      return !this._isCordao;
   }
}
