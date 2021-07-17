import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MensagemComponent } from './mensagem.component';
import { RequiredMensagemComponent } from './required.mensagem.component';

@NgModule({
  imports: [BrowserModule, CommonModule], 
  declarations: [MensagemComponent, RequiredMensagemComponent],
  providers: [],
  exports: [MensagemComponent, RequiredMensagemComponent]
})
export class MensagemModule {}