package br.org.cancer.modred.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.MediaType;

import br.org.cancer.modred.controller.dto.PacienteDto;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Responsavel;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;
import br.org.cancer.modred.test.util.MockService;
import br.org.cancer.modred.test.util.RetornoControladorErro;
import br.org.cancer.modred.test.util.base.H2BasePaciente;

/**
 * Classe de teste do controller do paciente
 * 
 * @author bruno.sousa
 *
 */
public class H2PacienteControllerTest extends BaseConfigurationTest {

	private static final String RETORNO_NAO_PODE_SER_VAZIO = "não pode ser vazio";
	private static final String RETORNO_CAMPO_NOME = "nome";
		
	@BeforeClass
	public static void setup() {
		makeAuthotization.paraUsuario("MEDICO")
			.addPerfil("MEDICO", "MEDICO_AVALIADOR")
			.addRecurso(
						"ACEITAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
						"ALTERAR_CADASTRO_MEDICO",
						"ANALISE_MATCH",
						"ANALISE_MATCH_PRELIMINAR",
						"AVALIAR_PACIENTE",
						"BAIXAR_SOLICITACAO_EXAME_CLASSE2",
						"CADASTRAR_BUSCA_PRELIMINAR",
						"CADASTRAR_EVOLUCOES_PACIENTE",
						"CADASTRAR_EXAMES_PACIENTE",
						"CADASTRAR_PACIENTE",
						"CANCELAR_BUSCA",
						"CONSULTAR_AVALIACOES",
						"CONSULTAR_EVOLUCOES_PACIENTE",
						"CONSULTAR_EVOLUCOES_PACIENTE",
						"CONSULTAR_EXAMES_PACIENTE",
						"CONSULTAR_EXAMES_PACIENTE",
						"CONSULTAR_PACIENTE",
						"CONSULTAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
						"CRIAR_PRESCRICAO",
						"DETALHE_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
						"DIALOGO_BUSCA",
						"EDITAR_CONTATO_PACIENTE",
						"EDITAR_DADOS_PESSOAIS",
						"EDITAR_DIAGNOSTICO_PACIENTE",
						"EDITAR_MISMATCH_PACIENTE",
						"EXCLUIR_EMAIL_CONTATO_MEDICO",
						"EXCLUIR_TELEFONE_CONTATO_MEDICO",
						"EXIBIR_HISTORICO_MATCH",
						"LISTAR_ARQUIVOS_RELATORIO_BUSCA_INTERNACIONAL",
						"PACIENTES_PARA_PROCESSO_BUSCA",
						"RECUSAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
						"SOLICITAR_FASE3_PACIENTE",
						"SOLICITAR_NOVA_BUSCA_PACIENTE",
						"TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
						"VISUALIZAR_AVALIACAO",
						"VISUALIZAR_CADASTRO_MEDICO",
						"VISUALIZAR_FAVORITO_MATCH",
						"VISUALIZAR_FICHA_PACIENTE",
						"VISUALIZAR_FICHA_PACIENTE",
						"VISUALIZAR_IDENTIFICACAO_COMPLETA",
						"VISUALIZAR_IDENTIFICACAO_COMPLETA_DOADOR",
						"VISUALIZAR_IDENTIFICACAO_PARCIAL",
						"VISUALIZAR_LOG_EVOLUCAO",
						"VISUALIZAR_MATCH_PRELIMINAR",
						"VISUALIZAR_MOTIVOS_CANCELAMENTO_BUSCA",
						"VISUALIZAR_NOTIFICACOES",
						"VISUALIZAR_PENDENCIAS_AVALIACAO",
						"VISUALIZAR_PENDENCIAS_AVALIACAO");
	}
	
	
	/**
	 * [SUCESSO]
	 * 
	 * Deve retornar <code>HttpStatus.OK</code> se o paciente for salvo com sucesso.
	 * 
	 * @throws Exception Se ocorrer algum erro ao utilizar o MockMVC.
	 */
	@Test
	public void deveRetornarOKAoSalvarPacienteComSucesso() throws Exception {
		
		RetornoControladorErro retornoControlador = H2BasePaciente.criarPaciente(mockMvc, new PacienteDto()
				.comONome("TESTE PACIENTE")
				.comADataNascimento(LocalDate.now().minusYears(25))
				.comOCpf("35562865807")
				.comOCns("117349522170001"));
		
		Long rmr = H2BasePaciente.obterRMR(retornoControlador);
		Assert.assertNotNull(rmr);
		//Processo processo = processoService.obterProcessoAtivo(TipoProcesso.AVALIACAO_PACIENTE_PARA_BUSCA, rmr);
		
		//Assert.assertNotNull(processo);
		
		/*
		 * TarefaDTO tarefaAvaliacao =
		 * TiposTarefa.AVALIAR_PACIENTE.getConfiguracao().obterTarefa()
		 * .comProcessoId(processo.getId())
		 * .comStatus(Arrays.asList(StatusTarefa.ABERTA)) .apply();
		 * 
		 * Assert.assertNotNull(tarefaAvaliacao);
		 */
		
	}


	
	/**
	 * [SUCESSO]
	 * 
	 * Deve retornar <code>HttpStatus.OK</code> se o paciente for salvo com sucesso.
	 * 
	 * @throws Exception Se ocorrer algum erro ao utilizar o MockMVC.
	 */
	@Test
	public void deveRetornarErroValidcaoPaciente() throws Exception {
		
		final Paciente paciente = H2BasePaciente.obterPacientePopulado(null, null, LocalDate.now().minusYears(25), "22461478678", "279793726960008", null);
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/pacientes")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(paciente)).contentType(BaseConfigurationTest.CONTENT_TYPE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))				
				.andExpect(status().is4xxClientError()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		RetornoControladorErro retornoControlador = BaseConfigurationTest.getObjectMapper().readValue(retorno, RetornoControladorErro.class);
		Assert.assertEquals(RETORNO_CAMPO_NOME, retornoControlador.getErros().get(0).getCampo());
		Assert.assertEquals(RETORNO_NAO_PODE_SER_VAZIO, retornoControlador.getErros().get(0).getMensagem());
	}
	
	
	/**
	 * [SUCESSO]
	 * 
	 * Deve retornar <code>HttpStatus.OK</code> se o paciente for salvo com sucesso.
	 * 
	 * @throws Exception Se ocorrer algum erro ao utilizar o MockMVC.
	 */
	@Test
	public void deveCadastrarPacienteMenorDeIdade() throws Exception {
		
		Responsavel responsavel = criarResponsavel();

		RetornoControladorErro retornoControlador = H2BasePaciente.criarPaciente(mockMvc, new PacienteDto()
				.comONome("TESTE PACIENTE")
				.comADataNascimento(LocalDate.now().minusYears(5))
				.comOCpf("33914143240")
				.comOCns(null)
				.comOResponsavel(responsavel));
		
		Long rmrRetorno = H2BasePaciente.obterRMR(retornoControlador);
		Assert.assertNotNull(rmrRetorno);
		
		/*
		 * Processo processo = processoService.obterProcessoAtivo(TipoProcesso.
		 * AVALIACAO_PACIENTE_PARA_BUSCA, rmrRetorno); Assert.assertNotNull(processo);
		 * 
		 * TarefaDTO tarefaAvaliacao = TiposTarefa.AVALIAR_PACIENTE .getConfiguracao()
		 * .obterTarefa() .comProcessoId(processo.getId())
		 * .comStatus(Arrays.asList(StatusTarefa.ABERTA)) .apply();
		 * 
		 * Assert.assertNotNull(tarefaAvaliacao);
		 */
	}



	private Responsavel criarResponsavel() {
		Responsavel responsavel = new Responsavel();
		responsavel.setCpf("74234160377");
		responsavel.setNome("TESTE RESPONSAVEL");
		responsavel.setParentesco("TIO");
		return responsavel;
	}
	
	@Test
	public void deveRetornarFichaPacienteComSucesso() throws Exception {
		
		RetornoControladorErro retornoControlador = H2BasePaciente.criarPaciente(mockMvc, new PacienteDto()
				.comONome("TESTE PACIENTE")
				.comADataNascimento(LocalDate.now().minusYears(25))
				.comOCpf("74234160377")
				.comOCns("930170376830008"));
		
		Long rmr = H2BasePaciente.obterRMR(retornoControlador);
		
		MockService.criarMockContarNotificacao(null, 0L);
		
		String retorno = mockMvc.perform(CreateMockHttpServletRequest.makeGet("/api/pacientes/" + rmr))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		Paciente pacienteRecuperado = getObjectMapper().readValue(retorno, Paciente.class);
		Assert.assertEquals(rmr, pacienteRecuperado.getRmr());
		final Paciente paciente = H2BasePaciente.obterPacientePopulado("TESTE PACIENTE", null, LocalDate.now().minusYears(25), "74234160377", "930170376830008", null);
		Assert.assertEquals(paciente.getNome(), pacienteRecuperado.getNome());
	}
	
	
	@Test
	public void deveObterPacienteWmdaPorBusca() throws Exception {
		mockMvc
		.perform(CreateMockHttpServletRequest.makeGet("/api/pacientes/wmda/42489")
		.contentType(BaseConfigurationTest.CONTENT_TYPE))
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
	}

//	@Test
//	public void deveAtualizarWmdaIdPorRmrDoPaciente() throws Exception {
//		
//		RetornoControladorErro retornoControlador = H2BasePaciente.criarPaciente(mockMvc, new PacienteDto()
//				.comONome("TESTE PACIENTE")
//				.comADataNascimento(LocalDate.now().minusYears(25))
//				.comOCpf("74234160377")
//				.comOCns("930170376830008")
//				.comORmr(3047084L)
//				.comOWmdaId(21011L));
//		
//		Long rmr = H2BasePaciente.obterRMR(retornoControlador);  
//		
//		mockMvc
//		.perform(CreateMockHttpServletRequest.makePut("/api/pacientes/wmda/" + rmr)
//		.content("21011")
//		.contentType(BaseConfigurationTest.CONTENT_TYPE))
//		.andExpect(status().isOk())
//		.andReturn().getResponse().getContentAsString();
//	}

}