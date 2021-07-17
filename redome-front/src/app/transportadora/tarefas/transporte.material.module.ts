import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from '@angular/router';
import { BusyModule } from 'angular2-busy/build';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ModalModule } from 'ngx-bootstrap/modal';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { AutenticacaoModule } from '../../shared/autenticacao/autenticacao.module';
import { DiretivasModule } from '../../shared/diretivas/diretivas.module';
import { ExportTranslateModule } from "../../shared/export.translate.module";
import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { CourierService } from '../courier.service';
import { ConsultaTransporteMaterialComponent } from './agendartransportematerial/consulta.transporte.material.component';
import { DetalheAgendarTransporteMaterialComponent } from './agendartransportematerial/detalhe.agendar.transporte.material.component';
import { ModalRetiradaEntregaTransporteMaterialComponent } from './retiradaentregatransportematerial/modal.retirada.entrega.transporte.material.component';
import { RetiradaEntregaTransporteMaterialComponent } from './retiradaentregatransportematerial/retirada.entrega.transporte.material.component';
import { ModalRetiradaEntregaTransporteMaterialInternacionalComponent } from "./retiradaentregatransportematerialinter/modal.retirada.entrega.transporte.material.inter.component";
import { RetiradaEntregaTransporteMaterialInternacionalComponent } from "./retiradaentregatransportematerialinter/retirada.entrega.transporte.material.inter.component";



@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, MensagemModule, 
    MensagemModalModule, BusyModule, PaginationModule, RouterModule, TextMaskModule,
    AutenticacaoModule, DiretivasModule, PaginationModule, ReactiveFormsModule],
  declarations: [ConsultaTransporteMaterialComponent, DetalheAgendarTransporteMaterialComponent, RetiradaEntregaTransporteMaterialComponent
    ,RetiradaEntregaTransporteMaterialInternacionalComponent, ModalRetiradaEntregaTransporteMaterialComponent
    ,ModalRetiradaEntregaTransporteMaterialInternacionalComponent],
  entryComponents: [ModalRetiradaEntregaTransporteMaterialComponent,ModalRetiradaEntregaTransporteMaterialInternacionalComponent],
  providers: [CourierService],
  exports: [ConsultaTransporteMaterialComponent, DetalheAgendarTransporteMaterialComponent, RetiradaEntregaTransporteMaterialComponent
    ,RetiradaEntregaTransporteMaterialInternacionalComponent]
})
export class TransporteMaterialModule { }
