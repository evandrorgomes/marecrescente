import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { FiltroConsultaModule } from 'app/admin/crud/consulta/filtro/filtro.consulta.module';
import { ConsultaLaboratorioComponent } from 'app/admin/laboratorio/consulta/consulta.laboratorio.component';
import { DetalheLaboratorioComponent } from 'app/admin/laboratorio/detalhe/detalhe.laboratorio.component';
import { InputModule } from 'app/shared/component/inputcomponent/input.module';
import { AutoCollapsedMenuModule } from 'app/shared/component/menu/auto.collapsed.menu.module';
import { DiretivasModule } from 'app/shared/diretivas/diretivas.module';
import { MensagemModule } from 'app/shared/mensagem/mensagem.module';
import { LaboratorioService } from 'app/shared/service/laboratorio.service';
import { TransporteMaterialModule } from 'app/transportadora/tarefas/transporte.material.module';
import { ModalModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../shared/export.translate.module';
import { LaboratorioComponent } from './laboratorio.component';
import { LaboratorioExameModule } from './laboratorioexame/laboratorio.exame.module';
import { PedidoExameService } from './pedido.exame.service';


@NgModule({
    imports: [CommonModule, RouterModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule, MensagemModule
      , TransporteMaterialModule, InputModule, FiltroConsultaModule, AutoCollapsedMenuModule, DiretivasModule,
      LaboratorioExameModule, TextMaskModule],
    declarations: [LaboratorioComponent, ConsultaLaboratorioComponent, DetalheLaboratorioComponent],
    providers: [PedidoExameService, LaboratorioService],
    exports: [LaboratorioComponent, ConsultaLaboratorioComponent, DetalheLaboratorioComponent]
  })
  export class LaboratorioModule { }