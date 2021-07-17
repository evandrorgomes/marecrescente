import { RouterModule } from '@angular/router';
import { BusyModule, BusyConfig } from 'angular2-busy';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { NgModule } from "@angular/core";
import { ExportTranslateModule } from '../../../../shared/export.translate.module';
import { MensagemModule } from '../../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../../shared/modal/mensagem.modal.module';
import { TarefaService } from '../../../../shared/tarefa.service';
import { ListarDefinirCentroTransplanteComponent } from './listar.definir.centro.transplante.component';
import { ConfirmarCentroTransplanteComponent } from './detalhe/confirmar.centro.transplante.component';
import { HeaderPacienteModule } from '../../../consulta/identificacao/header.paciente.module';
import { CentroTransplanteFormModule } from '../form/centro.transplante.form.module';

@NgModule({
  imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, 
            MensagemModule, MensagemModalModule, BusyModule, PaginationModule, 
            RouterModule, ReactiveFormsModule, HeaderPacienteModule, CentroTransplanteFormModule],
  declarations: [ListarDefinirCentroTransplanteComponent, ConfirmarCentroTransplanteComponent],
  providers: [TarefaService],
  exports: [ListarDefinirCentroTransplanteComponent, ConfirmarCentroTransplanteComponent]
})
export class ListarDefinirCentroTransplanteModule { }