package br.org.cancer.modred.test.util.base;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import br.org.cancer.modred.controller.dto.AvaliacaoDTO;
import br.org.cancer.modred.controller.dto.PacienteDto;
import br.org.cancer.modred.feign.dto.ProcessoDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.ArquivoEvolucao;
import br.org.cancer.modred.model.ArquivoExame;
import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Cid;
import br.org.cancer.modred.model.CondicaoPaciente;
import br.org.cancer.modred.model.ContatoTelefonicoPaciente;
import br.org.cancer.modred.model.Diagnostico;
import br.org.cancer.modred.model.EnderecoContatoPaciente;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.model.LocusExamePk;
import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.model.Metodologia;
import br.org.cancer.modred.model.Motivo;
import br.org.cancer.modred.model.Municipio;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Raca;
import br.org.cancer.modred.model.Responsavel;
import br.org.cancer.modred.model.Uf;
import br.org.cancer.modred.model.domain.Abo;
import br.org.cancer.modred.model.domain.AceiteMismatch;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.Sexo;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.IAvaliacaoService;
import br.org.cancer.modred.service.ILogEvolucaoService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.impl.AvaliacaoService;
import br.org.cancer.modred.service.impl.ExamePacienteService;
import br.org.cancer.modred.service.impl.ExecutarProcedureMatchService;
import br.org.cancer.modred.service.impl.GenotipoPacienteService;
import br.org.cancer.modred.service.impl.PacienteService;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;
import br.org.cancer.modred.test.util.MockService;
import br.org.cancer.modred.test.util.RetornoControladorErro;
import br.org.cancer.modred.test.util.RetornoPaginacao;

/**
 * Classe utilitária de criação de cenários específicos para testes 
 * de persistencia com banco de memoria.
 * 
 * @author Filipe Paes
 *
 */
public class H2BasePaciente {


	/**
	 * Cria paciente com tarefa inicial de avaliação.
	 * @param mockMvc objeto de mock vindo do contexto do teste.
	 * @param pacienteDto paciente a ser persistido.
	 * @return objeto com mensagens de retorno do servidor rest.
	 * @throws Exception
	 */
	public static RetornoControladorErro criarPaciente(MockMvc mockMvc, PacienteDto pacienteDto) throws Exception {
		
		File file = new File("laudo_teste.png");
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "laudo_teste.png", "image/png", in);

		String exameFilePath = enviarArquivoExame(mockMvc, mockMultipartFile);
		String evolucaoFilePath = enviarArquivoEvolucao(mockMvc, mockMultipartFile);

		final Paciente paciente = obterPacientePopulado(pacienteDto.getNome(), pacienteDto.getNomeMae(), pacienteDto.getDataNascimento(),
				pacienteDto.getCpf(),pacienteDto.getCns(),pacienteDto.getResponsavel());
		paciente.getExames().get(0).getArquivosExame().get(0).setCaminhoArquivo(exameFilePath);
		ArquivoEvolucao arquivoEvolucao = new ArquivoEvolucao();
		arquivoEvolucao.setCaminhoArquivo(evolucaoFilePath);
		paciente.getEvolucoes().get(0).setArquivosEvolucao(Arrays.asList(arquivoEvolucao));
		
		ProcessoDTO processo = new ProcessoDTO(1L, TiposTarefa.AVALIAR_PACIENTE.getTipoProcesso());
		TarefaDTO tarefa = new TarefaDTO(processo, TiposTarefa.AVALIAR_PACIENTE, new Perfil(TiposTarefa.AVALIAR_PACIENTE.getPerfil().getId()));
		tarefa.setId(1L);
		
		MockService.criarMockCriarTarefa(TiposTarefa.AVALIAR_PACIENTE, tarefa);
						
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/pacientes")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(paciente))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		MockService.limparMockCriarTarefa(TiposTarefa.AVALIAR_PACIENTE);

		return BaseConfigurationTest.getObjectMapper().readValue(retorno, RetornoControladorErro.class);
	}

	/**
	 * Cria um paciente pega a tarefa de avaliacao, atribui para o usuário logado e obtém a Id da avaliação gerada.
	 * @param mockMvc objeto de mock vindo do contexto do teste.
	 * @param pacienteDto paciente a ser persistido.
	 * @return objeto com mensagens de retorno do servidor rest.
	 * @throws Exception
	 */
	public static Long criarPacienteComAvaliacaoInicial(MockMvc mockMvc, IPacienteService pacienteService, ILogEvolucaoService logEvolucaoService, PacienteDto pacienteDto) throws Exception{

		ProcessoDTO processo = new ProcessoDTO(1L, TiposTarefa.AVALIAR_PACIENTE.getTipoProcesso());
		TarefaDTO tarefa = new TarefaDTO(processo, TiposTarefa.AVALIAR_PACIENTE, new Perfil(TiposTarefa.AVALIAR_PACIENTE.getPerfil().getId()));
		tarefa.setId(1L);
		
		MockService.criarMockCriarTarefa(TiposTarefa.AVALIAR_PACIENTE, tarefa);
		
		Paciente paciente = obterPacientePopulado(pacienteDto.getNome(), pacienteDto.getNomeMae(), pacienteDto.getDataNascimento(),
				pacienteDto.getCpf(),pacienteDto.getCns(),pacienteDto.getResponsavel());
		
		MockService.criarMockContarNotificacao(null, 0L);
		ReflectionTestUtils.setField(logEvolucaoService, "usuarioService", MockService.criarMockUsuarioService(1L, null, null));
		
		pacienteService.salvar(paciente);
		
		MockService.limparMockCriarTarefa(TiposTarefa.AVALIAR_PACIENTE);
		
		processo.setPaciente(new Paciente(paciente.getRmr()));
		
		MockService.criarMockListarTarefa(TiposTarefa.AVALIAR_PACIENTE, new JsonViewPage<>(new PageImpl<>(Arrays.asList(tarefa))));
		
		String retornoListagemTarefas = mockMvc.perform(
				CreateMockHttpServletRequest.makeGet("/api/avaliacoes/ABERTA")
				.param("pagina", "0")
				.param("quantidadeRegistros", "10")
				.param("idCentroTransplante", "2")				
				.contentType(BaseConfigurationTest.CONTENT_TYPE))				
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		RetornoPaginacao tarefas = BaseConfigurationTest.getObjectMapper().readValue(retornoListagemTarefas, RetornoPaginacao.class);
		
		Assert.assertTrue(tarefas.getContent().stream().map(obj ->  (HashMap<String, Object>)obj )
			.anyMatch(obj ->  paciente.getRmr().equals(new Long(obj.get("rmr").toString()))));
		
		MockService.limparMockListarTarefa(TiposTarefa.AVALIAR_PACIENTE);
						
		String retornoAvaliacaoAtual = mockMvc.perform(
				CreateMockHttpServletRequest.makeGet("/api/pacientes/" + paciente.getRmr().toString() + "/avaliacoes/atual")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))				
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		
		AvaliacaoDTO avaliacaoDTO =  BaseConfigurationTest.getObjectMapper().readValue(retornoAvaliacaoAtual, AvaliacaoDTO.class);
		
		Assert.assertNotNull(avaliacaoDTO);
		Assert.assertNotNull(avaliacaoDTO.getAvaliacaoAtual());
		Assert.assertNotNull(avaliacaoDTO.getAvaliacaoAtual().getId());

		tarefa.setRelacaoEntidade(avaliacaoDTO.getAvaliacaoAtual().getId());
		
		MockService.criarMockObterTarefa(TiposTarefa.AVALIAR_PACIENTE, tarefa);
		MockService.criarMockAtribuirTarefa(TiposTarefa.AVALIAR_PACIENTE, tarefa);
				
		mockMvc.perform(
				CreateMockHttpServletRequest.makePut("/api/avaliacoes/" + avaliacaoDTO.getAvaliacaoAtual().getId() + "/iniciar")
				.contentType(BaseConfigurationTest.CONTENT_TYPE))				
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		MockService.limparMockObterTarefa(TiposTarefa.AVALIAR_PACIENTE);
		MockService.limparMockAtribuirTarefa(TiposTarefa.AVALIAR_PACIENTE);
		
		return avaliacaoDTO.getAvaliacaoAtual().getId();
	}
	
	
	/*
	 * public static void criarAvaliacaoInicialDePaciente(MockMvc
	 * mockMvc,PacienteDto pacienteDto) throws Exception{ Long idAvaliacao =
	 * H2BasePaciente.criarPacienteComAvaliacaoInicial(mockMvc, null, null);
	 * 
	 * Avaliacao avaliacao = new Avaliacao(); Paciente paciente = new Paciente();
	 * avaliacao.setPaciente(paciente);
	 * avaliacao.getPaciente().setTempoParaTransplante(30);
	 * 
	 * avaliacao.setAprovado(true); mockMvc
	 * .perform(CreateMockHttpServletRequest.makePut("/api/avaliacoes/" +
	 * idAvaliacao)
	 * .content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(avaliacao)
	 * ) .contentType(BaseConfigurationTest.CONTENT_TYPE))
	 * .andExpect(status().isOk()); }
	 */
	
	/**
	 * Cria um paciente já aprovado pelo hemocentro.
	 * 
	 * @param mockMvc objeto de mock vindo do contexto do teste.
	 * @param pacienteDto paciente a ser persistido.
	 * @return objeto com mensagens de retorno do servidor rest.
	 * @throws Exception
	 */
	public static Long criarPacienteAprovadoAvaliacaoInicial(MockMvc mockMvc, 
			IPacienteService pacienteService,
			IAvaliacaoService avaliacaoService,
			ILogEvolucaoService logEvolucaoService,
			PacienteDto pacienteDto) throws Exception{
		
		ProcessoDTO processo = new ProcessoDTO(1L, TiposTarefa.AVALIAR_PACIENTE.getTipoProcesso());
		TarefaDTO tarefa = new TarefaDTO(processo, TiposTarefa.AVALIAR_PACIENTE, new Perfil(TiposTarefa.AVALIAR_PACIENTE.getPerfil().getId()));
		tarefa.setId(1L);
		
		MockService.criarMockCriarTarefa(TiposTarefa.AVALIAR_PACIENTE, tarefa);
		
		Paciente paciente = obterPacientePopulado(pacienteDto.getNome(), pacienteDto.getNomeMae(), pacienteDto.getDataNascimento(),
				pacienteDto.getCpf(),pacienteDto.getCns(),pacienteDto.getResponsavel());

		MockService.criarMockContarNotificacao(null, 0L);
		ReflectionTestUtils.setField(logEvolucaoService, "usuarioService", MockService.criarMockUsuarioService(1L, null, null));
		
		pacienteService.salvar(paciente);
		
		MockService.limparMockCriarTarefa(TiposTarefa.AVALIAR_PACIENTE);
		
		AvaliacaoDTO avaliacaoDTO = avaliacaoService.obterAvaliacaoAtual(paciente.getRmr());
		
		Assert.assertNotNull(avaliacaoDTO);
		Assert.assertNotNull(avaliacaoDTO.getAvaliacaoAtual());
		Assert.assertNotNull(avaliacaoDTO.getAvaliacaoAtual().getId());
		
		tarefa.setRelacaoEntidade(avaliacaoDTO.getAvaliacaoAtual().getId());
		
		MockService.criarMockObterTarefa(TiposTarefa.AVALIAR_PACIENTE, tarefa);
		MockService.criarMockAtribuirTarefa(TiposTarefa.AVALIAR_PACIENTE, tarefa);
		
		avaliacaoService.atribuirAvaliacaoAoAvaliador(avaliacaoDTO.getAvaliacaoAtual().getId());
		
		Avaliacao avaliacaoAprovada = new Avaliacao();
		avaliacaoAprovada.setAprovado(true);
		avaliacaoAprovada.setPaciente(new Paciente());
		avaliacaoAprovada.getPaciente().setTempoParaTransplante(30);
		
		ProcessoDTO processoAvaliacaoCamaraTecnica = new ProcessoDTO(2L, TiposTarefa.AVALIACAO_CAMARA_TECNICA.getTipoProcesso());
		TarefaDTO tarefaAvaliacaoCamaraTecnica = new TarefaDTO(processoAvaliacaoCamaraTecnica, TiposTarefa.AVALIACAO_CAMARA_TECNICA,  new Perfil(TiposTarefa.AVALIACAO_CAMARA_TECNICA.getPerfil().getId()));
		tarefaAvaliacaoCamaraTecnica.setId(2L);		
		MockService.criarMockCriarTarefa(TiposTarefa.AVALIACAO_CAMARA_TECNICA, tarefa);
		
		ProcessoDTO processoAvaliarExameHla = new ProcessoDTO(2L, TiposTarefa.AVALIAR_EXAME_HLA.getTipoProcesso());
		TarefaDTO tarefaAvaliarExameHla = new TarefaDTO(processoAvaliarExameHla, TiposTarefa.AVALIAR_EXAME_HLA,  new Perfil(TiposTarefa.AVALIAR_EXAME_HLA.getPerfil().getId()));
		tarefaAvaliacaoCamaraTecnica.setId(2L);		
		MockService.criarMockCriarTarefa(TiposTarefa.AVALIAR_EXAME_HLA, tarefaAvaliarExameHla);
		
		ProcessoDTO processoAvaliacao = new ProcessoDTO(1L, TiposTarefa.AVALIAR_PACIENTE.getTipoProcesso());		
		TarefaDTO tarefaAvalaicao = new TarefaDTO(processoAvaliacao, TiposTarefa.AVALIAR_PACIENTE, new Perfil(TiposTarefa.AVALIAR_PACIENTE.getPerfil().getId()));
		tarefaAvalaicao.setId(1L);
				
		MockService.criarMockFecharTarefa(TiposTarefa.AVALIAR_PACIENTE, tarefaAvalaicao);		
		
		avaliacaoService.alterarAprovacaoPaciente(avaliacaoDTO.getAvaliacaoAtual().getId(), avaliacaoAprovada);
		
		
		return paciente.getRmr();
		
	}
	
	/**
	 * Cria um paciente com exame e avaliação aprovados.
	 * @param mock externo para chamadas REST.
	 * @param serviço de paciente.
	 * @param serviço de avaliação.
	 * @param serviço de paciente.
	 * @param objeto DTO de paciente.
	 * @return rmr do paciente cadastrado.
	 * @throws Exception
	 */
	public static Long criarPacienteComExameAprovadoEAprovacaoInicial (
			MockMvc mockMvc, 
			PacienteService pacienteService,
			AvaliacaoService avaliacaoService,
			ExamePacienteService examePacienteService,			
			GenotipoPacienteService genotipoService,
			ILogEvolucaoService logEvolucaoService,
			PacienteDto pacienteDto) throws Exception{
		
		MockService.mockCriarPacienteComAvaliacaoInicialAprovada(pacienteService, avaliacaoService);
		
		MockService.criarMockCriarNotificacao(CategoriasNotificacao.AVALIACAO_PACIENTE);
		
		Long rmr = H2BasePaciente.criarPacienteAprovadoAvaliacaoInicial(mockMvc, 
				pacienteService, 
				avaliacaoService, 
				logEvolucaoService,
				pacienteDto);
		
		MockService.mockCriarPacienteComAvaliacaoInicialAprovadaEExameAprovado(examePacienteService, genotipoService);		
		
		List<ExamePaciente> examesPaciente = examePacienteService.listarExamesPorPaciente(rmr);
		Assert.assertEquals(1,examesPaciente.size());
		
		ExamePaciente examePaciente = criarExamePaciente(examesPaciente.get(0));
		
		ProcessoDTO processo = new ProcessoDTO(4L, TiposTarefa.AVALIAR_EXAME_HLA.getTipoProcesso());
		TarefaDTO tarefa = new TarefaDTO(processo, TiposTarefa.AVALIAR_EXAME_HLA, new Perfil(TiposTarefa.AVALIAR_EXAME_HLA.getPerfil().getId()));
		tarefa.setStatus(StatusTarefa.ABERTA.getId());
		tarefa.setId(4L);
		
		MockService.criarMockObterTarefa(TiposTarefa.AVALIAR_EXAME_HLA, tarefa);
		MockService.criarMockAtribuirTarefa(TiposTarefa.AVALIAR_EXAME_HLA, tarefa);
		MockService.criarMockFecharTarefa(TiposTarefa.AVALIAR_EXAME_HLA, tarefa);
		
		ProcessoDTO processoBusca = new ProcessoDTO(5L, TiposTarefa.RECEBER_PACIENTE.getTipoProcesso());
		TarefaDTO tarefaReceberPaciente = new TarefaDTO(processoBusca, TiposTarefa.RECEBER_PACIENTE, new Perfil(TiposTarefa.RECEBER_PACIENTE.getPerfil().getId()));
		
		MockService.criarMockCriarTarefa(TiposTarefa.RECEBER_PACIENTE, tarefaReceberPaciente);

		TarefaDTO tarefaEnviarWmda = new TarefaDTO(processoBusca, TiposTarefa.ENVIAR_DADOS_PACIENTE_WMDA_FOLLOWUP, null);
		
		MockService.criarMockCriarTarefa(TiposTarefa.ENVIAR_DADOS_PACIENTE_WMDA_FOLLOWUP, tarefaEnviarWmda);
		
		ExecutarProcedureMatchService executarProcedureMatchService = Mockito.spy(ExecutarProcedureMatchService.class);
		
		ReflectionTestUtils.setField(genotipoService, "executarProcedureMatchService", executarProcedureMatchService);
		ReflectionTestUtils.setField(examePacienteService, "genotipoService", genotipoService);
		
		Mockito.doNothing().when(executarProcedureMatchService).gerarMatchPaciente(Mockito.anyLong());		
		
		TarefaDTO tarefaEmdis = new TarefaDTO(processoBusca, TiposTarefa.ENVIAR_PACIENTE_PARA_EMDIS_FOLLOWUP, null);
		
		MockService.criarMockCriarTarefa(TiposTarefa.ENVIAR_PACIENTE_PARA_EMDIS_FOLLOWUP, tarefaEmdis);
		
		examePacienteService.aceitar(examePaciente);
						
		return rmr;
		
	}
	
	/**
	 * Atribui uma tarefa para o usuário logado
	 * @param mockMvc objeto de mock vindo do contexto do teste.
	 * @param processoId id do processo da tarefa.
	 * @param tarefaId id da tarefa a ser atribuida ao usuario.
	 * @throws Exception
	 */
	public static void atribuirTarefaParaUsuarioLogado(MockMvc mockMvc, Long processoId, Long tarefaId) throws Exception {
		mockMvc.perform(
				CreateMockHttpServletRequest.makePut("/api/processos/" + processoId + "/tarefas/" + tarefaId + "/atribuir")
				.param("atribuirUsuarioLogado", "true")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))				
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
	}
	
	public static Long obterRMR(RetornoControladorErro retorno) {
		String rmrRecuperado = retorno.getMensagem().split(" ")[2];
		rmrRecuperado = rmrRecuperado.substring(0, rmrRecuperado.length() - 1);
		return new Long(rmrRecuperado);
	}

	

	private static String enviarArquivoExame(MockMvc mockMvc,final MockMultipartFile mockMultipartFile)
			throws UnsupportedEncodingException, Exception {
		return mockMvc.perform(CreateMockHttpServletRequest.makeMultipart("/api/arquivosexame/salvarArquivo", "POST")
				.file(mockMultipartFile)				
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())                
                .andExpect(content().contentType("text/plain;charset=utf-8"))
                .andReturn().getResponse().getContentAsString();
	}
	
	
	public static String enviarArquivoEvolucao(MockMvc mockMvc, final MockMultipartFile mockMultipartFile)
			throws UnsupportedEncodingException, Exception {
		return mockMvc.perform(CreateMockHttpServletRequest.makeMultipart("/api/arquivosevolucao", "POST")
				.file(mockMultipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())                
                .andExpect(content().contentType("text/plain;charset=utf-8"))
                .andReturn().getResponse().getContentAsString();
	}
	
	public static Paciente obterPacientePopulado(String nome, String nomeMae, LocalDate dataNascimento, String cpf, String cns, Responsavel responsavel) {
		Paciente paciente = new Paciente();
        paciente.setCpf(cpf);
        paciente.setCns(cns);
        paciente.setNome(nome);
        paciente.setAbo(Abo.A_POSITIVO.getAbo());
        paciente.setDataNascimento(dataNascimento);
        paciente.setNomeMae(nomeMae);
        paciente.setResponsavel(responsavel);
        paciente.setEnderecosContato(new ArrayList<>());
        paciente.getEnderecosContato().add(montarEnderecoContato());
        paciente.setContatosTelefonicos(montarContatosTelefonicos());
        //paciente.setDataCadastro(LocalDateTime.now());
        paciente.setUsuario(new Usuario(1L));
        paciente.setPais(BaseConfigurationTest.PAIS_BRASIL);
        paciente.setRaca(new Raca(1L, "BRANCA"));
        paciente.setSexo(Sexo.MASCULINO.getSexo());
        paciente.setNaturalidade(new Uf("RJ", "RIO DE JANEIRO"));
        paciente.setMedicoResponsavel(new Medico(1L));
        paciente.getMedicoResponsavel().setUsuario(new Usuario(1L));
        paciente.setAceiteMismatch(AceiteMismatch.ACEITA.getCodigo());
        Diagnostico diagnostico = montarDiagnostico();
        diagnostico.setCid(montarCid());
        paciente.setDiagnostico(diagnostico);
        final Evolucao evolucao = montarEvolucao();
        evolucao.setCondicaoPaciente(montarCondicaoPaciente());
        evolucao.setMotivo(montarMotivo());
        paciente.setEvolucoes(new ArrayList<>());
        paciente.getEvolucoes().add(evolucao);

        evolucao.setExameAnticorpo(false);
        paciente.setCentroAvaliador(montarCentroAvaliador());
        paciente.setExames(new ArrayList<>());
        paciente.getExames().add(montarExame());
        paciente.setAceiteMismatch(0);
		return paciente;
	}
	
	public static EnderecoContatoPaciente montarEnderecoContato() {
        EnderecoContatoPaciente contatoEndereco = new EnderecoContatoPaciente();
        contatoEndereco.setBairro("CENTRO");
        contatoEndereco.setCep("20231048");
        contatoEndereco.setComplemento(null);
        contatoEndereco.setEnderecoEstrangeiro(null);
        final Municipio municipio = new Municipio();
        municipio.setId(1635L);
        final Uf uf = new Uf("RJ", null);
        municipio.setUf(uf);
        contatoEndereco.setMunicipio(municipio);
        contatoEndereco.setNomeLogradouro("DOS INVALIDOS");
        contatoEndereco.setNumero("212");
        contatoEndereco.setPais(BaseConfigurationTest.PAIS_BRASIL);
        contatoEndereco.setTipoLogradouro("RUA");
        return contatoEndereco;
    }
	
    public static List<ContatoTelefonicoPaciente> montarContatosTelefonicos() {
        List<ContatoTelefonicoPaciente> contatosTelefonicos = new ArrayList<>();
        ContatoTelefonicoPaciente contatoTelefonico = new ContatoTelefonicoPaciente();
        contatoTelefonico.addCodigoDeArea(21);
        contatoTelefonico.addCodigoInternacional(55);
        contatoTelefonico.setNome("Teste Contato");
        contatoTelefonico.setNumero(2222222L);
        contatoTelefonico.setPrincipal(true);
        contatoTelefonico.setTipo(1);
        contatosTelefonicos.add(contatoTelefonico);
        return contatosTelefonicos;
    }
    
    public static Diagnostico montarDiagnostico() {
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setDataDiagnostico(LocalDate.now());
        return diagnostico;
    }
    
    public static Cid montarCid() {
        final Cid cid = new Cid();
        cid.setId(1349L);
        cid.setTransplante(true);
        return cid;
    }
    
    public static Evolucao montarEvolucao() {
        Evolucao evolucao = new Evolucao();
        evolucao.setCmv(Boolean.FALSE);
        evolucao.setCondicaoAtual("Condição atual teste");
        evolucao.setTratamentoAnterior("Tratamento anterior teste");
        evolucao.setTratamentoAtual("Tratamento atual teste");
        evolucao.setAltura(BaseConfigurationTest.NUMERICO_UM_INTEIRO_DOIS_DECIMAIS);
        evolucao.setPeso(BaseConfigurationTest.NUMERICO_TRES_INTEIROS_UM_DECIMAL);
        return evolucao;
    }
    
    public static CondicaoPaciente montarCondicaoPaciente() {
        final CondicaoPaciente condPaciente = new CondicaoPaciente();
        condPaciente.setId(1L);
        return condPaciente;
    }
    
    public static Motivo montarMotivo() {
        final Motivo motivo = new Motivo();
        motivo.setId(1L);
        return motivo;
    }
    
    public static CentroTransplante montarCentroAvaliador() {
        final CentroTransplante centroAvaliador = new CentroTransplante();
        centroAvaliador.setId(2L);
        return centroAvaliador;
    }
    
    public static ExamePaciente montarExame() {
        ExamePaciente exame = new ExamePaciente();
        exame.setDataColetaAmostra(LocalDate.now());
        exame.setDataExame(LocalDate.now());
        exame.setLaboratorioParticular(true);
        ArquivoExame arquivo = new ArquivoExame();
        arquivo.setCaminhoArquivo("path");

        exame.setArquivosExame(new ArrayList<>());
        exame.getArquivosExame().add(arquivo);

        LocusExame locusA = new LocusExame("primeiro alelo", "segundo alelo");
        LocusExamePk locusPK = new LocusExamePk();
        LocusExamePk locusPK2 = new LocusExamePk();
        LocusExamePk locusPK3 = new LocusExamePk();
        locusPK.setLocus(new Locus(Locus.LOCUS_A));
        locusA.setId(locusPK);
        locusA.setPrimeiroAlelo("01:01:02");
        locusA.setSegundoAlelo("01:01:02");

        LocusExame locusB = new LocusExame("primeiro alelo", "segundo alelo");
        locusPK2.setLocus(new Locus(Locus.LOCUS_B));
        locusB.setId(locusPK2);
        locusB.setPrimeiroAlelo("35:251");
        locusB.setSegundoAlelo("35:251");

        LocusExame locusDRB1 = new LocusExame("primeiro alelo", "segundo alelo");
        locusPK3.setLocus(new Locus(Locus.LOCUS_DRB1));
        locusDRB1.setId(locusPK3);
        locusDRB1.setPrimeiroAlelo("11:01:19");
        locusDRB1.setSegundoAlelo("11:01:19");

        List<LocusExame> listaLocus = new ArrayList<>();
        listaLocus.add(locusA);
        listaLocus.add(locusB);
        listaLocus.add(locusDRB1);

        exame.setLocusExames(listaLocus);

        exame.setMetodologias(new ArrayList<>());
        exame.getMetodologias().add(new Metodologia(1L, "M1", "METODOLOGIA 1"));

        return exame;
    }
    
    private static ExamePaciente criarExamePaciente(ExamePaciente examePaciente) {
		ExamePaciente exame = new ExamePaciente();
		ArquivoExame arquivoExame = new ArquivoExame();
		ArquivoExame arquivosExamePaciente = examePaciente.getArquivosExame().get(0);
		arquivoExame.setCaminhoArquivo(arquivosExamePaciente.getCaminhoArquivo());
		arquivoExame.setExame(exame);
		arquivoExame.setId(arquivoExame.getId());
		arquivoExame.setRmr(examePaciente.getPaciente().getRmr());
		exame.setArquivosExame(Arrays.asList(arquivoExame));
		exame.setDataColetaAmostra(examePaciente.getDataColetaAmostra());
		exame.setEditadoPorAvaliador(false);
		exame.setDataExame(examePaciente.getDataExame());
		exame.setLaboratorioParticular(false);
		exame.setId(examePaciente.getId());
		List<LocusExame> locusExames = new ArrayList<>();
		examePaciente.getLocusExames().forEach(e -> {
			LocusExame locus = new LocusExame();
			locus.setDataMarcacao(e.getDataMarcacao());
			locus.setId(e.getId());
			locus.setIdBusca(e.getIdBusca());
			locus.setMotivoDivergencia(e.getMotivoDivergencia());
			locus.setPrimeiroAlelo(e.getPrimeiroAlelo());
			locus.setPrimeiroAleloComposicao(e.getPrimeiroAleloComposicao());
			locus.setPrimeiroAleloDivergente(e.getPrimeiroAleloDivergente());
			locus.setSegundoAlelo(e.getSegundoAlelo());
			locus.setSegundoAleloComposicao(e.getSegundoAleloComposicao());
			locus.setSegundoAleloDivergente(e.getSegundoAleloDivergente());
			locus.setSelecionado(e.getSelecionado());
			locusExames.add(locus);
		});
		exame.setLocusExames(locusExames );
		List<Metodologia> metodologias = new ArrayList<>();
		examePaciente.getMetodologias().forEach(m ->{
			Metodologia metodologia = new Metodologia();
			metodologia.setSigla(m.getSigla());
			metodologia.setDescricao(m.getDescricao());
			metodologia.setId(m.getId());
			metodologia.setPesoGenotipo(m.getPesoGenotipo());
			metodologias.add(metodologia);
		});
		exame.setMetodologias(metodologias );
		Paciente paciente = new Paciente();
		paciente.setRmr(examePaciente.getPaciente().getRmr());
		paciente.setExames(Arrays.asList(exame));
		exame.setPaciente(paciente );
		exame.setStatusExame(examePaciente.getStatusExame());
		exame.setTipoAmostra(examePaciente.getTipoAmostra());
		exame.setLaboratorioParticular(examePaciente.getLaboratorioParticular());
		if (!exame.getLaboratorioParticular()) {
			Laboratorio laboratorio = new Laboratorio();
			laboratorio.setId(examePaciente.getLaboratorio().getId());
			exame.setLaboratorio(laboratorio );
		}
		return exame;
	}

    
    
}
