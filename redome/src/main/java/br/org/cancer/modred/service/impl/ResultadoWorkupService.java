package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.ArquivoResultadoWorkup;
import br.org.cancer.modred.model.AvaliacaoResultadoWorkup;
import br.org.cancer.modred.model.CentroTransplanteUsuario;
import br.org.cancer.modred.model.Configuracao;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.ResultadoWorkup;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IResultadoWorkupRepository;
import br.org.cancer.modred.service.IResultadoWorkupService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.ArquivoUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;

/**
 * Classe de implementação dos métodos de negócio para resultado workup.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class ResultadoWorkupService extends AbstractLoggingService<ResultadoWorkup, Long> implements IResultadoWorkupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResultadoWorkupService.class);

	@Autowired
	private IResultadoWorkupRepository resultadoWorkupRepository;

	@SuppressWarnings("rawtypes")
	@Autowired
	private IStorageService storageService;

	private IUsuarioService usuarioService;

	private ConfiguracaoService configuracaoService;

	private PedidoWorkupService pedidoWorkupService;

	private AvaliacaoResultadoWorkupService avaliacaoResultadoWorkupService;
	
	@Override
	public void salvarResultadoWorkup(ResultadoWorkup resultado) {
		resultadoWorkupRepository.save(resultado);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResultadoWorkup obterDoadorComResultado(Long dmr, Long idCentroColeta, Boolean aberto) {
		LOGGER.info("ENTRADA NO MÉTODO DE OBTER RESULTADO DE WORKUP PARA UM DOADOR");
		if (dmr == null) {
			LOGGER.info("ERRO POR UM DMR SER NULLO");
			throw new BusinessException("erro.resultado.workup.dmr_obrigatorio");
		}
		if (aberto == null) {
			LOGGER.info("ERRO POR NÃO PASSAR O ESTADO DO EXAME");
			throw new BusinessException("erro.resultado.workup.aberto_obrigatorio");
		}
		ArrayList<Long> centros = new ArrayList<Long>();
		if (idCentroColeta == null) {
			for (CentroTransplanteUsuario centroUsuario : usuarioService.obterUsuarioLogado().getCentroTransplanteUsuarios()) {
				centros.add(centroUsuario.getCentroTransplante().getId());
			}
		}
		else {
			centros.add(idCentroColeta);
		}

		ResultadoWorkup resultado = resultadoWorkupRepository.obterDoadorComResultado(dmr, centros, aberto);

		if (resultado == null) {
			LOGGER.info("ERRO POR NÃO LOCALIZAR UM RESULTADO DE WORKUP DE ACORDO COM OS PARAMETROS PASSADO");
			throw new BusinessException("erro.resultado.workup.doador_nao_localizado");
		}
		excluirArquivosNaoVinculados(resultado);
		return resultado;
	}


	private TarefaDTO obterTarefaDeResultadoWorkupNacional(ResultadoWorkup resultado, Usuario usuario,
			List<StatusTarefa> status) {
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(resultado.getPedidoWorkup());
		
		final Long rmr = pedidoWorkup.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		TarefaDTO tarefa = TiposTarefa.INFORMAR_RESULTADO_WORKUP_NACIONAL.getConfiguracao().obterTarefa()
				.comStatus(status)
				.comObjetoRelacionado(resultado.getId())
				.comRmr(rmr)
				.apply();
		
		return tarefa;
	}

	private TarefaDTO fecharTarefaDeResultadoWorkupNacional(ResultadoWorkup resultado, List<StatusTarefa> status) {
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(resultado.getPedidoWorkup());
		final Long rmr = pedidoWorkup.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		TarefaDTO tarefa = TiposTarefa.INFORMAR_RESULTADO_WORKUP_NACIONAL.getConfiguracao().fecharTarefa()
				.comStatus(status)
				.comObjetoRelacionado(resultado.getId())
				.comRmr(rmr)
				.apply();

		return tarefa;
	}
	
	private TarefaDTO fecharTarefaDeResultadoWorkupInternacional(ResultadoWorkup resultado) {
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(resultado.getPedidoWorkup());
		final Long rmr = pedidoWorkup.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		TarefaDTO tarefa = TiposTarefa.CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL.getConfiguracao().fecharTarefa()
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
				.comObjetoRelacionado(resultado.getId())
				.comRmr(rmr)
				.apply();

		return tarefa;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void excluirArquivosNaoVinculados(ResultadoWorkup resultado) {
		LOGGER.info("EXCLUSÃO DE ARQUIVOS NÃO VINCULADOS A NENHUM RESULTADO DE WORKUP");
		if (resultado != null && resultado.getDataAtualizacao() == null) {		
			List<S3ObjectSummary> objectsSummary = storageService
					.localizarArquivosPorDiretorio(StorageService.DIRETORIO_PEDIDO + "/" + resultado.getPedidoWorkup() + "/" + StorageService.DIRETORIO_RESULTADO_WORKUP + "/" + resultado.getId());
			objectsSummary.forEach(objectSummary -> {				
				storageService.removerArquivo(objectSummary.getKey());
			});
		}
	}

	@Override
	public ResultadoWorkup obterResultadoWorkupPorId(Long id) {
		return resultadoWorkupRepository.findById(id).get();
	}

	/**
	 * {@inheritDoc}
	 */
	@CreateLog(TipoLogEvolucao.RESULTADO_PEDIDO_WORKUP_CADASTRADO_PARA_DOADOR)
	@Override
	public void finalizarResultadoWorkup(ResultadoWorkup resultado) {
		LOGGER.info("ENTRADA NO MÉTODO DE FINALIZAÇÃO DO RESULTADO DE WORKUP");
		ResultadoWorkup resultadoPersistir = resultadoWorkupRepository.findById(resultado.getId()).get();
		resultadoPersistir.setMotivoInviabilidade(resultado.getMotivoInviabilidade());
		resultadoPersistir.setColetaInviavel(resultado.getColetaInviavel());
		resultadoPersistir.setUsuario(usuarioService.obterUsuarioLogado());

		resultadoPersistir.setDataAtualizacao(LocalDateTime.now());

		List<CampoMensagem> resultadoValidacao = validar(resultadoPersistir);
		if (!resultadoValidacao.isEmpty()) {
			LOGGER.info("ERRO DE VALIDAÇÃO DO RESULTADO DE WORKUP");
			throw new ValidationException("erro.resultado.workup.resultado_com_erros_validacao", resultadoValidacao);
		}

		List<ArquivoResultadoWorkup> arquivos = resultado.getArquivosResultadoWorkup();

		arquivos
				.stream()
				.forEach(r ->
		{
					r.setResultadoWorkup(resultadoPersistir);
				});
		resultadoPersistir.setArquivosResultadoWorkup(arquivos);

		resultadoPersistir.setUsuario(usuarioService.obterUsuarioLogado());
		resultadoWorkupRepository.save(resultadoPersistir);

		pedidoWorkupService.fecharPedidoWorkupComoRealizado(resultadoPersistir.getPedidoWorkup());
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(resultado.getPedidoWorkup());

		
		TiposDoador tiposDoador = pedidoWorkup.getSolicitacao().getMatch().getDoador().getTipoDoador();
		if (tiposDoador == TiposDoador.NACIONAL) {

			fecharTarefaDeResultadoWorkupNacional(resultadoPersistir, Arrays.asList(StatusTarefa.ATRIBUIDA));
		}
		else
			if (tiposDoador == TiposDoador.INTERNACIONAL) {
				fecharTarefaDeResultadoWorkupInternacional(resultadoPersistir);
			}
		

		AvaliacaoResultadoWorkup avaliacao = criarAvaliacao(resultadoPersistir);
		criarTarefaParaAvaliador(avaliacao);
		
		criarNotificacaoParaCentroTransplate(resultadoPersistir);		
		
		LOGGER.info("SUCESSO NA GRAVAÇÃO DE RESULTADO DE WORKUP");
	}
	
	@SuppressWarnings("rawtypes")
	private void criarNotificacaoParaCentroTransplate(ResultadoWorkup resultadoWorkup) {
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(resultadoWorkup.getPedidoWorkup());
		
		final Match match = pedidoWorkup.getSolicitacao().getMatch();		
		IDoador doador = (IDoador) match.getDoador();
		
		CategoriasNotificacao.AVALIACAO_RESULTADO_WORKUP.getConfiguracao().criar()
			.comDescricao(AppUtil.getMensagem(messageSource, 
					"resultado.workup.cadastrado.centrotransplante.notificacao", 
					doador.getIdentificacao().toString(), match.getBusca().getPaciente().getRmr()))
			.comPaciente(match.getBusca().getPaciente().getRmr())
			.paraPerfil(Perfis.MEDICO_TRANSPLANTADOR)
			.comParceiro(match.getBusca().getCentroTransplante().getId())
			.apply();
		
	}

	private AvaliacaoResultadoWorkup criarAvaliacao(ResultadoWorkup resultadoPersistir) {
		AvaliacaoResultadoWorkup avaliacao = new AvaliacaoResultadoWorkup();
		avaliacao.setResultadoWorkup(resultadoPersistir);
		avaliacao.setDataCriacao(LocalDateTime.now());
		avaliacaoResultadoWorkupService.salvar(avaliacao);
		return avaliacao;
	}

	private void criarTarefaParaAvaliador(AvaliacaoResultadoWorkup avaliacao) {
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(avaliacao.getResultadoWorkup().getPedidoWorkup());
		Paciente paciente = pedidoWorkup.getSolicitacao().getPaciente();
		final Long rmr = paciente.getRmr();
		
		final Long idCentroTransplante = pedidoWorkup.getSolicitacao().getMatch().getBusca()
				.getCentroTransplante().getId();		
		
		TiposTarefa.AVALIAR_RESULTADO_WORKUP_NACIONAL.getConfiguracao().criarTarefa()
			.comObjetoRelacionado(avaliacao.getId())
			.comParceiro(idCentroTransplante)
			.comRmr(rmr)
			.comStatus(StatusTarefa.ABERTA)
			.apply();
		
	}

	private List<CampoMensagem> validar(ResultadoWorkup resultado) {
		List<CampoMensagem> camposMensagem = new ConstraintViolationTransformer(validator.validate(resultado))
				.transform();
		List<Configuracao> configuracoes = configuracaoService
				.listarConfiguracao(Configuracao.QUANTIDADE_MAXIMA_ARQUIVOS_RESULTADO_WORKUP);
		Configuracao configuracaoTamanho = configuracoes.get(0);
		Integer quantidadeMaxima = Integer.parseInt(configuracaoTamanho.getValor());
		if (resultado.getArquivosResultadoWorkup().size() > quantidadeMaxima) {
			String mensagem = AppUtil.getMensagem(messageSource, "erro.arquivo.quantidade.invalido_comum",
					ArquivoUtil.converterBytesParaMegaBytes(quantidadeMaxima));
			camposMensagem.add(new CampoMensagem("arquivo", mensagem));
		}
		if (resultado.getColetaInviavel() == true && StringUtils.isBlank(resultado.getMotivoInviabilidade())) {
			String mensagem = AppUtil.getMensagem(messageSource, "erro.resultado.workup.motivo_inviabilidade");
			camposMensagem.add(new CampoMensagem("viavel", mensagem));
		}
		return camposMensagem;
	}

	/**
	 * {@inheritDoc}
	 */
	public ResultadoWorkup obterResultado(Long idResultadoWorkup) {
		return resultadoWorkupRepository.findById(idResultadoWorkup).get();
	}

	@Override
	public ResultadoWorkup obterResultadoPorPedidoWorkup(Long idPedidoWorkup) {
		return resultadoWorkupRepository.findByPedidoWorkup(idPedidoWorkup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.impl.AbstractService#getRepository()
	 */
	@Override
	public IRepository<ResultadoWorkup, Long> getRepository() {
		return resultadoWorkupRepository;
	}

	@Override
	public TarefaDTO obterTarefaResultadoWorkupNacional(Long dmr, Long idCentroColeta, Boolean aberto) {
		ResultadoWorkup resultado = obterDoadorComResultado(dmr, idCentroColeta, aberto);
		TarefaDTO tarefa = obterTarefaDeResultadoWorkupNacional(resultado, usuarioService.obterUsuarioLogado(), Arrays.asList(
				StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA));
		if (tarefa == null) {
			throw new BusinessException("tarefa.nao.encontrada");
		}
		return tarefa;
	}

	@Override
	public JsonViewPage<TarefaDTO> listarTarefasPorCentroTransplante(Long idCentroColeta, PageRequest pageRequest) {

		//final CentroTransplante centroTransplante = centroTransplanteService.obterCentroTransplante(idCentroColeta);
				
		return TiposTarefa.INFORMAR_RESULTADO_WORKUP_NACIONAL.getConfiguracao()
					.listarTarefa()
					.comPaginacao(pageRequest)
					.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
					.apply();
	}
	
	
	@Override
	public JsonViewPage<TarefaDTO> listarTarefasCadastroResultado(PageRequest paginacao) {

		return TiposTarefa.CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL.getConfiguracao()
			.listarTarefa()
			.comStatus(Arrays.asList(StatusTarefa.ABERTA))
			.comPaginacao(paginacao)
			.paraTodosUsuarios()
			.apply();
	}
	
	/**
	 * @param resultadoWorkupRepository the resultadoWorkupRepository to set
	 */
	//public void setResultadoWorkupRepository(IResultadoWorkupRepository resultadoWorkupRepository) {
//		this.resultadoWorkupRepository = resultadoWorkupRepository;
	//}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @param configuracaoService the configuracaoService to set
	 */
	@Autowired
	public void setConfiguracaoService(ConfiguracaoService configuracaoService) {
		this.configuracaoService = configuracaoService;
	}

	/**
	 * @param pedidoWorkupService the pedidoWorkupService to set
	 */
	@Autowired
	public void setPedidoWorkupService(PedidoWorkupService pedidoWorkupService) {
		this.pedidoWorkupService = pedidoWorkupService;
	}

	/**
	 * @param avaliacaoResultadoWorkupService the avaliacaoResultadoWorkupService to set
	 */
	@Autowired
	public void setAvaliacaoResultadoWorkupService(AvaliacaoResultadoWorkupService avaliacaoResultadoWorkupService) {
		this.avaliacaoResultadoWorkupService = avaliacaoResultadoWorkupService;
	}

	@Override
	public Paciente obterPaciente(ResultadoWorkup resultadoWorkup) {
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(resultadoWorkup.getPedidoWorkup());
		return pedidoWorkup.getSolicitacao().getMatch().getBusca().getPaciente();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String[] obterParametros(ResultadoWorkup resultadoWorkup) {
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(resultadoWorkup.getPedidoWorkup());
		IDoador doador = (IDoador) pedidoWorkup.getSolicitacao().getMatch().getDoador();
		return StringUtils.split(doador.getIdentificacao().toString());
	}
	

}
