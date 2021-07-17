package br.org.cancer.modred.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.cancer.modred.controller.dto.PacienteDto;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.dto.ProcessoDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.AtribuirTarefa;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.helper.ObterTarefa;
import br.org.cancer.modred.model.AvaliacaoCamaraTecnica;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.service.ILogEvolucaoService;
import br.org.cancer.modred.service.impl.AvaliacaoCamaraTecnicaService;
import br.org.cancer.modred.service.impl.AvaliacaoService;
import br.org.cancer.modred.service.impl.PacienteService;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;
import br.org.cancer.modred.test.util.MockService;
import br.org.cancer.modred.test.util.RetornoPaginacao;
import br.org.cancer.modred.test.util.base.H2BasePaciente;

public class H2AvaliacaoCamaraTecnicaControllerTest  extends BaseConfigurationTest {
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private AvaliacaoService avaliacaoService;
	
	@Autowired
	private ILogEvolucaoService logEvolucaoService;
	
	@Mock
	private ITarefaFeign tarefaFeign;
	
	@Autowired
	private AvaliacaoCamaraTecnicaService avaliacaoCamaraTecnicaService;
	
	@BeforeClass
	public static void setup() {
		makeAuthotization.paraUsuario("AVALIADOR_CAMARA_TECNICA")
			.addPerfil("AVALIADOR_CAMARA_TECNICA")
			.addRecurso(
					"AVALIAR_PACIENTE_CAMARA_TECNICA",
					"VISUALIZAR_IDENTIFICACAO_COMPLETA",
					"CONSULTAR_EXAMES_PACIENTE",
					"CONSULTAR_EVOLUCOES_PACIENTE");
	}
	
	@Before
	public void setupBefore() {
		
		MockitoAnnotations.initMocks(this);
		
		MockService.mockCriarPacienteComAvaliacaoInicialAprovada(pacienteService, avaliacaoService);
		
		ReflectionTestUtils.setField(avaliacaoCamaraTecnicaService, "usuarioService", MockService.criarMockUsuarioService(3L, Arrays.asList(new Perfil(Perfis.AVALIADOR_CAMARA_TECNICA.getId())), 
				null));
		
		
	}
	
		
	@Test
	public void deveAprovarAvaliacaoDeCamaraTecnica() throws Exception{
		
		H2BasePaciente.criarPacienteAprovadoAvaliacaoInicial(mockMvc, pacienteService, avaliacaoService, logEvolucaoService,  new PacienteDto()
				.comADataNascimento(LocalDate.now().minusYears(75))
				.comONome("TESTE")
				.comONomeMae("NOME MAE")
				.comOCpf("93475288036"));
		
		ProcessoDTO processoAvaliacaoCamaraTecnica = new ProcessoDTO(2L, TiposTarefa.AVALIACAO_CAMARA_TECNICA.getTipoProcesso());
		TarefaDTO tarefaAvaliacaoCamaraTecnica = new TarefaDTO(processoAvaliacaoCamaraTecnica, TiposTarefa.AVALIACAO_CAMARA_TECNICA,  new Perfil(TiposTarefa.AVALIACAO_CAMARA_TECNICA.getPerfil().getId()));
		tarefaAvaliacaoCamaraTecnica.setId(2L);		
		tarefaAvaliacaoCamaraTecnica.setRelacaoEntidade(1L);
		
		MockService.criarMockListarTarefa(TiposTarefa.AVALIACAO_CAMARA_TECNICA, new JsonViewPage<TarefaDTO>(new PageImpl<TarefaDTO>(Arrays.asList(tarefaAvaliacaoCamaraTecnica))));
		
		String retornoListagemTarefas = mockMvc.perform(
				CreateMockHttpServletRequest.makeGet("/api/avaliacaocamaratecnica/tarefas")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.param("pagina", "0")
				.param("quantidadeRegistros", "10")				
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))				
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		RetornoPaginacao tarefas = BaseConfigurationTest.getObjectMapper().readValue(retornoListagemTarefas, RetornoPaginacao.class);
		HashMap<String,Object> obj = (HashMap<String, Object>) tarefas.getContent().get(0);
		
		Long idAvaliacao = new Long( "" + obj.get("relacaoEntidade"));
		HashMap<String, Object> processo =(HashMap<String, Object>) obj.get("processo");
		Long idProcesso = new Long( "" + processo.get("id"));
		Long idTarefa = new Long( "" + obj.get("id"));
		
		AtribuirTarefa.usuarioService = null;
		AtribuirTarefa.tarefaFeign = null;
		
		ObterTarefa.usuarioService = null;
		ObterTarefa.tarefaFeign = null;	
		
		//H2BasePaciente.atribuirTarefaParaUsuarioLogado(mockMvc, idProcesso, idTarefa);
		
		ReflectionTestUtils.setField(avaliacaoCamaraTecnicaService, "usuarioService", MockService.criarMockUsuarioService(24L, null, null));
		
		AvaliacaoCamaraTecnica avaliacao = new AvaliacaoCamaraTecnica();
		avaliacao.setId(idAvaliacao);
		
		MockMultipartFile avaliacaoJson = new MockMultipartFile("avaliacao", "", "application/json",BaseConfigurationTest.getObjectMapper().writeValueAsBytes(avaliacao));
		
		ProcessoDTO processoAvaliarExameHla = new ProcessoDTO(3L, TiposTarefa.AVALIAR_EXAME_HLA.getTipoProcesso());
		TarefaDTO tarefaAvaliarExameHla = new TarefaDTO(processoAvaliarExameHla, TiposTarefa.AVALIAR_EXAME_HLA,  new Perfil(TiposTarefa.AVALIAR_EXAME_HLA.getPerfil().getId()));
		tarefaAvaliarExameHla.setId(3L);		
		
		MockService.criarMockCriarTarefa(TiposTarefa.AVALIAR_EXAME_HLA, tarefaAvaliarExameHla);
					
		MockService.criarMockFecharTarefa(TiposTarefa.AVALIACAO_CAMARA_TECNICA, tarefaAvaliacaoCamaraTecnica);
		
		MockService.criarMockContarNotificacao(null, 0L);
		
//		mockMvc.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacaocamaratecnica/aprovar", "post")  
//				.file(obterArquivoTeste())
//				.file(avaliacaoJson)
//				.contentType(MediaType.MULTIPART_FORM_DATA)
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk());

		MockService.limparMockListarTarefa(TiposTarefa.AVALIACAO_CAMARA_TECNICA);
		MockService.limparMockCriarTarefa(TiposTarefa.AVALIAR_EXAME_HLA);		
		MockService.limparMockFecharTarefa(TiposTarefa.AVALIAR_PACIENTE);
				
	}

	@Test
	public void deveReprovarAvaliacaoDeCamaraTecnica() throws Exception{
		
		H2BasePaciente.criarPacienteAprovadoAvaliacaoInicial(mockMvc, pacienteService, avaliacaoService, logEvolucaoService, new PacienteDto()
				.comADataNascimento(LocalDate.now().minusYears(75))
				.comONome("TESTE")
				.comONomeMae("NOME MAE")
				.comOCpf("78878452033"));
		
		ProcessoDTO processoAvaliacaoCamaraTecnica = new ProcessoDTO(2L, TiposTarefa.AVALIACAO_CAMARA_TECNICA.getTipoProcesso());
		TarefaDTO tarefaAvaliacaoCamaraTecnica = new TarefaDTO(processoAvaliacaoCamaraTecnica, TiposTarefa.AVALIACAO_CAMARA_TECNICA,  new Perfil(TiposTarefa.AVALIACAO_CAMARA_TECNICA.getPerfil().getId()));
		tarefaAvaliacaoCamaraTecnica.setId(2L);		
		tarefaAvaliacaoCamaraTecnica.setRelacaoEntidade(2L);
		
		MockService.criarMockListarTarefa(TiposTarefa.AVALIACAO_CAMARA_TECNICA, new JsonViewPage<TarefaDTO>(new PageImpl<TarefaDTO>(Arrays.asList(tarefaAvaliacaoCamaraTecnica))));
		
		String retornoListagemTarefas = mockMvc.perform(
				CreateMockHttpServletRequest.makeGet("/api/avaliacaocamaratecnica/tarefas")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.param("pagina", "0")
				.param("quantidadeRegistros", "10")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))				
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		RetornoPaginacao tarefas = BaseConfigurationTest.getObjectMapper().readValue(retornoListagemTarefas, RetornoPaginacao.class);
		HashMap<String,Object> obj = (HashMap<String, Object>) tarefas.getContent().get(0);
		
		Long idAvaliacao = new Long( "" + obj.get("relacaoEntidade"));
		HashMap<String, Object> processo =(HashMap<String, Object>) obj.get("processo");
		Long idProcesso = new Long( "" + processo.get("id"));
		Long idTarefa = new Long( "" + obj.get("id"));
		
		AtribuirTarefa.usuarioService = null;
		AtribuirTarefa.tarefaFeign = null;
		
		ObterTarefa.usuarioService = null;
		ObterTarefa.tarefaFeign = null;
		
		//H2BasePaciente.atribuirTarefaParaUsuarioLogado(mockMvc, idProcesso, idTarefa);
		
		ReflectionTestUtils.setField(avaliacaoCamaraTecnicaService, "usuarioService", MockService.criarMockUsuarioService(24L, null, null));
		
		AvaliacaoCamaraTecnica avaliacao = new AvaliacaoCamaraTecnica();
		avaliacao.setId(idAvaliacao);
		MockMultipartFile avaliacaoJson = new MockMultipartFile("avaliacao", "", "application/json",BaseConfigurationTest.getObjectMapper().writeValueAsBytes(avaliacao));
		
		MockService.criarMockFecharTarefa(TiposTarefa.AVALIACAO_CAMARA_TECNICA, tarefaAvaliacaoCamaraTecnica);
		
//		mockMvc.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacaocamaratecnica/reprovar", "post")
//				.file(obterArquivoTeste())
//				.file(avaliacaoJson)
//				.contentType(MediaType.MULTIPART_FORM_DATA)
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk());
				
	}
	
	public MockMultipartFile obterArquivoTeste() throws Exception {
		File file = new File("teste_arquivo_pdf.pdf");
		FileInputStream in = new FileInputStream(file);
		return new MockMultipartFile("file", "teste_arquivo_pdf.pdf", "application/pdf", in);
    }

}
