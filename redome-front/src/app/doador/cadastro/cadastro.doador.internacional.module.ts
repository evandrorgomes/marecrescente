import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { ModalModule } from 'ngx-bootstrap';
import { PedidoExameInternacionalModule } from '../../paciente/busca/pedidoexame/doadorInternacional/pedido.exame.internacional.module';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { HlaModule } from '../../shared/hla/hla.module';
import { LocusModule } from '../../shared/hla/locus/locus.module';
import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { CordaoInternacionalService } from "../../shared/service/cordao.internacional.service";
import { DoadorInternacionalService } from "../../shared/service/doador.internacional.service";
import { CadastroDoadorInternacionalComponent } from './cadastro.doador.internacional.component';
import { RessalvaModule } from './contato/ressalvas/ressalva.module';
import { CadastroComponent } from 'app/paciente/cadastro/cadastro.component';

@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
    MensagemModule, MensagemModalModule, CurrencyMaskModule, LocusModule,
    RessalvaModule, TextMaskModule, HlaModule, PedidoExameInternacionalModule, CurrencyMaskModule],
  declarations: [CadastroDoadorInternacionalComponent],
  entryComponents: [],
  providers: [DoadorInternacionalService, CordaoInternacionalService],
  exports: [CadastroDoadorInternacionalComponent]
})
export class CadastroDoadorInternacionalModule { }
