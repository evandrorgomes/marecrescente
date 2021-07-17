import { MedicoService } from './../../shared/service/medico.service';
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
import { ConsultaPreCadastroMedicoComponent } from "./consultar/consulta.precadastro.medico.component";
import { DetalhePreCadastroMedicoComponent } from 'app/admin/precadastromedico/detalhe/detalhe.precadastro.medico.component';
import { ModalReprovarPreCadastroMedicoComponent } from 'app/admin/precadastromedico/detalhe/reprovar/modal.reprovar.precadastro.medico.component';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule, MensagemModule, MensagemModalModule,
            BusyModule, DiretivasModule, PaginationModule, RouterModule,  AutenticacaoModule],
  declarations: [ ConsultaPreCadastroMedicoComponent, DetalhePreCadastroMedicoComponent,
                  ModalReprovarPreCadastroMedicoComponent ],
  entryComponents: [ModalReprovarPreCadastroMedicoComponent],
  providers: [ MedicoService ],
  exports: [ ConsultaPreCadastroMedicoComponent, DetalhePreCadastroMedicoComponent ]
})
export class PreCadastroMedicoModule { }

