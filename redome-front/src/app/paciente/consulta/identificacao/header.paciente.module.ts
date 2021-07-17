import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HeaderPacienteComponent } from './header.paciente.component';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';

@NgModule({
  imports: [BrowserModule, CommonModule, ExportTranslateModule,MensagemModalModule], 
  declarations: [HeaderPacienteComponent],
  providers: [],
  exports: [HeaderPacienteComponent]
})
export class HeaderPacienteModule {}