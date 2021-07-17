import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { BusyModule } from 'angular2-busy';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { NovaBuscaModule } from 'app/paciente/busca/nova/nova.busca.module';
import { AnaliseMatchPreliminarComponent } from 'app/paciente/busca/preliminar/match.preliminar.component';
import { CardChecklistModule } from 'app/shared/component/listamatch/checklist/card.checklist.module';
import { TipoExameService } from 'app/shared/service/tipo.exame.service';
import { BsDropdownModule, CollapseModule, ModalModule, PaginationModule, TooltipModule } from 'ngx-bootstrap';
import { SortableModule } from 'ngx-bootstrap/sortable/sortable.module';
import { NgxCarouselModule } from 'ngx-carousel';
import { ResultadoPedidoIdmModule } from '../../doador/solicitacao/idm/resultado/resultado.pedido.idm.module';
import { SolicitacaoService } from '../../doador/solicitacao/solicitacao.service';
import { PedidoEnvioEmdisService } from '../../doador/solicitacao/pedidoenvioemdis.service';
import { PedidoExameService } from '../../laboratorio/pedido.exame.service';
import { PedidoIdmService } from '../../laboratorio/pedido.idm.service';
import { AutenticacaoModule } from '../../shared/autenticacao/autenticacao.module';
import { InputModule } from '../../shared/component/inputcomponent/input.module';
import { ListaMatchModule } from '../../shared/component/listamatch/lista.match.module';
import { ModalComentarioMatchModule } from '../../shared/component/modalcomentariomatch/modal.comentario.match.module';
import { RessalvaDoadorModule } from '../../shared/component/ressalvadoador/ressalva.doador.module';
import { DialogoModule } from '../../shared/dialogo/dialogo.module';
import { DiretivasModule } from '../../shared/diretivas/diretivas.module';
import { DominioService } from '../../shared/dominio/dominio.service';
import { ExportTranslateModule } from '../../shared/export.translate.module';
import { LocusModule } from '../../shared/hla/locus/locus.module';
import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { HlaService } from '../../shared/service/hla.service';
import { LaboratorioService } from '../../shared/service/laboratorio.service';
import { PendenciaDialogoModule } from '../avaliacao/pendenciadialogo/pendencia.dialogo.module';
import { PrescricaoModule } from '../cadastro/prescricao/prescricao.module';
import { HeaderPacienteModule } from '../consulta/identificacao/header.paciente.module';
import { PacienteService } from '../paciente.service';
import { ClassificacaoABOService } from './analise/classificacao.abo.service';
import { PrazoModule } from './analise/diretivas/prazo.module';
import { GenotipoDivergenteModule } from './analise/genotipodivergente/genotipo.divergente.module';
import { AnaliseMatchComponent } from './analise/match.component';
import { ModalGenotipoComparadoComponent } from './analise/modalgenotipocomparado/modal.genotipo.comparado.component';
import { SubHeaderPacienteModule } from './analise/subheaderpaciente/subheader.paciente.module';
import { BuscaService } from './busca.service';
import { BuscaInternacionalModule } from './buscainternacional/busca.internacional.module';
import { CancelamentoBuscaComponent } from './cancelamento.busca.component';
import { ConfirmarCentroTransplanteModule } from './centroTransplante/confirmar.centro.transplante.module';
import { RecusarCentroTransplanteModal } from './centroTransplante/recusarTransplante/recusar.centro.transplante.modal';
import { PacienteBuscaComponent } from './consulta/pacientebusca.component';
import { DialogoBuscaComponent } from './dialogo/dialogo.busca.component';
import { DialogoBuscaService } from './dialogo/dialogo.busca.service';
import { PedidoExameModal } from './modalcustom/modal/pedido.exame.modal';
import { PedidoExameNacionalModal } from './modalcustom/modal/pedido.exame.nacional.modal';
import { CancelarPedidoExameFase3Module } from './pedidoexame/doadorInternacional/cancelar/cancelar.pedido.exame.fase3.module';
import { ModalCancelarPedidoExameFase3Component } from './pedidoexame/paciente/modal.cancelar.pedido.exame.fase3.component';
import { ModalPedidoExameFase3Component } from './pedidoexame/paciente/modal.pedido.exame.fase3.component';
import { PedidoExameDoadorListaComponent } from './pedidoexame/paciente/pedido.exame.doador.lista.component';
import { PedidoExameFase3Component } from './pedidoexame/paciente/pedido.exame.fase3.component';
import { PedidoExamePacienteComponent } from './pedidoexame/paciente/pedido.exame.paciente.component';
import { WindowViewModule } from 'app/shared/component/window-view/window-view.module';
import { WindowViewService } from 'app/shared/component/window-view/window-view.service';
import { WindowViewLayerService } from 'app/shared/component/window-view/window-view-layer.service';
import {ChecklistWindowsComponent} from "./analise/windows/checklist/checklist.windows.component";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [CommonModule, BrowserAnimationsModule, FormsModule, ExportTranslateModule, ModalModule, MensagemModule, TextMaskModule,
    MensagemModalModule, BusyModule, PaginationModule, RouterModule, AutenticacaoModule, DiretivasModule,
    ReactiveFormsModule, PendenciaDialogoModule, PaginationModule, HeaderPacienteModule, SubHeaderPacienteModule, NgxCarouselModule,
    ListaMatchModule, ModalComentarioMatchModule, RessalvaDoadorModule, PrescricaoModule, ConfirmarCentroTransplanteModule,
    DialogoModule, TooltipModule, SortableModule.forRoot(), LocusModule, CancelarPedidoExameFase3Module,
    ResultadoPedidoIdmModule, BsDropdownModule, NovaBuscaModule, PrazoModule, CardChecklistModule, InputModule, CollapseModule,
    GenotipoDivergenteModule, BuscaInternacionalModule, WindowViewModule],

  entryComponents: [RecusarCentroTransplanteModal, ModalPedidoExameFase3Component, ModalCancelarPedidoExameFase3Component, PedidoExameModal,PedidoExameDoadorListaComponent,
    ModalGenotipoComparadoComponent, PedidoExameNacionalModal, ChecklistWindowsComponent],

  declarations: [PacienteBuscaComponent, CancelamentoBuscaComponent, AnaliseMatchComponent, DialogoBuscaComponent,
      RecusarCentroTransplanteModal, PedidoExameFase3Component, ModalPedidoExameFase3Component, ModalCancelarPedidoExameFase3Component, PedidoExameModal, PedidoExameDoadorListaComponent,
      ModalGenotipoComparadoComponent, AnaliseMatchPreliminarComponent, PedidoExameNacionalModal,PedidoExamePacienteComponent, ChecklistWindowsComponent],

  providers: [BuscaService, PacienteService, SolicitacaoService, DialogoBuscaService, HlaService, ClassificacaoABOService, DominioService, PedidoExameService, PedidoIdmService, TipoExameService,
              LaboratorioService, WindowViewService, WindowViewLayerService, SolicitacaoService, PedidoEnvioEmdisService],

  exports: [PacienteBuscaComponent, CancelamentoBuscaComponent, AnaliseMatchComponent, RecusarCentroTransplanteModal, PedidoExameFase3Component, PedidoExameModal, PedidoExameDoadorListaComponent,
    ModalGenotipoComparadoComponent, AnaliseMatchPreliminarComponent,PedidoExamePacienteComponent, ChecklistWindowsComponent]
})
export class BuscaModule { }
