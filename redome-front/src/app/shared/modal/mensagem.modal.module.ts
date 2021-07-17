import { ExportTranslateModule } from '../export.translate.module';
import { PerguntaModalComponente } from './pergunta.modal.component';
import { MensagemModalComponente } from './mensagem.modal.component';
import { ModalModule } from 'ngx-bootstrap';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import {FormsModule } from "@angular/forms";
import { AlertaModalComponente } from './factory/alerta.modal.component';
import { ConfirmacaoModalComponente } from './factory/confirmacao.modal.component';
import { ConteudoDinamicoModalComponente } from './factory/conteudo.dinamico.modal.component';
import { RecusarCentroTransplanteModal } from '../../paciente/busca/centroTransplante/recusarTransplante/recusar.centro.transplante.modal';

@NgModule({
  imports: [CommonModule, ModalModule, FormsModule, ExportTranslateModule],
  declarations: [MensagemModalComponente, PerguntaModalComponente, AlertaModalComponente, 
    ConfirmacaoModalComponente, ConteudoDinamicoModalComponente],
  providers: [],
  entryComponents: [ConteudoDinamicoModalComponente],
  exports: [MensagemModalComponente, PerguntaModalComponente, MensagemModalComponente]
})
export class MensagemModalModule { 

}

  
