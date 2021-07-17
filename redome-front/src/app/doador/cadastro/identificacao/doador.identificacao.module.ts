import { DoadorIdentificacaoComponent } from './doador.identificacao.component';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { ModalModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TextMaskModule } from "angular2-text-mask/src/angular2TextMask";


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, TextMaskModule],
    declarations: [DoadorIdentificacaoComponent],
    providers: [],
    exports: [DoadorIdentificacaoComponent]
  })
  export class DoadorIdentificacaoModule { }