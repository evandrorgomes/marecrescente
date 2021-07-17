import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { MensagemComponent } from '../../shared/mensagem/mensagem.component';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { HeaderPacienteModule } from '../consulta/identificacao/header.paciente.module';
import { DominioService } from '../../shared/dominio/dominio.service';
import { ArquivoExameService } from '../cadastro/exame/arquivo.exame.service';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { RouterModule } from '@angular/router';
import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { ExameService } from '../cadastro/exame/exame.service';
import { ConferenciaDetalheComponent } from './conferencia.detalhe.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { HlaModule } from '../../shared/hla/hla.module';
import { ConferenciaComponent } from './conferencia.component';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, FormsModule, MensagemModalModule, RouterModule,
             ExportTranslateModule, TextMaskModule, HeaderPacienteModule, ModalModule, MensagemModule, HlaModule,PaginationModule],
  declarations: [ConferenciaDetalheComponent, ConferenciaComponent],
  providers: [ExameService, ArquivoExameService, DominioService],
  exports: [ConferenciaDetalheComponent, ConferenciaComponent]
})
export class ConferenciaModule { }

  
