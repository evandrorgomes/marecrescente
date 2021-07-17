import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { HeaderPacienteModule } from '../../consulta/identificacao/header.paciente.module';
import { NovaBuscaComponent } from 'app/paciente/busca/nova/nova.busca.component';
import { DiretivasModule } from 'app/shared/diretivas/diretivas.module';
import { DiagnosticoModule } from 'app/paciente/cadastro/diagnostico/diagnostico.module';
import { VisualizarDiagnosticoComponent } from '../../../shared/component/visualizar/diagnostico/visualizar.diagnostico.component';
import { AvaliarNovaBuscaComponent } from './avaliar/avaliar.nova.busca.component';
import { MensagemModule } from 'app/shared/mensagem/mensagem.module';
import { AvaliacaoNovaBuscaService } from '../../../shared/service/avaliacao.nova.busca.service';
import { VisualizarEvolucaoComponent } from '../../../shared/component/visualizar/evolucao/visualizar.evolucao.component';
import { AvaliarNovaBuscaDetalheComponent } from './avaliar/detalhe/avaliar.nova.busca.detalhe.component';
import { ReprovarAvaliacaoModal } from 'app/paciente/busca/nova/avaliar/detalhe/modalReprovacao/reprovar.avaliacao.modal';

@NgModule({
  imports: [CommonModule, FormsModule, ReactiveFormsModule, ExportTranslateModule, 
            ModalModule, HeaderPacienteModule, RouterModule, DiretivasModule, MensagemModule,
            DiagnosticoModule, PaginationModule],
  declarations: [NovaBuscaComponent, VisualizarDiagnosticoComponent, AvaliarNovaBuscaComponent,
                VisualizarEvolucaoComponent, AvaliarNovaBuscaDetalheComponent, ReprovarAvaliacaoModal],
  providers: [AvaliacaoNovaBuscaService],
  entryComponents: [ReprovarAvaliacaoModal],
  exports: [NovaBuscaComponent, VisualizarDiagnosticoComponent, AvaliarNovaBuscaComponent,
            VisualizarEvolucaoComponent, AvaliarNovaBuscaDetalheComponent]
})
export class NovaBuscaModule { }

  