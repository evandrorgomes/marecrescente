import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { DiagnosticoComponent } from 'app/paciente/cadastro/diagnostico/diagnostico.component';
import { ModalModule, TooltipModule, TypeaheadModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';

@NgModule({
  imports: [FormsModule, CommonModule, ReactiveFormsModule, ExportTranslateModule, TypeaheadModule, 
    ModalModule, RouterModule, TextMaskModule, TooltipModule, MensagemModalModule, MensagemModule],
  declarations: [DiagnosticoComponent],
  providers: [],
  exports: [DiagnosticoComponent]
})
export class DiagnosticoModule { }

  