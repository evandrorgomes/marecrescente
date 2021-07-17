import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ExportTranslateModule } from 'app/shared/export.translate.module';
import { MensagemModule } from 'app/shared/mensagem/mensagem.module';
import { MensagemModalModule } from 'app/shared/modal/mensagem.modal.module';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { DoadorService } from '../doador.service';
import { ConsultaDoadorNacionalComponent } from './consulta.doador.nacional.component';
import { LogisticaModule } from '../cadastro/logistica/logistica.module';


@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
    MensagemModule, MensagemModalModule, TextMaskModule, PaginationModule, LogisticaModule],
    declarations: [ConsultaDoadorNacionalComponent],
    providers: [DoadorService],
    exports: [ConsultaDoadorNacionalComponent]
  })
  export class ConsultaDoadorNacionalModule { }
