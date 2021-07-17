import { HistoricoNavegacao } from '../shared/historico.navegacao';
import { BusyModule } from 'angular2-busy';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { TesteComponent } from "./teste.component";

@NgModule({
  imports: [CommonModule], 
  declarations:[TesteComponent],
  providers: [HistoricoNavegacao],
  exports: [TesteComponent]
})
export class TesteModule {}
