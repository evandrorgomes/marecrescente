import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { AgendarPedidoWorkupModule } from 'app/doador/cadastro/pedidoworkup/agendar.pedido.workup.module';
import { ModalModule } from 'ngx-bootstrap';
import { ExportTranslateModule } from '../shared/export.translate.module';
import { HlaModule } from '../shared/hla/hla.module';
import { MensagemModule } from '../shared/mensagem/mensagem.module';
import { MockDominioService } from '../shared/mock/mock.dominio.service';
import { MensagemModalModule } from '../shared/modal/mensagem.modal.module';
import { CadastroDoadorInternacionalModule } from './cadastro/cadastro.doador.internacional.module';
import { AgendarPedidoColetaModule } from './cadastro/coleta/agendar/agendar.pedido.coleta.module';
import { AvaliarPedidoColetaModule } from './cadastro/coleta/avaliacao/avaliar.pedido.coleta.module';
import { RessalvaModule } from './cadastro/contato/ressalvas/ressalva.module';
import { AprovarPlanoWorkupModule } from './cadastro/pedidoworkup/planoworkup/plano.workup.module';
import { AvaliarResultadoWorkupModule } from './cadastro/resultadoworkup/avaliacao/avaliar.resultado.workup.module';
import { PedidoAdicionalWorkupModule } from './cadastro/resultadoworkup/pedidoadicional/pedido.adicional.workup.module';
import { AvaliacoesResultadoWorkupModule } from './consulta/avaliacao/avaliacoes.resultado.workup.module';
import { PedidoColetaModule } from './consulta/coleta/pedido.coleta.module';
import { ConsultaDoadorInternacionalModule } from './consulta/consulta.doador.internacional.module';
import { ConsultaContatoDoadorModule } from './consulta/contatopassivo/consulta.contato.doador.module';
import { RecebimentoColetaService } from './consulta/recebimentocoleta/recebimento.coleta.service';
import { WorkupModule } from './consulta/workup/workup.module';
import { DoadorService } from './doador.service';
import { SimularSolicitacaoComponent } from './fake/simular.solicitacao.component';
import { EnriquecimentoModule } from './solicitacao/enriquecimento/enriquecimento.module';
import { ContatoFase2Module } from './solicitacao/fase2/contato.fase2.module';
import { MatchService } from './solicitacao/match.service';
import { PedidoContatoSmsModule } from './solicitacao/sms/pedido.contato.sms.module';
import { SolicitacaoService } from './solicitacao/solicitacao.service';
import { ConsultaLogisticaModule } from './consulta/logistica/consulta.logistica.module';
import { LogisticaModule } from './cadastro/logistica/logistica.module';

@NgModule({
  imports: [CommonModule,FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
    MensagemModule, MensagemModalModule, EnriquecimentoModule, ContatoFase2Module,
    WorkupModule, AgendarPedidoWorkupModule, ConsultaLogisticaModule, ConsultaContatoDoadorModule, AvaliacoesResultadoWorkupModule,
    AvaliarResultadoWorkupModule, PedidoColetaModule, AgendarPedidoColetaModule, AvaliarPedidoColetaModule,
    RessalvaModule, TextMaskModule, RessalvaModule, HlaModule, ConsultaDoadorInternacionalModule, CadastroDoadorInternacionalModule,
    PedidoContatoSmsModule, AprovarPlanoWorkupModule, PedidoAdicionalWorkupModule],
  declarations: [SimularSolicitacaoComponent],
  providers: [SolicitacaoService, DoadorService, MatchService, RecebimentoColetaService, MockDominioService],
  exports: [SimularSolicitacaoComponent]
})
export class DoadorModule { }
