import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { TextMaskModule } from "angular2-text-mask/src/angular2TextMask";
import { TipoAmostraService } from "app/doador/solicitacao/tipoamostra.service";
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { ModalModule } from 'ngx-bootstrap/modal/modal.module';
import { PrescricaoService } from '../../../doador/solicitacao/prescricao.service';
import { DetalhePrescricaoModule } from "../../../shared/component/detalheprescricao/detalhe.prescricao.module";
import { EnderecoContatoModule } from "../../../shared/component/endereco/endereco.contato.module";
import { InputModule } from "../../../shared/component/inputcomponent/input.module";
import { DiretivasModule } from '../../../shared/diretivas/diretivas.module';
import { ExportFileUploaderDirectiveModule } from "../../../shared/export.file.uploader.directive.module";
import { ExportTranslateModule } from "../../../shared/export.translate.module";
import { MensagemModule } from "../../../shared/mensagem/mensagem.module";
import { MensagemModalModule } from '../../../shared/modal/mensagem.modal.module';
import { HeaderPacienteModule } from "../../consulta/identificacao/header.paciente.module";
import { ConsultaAvaliacoesPrescricaoModule } from '../../consulta/prescricao/avaliacao/consulta.avaliacoes.prescricao.module';
import { UploadArquivoModule } from './../../../shared/upload/upload.arquivo.module';
import { ModalUploadAutorizacaoPacienteComponent } from './autorizacaopaciente/modal.upload.autorizacaopaciente.component';
import { AvaliarPrescricaoComponent } from './avaliacao/avaliar.prescricao.component';
import { AvaliarPrescricaoService } from './avaliacao/avaliar.prescricao.service';
import { JustificativaAvaliacaoModal } from "./avaliacao/justificativa.avaliacao.modal";
import { PrescricaoMedulaComponent } from "./prescricao.medula.component";
import { PrescricaoCordaoComponent } from "./prescricao.cordao.component";
import { DominioService } from "app/shared/dominio/dominio.service";
import { HeaderPacienteComponent } from "app/paciente/consulta/identificacao/header.paciente.component";

@NgModule({
    imports: [CommonModule, ReactiveFormsModule, ExportTranslateModule, HeaderPacienteModule,
        MensagemModule, MensagemModalModule, TextMaskModule, ExportFileUploaderDirectiveModule,
        CurrencyMaskModule, DiretivasModule, ModalModule, ConsultaAvaliacoesPrescricaoModule,
        UploadArquivoModule, DetalhePrescricaoModule, InputModule, EnderecoContatoModule],
    declarations: [PrescricaoMedulaComponent, PrescricaoCordaoComponent, AvaliarPrescricaoComponent, 
        ModalUploadAutorizacaoPacienteComponent, JustificativaAvaliacaoModal],
    entryComponents: [ModalUploadAutorizacaoPacienteComponent, JustificativaAvaliacaoModal],
    providers: [AvaliarPrescricaoService, PrescricaoService, TipoAmostraService, DominioService],
    exports: [PrescricaoMedulaComponent, PrescricaoCordaoComponent, AvaliarPrescricaoComponent, 
        ModalUploadAutorizacaoPacienteComponent]
  })
  export class PrescricaoModule {

  }
