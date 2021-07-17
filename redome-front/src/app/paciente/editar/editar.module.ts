import { EditarDiagnosticoComponent } from './diagnostico/editar.diagnostico.component';
import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { EditarIdentificacaoDadosPessoaisComponent } from './identificacaodadospessoais/editar.identificacaodadospessoais.component';
import { DadosPessoaisModule } from '../cadastro/dadospessoais/dadospessoais.module';
import { IdentificacaoModule } from '../cadastro/identificacao/identificacao.module';
import { DiretivasModule } from '../../shared/diretivas/diretivas.module';
import { BrowserModule } from '@angular/platform-browser';
import { ContatoModule } from '../cadastro/contato/contato.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {ExportTranslateModule} from '../../shared/export.translate.module';
import { CadastroComponent } from '../cadastro/cadastro.component';
import { CadastroModule } from '../cadastro/cadastro.module';
import { ContatoPacienteComponent } from '../cadastro/contato/contato.paciente.component';
import {PacienteService} from '../paciente.service';
import {DominioService} from '../../shared/dominio/dominio.service';
import { EditarContatoPacienteComponent } from './editar.contato.paciente.component';
import {RouterModule} from '@angular/router';
import {HeaderPacienteModule} from '../consulta/identificacao/header.paciente.module';
import { NgModule } from "@angular/core";
import { TooltipModule, ModalModule, TypeaheadModule, BsDropdownModule } from 'ngx-bootstrap';
import { DiagnosticoService } from 'app/shared/service/diagnostico.service';
import { DiagnosticoModule } from '../cadastro/diagnostico/diagnostico.module';
import { AvaliacaoModule } from '../cadastro/avaliacao/avaliacao.module';
import { EditarAvaliacaoComponent } from 'app/paciente/editar/avaliacao/editar.avaliacao.component';

@NgModule({
  imports: [BrowserModule, CommonModule, FormsModule, ReactiveFormsModule, ExportTranslateModule, 
            ModalModule, HeaderPacienteModule, RouterModule, ContatoModule, TooltipModule, DiretivasModule,
          IdentificacaoModule, DadosPessoaisModule, MensagemModalModule, DiagnosticoModule, AvaliacaoModule],
  declarations: [EditarContatoPacienteComponent, EditarIdentificacaoDadosPessoaisComponent, EditarDiagnosticoComponent,
                 EditarAvaliacaoComponent],
  providers: [DominioService, PacienteService, DiagnosticoService],
  exports: [EditarContatoPacienteComponent, EditarIdentificacaoDadosPessoaisComponent, EditarDiagnosticoComponent,
            EditarAvaliacaoComponent],
})

export class EditarModule { }