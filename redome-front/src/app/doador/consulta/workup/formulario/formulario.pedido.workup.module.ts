import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { DoadorAtualizacaoModule } from 'app/doador/atualizacao/doador.atualizacao.module';
import { DoadorService } from 'app/doador/doador.service';
import { InputModule } from 'app/shared/component/inputcomponent/input.module';
import { DiretivasModule } from 'app/shared/diretivas/diretivas.module';
import { ExportTranslateModule } from 'app/shared/export.translate.module';
import { MensagemModule } from 'app/shared/mensagem/mensagem.module';
import { MensagemModalModule } from 'app/shared/modal/mensagem.modal.module';
import { QuestionarioModule } from 'app/shared/questionario/questionario.module';
import { FormularioService } from 'app/shared/service/formulario.service';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { HeaderDoadorModule } from '../../header/header.doador.module';
import { WorkupService } from '../workup.service';
import { VisualizaFormularioPedidoWorkupComponent } from './visualiza.formulario.pedido.workup.component';
import {VisualizarPrescricaoModule} from "../../../../shared/component/visualizar/prescricao/visualizar.prescricao.module";

@NgModule({
  imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
    MensagemModule, MensagemModalModule, ModalModule, MensagemModule, DiretivasModule, DoadorAtualizacaoModule,
    RouterModule, TextMaskModule, PaginationModule, QuestionarioModule, InputModule,
    HeaderDoadorModule, VisualizarPrescricaoModule],
  declarations: [VisualizaFormularioPedidoWorkupComponent],
  providers: [DoadorService, FormularioService, WorkupService],
  exports: [VisualizaFormularioPedidoWorkupComponent],
  entryComponents: [],
})
export class FormularioPedidoWorkupModule { }
