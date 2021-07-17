import { AvaliacaoPedidoColetaComponent } from './avaliacao.pedido.coleta.component';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { RouterModule } from '@angular/router';
import { BusyModule, BusyConfig } from 'angular2-busy';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { DoadorService } from '../../../doador.service';
import { DiretivasModule } from '../../../../shared/diretivas/diretivas.module';
import { ExportTranslateModule } from "../../../../shared/export.translate.module";
import { MensagemModalModule } from "../../../../shared/modal/mensagem.modal.module";
import { HeaderDoadorModule } from "../../header/header.doador.module";
import { MensagemModule } from "../../../../shared/mensagem/mensagem.module";
import { TarefaService } from '../../../../shared/tarefa.service';

@NgModule({
  imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
    MensagemModule, MensagemModalModule, ModalModule, MensagemModule, DiretivasModule, 
    HeaderDoadorModule, RouterModule, TextMaskModule,PaginationModule],
  declarations: [AvaliacaoPedidoColetaComponent],
  providers: [TarefaService],
  exports: [AvaliacaoPedidoColetaComponent]
})
export class AvaliacaoPedidoColetaModule { }
