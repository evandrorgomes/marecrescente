package br.org.cancer.redome.tarefa.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import br.org.cancer.redome.tarefa.model.Processo;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.TipoTarefa;
import br.org.cancer.redome.tarefa.model.domain.ITiposTarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusProcesso;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.model.domain.TipoProcesso;
import br.org.cancer.redome.tarefa.model.domain.TiposTarefa;
import br.org.cancer.redome.tarefa.persistence.IProcessoRepository;
import br.org.cancer.redome.tarefa.service.IProcessoService;
import br.org.cancer.redome.tarefa.util.BaseConfigurationTest;
import br.org.cancer.redome.tarefa.util.CreateMockHttpServletRequest;

public class TarefaControllerTest extends BaseConfigurationTest {
	
	@Autowired
	private IProcessoService processoService;
	
	@Autowired
	private IProcessoRepository processoRepository;
	
	@BeforeClass
	public static void setup() {
		makeAuthotization.paraUsuario("MEDICO")
			.addPerfil("MEDICO")
			.addRecurso("TESTE");
	}
	
	/**
	 * [SUCESSO]
	 * 
	 * Deve retornar <code>HttpStatus.OK</code> se o paciente for salvo com sucesso.
	 * 
	 * @throws Exception Se ocorrer algum erro ao utilizar o MockMVC.
	 */
	@Test
	public void deveRetornarOKAoCriarTarefaEmAbertoComSucesso() throws Exception {
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefas")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(montarTarfa(TiposTarefa.AVALIAR_EXAME_HLA, StatusTarefa.ABERTA, null)))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertNotNull(retorno);
		
		Tarefa tarefaCriada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertNotNull(tarefaCriada);
		Assert.assertEquals(TiposTarefa.AVALIAR_EXAME_HLA.getId(), tarefaCriada.getTipoTarefa().getId());
		Assert.assertEquals(tarefaCriada.getStatus(), StatusTarefa.ABERTA.getId());
		
	}
	
	@Test
	public void deveRetornarOKAoCriarTarefaJaAtribudaComSucesso() throws Exception {
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefas")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(montarTarfa(TiposTarefa.AVALIAR_EXAME_HLA, StatusTarefa.ATRIBUIDA, 1L)))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertNotNull(retorno);
		
		Tarefa tarefaCriada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertNotNull(tarefaCriada);
		Assert.assertEquals(TiposTarefa.AVALIAR_EXAME_HLA.getId(), tarefaCriada.getTipoTarefa().getId());
		Assert.assertEquals(tarefaCriada.getStatus(), StatusTarefa.ATRIBUIDA.getId());
		
		List<Tarefa> tarefasTimeout = processoService.listarTarefasFilhas(tarefaCriada.getId(), TiposTarefa.TIMEOUT_CONFERENCIA_EXAME.getId(), StatusTarefa.ABERTA.getId());
		Assert.assertTrue(CollectionUtils.isNotEmpty(tarefasTimeout));
		
	}
	
	@Test
	public void deveRetornarOKAoAtribuirTarefaEmAbertoComSucesso() throws Exception {
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefas")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(montarTarfa(TiposTarefa.AVALIAR_EXAME_HLA, StatusTarefa.ABERTA, null)))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertNotNull(retorno);
		
		Tarefa tarefaCriada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertNotNull(tarefaCriada);
		Assert.assertEquals(TiposTarefa.AVALIAR_EXAME_HLA.getId(), tarefaCriada.getTipoTarefa().getId());
		Assert.assertEquals(tarefaCriada.getStatus(), StatusTarefa.ABERTA.getId());
		
		retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefa/"+ tarefaCriada.getId().toString() + "/atribuir")
				.content("1")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		
		Tarefa tarefaAtribuida = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertEquals(tarefaCriada.getId(), tarefaAtribuida.getId());
		Assert.assertEquals(StatusTarefa.ATRIBUIDA.getId(), tarefaAtribuida.getStatus());
				
		List<Tarefa> tarefasTimeout = processoService.listarTarefasFilhas(tarefaAtribuida.getId(), TiposTarefa.TIMEOUT_CONFERENCIA_EXAME.getId(), StatusTarefa.ABERTA.getId());
		Assert.assertTrue(CollectionUtils.isNotEmpty(tarefasTimeout));
		
	}
	
	@Test
	public void deveRetornarOKAoCriarTarefaEmAbertoComFollowUpComSucesso() throws Exception {
		
		processoService.criarProcesso(Processo.builder()
				.tipo(TipoProcesso.BUSCA.getId())
				.status(StatusProcesso.ANDAMENTO.getId())
				.rmr(300000L)
				.build());
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefas")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(montarTarfa(TiposTarefa.PEDIDO_WORKUP, StatusTarefa.ABERTA, null)))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertNotNull(retorno);
		
		Tarefa tarefaCriada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertNotNull(tarefaCriada);
		Assert.assertEquals(TiposTarefa.PEDIDO_WORKUP.getId(), tarefaCriada.getTipoTarefa().getId());
		Assert.assertEquals(tarefaCriada.getStatus(), StatusTarefa.ABERTA.getId());
		
		List<Tarefa> tarefasFollowUp = processoService.listarTarefasFilhas(tarefaCriada.getId(), TiposTarefa.WORKUP_FOLLOW_UP.getId(), StatusTarefa.AGUARDANDO.getId());
		Assert.assertTrue(CollectionUtils.isNotEmpty(tarefasFollowUp));
		
	}
	
	@Test
	public void deveRetornarOKAoRemoverAtribuicaoTarefaComSucesso() throws Exception {
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefas")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(montarTarfa(TiposTarefa.AVALIAR_EXAME_HLA, StatusTarefa.ABERTA, null)))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertNotNull(retorno);
		
		Tarefa tarefaCriada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertNotNull(tarefaCriada);
		Assert.assertEquals(tarefaCriada.getStatus(), StatusTarefa.ABERTA.getId());
		
		retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefa/"+ tarefaCriada.getId().toString() + "/atribuir")
				.content("1")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		
		Tarefa tarefaAtribuida = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertEquals(tarefaCriada.getId(), tarefaAtribuida.getId());
		Assert.assertEquals(StatusTarefa.ATRIBUIDA.getId(), tarefaAtribuida.getStatus());
		
		retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefa/"+ tarefaAtribuida.getId().toString() + "/remveratribuicao")
				.content("1")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		
		Tarefa tarefaSemAtribuicao = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertEquals(tarefaAtribuida.getId(), tarefaSemAtribuicao.getId());
		Assert.assertEquals(StatusTarefa.ABERTA.getId(), tarefaSemAtribuicao.getStatus());
		
				
		List<Tarefa> tarefasTimeout = processoService.listarTarefasFilhas(tarefaSemAtribuicao.getId(), TiposTarefa.TIMEOUT_CONFERENCIA_EXAME.getId(), StatusTarefa.ABERTA.getId());
		Assert.assertTrue(CollectionUtils.isEmpty(tarefasTimeout));
		
	}
	
	
	@Test
	public void deveRetornarOKAoFecharTarefaEmAbertoComSucesso() throws Exception {
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefas")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(montarTarfa(TiposTarefa.AVALIAR_EXAME_HLA, StatusTarefa.ABERTA, null)))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertNotNull(retorno);
		
		Tarefa tarefaCriada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertNotNull(tarefaCriada);
		Assert.assertEquals(TiposTarefa.AVALIAR_EXAME_HLA.getId(), tarefaCriada.getTipoTarefa().getId());
		Assert.assertEquals(tarefaCriada.getStatus(), StatusTarefa.ABERTA.getId());
		
		retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefa/"+ tarefaCriada.getId().toString() + "/feita")
				.content("false")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Tarefa tarefaFechada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		Assert.assertNotNull(tarefaFechada);		
		Assert.assertEquals(tarefaFechada.getStatus(), StatusTarefa.FEITA.getId());		
		
	}	
	
	@Test
	public void deveRetornarOKAoFecharTarefaEmAbertoEProcessoComSucesso() throws Exception {
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefas")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(montarTarfa(TiposTarefa.AVALIAR_EXAME_HLA, StatusTarefa.ABERTA, null)))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertNotNull(retorno);
		
		Tarefa tarefaCriada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertNotNull(tarefaCriada);
		Assert.assertEquals(TiposTarefa.AVALIAR_EXAME_HLA.getId(), tarefaCriada.getTipoTarefa().getId());
		Assert.assertEquals(tarefaCriada.getStatus(), StatusTarefa.ABERTA.getId());
		
		retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefa/"+ tarefaCriada.getId().toString() + "/feita")
				.content("true")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Tarefa tarefaFechada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		Assert.assertNotNull(tarefaFechada);		
		Assert.assertEquals(tarefaFechada.getStatus(), StatusTarefa.FEITA.getId());
		Assert.assertEquals(tarefaFechada.getProcesso().getStatus(), StatusProcesso.TERMINADO.getId());
		
	}
	
	@Test
	public void deveRetornarOKAoFecharTarefaAtribuidaComSucesso() throws Exception {
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefas")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(montarTarfa(TiposTarefa.AVALIAR_EXAME_HLA, StatusTarefa.ABERTA, null)))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertNotNull(retorno);
		
		Tarefa tarefaCriada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertNotNull(tarefaCriada);
		Assert.assertEquals(TiposTarefa.AVALIAR_EXAME_HLA.getId(), tarefaCriada.getTipoTarefa().getId());
		Assert.assertEquals(tarefaCriada.getStatus(), StatusTarefa.ABERTA.getId());
		
		retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefa/"+ tarefaCriada.getId().toString() + "/atribuir")
				.content("1")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		
		Tarefa tarefaAtribuida = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertEquals(tarefaCriada.getId(), tarefaAtribuida.getId());
		Assert.assertEquals(StatusTarefa.ATRIBUIDA.getId(), tarefaAtribuida.getStatus());
				
		List<Tarefa> tarefasTimeout = processoService.listarTarefasFilhas(tarefaAtribuida.getId(), TiposTarefa.TIMEOUT_CONFERENCIA_EXAME.getId(), StatusTarefa.ABERTA.getId());
		Assert.assertTrue(CollectionUtils.isNotEmpty(tarefasTimeout));
		
		retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefa/"+ tarefaCriada.getId().toString() + "/feita")
				.content("false")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		
		Tarefa tarefaFeita = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertEquals(tarefaAtribuida.getId(), tarefaFeita.getId());
		Assert.assertEquals(StatusTarefa.FEITA.getId(), tarefaFeita.getStatus());
				
		tarefasTimeout = processoService.listarTarefasFilhas(tarefaAtribuida.getId(), TiposTarefa.TIMEOUT_CONFERENCIA_EXAME.getId(), StatusTarefa.ABERTA.getId());
		Assert.assertTrue(CollectionUtils.isEmpty(tarefasTimeout));
		
		
	}
	
	@Test
	public void deveRetornarOKAoFecharTarefaEmAbertoComFollowUpComSucesso() throws Exception {
		
		processoService.criarProcesso(Processo.builder()
				.tipo(TipoProcesso.BUSCA.getId())
				.status(StatusProcesso.ANDAMENTO.getId())
				.rmr(300000L)
				.build());
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefas")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(montarTarfa(TiposTarefa.PEDIDO_WORKUP, StatusTarefa.ABERTA, null)))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertNotNull(retorno);
		
		Tarefa tarefaCriada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertNotNull(tarefaCriada);
		Assert.assertEquals(TiposTarefa.PEDIDO_WORKUP.getId(), tarefaCriada.getTipoTarefa().getId());
		Assert.assertEquals(tarefaCriada.getStatus(), StatusTarefa.ABERTA.getId());
		
		List<Tarefa> tarefasFollowUp = processoService.listarTarefasFilhas(tarefaCriada.getId(), TiposTarefa.WORKUP_FOLLOW_UP.getId(), StatusTarefa.AGUARDANDO.getId());
		Assert.assertTrue(CollectionUtils.isNotEmpty(tarefasFollowUp));
		
		retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefa/"+ tarefaCriada.getId().toString() + "/feita")
				.content("false")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		
		Tarefa tarefaFeita = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertEquals(tarefaCriada.getId(), tarefaFeita.getId());
		Assert.assertEquals(StatusTarefa.FEITA.getId(), tarefaFeita.getStatus());
		
		tarefasFollowUp = processoService.listarTarefasFilhas(tarefaCriada.getId(), TiposTarefa.WORKUP_FOLLOW_UP.getId(), StatusTarefa.AGUARDANDO.getId());
		Assert.assertTrue(CollectionUtils.isEmpty(tarefasFollowUp));
		
	}
	
	@Test
	public void deveRetornarOKAoCancelarTarefaEmAbertoComSucesso() throws Exception {
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefas")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(montarTarfa(TiposTarefa.AVALIAR_EXAME_HLA, StatusTarefa.ABERTA, null)))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertNotNull(retorno);
		
		Tarefa tarefaCriada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertNotNull(tarefaCriada);
		Assert.assertEquals(TiposTarefa.AVALIAR_EXAME_HLA.getId(), tarefaCriada.getTipoTarefa().getId());
		Assert.assertEquals(tarefaCriada.getStatus(), StatusTarefa.ABERTA.getId());
		
		retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefa/"+ tarefaCriada.getId().toString() + "/cancelar")
				.content("false")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Tarefa tarefaCancelada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		Assert.assertNotNull(tarefaCancelada);		
		Assert.assertEquals(tarefaCancelada.getStatus(), StatusTarefa.CANCELADA.getId());		
		
	}
	
	@Test
	public void deveRetornarOKAoCancelarTarefaEmAbertoEProcessoComSucesso() throws Exception {
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefas")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(montarTarfa(TiposTarefa.AVALIAR_EXAME_HLA, StatusTarefa.ABERTA, null)))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertNotNull(retorno);
		
		Tarefa tarefaCriada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertNotNull(tarefaCriada);
		Assert.assertEquals(TiposTarefa.AVALIAR_EXAME_HLA.getId(), tarefaCriada.getTipoTarefa().getId());
		Assert.assertEquals(tarefaCriada.getStatus(), StatusTarefa.ABERTA.getId());
		
		retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefa/"+ tarefaCriada.getId().toString() + "/cancelar")
				.content("true")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Tarefa tarefaCancelada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		Assert.assertNotNull(tarefaCancelada);		
		Assert.assertEquals(tarefaCancelada.getStatus(), StatusTarefa.CANCELADA.getId());
		Assert.assertEquals(tarefaCancelada.getProcesso().getStatus(), StatusProcesso.CANCELADO.getId());
		
	}
	
	@Test
	public void deveRetornarOKAoCancelarTarefaAtribuidaComSucesso() throws Exception {
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefas")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(montarTarfa(TiposTarefa.AVALIAR_EXAME_HLA, StatusTarefa.ABERTA, null)))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertNotNull(retorno);
		
		Tarefa tarefaCriada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertNotNull(tarefaCriada);
		Assert.assertEquals(TiposTarefa.AVALIAR_EXAME_HLA.getId(), tarefaCriada.getTipoTarefa().getId());
		Assert.assertEquals(tarefaCriada.getStatus(), StatusTarefa.ABERTA.getId());
		
		retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefa/"+ tarefaCriada.getId().toString() + "/atribuir")
				.content("1")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		
		Tarefa tarefaAtribuida = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertEquals(tarefaCriada.getId(), tarefaAtribuida.getId());
		Assert.assertEquals(StatusTarefa.ATRIBUIDA.getId(), tarefaAtribuida.getStatus());
				
		List<Tarefa> tarefasTimeout = processoService.listarTarefasFilhas(tarefaAtribuida.getId(), TiposTarefa.TIMEOUT_CONFERENCIA_EXAME.getId(), StatusTarefa.ABERTA.getId());
		Assert.assertTrue(CollectionUtils.isNotEmpty(tarefasTimeout));
		
		retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefa/"+ tarefaCriada.getId().toString() + "/cancelar")
				.content("false")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		
		Tarefa tarefaCancelada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertEquals(tarefaAtribuida.getId(), tarefaCancelada.getId());
		Assert.assertEquals(StatusTarefa.CANCELADA.getId(), tarefaCancelada.getStatus());
				
		tarefasTimeout = processoService.listarTarefasFilhas(tarefaAtribuida.getId(), TiposTarefa.TIMEOUT_CONFERENCIA_EXAME.getId(), StatusTarefa.ABERTA.getId());
		Assert.assertTrue(CollectionUtils.isEmpty(tarefasTimeout));
		
		
	}
	
	@Test
	public void deveRetornarOKAoCancelarTarefaEmAbertoComFollowUpComSucesso() throws Exception {
		
		processoService.criarProcesso(Processo.builder()
				.tipo(TipoProcesso.BUSCA.getId())
				.status(StatusProcesso.ANDAMENTO.getId())
				.rmr(300000L)
				.build());
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefas")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(montarTarfa(TiposTarefa.PEDIDO_WORKUP, StatusTarefa.ABERTA, null)))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertNotNull(retorno);
		
		Tarefa tarefaCriada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertNotNull(tarefaCriada);
		Assert.assertEquals(TiposTarefa.PEDIDO_WORKUP.getId(), tarefaCriada.getTipoTarefa().getId());
		Assert.assertEquals(tarefaCriada.getStatus(), StatusTarefa.ABERTA.getId());
		
		List<Tarefa> tarefasFollowUp = processoService.listarTarefasFilhas(tarefaCriada.getId(), TiposTarefa.WORKUP_FOLLOW_UP.getId(), StatusTarefa.AGUARDANDO.getId());
		Assert.assertTrue(CollectionUtils.isNotEmpty(tarefasFollowUp));
		
		retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/tarefa/"+ tarefaCriada.getId().toString() + "/cancelar")
				.content("false")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		
		Tarefa tarefaCancelada = BaseConfigurationTest.getObjectMapper().readValue(retorno, Tarefa.class);
		
		Assert.assertEquals(tarefaCriada.getId(), tarefaCancelada.getId());
		Assert.assertEquals(StatusTarefa.CANCELADA.getId(), tarefaCancelada.getStatus());
		
		tarefasFollowUp = processoService.listarTarefasFilhas(tarefaCriada.getId(), TiposTarefa.WORKUP_FOLLOW_UP.getId(), StatusTarefa.AGUARDANDO.getId());
		Assert.assertTrue(CollectionUtils.isEmpty(tarefasFollowUp));
		
	}
	
	
	
	private Tarefa montarTarfa(ITiposTarefa tiposTarefa, StatusTarefa status, Long idUsuario) {
		
		return Tarefa.builder().descricao("Teste para criar Tarefa")
				.processo(Processo.builder().tipo(tiposTarefa.getConfiguracao().getTipoProcesso().getId()).rmr(300000L).build())
				.status(status.getId())				
				.tipoTarefa(new TipoTarefa(tiposTarefa.getId()))
				.usuarioResponsavel(idUsuario)
				.build();
		
	}
	

}
