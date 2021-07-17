import { TranslateService } from '@ngx-translate/core';
import { ModalModule } from 'ngx-bootstrap';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ExportTranslateModule } from '../export.translate.module';
import { MensagemModule } from '../mensagem/mensagem.module';
import { MensagemModalModule } from '../modal/mensagem.modal.module';
import { DialogoComponent } from './dialogo.component';
import { AutenticacaoService } from '../autenticacao/autenticacao.service';

@NgModule({
  imports: [CommonModule, ModalModule, FormsModule, ExportTranslateModule, ReactiveFormsModule, MensagemModule, MensagemModalModule],
  declarations: [DialogoComponent],
  providers: [TranslateService, AutenticacaoService],
  exports: [DialogoComponent]
})
export class DialogoModule { 

}