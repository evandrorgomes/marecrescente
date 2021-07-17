import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BusyModule } from 'angular2-busy';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { AutenticacaoModule } from '../../../shared/autenticacao/autenticacao.module';
import { DiretivasModule } from '../../../shared/diretivas/diretivas.module';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { TarefaService } from '../../../shared/tarefa.service';
import { NotificacaoService } from '../../notificacao.service';
import { PacienteService } from '../../paciente.service';
import { SelectCentrosModule } from './../../../shared/component/selectcentros/select.centros.module';
import { ConsultarNotificacaoComponent } from './consultar.notificacao.component';
import { HeaderPacienteModule } from '../identificacao/header.paciente.module';


@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, MensagemModule, MensagemModalModule, BusyModule, PaginationModule, RouterModule,
            AutenticacaoModule, DiretivasModule, SelectCentrosModule, HeaderPacienteModule],
  declarations: [ConsultarNotificacaoComponent],
  providers: [PacienteService, TarefaService, NotificacaoService],
  exports: [ConsultarNotificacaoComponent]
})
export class ConsultaNotificacaoModule { }
