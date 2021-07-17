import { PaginationModule } from 'ngx-bootstrap';
import { InativacaoModule } from 'app/doador/inativacao/inativacao.module';
import { HeaderDoadorModule } from 'app/doador/consulta/header/header.doador.module';
import { ExportTranslateModule } from 'app/shared/export.translate.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EnriquecimentoDoadorComponent } from '../enriquecimento/enriquecimento.doador.component';
import { AnaliseMedicaComponent } from './analise.medica.component';
import { NgModule } from '@angular/core';
import { AnaliseMedicaService } from './analise.medica.service';
import { DetalheAnaliseMedicaComponent } from './detalhe/detalhe.analise.medica.component';
import { ContatoTelefoneModule } from 'app/shared/component/telefone/contato.telefone.module';
import { DoadorAtualizacaoModule } from 'app/doador/atualizacao/doador.atualizacao.module';
import { RouterModule } from '@angular/router';
import { DoadorContatoModule } from 'app/doador/cadastro/contato/doador.contato.module';
import { QuestionarioModule } from 'app/shared/questionario/questionario.module';
import { ContatoFase2Module } from '../fase2/contato.fase2.module';
import { ModalFinalizarAnaliseMedicaComponent } from './detalhe/modal.finalizar.analise.medica.component';
import { InputModule } from 'app/shared/component/inputcomponent/input.module';
import { EvolucaoDoadorService } from 'app/shared/service/evolucao.doador.service';



@NgModule({
    imports: [CommonModule,FormsModule, ReactiveFormsModule, ExportTranslateModule, RouterModule, HeaderDoadorModule, InativacaoModule, PaginationModule,
      DoadorAtualizacaoModule, ContatoTelefoneModule, DoadorContatoModule, QuestionarioModule, ContatoFase2Module , InputModule],
    declarations: [AnaliseMedicaComponent, DetalheAnaliseMedicaComponent, ModalFinalizarAnaliseMedicaComponent],
    providers: [AnaliseMedicaService, EvolucaoDoadorService],
    entryComponents: [ModalFinalizarAnaliseMedicaComponent],
    exports: [AnaliseMedicaComponent, DetalheAnaliseMedicaComponent]
  })
  export class AnaliseMedicaModule { }