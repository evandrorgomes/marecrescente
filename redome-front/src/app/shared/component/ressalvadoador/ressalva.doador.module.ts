import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { RessalvaModule } from '../../../doador/cadastro/contato/ressalvas/ressalva.module';
import { ExportTranslateModule } from '../../export.translate.module';
import { MensagemModule } from '../../mensagem/mensagem.module';
import { MensagemModalModule } from '../../modal/mensagem.modal.module';
import { ModalModule } from 'ngx-bootstrap';
import { RessalvaDoadorComponent } from './ressalva.doador.component';

@NgModule({
  imports: [CommonModule, ModalModule, FormsModule, ExportTranslateModule,
    MensagemModule, ReactiveFormsModule, TextMaskModule, MensagemModalModule,
    TextMaskModule, RessalvaModule],
  declarations: [RessalvaDoadorComponent],
  providers: [],
  exports: [RessalvaDoadorComponent]
})
export class RessalvaDoadorModule {}
