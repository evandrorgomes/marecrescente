import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { TranslateService } from '@ngx-translate/core';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { PendenciaService } from '../pendencia.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { PendenciaDialogoComponent } from './pendencia.dialogo.component';
import { ModalModule } from 'ngx-bootstrap';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule } from "@angular/forms";
import { DialogoModule } from '../../../shared/dialogo/dialogo.module';

@NgModule({
  imports: [CommonModule, ModalModule, FormsModule, ExportTranslateModule, ReactiveFormsModule, MensagemModule, MensagemModalModule, DialogoModule],
  declarations: [PendenciaDialogoComponent],
  providers: [AutenticacaoService, PendenciaService, TranslateService],
  exports: [PendenciaDialogoComponent]
})
export class PendenciaDialogoModule { 

}