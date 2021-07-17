import { MensagemModalModule } from './../../modal/mensagem.modal.module';
import { ModalModule } from 'ngx-bootstrap/modal/modal.module';
import { ExportTranslateModule } from './../../export.translate.module';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MensagemModule } from '../../mensagem/mensagem.module';
import { SelectCentrosComponent } from './select.centros.component';


@NgModule({
  imports: [CommonModule, FormsModule, TextMaskModule, ExportTranslateModule, 
            ModalModule, MensagemModalModule, MensagemModule],
  declarations: [SelectCentrosComponent],
  providers: [],
  exports: [SelectCentrosComponent]
})
export class SelectCentrosModule { }

  
