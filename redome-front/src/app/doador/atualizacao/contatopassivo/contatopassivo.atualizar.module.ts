import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ExportTranslateModule } from "../../../shared/export.translate.module";
import { ModalModule } from "ngx-bootstrap/modal/modal.module";
import { MensagemModule } from "../../../shared/mensagem/mensagem.module";
import { MensagemModalModule } from "../../../shared/modal/mensagem.modal.module";
import { ContatoPassivoAtualizarComponent } from "./contatopassivo.atualizar.component";
import { DoadorService } from "../../doador.service";
import { DoadorAtualizacaoModule } from "../doador.atualizacao.module";
import { HeaderDoadorModule } from "../../consulta/header/header.doador.module";
import { AlteracaoStatusModule } from "../alteracaostatus/alteracaostatus.module";



@NgModule({
  imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
    MensagemModule, MensagemModalModule, ModalModule, DoadorAtualizacaoModule, HeaderDoadorModule, AlteracaoStatusModule],
  declarations: [ContatoPassivoAtualizarComponent],
  providers: [DoadorService],
  exports: [ContatoPassivoAtualizarComponent]
})
export class ContatoPassivoAtualizarModule { }
