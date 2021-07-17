import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { MensagemModalComponente } from '../../shared/modal/mensagem.modal.component';
import { RetornarPaginaAnterior } from '../../shared/diretivas/retornar.pagina.anterior';
import { AutenticacaoModule } from '../../shared/autenticacao/autenticacao.module';
import { RouterModule } from '@angular/router';
import { DiretivasModule } from '../../shared/diretivas/diretivas.module';
import { BusyModule, BusyConfig } from 'angular2-busy';
import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";


import {PerfilComponent} from './perfil.component';
import {ConsultaPerfilComponent} from './consultar/consulta.perfil.component';

import { PerfilService } from './perfil.service';
import { SistemaService } from '../../shared/service/sistema.service';


@NgModule({
  imports: [CommonModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule, MensagemModule, MensagemModalModule, BusyModule, DiretivasModule, PaginationModule,  RouterModule,
  AutenticacaoModule],
  declarations: [PerfilComponent, ConsultaPerfilComponent],
  providers: [PerfilService, SistemaService], 
  exports: [PerfilComponent, ConsultaPerfilComponent]
})
export class PerfilModule { }
  
