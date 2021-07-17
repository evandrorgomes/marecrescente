import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { TextMaskModule } from "angular2-text-mask/src/angular2TextMask";
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { ModalModule } from "ngx-bootstrap/modal/modal.module";
import { PaginationModule } from 'ngx-bootstrap/pagination/pagination.module';
import { EmailContatoModule } from "../../shared/component/email/email.contato.module";
import { InputModule } from '../../shared/component/inputcomponent/input.module';
import { ContatoTelefoneModule } from "../../shared/component/telefone/contato.telefone.module";
import { DiretivasModule } from "../../shared/diretivas/diretivas.module";
import { ExportTranslateModule } from "../../shared/export.translate.module";
import { HlaModule } from "../../shared/hla/hla.module";
import { MensagemModule } from "../../shared/mensagem/mensagem.module";
import { MensagemModalModule } from "../../shared/modal/mensagem.modal.module";
import { DoadorDadosPessoaisModule } from "../cadastro/contato/dadospessoais/doador.dadospessoais.module";
import { DoadorContatoModule } from "../cadastro/contato/doador.contato.module";
import { RessalvaModule } from "../cadastro/contato/ressalvas/ressalva.module";
import { DoadorIdentificacaoModule } from "../cadastro/identificacao/doador.identificacao.module";
import { HeaderDoadorModule } from "../consulta/header/header.doador.module";
import { DoadorService } from "../doador.service";
import { HeaderDoadorInternacionalModule } from './../../doadorinternacional/identificacao/header.doador.internacional.module';
import { InativacaoModule } from './../inativacao/inativacao.module';
import { DoadorAtualizacaoComponent } from "./doador.atualizacao.component";
import { ModalInativarDoadorInternacionalComponent } from "./inativar.doador.internacional.modal.component";
import { CadastroResultadoExameInternacionalComponent } from './visualizar/exame/resultado/cadastrar.resultado.exame.internacional.component';
import { VisualizarDoadorInternacionalComponent } from './visualizar/visualizar.doador.internacional.component';
import { VisualizarDoadorInternacionalExameComponent } from "./visualizar/visualizar.doador.internacional.exame.component";


@NgModule({
  imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, ModalModule, PaginationModule, ContatoTelefoneModule, HeaderDoadorModule, DoadorIdentificacaoModule,
      EmailContatoModule, DoadorContatoModule, DoadorDadosPessoaisModule, TextMaskModule, HlaModule, RessalvaModule, DiretivasModule,InativacaoModule,CurrencyMaskModule,InputModule, HeaderDoadorInternacionalModule],
  declarations: [DoadorAtualizacaoComponent, VisualizarDoadorInternacionalExameComponent,
    ModalInativarDoadorInternacionalComponent, VisualizarDoadorInternacionalComponent, CadastroResultadoExameInternacionalComponent],
  providers: [DoadorService],
  entryComponents: [ModalInativarDoadorInternacionalComponent],
  exports: [DoadorAtualizacaoComponent, VisualizarDoadorInternacionalExameComponent,ModalInativarDoadorInternacionalComponent
    ,VisualizarDoadorInternacionalComponent, CadastroResultadoExameInternacionalComponent]
})
export class DoadorAtualizacaoModule { }
