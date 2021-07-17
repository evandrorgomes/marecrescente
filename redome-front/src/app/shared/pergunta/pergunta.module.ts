import { ExportTranslateModule } from '../export.translate.module';
import { ModalModule, AccordionModule } from 'ngx-bootstrap';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import {FormsModule } from "@angular/forms";
import { PerguntaComponente } from './pergunta.component';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';

@NgModule({
  imports: [CommonModule, ModalModule, FormsModule, ExportTranslateModule, AccordionModule, CurrencyMaskModule, TextMaskModule],
  declarations: [PerguntaComponente],
  providers: [],
  exports: [PerguntaComponente]
})
export class PerguntaModule { 

}

  
