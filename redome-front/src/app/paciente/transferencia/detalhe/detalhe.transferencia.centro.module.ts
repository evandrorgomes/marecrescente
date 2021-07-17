import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AutenticacaoModule } from 'app/shared/autenticacao/autenticacao.module';
import { DiretivasModule } from 'app/shared/diretivas/diretivas.module';
import { ExportTranslateModule } from 'app/shared/export.translate.module';
import { MensagemModule } from 'app/shared/mensagem/mensagem.module';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { PedidoTransferenciaCentroService } from './../../../shared/service/pedido.transferencia.centro.service';
import { DetalheTransferenciaCentroComponent } from 'app/paciente/transferencia/detalhe/detalhe.transferencia.centro.component';
import { HeaderPacienteModule } from '../../consulta/identificacao/header.paciente.module';
import { ModalRecusarTransferenciaCentroComponent } from 'app/paciente/transferencia/detalhe/modal/modal.recusar.transferencia.centro.component';

@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, MensagemModule, PaginationModule, RouterModule,
        AutenticacaoModule, DiretivasModule, HeaderPacienteModule],
  declarations: [DetalheTransferenciaCentroComponent, ModalRecusarTransferenciaCentroComponent],
  entryComponents: [ModalRecusarTransferenciaCentroComponent],
  providers: [PedidoTransferenciaCentroService],
  exports: [DetalheTransferenciaCentroComponent]
})
export class DetalheTransferenciaCentroModule { }
