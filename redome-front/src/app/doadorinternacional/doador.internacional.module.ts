import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {RouterModule} from '@angular/router';
import {ExportTranslateModule} from '../shared/export.translate.module';
import {ResultadoCTModule} from './cadastro/resultado/resultado.ct.module';
import {DoadorInternacionalComponent} from './doador.internacional.component';

@NgModule({
  imports: [CommonModule, RouterModule, FormsModule, ExportTranslateModule, ResultadoCTModule ],
  declarations: [DoadorInternacionalComponent],
  providers: [],
  exports: [DoadorInternacionalComponent]
})
export class DoadorInternacionalModule { }