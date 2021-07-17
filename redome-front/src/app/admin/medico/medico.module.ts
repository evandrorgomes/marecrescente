import { ConsultaMedicoComponent } from 'app/admin/medico/consulta/consulta.medico.component';
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { ExportTranslateModule } from "../../shared/export.translate.module";
import { ModalModule, PaginationModule } from "ngx-bootstrap";
import { MensagemModule } from "../../shared/mensagem/mensagem.module";
import { MensagemModalModule } from "../../shared/modal/mensagem.modal.module";
import { BusyModule } from "angular2-busy";
import { DiretivasModule } from "../../shared/diretivas/diretivas.module";
import { RouterModule } from "@angular/router";
import { AutenticacaoModule } from "../../shared/autenticacao/autenticacao.module";
import { InputModule } from "../../shared/component/inputcomponent/input.module";
import { EnderecoContatoModule } from "../../shared/component/endereco/endereco.contato.module";
import { ContatoTelefoneModule } from "../../shared/component/telefone/contato.telefone.module";
import { MedicoService } from "app/shared/service/medico.service";
import { DetalheMedicoComponent } from 'app/admin/medico/detalhe/detalhe.medico.component';
import { EmailContatoModule } from '../../shared/component/email/email.contato.module';
import { EnderecoContatoMedicoService } from 'app/shared/service/endereco.contato.medico.service';
import { FiltroConsultaModule } from '../crud/consulta/filtro/filtro.consulta.module';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule, MensagemModule, MensagemModalModule,
            BusyModule, DiretivasModule, PaginationModule, RouterModule,  AutenticacaoModule,
            InputModule, EnderecoContatoModule, ContatoTelefoneModule, EmailContatoModule, FiltroConsultaModule],
  declarations: [ ConsultaMedicoComponent, DetalheMedicoComponent ],
  entryComponents: [],
  providers: [ MedicoService, EnderecoContatoMedicoService ],
  exports: [ ConsultaMedicoComponent, DetalheMedicoComponent ]
})
export class MedicoModule { }

