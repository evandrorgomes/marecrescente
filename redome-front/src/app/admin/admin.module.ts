import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BusyModule } from 'angular2-busy';
import { MedicoModule } from 'app/admin/medico/medico.module';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { AutenticacaoModule } from '../shared/autenticacao/autenticacao.module';
import { DiretivasModule } from '../shared/diretivas/diretivas.module';
import { ExportTranslateModule } from '../shared/export.translate.module';
import { MensagemModule } from '../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../shared/modal/mensagem.modal.module';
import { CrudService } from '../shared/service/crud.service';
import { InputModule } from './../shared/component/inputcomponent/input.module';
import { AdminComponent } from './admin.component';
import { CentroTransplanteModule } from './centrotransplante/centrotransplante.module';
import { CrudConsultaComponent } from './crud/consulta/crud.consulta.component';
import { PreCadastroMedicoModule } from './precadastromedico/precadastro.medico.module';
import { UsuarioModule } from './usuario/usuario.module';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule, MensagemModule, MensagemModalModule,
            BusyModule, DiretivasModule, PaginationModule, RouterModule,  AutenticacaoModule,
            InputModule, CentroTransplanteModule, UsuarioModule, PreCadastroMedicoModule,
            MedicoModule],
  declarations: [AdminComponent, CrudConsultaComponent],
  entryComponents: [CrudConsultaComponent],
  providers: [CrudService],
  exports: [AdminComponent, CrudConsultaComponent]
})
export class AdminModule { }

