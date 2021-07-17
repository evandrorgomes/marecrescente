import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { HeaderDoadorModule } from '../../header/header.doador.module';
import { HeaderPacienteModule } from '../../../../paciente/consulta/identificacao/header.paciente.module';
import { AutenticacaoService } from "../../../../shared/autenticacao/autenticacao.service";
import { EmailContatoModule } from '../../../../shared/component/email/email.contato.module';
import { ContatoTelefoneModule } from '../../../../shared/component/telefone/contato.telefone.module';
import { DiretivasModule } from '../../../../shared/diretivas/diretivas.module';
import { ModalModule, PaginationModule } from 'ngx-bootstrap';
import { TarefaService } from "../../../../shared/tarefa.service";
import { EnderecoContatoModule } from '../../../../shared/component/endereco/endereco.contato.module';
import { ExportFileUploaderDirectiveModule } from '../../../../shared/export.file.uploader.directive.module';
import { ExportTranslateModule } from '../../../../shared/export.translate.module';
import { MensagemModule } from '../../../../shared/mensagem/mensagem.module';
import { MensagemModalModule } from '../../../../shared/modal/mensagem.modal.module';
import { ResultadoWorkupService } from './resultadoworkup.service';
import { ConsultaResultadoWorkupNacionalComponent } from "./consulta.resultado.workup.nacional.component";
import { SelectCentrosModule } from "../../../../shared/component/selectcentros/select.centros.module";
import { CadastroResultadoWorkupNacionalComponent } from "app/doador/cadastro/resultadoworkup/resultado/cadastro.resultado.workup.nacional.component";
import { CadastroResultadoWorkupInternacionalComponent } from "app/doador/cadastro/resultadoworkup/resultado/cadastro.resultado.workup.internacional.component";
import { QuestionarioModule } from "app/shared/questionario/questionario.module";
import {InputModule} from "../../../../shared/component/inputcomponent/input.module";
import { VisualizarPrescricaoModule } from "app/shared/component/visualizar/prescricao/visualizar.prescricao.module";


@NgModule({
   imports: [CommonModule, FormsModule, ExportTranslateModule, ModalModule, ReactiveFormsModule,
      MensagemModule, MensagemModalModule, EnderecoContatoModule, ContatoTelefoneModule, EmailContatoModule,
      PaginationModule, DiretivasModule, HeaderPacienteModule, TextMaskModule, DiretivasModule , HeaderDoadorModule,
      ExportFileUploaderDirectiveModule, SelectCentrosModule, QuestionarioModule, InputModule, VisualizarPrescricaoModule],
    declarations: [ConsultaResultadoWorkupNacionalComponent, CadastroResultadoWorkupNacionalComponent, CadastroResultadoWorkupInternacionalComponent],
    providers: [AutenticacaoService, ResultadoWorkupService, TarefaService],
    exports: [ConsultaResultadoWorkupNacionalComponent, CadastroResultadoWorkupNacionalComponent, CadastroResultadoWorkupInternacionalComponent]
  })
  export class ResultadoWorkupModule { }
