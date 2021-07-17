import { DiretivasModule } from '../../diretivas/diretivas.module';
import { MensagemModule } from '../../mensagem/mensagem.module';
import { MensagemModalModule } from '../../modal/mensagem.modal.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { TooltipModule, ModalModule, TypeaheadModule, BsDropdownModule } from 'ngx-bootstrap';
import { DadosPessoaisModule } from '../../../paciente/cadastro/dadospessoais/dadospessoais.module';
import { TextMaskModule } from "angular2-text-mask/src/angular2TextMask";
import { ExportTranslateModule } from "../../export.translate.module";
import { ContatoTelefoneComponent } from "./contato.telefone.component";
import { DominioService } from "../../dominio/dominio.service";

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, DiretivasModule, 
            TextMaskModule, ExportTranslateModule, ModalModule, MensagemModalModule, MensagemModule],
  declarations: [ContatoTelefoneComponent],
  providers: [DominioService],
  exports: [ContatoTelefoneComponent]
})
export class ContatoTelefoneModule { }

  
