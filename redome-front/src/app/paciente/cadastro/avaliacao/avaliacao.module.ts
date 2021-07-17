import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { AvaliacaoComponent } from 'app/paciente/cadastro/avaliacao/avaliacao.component';
import { ModalModule, TooltipModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';

@NgModule({
  imports: [FormsModule, CommonModule, ReactiveFormsModule, ExportTranslateModule, 
    ModalModule, RouterModule, TooltipModule, MensagemModalModule, MensagemModule],
  declarations: [AvaliacaoComponent],
  providers: [],
  exports: [AvaliacaoComponent]
})
export class AvaliacaoModule { }

  