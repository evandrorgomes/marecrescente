import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { IdentificacaoComponent } from './identificacao.component';
import { RouterModule } from '@angular/router';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TooltipModule, ModalModule, TypeaheadModule, BsDropdownModule } from 'ngx-bootstrap';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';

@NgModule({
  imports: [FormsModule, CommonModule, ReactiveFormsModule, ExportTranslateModule, 
    ModalModule, RouterModule, TextMaskModule, TooltipModule, MensagemModalModule, MensagemModule],
  declarations: [IdentificacaoComponent],
  providers: [],
  exports: [IdentificacaoComponent]
})
export class IdentificacaoModule { }

  