import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ModalModule } from 'ngx-bootstrap';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { PedidoExameInternacionalComponent } from '../pedido.exame.internacional.component';
import { ExportTranslateModule } from '../../../../../shared/export.translate.module';
import { MensagemModule } from '../../../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../../../shared/modal/mensagem.modal.module';
import { HlaModule } from '../../../../../shared/hla/hla.module';
import { CancelarPedidoExameFase3Component } from './cancelar.pedido.exame.fase3.component';
import { InativacaoModule } from '../../../../../doador/inativacao/inativacao.module';
import { SolicitacaoService } from '../../../../../doador/solicitacao/solicitacao.service';

@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
    MensagemModule, MensagemModalModule, CurrencyMaskModule, TextMaskModule, InativacaoModule],
  declarations: [CancelarPedidoExameFase3Component],
  providers: [SolicitacaoService],
  entryComponents: [CancelarPedidoExameFase3Component],
  exports: [CancelarPedidoExameFase3Component]
})
export class CancelarPedidoExameFase3Module { }
