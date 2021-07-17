import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FiltroConsulta } from 'app/admin/crud/consulta/filtro/filtro.consulta';

@NgModule({
  imports: [CommonModule],
  declarations: [FiltroConsulta],
  exports: [FiltroConsulta]
})
export class FiltroConsultaModule { }

