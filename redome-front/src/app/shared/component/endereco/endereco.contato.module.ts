import { DiretivasModule } from '../../diretivas/diretivas.module';
import { EnderecoContatoComponent } from './endereco.contato.component';
import { MensagemModalModule } from '../../modal/mensagem.modal.module';
import { MensagemModule } from '../../mensagem/mensagem.module';
import { ModalModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../export.translate.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';



@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, TextMaskModule, DiretivasModule],
    declarations: [EnderecoContatoComponent],
    providers: [],
    exports: [EnderecoContatoComponent]
  })
  export class EnderecoContatoModule { }