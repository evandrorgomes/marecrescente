import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ExportTranslateModule} from "../../export.translate.module";
import {MensagemModalModule} from "../../modal/mensagem.modal.module";
import {ModalJustificativaComponent} from "./modal.justificativa.component";
import {InputModule} from "../inputcomponent/input.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
   imports: [CommonModule, ExportTranslateModule, FormsModule, ReactiveFormsModule,
      MensagemModalModule, InputModule],
   declarations: [ModalJustificativaComponent],
   entryComponents: [ModalJustificativaComponent],
   providers: [],
   exports: [ModalJustificativaComponent]
})
export class ModalJustificativaModule { }