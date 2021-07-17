import { CommonModule } from "@angular/common";
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { ModalModule } from "ngx-bootstrap";
import { InputModule } from "../../shared/component/inputcomponent/input.module";
import { AutoCollapsedMenuModule } from "../../shared/component/menu/auto.collapsed.menu.module";
import { ContatoTelefoneModule } from '../../shared/component/telefone/contato.telefone.module';
import { ExportTranslateModule } from "../../shared/export.translate.module";
import { MensagemModule } from "../../shared/mensagem/mensagem.module";
import { CourierService } from '../../transportadora/courier.service';
import { FiltroConsultaModule } from "../crud/consulta/filtro/filtro.consulta.module";
import { CourierConsultaComponent } from './consulta/consulta.courier.component';
import { DetalheCourierComponent } from './detalhe/detalhe.courier.component';
import { DiretivasModule } from "../../shared/diretivas/diretivas.module";
import { EmailContatoModule } from "../../shared/component/email/email.contato.module";

@NgModule({
    imports: [CommonModule, RouterModule, ReactiveFormsModule, FormsModule, ExportTranslateModule,ModalModule, MensagemModule
      ,InputModule, FiltroConsultaModule,AutoCollapsedMenuModule,ContatoTelefoneModule,DiretivasModule,EmailContatoModule],
    declarations: [CourierConsultaComponent, DetalheCourierComponent],
    providers: [CourierService],
    exports: [CourierConsultaComponent, DetalheCourierComponent]
  })
  export class CourierModule { }