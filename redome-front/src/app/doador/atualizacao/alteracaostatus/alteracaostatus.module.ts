import { NgModule } from "@angular/core";
import { ModalModule } from 'ngx-bootstrap';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ExportTranslateModule } from "../../../shared/export.translate.module";
import { MensagemModule } from "../../../shared/mensagem/mensagem.module";
import { TextMaskModule } from "angular2-text-mask/src/angular2TextMask";
import { MensagemModalModule } from "../../../shared/modal/mensagem.modal.module";
import { AlteracaoStatusComponent } from "./alteracaostatus.component";
import { CommonModule } from "@angular/common";


@NgModule({
  imports: [CommonModule, ModalModule, FormsModule, ExportTranslateModule,
    MensagemModule, ReactiveFormsModule, TextMaskModule, MensagemModalModule,
    TextMaskModule],
  declarations: [AlteracaoStatusComponent],
  providers: [],
  exports: [AlteracaoStatusComponent]
})
export class AlteracaoStatusModule {}
