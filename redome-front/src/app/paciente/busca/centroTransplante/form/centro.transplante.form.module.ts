import { NgModule } from "@angular/core";
import { ModalModule } from 'ngx-bootstrap';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ExportTranslateModule } from "../../../../shared/export.translate.module";
import { MensagemModule } from "../../../../shared/mensagem/mensagem.module";
import { TextMaskModule } from "angular2-text-mask/src/angular2TextMask";
import { MensagemModalModule } from "../../../../shared/modal/mensagem.modal.module";
import { CommonModule } from "@angular/common";
import { CentroTransplanteFormComponent } from "./centro.transplante.form.component";

@NgModule({
  imports: [CommonModule, ModalModule, FormsModule, ExportTranslateModule,
    MensagemModule, ReactiveFormsModule, TextMaskModule, MensagemModalModule],
  declarations: [CentroTransplanteFormComponent],
  providers: [],
  exports: [CentroTransplanteFormComponent]
})
export class CentroTransplanteFormModule {}
