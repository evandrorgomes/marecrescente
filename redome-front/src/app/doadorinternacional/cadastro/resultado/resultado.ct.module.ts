import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { RouterModule } from '@angular/router';
import { InativacaoModule } from '../../../doador/inativacao/inativacao.module';
import { SolicitacaoService } from '../../../doador/solicitacao/solicitacao.service';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { HlaModule } from '../../../shared/hla/hla.module';
import { ResultadoCTComponent } from './resultado.ct.component';
import { UploadArquivoModule } from '../../../shared/upload/upload.arquivo.module';
import { PedidoExameService } from '../../../laboratorio/pedido.exame.service';
import { PedidoIdmService } from '../../../laboratorio/pedido.idm.service';
import { HeaderDoadorInternacionalModule } from 'app/doadorinternacional/identificacao/header.doador.internacional.module';

@NgModule({
  imports: [CommonModule, RouterModule, FormsModule, 
      ExportTranslateModule, HlaModule, InativacaoModule,
      UploadArquivoModule, HeaderDoadorInternacionalModule ],
  declarations: [ResultadoCTComponent],
  providers: [SolicitacaoService, PedidoExameService, PedidoIdmService],
  exports: [ResultadoCTComponent]
})
export class ResultadoCTModule { }