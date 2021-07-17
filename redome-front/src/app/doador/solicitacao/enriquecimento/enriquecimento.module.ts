import { DoadorContatoModule } from '../../cadastro/contato/doador.contato.module';
import { DoadorIdentificacaoModule } from '../../cadastro/identificacao/doador.identificacao.module';
import { EnriquecimentoDoadorComponent } from './enriquecimento.doador.component';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { ModalModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { HeaderDoadorModule } from '../../consulta/header/header.doador.module';
import { InativacaoModule } from '../../inativacao/inativacao.module';
import { PedidoEnriquecimentoService } from '../../../shared/service/pedido.enriquecimento.service';


@NgModule({
    imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule,ReactiveFormsModule,
      MensagemModule, MensagemModalModule, ModalModule, MensagemModule, DoadorIdentificacaoModule,
      DoadorContatoModule, HeaderDoadorModule, InativacaoModule ],
    declarations: [EnriquecimentoDoadorComponent],
    providers: [PedidoEnriquecimentoService],
    exports: [EnriquecimentoDoadorComponent]
  })
  export class EnriquecimentoModule { }