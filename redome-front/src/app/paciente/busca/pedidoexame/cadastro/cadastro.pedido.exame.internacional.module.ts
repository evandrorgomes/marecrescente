import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ModalModule } from 'ngx-bootstrap';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { ExportTranslateModule } from '../../../../shared/export.translate.module';
import { MensagemModule } from '../../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../../shared/modal/mensagem.modal.module';
import { HlaModule } from '../../../../shared/hla/hla.module';
import { LocusModule } from '../../../../shared/hla/locus/locus.module';
import { CadastroPedidoExameInternacionalComponent } from './cadastro.pedido.exame.internacional.component';
import { InativacaoModule } from '../../../../doador/inativacao/inativacao.module';

@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
    MensagemModule, MensagemModalModule, CurrencyMaskModule,
    TextMaskModule, HlaModule, LocusModule, InativacaoModule],
  declarations: [CadastroPedidoExameInternacionalComponent],
  providers: [],
  exports: [CadastroPedidoExameInternacionalComponent]
})
export class CadastroPedidoExameInternacionalModule { }
