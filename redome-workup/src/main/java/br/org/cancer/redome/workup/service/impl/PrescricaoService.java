package br.org.cancer.redome.workup.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.redome.workup.dto.BuscaDTO;
import br.org.cancer.redome.workup.dto.ConsultaPrescricaoDTO;
import br.org.cancer.redome.workup.dto.CriarLogEvolucaoDTO;
import br.org.cancer.redome.workup.dto.CriarPrescricaoCordaoDTO;
import br.org.cancer.redome.workup.dto.CriarPrescricaoMedulaDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.EvolucaoDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.MedicoDTO;
import br.org.cancer.redome.workup.dto.NotificacaoDTO;
import br.org.cancer.redome.workup.dto.PrescricaoCordaoDTO;
import br.org.cancer.redome.workup.dto.PrescricaoDTO;
import br.org.cancer.redome.workup.dto.PrescricaoEvolucaoDTO;
import br.org.cancer.redome.workup.dto.PrescricaoMedulaDTO;
import br.org.cancer.redome.workup.dto.PrescricaoSemAutorizacaoPacienteDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.UsuarioDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.IEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.IMedicoFeign;
import br.org.cancer.redome.workup.feign.client.INotificacaoFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.feign.client.ITarefaFeign;
import br.org.cancer.redome.workup.feign.client.IUsuarioFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.ArquivoPrescricao;
import br.org.cancer.redome.workup.model.AvaliacaoResultadoWorkup;
import br.org.cancer.redome.workup.model.FonteCelula;
import br.org.cancer.redome.workup.model.PedidoColeta;
import br.org.cancer.redome.workup.model.Prescricao;
import br.org.cancer.redome.workup.model.PrescricaoCordao;
import br.org.cancer.redome.workup.model.PrescricaoMedula;
import br.org.cancer.redome.workup.model.TipoAmostraPrescricao;
import br.org.cancer.redome.workup.model.domain.CategoriasNotificacao;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.StatusSolicitacao;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoLogEvolucao;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.persistence.IPrescricaoRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IArquivoPrescricaoService;
import br.org.cancer.redome.workup.service.IAvaliacaoPrescricaoService;
import br.org.cancer.redome.workup.service.IAvaliacaoResultadoWorkupService;
import br.org.cancer.redome.workup.service.IConfiguracaoService;
import br.org.cancer.redome.workup.service.IPedidoColetaService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaMaterialColetaInternacionalService;
import br.org.cancer.redome.workup.service.IPrescricaoService;
import br.org.cancer.redome.workup.service.IStorageService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;
import br.org.cancer.redome.workup.util.ArquivoUtil;
import br.org.cancer.redome.workup.util.CustomPageImpl;

@Service
public class PrescricaoService extends AbstractService<Prescricao, Long> implements IPrescricaoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrescricaoService.class);
	
	private static final Long INTERVALO_DIAS_LIMITE_PRESCRICAO_MEDULA = 30L;
	private static final Long INTERVALO_DIAS_LIMITE_PRESCRICAO_CORDAO = 20L;
	
	@Autowired
	private IPrescricaoRepository prescricaoRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IConfiguracaoService configuracaoService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private IStorageService storageService;
	
	@Autowired
	@Lazy(true)
	private IEvolucaoFeign evolucaoFeign;
	
	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	@Lazy(true)
	private IMedicoFeign medicoFeign;
	
	@Autowired
	private IArquivoPrescricaoService arquivoPrescricaoService;
	
	@Autowired
	@Lazy(true)
	private ITarefaFeign tarefaFeign;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IAvaliacaoPrescricaoService avaliacaoPrescricaoService;
	
	@Autowired
	@Lazy(true)
	private ILogEvolucaoFeign logEvolucaoFeign;
	
	@Autowired
	@Lazy(true)
	private INotificacaoFeign notificacaoFeign;
	
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;
	
	@Autowired
	@Lazy(true)
	private IUsuarioFeign usuarioFeign;
	
	@Autowired
	@Lazy(true)
	private IAvaliacaoResultadoWorkupService avaliacaoResultadoWorkupService; 
	
	@Autowired
	@Lazy(true)
	private IPedidoLogisticaMaterialColetaInternacionalService pedidoLogisticaService;
	
	@Autowired
	@Lazy(true)
	private IPedidoColetaService pedidoColetaService;

	@Override
	public IRepository<Prescricao, Long> getRepository() {	
		return prescricaoRepository;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void criarPrescricao(CriarPrescricaoMedulaDTO prescricaoMedulaDTO, MultipartFile arquivoJustificativa,
			List<MultipartFile> listaArquivos) throws Exception {
		
		if (prescricaoMedulaDTO.isNacional()) {
			criarPrescricaoMedulaDoadorNacionalPacienteNacional(prescricaoMedulaDTO, arquivoJustificativa, listaArquivos);			
		}
		if (prescricaoMedulaDTO.isInternacional()) {
			criarPrescricaoMedulaDoadorInternacional(prescricaoMedulaDTO, arquivoJustificativa, listaArquivos);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void criarPrescricao(CriarPrescricaoCordaoDTO prescricaoCordaoDTO, MultipartFile arquivoJustificativa,
			List<MultipartFile> listaArquivos) throws Exception {
		if (prescricaoCordaoDTO.isNacional()) {
			criarPrescricaoCordaoNacionalPacienteNacional(prescricaoCordaoDTO, arquivoJustificativa, listaArquivos);			
		}
		if (prescricaoCordaoDTO.isInternacional()) {
			criarPrescricaoCordaoInternacional(prescricaoCordaoDTO, arquivoJustificativa, listaArquivos);
		}
	}
		
	private void criarPrescricaoMedulaDoadorNacionalPacienteNacional(CriarPrescricaoMedulaDTO prescricaoMedulaDTO, MultipartFile arquivoJustificativa,
			List<MultipartFile> listaArquivos) throws Exception {
		
		PrescricaoMedula prescricao = criarPrescricaoMedula(prescricaoMedulaDTO, arquivoJustificativa, listaArquivos);
		
		final EvolucaoDTO ultimaEvolucaoPaciente = evolucaoFeign.obterUltimaEvolucaoDoPaciente(prescricaoMedulaDTO.getRmr());
		prescricao.setEvolucao(ultimaEvolucaoPaciente.getId());

		SolicitacaoDTO solicitacao = solicitacaoFeign.criarSolicitacaoWorkupDoadorNacionalPacienteNacioanl(prescricaoMedulaDTO.getIdMatch());
		prescricao.setSolicitacao(solicitacao.getId());
		
		prescricao.setCentroTransplante(solicitacao.getMatch().getBusca().getCentroTransplante().getId());
		
		save(prescricao);
		salvarArquivosPrescricao(prescricao, listaArquivos, arquivoJustificativa, null);
		
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
				
		avaliacaoPrescricaoService.criarAvaliacaoPrescricao(rmr, prescricao);
				
		TratarTarefaPrescricaoMedula(solicitacao.getMatch().getBusca(), prescricao);
		
		criarNotificacaoAnalistaWorkup(solicitacao);
		
		criarLogEvolucao(TipoLogEvolucao.PRESCRICAO_CRIADA_PARA_MEDULA, rmr, solicitacao.getMatch().getDoador());
		
	}
	
	private void criarNotificacaoAnalistaWorkup(SolicitacaoDTO solicitacao) {
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final String identificacaoDoador = solicitacao.getMatch().getDoador().getIdentificacao();
		final String tipo = solicitacao.solicitacaoWorkupMedula() ? "doador" : "cordão";
		
		final CategoriasNotificacao categoria = obterCategoriaNotificacao(solicitacao);
		NotificacaoDTO notificacao = NotificacaoDTO.builder()
				.rmr(rmr)
				.descricao(String.format("Criada prescrição para o %s %s com match para o paciente %d", tipo, identificacaoDoador, rmr) )
				.categoriaId(categoria.getId())
				.idPerfil(categoria.getPerfil() != null ? categoria.getPerfil().getId() : null)
				.lido(false)
				.build();
		
		notificacaoFeign.criarNotificacao(notificacao);
		
	}

	private CategoriasNotificacao obterCategoriaNotificacao(SolicitacaoDTO solicitacao) {
		if (solicitacao.solicitacaoWorkupNacional()) {
			return CategoriasNotificacao.PRESCRICAO_NACIONAL;
		}
		return CategoriasNotificacao.PRESCRICAO_INTERNACIONAL;
	}

	private void criarPrescricaoMedulaDoadorInternacional(CriarPrescricaoMedulaDTO prescricaoMedulaDTO, MultipartFile arquivoJustificativa,
			List<MultipartFile> listaArquivos) throws Exception {
		
		PrescricaoMedula prescricao = criarPrescricaoMedula(prescricaoMedulaDTO, arquivoJustificativa, listaArquivos);
		
		final EvolucaoDTO ultimaEvolucaoPaciente = evolucaoFeign.obterUltimaEvolucaoDoPaciente(prescricaoMedulaDTO.getRmr());				
		prescricao.setEvolucao(ultimaEvolucaoPaciente.getId());

		SolicitacaoDTO solicitacao = solicitacaoFeign.criarSolicitacaoWorkupDoadorInternacionalPacienteNacioanl(prescricaoMedulaDTO.getIdMatch());
		prescricao.setSolicitacao(solicitacao.getId());
		
		prescricao.setCentroTransplante(solicitacao.getMatch().getBusca().getCentroTransplante().getId());
		
		save(prescricao);
		salvarArquivosPrescricao(prescricao, listaArquivos, arquivoJustificativa, null);
		
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		
		avaliacaoPrescricaoService.criarAvaliacaoPrescricao(rmr, prescricao);
		
		TratarTarefaPrescricaoMedula(solicitacao.getMatch().getBusca(), prescricao);
		
		criarNotificacaoAnalistaWorkup(solicitacao);
		
		criarLogEvolucao(TipoLogEvolucao.PRESCRICAO_CRIADA_PARA_MEDULA, rmr, solicitacao.getMatch().getDoador());
	}
	
	private void TratarTarefaPrescricaoMedula(BuscaDTO busca, Prescricao prescricao) throws JsonProcessingException {
		TarefaDTO tarefaCadastrarPrescricao = atribuirTarefaCadastrarPrescricao(
				TiposTarefa.CADASTRAR_PRESCRICAO_MEDULA, 
				busca.getPaciente().getRmr(), 
				busca.getCentroTransplante().getId());
		
		tarefaCadastrarPrescricao = atualizarTarefaCadastrarPrescricao(tarefaCadastrarPrescricao, prescricao);
		fecharTarefaCadastrarPrescricao(tarefaCadastrarPrescricao);
		cancelarTarefaCadastrarPrescricao(busca, TiposTarefa.CADASTRAR_PRESCRICAO_CORDAO);
	}
	
	private PrescricaoMedula criarPrescricaoMedula(CriarPrescricaoMedulaDTO prescricaoMedulaDTO, MultipartFile arquivoJustificativa,
			List<MultipartFile> listaArquivos) {
		if (fontesCelulaInvalidas(prescricaoMedulaDTO.getFonteCelulaOpcao1(), prescricaoMedulaDTO.getFonteCelulaOpcao2())) {
			throw new BusinessException("erro.solicitacao.prescricao.fonte.celulas.iguais");			
		}
		if (verificaSeDataEstaDentroLimite(prescricaoMedulaDTO.getDataColeta1(), INTERVALO_DIAS_LIMITE_PRESCRICAO_MEDULA)
				|| verificaSeDataEstaDentroLimite(prescricaoMedulaDTO.getDataColeta2(),
						INTERVALO_DIAS_LIMITE_PRESCRICAO_MEDULA)) {
			ArquivoUtil.validarArquivoJustificativaPrescricao(arquivoJustificativa, messageSource, configuracaoService);
		}
		
		ArquivoUtil.validarArquivosPrescricao(listaArquivos, messageSource, configuracaoService);
		
		PrescricaoMedula prescricao = new PrescricaoMedula();
		prescricao.setDataColeta1(prescricaoMedulaDTO.getDataColeta1());
		prescricao.setDataColeta2(prescricaoMedulaDTO.getDataColeta2());
		prescricao.setDataResultadoWorkup1(prescricaoMedulaDTO.getDataLimiteWorkup1());
		prescricao.setDataResultadoWorkup2(prescricaoMedulaDTO.getDataLimiteWorkup2());
		prescricao.setFonteCelulaOpcao1(new FonteCelula(prescricaoMedulaDTO.getFonteCelulaOpcao1()));
		if (prescricaoMedulaDTO.getQuantidadeTotalOpcao1() != null) {
			prescricao.setQuantidadeTotalOpcao1(prescricaoMedulaDTO.getQuantidadeTotalOpcao1().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		}
		if (prescricaoMedulaDTO.getQuantidadePorKgOpcao1() != null) {
			prescricao.setQuantidadePorKgOpcao1(prescricaoMedulaDTO.getQuantidadePorKgOpcao1().setScale(2, BigDecimal.ROUND_HALF_EVEN));
		}
		if (prescricaoMedulaDTO.getFonteCelulaOpcao2() != null) {
			prescricao.setFonteCelulaOpcao2(new FonteCelula(prescricaoMedulaDTO.getFonteCelulaOpcao2()));
		}
		if (prescricaoMedulaDTO.getQuantidadeTotalOpcao2() != null) {
			prescricao.setQuantidadeTotalOpcao2(prescricaoMedulaDTO.getQuantidadeTotalOpcao2().setScale(2,
					BigDecimal.ROUND_HALF_EVEN));
		}
		if (prescricaoMedulaDTO.getQuantidadePorKgOpcao2() != null) {
			prescricao.setQuantidadePorKgOpcao2(prescricaoMedulaDTO.getQuantidadePorKgOpcao2().setScale(2,
					BigDecimal.ROUND_HALF_EVEN));
		}
		prescricao.setFazerColeta(prescricaoMedulaDTO.getFazerColeta() == null ? false : prescricaoMedulaDTO.getFazerColeta());
		
		if(CollectionUtils.isNotEmpty(prescricaoMedulaDTO.getTiposAmostraPrescricao())) {
			prescricao.setAmostras(new ArrayList<>());
			prescricaoMedulaDTO.getTiposAmostraPrescricao().stream()
				.map(t -> TipoAmostraPrescricao.builder()
						.ml(t.getMl())
						.tipoAmostra(t.getTipoAmostra())
						.prescricao(prescricao)
						.descricaoOutrosExames(t.getDescricaoOutrosExames())
						.build())
				.forEach(prescricao.getAmostras()::add);			
						
		}
		final MedicoDTO medico = medicoFeign.obterMedicoAssociadoUsuarioLogado();
		prescricao.setMedico(medico.getId());
		
		return prescricao;
	}

	private boolean fontesCelulaInvalidas(Long fonteCelulaOpcao1, Long fonteCelulaOpcao2) {
		if (fonteCelulaOpcao1 == null) {
			return true;
		}
		
		return fonteCelulaOpcao2 != null ? fonteCelulaOpcao1.equals(fonteCelulaOpcao2) : false;
	}
	
	private Boolean verificaSeDataEstaDentroLimite(LocalDate data, Long diasLimiteColeta) {
		if (data != null && LocalDate.now().isBefore(data)) {
			Long dias = LocalDate.now().until(data, ChronoUnit.DAYS);
			return dias >= 0 && dias <= diasLimiteColeta;
		}
		return false;
	}

	private void criarPrescricaoCordaoNacionalPacienteNacional(CriarPrescricaoCordaoDTO prescricaoCordaoDTO, MultipartFile arquivoJustificativa,
			List<MultipartFile> listaArquivos) throws JsonProcessingException {
		
		PrescricaoCordao prescricao = criarPrescricaoCordao(prescricaoCordaoDTO, arquivoJustificativa, listaArquivos);
		
		final EvolucaoDTO ultimaEvolucaoPaciente = evolucaoFeign.obterUltimaEvolucaoDoPaciente(prescricaoCordaoDTO.getRmr());
		prescricao.setEvolucao(ultimaEvolucaoPaciente.getId());

		SolicitacaoDTO solicitacao = solicitacaoFeign.criarSolicitacaoWorkupCordaoNacionalPacienteNacioanl(prescricaoCordaoDTO.getIdMatch());
		prescricao.setSolicitacao(solicitacao.getId());
		
		save(prescricao);
		salvarArquivosPrescricao(prescricao, listaArquivos, arquivoJustificativa, null);
		
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
				
		avaliacaoPrescricaoService.criarAvaliacaoPrescricao(rmr, prescricao);
				
		TratarTarefaPrescricaoCordao(solicitacao.getMatch().getBusca(), prescricao);
		
		criarNotificacaoAnalistaWorkup(solicitacao);
		
		criarLogEvolucao(TipoLogEvolucao.PRESCRICAO_CRIADA_PARA_CORDAO, rmr, solicitacao.getMatch().getDoador());

	}

	private void TratarTarefaPrescricaoCordao(BuscaDTO busca, PrescricaoCordao prescricao) throws JsonProcessingException {
		TarefaDTO tarefaCadastrarPrescricao = atribuirTarefaCadastrarPrescricao(TiposTarefa.CADASTRAR_PRESCRICAO_CORDAO, busca.getPaciente().getRmr(), 
				busca.getCentroTransplante().getId());
		
		tarefaCadastrarPrescricao = atualizarTarefaCadastrarPrescricao(tarefaCadastrarPrescricao, prescricao);
		fecharTarefaCadastrarPrescricao(tarefaCadastrarPrescricao);
		cancelarTarefaCadastrarPrescricao(busca, TiposTarefa.CADASTRAR_PRESCRICAO_MEDULA);		
	}
	
	private void cancelarTarefaCadastrarPrescricao(BuscaDTO busca, TiposTarefa tiposTarefa) throws JsonProcessingException {
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(tiposTarefa.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId()))
				.parceiros(Arrays.asList(busca.getCentroTransplante().getId()))
				.rmr(busca.getPaciente().getRmr())
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		String jsonFiltroDTO = objectMapper.writeValueAsString(filtroDTO); 
		String filtro = Base64Utils.encodeToString(jsonFiltroDTO.getBytes());
		List<TarefaDTO> tarefas = tarefaFeign.listarTarefas(filtro).getContent();		
		if (CollectionUtils.isNotEmpty(tarefas)) {
			tarefas.forEach(tarefa -> {
				tarefaFeign.cancelarTarefa(tarefa.getId(), false);
			});
		}
				
	}

	private void criarPrescricaoCordaoInternacional(CriarPrescricaoCordaoDTO prescricaoCordaoDTO, MultipartFile arquivoJustificativa,
			List<MultipartFile> listaArquivos) throws JsonProcessingException {

		PrescricaoCordao prescricao = criarPrescricaoCordao(prescricaoCordaoDTO, arquivoJustificativa, listaArquivos);
		
		final EvolucaoDTO ultimaEvolucaoPaciente = evolucaoFeign.obterUltimaEvolucaoDoPaciente(prescricaoCordaoDTO.getRmr());
		prescricao.setEvolucao(ultimaEvolucaoPaciente.getId());

		SolicitacaoDTO solicitacao = solicitacaoFeign.criarSolicitacaoWorkupCordaoInternacionalPacienteNacioanl(prescricaoCordaoDTO.getIdMatch());
		prescricao.setSolicitacao(solicitacao.getId());
		
		save(prescricao);
		salvarArquivosPrescricao(prescricao, listaArquivos, arquivoJustificativa, null);
		
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
				
		avaliacaoPrescricaoService.criarAvaliacaoPrescricao(rmr, prescricao);
				
		TratarTarefaPrescricaoCordao(solicitacao.getMatch().getBusca(), prescricao);
		
		criarNotificacaoAnalistaWorkup(solicitacao);
		
		criarLogEvolucao(TipoLogEvolucao.PRESCRICAO_CRIADA_PARA_CORDAO, rmr, solicitacao.getMatch().getDoador());

	}
	
	private PrescricaoCordao criarPrescricaoCordao(CriarPrescricaoCordaoDTO prescricaoCordaoDTO, MultipartFile arquivoJustificativa,
			List<MultipartFile> listaArquivos) {
		
		validarDatas(prescricaoCordaoDTO);
		
		if (verificaSeDataEstaDentroLimite(prescricaoCordaoDTO.getDataReceber1(), INTERVALO_DIAS_LIMITE_PRESCRICAO_CORDAO)
				|| verificaSeDataEstaDentroLimite(prescricaoCordaoDTO.getDataReceber2(), INTERVALO_DIAS_LIMITE_PRESCRICAO_CORDAO)
				|| verificaSeDataEstaDentroLimite(prescricaoCordaoDTO.getDataReceber3(), INTERVALO_DIAS_LIMITE_PRESCRICAO_CORDAO)) {
			ArquivoUtil.validarArquivoJustificativaPrescricao(arquivoJustificativa, messageSource, configuracaoService);
		}
		
		ArquivoUtil.validarArquivosPrescricao(listaArquivos, messageSource, configuracaoService);		
		
		PrescricaoCordao prescricao = new PrescricaoCordao();
		prescricao.setDataReceber1(prescricaoCordaoDTO.getDataReceber1());
		prescricao.setDataReceber2(prescricaoCordaoDTO.getDataReceber2());
		prescricao.setDataReceber3(prescricaoCordaoDTO.getDataReceber3());
		prescricao.setDataInfusao(prescricaoCordaoDTO.getDataInfusao());
		prescricao.setArmazenaCordao(prescricaoCordaoDTO.getArmazenaCordao());
		prescricao.setNomeContatoParaReceber(prescricaoCordaoDTO.getNomeContatoParaReceber());
		prescricao.setNomeContatoUrgente(prescricaoCordaoDTO.getNomeContatoUrgente());
		prescricao.setCodigoAreaUrgente(prescricaoCordaoDTO.getCodigoAreaUrgente());
		prescricao.setTelefoneUrgente(prescricaoCordaoDTO.getTelefoneUrgente());

		final MedicoDTO medico = medicoFeign.obterMedicoAssociadoUsuarioLogado();
		prescricao.setMedico(medico.getId());
		
		return prescricao;
	}
	
	
	private void validarDatas(CriarPrescricaoCordaoDTO prescricaoCordaoDTO) {
		if (dataInvalida(prescricaoCordaoDTO.getDataReceber1())) {
			throw new BusinessException("erro.validacao.data.menor", "Data Recebimento 1" , "data atual.");			
		}
		if (prescricaoCordaoDTO.getDataReceber2() != null && dataInvalida(prescricaoCordaoDTO.getDataReceber2())) {
			throw new BusinessException("erro.validacao.data.menor", "Data Recebimento 2" , "data atual.");			
		}
		if (prescricaoCordaoDTO.getDataReceber3() != null && dataInvalida(prescricaoCordaoDTO.getDataReceber3())) {
			throw new BusinessException("erro.validacao.data.menor", "Data Recebimento 3" , "data atual.");			
		}
		if (dataInvalida(prescricaoCordaoDTO.getDataInfusao())) {
			throw new BusinessException("erro.validacao.data.menor", "Data infusão" , "data atual.");			
		}
		
		if (prescricaoCordaoDTO.getDataInfusao().isBefore(prescricaoCordaoDTO.getDataReceber1()) 
				|| (prescricaoCordaoDTO.getDataReceber2() != null && prescricaoCordaoDTO.getDataInfusao().isBefore(prescricaoCordaoDTO.getDataReceber2()))
				|| (prescricaoCordaoDTO.getDataReceber3() != null && prescricaoCordaoDTO.getDataInfusao().isBefore(prescricaoCordaoDTO.getDataReceber3()))) {
			throw new BusinessException("erro.validacao.data.menor", "Data infusão", "data de recebimento.");
		}
		
		final boolean primeiraIgualSegunda = prescricaoCordaoDTO.getDataReceber2() == null ? false : prescricaoCordaoDTO.getDataReceber1().equals(prescricaoCordaoDTO.getDataReceber2());
		final boolean primeiraIgualTerceira = prescricaoCordaoDTO.getDataReceber3() == null ? false : prescricaoCordaoDTO.getDataReceber1().equals(prescricaoCordaoDTO.getDataReceber3());
		final boolean segundaIgualTerceira = prescricaoCordaoDTO.getDataReceber2() == null || prescricaoCordaoDTO.getDataReceber3() == null  ? false : prescricaoCordaoDTO.getDataReceber2().equals(prescricaoCordaoDTO.getDataReceber3());
				
		if (primeiraIgualSegunda || primeiraIgualTerceira || segundaIgualTerceira ) {
			throw new BusinessException("erro.validacao.data.diferente", "datas de recebimento");
		}		
		
	}

	private boolean dataInvalida(LocalDate data) {
		return  data == null ? true : LocalDate.now().isAfter(data);		
	}

	/**
	 * Salva os arquivos relacionados a prescrição.
	 * 
	 * @param prescricao prescrição salva no banco.
	 * @param listaArquivos lista de arquivos da prescrição.
	 * @param arquivoJustificativa arquivo de justificativa
	 * @param arquivoAutorizacaoPaciente arquivo de autorização do paciente
	 * @return
	 */
	private void salvarArquivosPrescricao(Prescricao prescricao, List<MultipartFile> listaArquivos, 
			MultipartFile arquivoJustificativa, MultipartFile arquivoAutorizacaoPaciente ) {
		
		Optional.ofNullable(listaArquivos).ifPresent((lista) -> {
			lista.stream().forEach(arquivo -> {
				salvarArquivo(prescricao, false, false, arquivo);
			});	
		});
		
		if (arquivoJustificativa != null) {
			salvarArquivo(prescricao, true, false, arquivoJustificativa);
		}
		
		if (arquivoAutorizacaoPaciente != null) {
			salvarArquivo(prescricao, false, true, arquivoAutorizacaoPaciente);
		}
		
	}
	
	/**
	 * Salva o arquivo de prescrição.
	 * O arquivo nunca poderá ser ao mesmo tempo do tipo justificativa e autorização do paciene 
	 * 
	 * @param prescricao prescrição salva.
	 * @param justificativa - indica que o aquivo é de justificativa
	 * @param autorizacaoPaciente - indica que o arquivo é  de autorização do paciente
	 * @param arquivo arquivo com a justificativa.
	 */
	private void salvarArquivo(Prescricao prescricao, Boolean justificativa, Boolean autorizacaoPaciente, 
			MultipartFile arquivo) {
		
		if (justificativa && autorizacaoPaciente) {
			throw new BusinessException("erro.prescricao.arquivo.justificativa.autorizacao.paciente");
		}
		
		String diretorio = ArquivoUtil.obterDiretorioArquivosPrescricao(prescricao.getId());
		Instant instant = Instant.now();
		final long timeStampMillis = instant.toEpochMilli();

		if (arquivo != null) {
			ArquivoPrescricao arquivoPrescricao = new ArquivoPrescricao();
			arquivoPrescricao.setCaminho(diretorio + "/"
					+ ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo));
			arquivoPrescricao.setPrescricao(prescricao);
			arquivoPrescricao.setJustificativa(justificativa);
			arquivoPrescricao.setAutorizacaoPaciente(autorizacaoPaciente);
			arquivoPrescricaoService.save(arquivoPrescricao);
			
			Optional.ofNullable(prescricao.getArquivosPrescricao())
				.orElseGet(() -> {
					prescricao.setArquivosPrescricao(new ArrayList<>());
					return prescricao.getArquivosPrescricao();
				})
				.add(arquivoPrescricao);
			
		}

		if (arquivo != null) {
			try {
				storageService.upload(ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo), diretorio,
						arquivo);
			}
			catch (IOException e) {
				throw new BusinessException("erro.arquivo.upload", e);
			}
		} 
	}
	
	private TarefaDTO atribuirTarefaCadastrarPrescricao(TiposTarefa tiposTarefa, Long rmr, Long idCentroTransplante) throws JsonProcessingException {
		//final Long rmr = prescricao.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		//final Long idCentroTransplante = prescricao.getSolicitacao().getMatch().getBusca().getCentroTransplante().getId();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(tiposTarefa.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId()))
				.parceiros(Arrays.asList(idCentroTransplante))
				.rmr(rmr)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		String jsonFiltroDTO = objectMapper.writeValueAsString(filtroDTO); 
		String filtro = Base64Utils.encodeToString(jsonFiltroDTO.getBytes());
		List<TarefaDTO> tarefas = tarefaFeign.listarTarefas(filtro).getContent();		
		if (CollectionUtils.isEmpty(tarefas)) {
			throw new BusinessException("tarefa.nao.encontrada");
		}
		TarefaDTO tarefa = tarefas.get(0);

		return tarefaFeign.atribuirTarefaUsuario(tarefa.getId(), usuarioService.obterIdUsuarioLogado());
	}
	
	
	public TarefaDTO atualizarTarefaCadastrarPrescricao(TarefaDTO tarefa, Prescricao prescricao) {
		TarefaDTO tarefaParaAtualizar = tarefa.toBuilder().relacaoEntidade(prescricao.getId()).build();
		
		return tarefaFeign.atualizarTarefa(tarefaParaAtualizar .getId(), tarefaParaAtualizar );
		
	}
	
	public void fecharTarefaCadastrarPrescricao(TarefaDTO tarefa) {
		
		tarefaFeign.encerrarTarefa(tarefa.getId(), false);
				
	}
	
	private void criarLogEvolucao(TipoLogEvolucao tipoLogEvolucao,Long rmr, DoadorDTO doador) {
		
		String[] parametros = {doador.getIdentificacao()};
		
		logEvolucaoFeign.criarLogEvolucao(CriarLogEvolucaoDTO.builder()
				.tipo(tipoLogEvolucao.name())
				.rmr(rmr)
				.parametros(parametros )
				.build());
				
	}
	
	/**
	 * Lista pacientes com prescrições disponiveis. 
	 * 
	 * @param idCentroTransplante
	 * @param pageable
	 * @return Page<PacientePrescricaoDTO>
	 */
	@Override
	public Page<ConsultaPrescricaoDTO> listarPrescricoesDisponiveis(Long idCentroTransplante, PageRequest pageable) throws JsonProcessingException {

		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.CADASTRAR_PRESCRICAO.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId()))
				.parceiros(Arrays.asList(idCentroTransplante))
				.pageable(pageable)
				.build();
		String jsonFiltroDTO = objectMapper.writeValueAsString(filtroDTO); 
		String filtro = Base64Utils.encodeToString(jsonFiltroDTO.getBytes());
		List<TarefaDTO> tarefas = tarefaFeign.listarTarefas(filtro).getContent();		
		
		List<ConsultaPrescricaoDTO> listaDto = new ArrayList<>();
		long total = 0;
		if (CollectionUtils.isNotEmpty(tarefas)) {
			total = tarefas.size();
			tarefas.stream().map(tarefa -> {
				ConsultaPrescricaoDTO dto = new ConsultaPrescricaoDTO();
				dto.setRmr(tarefa.getProcesso().getRmr());
				dto.setNomePaciente(tarefa.getProcesso().getNomePaciente());
				dto.setAgingDaTarefa(tarefa.getAging());
				return dto;
			})
			.distinct()
			.forEach(listaDto::add);
		}
		return new PageImpl<>(listaDto, pageable, total);
	}
	
	
	/**
	 * Listar pacientes com prescrição por centro de transplante.
	 * 
	 * @param status
	 * @param idCentroTransplante
	 * @param pageable
	 * @return Page<PacientePrescricaoDTO>
	 */
	@Override
	public Page<ConsultaPrescricaoDTO> listarPrescricoesPorStatusECentroTransplante(Long idCentroTransplante, String[] statusSolicitacao, Pageable pageable) {

		CustomPageImpl<SolicitacaoDTO> listaSolicitacoes = solicitacaoFeign.listarSolicitacaoPorCentroTransplanteEStatus(
				idCentroTransplante, statusSolicitacao, pageable.getPageNumber(), pageable.getPageSize());
		
		List<ConsultaPrescricaoDTO> prescricaoDTO = new ArrayList<>(); 
		
		long total = 0;
		if (CollectionUtils.isNotEmpty(listaSolicitacoes.getContent())) {
			
			
			total = listaSolicitacoes.getTotalElements();
			listaSolicitacoes.stream().map(solicitacao -> {
				
				Prescricao prescricao = this.obterPrescricaoPorSolicitacao(solicitacao.getId());
				BigDecimal peso = evolucaoFeign.obterEvolucaoPorId(prescricao.getEvolucao()).getPeso();
				
				ConsultaPrescricaoDTO dto = ConsultaPrescricaoDTO.builder()
					.rmr(solicitacao.getMatch().getBusca().getPaciente().getRmr())
					.nomePaciente(solicitacao.getMatch().getBusca().getPaciente().getNome())
					.peso(peso)
					.status(StatusSolicitacao.getDescricaoPorId(solicitacao.getStatus()))
					.identificadorDoador(solicitacao.getMatch().getDoador().getIdentificacao())
					.idTipoDoador(solicitacao.getMatch().getDoador().getIdTipoDoador())
					.idDoador(solicitacao.getMatch().getDoador().getId())
					.idPrescricao(prescricao.getId())
					.build();
				return dto;
			})
			.forEach(prescricaoDTO::add);
		}
		
		return new PageImpl<>(prescricaoDTO, pageable, total);
	}
	
	@Override
	public SolicitacaoDTO cancelarPrescricao(Long id) {		
		Prescricao prescricao = obterPrescricao(id);
		return solicitacaoFeign.cancelarSolicitacaoWorkup(prescricao.getSolicitacao());
		
	}
	
	@Override
	public Prescricao obterPrescricao(Long id) {
		if (id == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return prescricaoRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Prescrição"));
	}
	
	@Override
	public Prescricao obterPrescricaoPorSolicitacao(Long idSolicitacao) {
		if (idSolicitacao == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return prescricaoRepository.findBySolicitacao(idSolicitacao).orElseThrow(() -> new BusinessException("erro.nao.existe", "Prescrição"));
	}

	@Override
	public PrescricaoMedulaDTO obterPrescricaoMedulaComEvolucaoPorId(Long idPrescricao) {
		Prescricao prescricao = this.obterPrescricao(idPrescricao);
		EvolucaoDTO evolucao = evolucaoFeign.obterEvolucaoPorId(prescricao.getEvolucao());
		return null; //PrescricaoMedulaDTO.parse(prescricao, evolucao);
	}

	@Override
	public PrescricaoCordaoDTO obterPrescricaoCordaoPorId(Long idPrescricao) {
		Prescricao prescricao = this.obterPrescricao(idPrescricao);
		return PrescricaoCordaoDTO.parse(prescricao);
	}

	@Override
	public PrescricaoEvolucaoDTO obterPrescricaoComEvolucaoPorIdPrescricao(Long idPrescricao) {
		Prescricao prescricao = this.obterPrescricao(idPrescricao);
		EvolucaoDTO evolucaoDTO = evolucaoFeign.obterEvolucaoPorId(prescricao.getEvolucao());
		SolicitacaoDTO solicitacaoDTO = solicitacaoFeign.obterSolicitacao(prescricao.getSolicitacao());
		
		PrescricaoDTO prescricaoDTO = PrescricaoDTO.parse(prescricao, solicitacaoDTO);
		
		PrescricaoEvolucaoDTO retorno = PrescricaoEvolucaoDTO.builder()
				.prescricao(prescricaoDTO)
				.ultimaEvolucao(evolucaoDTO)
				.build();
		
		return retorno;
	}

	@Override
	public Page<PrescricaoSemAutorizacaoPacienteDTO> listarPrescricoesSemAutorizacaoPaciente(
			Long idCentroTransplante, Boolean atribuidoAMin, int pagina, int quantidadeRegistros) {
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.AUTORIZACAO_PACIENTE.getId(), TiposTarefa.INFORMAR_AUTORIZACAO_PACIENTE.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId(), StatusTarefa.ATRIBUIDA.getId()))
				.parceiros(Arrays.asList(idCentroTransplante))
				.atribuidoQualquerUsuario(true)
				.build();
		
		List<TarefaDTO> tarefas = Collections.emptyList();	
		try {
			tarefas = tarefaHelper.listarTarefas(filtroDTO);
		} catch (JsonProcessingException e) {
			LOGGER.error("listarPrescricoesSemAutorizacaoPaciente", e);
			throw new BusinessException("erro.interno");
		}
		
		List<PrescricaoSemAutorizacaoPacienteDTO> listaDto = new ArrayList<>();
		long total = 0;
		if (CollectionUtils.isNotEmpty(tarefas)) {
			total = tarefas.size();
			
			tarefas.stream()
				.sorted( new Comparator<TarefaDTO>() {
					@Override
					public int compare(TarefaDTO o1, TarefaDTO o2) {
						if (o2 != null) {
							return o1.getDataCriacao().compareTo(o2.getDataCriacao()) * -1;
						}
						return 0;
					}
				})
				.skip(pagina * quantidadeRegistros)
				.limit(quantidadeRegistros)
				.map(tarefa -> {
					
					UsuarioDTO usuario = null;
					if (tarefa.getUsuarioResponsavel() != null) {
						usuario = usuarioFeign.obterUsuarios(tarefa.getUsuarioResponsavel());	
					}
					
					Prescricao prescricao = obterPrescricao(tarefa.getRelacaoEntidade());
					SolicitacaoDTO solicitacao = solicitacaoFeign.obterSolicitacao(prescricao.getSolicitacao());
										
					return PrescricaoSemAutorizacaoPacienteDTO.builder()
							.idPrescricao(tarefa.getRelacaoEntidade())
							.idTarefa(tarefa.getId())
							.idStatusTarefa(tarefa.getStatus())
							.rmr(solicitacao.getMatch().getBusca().getPaciente().getRmr())														
							.nomePaciente(solicitacao.getMatch().getBusca().getPaciente().getNome())
							.dataNascimento(solicitacao.getMatch().getBusca().getPaciente().getDataNascimento())
							.nomeUsuarioResponsavelTarefa(usuario != null ? usuario.getNome() : null)
							.agingTarefa(tarefa.getAging())
							.build();
				})
				.forEach(listaDto::add);
		}
		return new CustomPageImpl<>(listaDto, PageRequest.of(pagina, quantidadeRegistros), total);
	}
	
	@Override
	public void salvarArquivoAutorizacaoPaciente(Long id, MultipartFile arquivo) {
		Prescricao prescricao = obterPrescricao(id);
		
		ArquivoUtil.validarArquivoAutorizacaoPaciente(arquivo, messageSource, configuracaoService);
		
		salvarArquivosPrescricao(prescricao, null, null, arquivo);
		
		SolicitacaoWorkupDTO solicitacao = null;
		if (existeAvaliacaoResultadoWorkupEProsseguir(prescricao.getSolicitacao())) {
			
			FasesWorkup fase = definirFasesSolicitacao(prescricao.getSolicitacao());
			solicitacao = solicitacaoFeign.atualizarFaseWorkup(prescricao.getSolicitacao(), fase.getId());
			PedidoColeta pedidoColeta = pedidoColetaService.criarPedidoColeta(solicitacao);
			if (solicitacao.solicitacaoWorkupInternacional()) {
				pedidoLogisticaService.criarPedidoLogisticaMaterialColetaInternacional(pedidoColeta.getId());
			}
		}
		else {
			solicitacao = solicitacaoFeign.obterSolicitacaoWorkup(prescricao.getSolicitacao());
		}		
		
		fecharTarefaAutorizacaoPaciente(prescricao, solicitacao);
		
	}
	
	public FasesWorkup definirFasesSolicitacao(Long idSolicitacao) {
		SolicitacaoDTO solicitacaoDTO = solicitacaoFeign.obterSolicitacaoWorkup(idSolicitacao);
		if (solicitacaoDTO.solicitacaoWorkupInternacional()) {
			return FasesWorkup.AGUARDANDO_LOGISTICA_COLETA_INTERNACIONAL;
		}
		else if (solicitacaoDTO.solicitacaoWorkupNacional()) {
			return FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA;
		}
		return null;
	}
	
	
	private void fecharTarefaAutorizacaoPaciente(Prescricao prescricao, SolicitacaoWorkupDTO solicitacao) {
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final Long idUsuarioLogado = usuarioService.obterIdUsuarioLogado();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.AUTORIZACAO_PACIENTE.getId(), TiposTarefa.INFORMAR_AUTORIZACAO_PACIENTE.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.parceiros(Arrays.asList(prescricao.getCentroTransplante()))
				.idUsuarioLogado(idUsuarioLogado)
				.idUsuarioResponsavel(idUsuarioLogado)
				.rmr(rmr)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();		
		
		try {
			tarefaHelper.encerrarTarefa(filtroDTO, false);
		} catch (JsonProcessingException e) {
			LOGGER.error("fecharTarefaAutorizacaoPaciente", e);
			throw new BusinessException("erro.interno");
		}
		
	}

	private Boolean existeAvaliacaoResultadoWorkupEProsseguir(Long idSolicitacao) {
		
		final AvaliacaoResultadoWorkup avaliacao = avaliacaoResultadoWorkupService.obterAvaliacaoResultadoWorkupPorSolicitacao(idSolicitacao);
		if (avaliacao != null && avaliacao.getProsseguir()) {
			return true;
		}
		
		return false;		
	}
	
	
	
}
