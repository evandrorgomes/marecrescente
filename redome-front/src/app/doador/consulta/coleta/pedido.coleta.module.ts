import { DetalhePedidoColetaModule } from '../../../shared/component/detalhepedidocoleta/detalhe.pedido.coleta.module';
import { PedidoColetaComponent } from './pedido.coleta.component';
import { PedidoColetaService } from './pedido.coleta.service';
import { HeaderDoadorModule } from '../header/header.doador.module';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import {DiretivasModule} from '../../../shared/diretivas/diretivas.module';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { ModalModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { CancelarAgendamentoPedidoColetaComponent } from './cancelaragendamento/cancelar.agendamento.component';
import { DetalheCancelarAgendamentoPedidoColetaComponent } from './cancelaragendamento/detalhe/detalhe.cancelar.agendamento.component';
import { MockPedidoColetaService } from '../../../shared/mock/mock.pedido.coleta.service';
import { TarefaService } from '../../../shared/tarefa.service';
import { MockTarefaService } from '../../../shared/mock/mock.tarefa.service';
import { WorkupService } from '../workup/workup.service';
import { AlertaModalComponente } from '../../../shared/modal/factory/alerta.modal.component';
import { ConfirmacaoModalComponente } from '../../../shared/modal/factory/confirmacao.modal.component';
import { PedidoColetaInternacionalComponent } from './pedido.coleta.internacional.component';
import { ConfirmarAgendamentoColetaComponent } from './centrotransplante/confirmar.agendamento.coleta.component';
import { HeaderPacienteModule } from '../../../paciente/consulta/identificacao/header.paciente.module';

@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, TextMaskModule, HeaderPacienteModule,
      PaginationModule, DiretivasModule, HeaderDoadorModule, DetalhePedidoColetaModule],
    entryComponents: [AlertaModalComponente, ConfirmacaoModalComponente],
    declarations: [PedidoColetaComponent, CancelarAgendamentoPedidoColetaComponent, DetalheCancelarAgendamentoPedidoColetaComponent, PedidoColetaInternacionalComponent, ConfirmarAgendamentoColetaComponent],
    providers: [PedidoColetaService, WorkupService, TarefaService, TarefaService],
    exports: [PedidoColetaComponent, CancelarAgendamentoPedidoColetaComponent, DetalheCancelarAgendamentoPedidoColetaComponent, PedidoColetaInternacionalComponent, ConfirmarAgendamentoColetaComponent]
  })
  export class PedidoColetaModule { }