import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { TextMaskModule } from "angular2-text-mask/src/angular2TextMask";
import { HeaderDoadorModule } from "app/doador/consulta/header/header.doador.module";
import { DoadorService } from "app/doador/doador.service";
import { InputModule } from "app/shared/component/inputcomponent/input.module";
import { ExportTranslateModule } from "app/shared/export.translate.module";
import { MensagemModule } from "app/shared/mensagem/mensagem.module";
import { MensagemModalModule } from "app/shared/modal/mensagem.modal.module";
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { PaginationModule } from "ngx-bootstrap";
import { ModalModule } from "ngx-bootstrap/modal/modal.module";
import { VisualizarDoadorNacionalComponent } from "./visualizar.doador.nacional.component";


@NgModule({
  imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
    MensagemModule, MensagemModalModule, ModalModule, PaginationModule,
    HeaderDoadorModule, TextMaskModule, CurrencyMaskModule,InputModule, HeaderDoadorModule],
  declarations: [VisualizarDoadorNacionalComponent],
  providers: [DoadorService],
  exports: [VisualizarDoadorNacionalComponent]
})
export class DoadorNacionalModule { }
