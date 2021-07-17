import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ModalModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../../../shared/export.translate.module';
import { MensagemModule } from '../../../../shared/mensagem/mensagem.module';
import { UploadArquivoModule } from '../../../../shared/upload/upload.arquivo.module';
import { ResultadoPedidoIdmComponent } from './resultado.pedido.idm.component';
import { HeaderDoadorInternacionalModule } from 'app/doadorinternacional/identificacao/header.doador.internacional.module';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, ExportTranslateModule, ModalModule, 
            RouterModule, MensagemModule, UploadArquivoModule, HeaderDoadorInternacionalModule],
  declarations: [ResultadoPedidoIdmComponent],
  providers: [],
  exports: [ResultadoPedidoIdmComponent]
})
export class ResultadoPedidoIdmModule { }

  