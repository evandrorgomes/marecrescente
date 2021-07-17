import { AssociaPendenciaModule } from '../pendencia/associa.pendencia.module';
import { ExameModule } from './exame.module';
import { NovoExameComponent } from './novoexame.component';
import { RouterModule } from '@angular/router';
import { HeaderPacienteModule } from '../../consulta/identificacao/header.paciente.module';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TooltipModule, ModalModule, TypeaheadModule, BsDropdownModule } from 'ngx-bootstrap';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, ExameModule, ExportTranslateModule, 
            ModalModule, HeaderPacienteModule, RouterModule, AssociaPendenciaModule],
  declarations: [NovoExameComponent],
  providers: [],
  exports: [NovoExameComponent]
})
export class NovoExameModule { }

  