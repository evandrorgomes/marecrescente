import { AssociaPendenciaModule } from '../pendencia/associa.pendencia.module';
import { RouterModule } from '@angular/router';
import { HeaderPacienteModule } from '../../consulta/identificacao/header.paciente.module';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { EvolucaoModule } from './evolucao.module';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NovaEvolucaoComponent } from './novaevolucao.component';
import { TooltipModule, ModalModule, TypeaheadModule, BsDropdownModule } from 'ngx-bootstrap';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, EvolucaoModule, ExportTranslateModule, ModalModule, HeaderPacienteModule, RouterModule, AssociaPendenciaModule],
  declarations: [NovaEvolucaoComponent],
  providers: [],
  exports: [NovaEvolucaoComponent]
})
export class NovaEvolucaoModule { }

  