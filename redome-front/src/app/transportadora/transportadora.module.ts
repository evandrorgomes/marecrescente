import { ContatoTelefoneModule } from './../shared/component/telefone/contato.telefone.module';
import { EmailContatoModule } from './../shared/component/email/email.contato.module';
import { DetalheTransportadoraComponent } from './cadastro/detalhe/detalhe.transportadora.component';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TransportadoraComponent } from './transportadora.component';
import { TransporteMaterialModule } from './tarefas/transporte.material.module';
import { PedidoTransporteService } from './pedido.transporte.service';
import { TransportadoraCadastroConsultaComponent } from './cadastro/transportadora.cadastro.consulta.component';
import { ModalModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../shared/export.translate.module';
import { MensagemModule } from '../shared/mensagem/mensagem.module';
import { EnderecoContatoModule } from '../shared/component/endereco/endereco.contato.module';
import { DiretivasModule } from '../shared/diretivas/diretivas.module';
import { AutoCollapsedMenuModule } from '../shared/component/menu/auto.collapsed.menu.module';
import { FiltroConsultaModule } from '../admin/crud/consulta/filtro/filtro.consulta.module';
import { InputModule } from '../shared/component/inputcomponent/input.module';

@NgModule({
  imports: [CommonModule, RouterModule, ReactiveFormsModule, FormsModule, ExportTranslateModule,ModalModule, MensagemModule
    , TransporteMaterialModule, InputModule, FiltroConsultaModule,AutoCollapsedMenuModule, DiretivasModule
    , EnderecoContatoModule, EmailContatoModule, ContatoTelefoneModule],
  declarations: [TransportadoraComponent, TransportadoraCadastroConsultaComponent, DetalheTransportadoraComponent],
  providers: [PedidoTransporteService],
  exports: [TransportadoraComponent, TransportadoraCadastroConsultaComponent, DetalheTransportadoraComponent]
})
export class TransportadoraModule { }