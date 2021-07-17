import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HeaderDoadorModule } from 'app/doador/consulta/header/header.doador.module';
import { InputModule } from 'app/shared/component/inputcomponent/input.module';
import { DiretivasModule } from 'app/shared/diretivas/diretivas.module';
import { ExportFileUploaderDirectiveModule } from 'app/shared/export.file.uploader.directive.module';
import { ExportTranslateModule } from 'app/shared/export.translate.module';
import { MensagemModule } from 'app/shared/mensagem/mensagem.module';
import { MensagemModalModule } from 'app/shared/modal/mensagem.modal.module';
import { PedidoWorkupService } from 'app/shared/service/pedido.workup.service';
import { UploadArquivoModule } from 'app/shared/upload/upload.arquivo.module';
import { ModalModule } from 'ngx-bootstrap';
import { CadastroPedidoAdicionalWorkupComponent } from './cadastro.pedido.adicional.workup.component';

@NgModule({

  imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule, DiretivasModule,
    MensagemModule, MensagemModalModule, HeaderDoadorModule, InputModule, UploadArquivoModule, ExportFileUploaderDirectiveModule],
  declarations: [CadastroPedidoAdicionalWorkupComponent],
  entryComponents: [],
  providers: [PedidoWorkupService],
  exports: [CadastroPedidoAdicionalWorkupComponent]
})
export class PedidoAdicionalWorkupModule { }
