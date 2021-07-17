import { MensagemModalModule } from '../../modal/mensagem.modal.module';
import { MensagemModule } from '../../mensagem/mensagem.module';
import { ModalModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../export.translate.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { EmailContatoComponent } from './email.contato.component';



@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, TextMaskModule],
    declarations: [EmailContatoComponent],
    providers: [],
    exports: [EmailContatoComponent]
  })
  export class EmailContatoModule { }