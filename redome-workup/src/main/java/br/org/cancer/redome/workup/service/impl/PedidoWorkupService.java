package br.org.cancer.redome.workup.service.impl;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.AprovarPlanoWorkupDTO;
import br.org.cancer.redome.workup.dto.ConsultaTarefasWorkupDTO;
import br.org.cancer.redome.workup.dto.FiltroListaTarefaWorkupDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.NotificacaoDTO;
import br.org.cancer.redome.workup.dto.PedidoWorkupDTO;
import br.org.cancer.redome.workup.dto.PlanoWorkupInternacionalDTO;
import br.org.cancer.redome.workup.dto.PlanoWorkupNacionalDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO.TarefaDTOBuilder;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.INotificacaoFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.ArquivoPedidoWorkup;
import br.org.cancer.redome.workup.model.AvaliacaoPrescricao;
import br.org.cancer.redome.workup.model.PedidoLogisticaDoadorWorkup;
import br.org.cancer.redome.workup.model.PedidoWorkup;
import br.org.cancer.redome.workup.model.Prescricao;
import br.org.cancer.redome.workup.model.PrescricaoMedula;
import br.org.cancer.redome.workup.model.domain.CategoriasNotificacao;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusPedidosWorkup;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposPedidosWorkup;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.persistence.IPedidoWorkupRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IArquivoPedidoWorkupService;
import br.org.cancer.redome.workup.service.IAvaliacaoPrescricaoService;
import br.org.cancer.redome.workup.service.IConfiguracaoService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaDoadorWorkupService;
import br.org.cancer.redome.workup.service.IPedidoWorkupService;
import br.org.cancer.redome.workup.service.IPrescricaoService;
import br.org.cancer.redome.workup.service.IStorageService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;
import br.org.cancer.redome.workup.util.ArquivoUtil;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade DistribuicaoWorkup.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class PedidoWorkupService extends AbstractService<PedidoWorkup, Long> implements IPedidoWorkupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoWorkupService.class);

	@Override
	public IRepository<PedidoWorkup, Long> getRepository() {
		return pedidoWorkupRepository;
	}
	
	@Autowired
	private IPedidoWorkupRepository pedidoWorkupRepository;
			
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;

	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	private IConfiguracaoService configuracaoService;
	
	private IUsuarioService usuarioService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private IStorageService storageService;
	
	@Autowired
	private IArquivoPedidoWorkupService arquivoPedidoWorkupService;

	@Autowired
	private IPrescricaoService prescricaoService;
	
	@Autowired
	private IPedidoLogisticaDoadorWorkupService pedidoLogisticaDoadorService;
	
	@Autowired
	@Lazy(true)
	private INotificacaoFeign notificacaoFeign;
	
	@Autowired
	private IAvaliacaoPrescricaoService avaliacaoPrescricaoService; 
	
	@Override
	public void criarPedidoWorkup(SolicitacaoWorkupDTO solicitacao) {
		
		Prescricao prescricao = this.prescricaoService.obterPrescricaoPorSolicitacao(solicitacao.getId());
		
		PedidoWorkup pedido = PedidoWorkup.builder()
				.solicitacao(solicitacao.getId())
				.centroTransplante(prescricao.getCentroTransplante())
				.tipo(solicitacao.solicitacaoWorkupNacional() ? TiposPedidosWorkup.NACIONAL.getId() : TiposPedidosWorkup.INTERNACIONAL.getId())
				.build();
		
		save(pedido);
		
		criarTarefa(pedido.getId(), solicitacao);
		
		
	}
	
	private void criarTarefa(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		Long idUsuarioResponsavel = solicitacao.getUsuarioResponsavel().getId();
		if (solicitacao.solicitacaoWorkupNacional()) {
			criarTarefaFormularioDoadorWorkup(idPedidoWorkup, rmr, idUsuarioResponsavel);	
		}
		else if (solicitacao.solicitacaoWorkupInternacional()) {
			criarTarefaPlanoworkupInternacional(idPedidoWorkup, rmr, idUsuarioResponsavel);			
		}		
	}
	
	
	private void criarTarefaPlanoworkupInternacional(Long idPedidoWorkup, Long rmr, Long idUsuarioResponsavel) {
		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_PLANO_WORKUP_INTERNACIONAL.getId()))
				.perfilResponsavel(Perfis.ANALISTA_WORKUP_INTERNACIONAL.getId())
				.status(StatusTarefa.ATRIBUIDA.getId())
				.usuarioResponsavel(idUsuarioResponsavel)
				.relacaoEntidade(idPedidoWorkup)
				.build(); 
		
		tarefaHelper.criarTarefa(tarefa);		
	}

	private void criarTarefaFormularioDoadorWorkup(Long idPedidoWorkup, Long rmr, Long idUsuarioResponsavel) {
		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.CADASTRAR_FORMULARIO_DOADOR.getId()))
				.perfilResponsavel(Perfis.ANALISTA_WORKUP.getId())
				.status(StatusTarefa.ATRIBUIDA.getId())
				.usuarioResponsavel(idUsuarioResponsavel)
				.relacaoEntidade(idPedidoWorkup)
				.build(); 
		
		tarefaHelper.criarTarefa(tarefa);
	}

	
	/**
	 * {@inheritDoc}
	 * @throws JsonProcessingException 
	 */
	@Override
	public void DefinirCentroColetaPorPedidoWorkup(Long idPedidoWorkup, Long idCentroColeta) throws JsonProcessingException {
		
		PedidoWorkup pedidoWorkup = pedidoWorkupRepository.findById(idPedidoWorkup).orElse(null);
		if (pedidoWorkup == null) {
			throw new BusinessException("erro.id.nulo");
		}
		pedidoWorkup.setCentroColeta(idCentroColeta);
		this.pedidoWorkupRepository.save(pedidoWorkup);
		
		SolicitacaoWorkupDTO solicitacao = this.solicitacaoFeign.atribuirCentroDeColeta(pedidoWorkup.getSolicitacao(), idCentroColeta);
		solicitacao = this.solicitacaoFeign.atualizarFaseWorkup(pedidoWorkup.getSolicitacao(), FasesWorkup.AGUARDANDO_PLANO_WORKUP.getId()); 
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		
		this.CriarTarefaCentroColeta(pedidoWorkup, solicitacao);
		this.fecharTarefa(rmr, TiposTarefa.DEFINIR_CENTRO_COLETA, pedidoWorkup.getId());
		
		LOGGER.info("Pedido de workup alterado para aguardando o centro de coleta.");
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Override
	public PedidoWorkup obterPedidoWorkup(Long id) {
		if (id == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return pedidoWorkupRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Pedido de Workup"));
	}

	@Override
	public PlanoWorkupNacionalDTO obterPlanoWorkupNacional(Long id) {
		if (id == null) {
			throw new BusinessException("erro.id.nulo");
		}
		PedidoWorkup pedidoWorkup = pedidoWorkupRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Pedido de Workup"));
		
		return PlanoWorkupNacionalDTO.builder()
		 .dataExameMedico1(pedidoWorkup.getDataExameMedico1())
		 .dataExameMedico2(pedidoWorkup.getDataExameMedico2())
		 .idCentroTransplante(pedidoWorkup.getCentroTransplante())
		 .dataRepeticaoBthcg(pedidoWorkup.getDataRepeticaoBthcg())
		 .setorAndar(pedidoWorkup.getSetorAndar())
		 .procurarPor(pedidoWorkup.getProcurarPor())
		 .doadorEmJejum(pedidoWorkup.getDoadorEmJejum())
		 .horasEmJejum(pedidoWorkup.getHorasEmJejum())
		 .observacaoPlanoWorkup(pedidoWorkup.getObservacaoAprovaPlanoWorkup())
		 .informacoesAdicionais(pedidoWorkup.getInformacoesAdicionais())
		 .build();
	}
	
	
	@Override
	public PedidoWorkupDTO obterPedidoWorkupDTO(Long id) {
		if (id == null) {
			throw new BusinessException("erro.id.nulo");
		}
		PedidoWorkup pedidoWorkup = pedidoWorkupRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Pedido de Workup"));
	
		SolicitacaoDTO solicitacao = this.solicitacaoFeign.obterSolicitacao(pedidoWorkup.getSolicitacao()); 
		
		List<ArquivoPedidoWorkup> arquivos = this.arquivoPedidoWorkupService.obterArquivosPorPedidoWorkup(id);
		
		PedidoWorkupDTO pedidoWorkupDTO = PedidoWorkupDTO.parse(pedidoWorkup, solicitacao, arquivos);
		
		PrescricaoMedula prescricao = (PrescricaoMedula) prescricaoService.obterPrescricaoPorSolicitacao(pedidoWorkup.getSolicitacao());
		if (prescricao.getFonteCelulaOpcao2() != null) {
			AvaliacaoPrescricao avaliacao = avaliacaoPrescricaoService.obterAvaliacaoPeloIdPrescricao(prescricao.getId());
			if (prescricao.getFonteCelulaOpcao1().equals(avaliacao.getFonteCelula())) {
				pedidoWorkupDTO.setIdFonteOpcao1(prescricao.getFonteCelulaOpcao2().getId());
			}
			else if (prescricao.getFonteCelulaOpcao2().equals(avaliacao.getFonteCelula())) {
				pedidoWorkupDTO.setIdFonteOpcao1(prescricao.getFonteCelulaOpcao1().getId());
			}
			else {
				pedidoWorkupDTO.setIdFonteOpcao1(prescricao.getFonteCelulaOpcao1().getId());
				pedidoWorkupDTO.setIdFonteOpcao2(prescricao.getFonteCelulaOpcao2().getId());
			}			
		}
		else {
			pedidoWorkupDTO.setIdFonteOpcao1(prescricao.getFonteCelulaOpcao1().getId());
		}
		
		
		return pedidoWorkupDTO;
	}
	
	/**
	 * @param pedidoWorkup
	 * @param rmr
	 * @return
	 */
	public void CriarTarefaCentroColeta(PedidoWorkup pedidoWorkup, SolicitacaoWorkupDTO solicitacao) {
		
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		TarefaDTOBuilder tarefa = TarefaDTO.builder()
		.processo(new ProcessoDTO(TipoProcesso.BUSCA, rmr))
		.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_PLANO_WORKUP_NACIONAL.getId()))
		.relacaoEntidade(pedidoWorkup.getId());
		
		if(solicitacao.solicitacaoWorkupNacional()) {
			tarefa.perfilResponsavel(Perfis.MEDICO_CENTRO_COLETA.getId()) 
			.status(StatusTarefa.ABERTA.getId())
			.relacaoParceiro(pedidoWorkup.getCentroColeta());
		} else {
			throw new BusinessException("erro.pedido.workup.perfil.invalido");
		}

		tarefaHelper.criarTarefa(tarefa.build());

	}

	private void fecharTarefa(Long rmr, TiposTarefa tipoTarefa, Long idPedidoWorkup) throws JsonProcessingException {
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(tipoTarefa.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idPedidoWorkup)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		tarefaHelper.encerrarTarefa(filtroDTO, false);
		
	}
	
	@Override
	public void informarPlanoWorkupNacional(Long idPedidoWorkup, PlanoWorkupNacionalDTO planoWorkup, MultipartFile arquivo) throws JsonProcessingException {
		LOGGER.info("Centro de coleta informará o plano de workup.");
		PedidoWorkup pedidoWorkup = obterPedidoWorkup(idPedidoWorkup);
		if (planoWorkupNacionalNaoPodeSerFeito(pedidoWorkup)) {
			throw new BusinessException("erro.plano.workup.ja.informado");
		}		
		
		validarPlanoWorkupNacional(planoWorkup);
//		ArquivoUtil.validarArquivoPedidoWorkup(arquivo, messageSource, configuracaoService, false);
		
		pedidoWorkup.setDataCriacaoPlano(LocalDateTime.now());
		pedidoWorkup.setDataExame(planoWorkup.getDataExame());
		pedidoWorkup.setDataResultado(planoWorkup.getDataResultado());
		pedidoWorkup.setDataInternacao(planoWorkup.getDataInternacao());
		pedidoWorkup.setDataColeta(planoWorkup.getDataColeta());
		pedidoWorkup.setDataExameMedico1(planoWorkup.getDataExameMedico1());
		pedidoWorkup.setDataExameMedico2(planoWorkup.getDataExameMedico2());
		pedidoWorkup.setDataRepeticaoBthcg(planoWorkup.getDataRepeticaoBthcg());
		pedidoWorkup.setSetorAndar(planoWorkup.getSetorAndar());
		pedidoWorkup.setProcurarPor(planoWorkup.getProcurarPor());
		pedidoWorkup.setDoadorEmJejum(planoWorkup.getDoadorEmJejum());
		pedidoWorkup.setHorasEmJejum(planoWorkup.getHorasEmJejum());
		pedidoWorkup.setObservacaoPlanoWorkup(planoWorkup.getObservacaoPlanoWorkup());
		pedidoWorkup.setInformacoesAdicionais(planoWorkup.getInformacoesAdicionais());
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedidoWorkup.getSolicitacao(), FasesWorkup.AGUARDANDO_ACEITE_PLANO.getId());
		
		pedidoWorkupRepository.save(pedidoWorkup);
		
//		if (arquivo != null) {
//			salvarArquivo(pedidoWorkup, arquivo);
//		}
		
		fecharTarefaInformarPlanoWorkupNacional(pedidoWorkup.getId(), solicitacao);		
		criarTarefaConfirmarPlanoWorkup(pedidoWorkup.getId(), solicitacao);
		
		LOGGER.info("Centro de coleta informou o plano de workup.");
		
	}

	private boolean planoWorkupNacionalNaoPodeSerFeito(PedidoWorkup pw) {
		if (Optional.ofNullable(pw.getDataExame()).isPresent() &&  
				Optional.ofNullable(pw.getDataResultado()).isPresent() &&
				Optional.ofNullable(pw.getDataInternacao()).isPresent() && 
				Optional.ofNullable(pw.getDataColeta()).isPresent()) {
			return true;
		}
		
		return false;
	}

	private void criarTarefaConfirmarPlanoWorkup(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		TarefaDTOBuilder tarefa = TarefaDTO.builder()
		.processo(new ProcessoDTO(TipoProcesso.BUSCA, rmr))
		.tipoTarefa(new TipoTarefaDTO(TiposTarefa.CONFIRMAR_PLANO_WORKUP.getId()))
		.relacaoEntidade(idPedidoWorkup)
		.perfilResponsavel(Perfis.MEDICO_TRANSPLANTADOR.getId())
		.relacaoParceiro(solicitacao.getMatch().getBusca().getCentroTransplante().getId())
		.status(StatusTarefa.ABERTA.getId());

		tarefaHelper.criarTarefa(tarefa.build());		
	}

	private void fecharTarefaInformarPlanoWorkupNacional(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao) throws JsonProcessingException {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		fecharTarefa(rmr, TiposTarefa.INFORMAR_PLANO_WORKUP_NACIONAL, idPedidoWorkup);
		
	}

	private void validarPlanoWorkupNacional(PlanoWorkupNacionalDTO planoWorkup) {
		validarValorNulo("dataExame", planoWorkup.getDataExame());
		validarDataMenor("dataExame", planoWorkup.getDataExame(), "Data Atual", LocalDate.now());
		
		validarValorNulo("dataResultado", planoWorkup.getDataResultado());
		validarDataMenor("dataResultado", planoWorkup.getDataResultado(), "Data Atual", LocalDate.now());
		
		validarValorNulo("dataInternacao", planoWorkup.getDataInternacao());
		validarDataMenor("dataInternacao", planoWorkup.getDataInternacao(), "Data Atual", LocalDate.now());
		
		validarValorNulo("dataColeta", planoWorkup.getDataColeta());
		validarDataMenor("dataColeta", planoWorkup.getDataColeta(), "Data Atual", LocalDate.now());
		
	}
	
	@Override
	public void informarPlanoWorkupInternacional(Long idPedidoWorkup, PlanoWorkupInternacionalDTO planoWorkup,
			MultipartFile arquivo) throws JsonProcessingException {
		
		LOGGER.info("Analista Workup internacional informará o plano de workup.");
		PedidoWorkup pedidoWorkup = obterPedidoWorkup(idPedidoWorkup);
		if (planoWorkupInternacionalNaoPodeSerFeito(pedidoWorkup)) {
			throw new BusinessException("erro.plano.workup.ja.informado");
		}		
		
		validarPlanoWorkupInternacional(planoWorkup);				
		//ArquivoUtil.validarArquivoPedidoWorkup(arquivo, messageSource, configuracaoService, true);
		
		pedidoWorkup.setDataCriacaoPlano(LocalDateTime.now());
		pedidoWorkup.setDataExame(planoWorkup.getDataExame());
		pedidoWorkup.setDataResultado(planoWorkup.getDataResultado());
		pedidoWorkup.setDataColeta(planoWorkup.getDataColeta());
		pedidoWorkup.setObservacaoPlanoWorkup(planoWorkup.getObservacaoPlanoWorkup());
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedidoWorkup.getSolicitacao(), FasesWorkup.AGUARDANDO_ACEITE_PLANO.getId());
				
		pedidoWorkupRepository.save(pedidoWorkup);
		
		//salvarArquivo(pedidoWorkup, arquivo);
		
		fecharTarefaInformarPlanoWorkupInternacional(pedidoWorkup.getId(), solicitacao);		
		criarTarefaConfirmarPlanoWorkup(pedidoWorkup.getId(), solicitacao);
		
		LOGGER.info("Analista Workup internacional informou o plano de workup.");				
	}
	
	private boolean planoWorkupInternacionalNaoPodeSerFeito(PedidoWorkup pw) {
		if (Optional.ofNullable(pw.getDataExame()).isPresent() &&  
				Optional.ofNullable(pw.getDataResultado()).isPresent() &&
				Optional.ofNullable(pw.getDataColeta()).isPresent()) {
			return true;
		}
		
		return false;
	}
	
	private void validarPlanoWorkupInternacional(PlanoWorkupInternacionalDTO planoWorkup) {
		validarValorNulo("dataExame", planoWorkup.getDataExame());
		validarDataMenor("dataExame", planoWorkup.getDataExame(), "Data Atual", LocalDate.now());
		
		validarValorNulo("dataResultado", planoWorkup.getDataResultado());
		validarDataMenor("dataResultado", planoWorkup.getDataResultado(), "Data Atual", LocalDate.now());
				
		validarValorNulo("dataColeta", planoWorkup.getDataColeta());
		validarDataMenor("dataColeta", planoWorkup.getDataColeta(), "Data Atual", LocalDate.now());
		
	}
	
	private void salvarArquivo(PedidoWorkup pedidoWorkup, MultipartFile arquivo) {
				
		String diretorio = ArquivoUtil.obterDiretorioArquivosPedidoWorkup(pedidoWorkup.getId());
		Instant instant = Instant.now();
		final long timeStampMillis = instant.toEpochMilli();

		if (arquivo != null) {
			ArquivoPedidoWorkup arquivoPedidoWorkup = ArquivoPedidoWorkup.builder()
					.caminho(diretorio + "/" + ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo))
					.pedidoWorkup(pedidoWorkup)
					.build();		
			
			arquivoPedidoWorkupService.save(arquivoPedidoWorkup);
			
			Optional.ofNullable(pedidoWorkup.getArquivos())
				.orElseGet(() -> {
					pedidoWorkup.setArquivos(new ArrayList<>());
					return pedidoWorkup.getArquivos();
				})
				.add(arquivoPedidoWorkup);
			
			try {
				storageService.upload(ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo), diretorio,
						arquivo);
			}
			catch (IOException e) {
				throw new BusinessException("arquivo.erro.upload.arquivo", e);
			}
		}
		
	}
	
	private void fecharTarefaInformarPlanoWorkupInternacional(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao) throws JsonProcessingException {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		fecharTarefa(rmr, TiposTarefa.INFORMAR_PLANO_WORKUP_INTERNACIONAL, idPedidoWorkup);		
	}
	
	@Override
	public void aprovarPlanoWorkup(Long idPedidoWorkup, AprovarPlanoWorkupDTO planoWorkup) throws JsonProcessingException {

		LOGGER.info("O Centro de Transplante fará a aprovação do plano de workup.");

		PedidoWorkup plano = obterPedidoWorkup(idPedidoWorkup);
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.obterSolicitacaoWorkup(plano.getSolicitacao());

		planoWorkup.setDataColeta(plano.getDataColeta());
		
		validarAprovacaoPlanoWorkup(planoWorkup);
		plano.setDataCondicionamento(planoWorkup.getDataCondicionamento());
		plano.setDataInfusao(planoWorkup.getDataInfusao());
		plano.setCriopreservacao(planoWorkup.getCriopreservacao());
		plano.setObservacaoAprovaPlanoWorkup(planoWorkup.getObservacaoAprovaPlanoWorkup());
		this.pedidoWorkupRepository.save(plano);
		
		fecharTarefaConfirmarPlanoWorkup(plano.getId(), solicitacao);
		
		if (solicitacao.solicitacaoWorkupInternacional()) {		
			solicitacao = solicitacaoFeign.atualizarFaseWorkup(plano.getSolicitacao(), FasesWorkup.AGUARDANDO_RESULTADO_WORKUP.getId());
			this.criarTarefaResultadoWorkup(plano.getId(), solicitacao);
		}else {
			solicitacao = solicitacaoFeign.atualizarFaseWorkup(plano.getSolicitacao(), FasesWorkup.AGUARDANDO_LOGISTICA_DOADOR_WORKUP.getId());
			PedidoLogisticaDoadorWorkup pedidoLogistica = this.pedidoLogisticaDoadorService.criarPedidoLogisticaDoadorWorkup(plano);
			this.criarTarefaLogisticaDoadorWorkup(pedidoLogistica.getId(), solicitacao);
		}
		
		LOGGER.info("O Centro de transplante aprovou o plano de workup.");
		
	}
	
	private void validarAprovacaoPlanoWorkup(AprovarPlanoWorkupDTO planoWorkup) {
		validarValorNulo("dataCondicionamento", planoWorkup.getDataCondicionamento());
		validarDataMenor("dataCondicionamento", planoWorkup.getDataCondicionamento(), "Data Atual", LocalDate.now());
		
		validarValorNulo("dataInfusao", planoWorkup.getDataInfusao());
		validarDataMenor("dataInfusao", planoWorkup.getDataInfusao(), "Data Atual", LocalDate.now());

		validarDataMenor("dataInfusao", planoWorkup.getDataInfusao(), "dataCondicionamento", planoWorkup.getDataCondicionamento());
		validarDataMenor("dataInfusao", planoWorkup.getDataInfusao(), "dataColeta", planoWorkup.getDataColeta());
	}

	/**
	 * @param pedidoWorkup
	 * @param rmr
	 * @return
	 */
	public void criarTarefaLogisticaDoadorWorkup(Long idPedidoLogistica, SolicitacaoWorkupDTO solicitacao) {
		
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		TarefaDTOBuilder tarefa = TarefaDTO.builder()
				.processo(new ProcessoDTO(TipoProcesso.BUSCA, rmr))
				.relacaoEntidade(idPedidoLogistica)		
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_LOGISTICA_DOADOR_WORKUP.getId()))
				.perfilResponsavel(Perfis.ANALISTA_LOGISTICA.getId()) 
				.status(StatusTarefa.ATRIBUIDA.getId())
				.usuarioResponsavel(solicitacao.getUsuarioResponsavel().getId());
		
		tarefaHelper.criarTarefa(tarefa.build());
	}
	
	/**
	 * @param pedidoWorkup
	 * @param rmr
	 * @return
	 */
	private void criarTarefaResultadoWorkup(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao) {
		
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		TarefaDTOBuilder tarefa = TarefaDTO.builder()
				.processo(new ProcessoDTO(TipoProcesso.BUSCA, rmr))
				.relacaoEntidade(idPedidoWorkup)		
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_RESULTADO_WORKUP_INTERNACIONAL.getId()))
				.perfilResponsavel(Perfis.ANALISTA_WORKUP_INTERNACIONAL.getId()) 
				.status(StatusTarefa.ATRIBUIDA.getId())
				.usuarioResponsavel(solicitacao.getUsuarioResponsavel().getId());
		
		tarefaHelper.criarTarefa(tarefa.build());
	}
	
	private void fecharTarefaConfirmarPlanoWorkup(Long idPedidoWorkup, SolicitacaoDTO solicitacao) throws JsonProcessingException {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		fecharTarefa(rmr, TiposTarefa.CONFIRMAR_PLANO_WORKUP, idPedidoWorkup);
		
	}
	
	@Override
	public PedidoWorkup obterPedidoWorkupPorSolicitacao(Long idSolicitacao) {
		if (idSolicitacao == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return pedidoWorkupRepository.findBySolicitacao(idSolicitacao).orElseThrow(() -> new BusinessException("erro.nao.existe", "Pedido de Workup"));
	}
	
	@Override
	public PedidoWorkup obterPedidoWorkupEmAberto(Long id) {
		PedidoWorkup pedidoWorkup = obterPedidoWorkup(id);
		if (!pedidoWorkup.getStatus().equals(StatusPedidosWorkup.INICIADO.getId())) {
			throw new BusinessException("erro.pedido.workup.finalizado.ou.cancelado",  
					pedidoWorkup.getStatus().equals(StatusPedidosWorkup.FINALIZADO.getId()) ? "já realizado" : "cancelado" );
		}
		return pedidoWorkup;
	}
	
	@Override
	public void finalizarWorkupInternacional(Long id) {
		PedidoWorkup pedidoWorkup = obterPedidoWorkupEmAberto(id);
		pedidoWorkup.setStatus(StatusPedidosWorkup.FINALIZADO.getId());		
		save(pedidoWorkup);
	}

	@Override
	public void finalizarWorkupNacional(Long id) {
		PedidoWorkup pedidoWorkup = obterPedidoWorkupEmAberto(id);
		pedidoWorkup.setStatus(StatusPedidosWorkup.FINALIZADO.getId());		
		save(pedidoWorkup);
	}
	
	@Override
	public void cancelarWorkupInternacional(Long id) {
		PedidoWorkup pedidoWorkup = obterPedidoWorkupEmAberto(id);
		pedidoWorkup.setStatus(StatusPedidosWorkup.CANCELADO.getId());		
		save(pedidoWorkup);
		
		SolicitacaoDTO solicitacao = solicitacaoFeign.cancelarSolicitacaoWorkup(pedidoWorkup.getSolicitacao());
		criarNotificacaoParaCentroTransplatePrescricaoCancelada(solicitacao);
		
	}
	
	private void criarNotificacaoParaCentroTransplatePrescricaoCancelada(SolicitacaoDTO solicitacao) {
		
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final String identificacaoDoador = solicitacao.getMatch().getDoador().getIdentificacao();
		
		final CategoriasNotificacao categoria = CategoriasNotificacao.PRESCRICAO_INTERNACIONAL_CANCELADA;
		NotificacaoDTO notificacao = NotificacaoDTO.builder()
				.rmr(rmr)
				.descricao(String.format("Prescrição do paciente %d com o doador %s foi cancelada.", rmr, identificacaoDoador) )
				.categoriaId(categoria.getId())
				.idPerfil(categoria.getPerfil() != null ? categoria.getPerfil().getId() : null)
				.lido(false)
				.build();
		
		notificacaoFeign.criarNotificacao(notificacao);
	}

	@Override
	public void cancelarWorkupNacional(Long id) {
		PedidoWorkup pedidoWorkup = obterPedidoWorkupEmAberto(id);
		pedidoWorkup.setStatus(StatusPedidosWorkup.CANCELADO.getId());		
		save(pedidoWorkup);
		
		solicitacaoFeign.cancelarSolicitacaoWorkup(pedidoWorkup.getSolicitacao());
	}
	
	
	@Override
	public List<ConsultaTarefasWorkupDTO> listarTarefasWorkupView(FiltroListaTarefaWorkupDTO filtro) throws JsonProcessingException {
		return this.pedidoWorkupRepository.listarTarefasWorkupView(filtro);
	}

	@Override
	public List<ConsultaTarefasWorkupDTO> listarSolicitacoesWorkupView(FiltroListaTarefaWorkupDTO filtro) throws JsonProcessingException {
		return this.pedidoWorkupRepository.listarSolicitacoesWorkupView(filtro);
	}

}
