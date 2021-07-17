import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { EnderecoContatoModule } from "app/shared/component/endereco/endereco.contato.module";
import { InputModule } from 'app/shared/component/inputcomponent/input.module';
import { VisualizarPrescricaoModule } from "app/shared/component/visualizar/prescricao/visualizar.prescricao.module";
import { ModalModule } from 'ngx-bootstrap';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { DetalhePedidoColetaModule } from '../../../../shared/component/detalhepedidocoleta/detalhe.pedido.coleta.module';
import { DiretivasModule } from '../../../../shared/diretivas/diretivas.module';
import { ExportTranslateModule } from '../../../../shared/export.translate.module';
import { MensagemModule } from '../../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../../shared/modal/mensagem.modal.module';
import { PedidoColetaService } from '../../../consulta/coleta/pedido.coleta.service';
import { HeaderDoadorModule } from '../../../consulta/header/header.doador.module';
import { AgendarPedidoColetaComponent } from './agendar.pedido.coleta.component';



@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, TextMaskModule, DetalhePedidoColetaModule,
      PaginationModule, DiretivasModule, HeaderDoadorModule, InputModule, EnderecoContatoModule, VisualizarPrescricaoModule],
    declarations: [AgendarPedidoColetaComponent],
    providers: [PedidoColetaService],
    exports: [AgendarPedidoColetaComponent]
  })
  export class AgendarPedidoColetaModule { }
