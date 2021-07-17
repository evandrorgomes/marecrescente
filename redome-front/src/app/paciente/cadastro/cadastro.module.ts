import { DiagnosticoModule } from './diagnostico/diagnostico.module';
import { UploadArquivoModule } from './../../shared/upload/upload.arquivo.module';
import { CadastrarBuscaPreliminarComponent } from './buscapreliminar/cadastrar.busca.preliminar.component';
import { ContatoTelefoneModule } from '../../shared/component/telefone/contato.telefone.module';
import { IdentificacaoModule } from './identificacao/identificacao.module';
import { ContatoModule } from './contato/contato.module';
import { MensagemModalModule } from '../../shared/modal/mensagem.modal.module';
import { ExameModule } from './exame/exame.module';
import { RascunhoService } from '../rascunho.service';
import { RouterModule } from '@angular/router';
import { EvolucaoModule } from './evolucao/evolucao.module';
import { Filtro } from '../../shared/diretivas/filtro.directive';
import { DiretivasModule } from '../../shared/diretivas/diretivas.module';

import { MensagemModule } from '../../shared/mensagem/mensagem.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CurrencyMaskModule } from 'ng2-currency-mask';
import { ExameService } from './exame/exame.service';
import { CepCorreioService } from '../../shared/cep.correio.service';
import { DominioService } from '../../shared/dominio/dominio.service';
import { IdentificacaoComponent } from './identificacao/identificacao.component';
import { DadosPessoaisComponent } from './dadospessoais/dadospessoais.component';

import { ExportTranslateModule } from '../../shared/export.translate.module';
import { TextMaskModule } from 'angular2-text-mask/src/angular2TextMask';
import { ExportFileUploaderDirectiveModule } from '../../shared/export.file.uploader.directive.module';
import { AvaliacaoComponent } from './avaliacao/avaliacao.component';
import { DiagnosticoComponent } from './diagnostico/diagnostico.component';
import { ExameComponent } from './exame/exame.component';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { Paciente } from '../paciente';
import { CadastroComponent } from './cadastro.component';
import { PacienteService } from '../paciente.service';
import { TooltipModule, ModalModule, BsDropdownModule } from 'ngx-bootstrap';
import { HeaderPacienteModule } from "../consulta/identificacao/header.paciente.module";
import { DadosPessoaisModule } from './dadospessoais/dadospessoais.module';
import { ListarDefinirCentroTransplanteModule } from '../busca/centroTransplante/indefinido/listar.centro.transplante.module';
import { CadastrarBuscaPreliminarModule } from 'app/paciente/cadastro/buscapreliminar/cadastrar.busca.preliminar.module';
import { AvaliacaoModule } from './avaliacao/avaliacao.module';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, ExportFileUploaderDirectiveModule, 
            TextMaskModule, ExportTranslateModule, TooltipModule, ModalModule, 
            CurrencyMaskModule, BsDropdownModule, MensagemModule, 
            DiretivasModule, EvolucaoModule, HeaderPacienteModule, RouterModule, 
            ExameModule, MensagemModalModule, ContatoModule, IdentificacaoModule,
            DadosPessoaisModule, ContatoTelefoneModule, ListarDefinirCentroTransplanteModule,
            CadastrarBuscaPreliminarModule, DiagnosticoModule, AvaliacaoModule],
  declarations: [CadastroComponent],
  providers: [DominioService, PacienteService, CepCorreioService, ExameService, RascunhoService],
  exports: [CadastroComponent]
})
export class CadastroModule { }

  
