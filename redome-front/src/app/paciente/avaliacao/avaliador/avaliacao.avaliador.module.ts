import { InputModule } from 'app/shared/component/inputcomponent/input.module';
import { ModalAprovarReprovarAvaliacaoComponent } from './modal/modal.aprovar.reprovar.avaliacao.component';
import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AccordionModule, ModalModule, PaginationModule } from 'ngx-bootstrap';
import { DialogoModule } from '../../../shared/dialogo/dialogo.module';
import { DiretivasModule } from '../../../shared/diretivas/diretivas.module';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { HeaderPacienteModule } from '../../consulta/identificacao/header.paciente.module';
import { PacienteService } from '../../paciente.service';
import { AvaliacaoService } from '../avaliacao.service';
import { PendenciaService } from '../pendencia.service';
import { PendenciaDialogoModule } from '../pendenciadialogo/pendencia.dialogo.module';
import { AvaliacaoAvaliadorComponent } from './avaliacao.avaliador.component';


@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule, AccordionModule,
              HeaderPacienteModule, RouterModule, MensagemModalModule, MensagemModule, PaginationModule, DiretivasModule, 
              PendenciaDialogoModule, DialogoModule, InputModule],
    declarations: [AvaliacaoAvaliadorComponent, ModalAprovarReprovarAvaliacaoComponent],
    entryComponents: [ModalAprovarReprovarAvaliacaoComponent],
    providers: [DominioService, PacienteService, AvaliacaoService, PendenciaService],
    exports: [AvaliacaoAvaliadorComponent]
  })
  export class AvaliacaoAvaliadorModule { }