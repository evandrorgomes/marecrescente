import {Component, OnDestroy, OnInit} from "@angular/core";
import {PermissaoRotaComponente} from "../../../../shared/permissao.rota.componente";
import {BuildForm} from "../../../../shared/buildform/build.form";
import {StringControl} from "../../../../shared/buildform/controls/string.control";
import {NumberControl} from "../../../../shared/buildform/controls/number.control";
import {FormGroup, RequiredValidator, Validators} from "@angular/forms";
import {MascaraUtil} from "../../../../shared/util/mascara.util";
import {ValidateData} from "../../../../validators/data.validator";
import {InvoiceApagarEventService} from "../invoice.apagar.event.service";

@Component({
   selector: 'dados-invoice-apagar',
   templateUrl: './dados.invoice.apagar.component.html'
})
export class DadosInvoiceApagarComponent implements OnInit, OnDestroy {

   private formDadosInvoice: BuildForm<any>;

   _mascaraData = MascaraUtil.data;

   constructor() {
      this.formDadosInvoice = new BuildForm<any>()
         .add(new NumberControl('numero', [Validators.required]))
         .add(new StringControl('dataFaturamento', [Validators.required, ValidateData]))
         .add(new StringControl('dataVencimento', [Validators.required, ValidateData]));


      InvoiceApagarEventService.validarFormularioEtapa1 = (): Boolean => {
         return this.formDadosInvoice.valid;
      }

      InvoiceApagarEventService.obterDadosFormularioEtapa1 = (): any => {
         return this.formDadosInvoice.value;
      }

      InvoiceApagarEventService.popularFormularioEtapa1 = (value: any) :void => {
         this.formDadosInvoice.value = value;
      }

   }

   ngOnInit(): void {
   }

   ngOnDestroy(): void {
   }

   form(): FormGroup {
      return <FormGroup> this.formDadosInvoice.form;
   }


}
