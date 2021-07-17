import { GroupCheckBoxComponent } from './group.checkbox.component';
import { SelectComponent } from './select.component';
import { InputComponent } from './input.component';
import { MensagemModalModule } from './../../modal/mensagem.modal.module';
import { ModalModule } from 'ngx-bootstrap/modal/modal.module';
import { ExportTranslateModule } from './../../export.translate.module';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MensagemModule } from '../../mensagem/mensagem.module';
import { GroupRadioComponent } from './group.radio.component';
import { TextAreaComponent } from 'app/shared/component/inputcomponent/textarea.component';
import { InputNumberComponent } from 'app/shared/component/inputcomponent/input.number.component';
import { CurrencyMaskModule } from 'ng2-currency-mask';


@NgModule({
  imports: [CommonModule, ReactiveFormsModule, TextMaskModule, ExportTranslateModule, 
            ModalModule, MensagemModalModule, MensagemModule, CurrencyMaskModule],
  declarations: [InputComponent, SelectComponent, GroupCheckBoxComponent, GroupRadioComponent, TextAreaComponent, InputNumberComponent],
  providers: [],
  exports: [InputComponent, SelectComponent, GroupCheckBoxComponent, GroupRadioComponent, TextAreaComponent, InputNumberComponent]
})
export class InputModule { }

  
