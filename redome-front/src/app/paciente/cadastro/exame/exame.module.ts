import { DiretivasModule } from '../../../shared/diretivas/diretivas.module';
import { PedidoExameService } from '../../../laboratorio/pedido.exame.service';
import { LaboratorioService } from '../../../shared/service/laboratorio.service';
import { ExportFileUploaderDirectiveModule } from '../../../shared/export.file.uploader.directive.module';
import { ExameComponent } from './exame.component';
import { RouterModule } from '@angular/router';
import { HeaderPacienteModule } from '../../consulta/identificacao/header.paciente.module';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TooltipModule, ModalModule, TypeaheadModule, BsDropdownModule } from 'ngx-bootstrap';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { HlaModule } from '../../../shared/hla/hla.module';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, ExportTranslateModule, ModalModule, HeaderPacienteModule
    , RouterModule, MensagemModule, TextMaskModule, ExportFileUploaderDirectiveModule, DiretivasModule, HlaModule],
  declarations: [ExameComponent],
  providers: [LaboratorioService, PedidoExameService],
  exports: [ExameComponent]
})
export class ExameModule { }

  