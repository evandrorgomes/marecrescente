import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { TentativaContatoDoadorService } from './tentativa.contato.doador.service';
import { ContatoTelefonicoService } from '../../../shared/service/contato.telefonico.service';
import { RouterModule } from '@angular/router';
import { BusyModule, BusyConfig } from 'angular2-busy';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { DoadorService } from '../../doador.service';
import { DiretivasModule } from '../../../shared/diretivas/diretivas.module';
import { ExportTranslateModule } from "../../../shared/export.translate.module";
import { MensagemModalModule } from "../../../shared/modal/mensagem.modal.module";
import { HeaderDoadorModule } from "../../consulta/header/header.doador.module";
import { ContatoFase2Component } from "./contato.fase2.component";
import { MensagemModule } from "../../../shared/mensagem/mensagem.module";
import { ContatoTelefoneModule } from "../../../shared/component/telefone/contato.telefone.module";
import { DoadorContatoModule } from "../../cadastro/contato/doador.contato.module";
import { ContatoFase3Component } from '../fase3/contato.fase3.component';
import { DoadorAtualizacaoModule } from 'app/doador/atualizacao/doador.atualizacao.module';
import { QuestionarioModule } from 'app/shared/questionario/questionario.module';
import { SelecionarHemocentroComponent } from '../fase3/selecionar.hemocentro.component';
import { PedidoContatoService } from 'app/shared/service/pedido.contato.service';
import { ModalRegistroContatoComponent } from './modal.registro.atendimento.component';
import { RegistroContatoService } from 'app/shared/service/registro.contato.service';
import { RegistroTipoOcorrenciaService } from 'app/shared/service/registro.tipo.ocorrencia.service';
import { InputModule } from '../../../shared/component/inputcomponent/input.module';
import { ModalAgendarContatoComponent } from './modal.agendar.contato.component';
import { ModalFinalizarContatoComponent } from './modal.finalizar.contato.component';
import { ListarEvolucaoDoadorComponent } from './listar.evolucao.doador.component';
import { ModalEvolucaoDoadorComponent } from './modal.evolucao.doador.component';
import { EvolucaoDoadorService } from 'app/shared/service/evolucao.doador.service';
import { ContatoDataEventService } from '../contato.data.event.service';

@NgModule({
  imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
    MensagemModule, MensagemModalModule, ModalModule, MensagemModule, DiretivasModule, DoadorAtualizacaoModule,
    ContatoTelefoneModule, HeaderDoadorModule, RouterModule, DoadorContatoModule, TextMaskModule, PaginationModule,
    QuestionarioModule, InputModule],
  declarations: [ContatoFase2Component, ContatoFase3Component, SelecionarHemocentroComponent, ModalRegistroContatoComponent, ModalAgendarContatoComponent, 
    ModalFinalizarContatoComponent, ListarEvolucaoDoadorComponent, ModalEvolucaoDoadorComponent],
  providers: [DoadorService, ContatoTelefonicoService, TentativaContatoDoadorService
    , PedidoContatoService, RegistroContatoService, RegistroTipoOcorrenciaService, EvolucaoDoadorService, ContatoDataEventService],
  exports: [ContatoFase2Component, ContatoFase3Component, SelecionarHemocentroComponent,ModalRegistroContatoComponent, ListarEvolucaoDoadorComponent],
  entryComponents: [ModalRegistroContatoComponent, ModalAgendarContatoComponent, ModalFinalizarContatoComponent, ModalEvolucaoDoadorComponent],
})
export class ContatoFase2Module { }
