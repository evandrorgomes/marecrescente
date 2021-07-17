import {HeaderDoadorModule} from '../../../consulta/header/header.doador.module';
import {DiretivasModule} from '../../../../shared/diretivas/diretivas.module';
import {MensagemModule} from '../../../../shared/mensagem/mensagem.module';
import {MensagemModalModule} from '../../../../shared/modal/mensagem.modal.module';
import {TextMaskModule} from 'angular2-text-mask/src/angular2TextMask';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {ModalModule} from 'ngx-bootstrap';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {AvaliarPedidoColetaComponent} from './avaliar.pedido.coleta.component';
import {TarefaService} from '../../../../shared/tarefa.service';
import {ExportTranslateModule} from '../../../../shared/export.translate.module';
import {AvaliacaoResultadoWorkupService} from '../../resultadoworkup/avaliacao/avaliacao.resultado.workup.service';
import {RessalvaModule} from '../../contato/ressalvas/ressalva.module';
import {InativacaoModule} from '../../../inativacao/inativacao.module';
import {VisualizarResultadoWorkupModule} from "../../../../shared/component/visualizar/resultadoworkup/visualizar.resultado.workup.module";
import {DetalhePrescricaoModule} from "../../../../shared/component/detalheprescricao/detalhe.prescricao.module";
import {VisualizarPrescricaoModule} from "../../../../shared/component/visualizar/prescricao/visualizar.prescricao.module";
import {ModalJustificativaModule} from "../../../../shared/component/modaljustificativa/modal.justificativa.module";


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, TextMaskModule,
      PaginationModule, DiretivasModule, HeaderDoadorModule, HeaderDoadorModule, RessalvaModule, InativacaoModule,
       VisualizarResultadoWorkupModule, DetalhePrescricaoModule, VisualizarPrescricaoModule, ModalJustificativaModule],
    declarations: [AvaliarPedidoColetaComponent],
    providers: [TarefaService, AvaliacaoResultadoWorkupService],
    exports: [AvaliarPedidoColetaComponent]
  })
  export class AvaliarPedidoColetaModule { }
