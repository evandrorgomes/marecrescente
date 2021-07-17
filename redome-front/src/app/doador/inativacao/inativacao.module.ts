import { ModalModule } from 'ngx-bootstrap';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { InativacaoModalComponent } from './inativacao.modal.component';
import { InativacaoComponent } from './inativacao.component';

@NgModule({
  imports: [CommonModule, ModalModule, FormsModule, ExportTranslateModule,
    MensagemModule, ReactiveFormsModule, TextMaskModule, MensagemModalModule,
    TextMaskModule],
  declarations: [InativacaoModalComponent, InativacaoComponent],
  providers: [],
  exports: [InativacaoModalComponent, InativacaoComponent]
})
export class InativacaoModule {}
