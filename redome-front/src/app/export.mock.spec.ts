import { MockPedidoExameService } from './shared/mock/mock.pedido.exame.service';
import { MockPedidoTransporteService } from './shared/mock/mock.pedido.transporte.service';
import { MockBuscaService } from './shared/mock/mock.busca.service';
import { MockCentroTransplanteService } from './shared/mock/mock.centrotransplante.service';
import { MockAutenticacaoService } from './shared/mock/mock.autenticacao.service';
import { MockActivatedRoute } from './shared/mock/mock.activated.route';
import { MockDominioService } from './shared/mock/mock.dominio.service';
import { MockPacienteService } from './shared/mock/mock.paciente.service';
import { MockTarefaService } from './shared/mock/mock.tarefa.service';
import { MockPedidoColetaService } from './shared/mock/mock.pedido.coleta.service';
import { MockDoadorService } from './shared/mock/mock.doador.service';
import { MockWorkupService } from './shared/mock/mock.workupservice';
import { MockClassificacaoABOService } from './shared/mock/mock.classificacao.abo.service';
import { MockCepCorreioService } from './shared/mock/mock.cep.correio.service';
import { MockArquivoExameService } from './shared/mock/mock.arquivo.exame.service';
import { MockExameService } from './shared/mock/mock.exame.service';
import { MockPendenciaService } from './shared/mock/mock.pendencia.service';
import { MockAvaliacaoService } from './shared/mock/mock.avaliacao.service';
import { MockTranslateLoader } from './shared/mock/mock.translate.loader';
import { MockLaboratorioService } from './shared/mock/mock.laboratorio.service';
import { MockGenotipoService } from './shared/mock/mock.genotipo.service';
import { MockResultadoWorkupService } from './shared/mock/mock.resultado.workup.service';
import { MockBuscaPreliminarService } from 'app/shared/mock/mock.busca.preliminar.service';
import { MockCrudService } from 'app/shared/mock/mock.crud.service';
import { MockDiagnosticoService } from 'app/shared/mock/mock.diagnostico.service';
import { MockEvolucaoService } from './shared/mock/mock.evolucao.service';
import { MockSolicitacaoService } from './shared/mock/mock.solicitacao.service';

export const activatedRoute: MockActivatedRoute = new MockActivatedRoute();
export const autenticacaoService: MockAutenticacaoService = new MockAutenticacaoService();
export const buscaService: MockBuscaService = new MockBuscaService();
export const centroTransplanteService: MockCentroTransplanteService = new MockCentroTransplanteService();
export const dominioService: MockDominioService = new MockDominioService();
export const pacienteService: MockPacienteService = new MockPacienteService();
export const tarefaService: MockTarefaService = new MockTarefaService();
export const pedidoColetaService: MockPedidoColetaService = new MockPedidoColetaService();
export const doadorService: MockDoadorService = new MockDoadorService();
export const workupService: MockWorkupService = new MockWorkupService();
export const classificacaoABOServiceService: MockClassificacaoABOService = new MockClassificacaoABOService();
export const cepCorreioService: MockCepCorreioService = new MockCepCorreioService();
export const arquivoExameService: MockArquivoExameService = new MockArquivoExameService();
export const exameService: MockExameService = new MockExameService();
export const pendenciaService: MockPendenciaService = new MockPendenciaService();
export const avaliacaoService: MockAvaliacaoService = new MockAvaliacaoService();
export const pedidoTransporteService: MockPedidoTransporteService = new MockPedidoTransporteService();
export const translateLoader: MockTranslateLoader = new MockTranslateLoader();
export const pedidoExameService: MockPedidoExameService = new MockPedidoExameService();
export const laboratorioService: MockLaboratorioService = new MockLaboratorioService();
export const genotipoService: MockGenotipoService = new MockGenotipoService();
export const resultadoWorkupService: MockResultadoWorkupService = new MockResultadoWorkupService();
export const buscaPreliminarService: MockBuscaPreliminarService = new MockBuscaPreliminarService();
export const crudService: MockCrudService = new MockCrudService();
export const diagnosticoService: MockDiagnosticoService = new MockDiagnosticoService();
export const evolucaoService: MockEvolucaoService = new MockEvolucaoService();
export const solicitacaoService: MockSolicitacaoService = new MockSolicitacaoService();
