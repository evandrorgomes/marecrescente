import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { PedidoExameService } from '../pedido.exame.service';
import { LaboratorioExameComponent } from './laboratorio.exame.component';
import { DiretivasModule } from '../../shared/diretivas/diretivas.module';
import { AutenticacaoModule } from '../../shared/autenticacao/autenticacao.module';
import { BusyModule } from 'angular2-busy';
import { RouterModule } from '@angular/router';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { LaboratorioExameReceberModal } from '../receber/laboratorio.exame.receber.modal';


@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, MensagemModule, 
    MensagemModalModule, BusyModule, PaginationModule, RouterModule, TextMaskModule,
    AutenticacaoModule, DiretivasModule, PaginationModule, ReactiveFormsModule],
  declarations: [LaboratorioExameComponent, LaboratorioExameReceberModal],
  entryComponents: [LaboratorioExameReceberModal],
  providers: [PedidoExameService],
  exports: [LaboratorioExameComponent]
})
export class LaboratorioExameModule { }