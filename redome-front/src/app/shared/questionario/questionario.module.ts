import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { AccordionModule, ModalModule, PaginationModule, AlertModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../export.translate.module';
import { PerguntaModule } from '../pergunta/pergunta.module';
import { QuestionarioComponent } from './questionario.component';

@NgModule({
  imports: [AlertModule.forRoot(), CommonModule, ModalModule, FormsModule, ExportTranslateModule, PerguntaModule, AccordionModule, PaginationModule],
  declarations: [QuestionarioComponent],
  providers: [],
  exports: [QuestionarioComponent]
})
export class QuestionarioModule { 

}

  
