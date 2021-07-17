import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BusyConfig, BusyModule } from 'angular2-busy';
import { ModalTransfereciaCentroAvaliadorComponent } from 'app/paciente/consulta/transferencia/centroavaliador/modal.transferencia.centro.avaliador.component';
import { InputModule } from 'app/shared/component/inputcomponent/input.module';
import {AccordionModule, BsDropdownModule, ModalModule, PaginationModule} from 'ngx-bootstrap';
import { PedidoExameService } from '../../laboratorio/pedido.exame.service';
import { LogEvolucaoModule } from '../../logEvolucao/component/log.evolucao.module';
import { AutenticacaoModule } from '../../shared/autenticacao/autenticacao.module';
import { DetalhePrescricaoModule } from "../../shared/component/detalheprescricao/detalhe.prescricao.module";
import { DiretivasModule } from '../../shared/diretivas/diretivas.module';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { MessageBox } from '../../shared/modal/message.box';
import { CadastroModule } from '../cadastro/cadastro.module';
import { ArquivoExameService } from '../cadastro/exame/arquivo.exame.service';
import { GenotipoService } from '../genotipo.service';
import { PacienteService } from '../paciente.service';
import { SelectCentrosModule } from './../../shared/component/selectcentros/select.centros.module';
import { PedidoTransferenciaCentroService } from './../../shared/service/pedido.transferencia.centro.service';
import { PrescricaoModule } from './../cadastro/prescricao/prescricao.module';
import { ConsultaComponent } from './consulta.component';
import { DetalheComponent } from './detalhe/detalhe.component';
import { ConsultarEvolucaoComponent } from './evolucao/consultar.evolucao.component';
import { ConsultarExameComponent } from './exame/consultar.exame.component';
import { HeaderPacienteModule } from './identificacao/header.paciente.module';
import { PendenciaComponent } from './pendencia/pendencia.component';
import { ConsultarAutorizacaoPacienteComponent } from './prescricao/autorizacaopaciente/consultar.autorizacaopaciente.component';
import { ConsultarPrescricaoComponent } from './prescricao/consultar.prescricao.component';
import { VisualizarDetalhePrescricaoComponent } from './prescricao/visualizardetalheprescricao/visualizar.detalhe.prescricao.component';
import {ConsultarDistribuicaoWorkupComponent} from "./prescricao/distribuicaoworkup/consultar.distribuicao.workup.component";
import {DistribuicaoWorkupService} from "../../shared/service/distribuicao.workup.service";
import {ModalDistribuicaoWorkupComponent} from "./prescricao/distribuicaoworkup/modaldistirbuicaoworkup/modal.distribuicao.workup.component";

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, FormsModule, ExportTranslateModule, ModalModule,
            MensagemModule, MensagemModalModule, BusyModule, DiretivasModule, PaginationModule,
            HeaderPacienteModule, RouterModule, AutenticacaoModule, CadastroModule, LogEvolucaoModule,
            PrescricaoModule, BsDropdownModule, InputModule, SelectCentrosModule, DetalhePrescricaoModule,
            AccordionModule],
  declarations: [ConsultaComponent, ConsultarExameComponent, ConsultarEvolucaoComponent,
      DetalheComponent, PendenciaComponent, ConsultarPrescricaoComponent, ConsultarAutorizacaoPacienteComponent,
      ModalTransfereciaCentroAvaliadorComponent, VisualizarDetalhePrescricaoComponent, ConsultarDistribuicaoWorkupComponent,
      ModalDistribuicaoWorkupComponent],
  entryComponents: [ModalTransfereciaCentroAvaliadorComponent, ModalDistribuicaoWorkupComponent],
  providers: [PacienteService, ArquivoExameService, GenotipoService,
      { provide: BusyConfig, useFactory: busyConfigFactory }, MessageBox, PedidoExameService,
      PedidoTransferenciaCentroService, DistribuicaoWorkupService ],
  exports: [ConsultaComponent, ConsultarExameComponent, ConsultarEvolucaoComponent, PendenciaComponent,
    ConsultarPrescricaoComponent, ConsultarAutorizacaoPacienteComponent, VisualizarDetalhePrescricaoComponent, ConsultarDistribuicaoWorkupComponent]
})
export class ConsultaModule { }

export function busyConfigFactory() {
  return new BusyConfig({
     message: 'Aguarde..',
     backdrop: false
  });
}
