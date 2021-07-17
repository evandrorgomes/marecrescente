import { EditarModule } from './editar/editar.module';
import { NotificacaoService } from './notificacao.service';
import { ConferenciaModule } from './conferencia/conferencia.module';
import { AvaliacaoAvaliadorModule } from './avaliacao/avaliador/avaliacao.avaliador.module';
import { AvaliacaoMedicoModule } from './avaliacao/medico/avaliacao.medico.module';
import { AutenticacaoModule } from '../shared/autenticacao/autenticacao.module';
import { NovoExameModule } from './cadastro/exame/novoexame.module';
import { RouterModule } from '@angular/router';
import {HeaderPacienteModule} from './consulta/identificacao/header.paciente.module';
import { ConsultaModule } from './consulta/consulta.module';
import {ExportTranslateModule} from '../shared/export.translate.module';
import {ConsultarEvolucaoComponent} from './consulta/evolucao/consultar.evolucao.component';
import { CommonModule } from '@angular/common';
import { NovaEvolucaoModule } from './cadastro/evolucao/novaevolucao.module';
import { PacienteComponent } from './paciente.component';
import { CadastroModule } from './cadastro/cadastro.module';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule } from "@angular/forms";
import { HeaderPacienteComponent } from "./consulta/identificacao/header.paciente.component";
import { BuscaModule } from './busca/busca.module';
import { ConsultaTransferenciaCentroModule } from 'app/paciente/transferencia/consulta/consulta.transferencia.centro.module';
import { DetalheTransferenciaCentroModule } from 'app/paciente/transferencia/detalhe/detalhe.transferencia.centro.module';

@NgModule({
  imports: [CommonModule, RouterModule, CadastroModule, NovaEvolucaoModule, AvaliacaoAvaliadorModule, 
      FormsModule, ExportTranslateModule, ConsultaModule, NovoExameModule, AutenticacaoModule, 
      AvaliacaoMedicoModule, ConferenciaModule, EditarModule, BuscaModule, ConsultaTransferenciaCentroModule,
      DetalheTransferenciaCentroModule],
  declarations: [PacienteComponent],
  providers: [NotificacaoService],
  exports: [PacienteComponent]
})
export class PacienteModule { }