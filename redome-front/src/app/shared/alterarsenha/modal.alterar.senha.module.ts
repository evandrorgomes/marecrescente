import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { ModalModule } from 'ngx-bootstrap/modal/modal.module';
import { ModalAlterarSenhaComponent } from './modal.alterar.senha.component';
import { ExportTranslateModule } from 'app/shared/export.translate.module';
import { InputModule } from '../component/inputcomponent/input.module';


@NgModule({
  imports: [CommonModule, ReactiveFormsModule, ExportTranslateModule, 
            ModalModule, InputModule],
  declarations: [ModalAlterarSenhaComponent],
  entryComponents: [ModalAlterarSenhaComponent],
  providers: [],
  exports: [ModalAlterarSenhaComponent]
})
export class ModalAlterarSenhaModule { }

  
