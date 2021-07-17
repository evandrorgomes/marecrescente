import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { TextMaskModule } from "angular2-text-mask/src/angular2TextMask";
import { ExportTranslateModule } from "app/shared/export.translate.module";
import { MensagemModule } from "app/shared/mensagem/mensagem.module";
import { MensagemModalModule } from "app/shared/modal/mensagem.modal.module";
import { LogisticaService } from "app/shared/service/logistica.service";
import { PedidoTransporteService } from "app/transportadora/pedido.transporte.service";
import { ModalModule, PaginationModule } from "ngx-bootstrap";
import { ConsultaLogisticaMaterialComponent } from "./material/consulta.logistica.material.component";


  @NgModule({
    imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, TextMaskModule, PaginationModule],
      declarations: [ConsultaLogisticaMaterialComponent],
      providers: [LogisticaService, PedidoTransporteService],
      exports: [ConsultaLogisticaMaterialComponent]
    })
    export class ConsultaLogisticaModule { }
