import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { CentroTransplanteService } from "app/admin/centrotransplante/centrotransplante.service";
import { CordaoInternacional } from "app/doador/cordao.internacional";
import { CordaoNacional } from "app/doador/cordao.nacional";
import { DoadorInternacional } from "app/doador/doador.internacional";
import { DoadorNacional } from "app/doador/doador.nacional";
import { DoadorService } from "app/doador/doador.service";
import { PrescricaoService } from "app/doador/solicitacao/prescricao.service";
import { EvolucaoDTO } from "app/paciente/consulta/evolucao/evolucao.dto";
import { HeaderPacienteComponent } from "app/paciente/consulta/identificacao/header.paciente.component";
import { BaseForm } from "app/shared/base.form";
import { DetalhePrescricaoDataEventService } from "app/shared/component/detalheprescricao/detalhe.prescricao.data.event.service";
import { CentroTransplante } from "app/shared/dominio/centro.transplante";
import { DominioService } from "app/shared/dominio/dominio.service";
import { TipoPrescricao } from "app/shared/dominio/tipo.prescricao";
import { PrescricaoDTO } from "app/shared/dto/prescricao.dto";
import { PrescricaoEvolucaoDTO } from "app/shared/dto/prescricao.evolucao.dto";
import { ErroMensagem } from "app/shared/erromensagem";
import { MessageBox } from "app/shared/modal/message.box";
import { PermissaoRotaComponente } from "app/shared/permissao.rota.componente";
import { PedidoWorkupService } from "app/shared/service/pedido.workup.service";
import { ErroUtil } from "app/shared/util/erro.util";
import { MascaraUtil } from "../../../shared/util/mascara.util";
import { EnderecoContatoCentroTransplante } from "app/shared/model/endereco.contato.centro.transplante";

/**
 *
 * @author ergomes
 * @export
 * @class ExameComponent

 */
@Component({
   selector: 'definir-centro-coleta-pedido-workup',
   moduleId: module.id,
   templateUrl: './definir.centro.coleta.pedido.workup.component.html',
   //styleUrls: ['../paciente.css']
})
export class DefinirCentroColetaPedidoWorkupComponent extends BaseForm<Object> implements OnInit, PermissaoRotaComponente {

   @ViewChild('headerPaciente')
   private headerPaciente: HeaderPacienteComponent;

   private _centrosColetores: CentroTransplante[] = [];
   private _prescricaoEvolucao: PrescricaoEvolucaoDTO;
   private _form: FormGroup;
   private _idPedidoWorkup: number;
   private _idPrescricao: number;

   constructor(translate: TranslateService, private fb: FormBuilder,
               private prescricaoService: PrescricaoService, private doadorService: DoadorService,
               private activatedRouter: ActivatedRoute, private router: Router,
               private centroTransplanteService: CentroTransplanteService,
               private dominioService: DominioService, private pedidoWorkupService: PedidoWorkupService,
               private detalhePrescricaoDataEventService: DetalhePrescricaoDataEventService,
               private messageBox: MessageBox) {

      super();
      this.translate = translate;

   }

   /**
    * Metodo que inicializa com os dados necessarios.
    */
   ngOnInit() {
      this.buildForm();

      this.activatedRouter.queryParamMap.subscribe(queryParam => {
         if (queryParam.keys.length != 0) {
             this._idPedidoWorkup = Number(queryParam.get('idPedidoWorkup'));
             this._idPrescricao = Number(queryParam.get('idPrescricao'));

             this.prescricaoService.obterPrescricaoComEvolucaoPorIdPrecricao(this._idPrescricao)
             .then(res => {

                this._prescricaoEvolucao = new PrescricaoEvolucaoDTO().jsonToEntity(res);
                let prescricao: PrescricaoDTO = this._prescricaoEvolucao.prescricao;
                let evolucao: EvolucaoDTO = this._prescricaoEvolucao.evolucao;

                this.populaDataEventMedula(prescricao);

                this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.evolucaoDTO = evolucao;
                this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.arquivosPrescricaoDTO = prescricao.arquivosPrescricao;
                this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.arquivosPrescricaoJustificativaDTO = prescricao.arquivosPrescricaoJustificativa;

                this.dominioService.listarCentroColeta().then(res => {
                   this._centrosColetores = res;
                }, (error: ErroMensagem) => {
                   ErroUtil.exibirMensagemErro(error, this.messageBox);
                });

                this.translate.get("workup.pedido").subscribe(res => {
                   this._formLabels = res;
                   this.criarMensagemValidacaoPorFormGroup(this._form);
                   this.setMensagensErro(this._form);
               });

                Promise.resolve(this.headerPaciente).then(() => {
                   this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(prescricao.rmr);
                });
             },
             (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
             });
         }
      });
   }

   /**
    * * Método que constrói o formulário
    * @returns void
    */
    buildForm(): void {
      this._form = this.fb.group({
         'centroColeta': [null, Validators.required]
      });
   }

   public form(): FormGroup {
      return this._form;
   }

   public salvarCentroColeta(){
      if (this.validateForm()) {
         let idCentroColeta: number = this.form().get("centroColeta").value;
         this.pedidoWorkupService.definirCentroColetaPorPedidoWorkup(this._idPedidoWorkup, idCentroColeta)
         .then(res => {
				this.router.navigateByUrl('/doadores/workup/consulta');
         },
         (error: ErroMensagem) => {
               ErroUtil.exibirMensagemErro(error, this.messageBox);
         });
      }
   }

   public populaDataEventMedula(prescricao: PrescricaoDTO): void{
      this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.prescricaoMedulaDTO = prescricao.medula;
      this.carregaDetalhesDoador(prescricao.idDoador).then(dadosDoadorMedula => {
         this.detalhePrescricaoDataEventService.detalhePrescricaoDataEvent.dadosDoador = dadosDoadorMedula;
      });
   }

   public carregaDetalhesDoador(idDoador:number): Promise<any> {
      return new Promise((resolve, reject) => {
        this.doadorService.obterDetalheDoadorParaPrescricao(idDoador)
          .then(result => {
            if(this._prescricaoEvolucao.prescricao.idTipoPrescricao == TipoPrescricao.MEDULA){
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

   public validateForm(): boolean{
      this.clearMensagensErro(this.form());
      let valid: boolean = this.validateFields(this.form());
      this.setMensagensErro(this.form());
      return valid;
 }

   nomeComponente(): string {
      return "DefinirCentroColetaPedidoWorkupComponent";
   }

   /**
    * Volta para listagem
    * @returns void
    */
   public voltar(): void {
      this.router.navigateByUrl('/doadores/workup/consulta');
   }

   /**
     * Getter centrosColetores
     * @return {CentroTransplante[] }
     */
	public get centrosColetores(): CentroTransplante[]  {
		return this._centrosColetores;
   }

   public preencherFormulario(entidade: Object): void {
      throw new Error("Method not implemented.");
   }

   public get maskData(): Array<string | RegExp> {
      return MascaraUtil.data;
   }

}
