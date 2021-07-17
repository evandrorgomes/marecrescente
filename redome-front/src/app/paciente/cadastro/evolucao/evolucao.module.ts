import { CurrencyMaskModule } from 'ng2-currency-mask';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { EvolucaoComponent } from './evolucao.component';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EvolucaoService } from './evolucao.service';
import { TooltipModule, ModalModule, TypeaheadModule, BsDropdownModule } from 'ngx-bootstrap';
import { UploadArquivoModule } from '../../../shared/upload/upload.arquivo.module';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ExportTranslateModule } from 'app/shared/export.translate.module';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, MensagemModule, CurrencyMaskModule,UploadArquivoModule,
          TextMaskModule, ExportTranslateModule],
  declarations: [EvolucaoComponent],
  providers: [EvolucaoService],
  exports: [EvolucaoComponent]
})
export class EvolucaoModule { }

  