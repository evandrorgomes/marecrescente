import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { RessalvaModule } from '../cadastro/contato/ressalvas/ressalva.module';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { HlaModule } from '../../shared/hla/hla.module';
import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { MockDoadorService } from '../../shared/mock/mock.doador.service';
import { MockDominioService } from '../../shared/mock/mock.dominio.service';
import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { ConsultaDoadorInternacionalComponent } from './consulta.doador.internacional.component';

@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
    MensagemModule, MensagemModalModule, 
    RessalvaModule, TextMaskModule, RessalvaModule, HlaModule, PaginationModule],
  declarations: [ConsultaDoadorInternacionalComponent],
  providers: [MockDominioService, MockDoadorService],
  exports: [ConsultaDoadorInternacionalComponent]
})
export class ConsultaDoadorInternacionalModule { }
