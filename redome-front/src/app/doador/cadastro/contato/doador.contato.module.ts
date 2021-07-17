import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { EmailContatoService } from '../../../shared/service/email.contato.service';
import { EnderecoContatoService } from '../../../shared/service/endereco.contato.service';
import { EnderecoContatoModule } from '../../../shared/component/endereco/endereco.contato.module';
import { ContatoTelefoneModule } from '../../../shared/component/telefone/contato.telefone.module';
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { ModalModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { DoadorContatoTelefoneComponent } from './telefone/doador.contato.telefone.component';
import { DoadorContatoEnderecoComponent } from './endereco/doador.contato.endereco.component';
import { ContatoModule } from '../../../paciente/cadastro/contato/contato.module';
import { DoadorEmailContatoComponent } from './email/doador.contato.email.component';
import { AutenticacaoService } from "../../../shared/autenticacao/autenticacao.service";
import { EmailContatoModule } from '../../../shared/component/email/email.contato.module';
import { DoadorDadosPessoaisModule } from "./dadospessoais/doador.dadospessoais.module";


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule, TextMaskModule,
      MensagemModule, MensagemModalModule, EnderecoContatoModule, ContatoTelefoneModule, EmailContatoModule],
    declarations: [DoadorContatoTelefoneComponent, DoadorContatoEnderecoComponent, DoadorEmailContatoComponent],
    providers: [AutenticacaoService, EnderecoContatoService, EmailContatoService],
    exports: [DoadorContatoTelefoneComponent, DoadorContatoEnderecoComponent, DoadorEmailContatoComponent]
  })
  export class DoadorContatoModule { }