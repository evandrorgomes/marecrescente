import {CurrencyMaskModule} from 'ng2-currency-mask';
import { DominioService } from '../../../../shared/dominio/dominio.service';
import {MensagemModalModule} from '../../../../shared/modal/mensagem.modal.module';
import {MensagemModule} from '../../../../shared/mensagem/mensagem.module';
import { ModalModule } from 'ngx-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { AutenticacaoService } from "../../../../shared/autenticacao/autenticacao.service";
import { TextMaskModule } from "angular2-text-mask/src/angular2TextMask";
import { DoadorDadosPessoaisComponent } from "./doador.dadospessoais.component";
import { ExportTranslateModule } from "../../../../shared/export.translate.module";


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, TextMaskModule, CurrencyMaskModule],
    declarations: [DoadorDadosPessoaisComponent],
    providers: [AutenticacaoService, DominioService],
    exports: [DoadorDadosPessoaisComponent]
  })
  export class DoadorDadosPessoaisModule { }