import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { BusyModule } from "angular2-busy";
import { ModalModule, PaginationModule } from "ngx-bootstrap";
import { AutenticacaoModule } from "../../shared/autenticacao/autenticacao.module";
import { EnderecoContatoModule } from "../../shared/component/endereco/endereco.contato.module";
import { InputModule } from "../../shared/component/inputcomponent/input.module";
import { ContatoTelefoneModule } from "../../shared/component/telefone/contato.telefone.module";
import { DiretivasModule } from "../../shared/diretivas/diretivas.module";
import { ExportTranslateModule } from "../../shared/export.translate.module";
import { MensagemModule } from "../../shared/mensagem/mensagem.module";
import { MensagemModalModule } from "../../shared/modal/mensagem.modal.module";
import { EnderecoContatoCentroTransplanteService } from "../../shared/service/endereco.contato.centro.transplante.service";
import { CentroTransplanteService } from "./centrotransplante.service";
import { ConsultaCentroTransplanteComponent } from "./consultar/consulta.centrotransplante.component";
import { DetalheCentroTransplanteComponent } from "./detalhe/detalhe.centrotransplante.component";
import { FiltroConsultaModule } from "../crud/consulta/filtro/filtro.consulta.module";
import { TextMaskModule } from "angular2-text-mask/src/angular2TextMask";
import { EmailContatoModule } from "app/shared/component/email/email.contato.module";

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule, MensagemModule, MensagemModalModule,
            BusyModule, DiretivasModule, PaginationModule, RouterModule,  AutenticacaoModule, EmailContatoModule,
            InputModule, EnderecoContatoModule, ContatoTelefoneModule, FiltroConsultaModule, TextMaskModule],
  declarations: [ ConsultaCentroTransplanteComponent, DetalheCentroTransplanteComponent],
  entryComponents: [],
  providers: [CentroTransplanteService, EnderecoContatoCentroTransplanteService],
  exports: [ConsultaCentroTransplanteComponent, DetalheCentroTransplanteComponent]
})
export class CentroTransplanteModule { }