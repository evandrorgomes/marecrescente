import { Component, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { CentroTransplanteService } from "app/admin/centrotransplante/centrotransplante.service";
import { CordaoInternacional } from "app/doador/cordao.internacional";
import { CordaoNacional } from "app/doador/cordao.nacional";
import { DoadorService } from "app/doador/doador.service";
import { ICordao } from "app/doador/ICordao";
import { PrescricaoDTO } from "app/shared/dto/prescricao.dto";
import { PrescricaoEvolucaoDTO } from "app/shared/dto/prescricao.evolucao.dto";
import { NumberUtil } from "app/shared/util/number.util";
import { PrescricaoService } from "../../../../doador/solicitacao/prescricao.service";
import { DetalhePrescricaoDataEventService } from "../../../../shared/component/detalheprescricao/detalhe.prescricao.data.event.service";
import { TiposDoador } from "../../../../shared/enums/tipos.doador";
import { ErroMensagem } from "../../../../shared/erromensagem";
import { HistoricoNavegacao } from "../../../../shared/historico.navegacao";
import { MessageBox } from "../../../../shared/modal/message.box";
import { PermissaoRotaComponente } from "../../../../shared/permissao.rota.componente";
import { ErroUtil } from "../../../../shared/util/erro.util";
import { RouterUtil } from "../../../../shared/util/router.util";
import { EvolucaoDTO } from "../../evolucao/evolucao.dto";
import { HeaderPacienteComponent } from "../../identificacao/header.paciente.component";
import { Doador } from "app/doador/doador";
import { DoadorInternacional } from "app/doador/doador.internacional";
import { DoadorNacional } from "app/doador/doador.nacional";
import {Observable} from "rxjs";
import { TiposPrescricao } from "app/shared/enums/tipos.prescricao";
import { EnderecoContatoCentroTransplante } from "app/shared/model/endereco.contato.centro.transplante";

/**
 *
 * @author Bruno Sousa
 * @export
 * @class ExameComponent

 */
@Component({
   selector: 'visualizardetalhe-prescricao',
   moduleId: module.id,
   templateUrl: './visualizar.detalhe.prescricao.component.html',
   //styleUrls: ['../paciente.css']
})
export class VisualizarDetalhePrescricaoComponent implements OnInit, PermissaoRotaComponente {

   @ViewChild('headerPaciente')
   private headerPaciente: HeaderPacienteComponent;

   private _prescricaoEvolucao: PrescricaoEvolucaoDTO;
   private _isCordao: boolean = false;
   private _titulo: string;
   public titulos: Map<number, string> = new Map<number, string>();

   constructor(private translate: TranslateService,
               private prescricaoService: PrescricaoService, private doadorService: DoadorService,
               private activatedRouter: ActivatedRoute, private router: Router,
               private centroTransplanteService: CentroTransplanteService,
               private detalhePrescricaoDataEventService: DetalhePrescricaoDataEventService,
               private messageBox: MessageBox) {

      this.translate.get("prescricao.visualizar").subscribe(res => {
         this.titulos.set(TiposDoador.NACIONAL, res['medulaNacional']);
         this.titulos.set(TiposDoador.INTERNACIONAL, res['medulaInternacional']);
         this.titulos.set(TiposDoador.CORDAO_NACIONAL, res['cordaoNacional']);
         this.titulos.set(TiposDoador.CORDAO_INTERNACIONAL, res['cordaoInternacional']);
      });

      this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.mostrarBotoesDescarteEAceite = false;
   }

   /**
    * Metodo que inicializa com os dados necessarios.
    */
   ngOnInit() {
      RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idPrescricao").then(res => {
         let idPrescricao = Number(res);
         this.prescricaoService.obterPrescricaoComEvolucaoPorIdPrecricao(idPrescricao)
         .then(res => {

            this._prescricaoEvolucao = new PrescricaoEvolucaoDTO().jsonToEntity(res);
            let prescricao: PrescricaoDTO = this._prescricaoEvolucao.prescricao;
            let evolucao: EvolucaoDTO = this._prescricaoEvolucao.evolucao;

            if(prescricao.idTipoPrescricao == TiposPrescricao.MEDULA){
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
            if(this._prescricaoEvolucao.prescricao.idTipoPrescricao == TiposPrescricao.MEDULA){
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

   nomeComponente(): string {
      return "VisualizarDetalhePrescricaoComponent";
   }

   /**
    * Volta para listagem
    * @returns void
    */
   public voltar(): void {
      this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
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
