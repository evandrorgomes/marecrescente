import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { DetalhePrescricaoModule } from "app/shared/component/detalheprescricao/detalhe.prescricao.module";
import { InputModule } from "app/shared/component/inputcomponent/input.module";
import { PedidoWorkupService } from 'app/shared/service/pedido.workup.service';
import { UploadArquivoModule } from "app/shared/upload/upload.arquivo.module";
import { AccordionModule, ModalModule } from 'ngx-bootstrap';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { DetalhePedidoColetaModule } from '../../../../shared/component/detalhepedidocoleta/detalhe.pedido.coleta.module';
import { DiretivasModule } from '../../../../shared/diretivas/diretivas.module';
import { ExportTranslateModule } from '../../../../shared/export.translate.module';
import { MensagemModule } from '../../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../../shared/modal/mensagem.modal.module';
import { HeaderDoadorModule } from '../../../consulta/header/header.doador.module';
import { AprovarPlanoWorkupComponent } from './aprovar.plano.workup.component';
import { InformarPlanoWorkupComponent } from "./informar.plano.workup.component";
import { HeaderDoadorInternacionalModule } from "app/doadorinternacional/identificacao/header.doador.internacional.module";
import { EnderecoContatoModule } from "app/shared/component/endereco/endereco.contato.module";
import {VisualizarPrescricaoModule} from "../../../../shared/component/visualizar/prescricao/visualizar.prescricao.module";


@NgModule({
    imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, TextMaskModule, DetalhePedidoColetaModule, UploadArquivoModule,
      PaginationModule, DiretivasModule, HeaderDoadorModule, AccordionModule,
      InputModule, HeaderDoadorInternacionalModule, EnderecoContatoModule, VisualizarPrescricaoModule],
    declarations: [AprovarPlanoWorkupComponent, InformarPlanoWorkupComponent],
    providers: [PedidoWorkupService],
    exports: [AprovarPlanoWorkupComponent, InformarPlanoWorkupComponent]
  })
  export class AprovarPlanoWorkupModule { }
