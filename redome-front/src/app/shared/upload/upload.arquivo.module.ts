import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ExportTranslateModule } from '../export.translate.module';
import { HlaComponent } from '../hla/hla.component';
import { MensagemModule } from '../mensagem/mensagem.module';
import { ModalModule } from 'ngx-bootstrap';
import { UploadArquivoComponent } from './upload.arquivo.component';
import { ExportFileUploaderDirectiveModule } from '../export.file.uploader.directive.module';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, ExportTranslateModule, ModalModule, 
            RouterModule, MensagemModule, ExportFileUploaderDirectiveModule],
  declarations: [UploadArquivoComponent],
  providers: [],
  exports: [UploadArquivoComponent]
})
export class UploadArquivoModule { }

  