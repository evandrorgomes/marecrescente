import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AccordionModule, ModalModule, PaginationModule } from 'ngx-bootstrap';
import { DiretivasModule } from '../../../shared/diretivas/diretivas.module';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { HeaderPacienteModule } from '../../consulta/identificacao/header.paciente.module';
import { BuscaChecklistConsultaComponent } from './buscachecklist.consulta.component';


@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule, AccordionModule,
              HeaderPacienteModule, RouterModule, MensagemModalModule, MensagemModule, PaginationModule, DiretivasModule],
    declarations: [BuscaChecklistConsultaComponent],
    providers: [],
    exports: [BuscaChecklistConsultaComponent]
  })
  export class BuscaCheckListModule{ }