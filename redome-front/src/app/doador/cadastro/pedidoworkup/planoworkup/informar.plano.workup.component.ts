import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { CentroTransplanteService } from "app/admin/centrotransplante/centrotransplante.service";
import { HeaderDoadorComponent } from "app/doador/consulta/header/header.doador.component";
import { DoadorService } from "app/doador/doador.service";
import { PrescricaoService } from "app/doador/solicitacao/prescricao.service";
import { BaseForm } from "app/shared/base.form";
import { BuildForm } from "app/shared/buildform/build.form";
import { StringControl } from "app/shared/buildform/controls/string.control";
import { DetalhePrescricaoDataEventService } from "app/shared/component/detalheprescricao/detalhe.prescricao.data.event.service";
import { EnderecoContatoComponent } from "app/shared/component/endereco/endereco.contato.component";
import { PlanoWorkupInternacionalDTO } from "app/shared/dto/plano.workup.internacional.dto";
import { PlanoWorkupNacionalDTO } from "app/shared/dto/plano.workup.nacional.dto";
import { PrescricaoDTO } from "app/shared/dto/prescricao.dto";
import { PrescricaoEvolucaoDTO } from "app/shared/dto/prescricao.evolucao.dto";
import { TiposDoador } from "app/shared/enums/tipos.doador";
import { TiposTarefa } from "app/shared/enums/tipos.tarefa";
import { ErroMensagem } from "app/shared/erromensagem";
import { EnderecoContatoCentroTransplante } from "app/shared/model/endereco.contato.centro.transplante";
import { PedidoWorkupService } from "app/shared/service/pedido.workup.service";
import { DateMoment } from "app/shared/util/date/date.moment";
import { DateTypeFormats } from "app/shared/util/date/date.type.formats";
import { ErroUtil } from "app/shared/util/erro.util";
import { ValidateData, ValidateDataMaiorOuIgualHoje } from "app/validators/data.validator";
import { FileItem, FileUploader } from "ng2-file-upload";
import { VisualizarPrescricaoDataEventService } from "../../../../shared/component/visualizar/prescricao/visualizar.prescricao.data.event.service";
import { ComponenteRecurso } from "../../../../shared/enums/componente.recurso";
import { MessageBox } from "../../../../shared/modal/message.box";
import { PermissaoRotaComponente } from "../../../../shared/permissao.rota.componente";
import { UploadArquivoComponent } from "../../../../shared/upload/upload.arquivo.component";

@Component({
   selector: "informar-plano-workup",
   templateUrl: './informar.plano.workup.component.html',
})
export class InformarPlanoWorkupComponent extends BaseForm<Object> implements OnInit, PermissaoRotaComponente {

   @ViewChild("uploadComponent")
   private uploadComponent: UploadArquivoComponent;

   @ViewChild('headerDoador')
   private headerDoador: HeaderDoadorComponent;

   @ViewChild("enderecoWorkup")
   private enderecoWorkup: EnderecoContatoComponent;

   public uploader: FileUploader;

   private _buildFormCentro: BuildForm<any>;
   private _buildFormDoador: BuildForm<any>;

   private _prescricaoEvolucao: PrescricaoEvolucaoDTO;

   private _idPedidoWorkup: number;
   private _idPrescricao: number;
   private _idTipoTarefa: number;
   private _rmr: number;
   private _titulo: string;
   private _tipoDoador: TiposDoador;
   private _doadorEmJejum: boolean = false;

   public labelsDoadorEmJejum:String[] = ["Sim","Nao"];
   public opcoesDoadorEmJejum:String[] = ["S","N"];

   public maskData: Array<string | RegExp>
   public maskHora: Array<string | RegExp>
   public titulos: Map<number, string> = new Map<number, string>();

   constructor(private router: Router, translate: TranslateService,
      private activatedRouter: ActivatedRoute, private messageBox: MessageBox, private fb: FormBuilder,
      protected pedidoWorkupService: PedidoWorkupService, protected detalhePrescricaoDataEventService: DetalhePrescricaoDataEventService,
      protected doadorService: DoadorService, protected prescricaoService: PrescricaoService,
      protected centroTransplanteService: CentroTransplanteService,
      private visualizarPrescricaoDataEventService: VisualizarPrescricaoDataEventService) {
      super();

      translate.get("workup.plano.visualizarTitulo").subscribe(res => {
         this.titulos.set(TiposDoador.NACIONAL, res['medulaNacional']);
         this.titulos.set(TiposDoador.INTERNACIONAL, res['medulaInternacional']);
      });

      this.maskData = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/]
      this.maskHora = [/[0-2]/, /\d/, ':', /[0-5]/, /\d/]

      this.buildFormCentro();
   }

   ngOnInit(): void {

      this.uploadComponent.extensoes = 'extensaoArquivoRelatorioInternacional';
      this.uploadComponent.tamanhoLimite = 'tamanhoArquivoRelatorioInternacionalEmByte';
      this.uploadComponent.quantidadeMaximaArquivos = 'quantidadeArquivosRelatorioInternacional';
      this.uploadComponent.uploadObrigatorio = true;

      this.activatedRouter.queryParamMap.subscribe(queryParam => {
         if (queryParam.keys.length != 0) {
            this._idPedidoWorkup = Number(queryParam.get('idPedidoWorkup'));
            this._idPrescricao = Number(queryParam.get('idPrescricao'));
            this._idTipoTarefa = Number(queryParam.get('idTipoTarefa'));
            let idCentroTransplante = Number(queryParam.get('idCentroTransplante'));

            if (!this.isWorkupNacional()) {
               this.resetFieldRequired(this._buildFormCentro.form, 'dataInternacao');
            }else{
               this.buildFormDoador();
            }

            this.visualizarPrescricaoDataEventService.dataEvent.carregarPrescricao(this._idPrescricao);

            this.prescricaoService.obterPrescricaoComEvolucaoPorIdPrecricao(this._idPrescricao)
               .then(res => {

                  this._prescricaoEvolucao = new PrescricaoEvolucaoDTO().jsonToEntity(res);
                  let prescricao: PrescricaoDTO = this._prescricaoEvolucao.prescricao;

                  this._rmr = prescricao.rmr;
                  this._titulo = this.titulos.get(prescricao.idTipoDoador);
                  this._tipoDoador = TiposDoador.valueOf(prescricao.idTipoPrescricao);

                  Promise.resolve(this.headerDoador).then(() => {
                     this.headerDoador.popularCabecalhoIdentificacaoPorDoador(prescricao.idDoador);
                  });

               });

               if (this.isWorkupNacional()) {

                    this.centroTransplanteService.obterEnderecoWorkup(idCentroTransplante).then(res => {
                        let endereco: EnderecoContatoCentroTransplante = new EnderecoContatoCentroTransplante()
                        .jsonToEntity(res);

                        if (endereco) {
                          endereco.principal = false;
                          this.enderecoWorkup.preencherFormulario(endereco);
                      }
                      else {
                          this.enderecoWorkup.setValoresPadroes();
                          this.enderecoWorkup.configEndNacionalForm();
                      }
                    });
               }
         }
      },
         (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
         });


   }

   buildFormCentro(): void {

      this._buildFormCentro  = new BuildForm<any>()
      .add(new StringControl("dataExame", [Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje]))
      .add(new StringControl("dataResultado", [Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje]))
      .add(new StringControl("dataInternacao", [Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje]))
      .add(new StringControl("dataColeta", [Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje]))
      .add(new StringControl("observacao"));

      this.criarMensagemValidacaoPorFormGroup(this._buildFormCentro.form);
      this.clearMensagensErro(this._buildFormCentro.form);
   }

   buildFormDoador(): void {
      this._buildFormDoador = new BuildForm<any>()
         .add(new StringControl("dataExameMedico1", [Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje]))
         .add(new StringControl("horaExameMedico1", [Validators.required]))
         .add(new StringControl("dataExameMedico2", [Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje]))
         .add(new StringControl("horaExameMedico2", [Validators.required]))
         .add(new StringControl("dataRepeticaoBthcg", [Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje]))
         .add(new StringControl("horaRepeticaoBthcg", [Validators.required]))
         .add(new StringControl("nomeSetorAndar", [Validators.required]))
         .add(new StringControl("procurarPor", [Validators.required]))
         .add(new StringControl("doadorEmJejum", [Validators.required]))
         .add(new StringControl("horasEmJejum", [Validators.required]))
         .add(new StringControl("informacoesAdicionais", [Validators.required]));

         this.criarMensagemValidacaoPorFormGroup(this._buildFormDoador.form);
         this.clearMensagensErro(this._buildFormDoador.form);
   }

   form(): FormGroup {
      return <FormGroup>this._buildFormCentro.form;
   }

   formDoador(): FormGroup {
      return <FormGroup>this._buildFormDoador.form;
   }

   nomeComponente(): string {
      return ComponenteRecurso.Componente[ComponenteRecurso.Componente.InformarPlanoWorkupComponent];
   }

   voltar() {
      if (this.isWorkupNacional()) {
        this.router.navigateByUrl('/doadores/workup/centrocoleta/consulta');
      }
      else{
        this.router.navigateByUrl('/doadores/workup/consulta');
      }
   }

   salvar() {

      if (this.validateForm()) {

         let arquivo: FileItem;
         this.uploadComponent.arquivos.forEach((item: FileItem, key: string) => {
            arquivo = item;
         });

         if (this.isWorkupNacional()) {

            let planoWorkupNacional = PlanoWorkupNacionalDTO.parse(this._buildFormCentro.form, this._buildFormDoador.form);

            this.pedidoWorkupService.salvarPlanoWorkupNacional(this._idPedidoWorkup, planoWorkupNacional, arquivo)
               .then(res => {
                  this.router.navigateByUrl('/doadores/workup/centrocoleta/consulta');
               },
                  (error: ErroMensagem) => {
                     ErroUtil.exibirMensagemErro(error, this.messageBox);
                  });

         } else {

            let planoWorkupInternacional = PlanoWorkupInternacionalDTO.parse(this._buildFormCentro.form);

            this.pedidoWorkupService.salvarPlanoWorkupInternacional(this._idPedidoWorkup, planoWorkupInternacional, arquivo)
               .then(res => {
                  this.router.navigateByUrl('/doadores/workup/consulta');
               },
                  (error: ErroMensagem) => {
                     ErroUtil.exibirMensagemErro(error, this.messageBox);
                  });
         }
      }
   }



   validateForm(): boolean {
      let validCentro: boolean = super.validateFields(this.form());
      if (!validCentro) {
        return false;
     }
      if (this.isWorkupNacional()) {
          let validDoador: boolean = super.validateFields(this.formDoador());
          if (!validDoador) {
            return false;
          }
      }
      let campoValida: boolean = true;
      let dateMoment: DateMoment = DateMoment.getInstance();

      let dataExame = dateMoment.parse(this.form().get("dataExame").value, DateTypeFormats.DATE_TIME);
      let dataResultado = dateMoment.parse(this.form().get("dataResultado").value, DateTypeFormats.DATE_TIME);
      let dataColeta = dateMoment.parse(this.form().get("dataColeta").value, DateTypeFormats.DATE_TIME);

      let dataResultadoValida = dateMoment.isDateTimeSameOrBefore(dataExame, dataResultado);
      let dataColetaValida = true;

      if (this.isWorkupNacional()) {
         let dataInternacao = dateMoment.parse(this.form().get("dataInternacao").value, DateTypeFormats.DATE_TIME);
         let dataInternacaoValida = dateMoment.isDateTimeSameOrBefore(dataResultado, dataInternacao);
         dataColetaValida = dateMoment.isDateTimeSameOrBefore(dataInternacao, dataColeta);

         let dataExameMedico2 = dateMoment.parse(this.formDoador().get("dataExameMedico2").value, DateTypeFormats.DATE_TIME);
         if(!dataExameMedico2){
            this.resetFieldRequired(this._buildFormDoador.form, 'horaExameMedico2');
         }

         let dataRepeticaoBthcg = dateMoment.parse(this.formDoador().get("dataRepeticaoBthcg").value, DateTypeFormats.DATE_TIME);
         if(!dataRepeticaoBthcg){
            this.resetFieldRequired(this._buildFormDoador.form, 'horaRepeticaoBthcg');
         }

         //let doadorEmJejum = this.formDoador().get("doadorEmJejum").value;
         //if(doadorEmJejum == 'S'){
           // this.resetFieldRequired(this._buildFormDoador.form, 'horasEmJejum');
         //}

         if (!dataInternacaoValida) {
            this.forceError("dataInternacao", "Data de Internação é menor que a Data do Resultado."); //this._formLabels['']
            campoValida = false;
         }

         if (!dataColetaValida) {
            this.forceError("dataColeta", "Data da Coleta é menor que a Data de Internação."); //this._formLabels['']
            campoValida = false;
         }

      } else {

         dataColetaValida = dateMoment.isDateTimeSameOrBefore(dataResultado, dataColeta);

         if (!dataColetaValida) {
            this.forceError("dataColeta", "Data da Coleta é menor que a Data de Resultado."); //this._formLabels['']
            campoValida = false;
         }

         if (this.uploadComponent.arquivos.size == 0) {
            this.forceError("arquivo", "Seleção do arquivo é obrigatória."); //this._formLabels['arquivo']
            campoValida = false;
         }
      }

      if (!dataResultadoValida) {
         this.forceError("dataResultado", "Data do Resultado é menor que a Data de Exame.");  //this._formLabels['']
         campoValida = false;
      }

      return campoValida;
   }

   isWorkupNacional(): boolean {
      return this._idTipoTarefa && this._idTipoTarefa == TiposTarefa.INFORMAR_PLANO_WORKUP_NACIONAL.id;
   }

   isDoadorEmJejum(value: string): void {
      value == 'S' ? this._doadorEmJejum = true: this._doadorEmJejum = false;
      if (this.doadorEmJejum) {
         this.setFieldRequired(this._buildFormDoador.form, 'horasEmJejum');
      }
      else {
         this.resetFieldRequired(this._buildFormDoador.form, 'horasEmJejum');
      }
   }


   public preencherFormulario(entidade: Object): void {
      throw new Error("Method not implemented.");
   }


   /**
    * Getter rmr
    * @return {number }
    */
   public get rmr(): number {
      return this._rmr;
   }

   /**
    * Setter rmr
    * @param {number } value
    */
   public set rmr(value: number) {
      this._rmr = value;
   }


   /**
    * Getter titulo
    * @return {string}
    */
   public get titulo(): string {
      return this._titulo;
   }

   /**
    * Setter titulo
    * @param {string} value
    */
   public set titulo(value: string) {
      this._titulo = value;
   }


    /**
     * Getter doadorEmJejum
     * @return {boolean }
     */
	public get doadorEmJejum(): boolean  {
		return this._doadorEmJejum;
	}

    /**
     * Setter doadorEmJejum
     * @param {boolean } value
     */
	public set doadorEmJejum(value: boolean ) {
		this._doadorEmJejum = value;
	}


}
