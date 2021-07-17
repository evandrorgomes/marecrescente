package br.org.cancer.modred.service.impl;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.AvaliacaoCamaraTecnicaDTO;
import br.org.cancer.modred.controller.page.TarefaJsonPage;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.AvaliacaoCamaraTecnica;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.StatusAvaliacaoCamaraTecnica;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IAvaliacaoCamaraTecnicaRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IAvaliacaoCamaraTecnicaService;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.IExamePacienteService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.ArquivoUtil;
import br.org.cancer.modred.util.PaginacaoUtil;

/**
 * Implementação de negocios de avaliação de camara técnica.
 * @author Filipe Paes
 *
 */
@Service
public class AvaliacaoCamaraTecnicaService extends AbstractLoggingService<AvaliacaoCamaraTecnica, Long>  
implements IAvaliacaoCamaraTecnicaService {

	@Autowired
	private IAvaliacaoCamaraTecnicaRepository avaliacaoCamaraTecnicaRepository;
	
	@Autowired
	private IStorageService storageService;
	
	private IConfiguracaoService configuracaoService;
	
	private IPacienteService pacienteService;
	
	private IExamePacienteService exameService;
	
	@Override
	public IRepository<AvaliacaoCamaraTecnica, Long> getRepository() {
		return avaliacaoCamaraTecnicaRepository;
	}

	@Override
	public Page<TarefaDTO> listarTarefasAvaliacaoCamaraTecnica(Boolean somenteSobResponsabilidadeUsuarioLogado, PageRequest paginacao) {
		
		Usuario usuarioResponsavel = null;
		if (somenteSobResponsabilidadeUsuarioLogado) {
			usuarioResponsavel = usuarioService.obterUsuarioLogado();
		}
		Page<TarefaDTO> pageTarefas = TiposTarefa.AVALIACAO_CAMARA_TECNICA.getConfiguracao().listarTarefa()
					.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
					.comUsuario(usuarioResponsavel)
					.comPaginacao(paginacao)
					.apply().getValue();
		
		return ordenacaoTarefas(paginacao, null, pageTarefas);
	}
	
	private Page<TarefaDTO> ordenacaoTarefas(PageRequest paginacao, Comparator<TarefaDTO> comparator,
			Page<TarefaDTO> pageTarefas) {

		@SuppressWarnings("unchecked")
		List<TarefaDTO> tarefas = (List<TarefaDTO>) pageTarefas.getContent();
		List<TarefaDTO> modifiableListTarefa = new ArrayList<TarefaDTO>(tarefas);

		Collections.sort(modifiableListTarefa, comparator);

		List<TarefaDTO> listaPaginada = PaginacaoUtil.retornarListaPaginada(modifiableListTarefa, paginacao
				.getPageNumber(), paginacao.getPageSize());
		return new TarefaJsonPage<>(listaPaginada, paginacao, pageTarefas.getTotalElements());
	}


	@Override
	@CreateLog(value=TipoLogEvolucao.AVALIACAO_CAMARA_TECNICA_CRIADA)
	public void criarAvaliacaoInicial(Paciente paciente) {
		/*
		 * Processo processo = new Processo(); processo.setPaciente(paciente);
		 * processo.setTipo(TipoProcesso.AVALIACAO_CAMARA_TECNICA);
		 * processoService.criarProcesso(processo);
		 */
		
		AvaliacaoCamaraTecnica avaliacaoCamara = new AvaliacaoCamaraTecnica();
		avaliacaoCamara.setPaciente(paciente);
		StatusAvaliacaoCamaraTecnica status = new StatusAvaliacaoCamaraTecnica();
		status.setId(StatusAvaliacaoCamaraTecnica.AGUARDANDO_AVALIACAO);
		avaliacaoCamara.setDataAtualizacao(LocalDateTime.now());
		avaliacaoCamara.setStatus(status );
		this.save(avaliacaoCamara);
		
		TiposTarefa.AVALIACAO_CAMARA_TECNICA.getConfiguracao()
			.criarTarefa()
			.comRmr(paciente.getRmr())			
			.comObjetoRelacionado(avaliacaoCamara.getId())
			.comRmr(paciente.getRmr())
			.apply();
	}

	@Override
	@CreateLog(value=TipoLogEvolucao.AVALIACAO_CAMARA_TECNICA_APROVADA)
	public void aprovarAvaliacao(AvaliacaoCamaraTecnica avaliacao, MultipartFile arquivo) {
		salvarAvaliacao(avaliacao, arquivo, StatusAvaliacaoCamaraTecnica.APROVADO);
		
	}

	@Override
	@CreateLog(value=TipoLogEvolucao.AVALIACAO_CAMARA_TECNICA_REPROVADA)
	public void reprovarAvaliacao(AvaliacaoCamaraTecnica avaliacao, MultipartFile arquivo) {
		salvarAvaliacao(avaliacao, arquivo, StatusAvaliacaoCamaraTecnica.REPROVADO);
	}
	
	private void salvarAvaliacao(AvaliacaoCamaraTecnica avaliacao, MultipartFile arquivo, Long idStatus){
		AvaliacaoCamaraTecnica avaliacaoLocalizada = this.findById(avaliacao.getId());
		avaliacaoLocalizada.setDataAtualizacao(LocalDateTime.now());
		avaliacaoLocalizada.setStatus(new StatusAvaliacaoCamaraTecnica(idStatus));
		avaliacaoLocalizada.setPaciente(pacienteService.obterPaciente(avaliacaoLocalizada.getPaciente().getRmr()));
//		if(arquivo != null){
//			String caminho = enviarArquivoDeRelatorio(arquivo, avaliacaoLocalizada.getId());
//			avaliacaoLocalizada.setCaminhoArquivoRelatorio(caminho);			
//		}
		avaliacaoLocalizada.setJustificativa(avaliacao.getJustificativa());
		
		if(StatusAvaliacaoCamaraTecnica.APROVADO.equals(avaliacaoLocalizada.getStatus().getId())){
			exameService.criarProcessoConferenciaExames(avaliacaoLocalizada.getPaciente());
			//buscaService.iniciarProcessoDeBusca(avaliacaoLocalizada.getPaciente().getRmr());
		}
		
		this.save(avaliacaoLocalizada);
		
		TiposTarefa.AVALIACAO_CAMARA_TECNICA.getConfiguracao()
			.fecharTarefa()
			.comObjetoRelacionado(avaliacao.getId())
			.comRmr(avaliacaoLocalizada.getPaciente().getRmr())
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comUsuario(usuarioService.obterUsuarioLogado())
			.apply();

	}

	
	private String enviarArquivoDeRelatorio(MultipartFile arquivo, Long idAvaliacao){
		try {
			ArquivoUtil.validarArquivoVoucher(arquivo, messageSource, configuracaoService);
			String diretorio = ArquivoUtil.obterDiretorioAvaliacaoCamaraTecnica(idAvaliacao);
			Instant instant = Instant.now();
			long timeStampMillis = instant.toEpochMilli();
			String caminho = diretorio + "/" + ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo);
			storageService.upload(ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo), diretorio, arquivo);
			return caminho;
		}
		catch (IOException e) {
			throw new BusinessException("arquivo.erro.upload.arquivo", e);
		}
	}

	@Override
	public void alterarStatus(AvaliacaoCamaraTecnica avaliacao) {
		AvaliacaoCamaraTecnica avaliacaoLocalizada = this.findById(avaliacao.getId());
		if(StatusAvaliacaoCamaraTecnica.APROVADO.equals(avaliacao.getStatus().getId()) 
		|| StatusAvaliacaoCamaraTecnica.REPROVADO.equals(avaliacao.getStatus().getId())
		|| avaliacaoLocalizada.getStatus().getId().equals(avaliacao.getStatus().getId())){
			throw new BusinessException("avaliacao.camara.tecnica.status.invalido");
		}
		avaliacaoLocalizada.setStatus(avaliacao.getStatus());
		avaliacaoLocalizada.setDataAtualizacao(LocalDateTime.now());
		this.save(avaliacaoLocalizada);
	}

	@Override
	public AvaliacaoCamaraTecnicaDTO obterAvaliacaoCamaraTecnicaDTO(Long idAvaliacao) {
		AvaliacaoCamaraTecnicaDTO avaliacaoDto = new AvaliacaoCamaraTecnicaDTO();
		AvaliacaoCamaraTecnica avaliacaoCamaraTecnica = this.findById(idAvaliacao);
		avaliacaoDto.setAvaliacaoCamaraTecnica(avaliacaoCamaraTecnica);
		avaliacaoDto.setUltimaEvolucao(pacienteService.obterUltimaEvolucao(avaliacaoCamaraTecnica.getPaciente().getRmr()));
		return avaliacaoDto;
	}

	@Override
	public AvaliacaoCamaraTecnica obterAvaliacaoPor(Long rmr) {
		return findOne(new Equals<Long>("paciente.rmr", rmr));
	}

	@Override
	public Paciente obterPaciente(AvaliacaoCamaraTecnica entity) {
		return entity.getPaciente();
	}

	@Override
	public String[] obterParametros(AvaliacaoCamaraTecnica entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param configuracaoService the configuracaoService to set
	 */
	@Autowired
	public void setConfiguracaoService(IConfiguracaoService configuracaoService) {
		this.configuracaoService = configuracaoService;
	}

	/**
	 * @param pacienteService the pacienteService to set
	 */
	@Autowired
	public void setPacienteService(IPacienteService pacienteService) {
		this.pacienteService = pacienteService;
	}

	/**
	 * @param exameService the exameService to set
	 */
	@Autowired
	public void setExameService(IExamePacienteService exameService) {
		this.exameService = exameService;
	}
	
	


}