import { EnderecoContatoModule } from '../../../shared/component/endereco/endereco.contato.module';
import {ContatoEnderecoPacienteComponent} from './endereco/contato.endereco.paciente.component';
import {ContatoPacienteComponent} from './contato.paciente.component';
import { ExportFileUploaderDirectiveModule } from '../../../shared/export.file.uploader.directive.module';
import { RouterModule } from '@angular/router';
import { HeaderPacienteModule } from '../../consulta/identificacao/header.paciente.module';
import { ExportTranslateModule } from '../../../shared/export.translate.module';
import { MensagemModule } from '../../../shared/mensagem/mensagem.module';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TooltipModule, ModalModule, TypeaheadModule, BsDropdownModule } from 'ngx-bootstrap';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ContatoTelefoneModule } from "../../../shared/component/telefone/contato.telefone.module";

@NgModule({
  imports: [FormsModule, CommonModule, ReactiveFormsModule, ExportTranslateModule, 
    ModalModule, HeaderPacienteModule, RouterModule, MensagemModule, TextMaskModule, 
    ExportFileUploaderDirectiveModule, TooltipModule, EnderecoContatoModule, ContatoTelefoneModule],
  declarations: [ContatoPacienteComponent, ContatoEnderecoPacienteComponent],
  providers: [],
  exports: [ContatoPacienteComponent, ContatoEnderecoPacienteComponent]
})
export class ContatoModule { }

  