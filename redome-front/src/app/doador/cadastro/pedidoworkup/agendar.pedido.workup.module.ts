import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from "angular2-text-mask/src/angular2TextMask";
import { ModalAgendarPedidoWorkupComponent } from 'app/doador/cadastro/pedidoworkup/modal.agendar.pedido.workup.component';
import { HeaderPacienteModule } from 'app/paciente/consulta/identificacao/header.paciente.module';
import { DetalhePrescricaoModule } from 'app/shared/component/detalheprescricao/detalhe.prescricao.module';
import { PedidoWorkupService } from "app/shared/service/pedido.workup.service";
import { ModalModule } from 'ngx-bootstrap';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { HeaderDoadorModule } from '../../consulta/header/header.doador.module';
import { WorkupService } from '../../consulta/workup/workup.service';
import { DoadorDadosPessoaisModule } from '../contato/dadospessoais/doador.dadospessoais.module';
import { DoadorContatoModule } from '../contato/doador.contato.module';
import { DoadorIdentificacaoModule } from '../identificacao/doador.identificacao.module';
import { AgendarPedidoWorkupComponent } from './agendar.pedido.workup.component';
import { DefinirCentroColetaPedidoWorkupComponent } from './definir.centro.coleta.pedido.workup.component';
import { ModalCancelarPedidoWorkupComponent } from './modal.cancelar.pedido.workup.component';
import { VisualizarDadosCTComponent } from './visualizar.dadosct.component';
import { VisualizarDoadorComponent } from './visualizar.doador.component';


@NgModule({
    imports: [CommonModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
        MensagemModule, MensagemModalModule, TextMaskModule, HeaderDoadorModule, HeaderPacienteModule, TextMaskModule,
        DoadorIdentificacaoModule, DetalhePrescricaoModule, DoadorDadosPessoaisModule,DoadorContatoModule],
    declarations: [AgendarPedidoWorkupComponent, VisualizarDoadorComponent, VisualizarDadosCTComponent,
        ModalAgendarPedidoWorkupComponent,  ModalCancelarPedidoWorkupComponent, DefinirCentroColetaPedidoWorkupComponent],
    entryComponents: [ModalAgendarPedidoWorkupComponent,
        ModalCancelarPedidoWorkupComponent],
    providers: [WorkupService, DominioService, PedidoWorkupService],
    exports: [AgendarPedidoWorkupComponent, VisualizarDoadorComponent, VisualizarDadosCTComponent, DefinirCentroColetaPedidoWorkupComponent]
  })
  export class AgendarPedidoWorkupModule { }
