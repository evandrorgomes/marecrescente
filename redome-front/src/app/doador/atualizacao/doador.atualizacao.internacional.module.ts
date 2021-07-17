import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ModalModule } from 'ngx-bootstrap';
import { HeaderPacienteModule } from '../../paciente/consulta/identificacao/header.paciente.module';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { HlaModule } from '../../shared/hla/hla.module';
import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { MockDominioService } from '../../shared/mock/mock.dominio.service';
import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { RessalvaModule } from '../cadastro/contato/ressalvas/ressalva.module';
import { AtualizacaoDoadorInternacionalComponent } from './atualizacao.doador.internacional.component';
import { DiretivasModule } from '../../shared/diretivas/diretivas.module';
import { AtualizacaoDoadorInternacionalExameComponent } from './atualizacao.doador.internacional.exame.component';

@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
    MensagemModule, MensagemModalModule, RessalvaModule, TextMaskModule, RessalvaModule, HlaModule,
    HeaderPacienteModule, DiretivasModule],
  declarations: [AtualizacaoDoadorInternacionalComponent, AtualizacaoDoadorInternacionalExameComponent],
  providers: [MockDominioService],
  exports: [AtualizacaoDoadorInternacionalComponent, AtualizacaoDoadorInternacionalExameComponent]
})
export class DoadorAtualizacaoInternacionalModule { }
