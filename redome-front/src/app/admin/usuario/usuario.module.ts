import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BusyModule } from 'angular2-busy';
import { FiltroConsultaModule } from 'app/admin/crud/consulta/filtro/filtro.consulta.module';
import { AnalistaBuscaCentroAvaliadorComponent } from 'app/admin/usuario/analistabusca/centroavaliador/analistabusca.centroavaliador.tarefa.component';
import { NovoUsuarioComponent } from 'app/admin/usuario/detalhe/novo.usuario.component';
import { InputModule } from 'app/shared/component/inputcomponent/input.module';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { HeaderPacienteModule } from '../../paciente/consulta/identificacao/header.paciente.module';
import { AutenticacaoModule } from '../../shared/autenticacao/autenticacao.module';
import { DiretivasModule } from '../../shared/diretivas/diretivas.module';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { DetalheUsuarioComponent } from './detalhe/detalhe.usuario.component';
import { ListarUsuarioComponent } from './listar.usuario.component';
import { UsuarioService } from './usuario.service';
import { BscupService } from 'app/shared/service/bscup.service';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule, MensagemModule, MensagemModalModule, BusyModule, DiretivasModule, PaginationModule,  RouterModule,
            AutenticacaoModule, InputModule, FiltroConsultaModule, HeaderPacienteModule],
  declarations: [ListarUsuarioComponent, AnalistaBuscaCentroAvaliadorComponent, 
                DetalheUsuarioComponent, NovoUsuarioComponent],
  providers: [UsuarioService, BscupService], 
  exports: [ListarUsuarioComponent, AnalistaBuscaCentroAvaliadorComponent, 
            DetalheUsuarioComponent, NovoUsuarioComponent]
})
export class UsuarioModule { }
  
