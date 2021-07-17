import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CentroTransplanteService } from "app/admin/centrotransplante/centrotransplante.service";
import { DoadorService } from "app/doador/doador.service";
import { HeaderPacienteComponent } from "app/paciente/consulta/identificacao/header.paciente.component";
import { BaseForm } from "app/shared/base.form";
import { DetalheLogisticaMaterialDTO } from "app/shared/dto/detalhe.logistica.material.dto";
import { TiposDoador } from "app/shared/enums/tipos.doador";
import { ErroMensagem } from "app/shared/erromensagem";
import { Modal } from "app/shared/modal/factory/modal.factory";
import { MessageBox } from "app/shared/modal/message.box";
import { Transportadora } from "app/shared/model/transportadora";
import { LogisticaService } from "app/shared/service/logistica.service";
import { TransportadoraService } from "app/shared/service/transportadora.service";
import { TarefaService } from 'app/shared/tarefa.service';
import { DataUtil } from "app/shared/util/data.util";
import { DateMoment } from "app/shared/util/date/date.moment";
import { DateTypeFormats } from "app/shared/util/date/date.type.formats";
import { ErroUtil } from "app/shared/util/erro.util";
import { LogisticaMaterialService } from "../../../../../shared/service/logistica.material.service";


/**
 * Componente de detalhe da logística de material coletado do doador.
 * Ponto onde é informada a data de retirada do material e a transportadora responsável.
 */
@Component({
   selector: 'logistica-material-nacional',
   templateUrl: './logistica.material.nacional.component.html'
})

export class LogisticaMaterialNacionalComponent extends BaseForm<DetalheLogisticaMaterialDTO> implements OnInit {

   public detalhe: DetalheLogisticaMaterialDTO;
   public listaTransportadoras: Transportadora[];
   public hora: Array<string | RegExp>;
   private pedidoLogisticaId: number;
   private _prosseguirComPedidoLogistica: boolean = true;
   public logisticaMaterialGroup: FormGroup;

   private _opcoesSimNao:String[] = ["false","true"];
   private _labelsSimNao:String[] = [];

   @ViewChild('headerPaciente')
   private headerPaciente: HeaderPacienteComponent;

   constructor(private fb: FormBuilder, private router: Router, translate: TranslateService,
               private activatedRouter: ActivatedRoute, private doadorService: DoadorService,
               private builder: FormBuilder, private transportadoraService: TransportadoraService,
               private logisticaMaterialService: LogisticaMaterialService,
               private centroTransplanteService: CentroTransplanteService,
               private logisticaService: LogisticaService,
               private tarefaService: TarefaService,
               private messageBox: MessageBox) {
      super();
      this.translate = translate;

      this.hora = [/[0-2]/, /[0-9]/, ':', /[0-5]/, /[0-9]/]

      this.logisticaMaterialGroup = builder.group({
         'transportadora': [null, Validators.required],
         'horaPrevistaRetirada': [null, Validators.required],
         'prosseguirComPedidoLogistica': ["true", Validators.required],
         'justificativa': [null, Validators.required],
      });

      this.criarMensagensErro(this.logisticaMaterialGroup);
      this.setMensagensErro(this.logisticaMaterialGroup);

   }

   ngOnInit() {

      this.translate.get(["textosGenericos.sim", "textosGenericos.nao"]).subscribe(res => {
        this._labelsSimNao[0] = res['textosGenericos.nao'];
        this._labelsSimNao[1] = res['textosGenericos.sim'];
      });

      this.activatedRouter.params.subscribe(params => {
         this.pedidoLogisticaId = params['pedidoId'];

         this.logisticaMaterialService.obterLogisticaMaterial(this.pedidoLogisticaId).then(detalhe => {

              this.detalhe = new DetalheLogisticaMaterialDTO().jsonToEntity(detalhe);

              Promise.resolve(this.headerPaciente).then(() => {
                this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(3047084);
              });

              if (detalhe.horaPrevistaRetirada) {
                    this.detalhe.horaPrevistaRetirada = DateMoment.getInstance().parse(detalhe.horaPrevistaRetirada);
              }

              this.transportadoraService.listarTransportadoras().then(transportadoras => {
                this.listaTransportadoras = transportadoras;

                    if (this.detalhe.transportadora) {
                        this.logisticaMaterialGroup.get('transportadora').setValue(this.detalhe.transportadora);
                        this.logisticaMaterialGroup.get('transportadora').updateValueAndValidity();
                        this.logisticaMaterialGroup.get('horaPrevistaRetirada').setValue(DateMoment.getInstance().format(this.detalhe.horaPrevistaRetirada, DateTypeFormats.TIME_ONLY));
                        this.logisticaMaterialGroup.get('horaPrevistaRetirada').updateValueAndValidity();
                    }
                },
                (error: ErroMensagem) => {
                  ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            },
            (error: ErroMensagem) => {
               this.exibirMensagemDeAutorizacao(error);
            });
      });
   }

   nomeComponente(): string {
      return "LogisticaMaterialNacionalComponent";
   }

   private exibirMensagemDeAutorizacao(error: ErroMensagem) {
      var mensagem: string;
      error.listaCampoMensagem.forEach(obj => {
         mensagem = obj.mensagem;

      })
      var modal: Modal = this.messageBox.alert(mensagem);
      modal.closeOption = () => {
         this.voltar();
      }
      modal.show();
   }

   validarForm() : boolean{
      if(this._prosseguirComPedidoLogistica){
        this.setFieldRequiredSemForm("transportadora")
        this.setFieldRequiredSemForm("horaPrevistaRetirada")
        this.resetFieldRequiredSemForm("justificativa")
      }else{
        this.resetFieldRequiredSemForm("transportadora")
        this.resetFieldRequiredSemForm("horaPrevistaRetirada")
        this.setFieldRequiredSemForm("justificativa")
      }
      if (!this.validateForm()) {
          return;
      }
      return true;
   }

   private setarCampos() {
      this.detalhe.prosseguirComPedidoLogistica = this.getPropertyValue("prosseguirComPedidoLogistica");
      if(this._prosseguirComPedidoLogistica){
        let data:string = this.detalhe.dataColeta;
        let hora:string = this.getField(this.form(),"horaPrevistaRetirada").value;
        this.detalhe.horaPrevistaRetirada = DataUtil.toDate(data + hora  , DateTypeFormats.DATE_TIME);
        this.detalhe.transportadora = new Number(this.getPropertyValue("transportadora")).valueOf();
      }else{
          this.detalhe.justificativa = this.getPropertyValue("justificativa");
      }
   }

   public salvarLogistica(): void {
      if(this.validarForm()){
        this.setarCampos();
        this.logisticaService.salvarPedidoLogisticaMaterialColetaNacional(this.detalhe).then(msg => {
            let modal: Modal = this.messageBox.alert(msg);
            modal.show();
          },
          (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
          });
      }
   }

   public finalizarLogistica(): void {
      if(this.validarForm()){
        this.setarCampos();
        this.logisticaService.finalizarPedidoLogisticaMaterialColetaNacional(this.detalhe).then(msg => {
              let modal: Modal = this.messageBox.alert(msg);
              modal.closeOption = () => {
                this.voltar();
              }
              modal.show();
          },
          (error: ErroMensagem) => {
              ErroUtil.exibirMensagemErro(error, this.messageBox);
          });
      }
   }

   public form(): FormGroup {
      return this.logisticaMaterialGroup;
   }

   public preencherFormulario(entidade: DetalheLogisticaMaterialDTO): void {
      throw new Error("Method not implemented.");
   }

   public formatarData(data: Date): string {
      return DataUtil.toDateFormat(data, DateTypeFormats.DATE_ONLY);
   }

  isProsseguirComPedidoLogistica(value: string): void {
    value == 'true' ? this._prosseguirComPedidoLogistica = true: this._prosseguirComPedidoLogistica = false;
  }

   private voltar(): void {
      this.router.navigateByUrl("/doadores/workup/logistica");
   }

   public isCordao(): boolean {
     return this.detalhe && this.detalhe.idTipoDoador == TiposDoador.CORDAO_NACIONAL;
   }

   public isMedula(): boolean {
    return this.detalhe && this.detalhe.idTipoDoador == TiposDoador.NACIONAL
   }

   public obterDocumento(relatorio: string) {
      this.logisticaMaterialService.baixarDocumentos(this.pedidoLogisticaId, relatorio);
   }

    /**
     * Getter opcoesSimNao
     * @return {String[] }
     */
	public get opcoesSimNao(): String[]  {
		return this._opcoesSimNao;
	}

    /**
     * Setter opcoesSimNao
     * @param {String[] } value
     */
	public set opcoesSimNao(value: String[] ) {
		this._opcoesSimNao = value;
	}


    /**
     * Getter labelsSimNao
     * @return {String[] }
     */
	public get labelsSimNao(): String[]  {
		return this._labelsSimNao;
	}

    /**
     * Setter labelsSimNao
     * @param {String[] } value
     */
	public set labelsSimNao(value: String[] ) {
		this._labelsSimNao = value;
	}


    /**
     * Getter prosseguirComPedidoLogistica
     * @return {boolean }
     */
	public get prosseguirComPedidoLogistica(): boolean  {
		return this._prosseguirComPedidoLogistica;
	}

    /**
     * Setter prosseguirComPedidoLogistica
     * @param {boolean } value
     */
	public set prosseguirComPedidoLogistica(value: boolean ) {
		this._prosseguirComPedidoLogistica = value;
	}


}
