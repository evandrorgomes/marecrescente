import { PedidoTransferenciaCentroService } from './../../../shared/service/pedido.transferencia.centro.service';
import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BusyModule } from 'angular2-busy';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from 'app/shared/export.translate.module';
import { MensagemModule } from 'app/shared/mensagem/mensagem.module';
import { DiretivasModule } from 'app/shared/diretivas/diretivas.module';
import { AutenticacaoModule } from 'app/shared/autenticacao/autenticacao.module';
import { ConsultaTransferenciaCentroComponent } from 'app/paciente/transferencia/consulta/consulta.transferencia.centro.component';
import { SelectCentrosModule } from '../../../shared/component/selectcentros/select.centros.module';



@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, MensagemModule, PaginationModule, RouterModule,
        AutenticacaoModule, DiretivasModule, SelectCentrosModule],
  declarations: [ConsultaTransferenciaCentroComponent],
  providers: [PedidoTransferenciaCentroService],
  exports: [ConsultaTransferenciaCentroComponent]
})
export class ConsultaTransferenciaCentroModule { }
