import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { DashboardModule } from 'app/dashboards/dashboard.module';
import { HistoricoNavegacao } from '../shared/historico.navegacao';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [CommonModule, DashboardModule], 
  declarations:[HomeComponent],
  providers: [HistoricoNavegacao],
  exports: [HomeComponent]
})
export class HomeModule {}
