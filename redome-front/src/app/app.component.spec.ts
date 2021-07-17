import { EvolucaoService } from './paciente/cadastro/evolucao/evolucao.service';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { BuscaPreliminarService } from 'app/shared/service/busca.preliminar.service';
import { ModalModule } from 'ngx-bootstrap';
import { CentroTransplanteService } from './admin/centrotransplante/centrotransplante.service';
import { AppComponent } from './app.component';
import { AppModule } from './app.module';
import { PedidoColetaService } from './doador/consulta/coleta/pedido.coleta.service';
import { ResultadoWorkupService } from './doador/consulta/workup/resultado/resultadoworkup.service';
import { WorkupService } from './doador/consulta/workup/workup.service';
import { DoadorService } from './doador/doador.service';
import { activatedRoute, arquivoExameService, autenticacaoService, avaliacaoService, buscaPreliminarService, buscaService, centroTransplanteService, cepCorreioService, classificacaoABOServiceService, doadorService, dominioService, exameService, genotipoService, laboratorioService, pacienteService, pedidoColetaService, pedidoExameService, pedidoTransporteService, pendenciaService, resultadoWorkupService, tarefaService, translateLoader, workupService, evolucaoService, solicitacaoService } from './export.mock.spec';
import { PedidoExameService } from './laboratorio/pedido.exame.service';
import { AvaliacaoService } from './paciente/avaliacao/avaliacao.service';
import { PendenciaService } from './paciente/avaliacao/pendencia.service';
import { ClassificacaoABOService } from './paciente/busca/analise/classificacao.abo.service';
import { BuscaService } from './paciente/busca/busca.service';
import { ArquivoExameService } from './paciente/cadastro/exame/arquivo.exame.service';
import { ExameService } from './paciente/cadastro/exame/exame.service';
import { GenotipoService } from './paciente/genotipo.service';
import { PacienteService } from './paciente/paciente.service';
import { AutenticacaoService } from './shared/autenticacao/autenticacao.service';
import { CepCorreioService } from './shared/cep.correio.service';
import { DominioService } from './shared/dominio/dominio.service';
import { LaboratorioService } from './shared/service/laboratorio.service';
import { TarefaService } from './shared/tarefa.service';
import { PedidoTransporteService } from './transportadora/pedido.transporte.service';
import { SolicitacaoService } from './doador/solicitacao/solicitacao.service';

jasmine.DEFAULT_TIMEOUT_INTERVAL = 10000;

describe('AppComponent', () => {

  let fixtureApp: ComponentFixture<AppComponent>;
  let app: AppComponent;


  beforeAll((done) => (async () => {
    TestBed.resetTestingModule();
    TestBed.configureTestingModule({
            imports: [AppModule, ModalModule.forRoot(),
              TranslateModule.forRoot({
                loader: { provide: TranslateLoader, useValue: translateLoader }
              })
            ],            
            declarations: [],
            providers: [
              {provide: ActivatedRoute, useValue: activatedRoute },
              {provide: AutenticacaoService, useValue: autenticacaoService},
              {provide: CentroTransplanteService, useValue: centroTransplanteService},
              {provide: BuscaService, useValue: buscaService},
              {provide: DominioService, useValue: dominioService},
              {provide: PacienteService, useValue: pacienteService},
              {provide: TarefaService, useValue : tarefaService},
              {provide: PedidoColetaService, useValue: pedidoColetaService},
              {provide: DoadorService, useValue: doadorService},
              {provide: WorkupService, useValue: workupService},
              {provide: ClassificacaoABOService, useValue: classificacaoABOServiceService},
              {provide: CepCorreioService, useValue: cepCorreioService},
              {provide: ArquivoExameService, useValue: arquivoExameService},
              {provide: ExameService, useValue: exameService},
              {provide: PendenciaService, useValue: pendenciaService},
              {provide: AvaliacaoService, useValue: avaliacaoService},
              {provide: PedidoTransporteService, useValue: pedidoTransporteService},
              {provide: PedidoExameService, useValue: pedidoExameService},
              {provide: LaboratorioService, useValue: laboratorioService},
              {provide: PedidoExameService, useValue: pedidoExameService},
              {provide: GenotipoService, useValue: genotipoService},
              {provide: ResultadoWorkupService, useValue: resultadoWorkupService},
              {provide: BuscaPreliminarService, useValue: buscaPreliminarService},
              {provide: EvolucaoService, useValue: evolucaoService},
              {provide: SolicitacaoService, useValue: solicitacaoService},
              { provide: 'Window', useValue: window }
            ]
        });
        
        await TestBed.compileComponents()
            .then(() => {
                fixtureApp = TestBed.createComponent(AppComponent);
                app = fixtureApp.debugElement.componentInstance;
            });

        TestBed.resetTestingModule = () => TestBed;

    })().then(done).catch(done.fail));

  it('should create the app', async(() => {
    expect(app).toBeTruthy();
  }));


});
