package br.org.cancer.redome.workup.controller;


import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import com.github.tomakehurst.wiremock.client.WireMock;

import br.org.cancer.redome.workup.dto.AprovarAvaliacaoPrescricaoDTO;
import br.org.cancer.redome.workup.dto.BuscaDTO;
import br.org.cancer.redome.workup.dto.CentroTransplanteDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.MatchDTO;
import br.org.cancer.redome.workup.dto.PacienteDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoSolicitacaoDTO;
import br.org.cancer.redome.workup.dto.UsuarioDTO;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposDoador;
import br.org.cancer.redome.workup.model.domain.TiposSolicitacao;
import br.org.cancer.redome.workup.util.BaseConfigurationTest;
import br.org.cancer.redome.workup.util.CreateMockHttpServletRequest;

public class AvaliacaoPrescricaoControllerTest extends BaseConfigurationTest {
		
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(13L, "MEDICO_REDOME")
			.addPerfil("MEDICO_REDOME")
			.addRecurso("AVALIAR_PRESCRICAO");
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}
	
	@Test
	public void deveAprovarAvaliacaoPresricaoDoadorNacionalComSucesso() throws Exception {
				
		SolicitacaoDTO solicitacao = criarSolicitacao(1L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL, 
														UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(), 
														criarMatch(1L, 1L, "4353344", criarBusca(16L, 300000L)));
		
		makeStubForPut("/redome/api/solicitacoes/1/atualizarfaseworkup", 
			okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
						
		TarefaDTO tarefaAvaliacao = TarefaDTO.builder().id(1L).build();
				
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOls0OV0sInN0YXR1c1RhcmVmYSI6WzJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOjEsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6MTN9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefaAvaliacao))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAvaliacao)));
				
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
						
		mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/avaliacaoprescricao/1/aprovar")
		        .contentType(MediaType.APPLICATION_JSON_UTF8))				
				.andExpect(status().isOk())
				.andExpect(content().string("Prescrição aprovada com sucesso."));
	}
	
	@Test
	public void deveAprovarAvaliacaoPresricaoDoadorNacionalDescartandoUmaFonteDeCelulaComSucesso() throws Exception {
				
		SolicitacaoDTO solicitacao = criarSolicitacao(1L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL, 
														UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(), 
														criarMatch(1L, 1L, "4353344", criarBusca(16L, 300000L)));
		
		makeStubForPut("/redome/api/solicitacoes/1/atualizarfaseworkup", 
			okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
						
		TarefaDTO tarefaAvaliacao = TarefaDTO.builder().id(1L).build();
				
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOls0OV0sInN0YXR1c1RhcmVmYSI6WzJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOjIsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6MTN9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefaAvaliacao))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAvaliacao)));
				
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		AprovarAvaliacaoPrescricaoDTO avaliacaoPrescricaoDTO = new AprovarAvaliacaoPrescricaoDTO("Teste de aprovação com descarte de uma fonte de celula", 1L);
				
		mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/avaliacaoprescricao/2/aprovar")
		        .contentType(MediaType.APPLICATION_JSON_UTF8)
		        .content(getObjectMapper().writeValueAsString(avaliacaoPrescricaoDTO)))
				.andExpect(status().isOk())
				.andExpect(content().string("Prescrição aprovada com sucesso."));
	}
	
	@Test
	public void deveAprovarAvaliacaoPresricaoDoadorInternacional() throws Exception {
				
		SolicitacaoDTO solicitacao = criarSolicitacao(1L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL, 
														UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(), 
														criarMatch(1L, 1L, "4353344", criarBusca(16L, 300000L)));
		
		makeStubForPut("/redome/api/solicitacoes/1/atualizarfaseworkup", 
			okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
						
		TarefaDTO tarefaAutorizacao = TarefaDTO.builder().id(2L).build();
		
		makeStubForPost("/redome-tarefa/api/tarefas",
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAutorizacao)));
				
		TarefaDTO tarefaAvaliacao = TarefaDTO.builder().id(1L).build();
				
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOls0OV0sInN0YXR1c1RhcmVmYSI6WzJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOjMsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6MTN9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefaAvaliacao))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAvaliacao)));
				
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/avaliacaoprescricao/3/aprovar")
		        .contentType(MediaType.APPLICATION_JSON_UTF8))		        
				.andExpect(status().isOk())
				.andExpect(content().string("Prescrição aprovada com sucesso."));
	}
	
	@Test
	public void naoDeveAprovarAvaliacaoPresricaoSeNaoExistir() throws Exception {
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/avaliacaoprescricao/4/aprovar")
        .contentType(MediaType.APPLICATION_JSON_UTF8))		        
		.andExpect(status().is4xxClientError())
		.andExpect(content().string("Avaliação de prescrição não existe."));
		
	}
	
	@Test
	public void naoDeveAprovarAvaliacaoPresricaoSeAvaliacaoJaTenhaSidoFeita() throws Exception {
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/avaliacaoprescricao/5/aprovar")
        .contentType(MediaType.APPLICATION_JSON_UTF8))		        
		.andExpect(status().is4xxClientError())
		.andExpect(content().string("Avaliação da Prescrição já realizada."));
		
	}
	
	@Test
	public void naoDeveAprovarAvaliacaoPresricaoSePrescricaoPossuirApenasUmaFonte() throws Exception {
		
		AprovarAvaliacaoPrescricaoDTO avaliacaoPrescricaoDTO = new AprovarAvaliacaoPrescricaoDTO(null, 1L);
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/avaliacaoprescricao/6/aprovar")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
		.content(getObjectMapper().writeValueAsString(avaliacaoPrescricaoDTO)))
		.andExpect(status().is4xxClientError())
		.andExpect(content().string("Não é possível descartar a Fonte de celula. Prescrição possui apenas uma opção de fonte de celula."));
		
	}
	
	@Test
	public void naoDeveAprovarAvaliacaoPresricaoSeJustificativaNulo() throws Exception {
		
		AprovarAvaliacaoPrescricaoDTO avaliacaoPrescricaoDTO = new AprovarAvaliacaoPrescricaoDTO(null, 1L);
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/avaliacaoprescricao/3/aprovar")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
		.content(getObjectMapper().writeValueAsString(avaliacaoPrescricaoDTO)))
		.andExpect(status().is4xxClientError())
		.andExpect(content().string("É necessário informar o motivo de descarte da fonte celula."));
		
	}
	
	@Test
	public void naoDeveAprovarAvaliacaoFonteCelulaDiferenteDaPrescricao() throws Exception {
		
		AprovarAvaliacaoPrescricaoDTO avaliacaoPrescricaoDTO = new AprovarAvaliacaoPrescricaoDTO(null, 3L);
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/avaliacaoprescricao/7/aprovar")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
		.content(getObjectMapper().writeValueAsString(avaliacaoPrescricaoDTO)))
		.andExpect(status().is4xxClientError())
		.andExpect(content().string("Fonte de celula descartada não faz parte da prescrição."));
		
	}
	
	@Test
	public void deveReprovarAvaliacaoPresricaoDoadorNacionalComSucesso() throws Exception {
				
		SolicitacaoDTO solicitacao = criarSolicitacao(1L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL, 
														UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(), 
														criarMatch(1L, 1L, "4353344", criarBusca(16L, 300000L)));
		
		makeStubForPost("/redome/api/solicitacoes/1/cancelar/workup", 
			okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
						
		TarefaDTO tarefaAvaliacao = TarefaDTO.builder().id(1L).build();
				
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOls0OV0sInN0YXR1c1RhcmVmYSI6WzJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOjgsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6MTN9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefaAvaliacao))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAvaliacao)));
				
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
				
						
		mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/avaliacaoprescricao/8/reprovar")
				.content("teste de reprovação")
		        .contentType(MediaType.APPLICATION_JSON_UTF8))				
				.andExpect(status().isOk())
				.andExpect(content().string("Prescrição foi reprovada."));
	}
	
	@Test
	public void naoDeveReprovarAvaliacaoPresricaoDoadorNacionalSemJustificatva() throws Exception {
				
		mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/avaliacaoprescricao/9/reprovar")				
		        .contentType(MediaType.APPLICATION_JSON_UTF8))				
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void naoDeveReprovarAvaliacaoPresricaoSeAvaliacaoJaTenhaSidoFeita() throws Exception {
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/avaliacaoprescricao/5/reprovar")
		.content("teste")
        .contentType(MediaType.APPLICATION_JSON_UTF8))		        
		.andExpect(status().is4xxClientError())
		.andExpect(content().string("Avaliação da Prescrição já realizada."));
		
	}
	
	@Test
	public void deveListarAvaliacoesPrescricaoComSucesso() throws Exception {
		ProcessoDTO processo = ProcessoDTO.builder().id(1L).tipo(TipoProcesso.BUSCA).build();
		processo.setRmr(300000L);
		processo.setNomePaciente("nome do paciente");
		TarefaDTO tarefaAvaliacao = TarefaDTO.builder().id(1L)
				.processo(processo)
				.relacaoEntidade(10L)
				.build();
		
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOls0OV0sInN0YXR1c1RhcmVmYSI6WzEsMl0sInN0YXR1c1Byb2Nlc3NvIjoxLCJwcm9jZXNzb0lkIjpudWxsLCJpbmNsdXNpdm9FeGNsdXNpdm8iOm51bGwsInBhZ2VhYmxlIjpudWxsLCJyZWxhY2FvRW50aWRhZGVJZCI6bnVsbCwicm1yIjpudWxsLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjpudWxsLCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6MTN9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefaAvaliacao))));
		
		SolicitacaoDTO solicitacao = criarSolicitacao(1L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL, 
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(), 
				criarMatch(1L, 1L, "4353344", criarBusca(16L, 300000L)));
		solicitacao.getMatch().getDoador().setTipoDoador(TiposDoador.NACIONAL.name());

		makeStubForGet("/redome/api/solicitacoes/1", 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeGet("/api/avaliacoesprescricao")
			.param("pagina", "0")
			.param("quantidadeRegistros", "10"))		        
			.andExpect(status().isOk())
			.andDo(print());
		
	}
	
	
	private SolicitacaoDTO criarSolicitacao(Long id, TiposSolicitacao tipo,  UsuarioDTO usuario, MatchDTO match) {
		return SolicitacaoDTO.builder()
				.id(id)
				.status(0)
				.tipoSolicitacao(TipoSolicitacaoDTO.builder().id(tipo.getId()).build())
				.usuario(usuario)
				.match(match)
				.build();
	}
	
	private MatchDTO criarMatch(Long idMatch, Long idDoador, String identificacaoDoador, BuscaDTO busca) {
		return MatchDTO.builder()
				.id(idMatch)
				.doador(DoadorDTO.builder()
						.id(idDoador)
						.Identificacao(identificacaoDoador)
						.build())
				.busca(busca)

				.build();
	}
	
	private BuscaDTO criarBusca(Long idCentroTransplante, Long rmr) {
		return BuscaDTO.builder()
				.centroTransplante(CentroTransplanteDTO.builder()
						.id(idCentroTransplante)
						.build())
				.paciente(PacienteDTO.builder()
						.rmr(rmr)
						.build())
				.build();
	}

}
