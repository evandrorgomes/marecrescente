import {RouterModule} from "@angular/router";
import {AdminModule} from "../../admin/admin.module";
import {CommonModule} from "@angular/common";
import {MensagemModalModule} from "../../shared/modal/mensagem.modal.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {DiretivasModule} from "../../shared/diretivas/diretivas.module";
import {ModalModule, PaginationModule} from "ngx-bootstrap";
import {TextMaskModule} from "angular2-text-mask";
import {AutenticacaoModule} from "../../shared/autenticacao/autenticacao.module";
import {MensagemModule} from "../../shared/mensagem/mensagem.module";
import {InputModule} from "../../shared/component/inputcomponent/input.module";
import {ExportTranslateModule} from "../../shared/export.translate.module";
import {FiltroConsultaModule} from "../../admin/crud/consulta/filtro/filtro.consulta.module";
import {ConsultaInvoiceApagarComponent} from "./consulta/consulta.invoice.apagar.component";
import {BusyModule} from "angular2-busy";
import {UsuarioModule} from "../../admin/usuario/usuario.module";
import {NgModule} from "@angular/core";
import {CadastroInvoiceApagarComponent} from "./cadastro/cadastro.invoice.apagar.component";
import {DadosInvoiceApagarComponent} from "./cadastro/dadosinvoice/dados.invoice.apagar.component";
import {ItensInvoiceApagarComponent} from "./cadastro/itensinvoice/itens.invoice.apagar.component";
import {UploadArquivoModule} from "../../shared/upload/upload.arquivo.module";
import { ItemConcilicacaoModalComponent } from "./cadastro/itensinvoice/modalconciliacao/itemconciliacao.modal.component";
import { ModalItensNaoConciliadosComponent } from "./cadastro/itensinvoice/modalitensnaolocalizados/modal.itensnaoconciliados.component";


@NgModule({
  imports: [CommonModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule, MensagemModule, MensagemModalModule,
    BusyModule, DiretivasModule, PaginationModule, RouterModule,  AutenticacaoModule, TextMaskModule,
    InputModule, UsuarioModule, AdminModule, FiltroConsultaModule, UploadArquivoModule],
  declarations: [ConsultaInvoiceApagarComponent, CadastroInvoiceApagarComponent, DadosInvoiceApagarComponent, ItensInvoiceApagarComponent, ItemConcilicacaoModalComponent, ModalItensNaoConciliadosComponent],
  entryComponents: [ItemConcilicacaoModalComponent, ModalItensNaoConciliadosComponent],
  providers: [],
  exports: [ConsultaInvoiceApagarComponent, CadastroInvoiceApagarComponent, DadosInvoiceApagarComponent, ItensInvoiceApagarComponent]
})
export class InvoiceApagarModule { }

